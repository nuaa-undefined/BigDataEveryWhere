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
 * @Description 计算操作系统的分布(饼状图）
 *
 */
public class SystemDistribution {

	public static class SystemDistributionMapper extends Mapper<Text, Text, Text, NullWritable>{
		
		Text outputKey = new Text();
		
		@Override
		protected void map(Text key, Text value, Context context)
				throws IOException, InterruptedException {

			String system = value.toString().split("\\s+")[0];
			outputKey.set(key + "\t" + system);
		    context.write(outputKey, NullWritable.get());
			
		}
	}
	
	public static class  SystemDistributionRedecer extends Reducer<Text, NullWritable, Text, NullWritable>{
		
		Text outputKey = new Text();
		private int iosUser = 0;
		private int androidUser = 0;
		
		@Override
		protected void reduce(Text key, Iterable<NullWritable> value,
				Context context) throws IOException, InterruptedException {

			String system = key.toString().split("\\s+")[1];
			if("iOS".equals(system)){
				iosUser++;
			}else if ("Android".equals(system)) {
				androidUser++;
			}
		}
		@Override
		protected void cleanup(Context context)
				throws IOException, InterruptedException {
			
			int sum = iosUser + androidUser;
			String androidPercentage = String.format("%.2f", androidUser/(double)sum*100);
			String isoPercentage = String.format("%.2f", iosUser/(double)sum*100);
			outputKey.set("iSO系统使用人数：" + iosUser + "\n" + "Android系统使用人数：" + androidUser + "\n" + "iSO系统使用比例" + isoPercentage + "%\n" + "Android系统使用人数" + androidPercentage + "%");
			context.write(outputKey, NullWritable.get());
		}
	}
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS","hdfs://master1:9000");
			
		Job systemDistributionJob;
		systemDistributionJob = Job.getInstance(conf, "SystemDistribution");
		systemDistributionJob.setJarByClass(SystemDistribution.class);
			
		systemDistributionJob.setMapperClass(SystemDistributionMapper.class);
		systemDistributionJob.setReducerClass(SystemDistributionRedecer.class);
		
		systemDistributionJob.setOutputKeyClass(Text.class);
		systemDistributionJob.setOutputValueClass(NullWritable.class);
		
		systemDistributionJob.setInputFormatClass(KeyValueTextInputFormat.class);
		
		FileInputFormat.addInputPath(systemDistributionJob, new Path("/game-2017-01-01-2017-01-07.log"));
		Path outputPath = new Path("/gameSystemDistribution");
		FileSystem.get(conf).delete(outputPath,true);
		FileOutputFormat.setOutputPath(systemDistributionJob, outputPath);
		
		System.exit(systemDistributionJob.waitForCompletion(true)?0:1);
	}
	
}
