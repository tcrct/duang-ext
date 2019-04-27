package com.duangframework.ext.aliyun.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
//import com.duangframework.ext.ConstEnum;
import com.duangframework.ext.IClient;
import com.duangframework.kit.ToolsKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OssUtils implements IClient<OSSClient> {

    private static final Logger logger = LoggerFactory.getLogger(OssUtils.class);

    private static Lock lock = new ReentrantLock();
    private static OSSClient ossClient;
    private static OssUtils INSTANCE;

    public static OssUtils getInstance() {
        try {
            lock.lock();
            if (ToolsKit.isEmpty(INSTANCE)) {
                INSTANCE = new OssUtils();
            }
        } catch (Exception e) {
            logger.warn("OssUtils getInstance is fail: " + e.getMessage(), e);
        }finally {
            lock.unlock();
        }
        return INSTANCE;
    }
    @Override
    public OSSClient getClient() throws Exception {
        if(ToolsKit.isEmpty(ossClient)) {
            // 创建OSSClient实例
//            ossClient = new OSSClient(ConstEnum.ALIYUN.OSS_ENDPOINT.getValue(),
//                    ConstEnum.ALIYUN.ACCESS_KEY_ID.getValue(),
//                    ConstEnum.ALIYUN.ACCESS_KEY_SECRET.getValue());
        }
        return ossClient;
    }

    @Override
    public boolean isSuccess() {
        return ToolsKit.isNotEmpty(ossClient);
    }


//    /**
//     * 创建存储空间
//     * @param bucketName        存储空间名称
//     * @return
//     */
//    public Bucket createBucket(String bucketName) {
//        try {
//            return getClient().createBucket(bucketName);
//        } catch (Exception e) {
//            logger.warn("create bucket: " + bucketName + " is fail: " + e.getMessage(), e);
//            return null;
//        }
//    }
//
//    /**
//     * 删除存储空间
//     * @param bucketName        存储空间名称
//     * @return
//     */
//    public void deleteBucket(String bucketName) {
//        try {
//            getClient().deleteBucket(bucketName);
//        } catch (Exception e) {
//            logger.warn("delete bucket: " + bucketName + " is fail: " + e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 上传文件到指定的bucket
//     *
//     * @param bucketName
//     *            Bucket是OSS上的命名空间，相当于数据的容器，可以存储若干数据实体（Object）
//     * @param filePath
//     *            文件相对路径，即除去域名或IP，端口部份，包含文件
//     * @param filePath
//     *            要上传的文件
//     * @throws FileNotFoundException
//     */
//    public void putObject(String bucketName, String filePath, File file) throws Exception {
//        // 获取指定文件
//        getClient().putObject(bucketName, filePath, file);
//    }
}
