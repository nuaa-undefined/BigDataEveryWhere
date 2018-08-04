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
 * @author 
 * @Description: 输入用户名得用户信息（登录次数、登录时长、登录日期、次均登录时长）
 *
 */
public class UserTable {
	
	private static String user;
	public static class UserTableMapper extends Mapper<Text, Text, Text, Text> {		

		private Text outputValue = new Text();

		@Override
		protected void map(Text key, Text value, Context context)
				throws IOException, InterruptedException {

			String[] word = value.toString().split("\\s+");
			String date = word[2].substring(8, 10);
			outputValue.set(date + "\t" + word[4]);
			context.write(key,outputValue);
		}				
	}

	public static class UserTableReducer extends Reducer<Text, Text, Text, NullWritable> {

		private Text outputKey = new Text();

		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {

			int number = 0;
			long totalTime = 0;
			int[] ifDay = {0,0,0,0,0,0,0}; 

			if(user.equals(key.toString())){
				for (Text value : values) {
					number++;
					totalTime += Long.parseLong(value.toString().split("\\s+")[1]);
					String date = value.toString().split("\\s+")[0];
					for (int i = 1; i < 8; i++) {
						if(date.equals("0"+i)){
							ifDay[i-1] = i;
						}
					}
				}
				String date = " ";
				for (int i = 0; i < 7; i++) {
					if(ifDay[i] != 0){
						date = date + ifDay[i] + "号  ";
					}
				}
				String averageLoginTime= String.format("%.2f",(double)totalTime/number);
				outputKey.set("用户名:" + key + "\n" + "登录次数:" + number + "\n" + "在线总时长:" + totalTime + "\n" + "次均登录时长:" + averageLoginTime + "\n"+ "在线日期:" + date + "\n");
				context.write(outputKey, NullWritable.get());
			}
		}
	}	

	public static void main(String[] args) throws Exception {
		
		user = args[0];
		
		Configuration conf=new Configuration();
		conf.set("fs.defaultFS", "hdfs://master1:9000");

		Job userTableJob ;
		userTableJob=Job.getInstance(conf,"UserTable");
		userTableJob.setJarByClass(UserTable.class);

		userTableJob.setMapperClass(UserTableMapper.class);
		userTableJob.setReducerClass(UserTableReducer.class);

		userTableJob.setMapOutputKeyClass(Text.class);
		userTableJob.setMapOutputValueClass(Text.class);

		userTableJob.setOutputKeyClass(Text.class);
		userTableJob.setOutputValueClass(NullWritable.class);

		userTableJob.setInputFormatClass(KeyValueTextInputFormat.class);

		FileInputFormat.addInputPath(userTableJob, new Path("/game1.log"));
		Path outputpath = new Path("/UserTable");

		FileSystem.get(conf).delete(outputpath,true);
		FileOutputFormat.setOutputPath(userTableJob, outputpath);

		System.exit(userTableJob.waitForCompletion(true)?0:1);	
	}		

}
