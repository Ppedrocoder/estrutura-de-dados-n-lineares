package grafo;
import java.util.ArrayList;

public class Vertice {
    private Object valor;
    private ArrayList<Aresta> arestas;
    public Vertice(Object valor) {
        this.arestas = new ArrayList<>();
        this.valor = valor;
    }
    public Object getValor() {
        return valor;
    }
    public void setValor(Object valor) {
        this.valor = valor;
    }
    public ArrayList<Aresta> getArestas() {
        return arestas;
    }
    public void adicionarAresta(Aresta aresta) {
        this.arestas.add(aresta);
    }

}