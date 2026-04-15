public class ArvoreAVL extends ArvoreBinaria {

    public ArvoreAVL() {
        super();
    }
    private void atualizarPai(NoAVL n) {
        if (n.getParent() == null) {
            root = n;
        } 
        else {
            No pai = n.getParent();
            if ((int) n.getElement() < (int) pai.getElement()) {
                pai.setLeftChild(n);
            } 
            else {
                pai.setRightChild(n);
            }
        }
    }
    private NoAVL girarEsquerda(NoAVL z) {
        NoAVL paiOriginal = (NoAVL) z.getParent();
        NoAVL irmaoDireito = (NoAVL) z.getRightChild();
        z.setRightChild(irmaoDireito.getLeftChild());
        irmaoDireito.setLeftChild(z);
        irmaoDireito.setParent(paiOriginal);
        return irmaoDireito;
    }
    private NoAVL girarDireita(NoAVL z) {
        NoAVL paiOriginal = (NoAVL) z.getParent();
        NoAVL filhoEsquerdo = (NoAVL) z.getLeftChild();
        z.setLeftChild(filhoEsquerdo.getRightChild());
        filhoEsquerdo.setRightChild(z);
        filhoEsquerdo.setParent(paiOriginal);
        return filhoEsquerdo;
    }
    private NoAVL rotacaoEsquerda(NoAVL z) {
        NoAVL fihoDireito = (NoAVL) z.getRightChild();
        int fbFDAnt = fihoDireito.getFB();
        NoAVL novaRaiz = girarEsquerda(z);
        if (fbFDAnt == -1) {
            z.setFB(0);  fihoDireito.setFB(0);
        } else { // fbFDAnt == 0
            z.setFB(-1); fihoDireito.setFB(1);
        }
        return novaRaiz;
    }
    private NoAVL rotacaoDireita(NoAVL z) {
        NoAVL filhoEsquerdo = (NoAVL) z.getLeftChild();
        int fbFEAnt = filhoEsquerdo.getFB();
        NoAVL novaRaiz = girarDireita(z);
        if (fbFEAnt == 1) {
            z.setFB(0);  filhoEsquerdo.setFB(0);
        } else { // fbFEAnt == 0
            z.setFB(1);  filhoEsquerdo.setFB(-1);
        }
        return novaRaiz;
    }
    private NoAVL rotacaoDuplaDireita(NoAVL z) {
        NoAVL filhoEsquerdo = (NoAVL) z.getLeftChild();
        NoAVL sobrinhoDireito = (NoAVL) filhoEsquerdo.getRightChild();
        int fbSDAnt = sobrinhoDireito.getFB(); // salvo ANTES de qualquer giro

        NoAVL novoEsq = girarEsquerda(filhoEsquerdo);
        novoEsq.setParent(z);
        z.setLeftChild(novoEsq);
        NoAVL novaRaiz = girarDireita(z);

        if (fbSDAnt > 0) {        // fbSDAnt == 1
            z.setFB(-1); sobrinhoDireito.setFB(0);
        } else if (fbSDAnt < 0) { // fbSDAnt == -1
            z.setFB(0);  sobrinhoDireito.setFB(1);
        } else {                 // fbSDAnt == 0
            z.setFB(0);  sobrinhoDireito.setFB(0);
        }
        sobrinhoDireito.setFB(0);

        return novaRaiz;
    }

    private NoAVL rotacaoDuplaEsquerda(NoAVL z) {
        NoAVL filhoDireito = (NoAVL) z.getRightChild();
        NoAVL sobrinhoEsquerdo = (NoAVL) filhoDireito.getLeftChild();
        int fbSEAnt = sobrinhoEsquerdo.getFB(); // salvo ANTES de qualquer giro

        NoAVL novoDir = girarDireita(filhoDireito);
        novoDir.setParent(z);
        z.setRightChild(novoDir);
        NoAVL novaRaiz = girarEsquerda(z);

        if (fbSEAnt > 0) {        // fbSEAnt == 1
            z.setFB(0);  sobrinhoEsquerdo.setFB(-1);
        } else if (fbSEAnt < 0) { // fbSEAnt == -1
            z.setFB(1);  sobrinhoEsquerdo.setFB(0);
        } else {                 // fbSEAnt == 0
            z.setFB(0);  sobrinhoEsquerdo.setFB(0);
        }
        sobrinhoEsquerdo.setFB(0);

        return novaRaiz;
    }

    private NoAVL rebalance(NoAVL z) {
        if (z.getFB() == 2) {
            NoAVL filhoEsquerdo = (NoAVL) z.getLeftChild();
            if (filhoEsquerdo.getFB() >= 0) {
                z = rotacaoDireita(z);      // RDS
            } else {
                z = rotacaoDuplaDireita(z); // RDD
            }
        } else if (z.getFB() == -2) {
            NoAVL filhoDireito = (NoAVL) z.getRightChild();
            if (filhoDireito.getFB() <= 0) {
                z = rotacaoEsquerda(z);     // RES
            } else {
                z = rotacaoDuplaEsquerda(z);// RDE
            }
        }
        atualizarPai(z);
        return z;
    }

    @Override
    public No insert(int chave) {
        if (isEmpty()) {
            root = new NoAVL(null, chave);
            size++;
            return root;
        }
        No current = root;
        No pai = null;
        while (current != null) {
            pai = current;
            if (chave < (int) current.getElement()) {
                current = current.getLeftChild();
            } else {
                current = current.getRightChild();
            }
        }
        NoAVL novo = new NoAVL(pai, chave);
        if (chave < (int) pai.getElement()) {
            pai.setLeftChild(novo);
        } else {
            pai.setRightChild(novo);
        }
        size++;
        atualizarFBInsercao(novo);
        return novo;
    }

    private void atualizarFBInsercao(NoAVL n) {
        NoAVL pai = (NoAVL) n.getParent();
        while (pai != null) {
            if (n == pai.getLeftChild()) {
                pai.setFB(pai.getFB() + 1);
            } else {
                pai.setFB(pai.getFB() - 1);
            }
            if (pai.getFB() == 0) break;
            if (pai.getFB() == 2 || pai.getFB() == -2) {
                rebalance(pai);
                break;
            }
            n = pai;
            pai = (NoAVL) pai.getParent();
        }
    }

    private void atualizarFBRemocao(NoAVL n, int variacao) {
        while (n != null) {
            n.setFB(n.getFB() + variacao);

            // FB == +-1: altura nao mudou, para
            if (n.getFB() == 1 || n.getFB() == -1) break;

            // FB == +-2: rebalanceia
            if (n.getFB() == 2 || n.getFB() == -2) {
                n = rebalance(n);
                // FB == +-1 apos rotacao: altura nao diminuiu, para
                if (n.getFB() == 1 || n.getFB() == -1) break;
                // FB == 0 apos rotacao: altura diminuiu, continua subindo
            }

            // FB == 0: altura diminuiu, sobe
            NoAVL parent = (NoAVL) n.getParent();
            if (parent == null) break;
            if (n == parent.getLeftChild()) {
                variacao = -1;
            } else {
                variacao = 1;
            }
            n = parent;
        }
    }

    @Override
    public void remove(int chave) {
        NoAVL node = (NoAVL) search(chave);
        if (node == null) return;

        // Caso 1: dois filhos → substitui pelo sucessor em-ordem
        if (node.getLeftChild() != null && node.getRightChild() != null) {
            NoAVL sucessor = (NoAVL) node.getRightChild();
            while (sucessor.getLeftChild() != null)
                sucessor = (NoAVL) sucessor.getLeftChild();
            node.setElement(sucessor.getElement());
            node = sucessor;
        }

        // Casos 2 e 3: 0 ou 1 filho
        NoAVL pai   = (NoAVL) node.getParent();
        NoAVL filho = (node.getLeftChild() != null) ? (NoAVL) node.getLeftChild() : (NoAVL) node.getRightChild();

        if (filho != null) filho.setParent(pai);

        if (pai == null) {
            root = filho;
        } else {
            int variacao;
            if (node == pai.getLeftChild()) {
                pai.setLeftChild(filho);
                variacao = -1;
            } else {
                pai.setRightChild(filho);
                variacao = 1;
            }
            atualizarFBRemocao(pai, variacao);
        }
        size--;
    }

    @Override
    protected void preencherMatriz(No no, String[][] matriz, int nivel,
                                   int coluna, int alturaRestante) {
        if (no == null) return;
        NoAVL node = (NoAVL) no;
        String chaveComFB = node.getElement() + "(" + node.getFB() + ")";
        matriz[nivel][coluna] = String.format("%6s", chaveComFB);
        if (alturaRestante > 0) {
            int deslocamento = (int) Math.pow(2, alturaRestante - 1);
            if (no.getLeftChild() != null)
                preencherMatriz(no.getLeftChild(), matriz, nivel + 1,
                        coluna - deslocamento, alturaRestante - 1);
            if (no.getRightChild() != null)
                preencherMatriz(no.getRightChild(), matriz, nivel + 1,
                        coluna + deslocamento, alturaRestante - 1);
        }
    }
}