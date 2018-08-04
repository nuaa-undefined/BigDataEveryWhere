package org.nuaa.undefined.BigDataEveryWhere.mr.game;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



/**
 * @author wyw
 * @Describtion 输入你想输出的top个数N，按（登录时长、登录次数、首登时间）为排序标准输出前topN（表格？）
 */
public class GetTop {
	
	private static int front;

	public static class GetTopMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {

			context.write(value, NullWritable.get());
		}
	}
	
	public static class GetTopRedecer extends Reducer<Text, NullWritable, Text, NullWritable> {
		
		private int num = 0;
		
		@Override
		protected void reduce(Text key, Iterable<NullWritable> values,
				Context context) throws IOException, InterruptedException {
			
			if(num++<front){
				context.write(key, NullWritable.get());
			}
		}
	}
	
	public static class GetTopSort extends WritableComparator {
		public  GetTopSort() {
			super(Text.class,true);
		}
		
		@Override
		public int compare(WritableComparable a, WritableComparable b) {

			String[] aWords = ((Text)a).toString().split("\\s+");
			String[] bWords = ((Text)b).toString().split("\\s+");
			
			// 累计时长
			Integer aTime = Integer.parseInt(aWords[1]);
			Integer bTime = Integer.parseInt(bWords[1]);
			
			// 累计次数
			Integer aCount = Integer.parseInt(aWords[2]);
			Integer bCount = Integer.parseInt(bWords[2]);
			
			return bTime.compareTo(aTime)==0?bCount.compareTo(aCount)==0?aWords[3].compareTo(bWords[3]):bCount.compareTo(aCount):bTime.compareTo(aTime);
			
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		front = Integer.parseInt(args[0]);
		
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://master1:9000");
		
		Job getTopJob;
		getTopJob=Job.getInstance(conf,"GetTop");
		getTopJob.setJarByClass(GetTop.class);
		
		getTopJob.setMapperClass(GetTopMapper.class);
		getTopJob.setReducerClass(GetTopRedecer.class);

		getTopJob.setOutputKeyClass(Text.class);
		getTopJob.setOutputValueClass(NullWritable.class);
		
		getTopJob.setSortComparatorClass(GetTopSort.class);
		getTopJob.setGroupingComparatorClass(GetTopSort.class);
		
		FileInputFormat.addInputPath(getTopJob, new Path("/gameSortInfo"));
		Path outputpath = new Path("/gameTop");
		
		FileSystem.get(conf).delete(outputpath,true);
        FileOutputFormat.setOutputPath(getTopJob, outputpath);
        System.exit(getTopJob.waitForCompletion(true)?0:1);
	}
}
