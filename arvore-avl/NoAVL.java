
public class NoAVL extends No {
    private int FB;
    public NoAVL() {
        super();
        this.FB = 0;
    }
    public NoAVL(No Parent,Object element) {
        super(Parent,element);
        this.FB = 0;
    }
    public NoAVL(No Parent) {
        super(Parent);
        this.FB = 0;
    }
    public void setFB(int FB) {
        this.FB = FB;
    }
    public int getFB() {
        return FB;
    }

    
}