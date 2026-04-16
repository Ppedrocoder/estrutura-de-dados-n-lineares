public class NoRN extends No {
    private String color;
    public NoRN() {
        super();
        this.color = "V";
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
