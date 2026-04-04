package ArvoreBinaria;

public class No {
    private Object element;
    private No parent;
    private No left_child;
    private No right_child;
    public No() {
        parent = null;
        left_child = null;
        right_child = null;
        element = null;
    }
    public No(No Parent,Object element) {
        this.element = element;
        parent = Parent;
        left_child = null;
        right_child = null;
    }
    public No(No Parent) {
        this.element = null;
        parent = Parent;
        left_child = null;
        right_child = null;
    }
    public void setElement(Object element) {
        this.element = element;
    }
    public void setParent(No parent) {
        this.parent = parent;
    }
    public void setLeftChild(No left_child) {
        this.left_child = left_child;
        if (left_child != null) {
            left_child.setParent(this);
        }
    }
    public void setRightChild(No right_child) {
        this.right_child = right_child;
        if (right_child != null) {
            right_child.setParent(this);
        }
    }
    public Object getElement() {
        return element;
    }
    public No getParent() {
        return parent;
    }
    public No getLeftChild() {
        return left_child;
    }
    public No getRightChild() {
        return right_child;
    }
}
