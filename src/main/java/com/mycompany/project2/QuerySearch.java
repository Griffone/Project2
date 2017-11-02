/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.project2;

import java.util.HashMap;
import se.kth.id1020.util.Document;

/**
 * A class for Querying a single word
 * 
 * @author Griffone
 */
public class QuerySearch extends QueryBase {

    private final String word;
    
    public QuerySearch(String word) {
        this.word = word;
    }
    
    @Override
    public String toInfix() {
        return word;
    }

    @Override
    public SortedList<Document> find(HashMap<Document, DocumentWrapper> data) {
        SortedList<Document> list = new SortedList();
        data.forEach((document, documentWrapper) -> {
            if (documentWrapper.words.containsKey(word))
                list.insert(document);
        });
        return list;
    }
    
    @Override
    public String toString() {
        return word;
    }
}
