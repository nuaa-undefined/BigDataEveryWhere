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
 * @Date: 2018/7/27 11:50
 * @Description:商品购买放弃率（到目前为止的情况）
 * 商品id、成交次数、未成交次数、总交易次数、男性购买次数、女性购买次数、成交总额、单笔最大金额、最早被浏览时间
 */
public class GoodsAbandonRateJob {
    public static class GoodsAbandonRateMapper extends Mapper<Text,Text,Text,Text> {

        private Text outputValue = new Text();

        @Override
        protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            String param[]  = value.toString().split("\\s+");

            outputValue.set(param[1] + "-" + param[2]);
            context.write(key,outputValue);
        }
    }
    public static class GoodsAbandonRateReducer extends Reducer<Text,Text,Text,Text> {
        private Text outputValue = new Text();

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for(Text value:values){
                String param[] = value.toString().split("-");
                double rate = Double.valueOf(param[0])/ Double.valueOf(param[1]);
                outputValue.set(String.format("%.4f", rate));
                context.write(key,outputValue);
            }
        }
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://master:9000");

        Job job = new Job(conf, "GoodsAbandonRateJob");
        job.setJarByClass(GoodsAbandonRateJob.class);
        job.setMapperClass(GoodsAbandonRateMapper.class);
        job.setReducerClass(GoodsAbandonRateReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        //数据中的第一个字段为key
        job.setInputFormatClass(KeyValueTextInputFormat.class);

        FileInputFormat.addInputPath(job, new Path("/e_commerce_dataset/goods_analysis/part-r-00000"));
        Path outputPath = new Path("/e_commerce_dataset/goods_abandon_rate");

        FileSystem.get(conf).delete(outputPath, true);
        FileOutputFormat.setOutputPath(job, outputPath);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
