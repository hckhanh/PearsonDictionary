package com.dictionary.hckhanh.pearsondictionary.history.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "HISTORY".
 */
public class History {

    private Long id;
    /** Not-null value. */
    private String word;

    public History() {
    }

    public History(Long id) {
        this.id = id;
    }

    public History(Long id, String word) {
        this.id = id;
        this.word = word;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getWord() {
        return word;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setWord(String word) {
        this.word = word;
    }

}
