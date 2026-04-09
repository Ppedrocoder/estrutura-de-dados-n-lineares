public class TesteAVL {
    public static void main(String[] args) {
        ArvoreAVL avl = new ArvoreAVL();

        System.out.println("===== INSERÇÃO =====");

        int[] valores = {30, 20, 40, 10, 25, 35, 50, 5, 15, 27};

        for (int v : valores) {
            System.out.println("\nInserindo: " + v);
            avl.insert(v);
            avl.mostrarArvoreMatriz(avl.root());
        }

        System.out.println("\n===== REMOÇÃO =====");

        int[] remover = {50, 40, 30};

        for (int v : remover) {
            System.out.println("\nRemovendo: " + v);
            avl.remove(v);
            avl.mostrarArvoreMatriz(avl.root());
        }

        System.out.println("\n===== VERIFICAÇÃO FINAL =====");
        verificarAVL((NoAVL) avl.root());
    }

    // 🔍 Verificador de AVL
    public static int verificarAVL(NoAVL no) {
        if (no == null) return -1;

        int hEsq = verificarAVL((NoAVL) no.getLeftChild());
        int hDir = verificarAVL((NoAVL) no.getRightChild());

        int fbCalculado = hEsq - hDir;

        if (fbCalculado != no.getFB()) {
            System.out.println("❌ ERRO FB no nó " + no.getElement() +
                    " | FB armazenado: " + no.getFB() +
                    " | FB real: " + fbCalculado);
        }

        if (Math.abs(fbCalculado) > 1) {
            System.out.println("❌ DESBALANCEADO no nó " + no.getElement());
        }

        return 1 + Math.max(hEsq, hDir);
    }
}
