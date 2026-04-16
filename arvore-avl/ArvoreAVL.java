public class ArvoreAVL extends ArvoreBinaria {

    public ArvoreAVL() {
        super();
    }
    public NoAVL RSE(NoAVL no){
        NoAVL subAD = (NoAVL) no.getRightChild();
        NoAVL subAE = (NoAVL) subAD.getLeftChild();
        NoAVL paiInicial = (NoAVL) no.getParent();
        subAD.setLeftChild(no);
        no.setRightChild(subAE);
        subAD.setParent(paiInicial);
        if (paiInicial == null) {
            root = subAD;
        } else if (paiInicial.getLeftChild() == no) {
            paiInicial.setLeftChild(subAD);
        } else {
            paiInicial.setRightChild(subAD);
        }
        int FBN_novo = no.getFB() + 1 - Math.min(subAD.getFB(), 0);
        int FBA_novo = subAD.getFB() + 1 + Math.max(FBN_novo, 0);
        no.setFB(FBN_novo);
        subAD.setFB(FBA_novo);
        return subAD;
    }

    public NoAVL RSD(NoAVL no){
        NoAVL subAE = (NoAVL) no.getLeftChild();
        NoAVL subAD = (NoAVL) subAE.getRightChild();
        NoAVL paiInicial = (NoAVL) no.getParent();
        subAE.setRightChild(no);
        no.setLeftChild(subAD);
        subAE.setParent(paiInicial);
        if (paiInicial == null) {
            root = subAE;
        } else if (paiInicial.getRightChild() == no) {
            paiInicial.setRightChild(subAE);
        } else {
            paiInicial.setLeftChild(subAE);
        }
        int FBN_novo = no.getFB() - 1 - Math.max(subAE.getFB(), 0);
        int FBA_novo = subAE.getFB() - 1 + Math.min(FBN_novo, 0);
        no.setFB(FBN_novo);
        subAE.setFB(FBA_novo);
        return subAE;
    }

    public NoAVL RDE(NoAVL no){
        no.setRightChild(RSD((NoAVL) no.getRightChild()));
        return RSE(no);
    }

    public NoAVL RDD(NoAVL no){
        no.setLeftChild(RSE((NoAVL) no.getLeftChild()));
        return RSD(no);
    }

    private NoAVL rebalance(NoAVL n) {
        if (n.getFB() == 2) {
            if (((NoAVL) n.getLeftChild()).getFB() >= 0) {
                return RSD(n);
            } else {
                return RDD(n);
            }
        } else if (n.getFB() == -2) {
            if (((NoAVL) n.getRightChild()).getFB() <= 0) {
                return RSE(n);
            } else {
                return RDE(n);
            }
        }

        return n;
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
            if (pai.getFB() == 0) {
                break;
            }
            if (pai.getFB() == 2 || pai.getFB() == -2) {
                rebalance(pai);
                break;
            }
            n = pai;
            pai = (NoAVL) pai.getParent();
        }
    }

    @Override
    public void remove(int chave) {
        NoAVL node = (NoAVL) search(chave);
        if (node == null) return;

        if (node.getLeftChild() != null && node.getRightChild() != null) {
            NoAVL sucessor = (NoAVL) node.getRightChild();
            while (sucessor.getLeftChild() != null)
                sucessor = (NoAVL) sucessor.getLeftChild();
            node.setElement(sucessor.getElement());
            node = sucessor;
        }

        NoAVL pai   = (NoAVL) node.getParent();
        NoAVL filho;
        if (node.getLeftChild() != null) {
            filho = (NoAVL) node.getLeftChild();
        }
        else {
            filho = (NoAVL) node.getRightChild();
        }

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

        private void atualizarFBRemocao(NoAVL n, int variacao) {
        while (n != null) {
            n.setFB(n.getFB() + variacao);

            if (n.getFB() == 1 || n.getFB() == -1){
                break;
            }
            if (n.getFB() == 2 || n.getFB() == -2) {
                n = rebalance(n);
                if (n.getFB() == 1 || n.getFB() == -1) {
                    break;
                }
            }
            NoAVL parent = (NoAVL) n.getParent();
            if (parent == null) {
                break;
            }
            if (n == parent.getLeftChild()) {
                variacao = -1;
            } else {
                variacao = 1;
            }
            n = parent;
        }
    }

    @Override
    protected void preencherMatriz(No no, String[][] matriz, int nivel,
                                   int coluna, int alturaRestante) {
        if (no == null) return;
        NoAVL node = (NoAVL) no;
        String chaveComFB = node.getElement() + "[" + node.getFB() + "]";
        matriz[nivel][coluna] = String.format("%6s", chaveComFB);
        if (alturaRestante > 0) {
            int deslocamento = (int) Math.pow(2, alturaRestante - 1);
            if (no.getLeftChild() != null)
                preencherMatriz(no.getLeftChild(), matriz, nivel + 1, coluna - deslocamento, alturaRestante - 1);
            if (no.getRightChild() != null)
                preencherMatriz(no.getRightChild(), matriz, nivel + 1, coluna + deslocamento, alturaRestante - 1);
        }
    }
}