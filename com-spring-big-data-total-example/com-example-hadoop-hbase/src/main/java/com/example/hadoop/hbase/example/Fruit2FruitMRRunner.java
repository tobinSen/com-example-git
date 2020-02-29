package com.example.hadoop.hbase.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;

public class Fruit2FruitMRRunner extends Configured implements Tool {

    @Override
    public int run(String[] strings) throws Exception {
        //1.获取到configuration
        Configuration conf = this.getConf();
        //2.创建job任务
        Job job = Job.getInstance(conf, this.getClass().getSimpleName());
        job.setJarByClass(Fruit2FruitMRRunner.class);

        //3.配置job
        Scan scan = new Scan();
        scan.setCacheBlocks(false);
        scan.setCaching(500);

        //设置Mapper,注意导入的是mapReduce包下的
        TableMapReduceUtil.initTableMapperJob(
                "fruit",
                scan,
                ReadFruitMapper.class,
                ImmutableBytesWritable.class,
                Put.class,
                job);

        //设置Reducer
        TableMapReduceUtil.initTableReducerJob("fruit_mr", WriteFruitMRReducer.class, job);

        job.setNumReduceTasks(1);

        boolean isSuccess = job.waitForCompletion(true);

        return isSuccess ? 0 : 1;
    }


//    public statics void main(String[] args) {
//
//        String str1 = "aaa";
//        String str2 = "bbb";
//        //compareAndSet中Str1 与str1进行比较和stamp
//        AtomicStampedReference<String> reference = new AtomicStampedReference<>(str1, 1);
//        reference.compareAndSet(str1, str2, reference.getStamp(), reference.getStamp() + 1);
//        System.out.println("reference.getReference() = " + reference.getReference());
//
//        //当前值与预期值相同时候，更新当前值的时间戳
//        boolean b = reference.attemptStamp(str2, reference.getStamp() + 1);
//        System.out.println("b: " + b);
//        System.out.println("reference.getStamp() = " + reference.getStamp());
//
//        boolean c = reference.weakCompareAndSet(str2, "ccc", 3, reference.getStamp() + 1);
//        System.out.println("reference.getReference() = " + reference.getReference());
//        System.out.println("c = " + c);
//    }

}
