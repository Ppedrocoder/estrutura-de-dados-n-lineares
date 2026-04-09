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
        NoAVL x = (NoAVL) z.getRightChild();
        z.setRightChild(x.getLeftChild());
        x.setLeftChild(z);
        x.setParent(paiOriginal);
        return x;
    }
    private NoAVL girarDireita(NoAVL z) {
        NoAVL paiOriginal = (NoAVL) z.getParent();
        NoAVL x = (NoAVL) z.getLeftChild();
        z.setLeftChild(x.getRightChild());
        x.setRightChild(z);
        x.setParent(paiOriginal);
        return x;
    }
    private NoAVL rotacaoEsquerda(NoAVL z) {
        NoAVL y = (NoAVL) z.getRightChild();
        int fyAnt = y.getFB();
        NoAVL novaRaiz = girarEsquerda(z);
        if (fyAnt == -1) {
            z.setFB(0);  y.setFB(0);
        } else { // fyAnt == 0
            z.setFB(-1); y.setFB(1);
        }
        return novaRaiz;
    }
    private NoAVL rotacaoDireita(NoAVL z) {
        NoAVL y = (NoAVL) z.getLeftChild();
        int fyAnt = y.getFB();
        NoAVL novaRaiz = girarDireita(z);
        if (fyAnt == 1) {
            z.setFB(0);  y.setFB(0);
        } else { // fyAnt == 0
            z.setFB(1);  y.setFB(-1);
        }
        return novaRaiz;
    }
    private NoAVL rotacaoDuplaDireita(NoAVL z) {
        NoAVL y = (NoAVL) z.getLeftChild();
        NoAVL x = (NoAVL) y.getRightChild();
        int fxAnt = x.getFB(); // salvo ANTES de qualquer giro

        NoAVL novoEsq = girarEsquerda(y);
        novoEsq.setParent(z);
        z.setLeftChild(novoEsq);
        NoAVL novaRaiz = girarDireita(z);

        if (fxAnt > 0) {        // fxAnt == 1
            z.setFB(-1); y.setFB(0);
        } else if (fxAnt < 0) { // fxAnt == -1
            z.setFB(0);  y.setFB(1);
        } else {                 // fxAnt == 0
            z.setFB(0);  y.setFB(0);
        }
        x.setFB(0);

        return novaRaiz;
    }

    private NoAVL rotacaoDuplaEsquerda(NoAVL z) {
        NoAVL y = (NoAVL) z.getRightChild();
        NoAVL x = (NoAVL) y.getLeftChild();
        int fxAnt = x.getFB(); // salvo ANTES de qualquer giro

        NoAVL novoDir = girarDireita(y);
        novoDir.setParent(z);
        z.setRightChild(novoDir);
        NoAVL novaRaiz = girarEsquerda(z);

        if (fxAnt > 0) {        // fxAnt == 1
            z.setFB(0);  y.setFB(-1);
        } else if (fxAnt < 0) { // fxAnt == -1
            z.setFB(1);  y.setFB(0);
        } else {                 // fxAnt == 0
            z.setFB(0);  y.setFB(0);
        }
        x.setFB(0);

        return novaRaiz;
    }

    private NoAVL rebalance(NoAVL z) {
        if (z.getFB() == 2) {
            NoAVL y = (NoAVL) z.getLeftChild();
            if (y.getFB() >= 0) {
                z = rotacaoDireita(z);      // RDS
            } else {
                z = rotacaoDuplaDireita(z); // RDD
            }
        } else if (z.getFB() == -2) {
            NoAVL y = (NoAVL) z.getRightChild();
            if (y.getFB() <= 0) {
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

    private void atualizarFBRemocao(NoAVL n, int delta) {
        while (n != null) {
            n.setFB(n.getFB() + delta);

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
            delta = (n == parent.getLeftChild()) ? -1 : 1;
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
        NoAVL filho = (node.getLeftChild() != null)
                    ? (NoAVL) node.getLeftChild()
                    : (NoAVL) node.getRightChild();

        if (filho != null) filho.setParent(pai);

        if (pai == null) {
            root = filho;
        } else {
            int delta;
            if (node == pai.getLeftChild()) {
                pai.setLeftChild(filho);
                delta = -1;
            } else {
                pai.setRightChild(filho);
                delta = 1;
            }
            atualizarFBRemocao(pai, delta);
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