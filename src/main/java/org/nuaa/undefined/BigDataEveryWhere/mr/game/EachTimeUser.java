package org.nuaa.undefined.BigDataEveryWhere.mr.game;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @author wyw
 * @description 不同时间段（0~4，4~8，8~12，12~16，16~20，20~24）的活跃用户比例（饼状图）
 */
public class EachTimeUser {

	public static class EachTimeUserMapper extends Mapper<Text, Text, Text, Text> {
		
		private Text outputValue = new Text();
		@Override
		protected void map(Text key, Text value, Context context)
				throws IOException, InterruptedException {

			String loginTime = value.toString().split("\\s+")[2];
			String time = loginTime.split("T")[1];
			String hour = time.split(":")[0];
			outputValue.set(hour);
			context.write(key, outputValue);
		}
	}
	
	public static class EachTimeUserRedecer extends Reducer<Text, Text, Text, NullWritable> {
		
		private Text outputKey = new Text();
		private int[] timeUser = {0,0,0,0,0,0};
		
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {

			for (Text value : values) {
				int hour = Integer.parseInt(value.toString());
				if(hour >= 0 && hour < 4){
					timeUser[0]++;
				}else if (hour >= 4 && hour <8) {
					timeUser[1]++;
				}else if (hour >=8 && hour <12) {
					timeUser[2]++;
				}else if (hour >= 12 && hour <16) {
					timeUser[3]++;
				}else if (hour >= 16 && hour < 20) {
					timeUser[4]++;
				}else if (hour >= 20 && hour <24) {
					timeUser[5]++;
				}
			}
		}
		
		@Override
		protected void cleanup(Context context)
				throws IOException, InterruptedException {

			int sum = 0;
			for (int i = 0; i < 6; i++) {
				sum += timeUser[i];
			}
			String[] userPercentage = {null,null,null,null,null,null};
			for (int i = 0; i < 6; i++) {
				userPercentage[i] = String.format("%.2f", timeUser[i]/(double)sum*100);
			}
			outputKey.set("0~4时登陆的人数为：" + timeUser[0] + "\n" + "比例为：" + userPercentage[0] + "%\n\n" + "4~8时登陆的人数为：" + timeUser[1] + "\n" + "比例为：" + userPercentage[1] + "%\n\n" + "8~12时登陆的人数为：" + timeUser[2] + "\n" + "比例为：" + userPercentage[2] + "%\n\n" + "12~16时登陆的人数为：" + timeUser[3] + "\n" + "比例为：" + userPercentage[3] + "%\n\n" + "16~20时登陆的人数为：" + timeUser[4] + "\n" + "比例为：" + userPercentage[4] + "%\n\n" + "20~24时登陆的人数为：" + timeUser[5] + "\n" + "比例为：" + userPercentage[5] + "%");
		    context.write(outputKey, NullWritable.get());
		}
	}
	
	public static void main(String[] args) throws Exception {
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS","hdfs://master1:9000");
				
			Job eachTimeUserJob;
			eachTimeUserJob = Job.getInstance(conf, "EachTimeUser");
			eachTimeUserJob.setJarByClass(EachTimeUser.class);
				
			eachTimeUserJob.setMapperClass(EachTimeUserMapper.class);
			eachTimeUserJob.setReducerClass(EachTimeUserRedecer.class);
			
			eachTimeUserJob.setMapOutputKeyClass(Text.class);
			eachTimeUserJob.setMapOutputValueClass(Text.class);
			
			eachTimeUserJob.setOutputKeyClass(Text.class);
			eachTimeUserJob.setOutputValueClass(NullWritable.class);
			
			eachTimeUserJob.setInputFormatClass(KeyValueTextInputFormat.class);
			
			FileInputFormat.addInputPath(eachTimeUserJob, new Path("/game-2017-01-01-2017-01-07.log"));
			Path outputPath = new Path("/gameEachTimeUser");
			FileSystem.get(conf).delete(outputPath,true);
			FileOutputFormat.setOutputPath(eachTimeUserJob, outputPath);
			
			System.exit(eachTimeUserJob.waitForCompletion(true)?0:1);
	}
}
