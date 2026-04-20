public class NoRN extends No {
    private String color;
    public NoRN() {
        super();
        this.color = "V";
        setLeftChild(new NoRN(this, null));
        setRightChild(new NoRN(this, null));
        NoRN lc = (NoRN) getLeftChild();
        lc.setColor("P");
        NoRN rc = (NoRN) getRightChild();
        rc.setColor("P");
    }
    public NoRN(NoRN parent, Object chave) {
        super(parent, chave);
        this.color = "V";
    }
    public String getColor() {
        return this.color;
    }
    public void setColor(String color) {
        this.color = color;
    }
}
