package org.nuaa.undefined.BigDataEveryWhere.mr.hero;

import java.io.IOException;

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
 *  Title:Info.java
 *  Description:
 *  将源数据规整，增加一列分路位置，1上单，2中单，3打野，4ADC，5辅助
 *  "Fiddlesticks,0"	#	98
 *  变为 Fiddlesticks		0	98	4
 *  //输出 英雄名	胜负	用户id	分路位置
 *  输入文件：/gamelog_heros/heros.txt
 *  输出文件：/gamelog_heros/Info
 *  
 *  @author Growarm
 *  @date 下午5:42:20
 *  version 1.0
 */
public class Info {
	public static class InfoMapper extends Mapper<LongWritable,Text, LongWritable,Text>{
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
		
				context.write(key,value);

		}		
	}
	
	private static class InfoReducer extends Reducer<LongWritable,Text, Text, NullWritable>{
		
		private Text outputKey = new Text();
		private int position;
		
		//"Fiddlesticks,0"	#	98
		@Override
		protected void reduce(LongWritable key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
				//1上单，2中单，3打野，4ADC，5辅助
		      double randomNumber;  
		      randomNumber = Math.random();  
		      if (randomNumber >= 0 && randomNumber <= 0.25){  
		    	  position = 1;  
		      }else if (randomNumber >= 0.25  && randomNumber <= 0.4){  
		    	  position = 2;  
		      }else if (randomNumber >= 0.4  && randomNumber <= 0.625) {  
		    	  position = 3;  
		      }else if (randomNumber >= 0.625  && randomNumber <= 0.875) {  
		    	  position = 4;  
		      }else if (randomNumber >= 0.875  && randomNumber <= 1){  
		    	  position = 5;  
		      }		

			for (Text value : values) {
				String[] words = value.toString().split("\\s+");
				String[] word = words[0].split(",");
				String aword = word[0].substring(1);
				String bword = word[1].substring(0, 1);

				outputKey.set(aword +"\t"+ bword + "\t" + words[2] + "\t" + position);		
				context.write(outputKey, NullWritable.get());
				
			}
		}
	}

	public static void main(String[] args) {
	
		try {
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://master:9000");

			Job InfoJob;
			InfoJob=Job.getInstance(conf, "Info");
			InfoJob.setJarByClass(Info.class);

			InfoJob.setMapperClass(InfoMapper.class);
			InfoJob.setReducerClass(InfoReducer.class);

			InfoJob.setMapOutputKeyClass(LongWritable.class);
			InfoJob.setMapOutputValueClass(Text.class);

			InfoJob.setOutputKeyClass(Text.class);
			InfoJob.setOutputValueClass(NullWritable.class);
			
			InfoJob.setInputFormatClass(TextInputFormat.class);	
			
			FileInputFormat.addInputPath(InfoJob, new Path("/gamelog_heros/heros.txt"));
			Path outputPath = new Path("/gamelog_heros/Info2");

			FileSystem.get(conf).delete(outputPath,true);
			FileOutputFormat.setOutputPath(InfoJob, outputPath);

			System.exit(InfoJob.waitForCompletion(true)?0:1);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
