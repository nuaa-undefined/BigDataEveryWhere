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
import org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce.bean.EComMonthBean;
import org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce.bean.ECommerceYearBean;

import java.io.IOException;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/2 21:27
 */
public class OutputECommerceMonthToDBJob {
    public static String inputPathString = "project/e-commerce-clean/part-r-00000";
    public static String outputPathString = "project/e-commerce-consume-month-distribution";
    public static String jobName = "output-month-distribution";

    public static class OutputECommerceMonthMapper extends Mapper<LongWritable, Text, Text, Text> {
        private Text outputKey = new Text();
        private Text outputValue = new Text();
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String [] data = value.toString().split("\\s+");
            outputKey.set(data[6].split("-")[1]);
            outputValue.set(data[4]+"|"+data[7]+"|"+data[8]);
            context.write(outputKey, outputValue);
        }
    }

    public static class OutputECommerceMonthReducer extends Reducer<Text, Text, EComMonthBean, NullWritable> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            int sumConsumeCount = 0;
            int manConsumeCount = 0;
            int womanConsumeCount = 0;
            int successConsumeCount = 0;
            int failConsumeCount = 0;
            double sumConsumeMoney = 0;
            double manConsumeMoney = 0;
            double womanConsumeMoney = 0;
            double maxMoney = 0;
            for (Text in : values) {
                String [] data = in.toString().split("\\|");
                double money = Double.parseDouble(data[1]);
                int success = Integer.parseInt(data[2]);
                sumConsumeCount++;
                sumConsumeMoney += success * money;
                successConsumeCount += success;
                failConsumeCount += success == 1 ? 0 : 1;
                maxMoney = Math.max(maxMoney, success * money);
                if (ConstCommerceValue.MAN_SEX.equals(data[0])) {
                    manConsumeCount++;
                    manConsumeMoney += success * money;
                } else {
                    womanConsumeCount++;
                    womanConsumeMoney += success * money;
                }
            }
            context.write(new EComMonthBean(Integer.parseInt(key.toString()),
                    sumConsumeCount, successConsumeCount, failConsumeCount, sumConsumeMoney,
                    manConsumeMoney, womanConsumeMoney, maxMoney, manConsumeCount, womanConsumeCount
            ), NullWritable.get());
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://master:9000/");
        DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver",
                "jdbc:mysql://192.168.163.101:3306/big_data??characterEncoding=utf8&useSSL=false",
                "root", "123456");
        Job job = Job.getInstance(conf, jobName);
        job.setMapperClass(OutputECommerceMonthMapper.class);
        job.setReducerClass(OutputECommerceMonthReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(EComMonthBean.class);
        job.setOutputValueClass(NullWritable.class);
        FileInputFormat.addInputPath(job, new Path(inputPathString));
        DBOutputFormat.setOutput(job, "e_commerce_month",
                "month","buy_sum","success_sum","fail_sum","money_sum",
                "man_money_sum","woman_money_sum","man_buy_count",
                "woman_buy_count", "max_money");
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
