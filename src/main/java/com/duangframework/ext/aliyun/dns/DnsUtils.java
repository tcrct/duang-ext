package com.duangframework.ext.aliyun.dns;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.*;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse.Record;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainsResponse.Domain;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
//import com.duangframework.ext.ConstEnum;
import com.duangframework.ext.IClient;
import com.duangframework.kit.PropKit;
import com.duangframework.kit.ToolsKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 *
 * https://help.aliyun.com/document_detail/29772.html?spm=5176.doc29740.2.20.Bh81JP
 * https://help.aliyun.com/document_detail/29821.html?spm=a2c4g.11186623.6.679.7cf21ff63Ma7tA
 * @author pc_01
 *
 */
public class DnsUtils implements IClient<IAcsClient> {

	private static final Logger logger = LoggerFactory.getLogger(DnsUtils.class);

	private static IAcsClient dnsClient = null;
	private static String DOMAIN = "signetz.com";
	private static String PRODUCT = "Alidns";
	private static String ALIDNS_DOMAIN = "alidns.aliyuncs.com";
	private static String[] RRS = {"dev","bug", "dev-platform","crm"};
	private static Lock lock = new ReentrantLock();
	private static DnsUtils INSTANCE;

	public static DnsUtils getInstance() {
		try {
			lock.lock();
			if (ToolsKit.isEmpty(INSTANCE)) {
				INSTANCE = new DnsUtils();
				INSTANCE.getClient();
			}
		} catch (Exception e) {
			logger.warn("SmsUtils getInstance is fail: " + e.getMessage(), e);
		}finally {
			lock.unlock();
		}
		return INSTANCE;
	}

	private DnsUtils() {
	}

	@Override
	public IAcsClient getClient() throws Exception {
		if(ToolsKit.isNotEmpty(dnsClient)) {
			return dnsClient;
		}
//		IClientProfile profile = DefaultProfile.getProfile(ConstEnum.ALIYUN.DNS_REGION_ID.getValue(),
//				ConstEnum.ALIYUN.ACCESS_KEY_ID.getValue(), ConstEnum.ALIYUN.ACCESS_KEY_SECRET.getValue());
//		// 若报Can not find endpoint to access异常，请添加以下此行代码
//		try {
//			DefaultProfile.addEndpoint(ConstEnum.ALIYUN.DNS_REGION_ID.getValue(), ConstEnum.ALIYUN.DNS_REGION_ID.getValue(), PRODUCT, ALIDNS_DOMAIN);
//		} catch (ClientException e) {
//			e.printStackTrace();
//		}
//		dnsClient = new DefaultAcsClient(profile);
		return dnsClient;
	}

	@Override
	public boolean isSuccess() {
		return ToolsKit.isNotEmpty(dnsClient);
	}

	public static void main(String [] args) throws Exception {
		DnsUtils dnsUtils = DnsUtils.getInstance();
		dnsUtils.run(DOMAIN,Arrays.asList(RRS));
	}

	/**
	 *  运行刷新DNS
	 * @param domain		域名，去掉www部分
	 * @param rrsArray		域名前缀，即第一个.号前的字符，要刷新的域名前缀集合
	 * @throws Exception
	 */
	public void run(String domain, List<String> rrsArray) throws Exception{
		List<String> rrsList;
		String[] tmpRRS = null;
		if(ToolsKit.isEmpty(rrsArray)) {
			rrsArray = PropKit.getList("alidns.rrs");
			if(ToolsKit.isNotEmpty(rrsArray)) {
				tmpRRS = rrsArray.toArray(new String[]{});
			}
		}
		if(ToolsKit.isEmpty(domain)) {
			domain = PropKit.get("dns.domain","");
			if(ToolsKit.isNotEmpty(domain)) {
				DOMAIN = domain;
			}
		}
		if(ToolsKit.isEmpty(tmpRRS)) {
			tmpRRS = RRS;
		}
		rrsList = new ArrayList<String>(tmpRRS.length);
		rrsList.addAll(Arrays.asList(tmpRRS));
        String localIp ="";
		String ipJsonString = "";
		try {
			ipJsonString = getRouteIp("http://ip.taobao.com/service/getIpInfo2.php?ip=myip"); //取淘宝IP地址库
			if (ipJsonString.startsWith("{") && ipJsonString.endsWith("}")) {
				Map<String, Object> ipMap = ToolsKit.jsonParseObject(ipJsonString, Map.class);
				String code = ipMap.get("code") + "";
				if (ToolsKit.isNotEmpty(code) && "0".equals(code)) {
					JSONObject dataObj = (JSONObject) ipMap.get("data");
					localIp = dataObj.get("ip").toString();
				}
			} else {
				localIp = localIp.substring(localIp.indexOf("ip") + 5, localIp.length() - 3);        //自定义的截取方法
			}
		} catch (Exception e) {
			System.err.println("get taobao ip address is fail: " + e.getMessage() +" so get soho ip address");
			ipJsonString = getRouteIp("http://pv.sohu.com/cityjson"); //取sohu IP 地址库
			ipJsonString = ipJsonString.substring(ipJsonString.indexOf("{"), ipJsonString.indexOf("}")+1);
			if (ToolsKit.isNotEmpty(ipJsonString) && ipJsonString.startsWith("{") && ipJsonString.endsWith("}")) {
				Map<String, Object> ipMap = ToolsKit.jsonParseObject(ipJsonString, Map.class);
				localIp = ipMap.get("cip") + "";
			}
		}
        System.out.println("#############localIp: " + localIp);
//        try{
//			Double.parseDouble(localIp.replace(".", ""));
//		} catch(Exception e){System.err.println(localIp);e.printStackTrace();}
		Map<String, Record> recordMap = getDomainRecords(DOMAIN, rrsList.toArray(new String[]{}));
		updateLocalDomainIp(recordMap, localIp);
	}

