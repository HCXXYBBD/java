import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;

public class Log {
    public static void write(Log_1 l) {
        // 使用相对路径，日志文件在项目内
        String filepath = "log.txt";

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss:SSS");
        File file = new File(filepath);

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter
                    .write("l.getCode()" + "\t" + "l.getName()" + "\t" + "l.getM_code()" + "\t" + "l.getM_num()" + "\t"
                            + "l.getTotal()" + "\t" + df.format(new Date()) + "\n");
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        write(null);
    }
}
