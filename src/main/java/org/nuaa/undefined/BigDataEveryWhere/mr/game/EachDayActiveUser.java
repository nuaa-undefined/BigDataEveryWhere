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
 * description: 每天登陆次数超过3次的活跃用户 
*/

public class EachDayActiveUser {

		public static class EachDayActiveUserMapper extends Mapper<Text, Text, Text, Text> {
			
			private Text outputValue=new Text();
			
			@Override
			protected void map(Text key, Text value, Context context)
					throws IOException, InterruptedException {
				
				String[] word = value.toString().split("\\s+");
				outputValue.set(word[2].substring(8, 10));
				context.write(key,outputValue);
			}				
		}
		
		public static class EachDayActiveUserReducer extends Reducer<Text, Text, Text, NullWritable> {
			
			private Text outputKey = new Text();
			private int[] activeUser = {0,0,0,0,0,0,0};
			
			@Override
			protected void reduce(Text key, Iterable<Text> values, Context context)
					throws IOException, InterruptedException {
				int[] num = {0,0,0,0,0,0,0};
				for (Text value : values) {
					String value1 = value.toString();
					for (int i = 1; i < 8; i++) {
						if(value1.equals("0" + i)){
							num[i-1]++;
						}
					}
				}
				for (int i = 0; i < 7; i++) {
					if(num[i] > 3){
						activeUser[i]++;
					}
				}
			}
				
			@Override
			protected void cleanup(Context context)
					throws IOException, InterruptedException {
				outputKey.set("第一天" + activeUser[0] + "\n" + "第二天" + activeUser[1] + "\n" + "第三天" + activeUser[2] + "\n" + "第四天" + activeUser[3] + "\n" + "第五天" + activeUser[4] + "\n" + "第六天" + activeUser[5] + "\n" + "第七天" + activeUser[6]);
			    context.write(outputKey, NullWritable.get());
			}
		}	
		
		public static void main(String[] args) throws Exception {
			Configuration conf=new Configuration();
			conf.set("fs.defaultFS", "hdfs://master1:9000");

			Job activeUserJob ;
			activeUserJob=Job.getInstance(conf,"EachDayActiveUser");
			activeUserJob.setJarByClass(EachDayActiveUser.class);

			activeUserJob.setMapperClass(EachDayActiveUserMapper.class);
			activeUserJob.setReducerClass(EachDayActiveUserReducer.class);
			
			activeUserJob.setMapOutputKeyClass(Text.class);
			activeUserJob.setMapOutputValueClass(Text.class);
			
			activeUserJob.setOutputKeyClass(Text.class);
			activeUserJob.setOutputValueClass(NullWritable.class);

			activeUserJob.setInputFormatClass(KeyValueTextInputFormat.class);

			FileInputFormat.addInputPath(activeUserJob, new Path("/game-2017-01-01-2017-01-07.log"));
			Path outputpath = new Path("/gameEachDayActiveUser");

			FileSystem.get(conf).delete(outputpath,true);
			FileOutputFormat.setOutputPath(activeUserJob, outputpath);

			System.exit(activeUserJob.waitForCompletion(true)?0:1);	
		}			
	}

