package com.example.ajmal.project1;

import java.util.ArrayList;

/**
 * Created by ajmal on 15-08-2016.
 */
public class Definition {
    private int _id;
    private String term;
    private String defn;
    private String set;

    public Definition(){}
    public Definition(int id,String term,String defn,String set) {
        this._id=id;
        this.term=term;
        this.defn=defn;
        this.set=set;
    }
    public Definition(String term,String defn,String set){
        this.term=term;
        this.defn=defn;
        this.set=set;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        int id=1;
        this._id =id;
        id++;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefn() {
        return defn;
    }

    public void setDefn(String defn) {
        this.defn = defn;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }
}
