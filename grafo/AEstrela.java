package grafo;
import java.util.*;

public class AEstrela{

   public interface Heuristica {
        Integer estimar(Vertice v, Vertice fim);
    }
 
    public static void calcular(Grafo g, Vertice inicio, Vertice fim, Heuristica heuristica) {
        Map<Vertice, Integer>  gCusto  = new HashMap<>();
        Map<Vertice, Integer>  fCusto  = new HashMap<>();
        Map<Vertice, Vertice> prev    = new HashMap<>();
        Map<Vertice, Aresta>  prevA   = new HashMap<>();
        Set<Vertice>          fechado = new HashSet<>();
        List<String>          ordemVisita = new ArrayList<>();
 
        for (Vertice v : g.vertices()) {
            gCusto.put(v, Integer.MAX_VALUE);
            fCusto.put(v, Integer.MAX_VALUE);
        }
        gCusto.put(inicio, 0);
        fCusto.put(inicio, heuristica.estimar(inicio, fim));
 
        PriorityQueue<Vertice> aberto = new PriorityQueue<>(
                Comparator.comparingDouble(v -> fCusto.get(v))
        );
        aberto.add(inicio);
 
        long tempoInicio = System.nanoTime();
 
        while (!aberto.isEmpty()) {
            Vertice u = aberto.poll();
            if (fechado.contains(u)) continue;
            fechado.add(u);
            ordemVisita.add(u.getValor().toString());
            if (u.equals(fim)) break;
 
            for (Aresta a : g.arestasIncidentes(u)) {
                Vertice w = g.oposto(u, a);
                if (fechado.contains(w)) continue;
                int novoG = gCusto.get(u) + ((Number) a.getValor()).intValue();
                if (novoG < gCusto.get(w)) {
                    gCusto.put(w, novoG);
                    fCusto.put(w, novoG + heuristica.estimar(w, fim));
                    prev.put(w, u);
                    prevA.put(w, a);
                    aberto.add(w);
                }
            }
        }
 
        long tempoFim = System.nanoTime();
 
        System.out.println("|           ALGORITMO A* (A-estrela)           |");
 
        if (gCusto.get(fim) == Double.MAX_VALUE) {
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
            System.out.println("  Custo     : " + gCusto.get(fim).intValue());
        }
 
        System.out.printf("  Tempo     : %.4f ms%n", (tempoFim - tempoInicio) / 1_000_000.0);
        System.out.println("================================================");
    }
}
