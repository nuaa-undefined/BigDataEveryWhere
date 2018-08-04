package org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @Auther: cyw35
 * @Date: 2018/7/27 16:32
 * @Description:
 */
public class GoodsBrowseTimeCompare extends WritableComparator {
    public GoodsBrowseTimeCompare(){
        super(Text.class,true);
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        Text aKey  = (Text)a;
        Text bKey  = (Text)b;

        String aParam = aKey.toString().split("\\s+")[1];
        String bParam = bKey.toString().split("\\s+")[1];


        try {
            return sdf.parse(aParam).before(sdf.parse(bParam)) ? -1: 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
