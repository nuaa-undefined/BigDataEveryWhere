package org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @Auther: cyw35
 * @Date: 2018/7/27 16:44
 *@Description:商品女性购买率
 * 商品id、成交次数、未成交次数、总交易次数、男性购买次数、女性购买次数、成交总额、单笔最大金额、最早被浏览时间
 */
public class GoodsFemalePurchasedRateJob {
    public static class GoodsFemalePurchasedRateMapper extends Mapper<Text,Text,Text,Text> {

        private Text outputValue = new Text();

        @Override
        protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            String param[]  = value.toString().split("\\s+");

            outputValue.set(param[3] + "-" + param[4]);
            context.write(key,outputValue);
        }
    }
    public static class GoodsFemalePurchasedRateReducer extends Reducer<Text,Text,Text,Text> {
        private Text outputValue = new Text();

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for(Text value:values){
                String param[] = value.toString().split("-");
                Double male = Double.valueOf(param[0]);
                Double female = Double.valueOf(param[1]);
                outputValue.set(dataFormat(male,female));
                context.write(key,outputValue);
            }
        }
        private String dataFormat(Double male ,Double female){
            double femaleRate = 100 * female / (male + female);
            return String.format("%.2f", femaleRate) + "%" ;
        }
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://master:9000");

        Job job = new Job(conf, "GoodsFemalePurchasedRateJob");
        job.setMapperClass(GoodsFemalePurchasedRateMapper.class);
        job.setReducerClass(GoodsFemalePurchasedRateReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        //数据中的第一个字段为key
        job.setInputFormatClass(KeyValueTextInputFormat.class);

        FileInputFormat.addInputPath(job, new Path("/e_commerce_dataset/goods_analysis/part-r-00000"));
        Path outputPath = new Path("/e_commerce_dataset/goods_female_purchased_rate");

        FileSystem.get(conf).delete(outputPath, true);
        FileOutputFormat.setOutputPath(job, outputPath);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
