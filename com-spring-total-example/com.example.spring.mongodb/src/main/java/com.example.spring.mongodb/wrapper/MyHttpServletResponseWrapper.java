package com.example.spring.mongodb.wrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

public class MyHttpServletResponseWrapper extends HttpServletResponseWrapper {

    MyServletOutputStream myServletOutputStream;
    MyPrintWriter myPrintWriter;

    public MyHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        //这里存储
        return new MyServletOutputStream(super.getOutputStream());
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new MyPrintWriter(super.getWriter());
    }

    public class MyServletOutputStream extends ServletOutputStream {

        private OutputStream outputStream;


        public MyServletOutputStream(OutputStream outputStream) {
            this.outputStream = outputStream;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener listener) {

        }

        //所有write的基类
        @Override
        public void write(int b) throws IOException {
            outputStream.write(b);
        }
    }

    public class MyPrintWriter extends PrintWriter {

        private Writer writer;

        public MyPrintWriter(Writer out) {
            super(out);
            this.writer = out;
        }

        @Override
        public void write(char[] buf) {
            //writer.write(buf);
        }
    }
}
