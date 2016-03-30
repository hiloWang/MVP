package com.user.hilo.others;

import com.user.hilo.utils.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/3/28.
 */
public class TestMulThreadDownload {

    public static final String SERVER_URL = "https://192.168.1.1:8080/test.txt";
    public static int threadCount = 3;

    public static void mulThreadDownloadMethod(String fileName) throws IOException {
        HttpURLConnection conn = TestMulThreadDownload.connectionServer();

        if (conn != null) {
            conn.setRequestMethod("GET");
//            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                int serverFileLength = conn.getContentLength();
                RandomAccessFile raf = new RandomAccessFile(fileName, "rwd");
                raf.setLength(serverFileLength);
                raf.close();

                int blockSize = serverFileLength / threadCount;

                for (int threadId = 1; threadId <= threadCount; ++threadId) {
                    int startIndex = blockSize * (threadId - 1);
                    int endIndex = blockSize * threadId - 1;
                    if (startIndex == endIndex) {
                        endIndex = serverFileLength;
                    }
                    new MulThreadDownLoad(threadId, startIndex, endIndex, fileName).run();
                }
            }
//            conn.disconnect();
        }
    }

    public static HttpURLConnection connectionServer() throws IOException {
        URL url = new URL(SERVER_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        return conn;
    }

    private static class MulThreadDownLoad implements Runnable {

        private int threadId, startIndex, endIndex;
        private String fileName;

        public MulThreadDownLoad(int threadId, int startIndex, int endIndex, String fileName) {
            this.threadId = threadId;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.fileName = fileName;
        }


        @Override
        public void run() {
            try {
                HttpURLConnection conn = TestMulThreadDownload.connectionServer();
                if (conn != null) {
                    conn.setRequestMethod("GET");
                    // 请求服务器下载部分的文件的指定的位置：
                    conn.setRequestProperty("Range", "bytes="+startIndex+"-"+endIndex);
                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // 返回服务端资源
                        InputStream is = conn.getInputStream();
                        RandomAccessFile raf = new RandomAccessFile(fileName, "rwd");

                        int len;
                        byte[] buffer = new byte[2 * 1024];// 2 kb

                        raf.seek(startIndex);

                        if ((len = is.read(buffer))!= -1) {
                            raf.write(buffer, 0, len);
                        }
                        is.close();
                        raf.close();
                        LogUtils.I("线程 " + threadId + " 下载完毕");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
