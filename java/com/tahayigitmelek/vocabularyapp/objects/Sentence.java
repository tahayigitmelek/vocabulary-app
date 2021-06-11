package com.tahayigitmelek.vocabularyapp.objects;

public class Sentence {

    private int id;
    private String firstSentence, secondSentence, thirdSentence;

    public Sentence() {
    }

    public Sentence(int id, String firstSentence, String secondSentence, String thirdSentence) {
        this.id = id;
        this.firstSentence = firstSentence;
        this.secondSentence = secondSentence;
        this.thirdSentence = thirdSentence;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getFirstSentence() {
        return this.firstSentence;
    }

    public void setFirstSentence(String str) {
        this.firstSentence = str;
    }

    public String getSecondSentence() {
        return this.secondSentence;
    }

    public void setSecondSentence(String str) {
        this.secondSentence = str;
    }

    public String getThirdSentence() {
        return this.thirdSentence;
    }

    public void setThirdSentence(String str) {
        this.thirdSentence = str;
    }
}
