public class TesteRubroNegra {
    public static void main(String[] args) {
        runCase("RSD", new int[]{30, 20, 10}, new int[]{20, 10, 30});
        runCase("RSE", new int[]{10, 20, 30}, new int[]{20, 30, 10});
        runCase("RDD", new int[]{30, 10, 20}, new int[]{10, 20, 30});
        runCase("RDE", new int[]{10, 30, 20}, new int[]{30, 20, 10});
        runCase("Misto", new int[]{41, 38, 31, 12, 19, 8, 50, 60, 55, 54}, new int[]{12, 60, 38, 54, 41, 8, 31, 55, 50, 19});

        System.out.println("Todos os testes de insercao e remocao passaram.");
    }

    private static void runCase(String nome, int[] insercoes, int[] remocoes) {
        ArvoreRubroNegra arvore = new ArvoreRubroNegra();
        System.out.println("\n===== Caso " + nome + " =====");
        int tamanhoEsperado = 0;

        for (int v : insercoes) {
            arvore.insert(v);
            tamanhoEsperado++;
            System.out.println("\nInserindo: " + v);
            arvore.mostrarArvoreMatriz(arvore.root());
            validateOrThrow(arvore, nome + " apos inserir " + v);
            validateSizeOrThrow(arvore, tamanhoEsperado, nome + " apos inserir " + v);
        }

        for (int v : remocoes) {
            arvore.remove(v);
            tamanhoEsperado--;
            System.out.println("\nRemovendo: " + v);
            arvore.mostrarArvoreMatriz(arvore.root());
            validateOrThrow(arvore, nome + " apos remover " + v);
            validateSizeOrThrow(arvore, tamanhoEsperado, nome + " apos remover " + v);
            validateRemovedOrThrow(arvore, v, nome + " apos remover " + v);
        }

        if (!arvore.isEmpty()) {
            throw new IllegalStateException("Arvore deveria estar vazia ao fim de " + nome);
        }

        System.out.println("OK: " + nome);
    }

    private static void validateRemovedOrThrow(ArvoreRubroNegra arvore, int valor, String contexto) {
        if (arvore.search(valor) != null) {
            throw new IllegalStateException("Elemento " + valor + " ainda encontrado em " + contexto);
        }
    }

    private static void validateSizeOrThrow(ArvoreRubroNegra arvore, int esperado, String contexto) {
        if (arvore.size() != esperado) {
            throw new IllegalStateException("Tamanho incorreto em " + contexto + ": esperado " + esperado + ", obtido " + arvore.size());
        }
    }

    private static void validateOrThrow(ArvoreRubroNegra arvore, String contexto) {
        NoRN raiz = (NoRN) arvore.root();

        if (raiz == null) {
            return;
        }

        if (!"P".equals(raiz.getColor())) {
            throw new IllegalStateException("Raiz nao-preta em " + contexto);
        }

        if (hasRedRedViolation(raiz)) {
            throw new IllegalStateException("Violacao vermelho-vermelho em " + contexto);
        }

        int blackHeight = blackHeightOrFail(raiz);
        if (blackHeight < 0) {
            throw new IllegalStateException("Altura negra inconsistente em " + contexto);
        }
    }

    private static boolean hasRedRedViolation(NoRN no) {
        if (no == null) {
            return false;
        }

        if ("V".equals(no.getColor())) {
            NoRN esq = (NoRN) no.getLeftChild();
            NoRN dir = (NoRN) no.getRightChild();
            if ((esq != null && "V".equals(esq.getColor())) || (dir != null && "V".equals(dir.getColor()))) {
                return true;
            }
        }

        return hasRedRedViolation((NoRN) no.getLeftChild()) || hasRedRedViolation((NoRN) no.getRightChild());
    }

    private static int blackHeightOrFail(NoRN no) {
        if (no == null) {
            return 1;
        }

        int esquerda = blackHeightOrFail((NoRN) no.getLeftChild());
        int direita = blackHeightOrFail((NoRN) no.getRightChild());

        if (esquerda < 0 || direita < 0 || esquerda != direita) {
            return -1;
        }

        int atual = "P".equals(no.getColor()) ? 1 : 0;
        return esquerda + atual;
    }
}
