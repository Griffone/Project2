/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.project2;

import java.util.HashMap;
import se.kth.id1020.util.Document;

/**
 * An abstract class that is subclassed by all types of Queries
 * 
 * @author Griffone
 */
public abstract class QueryBase {
    
    private static final Cache<QueryBase, SortedList<SearchResult>> CACHE = new Cache();
    
    public abstract String toInfix();
    public abstract SortedList<SearchResult> find(HashMap<Document, DocumentWrapper> data);
    
    /**
     * Gets the results.
     * 
     * Will get results from cached query if identical one is cached or call find() method
     * 
     * @param data the data to search from
     * @return SortedList of documents
     */
    public SortedList<SearchResult> getResutls(HashMap<Document, DocumentWrapper> data) {
        SortedList<SearchResult> results = CACHE.find(this);
        if (results == null) {
            results = find(data);
            CACHE.put(this, results);
        }
        return results;
    }
}
