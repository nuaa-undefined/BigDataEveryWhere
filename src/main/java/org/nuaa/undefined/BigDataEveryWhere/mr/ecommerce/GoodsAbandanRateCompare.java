package org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @Auther: cyw35
 * @Date: 2018/7/27 15:08
 * @Description:
 */
public class GoodsAbandanRateCompare extends WritableComparator {
    public GoodsAbandanRateCompare(){
        super(Text.class,true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        Text aKey  = (Text)a;
        Text bKey  = (Text)b;

        Double aParam = Double.valueOf(aKey.toString().split("-")[1]);
        Double bParam = Double.valueOf(bKey.toString().split("-")[1]);

        return aParam == bParam ? 1: bParam.compareTo(aParam);

    }
}
