// package test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server_420 {
    // private ObjectInputStream ois;
    // private static ServerSocket server;
    // private static Socket socket;

    static void getServer() throws IOException {
        // try {
        ServerSocket server = new ServerSocket(999);
        // server.bind("10.208.91.153:999");
        // System.out.println(server.getInetAddress());
        // System.out.println(server.getLocalPort());
        System.out.println("socket创建成功！");
        System.out.println("等待客户连接···");
        // ois = new ObjectInputStream(new
        // BufferedInputStream(socket.getInputStream()));

        // try {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
        // System.out.println("收到信息：");
        System.out.println("random1\trandom2\ttimer\t\tlocaltime");
        // for (int i = 0; i < 20; i++) {
        // Message m = new Message();

        // try {
        // while (true) {
        for (int i = 0; i < 20; i++) {
            Socket socket = server.accept();
            // System.out.println("连接中···");
            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str = null;
            str = bufferedReader1.readLine(); // 读取一行数据
            String[] newStr = str.split(" ");
            System.out.println(
                    newStr[0] + "\t" + newStr[1] + "\t" + newStr[2] + "\t" + simpleDateFormat.format(new Date()));
            if (newStr[1] == "0") {
                break;
            }
        }
        // }
        // } catch (IOException e) {
        // e.printStackTrace(); // 输出信息
        // }

        // m=(Message)ois.readObject();
        // if(m.getRandom1()==2) {
        // break;
        // }else
        // System.out.println(m.getRandom1() + "\t" + m.getRandom2() + "\t" +
        // m.getTimer() + "\t"
        // + simpleDateFormat.format(date));

        // }
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        // try {
        // ois.close();
        // System.out.println("输出流关闭");
        // socket.close();
        // System.out.println("socket关闭");
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
    }

    public static void main(String[] args) throws IOException {
        // System.setProperty("java.net.preferIPv4Stack", "true");
        getServer(); // 启动客户端
    }
}
