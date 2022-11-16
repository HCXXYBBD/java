// import java.io.*;
// import java.net.ServerSocket;
// import java.net.Socket;
// import java.text.SimpleDateFormat;
// import java.util.Date;
// import java.util.Random;
// import java.util.Scanner;

// public class P2P_3 {
//     private static int count_0;
//     private static int count_1;
//     private static int threshold;
//     private static String ip_1;
//     private static String ip_2;
//     private static Scanner input = new Scanner(System.in);
//     private static Random r;
//     private static String me;

//     public static void menu() {
//         System.out.print("请输入随机数种子：");
//         long s = input.nextLong();
//         r = new Random(s);

//         System.out.print("请输入本机编号：");
//         me = input.next();

//         System.out.print("请输入第一个接收者IP:>");
//         ip_1 = input.next();

//         System.out.print("请输入第二个接收者IP:>");
//         ip_2 = input.next();
//     }

//     public static void receive() throws IOException {
//         ServerSocket server = new ServerSocket(1111);
//         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
//         int M = 0;
//         for (int i = 0; i < 10; i++) {
//             Socket socket = server.accept();
//             BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//             String str = null;
//             str = bufferedReader1.readLine(); // 读取一行数据
//             M = M + Integer.parseInt(str);
//             System.out.println("1\t" + server.getInetAddress() + "\t" + str + "\t" + M + "\t"
//                     + simpleDateFormat.format(new Date()));
//         }
//     }

//     public static void send() throws InterruptedException {
//         Message m = new Message();// 初始化message
//         m.setCode(0);// 发送端
//         int a = 0;// 记录发送给第一个对端的次数
//         int b = 0;// 记录发送给第二个对端的次数
//         int c = 0;// 记录发送给第几个对端
//         int sender = 0;// 映射本体ID
//         int receiver = 0;// 映射对端ID
//         int M = 100;// 资源数
//         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");// 设置日期格式

//         if (me.equals("i")) {
//             sender = 1;
//         } else if (me.equals("j")) {
//             sender = 2;
//         } else if (me.equals("k")) {
//             sender = 3;
//         } // 确定本体ID

//         for (int i = 0; i < 10; i++) {
//             try {
//                 double rand = r.nextDouble();
//                 long interval = (long) (Math.log1p(rand - 1) * 2000 * (-6));
//                 Thread.sleep(interval);
//             } catch (InterruptedException e) {
//                 e.printStackTrace();
//             } // 发送时间间隔

//             double r1 = r.nextDouble();
//             if (a < 5 || b < 5) {
//                 if (r1 >= 0.5 && r1 < 1 && a < 5) {
//                     c = 0;
//                     a++;
//                 }
//                 if (r1 >= 0 && r1 < 0.5 && b < 5) {
//                     c = 1;
//                     b++;
//                 }
//             } // 随机决定发送给哪个对端

//             if (sender == 1 && c == 0) {
//                 receiver = 2;
//             } else if (sender == 1 && c == 1) {
//                 receiver = 3;
//             } else if (sender == 2 && c == 0) {
//                 receiver = 1;
//             } else if (sender == 2 && c == 1) {
//                 receiver = 3;
//             } else if (sender == 3 && c == 0) {
//                 receiver = 1;
//             } else if (sender == 3 && c == 1) {
//                 receiver = 2;
//             } // 确定发送的对端的ID

//             if (receiver == 1) {
//                 m.setName("i");
//             } else if (receiver == 2) {
//                 m.setName("j");
//             } else if (receiver == 3) {
//                 m.setName("k");
//             } // 将发送对端的ID写入message

//             int N = (int) Math.ceil(M / 4);
//             m.setTrans(N);// 确定本次转移资源数量

//             int D = M;
//             M = D - N;
//             m.setTotal(M);// 确定剩余资源数量

//             m.setTimer(simpleDateFormat.format(new Date()));// 将发送时间写入message

//             try {
//                 Thread.sleep(500);
//             } catch (InterruptedException e) {
//                 e.printStackTrace();
//             } // 通道延迟

//             if (c == 0) {
//                 try {
//                     Socket socket1 = new Socket(ip_1, 1111);

