package edu.wpi.teamname.views;

import java.util.HashMap;

public class Translator {

    private Translator(){}
    private static final Translator instance = new Translator();
    public static synchronized Translator getInstance(){
        return instance;
    }
    HashMap<String, HashMap<String, String>> languageHashmap = new HashMap<>();
    HashMap<String, String> language_english = new HashMap<>();
    HashMap<String, String> language_spanish = new HashMap<>();
    HashMap<String, String> language_chineseSimplified = new HashMap<>();

    public void setCurrentLanguage(String currentLanguage) {
        this.currentLanguage = currentLanguage;
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    private String currentLanguage = "language_english";



    public String get(String fieldId){
        return languageHashmap.get(currentLanguage).get(fieldId);
    }





}
