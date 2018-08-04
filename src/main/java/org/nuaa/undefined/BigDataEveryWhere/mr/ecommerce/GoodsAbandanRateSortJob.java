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
 * @Date: 2018/7/27 15:01
 * @Description:对商品购买放弃率排序
 * 商品id 、商品购买放弃率
 */
public class GoodsAbandanRateSortJob {
    public static class GoodsAbandanRateSortMapper extends Mapper<Text,Text,Text,Text> {
        private Text outputKey = new Text();
        private Text outputValue = new Text();

        @Override
        protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            String param  = value.toString();
            outputKey.set(key + "-" + param);
            outputValue.set(param);
            context.write(outputKey,outputValue);
        }
    }

    public static class TransactionSortReducer extends Reducer<Text,Text,Text,Text> {
        private Text outputKey = new Text();
        private Text outputValue = new Text();

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for(Text value:values){
                outputValue.set(String.format("%.2f",100 * Double.valueOf(value.toString())) + "%");
                outputKey.set(key.toString().split("-")[0]);
                context.write(outputKey,outputValue);
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://master:9000");

        Job job = new Job(conf, "GoodsAbandanRateSortJob");
        job.setJarByClass(GoodsAbandanRateSortJob.class);
        job.setMapperClass(GoodsAbandanRateSortMapper.class);
        job.setReducerClass(TransactionSortReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        //排序实在map之后的，reduce之前的
        job.setSortComparatorClass(GoodsAbandanRateCompare.class);
        job.setGroupingComparatorClass(GoodsAbandanRateCompare.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        //数据中的第一个字段为key
        job.setInputFormatClass(KeyValueTextInputFormat.class);

        FileInputFormat.addInputPath(job, new Path("/e_commerce_dataset/goods_abandon_rate/part-r-00000"));
        Path outputPath = new Path("/e_commerce_dataset/goods_abandon_rate_sort");

        FileSystem.get(conf).delete(outputPath, true);
        FileOutputFormat.setOutputPath(job, outputPath);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}
