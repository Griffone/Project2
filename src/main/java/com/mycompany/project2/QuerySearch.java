/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.project2;

import java.util.HashMap;
import java.util.Iterator;
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

    
    private static int documentHits;
    
    @Override
    public SortedList<SearchResult> find(HashMap<Document, DocumentWrapper> data) {
        SortedList<SearchResult> list = new SortedList();
        documentHits = 0;
        data.forEach((document, documentWrapper) -> {
            if (documentWrapper.words.containsKey(word)) {
                documentHits++;
                double freq = (double) documentWrapper.words.get(word).wordCount() / (double) documentWrapper.wordCount();
                list.insert(new SearchResult(document, freq));
            }
        });
        Iterator<SearchResult> it = list.iterator();
        double inverseDocumentFreq = Math.log10((double) data.size() / (double) documentHits);
        while (it.hasNext())
            it.next().relevance *= inverseDocumentFreq;
        
        return list;
    }
    
    @Override
    public boolean equals(Object o) {
        return o.getClass().equals(QuerySearch.class) && (word.compareToIgnoreCase(((QuerySearch)o).word) == 0);
    }
    
    @Override
    public String toString() {
        return word;
    }
}
