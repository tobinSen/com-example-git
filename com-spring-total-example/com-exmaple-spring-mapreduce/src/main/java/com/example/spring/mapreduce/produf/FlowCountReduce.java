package com.example.spring.mapreduce.produf;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlowCountReduce extends Reducer<Text, FlowBeanWritable, Text, FlowBeanWritable> {

    FlowBeanWritable v = new FlowBeanWritable();

    @Override
    protected void reduce(Text key, Iterable<FlowBeanWritable> values, Context context) throws IOException, InterruptedException {
        long sum_upFlow = 0;
        long sum_downFlow = 0;

        // 1 遍历所用bean，将其中的上行流量，下行流量分别累加
        for (FlowBeanWritable flowBean : values) {
            sum_upFlow += flowBean.getUpFlow();
            sum_downFlow += flowBean.getDownFlow();
        }
        v.set(sum_upFlow, sum_downFlow);

        // 3 写出
        context.write(key, v);
    }
}
