package ArvoreBinaria;

public class teste {
    public static void main(String[] args) {

        ArvoreBinaria arvore = new ArvoreBinaria();
        System.out.println("Size: " + arvore.size());
        System.out.println("=== Inserindo elementos ===");
        arvore.insert(50);
        arvore.insert(30);
        arvore.insert(70);
        arvore.insert(20);
        arvore.insert(40);
        arvore.insert(60);
        arvore.insert(80);
        System.out.println("Arvore:");
        arvore.mostrarArvoreMatriz(arvore.root());
        System.out.println("\n=== Tamanho da árvore ===");
        System.out.println("Size: " + arvore.size());

        System.out.println("\n=== Percurso INORDER (ordenado) ===");
        arvore.inorder(arvore.root());

        System.out.println("\n=== Percurso PREORDER ===");
        arvore.preorder(arvore.root());

        System.out.println("\n=== Percurso POSORDER ===");
        arvore.posorder(arvore.root());

        System.out.println("\n=== Testando busca ===");
        int chaveBusca = 40;
        No resultado = arvore.search(chaveBusca);
        if (resultado != null)
            System.out.println("Encontrado: " + resultado.getElement());
        else
            System.out.println("Elemento " + chaveBusca + " não encontrado.");

        System.out.println("\n=== Altura da árvore ===");
        System.out.println("Altura: " + arvore.height(arvore.root()));

        System.out.println("\n=== Profundidade chave 70 ===");
        System.out.println("Profundidade: " + arvore.depth(arvore.search(70)));


        System.out.println("\n=== Removendo 20 (nó folha) ===");
        arvore.remove(20);
        arvore.mostrarArvoreMatriz(arvore.root());
        System.out.println("\n=== Percurso INORDER após remoção ===");
        arvore.inorder(arvore.root());

        System.out.println("\n=== Removendo 30 (nó com 1 filho) ===");
        arvore.remove(30);
        arvore.mostrarArvoreMatriz(arvore.root());
        System.out.println("\n=== Percurso INORDER após remoção ===");
        arvore.inorder(arvore.root());

        System.out.println("\n=== Removendo 50 (nó com 2 filhos) ===");
        arvore.remove(50);
        arvore.mostrarArvoreMatriz(arvore.root());
        System.out.println("\n=== Percurso INORDER após remoção ===");
        arvore.inorder(arvore.root());

        System.out.println("\n=== Tamanho final ===");
        System.out.println("Size: " + arvore.size());
        System.out.println("\n=== Elemento da raiz ===");
        System.out.println(arvore.root().getElement());
    }
}
