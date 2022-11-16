import java.io.*;
import java.util.Scanner;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.text.SimpleDateFormat;

public class C_L {
    private static String name = null;// 结点名
    private static String ip_1 = null;
    private static String ip_2 = null;
    private static Scanner input = new Scanner(System.in);
    private static int port_p = 1888;// 对等端口
    private static int port_c = 1999;// 控制端口
    private static boolean flag = false;// 结束标志
    private static int M = 100;// 资源数
    private static int temp1 = 0;// 通道1资源数目
    private static int temp2 = 0;// 通道2资源数目
    private static int len = 0;// 当前快照数组位置
    private static Snapshoot[] snapshoot = new Snapshoot[50];// 快照数组
    private static Msg[] msgs = new Msg[50];// 消息数组
    private static int msgs_num = 0;
    // 通道延迟 ij ji ik ki jk kj
    private static int[] delay = { 1000, 1300, 1600, 1900, 2100, 2400 };
    private static MyThread_snapshoot_P2P[] myThread_snapshoot_P2Ps = new MyThread_snapshoot_P2P[50];
    private static MyThread_determine[] myThread_determines = new MyThread_determine[50];

    public static class Msg {
        public String s_M = "null";
        public int o_M = 2;
        public boolean state = true;// false为未使用

        public Msg() {
        }

        public Msg(Msg msg) {
            this.s_M = msg.s_M;
            this.o_M = msg.o_M;
            this.state = msg.state;
        }
    }

    public static class Snapshoot {
        private int a = 0;// 结点资源数目
        private int b = 0;// 通道资源数目
        private int c = 0;// 通道资源数目
        private String id = "null";// 快照编号
        public boolean flag1 = false;// 通道1结束标志
        public boolean flag2 = false;// 通道2结束标志

        public Snapshoot() {
            flag1 = false;
            flag2 = false;
        }

        public void seta(int a) {
            this.a = a;
        }

        public void setb(int b) {
            this.b = b;
        }

        public void setc(int c) {
            this.c = c;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int geta() {
            return a;
        }

        public int getb() {
            return b;
        }

        public int getc() {
            return c;
        }

        public String getId() {
            return id;
        }
    }

    public static void menu() {
        System.out.print("请输入本机编号(i,j,k):>");
        name = input.nextLine();

        System.out.print("请输入第一个接收者IP:>");
        ip_1 = input.nextLine();

        System.out.print("请输入第二个接收者IP:>");
        ip_2 = input.nextLine();
    }

    public static class MyThread_c extends Thread {
        public Socket socket = null;
        public ServerSocket server = null;

        public MyThread_c() {
        }

