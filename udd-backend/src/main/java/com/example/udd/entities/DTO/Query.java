package com.example.udd.entities.DTO;

import com.example.udd.entities.Operator;

public class Query {
    public String Field;
    public String Value;
    public Operator Operator;

    public boolean Match;

    public Query(String field, String value, com.example.udd.entities.Operator operator, boolean match) {
        Field = field;
        Value = value;
        Operator = operator;
        match = true;
    }
}
