public class Message {
    private int[] a = new int[12];

    // 信息初始化
    public Message() {
        a[0] = 0;
        a[1] = 0;
        a[2] = 0;
        a[3] = 0;
        a[4] = 0;
        a[5] = 0;
        a[6] = 0;
        a[7] = 0;
        a[8] = 0;
        a[9] = 0;
        a[10] = 0;
        a[11] = 0;
    }

    public Message(int n_1, int n_2, int n_3, int n_4, int n_5, int n_6, int n_7, int n_8, int n_9, int n_10, int n_11,
            int n_12) {
        a[0] = n_1;
        a[1] = n_2;
        a[2] = n_3;
        a[3] = n_4;
        a[4] = n_5;
        a[5] = n_6;
        a[6] = n_7;
        a[7] = n_8;
        a[8] = n_9;
        a[9] = n_10;
        a[10] = n_11;
        a[11] = n_12;
    }

    public int getCode() {
        return (a[0] * 2 + a[1]);
    }// 0为转移数据，1为统计请求，2为统计响应

    public int getNum() {
        return (a[11] + a[10] * 2 + a[9] * 4 + a[8] * 8 + a[7] * 16 + a[6] * 32 + a[5] * 64 + a[4] * 128 + a[3] * 256
                + a[2] * 512);
    }

    // c=0/1/2对应其功能
    public void setCode(int c) {
        a[0] = c / 2;
        a[1] = c % 2;
    }

    // 十进制转二进制
    public void setNum(int n) {
        // 判断n转为二进制后是否会造成数组溢出
        if (n < 1024) {
            int i = 11;
            do {
                a[i] = n % 2;
                n /= 2;
                i--;
            } while (n != 0);
        }
    }
}

