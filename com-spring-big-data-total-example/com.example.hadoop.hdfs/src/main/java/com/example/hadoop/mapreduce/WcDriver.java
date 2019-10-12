package com.example.hadoop.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WcDriver {

    public static void main(String[] args) throws Exception {
        //1.获取Job实例
        Job job = Job.getInstance(new Configuration());

        //2.设置jar的类路径
        job.setJarByClass(WcDriver.class);

        //3.设置mapper和reduce
        job.setMapperClass(WcMapper.class);
        job.setReducerClass(WcReduce.class);

        //4.设置mapper和reduce输出类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //5.设置输入输出数据
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //提交job
        boolean wait = job.waitForCompletion(true);
        System.exit(wait ? 0 : 1);

    }

}

