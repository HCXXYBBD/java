import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
  private ObjectInputStream ois;
  private ServerSocket server;
  private Socket socket;
  void getServer() {
	  try {
		  server=new ServerSocket(999);
		  System.out.println("socket创建成功！");
		  System.out.println("等待客户连接···");
		  socket=server.accept();
		  System.out.println("连接中···");
		  ois=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
	  }catch (Exception e) {
          e.printStackTrace();
      }
  }
  public void getClientMessage() {
	// TODO Auto-generated method stub
	try {
		Date date = new Date();
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		System.out.println("收到信息：");
		System.out.println("random1\trandom2\ttimer\t\tlocaltime");
		for(int i=0;i<20;i++) {
			Message m=new Message();
			ois.readObject();
			if(m.getRandom1()==2) {
				break;
			}else
			System.out.println(m.getRandom1()+"\t"+m.getRandom2()+"\t"+m.getTimer()+"\t"+simpleDateFormat.format(date));
		}
	}catch(IOException e) {
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		ois.close();
		System.out.println("输出流关闭");
		socket.close();
		System.out.println("socket关闭");
	}catch(IOException e) {
		e.printStackTrace();
	}
}
public static void main(String[] args) {
	Server s=new Server();
    s.getServer(); //启动客户端
  }
}