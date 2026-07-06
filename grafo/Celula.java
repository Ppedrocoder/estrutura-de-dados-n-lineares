package grafo;

public class Celula {
    public final int linha;
    public final int coluna;
    public final int tipo; // 0=vazio, 1=parede, 2=partida, 3=saida

    public Celula(int linha, int coluna, int tipo) {
        this.linha  = linha;
        this.coluna = coluna;
        this.tipo   = tipo;
    }

    public boolean eParede()   { return tipo == 1; }
    public boolean ePartida()  { return tipo == 2; }
    public boolean eSaida()    { return tipo == 3; }
    public boolean eCaminhavel() { return tipo != 1; }

    @Override
    public String toString() { return "(" + linha + "," + coluna + ")"; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Celula)) return false;
        Celula c = (Celula) o;
        return linha == c.linha && coluna == c.coluna;
    }

    @Override
    public int hashCode() { return 31 * linha + coluna; }
}
