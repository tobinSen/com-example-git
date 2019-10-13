package com.example.hadoop.inputformat;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * the data into key/value pairs for input to the
 * 将文件等数据转换成key value的形式进行输入到inputFormat中
 * <p>
 * 一个recordReader读取一个文件，读取一个kv值
 */
public class WholeFileRecordReader extends RecordReader<Text, BytesWritable> {

    private boolean noRead = true;
    private Text key = new Text();
    private BytesWritable value = new BytesWritable();
    private FSDataInputStream inputStream;
    private FileSplit fs;

    //框架初始化的时候会进行调用
    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        fs = (FileSplit) split;
        Path path = fs.getPath();
        FileSystem fileSystem = path.getFileSystem(context.getConfiguration());
        inputStream = fileSystem.open(path);
    }

    //尝试读取下一个数据key value 如果读取到返回true 否则返回false
    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if (noRead) {
            noRead = false;
            key.set(fs.getPath().toString());
            byte[] buf = new byte[(int) fs.getLength()];
            inputStream.read(buf);
            value.set(buf, 0, buf.length);
            return true;
        } else {
            return false;
        }
    }

    //获取当前读到的key
    @Override
    public Text getCurrentKey() throws IOException, InterruptedException {
        return key;
    }

    //获取当前读到的value
    @Override
    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return value;
    }

    //获取读取的进度
    @Override
    public float getProgress() throws IOException, InterruptedException {
        return noRead ? 0 : 1;
    }

    //关闭资源
    @Override
    public void close() throws IOException {
        if (null != inputStream) {
            IOUtils.closeStream(inputStream);
        }
    }
}
