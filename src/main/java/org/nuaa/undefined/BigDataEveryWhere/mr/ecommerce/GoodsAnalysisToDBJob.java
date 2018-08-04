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
import org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce.bean.GoodsAllBean;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: cyw35
 * @Date: 2018/8/1 11:53
 * @Description:生成新的商品表（对应到数据库中） 商品表：商品id、成交次数、未成交次数、总交易次数、男性购买次数、女性购买次数、成交总额、上市季度、
 * 消费最高地区、购买放弃率、商品男性购买率、女性购买率
 */
public class GoodsAnalysisToDBJob {
    public static class GoodsAnalysisToDBMapper extends Mapper<LongWritable, Text, Text, Text> {
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
    public static class GoodsAnalysisToDBReducer extends Reducer<Text, Text, GoodsAllBean, NullWritable> {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        private Text outputValue = new Text();

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            Map<String, Integer> map = new TreeMap<>();
            int successCounts = 0;      //成交次数
            int failCounts = 0;         //未成交次数
            int totalCounts = 0;        //总交易次数
            int maleCounts = 0;         //男性购买次数
            int femaleCounts = 0;       //女性购买次数
            double totalAmounts = 0;    //成交总额
            int season = 0;             //上市季度
            String highestRegion = "";
            double abandonRate = 0;
            double maleRate = 0;
            double femaleRate = 0;
            try {
                Date earlistBrowseTime = sdf.parse("2020-01-01");
                for (Text value : values) {
                    String[] params = value.toString().split("\\s+");
                    if (params[6].equals("1")) {  //交易成功的数据
                        successCounts++;
                        addRegion(map, params[0]);
                        if (params[2].equals("1")) {
                            maleCounts++;
                        } else {
                            femaleCounts++;
                        }
                        totalAmounts += Double.valueOf(params[5]);
                        earlistBrowseTime = earlistBrowseTime.before(sdf.parse(params[3])) ? earlistBrowseTime : sdf.parse(params[3]);
                    } else {
                        failCounts++;
                    }
                }
                totalCounts = successCounts + failCounts;
                season = judgeSeason(earlistBrowseTime);
                //将treeMap 按值排序
                List<Map.Entry<String, Integer>> entryArrayList = new ArrayList<>(map.entrySet());
                Collections.sort(entryArrayList, (o1, o2) -> o1.getValue().compareTo(o2.getValue()));

                abandonRate = successCounts / (double) totalCounts;
                maleRate = maleCounts / (double) successCounts;
                femaleRate = femaleCounts / (double) successCounts;
                context.write(new GoodsAllBean(
                        key.toString(), successCounts, failCounts, totalCounts, maleCounts, femaleCounts,
                        totalAmounts, season, highestRegion, abandonRate, maleRate, femaleRate
                ), NullWritable.get());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        private void addRegion(Map map, String region) {
            if (map.containsKey(region)) {
                Integer temp = (Integer) map.get(region);
                map.remove(region);
                map.put(region, temp + 1);
            } else {
                map.put(region, 1);
            }
        }

        private int judgeSeason(Date earlistBrowseTime) {
            Integer month = Integer.valueOf(sdf.format(earlistBrowseTime).split("-")[1]);
            switch (month) {
                case 1:
                case 2:
                case 3:
                    return 1;
                case 4:
                case 5:
                case 6:
                    return 2;
                case 7:
                case 8:
                case 9:
                    return 3;
                case 10:
                case 11:
                case 12:
                    return 4;
            }
            return 0;
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://master:9000");
        DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver",
                "jdbc:mysql://192.168.120.101:3306/big_data??characterEncoding=utf8&useSSL=false",
                "root", "123456");
        Job job = Job.getInstance(conf, "GoodsAnalysisToDBJob");
        job.setMapperClass(GoodsAnalysisToDBMapper.class);
        job.setReducerClass(GoodsAnalysisToDBReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(GoodsAllBean.class);
        job.setOutputValueClass(NullWritable.class);
        //数据中的第一个字段为key
        job.setInputFormatClass(TextInputFormat.class);

        FileInputFormat.addInputPath(job, new Path("/e-commerce-clean.log"));
        Path outputPath = new Path("/e_commerce_dataset/goods_analysis");

        FileSystem.get(conf).delete(outputPath, true);
        FileOutputFormat.setOutputPath(job, outputPath);

        DBOutputFormat.setOutput(job, "e_commerce_goods",
                "id", "success_counts", "fail_counts", "total_counts", "male_counts", "female_counts",
                "total_amounts", "season", "highest_region", "abandon_rate", "male_rate", "female_rate");

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
