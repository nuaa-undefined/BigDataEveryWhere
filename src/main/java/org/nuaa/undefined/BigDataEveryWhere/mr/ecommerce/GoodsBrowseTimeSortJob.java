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
import java.text.SimpleDateFormat;

/**
 * @Auther: cyw35
 * @Date: 2018/7/27 16:30
 * @Description:商品最早被浏览时间排序
 * 商品id、成交次数、未成交次数、总交易次数、男性购买次数、女性购买次数、成交总额、单笔最大金额、最早被浏览时间
 */
public class GoodsBrowseTimeSortJob {
    public static class GoodsMaxAmountsSortMapper extends Mapper<Text,Text,Text,Text> {
        private Text outputKey = new Text();
        private Text outputValue = new Text();

        @Override
        protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            String param  = value.toString().split("\\s+")[7];
            outputKey.set(key + " " + param);
            outputValue.set(param);
            context.write(outputKey,outputValue);
        }
    }

    public static class GoodsMaxAmountsSortReducer extends Reducer<Text,Text,Text,Text> {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        private Text outputKey = new Text();
        private Text outputValue = new Text();

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for(Text value:values){
                outputValue.set(value);
                outputKey.set(key.toString().split("\\s+")[0]);
                context.write(outputKey,outputValue);
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://master:9000");

        Job job = new Job(conf, "GoodsBrowseTimeSortJob");
        job.setMapperClass(GoodsMaxAmountsSortMapper.class);
        job.setReducerClass(GoodsMaxAmountsSortReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        //排序实在map之后的，reduce之前的
        job.setSortComparatorClass(GoodsBrowseTimeCompare.class);
        job.setGroupingComparatorClass(GoodsBrowseTimeCompare.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        //数据中的第一个字段为key
        job.setInputFormatClass(KeyValueTextInputFormat.class);

        FileInputFormat.addInputPath(job, new Path("/e_commerce_dataset/goods_analysis/part-r-00000"));
        Path outputPath = new Path("/e_commerce_dataset/goods_browse_time_sort");

        FileSystem.get(conf).delete(outputPath, true);
        FileOutputFormat.setOutputPath(job, outputPath);



        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
