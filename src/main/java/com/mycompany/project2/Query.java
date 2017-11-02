/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.project2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import se.kth.id1020.util.Document;

public class Query {

    private final QueryBase query;
    private final boolean sort_descending;
    private final SortBy sort_type;
    
    private Query(QueryBase query, boolean descending, SortBy type) {
        this.query = query;
        this.sort_descending = descending;
        this.sort_type = type;
    }
    
    /**
     * Parses the provided string and returns the corresponding query or null if the string is misformatted
     * 
     * @param string The string to parse
     * @return The corresponding Query object or null if string is misformatted
     */
    public static Query fromString(String string) {
        ParseTree tree = new ParseTree();
        boolean found_orderby = false;
        SortBy sort_type = SortBy.POPULARITY;
        boolean sort_dir_desc = true;
        
        String[] words = string.split(" ");
        for (String word : words) {
            if (word.compareTo("orderby") == 0) {
                found_orderby = true;
                continue;
            }
            
            if (found_orderby) {
                if (word.compareToIgnoreCase("popularity") == 0 || word.compareToIgnoreCase("pop") == 0)
                    sort_type = SortBy.POPULARITY;
                else if (word.compareToIgnoreCase("relevance") == 0 || word.compareToIgnoreCase("rel") == 0)
                    sort_type = SortBy.RELEVANCE;
                else if (word.compareToIgnoreCase("ascending") == 0 || word.compareToIgnoreCase("asc") == 0)
                    sort_dir_desc = false;
                else if (word.compareToIgnoreCase("descending") == 0 || word.compareToIgnoreCase("desc") == 0)
                    sort_dir_desc = true;
                else {
                    System.err.println("Error reading sortby direction or type!");
                    return null;
                }
            } else {
                if (!tree.insert(word)) {
                    System.err.println("Error parsing query!");
                    return null;
                }
            }
        }
        
        if (!tree.root.isFinal()) {
            System.err.println("Error: non-final query!");
            return null;
        } else
            return new Query(tree.root.toQueryBase(), sort_dir_desc, sort_type);
    }
    
    public List<Document> getResults(HashMap<Document, DocumentWrapper> data) {
        List<Document> list = query.getResutls(data).toList();
        switch (sort_type) {
            case POPULARITY:
                if (sort_descending)
                    list.sort((a, b) -> {
                        return b.popularity - a.popularity;
                    });
                else
                    list.sort((a, b) -> {
                        return a.popularity - b.popularity;
                    });
            case RELEVANCE:
                break;
        }
        return list;
    }
    
    public String toInfix() {
        StringBuilder sb = new StringBuilder();
        sb.append(query.toInfix()).append(" ORDERBY ");
        if (sort_descending)
            sb.append("DESCENDING ");
        else
            sb.append("ASCENDING ");
        switch (sort_type) {
            case POPULARITY:
                sb.append("POPULARITY");
                break;
            case RELEVANCE:
                sb.append("RELEVANCE");
                break;
        }
        return sb.toString();
    }
    
    static private class ParseTree {
        
        NodeBase root = null;
        NodeCombo target = null;
        
        boolean insert(String word) {
            if (root != null && root.isFinal())
                return false;

            NodeBase node;
            if (word.compareTo("+") == 0)
                node = new NodeCombo(QueryCombo.CombinationType.INTERSECTION);
            else if (word.compareTo("|") == 0)
                node = new NodeCombo(QueryCombo.CombinationType.UNION);
            else if (word.compareTo("-") == 0)
                node = new NodeCombo(QueryCombo.CombinationType.DIFFERENCE);
            else
                node = new NodeSearch(word);

            if (target == null) {
                root = node;
                if (node.getClass().equals(NodeCombo.class))
                    target = (NodeCombo) node;
                return true;
            } else {
                if (target.left == null) {
                    target.left = node;
                    node.parent = target;
                    if (node.getClass().equals(NodeCombo.class))
                        target = (NodeCombo) node;
                    return true;
                } else {
                    target.right = node;
                    node.parent = target;
                    if (node.getClass().equals(NodeCombo.class))
                        target = (NodeCombo) node;
                    else {
                        while (node != null && node.isFinal())
                            node = node.parent;
                        target = (NodeCombo) node;
                    }
                    return true;
                }
            }
        }
        
        private abstract class NodeBase {
            NodeBase parent;
            
            abstract boolean isFinal();
            abstract QueryBase toQueryBase();
        }
        
        private class NodeSearch extends NodeBase {
            String word;
            
            NodeSearch(String word) {
                this.word = word;
            }

            @Override
            boolean isFinal() {
                return true;
            }

            @Override
            QueryBase toQueryBase() {
                return new QuerySearch(word);
            }
        }
        
        private class NodeCombo extends NodeBase {
            
            NodeBase left;
            NodeBase right;
            QueryCombo.CombinationType type;
            
            NodeCombo(QueryCombo.CombinationType type) {
                this.type = type;
            }
            
            @Override
            boolean isFinal() {
                return left != null && right != null && left.isFinal() && right.isFinal();
            }

            @Override
            QueryBase toQueryBase() {
                return new QueryCombo(left.toQueryBase(), right.toQueryBase(), type);
            }
        }
    }
    
    public static enum SortBy {
        POPULARITY, RELEVANCE
    }
}
