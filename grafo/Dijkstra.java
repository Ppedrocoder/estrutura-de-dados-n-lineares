package grafo;

import java.util.*;

public class Dijkstra {

    public static List<Celula> calcular(Labirinto lab) {
        Celula inicio = lab.getPartida();
        Celula fim    = lab.getSaida();

        Map<Celula, Integer> dist  = new HashMap<>();
        Map<Celula, Celula>  prev  = new HashMap<>();
        Set<Celula>          visitados   = new HashSet<>();
        List<Celula>         ordemVisita = new ArrayList<>();

        dist.put(inicio, 0);

        PriorityQueue<Celula> fila = new PriorityQueue<>(
                Comparator.comparingInt(c -> dist.getOrDefault(c, Integer.MAX_VALUE))
        );
        fila.add(inicio);

        long tempoInicio = System.nanoTime();

        while (!fila.isEmpty()) {
            Celula u = fila.poll();
            if (visitados.contains(u)) continue;
            visitados.add(u);
            ordemVisita.add(u);

            if (u.equals(fim)) break;

            for (Celula w : lab.vizinhos(u)) {
                if (visitados.contains(w)) continue;
                int novaDist = dist.getOrDefault(u, Integer.MAX_VALUE) + 1;
                if (novaDist < dist.getOrDefault(w, Integer.MAX_VALUE)) {
                    dist.put(w, novaDist);
                    prev.put(w, u);
                    fila.add(w);
                }
            }
        }

        long tempoFim = System.nanoTime();

        // Reconstrói caminho
        List<Celula> caminho = new ArrayList<>();
        if (dist.containsKey(fim)) {
            Celula cur = fim;
            while (cur != null) {
                caminho.add(0, cur);
                cur = prev.get(cur);
            }
        }

        System.out.println("|           ALGORITMO DE DIJKSTRA              |");

        if (caminho.isEmpty()) {
            System.out.println("  Sem caminho encontrado.");
        } else {
            System.out.println("  Visitados : " + ordemVisita.size() + " celulas");
            System.out.print  ("  Caminho   : ");
            for (int i = 0; i < caminho.size(); i++) {
                Celula c = caminho.get(i);
                System.out.print(c);
                if (i < caminho.size() - 1) System.out.print(" -> ");
            }
            System.out.println();
            System.out.println("  Passos    : " + (caminho.size() - 1));
        }

        System.out.printf("  Tempo     : %.4f ms%n", (tempoFim - tempoInicio) / 1_000_000.0);
        System.out.println("================================================");

        lab.imprimirComCaminho(caminho);

        return caminho;
    }
}