//                     BufferedWriter bufferedWriter1 = new BufferedWriter(
//                             new OutputStreamWriter(socket1.getOutputStream()));

//                     bufferedWriter1.write(Integer.toString(m.getTrans()) + "\n");
//                     bufferedWriter1.flush();

//                 } catch (IOException e) {
//                     e.printStackTrace();
//                 }
//             } else if (c == 1) {
//                 try {
//                     Socket socket2 = new Socket(ip_2, 1111);

//                     BufferedWriter bufferedWriter2 = new BufferedWriter(
//                             new OutputStreamWriter(socket2.getOutputStream()));

//                     bufferedWriter2.write(Integer.toString(m.getTrans()) + "\n");
//                     bufferedWriter2.flush();

//                 } catch (IOException e) {
//                     e.printStackTrace();
//                 }
//             } // 向指定对端发送N

//             System.out.println("0\t" + m.getName() + "\t" + m.getTrans() + "\t" + m.getTotal() + "\t"
//                     + m.getTimer());
//         }
//     }

//     public static class MyThread_R extends Thread {
//         private String name;

//         public MyThread_R(String name) {
//             this.name = name;
//         }

//         @Override
//         public void run() {
//             try {
//                 receive();
//             } catch (IOException e) {
//                 e.printStackTrace();
//             }
//         }
//     }

//     public static class MyThread_S extends Thread {
//         private String name;

//         public MyThread_S(String name) {
//             this.name = name;
//         }

//         @Override
//         public void run() {
//             try {
//                 send();
//             } catch (InterruptedException e) {
//                 e.printStackTrace();
//             }
//         }
//     }

//     public static void main(String[] args) throws Exception {
//         menu();

//         MyThread_R T1 = new MyThread_R("R");
//         MyThread_S T2 = new MyThread_S("S");

//         System.out.println("参数输入结束，启动receive");
//         T1.start();
//         System.out.println("receive启动完毕");
//         System.out.print("输入y启动send:");

//         while (true) {
//             char s = input.next().charAt(0);
//             if (s == 'y') {
//                 T2.start();
//                 break;
//             } else {
//                 System.out.print("输入错误，请重新输入：");
//             }
//         }
//         System.out.println("code\tname\t\ttrans\ttotal\ttimer");
//         T2.join();
//         System.out.println("发送进程结束！");

//         T1.join();
//         System.out.println("接收进程结束！");

