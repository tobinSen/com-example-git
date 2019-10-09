package com.example.spring.mapreduce.produf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FlowCountDriver {

    public static void main(String[] args) throws Exception {
        Job job = Job.getInstance(new Configuration());
        job.setJarByClass(FlowCountDriver.class);

        job.setMapperClass(FlowCountMapper.class);
        job.setReducerClass(FlowCountReduce.class);

        job.setMapOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBeanWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBeanWritable.class);

        FileOutputFormat.setOutputPath(job, new Path(args[0]));
        FileInputFormat.setInputPaths(job, new Path(args[1]));

        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : -1);

    }
}
