System.out.print("请输入种子:>");
        Scanner input = new Scanner(System.in);
        long s = input.nextLong();
        Random r = new Random(s);
        input.close();

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss:SSS");// 设置日期格式
        System.out.println("开始时间:>" + df.format(new Date()));// new Date()为获取当前系统时间

        String id = "序号";
        System.out.printf("%-8s\t", id);

        String code = "1/0";
        System.out.printf("%-8s\t", code);

        String time = "时刻";
        System.out.printf("%-20s\t", time);

        String gap = "间隔";
        System.out.println(gap);

        int i = 1;
        int count_1 = 0;
        int count_0 = 0;

        long interval = 0;
        long sum = 0;

        while (i <= 20) {
            double rand = r.nextDouble();
            interval = (long) (Math.log1p(rand-1) * 1000 * (-6));
            Thread.sleep(interval);
            sum += interval;

            System.out.printf("%-8s\t", i);
            if (count_0 == 15) {
                System.out.printf("%-8s\t", 1);
                count_1++;
                i++;
            } else if (count_1 == 5) {
                System.out.printf("%-8s\t", 0);
                count_0++;
                i++;
            } else if (rand < 0.25) {
                System.out.printf("%-8s\t", 1);
                count_1++;
                i++;
            } else {
                System.out.printf("%-8s\t", 0);
                count_0++;
                i++;
            }
            System.out.printf("%-20s\t", df.format(new Date()));
            System.out.println(interval);
        }

        System.out.println("结束时间:>" + df.format(new Date()));
        System.out.println("运行时间(ms):>" + sum);