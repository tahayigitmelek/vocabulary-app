package com.tahayigitmelek.vocabularyapp.objects;

public class Words {

    private int status, id;
    private String targetLanguage, nativeLanguage;

    public Words() {
    }

    public Words(int id, String targetLanguage, String nativeLanguage, int status) {
        this.id = id;
        this.targetLanguage = targetLanguage;
        this.nativeLanguage = nativeLanguage;
        this.status = status;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTargetLanguage() {
        return this.targetLanguage;
    }

    public String getNativeLanguage() {
        return this.nativeLanguage;
    }

    public int getStatus() {
        return this.status;
    }
    public void setStatus(int i) {
        this.status = i;
    }
}
