package com.uplooking.httpasynclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;

public class HttpAsynClient {

    public static void main(String[] args) throws Exception {
        String url = "http://www.xxxxx.com";

        //1.IO响应式配置
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setIoThreadCount(Runtime.getRuntime().availableProcessors())
                .build();

        //2.请求配置
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(20000) // 从连接池获取连接的 timeout
                .setConnectTimeout(20000) // 发送http请求 连接目标服务器的timeout
                .setSocketTimeout(20000)  // 获取目标服务器数据的timeout
                .build();

        //3.连接响应式配置
        ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);

        //4.httpConnection管理者
        PoolingNHttpClientConnectionManager httpConnectionManager = new PoolingNHttpClientConnectionManager(ioReactor);

        httpConnectionManager.setMaxTotal(2000);
        httpConnectionManager.setDefaultMaxPerRoute(500);

        //5.产生异步的httpClient
        CloseableHttpAsyncClient httpClient = HttpAsyncClients.custom()
                .setConnectionManager(httpConnectionManager)
                .setDefaultRequestConfig(config).build();

        //6开启异步
        httpClient.start();
        HttpGet httpGet = new HttpGet(url);

        //7.发送请求，进行回调
        httpClient.execute(httpGet, new FutureCallback<HttpResponse>() {

            @Override
            public void completed(HttpResponse httpResponse) {
                System.out.println(httpResponse.getStatusLine().getStatusCode());
            }

            @Override
            public void failed(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void cancelled() {
                System.out.println("connection is closed");
            }
        });
        /**
         * 不要关闭 client，close方法会关闭PoolingNHttpClientConnectionManager,导致异步监听器还没
         * 收到响应就被关闭了，被这个坑了好久
         * 生产环境可以维护这个httpClient不要关闭
         */
    }
}
