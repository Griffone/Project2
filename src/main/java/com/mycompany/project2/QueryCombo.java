/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.project2;

import java.util.HashMap;
import se.kth.id1020.util.Document;

/**
 * A combination of 2 sub-queries
 * 
 * @author Griffone
 */
public class QueryCombo extends QueryBase {
    
    private final QueryBase a;
    private final QueryBase b;
    private final CombinationType comboType;

    public QueryCombo(QueryBase a, QueryBase b, CombinationType combo) {
        this.a = a;
        this.b = b;
        comboType = combo;
    }
    
    @Override
    public String toInfix() {
        StringBuilder sb = new StringBuilder();
        sb.append('(').append(a.toInfix());
        switch (comboType) {
            case INTERSECTION:
                sb.append(" + ");
                break;
            case UNION:
                sb.append(" | ");
                break;
            case DIFFERENCE:
                sb.append(" - ");
                break;
        }
        sb.append(b.toInfix()).append(')');
        return sb.toString();
    }

    @Override
    public SortedList<Document> find(HashMap<Document, DocumentWrapper> data) {
        SortedList<Document> list = a.getResutls(data);
        switch (comboType) {
            case INTERSECTION:
                list = list.instersect(b.getResutls(data));
                break;
            case UNION:
                list = list.union(b.getResutls(data));
                break;
            case DIFFERENCE:
                list = list.difference(b.getResutls(data));
                break;
        }
        return list;
    }
    
    public static enum CombinationType {
        INTERSECTION, UNION, DIFFERENCE
    }
}