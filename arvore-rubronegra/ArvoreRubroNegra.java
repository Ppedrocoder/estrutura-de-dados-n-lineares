public class ArvoreRubroNegra extends ArvoreBinaria {
    public ArvoreRubroNegra(){
        super();
    }

    @Override
    public No insert(int chave){
        if (isEmpty()) {
            NoRN node = new NoRN(null, chave);
            node.setColor("black");
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
        if (node.getColor() == "V") {
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
