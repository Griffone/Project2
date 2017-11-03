/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.project2;

import se.kth.id1020.util.Document;

/**
 *
 * @author Griffone
 */
public class SearchResult implements Comparable<SearchResult> {
    
    /*
    If relevances are withing this number of each other they are considered equal
    Set to 2^-16, as most seem to be around 8*10^-4
    */
    public static final double RELEVANCE_EQUALITY = 0.00001525878;
    
    public final Document document;
    public double relevance;

    public SearchResult(Document document, double relevance) {
        this.document = document;
        this.relevance = relevance;
    }
    
    @Override
    public int compareTo(SearchResult o) {
        return document.compareTo(o.document);
    }
}
