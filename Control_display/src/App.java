import java.util.*;
import java.io.*;
import java.net.Socket;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String i,j,k;
		//String localhost = "127.0.0.1";
		int port = 999;//i,j,k,c节点端口号
		int n = 0;//资源转移次数
		int m = 0;//快照次数
		//通道延迟		   ij	 ji   ik   ki   jk   kj 
		int[] delay = {1000,1300,1600,1900,2100,2400};
		int R;//随机数种子
		Random Rd;
			
		//输入相关信息
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入i的IP地址:");
		i=sc.nextLine();
		System.out.println("请输入j的IP地址:");
		j=sc.nextLine();
		System.out.println("请输入k的IP地址:");
		k=sc.nextLine();
		System.out.println("请输入资源转移次数:");
		n=sc.nextInt();
		System.out.println("请输入快照次数:");
		m=sc.nextInt();
		System.out.println("请输入随机数种子:");
		R=sc.nextInt();
        Rd = new Random(R);
        sc.close();
        int p,q;
        Msg[] msgSquence = new Msg[n+m+1];//原始消息队列
		for(p=0;p<m+n+1;p++){  
			msgSquence[p] = new Msg();  
		}  
		LinkedList<MsgArrive>  msgArrived= new LinkedList<MsgArrive>();//消息到达
		int[] nodeState = {100,100,100};//节点状态
		int[] chanState = {0,0,0,0,0,0};//通道状态。顺序：ij,ji,ik,ki,jk,kj
		int[][] result = new int[m][10];//快照答案(i#,j#,k#,ij,ji,ik,ki,jk,kj,快照编号)
		boolean[][] stateResult = new boolean[m][9];//标记，(i#,j#,k#,ij,ji,ik,ki,jk,kj)
		int[] shotNum = {101,201,301};//全局快照编号
		int snapShotNum = 0;//全局快照编号
		
		//随机产生快照发生的位置
		int rd = 0;
		for(p=0;p<m;p++)
		{
			do {
				rd = Rd.nextInt(n+m-1)+1;
			}while(msgSquence[rd].getMsgType() != 1);
			msgSquence[rd].setMsgType(2);
		}
		
		//随机产生事件发生时间，时间间隔5ln(R)
		double interval = 0;
		for(p=1;p<m+n+1;p++) {
			do{
				interval = -5*Math.log(Rd.nextDouble());
			}while(interval<0.15);
			msgSquence[p].setInterval((int)(interval*1000));
			msgSquence[p].setTime(msgSquence[p-1].getTime()+(int)(interval*1000));
		}
		p=1;
		while(true) {
			if(p>(m+n) && msgArrived.isEmpty())
				break;
			if(msgArrived.isEmpty() || (p<=m+n && msgSquence[p].getTime()<msgArrived.getFirst().getArriveTime()))
			{
				if(msgSquence[p].msgType==1)
				{
					msgSquence[p].setFrom(Rd.nextInt(3));//随机选择转出节点。0-i，1-j，2-k
					do {
						msgSquence[p].setTo(Rd.nextInt(3));;//随机选择转入节点
					} while (msgSquence[p].getFrom() == msgSquence[p].getTo());
					if(nodeState[msgSquence[p].getFrom()]==0)
						msgSquence[p].setNum(0);
					else if(nodeState[msgSquence[p].getFrom()]==1)
						msgSquence[p].setNum(1);
					else
						msgSquence[p].setNum(Rd.nextInt(nodeState[msgSquence[p].getFrom()]-1)+1);//转出量
					nodeState[msgSquence[p].getFrom()] -= msgSquence[p].getNum();
					chanState[getPath(msgSquence[p].getFrom(), msgSquence[p].getTo())] += msgSquence[p].getNum();
					System.out.println("资源转移："+getName(msgSquence[p].getFrom(), msgSquence[p].getTo())+
							" 数量："+msgSquence[p].getNum()+" 时间："+msgSquence[p].getTime()+"ms");
					//生成对应的到达消息，并放入msgArrived
					MsgArrive temp = new MsgArrive(3, 
							msgSquence[p].getTime()+delay[getPath(msgSquence[p].getFrom(), msgSquence[p].getTo())], 
							getPath(msgSquence[p].getFrom(), msgSquence[p].getTo()),msgSquence[p].getNum());
					insert(msgArrived,temp);
				}
				else if(msgSquence[p].msgType==2)
				{
					msgSquence[p].setFrom(Rd.nextInt(3));//随机选择快照发起节点。0-i，1-j，2-k
					msgSquence[p].setNum(snapShotNum++);
					msgSquence[p].setShotNum(shotNum[msgSquence[p].getFrom()]++);
					System.out.println("快照发起："+getNodeName(msgSquence[p].getFrom())+
							" 编号："+msgSquence[p].getShotNum()+" 时间："+msgSquence[p].getTime()+"ms");
					
					result[msgSquence[p].getNum()][9] = msgSquence[p].getShotNum();//记录快照编号
					result[msgSquence[p].getNum()][msgSquence[p].getFrom()] = nodeState[msgSquence[p].getFrom()];//记录局部状态
					stateResult[msgSquence[p].getNum()][msgSquence[p].getFrom()] = true;//给自己一个标记
					//给还未收到消息的通道一个标记
					stateResult[msgSquence[p].getNum()][getPath((msgSquence[p].getFrom()+1)%3, msgSquence[p].getFrom())+3] = true;
					stateResult[msgSquence[p].getNum()][getPath((msgSquence[p].getFrom()+2)%3, msgSquence[p].getFrom())+3] = true;
					//在所有还没有发送过标志的通道上发送一个标志 
					MsgArrive temp1 = new MsgArrive(4, 
							msgSquence[p].getTime()+delay[getPath(msgSquence[p].getFrom(), (msgSquence[p].getFrom()+1)%3)], 
							getPath(msgSquence[p].getFrom(), (msgSquence[p].getFrom()+1)%3 ) ,msgSquence[p].getNum());
					MsgArrive temp2 = new MsgArrive(4, 
							msgSquence[p].getTime()+delay[getPath(msgSquence[p].getFrom(), (msgSquence[p].getFrom()+2)%3)], 
							getPath(msgSquence[p].getFrom(), (msgSquence[p].getFrom()+2)%3 ) ,msgSquence[p].getNum());
					insert(msgArrived,temp1);
					insert(msgArrived,temp2);
				}
				p++;
			}
			else
			{
				MsgArrive temp = msgArrived.getFirst();
				if(temp.msgType==3)
				{
					nodeState[getTo(temp.getPath())] += temp.getNum();
					chanState[temp.getPath()] -= temp.getNum();
					for (q=0;q<m;q++)
					{
						if(stateResult[q][temp.getPath()+3])//如果该通道上有标记，处于快照接收消息状态
							result[q][temp.getPath()+3] += temp.getNum();
					}
				}
				if(temp.msgType==4)
				{
					stateResult[temp.getNum()][temp.getPath()+3] = false;
					if(stateResult[temp.getNum()][getTo(temp.getPath())]==false)//Q还没有记录它的状态
					{
						stateResult[temp.getNum()][getTo(temp.getPath())] = true;//标记
						result[temp.getNum()][getTo(temp.getPath())] = nodeState[getTo(temp.getPath())];//记录局部状态
						result[temp.getNum()][temp.getPath()+3] = 0;//记录通道chan的状态为一个空序列
						//在所有还没有发送过标志的通道上发送一个标志 
						MsgArrive temp1 = new MsgArrive(4,
								temp.getArriveTime()+delay[getPath(getTo(temp.getPath()),(getTo(temp.getPath())+1)%3)],
								getPath(getTo(temp.getPath()),(getTo(temp.getPath())+1)%3),temp.getNum());
						MsgArrive temp2 = new MsgArrive(4,
								temp.getArriveTime()+delay[getPath(getTo(temp.getPath()),(getTo(temp.getPath())+2)%3)],
								getPath(getTo(temp.getPath()),(getTo(temp.getPath())+2)%3),temp.getNum());
						insert(msgArrived, temp1);
						insert(msgArrived, temp2);
						//在所有还没有发送过标志的通道上发送一个标志
						stateResult[temp.getNum()][getPath(3-getFrom(temp.getPath())-getTo(temp.getPath()), getTo(temp.getPath()))+3] = true;
					}
				}
				msgArrived.removeFirst();
			}
		}
		
		//快照理论结果
		for(p=0;p<m;p++)
		{
			System.out.println("/***理论结果***快照编号("+result[p][9]+")***/");
			System.out.printf("%-1s%-4s%-4s%-4s%-4s%-4s%-4s%-4s%-4s%-4s%-1s\n","[","i,","j,","k,","ij,","ji,","ik,","ki,","jk,","kj","]");
			System.out.printf("%-1s%-4d%-4d%-4d%-4d%-4d%-4d%-4d%-4d%-4d%-1s\n","[",result[p][0],result[p][1],result[p][2],result[p][3],
					result[p][4],result[p][5],result[p][6],result[p][7],result[p][8],"]");
		}
		
		//依次给P节点发送控制消息
		for(p=1;p<m+n+1;p++)
		{
			try {
				Thread.sleep(msgSquence[p].getInterval());
			} catch (InterruptedException e) {
					e.printStackTrace();
			}
			if(msgSquence[p].getMsgType()==1)
			{
				String to = getNodeName(msgSquence[p].getTo());
				String msg = "1|"+to+"|"+msgSquence[p].getNum();
				if(msgSquence[p].getFrom()==0)
					sendMsg(i,port,msg);
				if(msgSquence[p].getFrom()==1)
					sendMsg(j,port,msg);
				if(msgSquence[p].getFrom()==2)
					sendMsg(k,port,msg);
			}
			if(msgSquence[p].getMsgType()==2)
			{
				String msg = "2|"+msgSquence[p].getShotNum();
				if(msgSquence[p].getFrom()==0)
					sendMsg(i,port,msg);
				if(msgSquence[p].getFrom()==1)
					sendMsg(j,port,msg);
				if(msgSquence[p].getFrom()==2)
					sendMsg(k,port,msg);
			}
		}
				
		//发送结束命令
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
				e.printStackTrace();
		}
		sendMsg(i,port,"5");
		sendMsg(j,port,"5");
		sendMsg(k,port,"5");
	}
	
	public static void sendMsg(String ip,int port,String msg)
	{
	    Socket socket = null;
		try {
			socket = new Socket(ip,port);
			socket.getOutputStream().write(msg.getBytes("UTF-8"));
			socket.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}		
	}
	
	//根据节点编号，返回通道编号
	//顺序：ij,ji,ik,ki,jk,kj
	public static int getPath(int from,int to)
	{
		if(from==0)
			if(to==1)
				return 0;
			else 
				return 2;
		if(from==1)
			if(to==0)
				return 1;
			else
				return 4;
		if(from==2)
			if(to==0)
				return 3;
			else
				return 5;
		return -1;
	}
	
	//根据通道编号，返回通道名称
	public static String getName(int from,int to)
	{
		if(from==0)
			if(to==1)
				return "i->j";
			else 
				return "i->k";
		if(from==1)
			if(to==0)
				return "j->i";
			else
				return "j->k";
		if(from==2)
			if(to==0)
				return "k->i";
			else
				return "k->j";
		return "";
	}
	
	//根据结点编号，返回结点名称
	public static String getNodeName(int from)
	{
		if(from==0)
			return "i";
		if(from==1)
			return "j";
		if(from==2)
			return "k";
		return "";
	}
	
	//在MsgArrived链表的合适位置插入消息
	public static void insert(LinkedList<MsgArrive> a,MsgArrive temp)
	{
		if(a.isEmpty())
			a.add(temp);
		else
			if(temp.getArriveTime() <= a.getFirst().getArriveTime())
				a.addFirst(temp);
			else if(temp.getArriveTime()>=a.getLast().getArriveTime())
				a.addLast(temp);
			else
				for(int i=0;i<a.size();i++)
				{
					if(temp.getArriveTime() <= a.get(i).getArriveTime())
					{
						a.add(i, temp);
						break;
					}
				}		
	}
	
	//根据通道编号，返回目的节点编号
	public static int getTo(int pathNum)
	{
		if(pathNum==1||pathNum==3)
			return 0;
		if(pathNum==0||pathNum==5)
			return 1;
		if(pathNum==2||pathNum==4)
			return 2;
		return -1;
	}
		
	//根据通道编号，返回源节点编号
	public static int getFrom(int pathNum)
	{
		if(pathNum==0||pathNum==2)
			return 0;
		if(pathNum==1||pathNum==4)
			return 1;
		if(pathNum==3||pathNum==5)
			return 2;
		return -1;
	}

}
