package edu.wpi.teamname.views;

import edu.wpi.teamname.views.manager.LanguageListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Translator {

    private Translator() {
        languageHashmap.put("language_english", language_english); //add English hashmap
        languageHashmap.put("language_spanish", language_spanish); //add Spanish hashmap
        languageHashmap.put("language_chineseSimplified", language_chineseSimplified); // add Chinese Simplified hashmap

        language_english.put("Navigation", "Navigation"); //default page, navigation button
        language_spanish.put("Navigation", "Navegacion");
        language_chineseSimplified.put("Navigation", "导航");
        language_english.put("Requests", "Requests"); //default page, requests button
        language_spanish.put("Requests", "Peticiones");
        language_chineseSimplified.put("Requests", "请求");
        language_english.put("CheckIn", "Check-In"); // default page, check in button
        language_spanish.put("CheckIn", "Registrarse");
        language_chineseSimplified.put("CheckIn", "登记");
        language_english.put("NavMenu_MapEditor","Map Editor"); //navigation menu, map editor button
        language_spanish.put("NavMenu_MapEditor","Editor de Mapas");
        language_chineseSimplified.put("NavMenu_MapEditor", "地图编辑器");
        language_english.put("NavMenu_SubmittedRequests","Submitted Requests"); //navigation menu, submitted requests button
        language_spanish.put("NavMenu_SubmittedRequests","Solicitudes Enviadas");
        language_chineseSimplified.put("NavMenu_SubmittedRequests", "提交申请");
        language_english.put("NavMenu_Registrations","Registrations"); //navigation menu, registrations button
        language_spanish.put("NavMenu_Registrations","Inscripciones");
        language_chineseSimplified.put("NavMenu_Registrations", "已登记项目");
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