//         input.close();
//     }
// }

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class P2P_3 {
    private static int count_0;
    private static int count_1;
    private static int threshold;
    private static String ip_1;
    private static String ip_2;
    private static Scanner input = new Scanner(System.in);
    private static Random r;
    private static String me;
    private static int M = 100;

    public static void menu() {
        System.out.print("请输入随机数种子：");
        long s = input.nextLong();
        r = new Random(s);

        System.out.print("请输入本机编号：");
        me = input.next();

        System.out.print("请输入第一个接收者IP:>");
        ip_1 = input.next();

        System.out.print("请输入第二个接收者IP:>");
        ip_2 = input.next();
    }

    public static void receive() throws IOException {
        ServerSocket server = new ServerSocket(1111);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
        int i = 10;
        while (i != 0) {
            Socket socket = server.accept();

            String remoteIp = socket.getInetAddress().getHostAddress();

            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str = null;
            str = bufferedReader1.readLine(); // 读取一行数据
            synchronized ("") {
                M = M + Integer.parseInt(str);

                String sender = 0;// 映射对端

                if (me.equals("i") && remoteIp.equals(ip_1)) {
                    sender = "j";
                } else if (me.equals("i") && remoteIp.equals(ip_2)) {
                    sender = "k";
                } else if (me.equals("j") && remoteIp.equals(ip_1)) {
                    sender = "i";
                } else if (me.equals("j") && remoteIp.equals(ip_2)) {
                    sender = "k";
                } else if (me.equals("k") && remoteIp.equals(ip_1)) {
                    sender = "i";
                } else if (me.equals("k") && remoteIp.equals(ip_2)) {
                    sender = "j";
                } // 确定发送的对端的ID

                System.out.println("1\t" + sender + "\t" + str + "\t" + M + "\t"
                        + simpleDateFormat.format(new Date()));
            }
            i--;
        }
        server.close();
    }

    public static void send() throws InterruptedException {
        Message m = new Message();// 初始化message
        m.setCode(0);// 发送端
        int a = 0;// 记录发送给第一个对端的次数
        int b = 0;// 记录发送给第二个对端的次数
        int c = 0;// 记录发送给第几个对端
        int sender = 0;// 映射本体ID
        int receiver = 0;// 映射对端ID
        // int M = 100;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");// 设置日期格式

        if (me.equals("i")) {
            sender = 1;
        } else if (me.equals("j")) {
            sender = 2;
        } else if (me.equals("k")) {
            sender = 3;
        } // 确定本体ID

        for (int i = 0; i < 10; i++) {
            try {
                double rand = r.nextDouble();
                long interval = (long) (Math.log1p(rand - 1) * 2000 * (-6));
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } // 发送时间间隔

            double r1 = r.nextDouble();
            if (a < 5 || b < 5) {
                if (r1 >= 0.5 && r1 < 1 && a < 5) {
                    c = 0;
                    a++;
                }
                if (r1 >= 0 && r1 < 0.5 && b < 5) {
                    c = 1;
                    b++;
                }
            } // 随机决定发送给哪个对端

            if (sender == 1 && c == 0) {
                receiver = 2;
            } else if (sender == 1 && c == 1) {
                receiver = 3;
            } else if (sender == 2 && c == 0) {
                receiver = 1;
            } else if (sender == 2 && c == 1) {
                receiver = 3;
            } else if (sender == 3 && c == 0) {
                receiver = 1;
            } else if (sender == 3 && c == 1) {
                receiver = 2;
            } // 确定发送的对端的ID

            if (receiver == 1) {
                m.setName("i");
            } else if (receiver == 2) {
                m.setName("j");
            } else if (receiver == 3) {
                m.setName("k");
            } // 将发送对端的ID写入message

            int N = (int) Math.ceil(M / 4);
            m.setTrans(N);// 确定本次转移资源数量

            int D = M;
            M = D - N;
            m.setTotal(M);// 确定剩余资源数量

            m.setTimer(simpleDateFormat.format(new Date()));// 将发送时间写入message

            // System.out.println("code\tname\t\ttrans\ttotal\ttimer");// 显示五元组

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } // 通道延迟

            if (c == 0) {
                try {
                    Socket socket1 = new Socket(ip_1, 1111);

                    BufferedWriter bufferedWriter1 = new BufferedWriter(
                            new OutputStreamWriter(socket1.getOutputStream()));

                    bufferedWriter1.write(Integer.toString(m.getTrans()) + "\n");
                    bufferedWriter1.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (c == 1) {
                try {
                    Socket socket2 = new Socket(ip_2, 1111);

                    BufferedWriter bufferedWriter2 = new BufferedWriter(
                            new OutputStreamWriter(socket2.getOutputStream()));

                    bufferedWriter2.write(Integer.toString(m.getTrans()) + "\n");
                    bufferedWriter2.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } // 向指定对端发送N

            System.out.println("0\t" + m.getName() + "\t" + m.getTrans() + "\t" + m.getTotal() + "\t"
                    + m.getTimer());
        }
    }

    public static class MyThread_R extends Thread {
        private String name;

        public MyThread_R(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                receive();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class MyThread_S extends Thread {
        private String name;

        public MyThread_S(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                send();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        menu();

        MyThread_R T1 = new MyThread_R("R");
        MyThread_S T2 = new MyThread_S("S");

        System.out.println("参数输入结束，启动receive");
        T1.start();
        System.out.println("receive启动完毕");
        System.out.print("输入y启动send:");

        while (true) {
            char s = input.next().charAt(0);
            if (s == 'y') {
                T2.start();
                break;
            } else {
                System.out.print("输入错误，请重新输入：");
            }
        }
        System.out.println("code\tname\ttrans\ttotal\ttimer");
        T2.join();
        System.out.println("发送进程结束！");
        T1.join();
        System.out.println("接收进程结束！");

        input.close();
    }
}
