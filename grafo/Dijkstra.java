package grafo;


import java.util.*;

public class Dijkstra {

    public static void calcular(Grafo g, Vertice inicio, Vertice fim) {
        Map<Vertice, Double>  dist  = new HashMap<>();
        Map<Vertice, Vertice> prev  = new HashMap<>();
        Map<Vertice, Aresta>  prevA = new HashMap<>();
        Set<Vertice>          visitados = new HashSet<>();
        List<String>          ordemVisita = new ArrayList<>();
 
        for (Vertice v : g.vertices())
            dist.put(v, Double.MAX_VALUE);
        dist.put(inicio, 0.0);
 
        PriorityQueue<Vertice> fila = new PriorityQueue<>(
                Comparator.comparingDouble(v -> dist.get(v))
        );
        fila.add(inicio);
 
        long tempoInicio = System.nanoTime();
 
        while (!fila.isEmpty()) {
            Vertice u = fila.poll();
            if (visitados.contains(u)) continue;
            visitados.add(u);
            ordemVisita.add(u.getValor().toString());
            if (u.equals(fim)) break;
 
            for (Aresta a : g.arestasIncidentes(u)) {
                Vertice w = g.oposto(u, a);
                if (visitados.contains(w)) continue;
                double novaDist = dist.get(u) + ((Number) a.getValor()).doubleValue();
                if (novaDist < dist.get(w)) {
                    dist.put(w, novaDist);
                    prev.put(w, u);
                    prevA.put(w, a);
                    fila.add(w);
                }
            }
        }
 
        long tempoFim = System.nanoTime();

        System.out.println("|           ALGORITMO DE DIJKSTRA              |");
 
        if (dist.get(fim) == Double.MAX_VALUE) {
            System.out.println("  Sem caminho entre " + inicio.getValor() + " e " + fim.getValor() + ".");
        } else {
            LinkedList<String> caminho = new LinkedList<>();
            Vertice cur = fim;
            while (cur != null && !cur.equals(inicio)) {
                Aresta a = prevA.get(cur);
                caminho.addFirst(cur.getValor() + "(+" + a.getValor() + ")");
                cur = prev.get(cur);
            }
            caminho.addFirst(inicio.getValor().toString());
 
            System.out.println("  Visitados : " + String.join(" -> ", ordemVisita));
            System.out.println("  Caminho   : " + String.join(" -> ", caminho));
            System.out.println("  Custo     : " + dist.get(fim).intValue());
        }
 
        System.out.printf("  Tempo     : %.4f ms%n", (tempoFim - tempoInicio) / 1_000_000.0);
        System.out.println("================================================");
    }
}
