package edu.wpi.teamname.views;

import edu.wpi.teamname.views.manager.LanguageListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Translator {

    private Translator() {
        languageHashmap.put("language_english", language_english); //add english hashmap
        languageHashmap.put("language_spanish", language_spanish); //add spanish hashmap

        language_english.put("NavMenu_MapEditor","Map Editor"); //navigation menu, map editor button
        language_spanish.put("NavMenu_MapEditor","Editor de Mapas"); //navigation menu, map editor button
        language_english.put("NavMenu_SubmittedRequests","Submitted Requests"); //navigation menu, submitted requests button
        language_spanish.put("NavMenu_SubmittedRequests","Solicitudes Enviadas"); //navigation menu, submitted requests button
        language_english.put("NavMenu_Registrations","Registrations"); //navigation menu, registrations button
        language_spanish.put("NavMenu_Registrations","Inscripciones"); //navigation menu, registrations button
    }

    private static final Translator instance = new Translator();

    private ArrayList<LanguageListener> languageListeners = new ArrayList<>();

    public static synchronized Translator getInstance() {
        return instance;
    }

    HashMap<String, HashMap<String, String>> languageHashmap = new HashMap<>();
    HashMap<String, String> language_english = new HashMap<>();
    HashMap<String, String> language_spanish = new HashMap<>();
    HashMap<String, String> language_chineseSimplified = new HashMap<>();

    public void setCurrentLanguage(String currentLanguage) {
        this.currentLanguage = currentLanguage;
        updateLanguage();
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    private String currentLanguage = "language_english";


    public String get(String fieldId) {
        return languageHashmap.get(currentLanguage).get(fieldId);
    }


    public void addLanguageListener(LanguageListener listener) {
        languageListeners.add(listener);
    }

    public void updateLanguage() {
        for (LanguageListener listener : languageListeners
        ) {
            listener.updateLanguage();
        }
    }



}
