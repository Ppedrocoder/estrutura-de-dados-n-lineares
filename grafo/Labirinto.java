package grafo;

import java.io.*;
import java.util.*;

public class Labirinto {
    private int[][] grid;
    public  final int linhas;
    public  final int colunas;
    private Celula partida;
    private Celula saida;

    public Labirinto(String arquivo) throws IOException {
        List<int[]> linhasDat = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(arquivo));
        String linha;
        while ((linha = br.readLine()) != null) {
            linha = linha.trim();
            if (linha.isEmpty()) continue;
            String[] parts = linha.split("\\s+");
            int[] row = new int[parts.length];
            for (int j = 0; j < parts.length; j++)
                row[j] = Integer.parseInt(parts[j]);
            linhasDat.add(row);
        }
        br.close();

        this.linhas  = linhasDat.size();
        this.colunas = linhasDat.get(0).length;
        this.grid    = new int[linhas][colunas];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                grid[i][j] = linhasDat.get(i)[j];
                if (grid[i][j] == 2) partida = new Celula(i, j, 2);
                if (grid[i][j] == 3) saida   = new Celula(i, j, 3);
            }
        }

        if (partida == null) throw new IllegalArgumentException("Labirinto sem ponto de partida (2).");
        if (saida   == null) throw new IllegalArgumentException("Labirinto sem saida (3).");
    }

    public Celula getPartida() { return partida; }
    public Celula getSaida()   { return saida;   }

    public Celula getCelula(int i, int j) {
        return new Celula(i, j, grid[i][j]);
    }

    // Vizinhos: cima, baixo, esquerda, direita
    public List<Celula> vizinhos(Celula c) {
        List<Celula> viz = new ArrayList<>();
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] d : dirs) {
            int ni = c.linha  + d[0];
            int nj = c.coluna + d[1];
            if (ni >= 0 && ni < linhas && nj >= 0 && nj < colunas) {
                Celula nova = getCelula(ni, nj);
                if (nova.eCaminhavel()) viz.add(nova);
            }
        }
        return viz;
    }

    // Imprime o labirinto com o caminho marcado com '.'
    public void imprimirComCaminho(List<Celula> caminho) {
        Set<String> set = new HashSet<>();
        if (caminho != null)
            for (Celula c : caminho)
                set.add(c.linha + "," + c.coluna);

        System.out.println();
        for (int i = 0; i < linhas; i++) {
            System.out.print("  ");
            for (int j = 0; j < colunas; j++) {
                String chave = i + "," + j;
                if      (grid[i][j] == 1) System.out.print("█ ");
                else if (grid[i][j] == 2) System.out.print("S ");
                else if (grid[i][j] == 3) System.out.print("E ");
                else if (set.contains(chave)) System.out.print(". ");
                else    System.out.print("  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Imprime o labirinto limpo
    public void imprimir() { imprimirComCaminho(null); }
}
