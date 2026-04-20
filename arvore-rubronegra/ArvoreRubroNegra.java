public class ArvoreRubroNegra extends ArvoreBinaria {
    public ArvoreRubroNegra(){
        super();
    }

    private boolean isRed(NoRN no) {
        return no != null && "V".equals(no.getColor());
    }

    private void setColor(NoRN no, String color) {
        if (no != null) {
            no.setColor(color);
        }
    }

    public NoRN RSE(NoRN no){
        NoRN subAD = (NoRN) no.getRightChild();
        NoRN subAE = (NoRN) subAD.getLeftChild();
        NoRN paiInicial = (NoRN) no.getParent();
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
        return subAD;
    }

    public NoRN RSD(NoRN no){
        NoRN subAE = (NoRN) no.getLeftChild();
        NoRN subAD = (NoRN) subAE.getRightChild();
        NoRN paiInicial = (NoRN) no.getParent();
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
        return subAE;
    }

    public NoRN RDE(NoRN no){
        no.setRightChild(RSD((NoRN) no.getRightChild()));
        return RSE(no);
    }

    public NoRN RDD(NoRN no){
        no.setLeftChild(RSE((NoRN) no.getLeftChild()));
        return RSD(no);
    }

    private NoRN rebalance(NoRN n) {
        while (n != null && n != root && isRed((NoRN) n.getParent())) {
            NoRN pai = (NoRN) n.getParent();
            NoRN avo = (NoRN) pai.getParent();

            if (avo == null) {
                break;
            }

            if (pai == avo.getLeftChild()) {
                NoRN tio = (NoRN) avo.getRightChild();

                if (isRed(tio)) {
                    setColor(pai, "P");
                    setColor(tio, "P");
                    setColor(avo, "V");
                    n = avo;
                } else {
                    if (n == pai.getRightChild()) {
                        RDD(avo);
                        NoRN novoTopo = (NoRN) avo.getParent();
                        setColor(novoTopo, "P");
                        setColor((NoRN) novoTopo.getLeftChild(), "V");
                        setColor((NoRN) novoTopo.getRightChild(), "V");
                        n = novoTopo;
                    } else {
                        RSD(avo);
                        NoRN novoTopo = (NoRN) avo.getParent();
                        setColor(novoTopo, "P");
                        setColor((NoRN) novoTopo.getLeftChild(), "V");
                        setColor((NoRN) novoTopo.getRightChild(), "V");
                        n = novoTopo;
                    }
                }
            } else {
                NoRN tio = (NoRN) avo.getLeftChild();

                if (isRed(tio)) {
                    setColor(pai, "P");
                    setColor(tio, "P");
                    setColor(avo, "V");
                    n = avo;
                } else {
                    if (n == pai.getLeftChild()) {
                        RDE(avo);
                        NoRN novoTopo = (NoRN) avo.getParent();
                        setColor(novoTopo, "P");
                        setColor((NoRN) novoTopo.getLeftChild(), "V");
                        setColor((NoRN) novoTopo.getRightChild(), "V");
                        n = novoTopo;
                    } else {
                        RSE(avo);
                        NoRN novoTopo = (NoRN) avo.getParent();
                        setColor(novoTopo, "P");
                        setColor((NoRN) novoTopo.getLeftChild(), "V");
                        setColor((NoRN) novoTopo.getRightChild(), "V");
                        n = novoTopo;
                    }
                }
            }
        }

        setColor((NoRN) root, "P");
        return n;
    }

    @Override
    public No insert(int chave){
        if (isEmpty()) {
            NoRN node = new NoRN(null, chave);
            node.setColor("P");
            this.root = node;
            this.size++;
            return this.root;
        }

        NoRN current = (NoRN) this.root;
        NoRN pai = null;
        while(current != null){
            pai = current;
            if (chave < (int) current.getElement()) {
                current = (NoRN) current.getLeftChild();
            } else {
                current = (NoRN) current.getRightChild();
            }
        }

        NoRN node = new NoRN(pai, chave);

        if (chave < (int) pai.getElement()) {
            pai.setLeftChild(node);
        } else {
            pai.setRightChild(node);
        }

        this.size++;
        rebalance(node);
        return node;
    }
    @Override
    public void remove(int chave){
        NoRN node = (NoRN) search(chave);
    }

    @Override
    protected void preencherMatriz(No no, String[][] matriz, int nivel,
                                   int coluna, int alturaRestante) {
        if (no == null) return;
        NoRN node = (NoRN) no;
        if ("V".equals(node.getColor())) {
            matriz[nivel][coluna] = String.format("%6s", node.getElement() + "[V]");
        } else {
            matriz[nivel][coluna] = String.format("%6s", node.getElement() + "[P]");
        }
        if (alturaRestante > 0) {
            int deslocamento = (int) Math.pow(2, alturaRestante - 1);
            if (no.getLeftChild() != null)
                preencherMatriz(no.getLeftChild(), matriz, nivel + 1, coluna - deslocamento, alturaRestante - 1);
            if (no.getRightChild() != null)
                preencherMatriz(no.getRightChild(), matriz, nivel + 1, coluna + deslocamento, alturaRestante - 1);
        }
    }
}
