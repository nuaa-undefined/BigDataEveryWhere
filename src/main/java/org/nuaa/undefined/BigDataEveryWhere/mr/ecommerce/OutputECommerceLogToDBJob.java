package org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.lib.db.DBConfiguration;
import org.apache.hadoop.mapred.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce.bean.ECommerceLogBean;

import java.io.IOException;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/2 10:42
 */
public class OutputECommerceLogToDBJob {
    public static String inputPathString = "project/e-commerce-clean/part-r-00000";
    public static String jobName = "output-e-commerce-log-todb";
    public static class OutputECommerceLogMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            context.write(value, NullWritable.get());
        }
    }

    public static class OutputECommerceLogReducer extends Reducer<Text, Text, ECommerceLogBean, NullWritable> {
        private Text outputValue = new Text();

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            context.write(new ECommerceLogBean(key.toString()), NullWritable.get());
        }
    }
    public static void main(String [] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://master:9000/");
        DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver",
                "jdbc:mysql://192.168.163.101:3306/big_data??characterEncoding=utf8&useSSL=false",
                "root", "123456");
        Job job = Job.getInstance(conf, jobName);
        job.setMapperClass(OutputECommerceLogMapper.class);
        job.setReducerClass(OutputECommerceLogReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setOutputKeyClass(ECommerceLogBean.class);
        job.setOutputValueClass(NullWritable.class);
        FileInputFormat.addInputPath(job, new Path(inputPathString));
        DBOutputFormat.setOutput(job, "e_commerce_log",
                "ecom_id","good_id","region_id","user_id","sex",
                "beg_time","buy_time","money","status");
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
