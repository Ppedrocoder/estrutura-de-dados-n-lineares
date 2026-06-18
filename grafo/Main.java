package grafo;

public class Main {
    public static void main(String[] args) {

        Grafo g = new Grafo();

        // --- inserirVertice ---
        System.out.println("=== Inserindo vértices ===");
        g.inserirVertice("A");
        g.inserirVertice("B");
        g.inserirVertice("C");
        g.inserirVertice("D");
        System.out.println("Vértices: " + g.vertices().stream().map(v -> v.getValor().toString()).toList());

        // pegar referências para usar nos testes
        Vertice vA = g.vertices().get(0);
        Vertice vB = g.vertices().get(1);
        Vertice vC = g.vertices().get(2);
        Vertice vD = g.vertices().get(3);

        // --- inserirAresta ---
        System.out.println("\n=== Inserindo arestas não-dirigidas ===");
        Aresta aAB = (Aresta) g.inserirAresta(vA, vB, "A-B");
        Aresta aAC = (Aresta) g.inserirAresta(vA, vC, "A-C");
        Aresta aBC = (Aresta) g.inserirAresta(vB, vC, "B-C");
        // aresta paralela (multigrafo)
        Aresta aAB2 = (Aresta) g.inserirAresta(vA, vB, "A-B paralela");
        System.out.println("Arestas: " + g.arestas().stream().map(a -> a.getValor().toString()).toList());

        // --- inserirArestaDirecionada ---
        System.out.println("\n=== Inserindo aresta dirigida ===");
        g.inserirArestaDirecionada(vD, vA, "D->A");
        System.out.println("Arestas: " + g.arestas().stream().map(a -> a.getValor().toString()).toList());

        // --- eDirecionada ---
        System.out.println("\n=== eDirecionada ===");
        System.out.println("A-B é dirigida? " + g.eDirecionada(aAB));         // false
        Aresta aDA = (Aresta) g.arestas().get(g.arestas().size() - 1);
        System.out.println("D->A é dirigida? " + g.eDirecionada(aDA));        // true

        // --- finalVertices ---
        System.out.println("\n=== finalVertices ===");
        System.out.println("Vértices de A-B: " + g.finalVertices(aAB).stream().map(v -> v.getValor().toString()).toList());

        // --- oposto ---
        System.out.println("\n=== oposto ===");
        System.out.println("Oposto de A em A-B: " + g.oposto(vA, aAB).getValor()); // B
        System.out.println("Oposto de B em A-B: " + g.oposto(vB, aAB).getValor()); // A

        // --- eAdjacente ---
        System.out.println("\n=== eAdjacente ===");
        System.out.println("A adjacente a B? " + g.eAdjacente(vA, vB)); // true
        System.out.println("A adjacente a D? " + g.eAdjacente(vA, vD)); // false

        // --- arestasIncidentes ---
        System.out.println("\n=== arestasIncidentes ===");
        System.out.println("Arestas de A: " + g.arestasIncidentes(vA).stream().map(a -> a.getValor().toString()).toList());
        System.out.println("Arestas de D: " + g.arestasIncidentes(vD).stream().map(a -> a.getValor().toString()).toList());

        // --- substituir ---
        System.out.println("\n=== substituir ===");
        g.substituir(vA, "A_novo");
        System.out.println("Valor de vA após substituir: " + vA.getValor()); // A_novo
        g.substituir(aAB, "A-B_novo");
        System.out.println("Valor de aAB após substituir: " + aAB.getValor()); // A-B_novo

        // --- removeAresta ---
        System.out.println("\n=== removeAresta ===");
        System.out.println("Arestas de A antes: " + g.arestasIncidentes(vA).stream().map(a -> a.getValor().toString()).toList());
        g.removeAresta(aAB2);
        System.out.println("Arestas de A após remover paralela: " + g.arestasIncidentes(vA).stream().map(a -> a.getValor().toString()).toList());

        // --- removeVertice ---
        System.out.println("\n=== removeVertice ===");
        System.out.println("Vértices antes: " + g.vertices().stream().map(v -> v.getValor().toString()).toList());
        System.out.println("Arestas antes: " + g.arestas().stream().map(a -> a.getValor().toString()).toList());
        g.removeVertice(vC);
        System.out.println("Vértices após remover C: " + g.vertices().stream().map(v -> v.getValor().toString()).toList());
        System.out.println("Arestas após remover C: " + g.arestas().stream().map(a -> a.getValor().toString()).toList());
        // A-C e B-C devem ter sumido

        // --- oposto com erro ---
        System.out.println("\n=== oposto com vértice inválido ===");
        try {
            g.oposto(vC, aAB); // vC não incide sobre aAB
        } catch (IllegalArgumentException ex) {
            System.out.println("Exceção esperada: " + ex.getMessage());
        }
    }
}
