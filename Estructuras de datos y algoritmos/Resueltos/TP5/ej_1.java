package TP5;

public class ej_1<T,S> {
    public static void main(String[] args){

    }

    public static <S,T> BinaryTree<S> binaryTreeByFunction(BinaryTree<T> tree, Function<T,S> function){
        if(tree==null)
        	return null;
    	return new BinaryTree<S>(function.evaluate(tree.getData()), binaryTreeByFunction(tree.getLeft(), function), binaryTreeByFunction(tree.getRight(), function));
    }

}