	private void updateLocalDomainIp(Map<String, Record> recordMap, String routeIp) {
		if (recordMap.isEmpty()) {
			throw new NullPointerException("recordMap is null");
		}
		try {
			for (Iterator<Entry<String, Record>> it = recordMap.entrySet().iterator(); it.hasNext();) {
				UpdateDomainRecordRequest request = new UpdateDomainRecordRequest();
				Entry<String, Record> entry = it.next();
				Record record = entry.getValue();
				if (record.getValue().equals(routeIp)){
					System.out.println(record.getRR()+"."+record.getDomainName()+"所使用的IP地址是： "+record.getValue()+",  与路由IP："+routeIp+ "是同一个IP地址， 所以不用更新！直接退出本次操作。");
					continue; // 如果是同一个IP地址则不用更新
				}
				request.setRecordId(record.getRecordId());
				request.setRR(entry.getKey());
				request.setType("A");
				request.setValue(routeIp);
				request.setTTL(600l);
				UpdateDomainRecordResponse response = dnsClient.getAcsResponse(request);
//				System.out.println(response.getRequestId() + "                        " + response.getRecordId());
				System.out.println(response.getRequestId() + "                        " + response.getRecordId()+"                         "+record.getRR()+"."+record.getDomainName()+"现使用的IP地址是： "+routeIp);
			}
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	private Map<String, Record> getDomainRecords(String domain, String[] RRS) {
		DescribeDomainRecordsRequest request = new DescribeDomainRecordsRequest();
		request.setDomainName(domain);
		request.setPageNumber(1l);
		request.setPageSize(500l);
		DescribeDomainRecordsResponse response;
		Map<String, Record> recordMap = new HashMap<String, Record>();
		try {
			response = dnsClient.getAcsResponse(request);
			List<Record> list = response.getDomainRecords();
			for (Record record : list) {
				for (int i = 0; i < RRS.length; i++) {
					if (record.getRR().equalsIgnoreCase(RRS[i])) {
						recordMap.put(RRS[i], record);
						break;
					}
				}
				// System.out.println(record.getRR()+"
				// "+record.getDomainName()+" "+record.getRecordId()+"
				// "+record.getRR()+" "+record.getType()+" "+record.getTTL()+"
				// "+record.getValue());
			}
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return recordMap;
	}

	private String getRouteIp(String url) {
		URLConnection urlConnection = null;
		HttpURLConnection httpURLConnection = null;
		try {
			URL ipUrl = new URL(url);
			urlConnection = ipUrl.openConnection();
			httpURLConnection = (HttpURLConnection) urlConnection;
			httpURLConnection.connect();
			return inputStream2String(httpURLConnection.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			httpURLConnection.disconnect();
		}
	}

	private String inputStream2String(InputStream is) {
		InputStreamReader in = null;
		BufferedReader br = null;
		try {
			in = new InputStreamReader(is);
			br = new BufferedReader(in);
			StringBuilder showBuilder = new StringBuilder();
			String inLine = "";
			while ((inLine = br.readLine()) != null) {
//				System.out.println(">>>>>>>>>>>>>>>>>>" + inLine);
				showBuilder.append(inLine);
			}
			return showBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception ex) {
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (Exception ex) {
				}
			}
		}
	}

	private void getAllDomains() {
		DescribeDomainsRequest request = new DescribeDomainsRequest();
		DescribeDomainsResponse response;
		// describeRegionsRequest.setProtocol(ProtocolType.HTTPS); //指定访问协议
		// describeRegionsRequest.setAcceptFormat(FormatType.JSON); //指定api返回格式
		// describeRegionsRequest.setMethod(MethodType.POST); //指定请求方法
		// describeRegionsRequest.setRegionId("cn-hangzhou");//指定要访问的Region,仅对当前请求生效，不改变client的默认设置。
		try {
			response = dnsClient.getAcsResponse(request);
			List<Domain> list = response.getDomains();
			System.out.println(response.getRequestId());
			for (Domain domain : list) {
				System.out.println(domain.getDomainId() + "                        " + domain.getDomainName());
			}
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
}
