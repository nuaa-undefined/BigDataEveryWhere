package org.nuaa.undefined.BigDataEveryWhere.mr.game;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @author mll
 * Description: 计算每天的登录用户
 * （Mapper端以KeyValue形式输入，Mapper的输出为（用户的唯一标识+系统类型，具体的天）；
 *   用Hash表对用户七天内登录的日期做记录，如果该天此用户登录了则对应的日期的用户数+1）
 *
 */
public class DayLogin {
	public static class DayLoginMapper extends Mapper<Text, Text, Text, Text> {
		
		private Text outputvalue = new Text();
		
		@Override
		protected void map(Text key, Text value, Context context)
				throws IOException, InterruptedException {
			
			String[] word = value.toString().split("\\s+");
			outputvalue.set(word[2].substring(8, 10));
			context.write(key, outputvalue);					
		}		
	}

	public static class DayLoginReducer extends Reducer<Text, Text, Text, NullWritable> {
		
		private Text outputKey = new Text();
		private int[] day={0,0,0,0,0,0,0};
		
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
		     int[] sign={0,0,0,0,0,0,0};			
			for (Text value : values) {
				String data = value.toString();
				for (int i = 1; i < 8; i++) {
					if (data.equals("0"+i)) {
						sign[i-1]=1;
					}					
				}				
			}			
			for (int i = 0; i < 7; i++) {
				if(sign[i]==1)
				day[i]++;
			}		
		}
		
		@Override
		protected void cleanup(Context context)
				throws IOException, InterruptedException {
			outputKey.set("第一天的登录用户数：    "+day[0]+"\n"+"第二天的登录用户数：    "+day[1]+"\n"+"第三天的登录用户数：    "+day[2]+"\n"+"第四天的登录用户数：    "+day[3]+"\n"+"第五天的登录用户数：    "+day[4]+"\n"+"第六天的登录用户数：    "+day[5]+"\n"+"第七天的登录用户数：    "+day[6]+"\n");
			context.write(outputKey, NullWritable.get());
		}
    }
	
	public static void main(String[] args) throws Exception {
		
		Configuration conf=new Configuration();
		conf.set("fs.defaultFS", "hdfs://master1:9000");

		Job dayLoginJob;
		dayLoginJob=Job.getInstance(conf,"power1");
		dayLoginJob.setJarByClass(DayLogin.class);

		dayLoginJob.setMapperClass(DayLoginMapper.class);
		dayLoginJob.setReducerClass(DayLoginReducer.class);
		
		dayLoginJob.setMapOutputKeyClass(Text.class);
		dayLoginJob.setMapOutputValueClass(Text.class);

		dayLoginJob.setOutputKeyClass(Text.class);
		dayLoginJob.setOutputValueClass(NullWritable.class);

		dayLoginJob.setInputFormatClass(KeyValueTextInputFormat.class);

		FileInputFormat.addInputPath(dayLoginJob, new Path("/game1.log"));
		Path outputpath = new Path("/DayLogin");

		FileSystem.get(conf).delete(outputpath,true);
		FileOutputFormat.setOutputPath(dayLoginJob, outputpath);

		System.exit(dayLoginJob.waitForCompletion(true)?0:1);		
	}
}
