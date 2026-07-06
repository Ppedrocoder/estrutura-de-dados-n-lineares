package grafo;
public class testeAlgs {
    public static void main(String[] args) throws Exception {
        String arquivo = args.length > 0 ? args[0] : "labirinto.dat";

        System.out.println("Carregando labirinto: " + arquivo);
        Labirinto lab = new Labirinto(arquivo);

        System.out.println();
        System.out.println("=== LABIRINTO ===");
        System.out.println("  Tamanho  : " + lab.linhas + "x" + lab.colunas);
        System.out.println("  Partida  : " + lab.getPartida());
        System.out.println("  Saida    : " + lab.getSaida());
        System.out.println();
        System.out.println("Legenda: S=partida  E=saida  █=parede  .=caminho");
        lab.imprimir();

        System.out.println();

        // Dijkstra
        Dijkstra.calcular(lab);

        System.out.println();

        // A*
        AEstrela.calcular(lab);
    }
}