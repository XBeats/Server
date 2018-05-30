package cn.sskbskdrin.server;

import cn.sskbskdrin.log.Logger;
import cn.sskbskdrin.server.ftp.FtpServer;
import cn.sskbskdrin.server.http.HttpServer;
import cn.sskbskdrin.server.socket.NettyServer;
import cn.sskbskdrin.server.utils.SysUtil;

/**
 * @author sskbskdrin
 * @date 2018/一月/27
 */

public class Main {
    private static final String TAG = "Main";
    private static final Object lock = new Object();

    public static void main(String[] args) {
        Logger.d(TAG, "running");
        if (args != null && args.length > 0) {
            for (String arg : args) {
                String[] name = arg.split(":");
                int port = 0;
                if (name.length > 1) {
                    port = SysUtil.parseInt(name[1]);
                }
                if ("http".equals(name[0])) {
                    if (port == 0) {
                        port = 8080;
                    }
                    HttpServer.getInstance().start(port);
                } else if ("ftp".equals(name[0])) {
                    if (port == 0) {
                        port = 2121;
                    }
                    FtpServer.getInstance().start(port);
                } else if ("socket".equals(name[0])) {
                    if (port == 0) {
                        port = 8088;
                    }
                    NettyServer.getInstance().start(port);
                } else {
                    Logger.e(TAG, "illegal params " + name[0]);
                }
            }
        } else {
            Logger.w(TAG, "Lack of parameters");
        }
        while (true) {
            synchronized (lock) {
                try {
                    lock.wait();
                    System.out.println("main: end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
