package org.nuaa.undefined.BigDataEveryWhere.mr.game;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
 * @author mll
 * description:  ios与android活跃用户统计（登录超过两天的用户）
 * （Mapper端以KeyValue形式输入，Mapper的输出为（用户的唯一标识+系统类型，具体的天）；
 *   用Hash表对用户七天内登录的日期做记录，当该用户登录超过两天时，该用户的系统类型的活跃人数+1）
 * 
 *
 */
public class ActiveUser {

	public static class ActiveUserMapper extends Mapper<Text, Text, Text, Text> {
		
		private Text outputValue=new Text();
		private Text outputKey=new Text();
		
		@Override
		protected void map(Text key, Text value, Context context)
				throws IOException, InterruptedException {
			
			String[] word = value.toString().split("\\s+");
			outputValue.set(word[2].substring(8, 10));
			outputKey.set(key+"\t"+word[0]);
			context.write(outputKey,outputValue);
		}				
	}
	
	public static class ActiveUserReducer extends Reducer<Text, Text, Text, NullWritable> {
		
		private Text outputKey = new Text();
		private int totalUser=0;
		private int iosUser=0;
		private int androidUser=0;
		
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			
			totalUser++;
			
			Set<String> set=new HashSet<String>();
			String systemType=key.toString().split("\\s+")[1];
			
			for (Text value : values) {				        
				String word = value.toString();	
				set.add(word);	
			}
			if((set.size()>2 )&& "iOS".equals(systemType)){
				iosUser++;
			}
			if ((set.size()>2) && "Android".equals(systemType)) {
				androidUser++;
			}	
		}
		
		@Override
		protected void cleanup(Context context)
				throws IOException, InterruptedException {
			String androidRate= String.format("%.2f",androidUser/(double)totalUser*100);
			String iosRate= String.format("%.2f",iosUser/(double)totalUser*100);
			outputKey.set("Android的活跃人数为： "+androidUser+"\n"+"Android的活跃人数比例为：  "+androidRate+"%\n"+"iOS的活跃人数为：  "+iosUser+"\n"+"iOS的活跃人数比例为： "+iosRate+"%\t");
		    context.write(outputKey, NullWritable.get());
		}
	}	
	
	public static void main(String[] args) throws Exception {
		Configuration conf=new Configuration();
		conf.set("fs.defaultFS", "hdfs://master1:9000");

		Job activeUserJob ;
		activeUserJob=Job.getInstance(conf,"ActiveUser");
		activeUserJob.setJarByClass(ActiveUser.class);

		activeUserJob.setMapperClass(ActiveUserMapper.class);
		activeUserJob.setReducerClass(ActiveUserReducer.class);
		
		activeUserJob.setMapOutputKeyClass(Text.class);
		activeUserJob.setMapOutputValueClass(Text.class);
		
		activeUserJob.setOutputKeyClass(Text.class);
		activeUserJob.setOutputValueClass(NullWritable.class);

		activeUserJob.setInputFormatClass(KeyValueTextInputFormat.class);

		FileInputFormat.addInputPath(activeUserJob, new Path("/game1.log"));
		Path outputpath = new Path("/ActiveUser");

		FileSystem.get(conf).delete(outputpath,true);
		FileOutputFormat.setOutputPath(activeUserJob, outputpath);

		System.exit(activeUserJob.waitForCompletion(true)?0:1);	
	}			
}
