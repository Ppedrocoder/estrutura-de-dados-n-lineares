package grafo;
import java.util.ArrayList;

public class Grafo {
    private ArrayList<Vertice> vertices;
    private ArrayList<Aresta> arestas;
    public Grafo() {
        this.vertices = new ArrayList<>();
        this.arestas = new ArrayList<>();
    }

    public ArrayList<Vertice> finalVertices(Aresta e) {
        return null;
    }
    public Vertice oposto(Vertice v, Aresta e) {
        return null;
    }
    public boolean éAdjacente(Vertice v, Vertice w) {
        return false;
    }
    public void substituir(Vertice v, Vertice x) {
    }
    public void substituir(Aresta e, Aresta x) {
    }
    public void inserirVertice(Object o) {
        Vertice vertice = new Vertice(o);
        this.vertices.add(vertice);
    }
    public void inserirAresta(Vertice origem, Vertice destino, Object o) {
        Aresta aresta = new Aresta(origem, destino, o);
        this.arestas.add(aresta);
        origem.adicionarAresta(aresta);
        destino.adicionarAresta(aresta);
    }
    public void removeVértice(Vertice v) {
    }
    public void removeAresta(Aresta e) {
    }
}
