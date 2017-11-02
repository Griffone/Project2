/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.project2;

import java.util.ArrayList;
import java.util.List;
/**
 * An always sorted list.
 * 
 * Has more efficient intersectoin, union and difference methods.
 * 
 * Is a linked-list internally.
 * 
 * @author Griffone
 * @param <T extends Comparable<T>> a comparable type
 */
public class SortedList<T extends Comparable<T>> {

    private Node first = null;
    private Node last = null;
    
    private class Node {
        Node next;
        T item;
        
        Node(T item) {
            this.item = item;
        }
    }
    
    private void append(T item) {
        if (last == null)
            last = first = new Node(item);
        else {
            last.next = new Node(item);
            last = last.next;
        }
    }
    
    private void append(Node node) {
        append(node.item);
    }
    
    public void insert(T item) {
        if (last == null) {
            last = first = new Node(item);
            return;
        }
        
        if (first.item.compareTo(item) > 0) {
            Node tmp = first;
            first = new Node(item);
            first.next = tmp;
            return;
        }
        
        Node node = first;
        while (node.next != null) {
            if (node.next.item.compareTo(item) > 0) {
                Node tmp = node.next;
                node.next = new Node(item);
                node.next.next = tmp;
                return;
            }
            node = node.next;
        }
        node.next = new Node(item);
        last = node.next;
    }
    
    public SortedList<T> instersect(SortedList<T> other) {
        SortedList<T> list = new SortedList();
        Node thisNode = this.first;
        Node otherNode = other.first;
        
        while (thisNode != null && otherNode != null) {
            int dif = thisNode.item.compareTo(otherNode.item);
            if (dif < 0) {
                thisNode = thisNode.next;
            } else if (dif == 0) {
                list.append(thisNode);
                thisNode = thisNode.next;
            } else {
                otherNode = otherNode.next;
            }
        }
        
        return list;
    }
    
    public SortedList<T> union(SortedList<T> other) {
        SortedList<T> list = new SortedList();
        Node thisNode = this.first;
        Node otherNode = other.first;
        
        while (thisNode != null || otherNode != null) {
            if (thisNode == null) {
                list.append(otherNode);
                otherNode = otherNode.next;
            } else if (otherNode == null) {
                list.append(thisNode);
                thisNode = thisNode.next;
            } else {
                int dif = thisNode.item.compareTo(otherNode.item);
                if (dif < 0) {
                    list.append(thisNode);
                    thisNode = thisNode.next;
                } else if (dif == 0) {
                    thisNode = thisNode.next;
                } else {
                    list.append(otherNode);
                    otherNode = otherNode.next;
                }
            }
        }
        
        return list;
    }
    
    public SortedList<T> difference(SortedList<T> other) {
        SortedList<T> list = new SortedList();
        Node thisNode = this.first;
        Node otherNode = other.first;
        
        while (thisNode != null && otherNode != null) {
            int dif = thisNode.item.compareTo(otherNode.item);
            if (dif < 0) {
                list.append(thisNode);
                thisNode = thisNode.next;
            } else if (dif == 0) {
                thisNode = thisNode.next;
                otherNode = otherNode.next;
            } else {
                otherNode = otherNode.next;
            }
        }
        while (thisNode != null) {
            list.append(thisNode);
            thisNode = thisNode.next;
        }
        
        return list;
    }
    
    public List<T> toList() {
        List<T> list = new ArrayList();
        Node node = first;
        while (node != null) {
            list.add(node.item);
            node = node.next;
        }
        return list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node node = first;
        while (node != null) {
            sb.append(node.item).append(' ');
            node = node.next;
        }
        return sb.toString();
    }
}
