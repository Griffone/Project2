/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.project2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import se.kth.id1020.Driver;
import se.kth.id1020.TinySearchEngineBase;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Sentence;
import se.kth.id1020.util.Word;

/**
 *
 * @author Griffone
 */
public class TinySearchEngine implements TinySearchEngineBase {
    
    public final HashMap<Document, DocumentWrapper> docs;
    
    Query lastQuery = null;
    String lastQueryString = null;
    
    public TinySearchEngine() {
        docs = new HashMap();
    }
    
    public static void main(String[] args) throws Exception {
        TinySearchEngineBase searchEngine = new TinySearchEngine();
        Driver.run(searchEngine);
    }

    @Override
    public void preInserts() {}

    @Override
    public void insert(Sentence sntnc, Attributes atrbts) {
        DocumentWrapper doc;
        if (docs.containsKey(atrbts.document))
            doc = docs.get(atrbts.document);
        else {
            doc = new DocumentWrapper();
            docs.put(atrbts.document, doc);
        }
        
        for (Word word : sntnc.getWords())
            try {
                doc.add(word, atrbts);
            } catch (Exception ex) {
                Logger.getLogger(TinySearchEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Override
    public void postInserts() {}

    @Override
    public List<Document> search(String string) {
        if (lastQueryString == null || string.compareToIgnoreCase(lastQueryString) != 0) {
            lastQueryString = string;
            lastQuery = Query.fromString(string);
        }
        if (lastQuery != null)
            return lastQuery.getResults(docs);
        return null;
    }

    @Override
    public String infix(String string) {
        if (lastQueryString == null || string.compareToIgnoreCase(lastQueryString) != 0) {
            lastQueryString = string;
            lastQuery = Query.fromString(string);
        }
        return lastQuery.toInfix();
    }

}
