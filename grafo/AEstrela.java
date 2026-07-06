package grafo;

import java.util.*;

public class AEstrela {

    // Heuristica de Manhattan: distancia em linha reta sem diagonais
    private static int heuristica(Celula a, Celula b) {
        return Math.abs(a.linha - b.linha) + Math.abs(a.coluna - b.coluna);
    }

    public static List<Celula> calcular(Labirinto lab) {
        Celula inicio = lab.getPartida();
        Celula fim    = lab.getSaida();

        Map<Celula, Integer> gCusto = new HashMap<>();
        Map<Celula, Integer> fCusto = new HashMap<>();
        Map<Celula, Celula>  prev   = new HashMap<>();
        Set<Celula>          fechado     = new HashSet<>();
        List<Celula>         ordemVisita = new ArrayList<>();

        gCusto.put(inicio, 0);
        fCusto.put(inicio, heuristica(inicio, fim));

        PriorityQueue<Celula> aberto = new PriorityQueue<>(
                Comparator.comparingInt(c -> fCusto.getOrDefault(c, Integer.MAX_VALUE))
        );
        aberto.add(inicio);

        long tempoInicio = System.nanoTime();

        while (!aberto.isEmpty()) {
            Celula u = aberto.poll();
            if (fechado.contains(u)) continue;
            fechado.add(u);
            ordemVisita.add(u);

            if (u.equals(fim)) break;

            for (Celula w : lab.vizinhos(u)) {
                if (fechado.contains(w)) continue;
                int novoG = gCusto.getOrDefault(u, Integer.MAX_VALUE) + 1;
                if (novoG < gCusto.getOrDefault(w, Integer.MAX_VALUE)) {
                    gCusto.put(w, novoG);
                    fCusto.put(w, novoG + heuristica(w, fim));
                    prev.put(w, u);
                    aberto.add(w);
                }
            }
        }

        long tempoFim = System.nanoTime();

        // Reconstrói caminho
        List<Celula> caminho = new ArrayList<>();
        if (gCusto.containsKey(fim)) {
            Celula cur = fim;
            while (cur != null) {
                caminho.add(0, cur);
                cur = prev.get(cur);
            }
        }

        System.out.println("|           ALGORITMO A* (A-estrela)           |");

        if (caminho.isEmpty()) {
            System.out.println("  Sem caminho encontrado.");
        } else {
            System.out.println("  Heuristica: distancia de Manhattan");
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
