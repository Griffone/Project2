/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.project2;

import java.util.HashMap;
import java.util.function.BiFunction;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.PartOfSpeech;
import se.kth.id1020.util.Word;

/**
 * A representation of many Words with the same string.
 * 
 * @author Griffone
 */
public class WordFamily {
    
    public final String string;
    public HashMap<PartOfSpeech, WordInstance> instances;
    
    public WordFamily(Word word, Attributes attributes) {
        this.string = word.word.toLowerCase();
        instances = new HashMap();
        instances.put(word.pos, new WordInstance(attributes.occurrence, 1));
    }
    
    public WordFamily add(Word word, Attributes attributes) throws Exception {
        // TODO: Disable this once debugging is done:
        if (word.word.compareToIgnoreCase(string) != 0)
            throw new Exception("Wrong WordFamily!");
        
        instances.merge(word.pos, new WordInstance(attributes.occurrence, 1), (oldWord, newWord) -> {
            int occurance = (oldWord.occurance <= newWord.occurance) ? oldWord.occurance : newWord.occurance;
            return new WordInstance(occurance, oldWord.count + newWord.count);
        });
        return this;
    }
}
