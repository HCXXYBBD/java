public class MsgArrive {
	int msgType = 3;//消息类型
	int arriveTime = 0;//到达时间
	int path;//到达通道.顺序：ij,ji,ik,ki,jk,kj
	int num;//消息类型3时为资源数量；消息类型4时为全局快照编号。
	
	public MsgArrive(){}
	
	public MsgArrive(int msgType,int arriveTime,int path,int num)
	{
		this.msgType = msgType;
		this.arriveTime = arriveTime;
		this.path = path;
		this.num = num;
	}
	
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	
	public int getsetMsgType() {
		return this.msgType;
	}
	
	public void setArriveTime(int arriveTime) {
		this.arriveTime = arriveTime;
	}
	
	public int getArriveTime() {
		return this.arriveTime;
	}
	
	public void setPath(int path) {
		this.path = path;
	}
	
	public int getPath() {
		return this.path;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
	
	public int getNum() {
		return this.num;
	}
}
