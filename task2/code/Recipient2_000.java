package test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Recipient2_000 {
    static void getServer() throws IOException {
        ServerSocket server = new ServerSocket(999);
        System.out.println("socket创建成功！");
        System.out.println("等待客户连接···");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");

        System.out.println("random1\trandom2\ttimer\t\tlocaltime");

        int counter0=0,counter1=0;
        while(true) {
            Socket socket = server.accept();
            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str = null;
            str = bufferedReader1.readLine(); // 读取一行数据
            String[] newStr = str.split(" ");
            if(newStr[0]=="1") {
            	counter1++;
            }else if(newStr[0]=="0"){
            	counter0++;
            }
            if(counter1==5) {
                break;
            }
            if(counter0==15) {
                break;
            }
            if(newStr[1]=="0") {
                break;
            }
            System.out.println(newStr[0]+"\t"+newStr[1]+"\t"+newStr[2]+"\t"+simpleDateFormat.format(new Date()));
        }
    }

    public static void main(String[] args) throws IOException {
        getServer(); // 启动客户端
    }
}
