/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.project2;

/**
 * A simple <k - v> mapping for caching.
 * 
 * @author Griffone
 * @param <K> - the key type to a cache
 * @param <V> - the value type for each key
 */
public class Cache<K, V> {
    
    public static final int DEFAULT_SIZE = 32;
    
    private K[] key_array;
    private V[] value_array;
    
    private int current_id;
    
    public Cache() {
        key_array = (K[]) new Object[DEFAULT_SIZE];
        value_array = (V[]) new Object[DEFAULT_SIZE];
        current_id = 0;
    }

    /**
     * A lazy resize (drops all data)
     * 
     * @param size the new size
     */
    public void resize(int size) {
        if (key_array.length == size)
            return;
        key_array = (K[]) new Object[size];
        value_array = (V[]) new Object[size];
        current_id = 0;
    }
    
    /**
     * Tries to get the value of a given key
     * 
     * returns the value for that key or null if the key is not cached
     * 
     * @param key
     * @return the value for the given key or null if the key is not cached
     */
    public V find(K key) {
        for (int i = 0; i < key_array.length; i++) {
            if (key_array[i] == null)
                return null;
            
            if (key_array[i].equals(key))
                return value_array[i];
        }
        return null;
    }
    
    /**
     * Puts the given key-value pair into the array
     * 
     * Doesn't check for duplicates
     * 
     * @param key
     * @param value 
     */
    public void put(K key, V value) {
        key_array[current_id] = key;
        value_array[current_id] = value;
        incrementCurrent();
    }
    
    private void incrementCurrent() {
        if (++current_id >= key_array.length)
            current_id = 0;
    }
}
