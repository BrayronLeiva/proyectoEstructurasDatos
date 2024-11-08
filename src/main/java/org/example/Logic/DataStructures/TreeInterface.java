package org.example.Logic.DataStructures;

import org.example.Logic.Animal;

import java.util.Map;

public interface TreeInterface {
    public boolean loadTree();
    void inorder();
    void preorder();
    void posorder();
    void cleanTree();
    void printTreeFormat();
    boolean isEmpty();
    void play();
    void playRecursive(NodeTree root, NodeTree parent, int nivel);

    Contenedor<Animal> convertTreeIntoList();

    Map<String, ListInterface<String>> convertTreeIntoHashMap(ListInterface<Animal> contenedorAnimales);

    public void guardarArbol();
}