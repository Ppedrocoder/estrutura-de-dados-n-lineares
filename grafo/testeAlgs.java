package grafo;
public class testeAlgs {

    public static void main(String[] args) {

        // ── Montando o grafo ──────────────────────────────────────────────
        Grafo g = new Grafo();

        g.inserirVertice("A");
        g.inserirVertice("B");
        g.inserirVertice("C");
        g.inserirVertice("D");
        g.inserirVertice("E");

        Vertice vA = g.vertices().get(0);
        Vertice vB = g.vertices().get(1);
        Vertice vC = g.vertices().get(2);
        Vertice vD = g.vertices().get(3);
        Vertice vE = g.vertices().get(4);

        g.inserirAresta(vA, vB, 4);
        g.inserirAresta(vA, vC, 2);
        g.inserirAresta(vB, vD, 5);
        g.inserirAresta(vC, vB, 1);
        g.inserirAresta(vC, vD, 8);
        g.inserirAresta(vD, vE, 2);
        g.inserirAresta(vB, vE, 6);
 
        // ── Dijkstra de A ate E ───────────────────────────────────────────
        Dijkstra.calcular(g, vA, vE);

        System.out.println();

        // ── A* de A ate E ─────────────────────────────────────────────────
        // Heuristica: estimativa do custo restante ate E para cada vertice
        AEstrela.calcular(g, vA, vE, (v, dest) -> {
            switch (v.getValor().toString()) {
                case "A": return 7;
                case "B": return 4;
                case "C": return 5;
                case "D": return 2;
                case "E": return 0;
                default:  return 0;
            }
        });
    }
}

