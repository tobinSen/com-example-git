package com.example.spring.mapreduce.produf;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlowCountMapper extends Mapper<IntWritable, Text, Text, FlowBeanWritable> {

    FlowBeanWritable v = new FlowBeanWritable();
    Text k = new Text();

    @Override
    protected void map(IntWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 1 获取一行
        String line = value.toString();
        // 2 切割字段
        String[] fields = line.split("\t");
        // 3 封装对象
        // 取出手机号码
        String phoneNum = fields[1];

        // 取出上行流量和下行流量
        long upFlow = Long.parseLong(fields[fields.length - 3]);
        long downFlow = Long.parseLong(fields[fields.length - 2]);

        k.set(phoneNum);
        v.setUpFlow(upFlow);
        v.setDownFlow(downFlow);

        // 4 写出
        context.write(k, v);

    }
}
