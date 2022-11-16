public class Log_1 extends Message{
    private int code;// 0发送/1接收
    private String name;// 对端节点名称（如果为自己，则是向其他两个对端发送统计请求）
    public Message m;// 储存发送或接收的信息
    private int total;// 操作后的资源总数

    public Log_1(int code, String name, Message m, int total) {
        setCode(code);
        setName(name);
        setMessage(m);
        setTotal(total);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(Message m) {
        this.m.setCode(m.getCode());
        this.m.setNum(m.getNum());
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public int getM_code() {
        return this.m.getCode();
    }
    public int getM_num() {
        return this.m.getNum();
    }

    public int getTotal() {
        return this.total;
    }
}

