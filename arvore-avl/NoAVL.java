import ArvoreBinaria.No;

public class NoAVL extends No {
    private int FB;
    public NoAVL() {
        super();
        FB = 0;
    }
    public NoAVL(NoAVL Parent,Object element) {
        super(Parent,element);
        FB = 0;
    }
    public NoAVL(NoAVL Parent) {
        super(Parent);
        FB = 0;
    }
    public void setBalanceFactor(int FB) {
        this.FB = FB;
    }
    public int getBalanceFactor() {
        return FB;
    }

    
}