        @Override
        public void run() {
            try {
                server = new ServerSocket(port_c);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            BufferedReader bufferedReader;

            while (!flag) {
                try {
                    socket = server.accept();
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    msgs[msgs_num].s_M = bufferedReader.readLine();
                    msgs[msgs_num++].state = false;
                    // System.out.println(msgs[num].s_M + "\t" + msgs[num].state + "\t" + num);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            // System.out.println("receive_c线程结束");
        }
    }

    public static class MyThread_determine extends Thread {
        private String s = null;
        private int i = 0;

        public MyThread_determine() {
        }

        public MyThread_determine(String s_t, int i_t) {
            this.s = s_t;
            this.i = i_t;
        }

        @Override
        public void run() {
            System.out.println("执行" + msgs[i].s_M);
            // 在未收到标志前一直监听通道数据
            if (name.equals("i")) {
                if (s.equals(ip_1)) {
                    msgs[i].o_M = 0;
                    msgs[i].state = false;
                    if (msgs[i].s_M.substring(0, 1).equals("3")) {
                        temp1 = Integer.parseInt(msgs[i].s_M.substring(2));
                    }
                } else if (s.equals(ip_2)) {
                    msgs[i].o_M = 1;
                    msgs[i].state = false;
                    if (msgs[i].s_M.substring(0, 1).equals("3")) {
                        temp2 = Integer.parseInt(msgs[i].s_M.substring(2));
                    }
                }
            } else if (name.equals("j")) {
                if (s.equals(ip_1)) {
                    msgs[i].o_M = 0;
                    msgs[i].state = false;
                    if (msgs[i].s_M.substring(0, 1).equals("3")) {
                        temp1 = Integer.parseInt(msgs[i].s_M.substring(2));
                    }
                } else if (s.equals(ip_2)) {
                    msgs[i].o_M = 1;
                    msgs[i].state = false;
                    if (msgs[i].s_M.substring(0, 1).equals("3")) {
                        temp2 = Integer.parseInt(msgs[i].s_M.substring(2));
                    }
                }
            } else if (name.equals("k")) {
                if (s.equals(ip_1)) {
                    msgs[i].o_M = 0;
                    msgs[i].state = false;
                    if (msgs[i].s_M.substring(0, 1).equals("3")) {
                        temp1 = Integer.parseInt(msgs[i].s_M.substring(2));
                    }
                } else if (s.equals(ip_2)) {
                    msgs[i].o_M = 1;
                    msgs[i].state = false;
                    if (msgs[i].s_M.substring(0, 1).equals("3")) {
                        temp2 = Integer.parseInt(msgs[i].s_M.substring(2));
                    }
                }
            } // 确定发送的对端的ID,并记录当前通道数据
              // System.out.println(msgs[num].s_M + "\t" + msgs[num].o_M + "\t" +
              // msgs[num].state + "\t" + num);
        }
    }

    public static class MyThread_p extends Thread {
        public Socket socket = null;
        public ServerSocket server = null;

        public MyThread_p() {
        }

        @Override
        public void run() {
            int temp = 0;
            try {
                server = new ServerSocket(port_p);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            BufferedReader bufferedReader;
            String remoteIp = null;
            while (!flag) {
                try {
                    socket = server.accept();
                    remoteIp = socket.getInetAddress().getHostAddress();// 获取对端ip地址
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    msgs[msgs_num].s_M = bufferedReader.readLine();
                    int num = msgs_num++;
                    myThread_determines[temp] = new MyThread_determine(remoteIp, num);
                    myThread_determines[temp++].start();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            // System.out.println("receive_p线程结束");
        }
    }

    public static class Receive extends Thread {
        private int len_s = 0;
        private int len_ss = 0;

        public Receive() {
        }

        @Override
        public void run() {
            MyThread_S[] myThread_S = new MyThread_S[50];
            MyThread_snapshoot[] myThread_snapshoot = new MyThread_snapshoot[50];
            for (int i = 0; i < 50; i++) {
                myThread_snapshoot[i] = new MyThread_snapshoot();
                myThread_snapshoot_P2Ps[i] = new MyThread_snapshoot_P2P();
                myThread_determines[i] = new MyThread_determine();
                myThread_S[i] = new MyThread_S(null);
                snapshoot[i] = new Snapshoot();
                msgs[i] = new Msg();
            }
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss:SSS");
            MyThread_c myThread_c = new MyThread_c();
            myThread_c.start();
            MyThread_p myThread_p = new MyThread_p();
            myThread_p.start();

            int count = 0;
            while (!flag) {
                if (!msgs[count].state) {
                    switch (msgs[count].s_M.substring(0, 1)) {
                        case "1":
                            msgs[count].state = true;
                            myThread_S[len_s] = new MyThread_S(msgs[count].s_M);
                            myThread_S[len_s++].start();
                            break;
                        case "2":
                            msgs[count].state = true;
                            myThread_snapshoot[len_ss] = new MyThread_snapshoot(msgs[count]);
                            myThread_snapshoot_P2Ps[len_ss] = new MyThread_snapshoot_P2P(msgs[count].s_M);
                            // System.out.println("创建快照线程" + "\t" + msgs[count].s_M.substring(2));
                            myThread_snapshoot[len_ss].start();
                            myThread_snapshoot_P2Ps[len_ss++].start();
                            break;
                        case "3":
                            msgs[count].state = true;
                            M += Integer.parseInt(msgs[count].s_M.substring(2));
                            break;
                        case "4":
                            msgs[count].state = true;
                            int n = 0;
                            for (n = 0; n < len;) {
                                if (snapshoot[n].getId().equals(msgs[count].s_M.substring(2))) {
                                    if (msgs[count].o_M == 0 || snapshoot[n].flag2 == true) {
                                        snapshoot[n].flag1 = true;
                                        // System.out.println(snapshoot[n].getId() + "通道一置一");
                                        // System.out.println(snapshoot[n].flag1 + "\t" + snapshoot[n].flag2);
                                        if (snapshoot[n].flag1 == true && snapshoot[n].flag2 == true) {
                                            System.out.println(
                                                    "Snapshoot:\t" + "资源数\t" + "chan1\t" + "chan2\t" + "timer");
                                            System.out.println(snapshoot[n].getId() + "\t" + snapshoot[n].geta() + "\t"
                                                    + snapshoot[n].getb() +
                                                    "\t" + snapshoot[n].getc() + "\t" + df.format(new Date()));
                                            myThread_snapshoot[n].stop();
                                        }
                                        break;
                                    } else if (msgs[count].o_M == 1 || snapshoot[n].flag1 == true) {
                                        snapshoot[n].flag2 = true;
                                        // System.out.println(snapshoot[n].getId() + "通道二置一");
                                        // System.out.println(snapshoot[n].flag1 + "\t" + snapshoot[n].flag2);
                                        if (snapshoot[n].flag1 == true && snapshoot[n].flag2 == true) {
                                            System.out.println(
                                                    "Snapshoot:\t" + "资源数\t" + "chan1\t" + "chan2\t" + "timer");
                                            System.out.println(snapshoot[n].getId() + "\t" + snapshoot[n].geta() + "\t"
                                                    + snapshoot[n].getb() +
                                                    "\t" + snapshoot[n].getc() + "\t" + df.format(new Date()));
                                            myThread_snapshoot[n].stop();
                                        }
                                        break;
                                    }
                                }
                                n++;
                            }
                            if (n == len) {
                                myThread_snapshoot[len_ss] = new MyThread_snapshoot(msgs[count]);
                                myThread_snapshoot_P2Ps[len_ss] = new MyThread_snapshoot_P2P(msgs[count].s_M);
                                // System.out.println("创建快照线程" + "\t" + msgs[count].s_M.substring(2));
                                myThread_snapshoot[len_ss].start();
                                myThread_snapshoot_P2Ps[len_ss++].start();
                            }
                            break;
                        case "5":
                            msgs[count].state = true;
                            flag = true;
                            // System.out.println(flag);
                            try {
                                Thread.sleep(delay[5]);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            try {
                                myThread_c.server.close();
                                myThread_p.server.close();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            ;
                            break;
                        default:
                            break;
                    }
                    count++;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            // System.out.println("receive线程结束");
        }
    }

    public static void send_P2P(String s_f) throws InterruptedException {
        int c = 2;// 记录发送给第几个对端
        int N = Integer.parseInt(s_f.substring(4));// 确定本次转移资源数量
        if (name.equals("i")) {
            if (s_f.substring(2, 3).equals("j")) {
                c = 0;
            } else if (s_f.substring(2, 3).equals("k")) {
                c = 1;
            }
        } else if (name.equals("j")) {
            if (s_f.substring(2, 3).equals("i")) {
                c = 0;
            } else if (s_f.substring(2, 3).equals("k")) {
                c = 1;
            }
        } else if (name.equals("k")) {
            if (s_f.substring(2, 3).equals("i")) {
                c = 0;
            } else if (s_f.substring(2, 3).equals("j")) {
                c = 1;
            }
        }
        // 决定发送给哪个对端
        M -= N;

        if (name.equals("i")) {
            if (c == 0) {
                try {
                    Thread.sleep(delay[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } // 通道延迟
            } else if (c == 1) {
                try {
                    Thread.sleep(delay[2]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } // 通道延迟
            }
        } else if (name.equals("j")) {
            if (c == 0) {
                try {
                    Thread.sleep(delay[1]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } // 通道延迟
            } else if (c == 1) {
                try {
                    Thread.sleep(delay[4]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } // 通道延迟
            }
        } else if (name.equals("k")) {
            if (c == 0) {
                try {
                    Thread.sleep(delay[3]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } // 通道延迟
            } else if (c == 1) {
                try {
                    Thread.sleep(delay[5]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } // 通道延迟
            }
        }

        if (c == 0) {
            try {
                Socket socket1 = new Socket(ip_1, port_p);

                BufferedWriter bufferedWriter1 = new BufferedWriter(
                        new OutputStreamWriter(socket1.getOutputStream()));

                bufferedWriter1.write("3|" + N + "\n");
                bufferedWriter1.flush();
                bufferedWriter1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (c == 1) {
            try {
                Socket socket2 = new Socket(ip_2, port_p);

                BufferedWriter bufferedWriter2 = new BufferedWriter(
                        new OutputStreamWriter(socket2.getOutputStream()));

                bufferedWriter2.write("3|" + N + "\n");
                bufferedWriter2.flush();
                bufferedWriter2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } // 向指定对端发送3|N

    }

    public static class MyThread_S extends Thread {
        private String s_t = null;

        public MyThread_S(String str) {
            this.s_t = str;
        }

        @Override
        public void run() {
            try {
                send_P2P(s_t);
                // System.out.println("send_P2P线程结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class MyThread_snapshoot extends Thread {
        private Msg msg_t = new Msg();

        public MyThread_snapshoot() {
        }

        public MyThread_snapshoot(Msg msg) {
            this.msg_t = new Msg(msg);
        }

        @Override
        public void run() {
            try {
                snapshoot(msg_t);
                // System.out.println(msg_t.s_M.substring(2) + "快照线程结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void snapshoot(Msg msg_f) throws InterruptedException {
        int n = len++;// 记录当前在快照数组内位置
        if (msg_f.s_M.substring(0, 1).equals("2")) {
            snapshoot[n].seta(M);
            snapshoot[n].setId(msg_f.s_M.substring(2));
            while (snapshoot[n].flag1 == false || snapshoot[n].flag2 == false) {
                if (snapshoot[n].flag1 == false) {
                    snapshoot[n].setb(temp1);
                    temp1 = 0;
                }
                if (snapshoot[n].flag2 == false) {
                    snapshoot[n].setc(temp2);
                    temp2 = 0;
                }
            }
        } else if (msg_f.s_M.substring(0, 1).equals("4")) {
            snapshoot[n].seta(M);
            snapshoot[n].setId(msg_f.s_M.substring(2));
            if (msg_f.o_M == 0) {
                snapshoot[n].setb(0);
                snapshoot[n].flag1 = true;
                snapshoot[n].setc(temp2);
            } else if (msg_f.o_M == 1) {
                snapshoot[n].setb(temp1);
                snapshoot[n].flag2 = true;
                snapshoot[n].setc(0);
            }
            // 确定发送端的ID,并记录其通道为空
            while (snapshoot[n].flag1 == false || snapshoot[n].flag2 == false) {
                if (snapshoot[n].flag1 == false) {
                    snapshoot[n].setb(temp1);
                    temp1 = 0;
                }
                if (snapshoot[n].flag2 == false) {
                    snapshoot[n].setc(temp2);
                    temp2 = 0;
                }
            }
        }
    }

    public static class MyThread_snapshoot_P2P extends Thread {
        private String s_t = null;

        public MyThread_snapshoot_P2P() {
        }

        public MyThread_snapshoot_P2P(String s_f) {
            this.s_t = s_f;
        }

        @Override
        public void run() {
            try {
                snapshoot_P2P(s_t);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // System.out.println("snapshoot_P2P结束");
        }
    }

    public static void snapshoot_P2P(String s_f) throws InterruptedException {
        String N = s_f.substring(2);// 确定本次快照编号
        try {
            Socket socket1 = new Socket(ip_1, port_p);
            Socket socket2 = new Socket(ip_2, port_p);
            // sender = ip_1;

            BufferedWriter bufferedWriter1 = new BufferedWriter(
                    new OutputStreamWriter(socket1.getOutputStream()));
            BufferedWriter bufferedWriter2 = new BufferedWriter(
                    new OutputStreamWriter(socket2.getOutputStream()));

            if (name.equals("i")) {
                try {
                    Thread.sleep(delay[0]);
                    bufferedWriter1.write("4|" + N + "\n");
                    bufferedWriter1.flush();
                    bufferedWriter1.close();
                    Thread.sleep(delay[2] - delay[0]);
                    bufferedWriter2.write("4|" + N + "\n");
                    bufferedWriter2.flush();
                    bufferedWriter2.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } // 通道延迟
            }
            if (name.equals("j")) {
                try {
                    Thread.sleep(delay[1]);
                    bufferedWriter1.write("4|" + N + "\n");
                    bufferedWriter1.flush();
                    bufferedWriter1.close();
                    Thread.sleep(delay[4] - delay[1]);
                    bufferedWriter2.write("4|" + N + "\n");
                    bufferedWriter2.flush();
                    bufferedWriter2.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } // 通道延迟
            }
            if (name.equals("k")) {
                try {
                    Thread.sleep(delay[3]);
                    bufferedWriter1.write("4|" + N + "\n");
                    bufferedWriter1.flush();
                    bufferedWriter1.close();
                    Thread.sleep(delay[5] - delay[3]);
                    bufferedWriter2.write("4|" + N + "\n");
                    bufferedWriter2.flush();
                    bufferedWriter2.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } // 通道延迟
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        menu();
        Receive receive = new Receive();
        receive.start();
    }
}
