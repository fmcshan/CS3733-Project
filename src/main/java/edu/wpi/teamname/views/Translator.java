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
        language_spanish.put("NavMenu_SubmittedRequests","Solicitudes Enviadas");
        language_spanish.put("Login_Button","Acceso");
        language_spanish.put("Login_loginDescription","\n" +
                "Ingrese sus credenciales de administrador.");
        language_spanish.put("Login_loginLabel","Acceso");
        language_spanish.put("Login_failedLogin","Credenciales no validas"); //need accent over first a in validas
        language_spanish.put("Login_passwordField","Contrasena");  //second n is n with line
        language_spanish.put("Login_emailField","\n" +
                "Correo electronico"); //need accent here
        language_english.put("Login_Button","Login");
        language_english.put("Login_loginDescription","\n" +
                "Please enter your admin credentials.");
        language_english.put("Login_loginLabel","Login");
        language_english.put("Login_failedLogin","Invalid Credentials");
        language_english.put("Login_passwordField","Password");
        language_english.put("Login_emailField","\n" +
                "Email");

        //Registration Page
        language_english.put("Registration_submitButton","Submit");
        language_english.put("Registration_phoneInput","Phone Number");
        language_english.put("Registration_enterPhoneDesc","Please enter your phone number to receive a confirmation of your visit:");
        language_english.put("Registration_otherCheckbox","Other:");
        language_english.put("Registration_physicalTherapyCheckbox","Physical Therapy");
        language_english.put("Registration_labWorkCheckbox","Lab Work");
        language_english.put("Registration_eyeExamCheckbox","Eye Exam");
        language_english.put("Registration_mriCheckbox","MRI");
        language_english.put("Registration_xrayCheckbox","Radiology");
        language_english.put("Registration_emergencyRoomCheckbox","Emergency Room");
        language_english.put("Registration_reasonsLabel","Please select your reason(s) for visting the hospital today:");
        language_english.put("Registration_birthdayDesc","Please enter your date of birth (mm/dd/yyyy):");
        language_english.put("Registration_fullName","Full Name");
        language_english.put("Registration_fullNameDesc","Please enter your full name:");
        language_english.put("Registration_fillfieldsDesc","Please fill in the following fields to register:");
        language_english.put("Registration_registrationForm","Registration Form");

        language_spanish.put("Registration_submitButton","Enviar");
        language_spanish.put("Registration_phoneInput","Numero de telefono"); //Número de teléfono
        language_spanish.put("Registration_enterPhoneDesc","Ingrese su numero de telefono para recibir una confirmacion de su visita:");
        //Ingrese su número de teléfono para recibir una confirmación de su visita:
        language_spanish.put("Registration_otherCheckbox","Otra:");
        language_spanish.put("Registration_physicalTherapyCheckbox","Terapia fisica"); //Terapia física
        language_spanish.put("Registration_labWorkCheckbox","Trabajo de laboratorio");
        language_spanish.put("Registration_eyeExamCheckbox", "Examen de la vista");
        language_spanish.put("Registration_mriCheckbox","MRI");
        language_spanish.put("Registration_xrayCheckbox","Radiologia"); //Radiología
        language_spanish.put("Registration_emergencyRoomCheckbox","Sala de emergencias");
        language_spanish.put("Registration_reasonsLabel","Seleccione su motivo (s) para visitar el hospital hoy:");
        language_spanish.put("Registration_birthdayDesc","Ingrese su fecha de nacimiento (mm/dd/yyyy):");
        language_spanish.put("Registration_fullName","Nombre completo");
        language_spanish.put("Registration_fullNameDesc","Por favor ingresa tu nombre completo:");
        language_spanish.put("Registration_fillfieldsDesc","Por favor complete los siguientes campos para registrarse:");
        language_spanish.put("Registration_registrationForm","Formulario de Inscripcion"); //Formulario de inscripción

        //Navigation Page

        language_spanish.put("Navigation_title","Navegacion"); //Navegacion
        language_spanish.put("Navigation_description", "Ingrese su ubicacion actual y destino para recibir una ruta."); //Ingrese su ubicación actual y destino para recibir una ruta.
        language_spanish.put("Navigation_toLabel", "A");
        language_spanish.put("Navigation_fromLabel","De");
        language_spanish.put("Navigation_toBox","Ubicacion actual"); //Ubicación actual
        language_spanish.put("Navigation_fromBox","Destino");
        language_english.put("Navigation_title","Navigation"); //Navegacion
        language_english.put("Navigation_description", "Enter your current location and destination to recieve a path."); //Ingrese su ubicación actual y destino para recibir una ruta.
        language_english.put("Navigation_toLabel", "To");
        language_english.put("Navigation_fromLabel","From");
        language_english.put("Navigation_toBox","Current Location"); //Ubicación actual
        language_english.put("Navigation_fromBox","Destination");

        //Requests

        language_spanish.put("Requests_header", "Peticiones"); //Ingrese su ubicación actual y destino para recibir una ruta.
        language_spanish.put("Requests_openButton", "Seleccione la solicitud de servicio que le gustaria realizar."); //Seleccione la solicitud de servicio que le gustaría realizar.
        language_spanish.put("Requests_requestsBox","Tipo");
        language_spanish.put("Requests_typeLabel","Peticiones"); //Ubicación actual
        language_spanish.put("Requests_description", "Formulario de Solicitud Abierto");
        language_english.put("Requests_header", "Peticiones"); //Ingrese su ubicación actual y destino para recibir una ruta.
        language_english.put("Requests_openButton", "Seleccione la solicitud de servicio que le gustaria realizar."); //Seleccione la solicitud de servicio que le gustaría realizar.
        language_english.put("Requests_requestsBox","Tipo");
        language_english.put("Requests_typeLabel","Peticiones"); //Ubicación actual
        language_english.put("Requests_description", "Formulario de Solicitud Abierto");



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
