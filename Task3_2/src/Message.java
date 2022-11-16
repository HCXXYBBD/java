public class Message {
    private int code;
    private String name;
    private int trans;
    private int total;
    private String timer;

    public Message() {
        setCode(2);
        setName("a");
        setTrans(0);
        setTotal(100);
        setTimer("00:00:00:000");
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getTrans() {
        return trans;
    }

    public int getTotal() {
        return total;
    }

    public String getTimer() {
        return timer;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTrans(int trans) {
        this.trans = trans;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

}

