package grafo;

public class Aresta {
    private Vertice origem;
    private Vertice destino;
    private Object valor;
    private boolean direcionada;
    public Aresta(Vertice origem, Vertice destino, Object valor) {
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
        this.direcionada = false;
    }
    public Vertice getOrigem() {
        return origem;
    }
    public Vertice getDestino() {
        return destino;
    }
    public Object getValor() {
        return valor;
    }
    public void setValor(Object valor) {
        this.valor = valor;
    }
    public boolean getDirecionada() {
        return direcionada;
    }
    public void setDirecionada(boolean direcionada) {
        this.direcionada = direcionada;
    }
}
