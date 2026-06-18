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
        ArrayList<Vertice> fins = new ArrayList<>();
        fins.add(e.getOrigem());
        fins.add(e.getDestino());
        return fins;
    }
    public Vertice oposto(Vertice v, Aresta e) {
        if (e.getOrigem().equals(v)) {
            return e.getDestino();
        } else if (e.getDestino().equals(v)) {
            return e.getOrigem();
        }
        throw new IllegalArgumentException("Vértice não incide sobre a aresta.");
    }
    public boolean eAdjacente(Vertice v, Vertice w) {
        for (Aresta a : v.getArestas()) {
            if (a.getOrigem().equals(w) || a.getDestino().equals(w)) {
                return true;
            }
        }
        return false;
    }
    public void substituir(Vertice v, Object x) {
        v.setValor(x);
    }
    public void substituir(Aresta e, Object x) {
        e.setValor(x);
    }
    public void inserirVertice(Object o) {
        Vertice vertice = new Vertice(o);
        this.vertices.add(vertice);
    }
    public Object inserirAresta(Vertice origem, Vertice destino, Object o) {
        Aresta aresta = new Aresta(origem, destino, o);
        this.arestas.add(aresta);
        origem.adicionarAresta(aresta);
        destino.adicionarAresta(aresta);
        return aresta;
    }
    public Object removeVertice(Vertice v) {
        for (Aresta a : arestasIncidentes(v)) {
            Vertice outro = oposto(v, a);
            if (!a.getDirecionada()) {
                outro.getArestas().remove(a); 
            }
            this.arestas.remove(a);
        }
        for (Aresta a : arestas()) {
            if (a.getDirecionada() && a.getDestino().equals(v)) {
                a.getOrigem().getArestas().remove(a);
                this.arestas.remove(a);
            }
        }
        this.vertices.remove(v);
        return v.getValor();
    }
    public Object removeAresta(Aresta e) {
        e.getOrigem().getArestas().remove(e);
        if (!e.getDirecionada()) {
            e.getDestino().getArestas().remove(e);
        }
        this.arestas.remove(e);
        return e.getValor();
    }
    public boolean eDirecionada(Aresta e) {
        return e.getDirecionada();
    }
    public void inserirArestaDirecionada(Vertice origem, Vertice destino, Object o) {
        Aresta aresta = new Aresta(origem, destino, o);
        aresta.setDirecionada(true);
        this.arestas.add(aresta);
        origem.adicionarAresta(aresta);
    }
    public ArrayList<Aresta> arestasIncidentes(Vertice v) {
        return new ArrayList<>(v.getArestas());
    }
    public ArrayList<Vertice> vertices() {
        return new ArrayList<>(this.vertices);
    }
    public ArrayList<Aresta> arestas() {
        return new ArrayList<>(this.arestas);
    }
}