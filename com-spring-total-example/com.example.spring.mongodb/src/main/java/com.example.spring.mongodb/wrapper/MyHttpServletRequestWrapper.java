package com.example.spring.mongodb.wrapper;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;

public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final String body;

    public MyHttpServletRequestWrapper(HttpServletRequest request) throws Exception {
        super(request);
        BufferedReader reader = request.getReader();
        ServletInputStream inputStream = request.getInputStream();
        body = pareBody(request.getInputStream());
    }

    //1.存servlet流
    private String pareBody(InputStream in) throws Exception {
        String line;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")))) {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        //可以在这里进行解析，进行懒加载的方式
        return new MyServletInputStream(new ByteArrayInputStream(body.getBytes(Charset.defaultCharset())));
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), Charset.defaultCharset()));
    }

    private class MyServletInputStream extends ServletInputStream {
        private InputStream inputStream;

        public MyServletInputStream(InputStream body) {
            this.inputStream = body;
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener listener) {

        }

        //这是所有read的基类
        @Override
        public int read() throws IOException {
            return inputStream.read();
        }
    }
}
