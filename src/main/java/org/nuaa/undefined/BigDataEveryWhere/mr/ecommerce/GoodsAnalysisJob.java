package org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
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
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce.bean.GoodsBean;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: cyw35
 * @Date: 2018/7/26 20:36
 * @Description:整合出一张商品表
 * 订单号、商品id、省级行政区编号、用户id、性别（1为男）、第一次浏览时间、下单时间、交易金额、是否交易
 */
public class GoodsAnalysisJob {
    public static class GoodsAnalysisMapper extends Mapper<LongWritable, Text, Text, Text> {
        private Text outputKey = new Text();
        private Text outputValue = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] params = value.toString().split("\\s+");
            String output = "";
            for (int i = 2; i < params.length; i++) {
                output += params[i] + " ";
            }
            outputKey.set(params[1]);
            outputValue.set(output);
            context.write(outputKey, outputValue);
        }
    }

    //商品id、省级行政区编号、用户id、性别（1为男）、第一次浏览时间、下单时间、交易金额、是否交易
    public static class GoodsAnalysisReducer extends Reducer<Text, Text, GoodsBean, NullWritable> {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        private Text outputValue = new Text();

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            int successCounts = 0;     //成交次数
            int failCounts = 0;        //未成交次数
            int totalCounts = 0;       //总交易次数
            int maleCounts = 0;         //男性购买次数
            int femaleCounts = 0;       //女性购买次数
            double totalAmounts = 0;       //成交总额
            double singleMaxAmounts = 0;   //单笔最大金额
            String earlistTime = " ";   //最早被浏览时间
            try {
                Date earlistBrowseTime = sdf.parse("2020-01-01");
                for (Text value : values) {
                    String[] params = value.toString().split("\\s+");
                    if (params[6].equals("1")) {  //交易成功的数据
                        successCounts++;
                        if (params[2].equals("1")) {
                            maleCounts++;
                        } else {
                            femaleCounts++;
                        }
                        double amounts = Double.valueOf(params[5]);
                        totalAmounts += amounts;
                        singleMaxAmounts = Math.max(singleMaxAmounts, amounts);
                        earlistBrowseTime = earlistBrowseTime.before(sdf.parse(params[3])) ? earlistBrowseTime : sdf.parse(params[3]);
                    } else {
                        failCounts++;
                    }
                }
                totalCounts = successCounts + failCounts;
                earlistTime = sdf.format(earlistBrowseTime);
                outputValue.set(successCounts + " " + failCounts + " " + totalCounts + " " +
                        maleCounts + " " + femaleCounts + " " + totalAmounts + " " +
                        singleMaxAmounts + " " + earlistTime);
                context.write(new GoodsBean(
                        key.toString(),successCounts,failCounts,totalCounts,maleCounts,femaleCounts,totalAmounts,singleMaxAmounts,earlistTime
                        ),NullWritable.get());

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://master:9000");
        DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver",
                "jdbc:mysql://192.168.120.101:3306/big_data??characterEncoding=utf8&useSSL=false",
                "root", "123456");
        Job job = Job.getInstance(conf,"GoodsAnalysis");
        job.setJarByClass(GoodsAnalysisJob.class);
        job.setMapperClass(GoodsAnalysisMapper.class);
        job.setReducerClass(GoodsAnalysisReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(GoodsBean.class);
        job.setOutputValueClass(NullWritable.class);
        //数据中的第一个字段为key
        job.setInputFormatClass(TextInputFormat.class);

        FileInputFormat.addInputPath(job, new Path("/e-commerce-clean.log"));
        Path outputPath = new Path("/e_commerce_dataset/goods_analysis");

        FileSystem.get(conf).delete(outputPath, true);
        FileOutputFormat.setOutputPath(job, outputPath);

        DBOutputFormat.setOutput(job, "goods",
                "id","successCounts","failCounts","totalCounts","maleCounts",
                "femaleCounts","totalAmounts","singleMaxAmounts","earlistTime");

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
