package org.nuaa.undefined.BigDataEveryWhere.mr.hero;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 *  Title:UserInfo.java
 *  Description:
 *  统计用户信息 : id，胜率，所有英雄
 *  输入文件：/gamelog_heros/Info/part-r-00000
 *  输出文件：/gamelog_heros/result/UserInfo
 *  以id为key
 *  @author Growarm
 *  @date 下午7:25:50
 *  version 1.0
 */
public class UserInfo {
	
	public static class UserInfoMapper extends Mapper<LongWritable,Text, Text, Text>{

		private Text outputKey = new Text();
		private Text outputValue = new Text();
		//Fiddlesticks		0	98
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
		
			String[] words = value.toString().split("\\s+");
			outputKey.set(words[2]);
			outputValue.set(words[0] + "\t" + words[1]);
			context.write(outputKey,outputValue);
		}		
	}
	
	private static class UserInfoReducer extends Reducer<Text, Text, Text, NullWritable>{
		
		private Text outputKey = new Text();
		
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			
			Set<String> set = new HashSet<String>();
			String word = new String();
			
			int sumNum = 0;
			int winNum = 0;
			
			for (Text value : values) {
				
				sumNum++;
				String[] words = value.toString().split("\\s+");
				set.add(words[0]);
				
				if (words[1].equals("1")) {
					winNum++;
				}
			}
			
			for (String heros : set) {
				word = word + "\t" + heros;
			}
			
			String winRate = String.format("%.2f", 1.0 * winNum / sumNum * 100 ) + "%";
			
			outputKey.set(key + "\t" + winRate + "\t" + word);
			context.write(outputKey, NullWritable.get());
		}
	}
	
	public static void main(String[] args) {
		
		try {
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://master:9000");

			Job UserInfoJob;
			UserInfoJob=Job.getInstance(conf, "UserInfo");
			UserInfoJob.setJarByClass(UserInfo.class);

			UserInfoJob.setMapperClass(UserInfoMapper.class);
			UserInfoJob.setReducerClass(UserInfoReducer.class);

			UserInfoJob.setMapOutputKeyClass(Text.class);
			UserInfoJob.setMapOutputValueClass(Text.class);

			UserInfoJob.setOutputKeyClass(Text.class);
			UserInfoJob.setOutputValueClass(NullWritable.class);

			UserInfoJob.setInputFormatClass(TextInputFormat.class);	

			FileInputFormat.addInputPath(UserInfoJob, new Path("/gamelog_heros/Info/part-r-00000"));
			Path outputPath = new Path("/gamelog_heros/result/UserInfo");

			FileSystem.get(conf).delete(outputPath,true);
			FileOutputFormat.setOutputPath(UserInfoJob, outputPath);

			System.exit(UserInfoJob.waitForCompletion(true)?0:1);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
