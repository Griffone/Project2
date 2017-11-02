/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.project2;

/**
 * A single word representation that is usually contained in WordFamily class.
 * 
 * The relevant PartOfSpeech is implicitly contained in WordFamily.instances hashmap
 * 
 * @author Griffone
 */
public class WordInstance {
    
    public final int occurance;
    public final int count;
    
    public WordInstance(int occurance, int count) {
        this.occurance = occurance;
        this.count = count;
    }
}
