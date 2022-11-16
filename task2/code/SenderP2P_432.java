import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.io.*;
import java.net.Socket;

public class Server_2 {
    private static int count_0;
    private static int count_1;
    private static int threshold;
    private static String ip_1;
    private static String ip_2;

    public static Random init() {
        count_0 = 0;
        count_1 = 0;

        System.out.print("请输入随机数种子:>");
        Scanner input = new Scanner(System.in);
        long s = input.nextLong();
        Random r = new Random(s);

        System.out.print("请输入超时阈值(ms):>");
        threshold = input.nextInt();

        System.out.print("请输入第一个接收者IP:>");
        ip_1 = input.next();

        System.out.print("请输入第二个接收者IP:>");
        ip_2 = input.next();
        input.close();

        System.out.printf("%-20s\t", "random1");
        System.out.printf("%-20s\t", "random2");
        System.out.printf("%-20s\t\n", "timer");

        return r;
    }

    public static int create_rand1(Random r) throws InterruptedException {
        double rand = r.nextDouble();
        long interval = (long) (Math.log1p(rand - 1) * 1000 * (-6));
        Thread.sleep(interval);
        if (interval > threshold) {
            return 2;
        } else {
            if (count_0 == 15) {
                count_1++;
                return 1;
            } else if (count_1 == 5) {
                count_0++;
                return 0;
            } else if (rand < 0.25) {
                count_1++;
                return 1;
            } else {
                count_0++;
                return 0;
            }
        }
    }

    public static int create_rand2(Random r, int rand1) {
        if (rand1 == 1) {
            int rand = r.nextInt(4) + 1;
            return rand;
        } else if (rand1 == 0) {
            int rand = r.nextInt(99) + 101;
            return rand;
        } else
            return 0;
    }

    public static void init_m(Random r, Message m) throws InterruptedException {
        m.setRandom1(create_rand1(r));
        m.setRandom2(create_rand2(r, m.getRandom1()));
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss:SSS");// 设置日期格式
        m.setTimer(df.format(new Date()));
    }

    public static void send() throws InterruptedException {
        Message m = new Message();
        Random r = init();
        int i = 0;
        while (i < 20) {
            init_m(r, m);
            if (m.getRandom1() == 0) {

                try {
                    Socket socket1 = new Socket(ip_1, 999);

                    // 发送给服务端消息
                    BufferedWriter bufferedWriter1 = new BufferedWriter(
                            new OutputStreamWriter(socket1.getOutputStream()));
                    bufferedWriter1.write(Integer.toString(m.getRandom1()) + " ");
                    bufferedWriter1.flush();

                    bufferedWriter1.write(Integer.toString(m.getRandom2()) + " ");
                    bufferedWriter1.flush();

                    bufferedWriter1.write(m.getTimer() + " " + "\n");
                    bufferedWriter1.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.printf("%-20d\t", m.getRandom1());
                System.out.printf("%-20d\t", m.getRandom2());
                System.out.printf("%-20s\t\n", m.getTimer());
                i++;
                if (m.getRandom1() == 2) {
                    break;
                }
            }

            else if (m.getRandom1() == 1) {
                try {
                    Socket socket2 = new Socket(ip_2, 999);

                    // 发送给服务端消息
                    BufferedWriter bufferedWriter2 = new BufferedWriter(
                            new OutputStreamWriter(socket2.getOutputStream()));
                    bufferedWriter2.write(Integer.toString(m.getRandom1()) + " ");
                    bufferedWriter2.flush();

                    bufferedWriter2.write(Integer.toString(m.getRandom2()) + " ");
                    bufferedWriter2.flush();

                    bufferedWriter2.write(m.getTimer() + " " + "\n");
                    bufferedWriter2.flush();
                    // }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.printf("%-20d\t", m.getRandom1());
                System.out.printf("%-20d\t", m.getRandom2());
                System.out.printf("%-20s\t\n", m.getTimer());
                i++;
                if (m.getRandom1() == 2) {
                    break;
                }
            }

            else if (m.getRandom1() == 2) {
                try {
                    Socket socket1 = new Socket(ip_1, 999);

                    // 发送给服务端消息
                    BufferedWriter bufferedWriter1 = new BufferedWriter(
                            new OutputStreamWriter(socket1.getOutputStream()));
                    bufferedWriter1.write(Integer.toString(m.getRandom1()) + " ");
                    bufferedWriter1.flush();

                    bufferedWriter1.write(Integer.toString(m.getRandom2()) + " ");
                    bufferedWriter1.flush();

                    bufferedWriter1.write(m.getTimer() + " " + "\n");
                    bufferedWriter1.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    // Socket socket1 = new Socket(ip_1, 999);
                    Socket socket2 = new Socket(ip_2, 999);

                    // 发送给服务端消息
                    BufferedWriter bufferedWriter2 = new BufferedWriter(
                            new OutputStreamWriter(socket2.getOutputStream()));
                    bufferedWriter2.write(Integer.toString(m.getRandom1()) + " ");
                    bufferedWriter2.flush();

                    bufferedWriter2.write(Integer.toString(m.getRandom2()) + " ");
                    bufferedWriter2.flush();

                    bufferedWriter2.write(m.getTimer() + " " + "\n");
                    bufferedWriter2.flush();
                    // }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.printf("%-20d\t", m.getRandom1());
                System.out.printf("%-20d\t", m.getRandom2());
                System.out.printf("%-20s\t\n", m.getTimer());
                i++;
                if (m.getRandom1() == 2) {
                    break;
                }
            }

        }
    }

    public static void main(String[] args) throws Exception {
        send();
    }

}
