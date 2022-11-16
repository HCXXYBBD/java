public class Msg {
	int msgType = 1;//消息类型
	int interval = 0;//消息时间间隔
	int time = 0;//消息开始时间
	int num = 0;//转移资源数或全局快照编号
	int shotNum = 0;//快照编号
	int from;//消息类型1时为转出节点；消息类型2时为快照发起节点。0-i，1-j，2-k
	int to;//消息类型为1时的转入节点
	
	public Msg(){}
	
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	
	public int getMsgType() {
		return this.msgType;
	}
	
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	public int getInterval() {
		return this.interval;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public int getTime() {
		return this.time;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
	
	public int getNum() {
		return this.num;
	}
	
	public void setShotNum(int shotNum) {
		this.shotNum = shotNum;
	}
	
	public int getShotNum() {
		return this.shotNum;
	}
	
	public void setFrom(int from) {
		this.from=from;
	}
	
	public int getFrom() {
		return this.from;
	}
	
	public void setTo(int to) {
		this.to=to;
	}
	
	public int getTo() {
		return this.to;
	}
}

