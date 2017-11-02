/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.project2;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Word;

/**
 * A representation of a Document class.
 * 
 * The relevant Document is contained implicitly within the HashMap in TinySearchEngine
 * Contains a hashtable of WordFamily classes.
 * 
 * @author Griffone
 */
public class DocumentWrapper {
    
    public HashMap<String, WordFamily> words;
    
    public DocumentWrapper() {
        words = new HashMap();
    }
    
    public void add(Word word, Attributes attributes) throws Exception {
        words.merge(word.word, new WordFamily(word, attributes), (oldFamily, newFamily) -> {
            try {
                return oldFamily.add(word, attributes);
            } catch (Exception ex) {
                Logger.getLogger(DocumentWrapper.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        });
    }
}
