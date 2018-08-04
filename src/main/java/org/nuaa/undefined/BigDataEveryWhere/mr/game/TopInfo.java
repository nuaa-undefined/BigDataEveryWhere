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
 * @description 对数据进行初步处理，得到求topN需要的数据
 */
public class TopInfo {

public static class TopInfoMapper extends Mapper<Text, Text, Text, Text> {
		
		@Override
		protected void map(Text key, Text value, Context context)
				throws IOException, InterruptedException {

			context.write(key, value);
		}
	}
	
	public static class top20InfoRedece extends Reducer<Text, Text, Text, NullWritable> {
		
		private Text outputKey = new Text();
		
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {

			int playTime = 0;
			int playCount = 0;
			String firstTime = null;
			
			for (Text value : values) {
				String[] words = value.toString().split("\\s+");
				playCount++;
				playTime += Long.parseLong(words[4]);
				
				if(playCount == 1){
					firstTime = words[2];
				}
			}
			outputKey.set(key + "\t" + playTime + "\t" +playCount + "\t" + firstTime);
			context.write(outputKey, NullWritable.get());
		}
	}
	
	public static void main(String[] args) throws Exception, IOException {
		
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://master1:9000");
		
		Job topInfoJob;
		topInfoJob=Job.getInstance(conf,"TopInfo");
		topInfoJob.setJarByClass(TopInfo.class);
		
		topInfoJob.setMapperClass(TopInfoMapper.class);
		topInfoJob.setReducerClass(top20InfoRedece.class);
		
		topInfoJob.setMapOutputKeyClass(Text.class);
		topInfoJob.setMapOutputValueClass(Text.class);

		topInfoJob.setOutputKeyClass(Text.class);
		topInfoJob.setOutputValueClass(NullWritable.class);
		
		topInfoJob.setInputFormatClass(KeyValueTextInputFormat.class);
		
		FileInputFormat.addInputPath(topInfoJob, new Path("/gamePartition"));
		Path outputpath = new Path("/gameSortInfo");
		FileSystem.get(conf).delete(outputpath,true);
        FileOutputFormat.setOutputPath(topInfoJob, outputpath);
        
        System.exit(topInfoJob.waitForCompletion(true)?0:1);
	}
}
