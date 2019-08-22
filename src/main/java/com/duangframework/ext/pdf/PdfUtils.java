package com.duangframework.ext.pdf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PdfUtils {
    /**
     * 第一种解决方案
     * 在不改变图片形状的同时，判断，如果h>w，则按h压缩，否则在w>h或w=h的情况下，按宽度压缩
     * @param h
     * @param w
     * @return
     */

    public static int getPercent(float h,float w)
    {
        int p=0;
        float p2=0.0f;
        if(h>w)
        {
            p2=297/h*100;
        }
        else
        {
            p2=210/w*100;
        }
        p=Math.round(p2);
        return p;
    }
    /**
     * 第二种解决方案，统一按照宽度压缩
     * 这样来的效果是，所有图片的宽度是相等的，自我认为给客户的效果是最好的
     * @param args
     */
    public static int getPercent2(float h,float w) {
        int p = 0;
        float p2 = 0.0f;
        p2 = 530 / w * 100;
        p = Math.round(p2);
        return p;
    }

    /**
     *
     * @return
     */
    public static List<List<String>> getData() {
        List<List<String>> data = new ArrayList<List<String>>();
        String[] tableTitleList = {" Title", " (Re)set", " Obs", " Mean", " Std.Dev", " Min", " Max", "Unit"};
        data.add(Arrays.asList(tableTitleList));
        for (int i = 0; i < 10; ) {
            List<String> dataLine = new ArrayList<String>();
            i++;
            for (int j = 0; j < tableTitleList.length; j++) {
                dataLine.add(tableTitleList[j] + " " + i);
            }
            data.add(dataLine);
        }
        return data;
    }
}
