package org.example;

public interface ListInterface<T> {

    void addFront(T obj);

    void addEnd(T obj);

    NodeList<T> getDummy();

    void setDummy(NodeList<T> dummy);

    // Getter y setter para back
    NodeList<T> getBack();

    void setBack(NodeList<T> back);

    void printList();
    String getStreamList();

}
