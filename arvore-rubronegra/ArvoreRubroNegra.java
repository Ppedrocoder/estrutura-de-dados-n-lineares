public class ArvoreRubroNegra extends ArvoreBinaria {
    public ArvoreRubroNegra(){
        super();
    }

    private boolean isRed(NoRN no) {
        return "V".equals(getColor(no));
    }

    private void setColor(NoRN no, String color) {
        if (no != null) {
            no.setColor(color);
        }
    }

    private String getColor(NoRN no) {
        if (no == null) {
            return "P";
        }
        return no.getColor();
    }

    private NoRN getLeftNo(NoRN no) {
        if (no == null) {
            return null;
        }
        return (NoRN) no.getLeftChild();
    }

    private NoRN getRightNo(NoRN no) {
        if (no == null) {
            return null;
        }
        return (NoRN) no.getRightChild();
    }

    private void transplantar(NoRN noOrigem, NoRN noDestino) {
        NoRN pai = (NoRN) noOrigem.getParent();
        if (pai == null) {
            root = noDestino;
            if (noDestino != null) {
                noDestino.setParent(null);
            }
        } else if (noOrigem == pai.getLeftChild()) {
            pai.setLeftChild(noDestino);
        } else {
            pai.setRightChild(noDestino);
        }
    }

    public NoRN getMin(No no) {
        NoRN current = (NoRN) no;
        while (getLeftNo(current) != null) {
            current = getLeftNo(current);
        }
        return current;
    }
    public NoRN RSE(NoRN no){
        NoRN subAD = getRightNo(no);
        NoRN subAE = getLeftNo(subAD);
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
        NoRN subAE = getLeftNo(no);
        NoRN subAD = getRightNo(subAE);
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
        no.setRightChild(RSD(getRightNo(no)));
        return RSE(no);
    }

    public NoRN RDD(NoRN no){
        no.setLeftChild(RSE(getLeftNo(no)));
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
                NoRN tio = getRightNo(avo);

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
                        setColor(getLeftNo(novoTopo), "V");
                        setColor(getRightNo(novoTopo), "V");
                        n = novoTopo;
                    } else {
                        RSD(avo);
                        NoRN novoTopo = (NoRN) avo.getParent();
                        setColor(novoTopo, "P");
                        setColor(getLeftNo(novoTopo), "V");
                        setColor(getRightNo(novoTopo), "V");
                        n = novoTopo;
                    }
                }
            } else {
                NoRN tio = getLeftNo(avo);

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
                        setColor(getLeftNo(novoTopo), "V");
                        setColor(getRightNo(novoTopo), "V");
                        n = novoTopo;
                    } else {
                        RSE(avo);
                        NoRN novoTopo = (NoRN) avo.getParent();
                        setColor(novoTopo, "P");
                        setColor(getLeftNo(novoTopo), "V");
                        setColor(getRightNo(novoTopo), "V");
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
                current = getLeftNo(current);
            } else {
                current = getRightNo(current);
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
        NoRN noRemovido = (NoRN) search(chave);
        if (noRemovido == null) {
            return;
        }

        NoRN noSubstituto = noRemovido;
        String corOriginal = getColor(noSubstituto);
        NoRN noAjuste;
        NoRN paiNoAjuste;
        boolean ajusteEhFilhoEsquerdo;

        if (getLeftNo(noRemovido) == null) {
            noAjuste = getRightNo(noRemovido);
            paiNoAjuste = (NoRN) noRemovido.getParent();
            ajusteEhFilhoEsquerdo = paiNoAjuste != null && noRemovido == paiNoAjuste.getLeftChild();
            transplantar(noRemovido, noAjuste);
        } else if (getRightNo(noRemovido) == null) {
            noAjuste = getLeftNo(noRemovido);
            paiNoAjuste = (NoRN) noRemovido.getParent();
            ajusteEhFilhoEsquerdo = paiNoAjuste != null && noRemovido == paiNoAjuste.getLeftChild();
            transplantar(noRemovido, noAjuste);
        } else {
            noSubstituto = getMin(getRightNo(noRemovido));
            corOriginal = getColor(noSubstituto);
            noAjuste = getRightNo(noSubstituto);

            if (noSubstituto.getParent() == noRemovido) {
                paiNoAjuste = noSubstituto;
                ajusteEhFilhoEsquerdo = false;
            } else {
                paiNoAjuste = (NoRN) noSubstituto.getParent();
                ajusteEhFilhoEsquerdo = paiNoAjuste != null && noSubstituto == paiNoAjuste.getLeftChild();
                transplantar(noSubstituto, noAjuste);
                noSubstituto.setRightChild(getRightNo(noRemovido));
            }

            transplantar(noRemovido, noSubstituto);
            noSubstituto.setLeftChild(getLeftNo(noRemovido));
            noSubstituto.setColor(getColor(noRemovido));
        }

        size--;

        if ("P".equals(corOriginal)) {
            rebalanceRemocao(noAjuste, paiNoAjuste, ajusteEhFilhoEsquerdo);
        }

        if (root != null) {
            setColor((NoRN) root, "P");
        }
    }

    public NoRN rebalanceRemocao(NoRN no, NoRN pai, boolean noEhFilhoEsquerdo) {
        while (no != root && "P".equals(getColor(no))) {
            if (pai == null) {
                break;
            }

            boolean ehEsquerdo;
            if (no != null) {
                ehEsquerdo = no == pai.getLeftChild();
            } else {
                ehEsquerdo = noEhFilhoEsquerdo;
            }

            if (ehEsquerdo) {
                NoRN irmao = getRightNo(pai);

                if (isRed(irmao)) {
                    setColor(irmao, "P");
                    setColor(pai, "V");
                    RSE(pai);
                    irmao = getRightNo(pai);
                }

                if ("P".equals(getColor(getLeftNo(irmao)))
                    && "P".equals(getColor(getRightNo(irmao)))) {
                    setColor(irmao, "V");
                    no = pai;
                    pai = (NoRN) no.getParent();
                    noEhFilhoEsquerdo = pai != null && no == pai.getLeftChild();
                } else {
                    if ("P".equals(getColor(getRightNo(irmao)))) {
                        setColor(getLeftNo(irmao), "P");
                        setColor(irmao, "V");
                        if (irmao != null) {
                            RSD(irmao);
                        }
                        irmao = getRightNo(pai);
                    }

                    setColor(irmao, getColor(pai));
                    setColor(pai, "P");
                    setColor(getRightNo(irmao), "P");
                    RSE(pai);
                    no = (NoRN) root;
                    pai = null;
                }
            } else {
                NoRN irmao = getLeftNo(pai);

                if (isRed(irmao)) {
                    setColor(irmao, "P");
                    setColor(pai, "V");
                    RSD(pai);
                    irmao = getLeftNo(pai);
                }

                if ("P".equals(getColor(getLeftNo(irmao)))
                    && "P".equals(getColor(getRightNo(irmao)))) {
                    setColor(irmao, "V");
                    no = pai;
                    pai = (NoRN) no.getParent();
                    noEhFilhoEsquerdo = pai != null && no == pai.getLeftChild();
                } else {
                    if ("P".equals(getColor(getLeftNo(irmao)))) {
                        setColor(getRightNo(irmao), "P");
                        setColor(irmao, "V");
                        if (irmao != null) {
                            RSE(irmao);
                        }
                        irmao = getLeftNo(pai);
                    }

                    setColor(irmao, getColor(pai));
                    setColor(pai, "P");
                    setColor(getLeftNo(irmao), "P");
                    RSD(pai);
                    no = (NoRN) root;
                    pai = null;
                }
            }
        }

        setColor(no, "P");
        if (root != null) {
            setColor((NoRN) root, "P");
        }
        return no;
    }

    @Override
    protected void preencherMatriz(No no, String[][] matriz, int nivel,
                                   int coluna, int alturaRestante) {
        if (no == null) return;
        NoRN node = (NoRN) no;
        if ("V".equals(getColor(node))) {
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
