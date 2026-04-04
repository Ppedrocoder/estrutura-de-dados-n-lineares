package ArvoreBinaria;

import java.util.Iterator;
import java.util.ArrayList;
import static java.lang.Math.max;
import java.util.List;

public class ArvoreBinaria {
    private No root;
    private int size;
    public ArvoreBinaria() {
        this.root = null;
        this.size = 0;
    }
    public int size() {
        return this.size;
    }
    public boolean isEmpty(){
        return this.size == 0;
    }
    public void inorder(No raiz){
        if (raiz == null) {
            return;
        }
        inorder(raiz.getLeftChild());
        System.out.println(raiz.getElement());
        inorder(raiz.getRightChild());
    }
    public void posorder(No raiz){
        if( raiz == null) {
            return;
        }
        posorder(raiz.getLeftChild());
        posorder(raiz.getRightChild());
        System.out.println(raiz.getElement());
    }
    public void preorder(No raiz){
        if (raiz == null) {
            return;
        }
        System.out.println(raiz.getElement()); 
        preorder(raiz.getLeftChild());
        preorder(raiz.getRightChild());
    }
    public void preorderList(No raiz, List<No> nos){
        if (raiz == null) {
            return;
        }
        nos.add(raiz);
        preorderList(raiz.getLeftChild(), nos);
        preorderList(raiz.getRightChild(), nos);
    }
    public int height(No n){
        if (n == null) {
            return -1;
        }
        if (isExternal(n)) {
            return 0;
        }
        else {
            return 1 + max(height(leftchild(n)), height(rightchild(n)));
        }
    }
    public int depth(No n){
        if (isRoot(n)) {
            return 0;
        }
        else {
            return 1 + depth(parent(n));
        }
    }
    public Iterator<Object> elements(){
        List<Object> elements = new ArrayList<>();
        Iterator<No> nos = nos();
        while (nos.hasNext()) {
            elements.add(nos.next().getElement());
        }
        return elements.iterator();
    }
    public Iterator<No> nos(){
        List<No> nos = new ArrayList<>();
        preorderList(this.root, nos);
        return nos.iterator();
    }
    public No root() {
        return this.root;
    }
    public No parent(No n){
        return n.getParent();
    }
    public No leftchild(No n){
        return n.getLeftChild();
    }
    public No rightchild(No n){
        return n.getRightChild();
    }
    public boolean isInternal(No n){
        return hasLeft(n) || hasRight(n);
    }
    public boolean isExternal(No n){
        return !hasLeft(n) && !hasRight(n);
    }
    public boolean isRoot(No n){
        return n.getParent() == null;
    }
    public boolean hasLeft(No n){
        return n.getLeftChild() != null;
    }
    public boolean hasRight(No n){
        return n.getRightChild() != null;
    }
    public void replace(No n, Object o){
        n.setElement(o);
    }
    public No insert(int chave){
        if (isEmpty()) {
            this.root = new No(null, chave);
            this.size++;
            return this.root;
        }
        No current = this.root;
        No pai = null;
        while(current != null){
            pai = current;
            if (chave < (int) current.getElement()) {
                current = current.getLeftChild();
            } else {
                current = current.getRightChild();
            }
        }
        No no = new No(pai, chave);
        if (chave < (int) pai.getElement()) {
            pai.setLeftChild(no);
        } else {
            pai.setRightChild(no);
        }
        this.size++;
        return no;
    }
    public No search(int chave){
        No current = this.root;
        while(current != null && (int) current.getElement() != chave){
            if (chave < (int) current.getElement()) {
                current = current.getLeftChild();
            } else {
                current = current.getRightChild();
            }
        }
        return current;
    }
    public void remove(int chave){
        No pai = null;
        No current = this.root;

        while (current != null && (int) current.getElement() != chave) {
            pai = current;
            if (chave < (int) current.getElement()) {
                current = current.getLeftChild();
            } else {
                current = current.getRightChild();
            }
        }
        if (current ==  null) {
            return;
        }

        if (current.getLeftChild() == null && current.getRightChild() == null) {
            if (current == this.root) {
                this.root = null;
            }
            else if (pai.getLeftChild() == current) {
                pai.setLeftChild(null);
            }
            else {
                pai.setRightChild(null);
            }
            this.size--;
            return;
        }
        if (current.getLeftChild() == null || current.getRightChild() == null) {
            No filho;
            if (current.getLeftChild() != null) {
                filho = current.getLeftChild();
            } else {
                filho = current.getRightChild();
            }
            if (current == this.root) {
                this.root = filho;
                filho.setParent(null);
            }
            else if (pai.getLeftChild() == current) {
                pai.setLeftChild(filho);
            }
            else {
                pai.setRightChild(filho);
            }
            this.size--;
            return;
        }
        No Paisucessor = current;
        No sucessor = current.getRightChild();
        while (sucessor.getLeftChild() != null) {
            Paisucessor = sucessor;
            sucessor = sucessor.getLeftChild();
        }
        current.setElement(sucessor.getElement());
        if (Paisucessor.getLeftChild() == sucessor) {
            Paisucessor.setLeftChild(sucessor.getRightChild());
            if (sucessor.getRightChild() != null) {
                sucessor.getRightChild().setParent(Paisucessor);
            }
        }
        else {
            Paisucessor.setRightChild(sucessor.getRightChild());
            if (sucessor.getRightChild() != null) {
                sucessor.getRightChild().setParent(Paisucessor);
            }
        }
        this.size--;
    }
    public void mostrarArvoreMatriz(No raiz) {
        if (raiz == null) {
            System.out.println("Árvore vazia.");
            return;
        }

        int altura = height(raiz);
        int linhas = altura + 1;
        int colunas = (int) Math.pow(2, altura + 1) - 1;

        String[][] matriz = new String[linhas][colunas];

        for (int i = 0; i < linhas; i++)
            for (int j = 0; j < colunas; j++)
                matriz[i][j] = "   ";

        preencherMatriz(raiz, matriz, 0, colunas / 2, altura);

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++)
                System.out.print(matriz[i][j]);
            System.out.println();
        }
    }
    private void preencherMatriz(No no, String[][] matriz, int nivel, int coluna, int alturaRestante) {
        if (no == null) return;

        matriz[nivel][coluna] = String.format("%3s", no.getElement());

        if (alturaRestante > 0) {
            int deslocamento = (int) Math.pow(2, alturaRestante - 1);

            if (no.getLeftChild() != null)
                preencherMatriz(no.getLeftChild(), matriz, nivel + 1, coluna - deslocamento, alturaRestante - 1);

            if (no.getRightChild() != null)
                preencherMatriz(no.getRightChild(), matriz, nivel + 1, coluna + deslocamento, alturaRestante - 1);
        }
    }
    public boolean eArvoreBinariaPesquisa(ArvoreBinaria arvore){
        boolean result = analisearvore(arvore.root());
        return result;
    }
    public boolean analisearvore(No raiz) {
        if (raiz == null) {
            System.out.println("Árvore vazia.");
            return false;
        }
        if (raiz.getLeftChild() != null && (int) raiz.getLeftChild().getElement() > (int) raiz.getElement()) {
            System.out.println("Árvore não é uma árvore de pesquisa binária.");
            return false;
        }
        if (raiz.getRightChild() != null && (int) raiz.getRightChild().getElement() < (int) raiz.getElement()) {
            System.out.println("Árvore não é uma árvore de pesquisa binária.");
            return false;
        }

        analisearvore(raiz.getLeftChild());
        analisearvore(raiz.getRightChild());
        return true;
    }
}
