package com.duangframework.ext.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class PdfMain {

    private static final String DEST = "d:/apidoc/HelloWorld_CN_HTML.pdf";
    private static final String HTML = "d:/apidoc/template.html";
    private static final String FONT = "C:\\Windows\\Fonts\\simhei.ttf";
    private static final String imageFile = "C:\\Users\\laotang\\Pictures\\营业执照.jpg";


    public static void main1(String[] args) throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
        // step 3
        document.open();
        // step 4
        XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
        fontImp.register(FONT);
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream(HTML), null, Charset.forName("UTF-8"), fontImp);
        // step 5
        document.close();
    }
    public static void main(String[] args) throws IOException, DocumentException {
        BaseFont bfComic5 = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        Font font = new Font(bfComic5, 14);
        String text1 = "啊发生!@#！@#的发球特工是大哥是法国时的风格是This is the quite popular True Type font (繁體字測試VS简体字测试) ==>"+new java.util.Date();
        Document document = new Document();
        Image img = Image.getInstance(imageFile);
        //设置绝对坐标
//        img.setAbsolutePosition(100, 250);
        //获得图片的高度
        float heigth=img.getHeight();
        float width=img.getWidth();
        System.out.println("heigth：----"+heigth);
        System.out.println("width：  ----"+width);
        int percent= PdfUtils.getPercent2(heigth, width);
        System.out.println("--"+percent);
        //设置图片显示位置
        img.setAlignment(Image.LEFT);
        //直接设置图片的大小~~~~~~~第三种解决方案，按固定比例压缩
        //jpg1.scaleAbsolute(210.0f, 297.0f);
        //按百分比显示图片的比例
        img.scalePercent(percent);//表示是原来图像的比例;
        //可设置图像高和宽的比例
        //jpg1.scalePercent(50, 100);

        PdfPTable table = new PdfPTable(8);
        // 宽度百分比
        table.setWidthPercentage(100);
        // 是否显示边框
//        table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        java.util.List<java.util.List<String>> dataset = PdfUtils.getData();
        for (java.util.List<String> record : dataset) {
            for (String field : record) {
                table.addCell(field);
            }
        }
        try {
            PdfWriter.getInstance(document, new FileOutputStream(DEST));
            document.open();
            document.add(new Paragraph(text1, font));
            document.add(img);
            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
