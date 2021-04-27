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
        language_english.put("Requests", "Requests"); //default page, requests button
        language_spanish.put("Requests", "Peticiones");
        language_english.put("CheckIn", "Check-In"); // default page, check in button
        language_spanish.put("CheckIn", "Registrarse");
        language_english.put("NavMenu_MapEditor","Map Editor"); //navigation menu, map editor button
        language_spanish.put("NavMenu_MapEditor","Editor de Mapas");
        language_english.put("NavMenu_SubmittedRequests","Submitted Requests"); //navigation menu, submitted requests button
        language_spanish.put("NavMenu_SubmittedRequests","Solicitudes Enviadas");
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
        language_spanish.put("Navigation_directions","Direcciones en Ingles");
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
        language_english.put("Navigation_directions","Directions");

        //Requests


        language_spanish.put("Requests_header", "Peticiones"); //Ingrese su ubicación actual y destino para recibir una ruta.
        language_spanish.put("Requests_desc", "Seleccione la solicitud de servicio que le gustaria realizar."); //Seleccione la solicitud de servicio que le gustaría realizar.
        language_spanish.put("Requests_GiftDelivery", "Entrega de Regalo");
        language_spanish.put("Requests_FoodDelivery", "Entrega de Comida");
        language_spanish.put("Requests_MedicineDelivery", "Entrega de Medicamentos");
        language_spanish.put("Requests_LaundryServices", "Servicios de Lavanderia");
        language_spanish.put("Requests_ComputerService", "Servicio Informatico"); //Servicio informático
        language_spanish.put("Requests_FacilitiesMaintenance", "Mantenimiento de Instalaciones");
        language_spanish.put("Requests_SanitationServices", "Servicios de Saneamiento");
        language_spanish.put("Requests_PatientTransportation", "Transporte de Pacientes");
        language_english.put("Requests_header", "Requests"); //Ingrese su ubicación actual y destino para recibir una ruta.
        language_english.put("Requests_desc", "Please select the service request you would like to make."); //Seleccione la solicitud de servicio que le gustaría realizar.
        language_english.put("Requests_GiftDelivery", "Gift Delivery");
        language_english.put("Requests_FoodDelivery", "Food Delivery");
        language_english.put("Requests_MedicineDelivery", "Medicine Delivery");
        language_english.put("Requests_LaundryServices", "Laundry Services");
        language_english.put("Requests_ComputerService", "Computer Services"); //Servicio informático
        language_english.put("Requests_FacilitiesMaintenance", "Facilities Maintenance");
        language_english.put("Requests_SanitationServices", "Sanitation Services");
        language_english.put("Requests_PatientTransportation", "Patient Transportation");


//Covid Screening Page
        language_english.put("Covid_title", "COVID-19 Screening");
        language_english.put("Covid_desc", "The CDC recommends these steps to reduce your risk of getting and spreading COVID-19: ");
        language_english.put("Covid_check1", "Wear a mask over your nose and mouth.");
        language_english.put("Covid_check2", "Stay at least six feet away from people who don't live with you.");
        language_english.put("Covid_check3", "Get a COVID-19 vaccine when it is available to you.");
        language_english.put("Covid_check4", "Avoid crowded areas and poorly ventinated spaces.");
        language_english.put("Covid_check5", "Wash your hands often with soap and water or use hand sanitizer with at least 60% alcohol.");
        language_english.put("Covid_checkInst", "Please check all that apply:");
        language_english.put("Covid_symptom1Checkbox", "I have had a symptomatic COVID-19 test in the last 14 days.");
        language_english.put("Covid_symptom2Checkbox", "I have received a positive test result for COVID-19 in the last 14 days.");
        language_english.put("Covid_symptom3Checkbox", "I have been in close contact with someone diagnosed with COVID-19 in the last 14 days.");
        language_english.put("Covid_symptom4Checkbox", "I am experiencing COVID-19-like symptoms.");
        language_english.put("Covid_submitButton", "Submit");
        language_spanish.put("Covid_title", "Deteccion de COVID-19"); //Detección de COVID-19
        language_spanish.put("Covid_desc", "El CDC recomienda estos pasos para reducir su riesgo de contraer y propagar COVID-19:");
        language_spanish.put("Covid_check1", "Use una mascarilla sobre su nariz y boca.");
        language_spanish.put("Covid_check2", "Mantengase al menos a seis pies de distancia de las personas que no viven con usted.");
        //Manténgase al menos a seis pies de distancia de las personas que no viven con usted.
        language_spanish.put("Covid_check3", "Obtenga una vacuna COVID-19 cuando este disponible para usted.");
        //Obtenga una vacuna COVID-19 cuando esté disponible para usted.
        language_spanish.put("Covid_check4", "Evite las areas concurridas y los espacios mal ventilados.");
        //Evite las áreas concurridas y los espacios mal ventilados.
        language_spanish.put("Covid_check5", "Lavese las manos con frecuencia con agua y jabon o use un desinfectante de manos con al menos un 60% de alcohol.");
        //Lávese las manos con frecuencia con agua y jabón o use un desinfectante de manos con al menos un 60% de alcohol.
        language_spanish.put("Covid_checkInst", "Por favor marque todos los que apliquen:");
        language_spanish.put("Covid_symptom1Checkbox", "He tenido una prueba de COVID-19 sintomatica en los" + "\n" + " ultimos 14 días.");
        //He tenido una prueba de COVID-19 sintomática en los últimos 14 días.
        language_spanish.put("Covid_symptom2Checkbox", "He estado en contacto cercano con alguien diagnosticado"+ "\n" + " con COVID-19 en los ultimos 14 dias.");
        //He estado en contacto cercano con alguien diagnosticado con COVID-19 en los últimos 14 días.
        language_spanish.put("Covid_symptom3Checkbox", "I have been in close contact with someone diagnosed with" + "\n" + " COVID-19 in the last 14 days.");
        language_spanish.put("Covid_symptom4Checkbox", "Estoy experimentando sintomas similares al COVID-19.");
        //Estoy experimentando síntomas similares al COVID-19.
        language_spanish.put("Covid_submitButton", "Enviar");



//Symptoms Page
        language_spanish.put("Symptoms_title", "Sintomas potenciales"); //Síntomas potenciales
        language_spanish.put("Symptoms_symptomsInclude", "Los sintomas pueden incluir:");
        language_spanish.put("Symptoms_s1", "fiebre"); //Los síntomas pueden incluir:
        language_spanish.put("Symptoms_s2", "escalofrios"); //escalofríos
        language_spanish.put("Symptoms_s3", "tos");
        language_spanish.put("Symptoms_s4", "dificultad para respirar");
        language_spanish.put("Symptoms_s5", "dolor de garganta");
        language_spanish.put("Symptoms_s6", "fatiga");
        language_spanish.put("Symptoms_s7", "dolor de cabeza");
        language_spanish.put("Symptoms_s8", "dolor de cuerpo");
        language_spanish.put("Symptoms_s9", "secrecion nasal / congestion"); //secreción nasal / congestión
        language_spanish.put("Symptoms_s10", "perdida del gusto u olfato"); //pérdida del gusto u olfato
        language_spanish.put("Symptoms_s11", "nauseas, vomitos o diarrea"); //náuseas, vómitos o diarrea
        language_spanish.put("Symptoms_OKButton", "OK");
        language_english.put("Symptoms_title", "Potential Symptoms"); //Síntomas potenciales
        language_english.put("Symptoms_symptomsInclude", "Symptoms may include:");
        language_english.put("Symptoms_s1", "fever"); //Los síntomas pueden incluir:
        language_english.put("Symptoms_s2", "chills"); //escalofríos
        language_english.put("Symptoms_s3", "cough");
        language_english.put("Symptoms_s4", "shortness of breath");
        language_english.put("Symptoms_s5", "sore throat");
        language_english.put("Symptoms_s6", "fatigue");
        language_english.put("Symptoms_s7", "headache");
        language_english.put("Symptoms_s8", "muscle/body aches");
        language_english.put("Symptoms_s9", "runny nose/congestion"); //secreción nasal / congestión
        language_english.put("Symptoms_s10", "new loss of taste or smell"); //pérdida del gusto u olfato
        language_english.put("Symptoms_s11", "nausea, vomiting, or diarrhea"); //náuseas, vómitos o diarrea
        language_english.put("Symptoms_OKButton", "OK");

        //Close Contact

        language_english.put("CloseContact_title", "Close Contacts");
        language_english.put("CloseContact_desc", "\"Close contact\" means:");
        language_english.put("CloseContact_b1", "Living in the same household as a person who has tested positive for COVID-19.");
        language_english.put("CloseContact_b2", "Caring for a person who has tested positive for COVID-19.");
        language_english.put("CloseContact_b3", "Being within 6 feet of a person who has tested positive for COVID-19 for 15 minutes or more.");
        language_english.put("CloseContact_b4", "Coming in direct contact with secretions (e.g. sharing utensils, being coughed on) from a person who has tested positive for COVID-19, while that person was symptomatic.");
        language_spanish.put("CloseContact_title", "Cerrar Contactos");
        language_spanish.put("CloseContact_desc", "\"Contacto cercano\" significa:");
        language_spanish.put("CloseContact_b1", "Vivir en el mismo hogar que una persona que dio positivo en la prueba de COVID-19.");
        language_spanish.put("CloseContact_b2", "Cuidar a una persona que dio positivo en la prueba de COVID-19.");
        language_spanish.put("CloseContact_b3", "Estar a menos de 6 pies de una persona que haya dado positivo en la prueba de COVID-19 durante 15 minutos o mas.");
        //Estar a menos de 6 pies de una persona que haya dado positivo en la prueba de COVID-19 durante 15 minutos o más.
        language_spanish.put("CloseContact_b4", "Entrar en contacto directo con secreciones (por ejemplo, compartir utensilios, toser) de una persona que dio positivo en la prueba de COVID-19, mientras esa persona tenia sintomas.");
        //Entrar en contacto directo con secreciones (por ejemplo, compartir utensilios, toser) de una persona que dio positivo en la prueba de COVID-19, mientras esa persona tenía síntomas.

//Covid Message

        language_english.put("CovidMessage_title", "Stop!");
        language_english.put("CovidMessage_successText", "Based on your answers, you might have COVID-19. Please proceed to the emergency entrance for further evaluation.");
        language_spanish.put("CovidMessage_title", "Detener");
        language_spanish.put("CovidMessage_successText", "Segun sus respuestas, es posible que tenga COVID-19. Dirijase a la entrada de emergencia para una evaluacion adicional.");
        //Según sus respuestas, es posible que tenga COVID-19. Diríjase a la entrada de emergencia para una evaluación adicional.

        //Success Page

        language_english.put("Success_title", "Success!");
        language_english.put("Success_successText", "You have successfully submitted the form. A receptionist will be with you shortly.");
        language_spanish.put("Success_title", "Exito"); //Éxito
        language_spanish.put("Success_successText", "Ha enviado correctamente el formulario. Una recepcionista estara con usted en breve.");
        //Ha enviado correctamente el formulario. Una recepcionista estará con usted en breve.

   //Computer Services

        language_english.put("ComputerServices_title", "Computer Service Request!");
        language_english.put("ComputerServices_desc", "Please fill in the following fields to request computer services.");
        language_english.put("ComputerServices_askFullName", "Please enter your full name:");
        language_english.put("ComputerServices_nameInput", "Full Name");
        language_english.put("ComputerServices_askDesc", "Please enter a brief description of the issue:");
        language_english.put("ComputerServices_descriptionInput", "Description");
        language_english.put("ComputerServices_askPriority", "Please select a request priority level:");
        language_english.put("ComputerServices_lowUrgency", "Low");
        language_english.put("ComputerServices_mediumUrgency", "Medium");
        language_english.put("ComputerServices_highUrgency", "High");
        language_english.put("ComputerServices_askPhone", "Please enter your phone number to receive a confirmation of the request:");
        language_english.put("ComputerServices_phoneInput", "Phone Number");
        language_english.put("ComputerServices_askLocation", "Please enter the location of the computer that requires service:");
        language_english.put("ComputerServices_requestLocation", "Location");
        language_english.put("ComputerServices_submitButton", "Submit");

        language_spanish.put("ComputerServices_title", "Solicitud de Servicio Informatico"); //Solicitud de servicio informático
        language_spanish.put("ComputerServices_desc", "Complete los siguientes campos para solicitar servicios informaticos."); //Complete los siguientes campos para solicitar servicios informáticos.
        language_spanish.put("ComputerServices_askFullName", "Por favor ingresa tu nombre completo:");
        language_spanish.put("ComputerServices_nameInput", "Nombre completo");
        language_spanish.put("ComputerServices_askDesc", "Introduzca una breve descripcion del problema:"); //Introduzca una breve descripción del problema:
        language_spanish.put("ComputerServices_descriptionInput", "Descripcion"); //Descripción
        language_spanish.put("ComputerServices_askPriority", "Seleccione un nivel de prioridad de solicitud:"); //Seleccione un nivel de prioridad de solicitud:
        language_spanish.put("ComputerServices_lowUrgency", "Baja");
        language_spanish.put("ComputerServices_mediumUrgency", "Medio");
        language_spanish.put("ComputerServices_highUrgency", "High");
        language_spanish.put("ComputerServices_askPhone", "Ingrese su numero de telefono para recibir una confirmacion de la solicitud:"); //Ingrese su número de teléfono para recibir una confirmación de la solicitud:
        language_spanish.put("ComputerServices_phoneInput", "Numero de telefono"); //Número de teléfono
        language_spanish.put("ComputerServices_askLocation", "Ingrese la ubicacion de la computadora que requiere servicio:"); //Ingrese la ubicación de la computadora que requiere servicio:
        language_spanish.put("ComputerServices_requestLocation", "Localizacion"); //Localización
        language_spanish.put("ComputerServices_submitButton", "Enviar");

//Gift Delivery

        language_english.put("GiftDelivers_title", "Gift Delivery Request");
        language_english.put("GiftDelivers_desc", "Please fill in the following fields to send a gift to a patient.");
        language_english.put("GiftDelivers_askName", "Please enter your full name:");
        language_english.put("GiftDelivers_nameInput", "Full Name");
        language_english.put("GiftDelivers_askGifts", "Please select the desired gift(s):");
        language_english.put("GiftDelivers_teddyBearBox", "Teddy Bear");
        language_english.put("GiftDelivers_flowerCheckbox", "Flowers");
        language_english.put("GiftDelivers_flowerType", "Type");
        language_english.put("GiftDelivers_chocolateBox", "Chocolates");
        language_english.put("GiftDelivers_balloonsBox", "Balloons");
        language_english.put("GiftDelivers_giftBasketBox", "Gift Basket");
        language_english.put("GiftDelivers_otherCheckbox", "Other:");
        language_english.put("GiftDelivers_askNumber", "Please enter your phone number to receive a confirmation for the delivery:");
        language_english.put("GiftDelivers_phoneInput", "Phone Number");
        language_english.put("GiftDelivers_askLocation", "Please enter the location for the delivery:");
        language_english.put("GiftDelivers_requestLocation", "Location");
        language_english.put("GiftDelivers_submitButton", "Submit");

        language_spanish.put("GiftDelivers_title", "Solicitud de Entrega de Regalo");
        language_spanish.put("GiftDelivers_desc", "Complete los siguientes campos para enviar un regalo a un paciente.");
        language_spanish.put("GiftDelivers_askName", "Por favor ingresa tu nombre completo:");
        language_spanish.put("GiftDelivers_nameInput", "Nombre Completo");
        language_spanish.put("GiftDelivers_askGifts", "Seleccione los obsequios deseados:");
        language_spanish.put("GiftDelivers_teddyBearBox", "Oso de peluche");
        language_spanish.put("GiftDelivers_flowerCheckbox", "Flores");
        language_spanish.put("GiftDelivers_flowerType", "Tipo");
        language_spanish.put("GiftDelivers_chocolateBox", "Chocolates");
        language_spanish.put("GiftDelivers_balloonsBox", "Globos");
        language_spanish.put("GiftDelivers_giftBasketBox", "Cesta de Regalo");
        language_spanish.put("GiftDelivers_otherCheckbox", "Otro/a:");
        language_spanish.put("GiftDelivers_askNumber", "Ingrese su numero de telefono para recibir una confirmacion de la entrega:");
        //Ingrese su número de teléfono para recibir una confirmación de la entrega:
        language_spanish.put("GiftDelivers_phoneInput", "Numero de telefono"); //Número de teléfono
        language_spanish.put("GiftDelivers_askLocation", "Por favor ingrese la ubicacion para la entrega:"); //Por favor ingrese la ubicación para la entrega:
        language_spanish.put("GiftDelivers_requestLocation", "Localizacion"); //Localización
        language_spanish.put("GiftDelivers_submitButton", "Enviar");

//Food Delivery

        language_english.put("FoodDelivery_title", "Food Delivery Request");
        language_english.put("FoodDelivery_desc", "Please fill in the following fields to send food to a patient.");
        language_english.put("FoodDelivery_askName", "Please enter your full name:");
        language_english.put("FoodDelivery_nameInput", "Full Name");
        language_english.put("FoodDelivery_askFood", "Please select the desired food:");
        language_english.put("FoodDelivery_hamburgerBox", "Hamburger");
        language_english.put("FoodDelivery_hotdogBox", "Hot Dog");
        language_english.put("FoodDelivery_impossibleBurgerBox", "Impossible Burger");
        language_english.put("FoodDelivery_otherCheckbox", "Other:");
        language_english.put("FoodDelivery_askNumber", "Please enter your phone number to receive a confirmation for the delivery:");
        language_english.put("FoodDelivery_phoneInput", "Phone Number");
        language_english.put("FoodDelivery_askLocation", "Please enter the location for the delivery:");
        language_english.put("FoodDelivery_requestLocation", "Location");
        language_english.put("FoodDelivery_submitButton", "Submit");

        language_spanish.put("FoodDelivery_title", "Solicitud de Entrega de Alimentos");
        language_spanish.put("FoodDelivery_desc", "Complete los siguientes campos para enviar alimentos a un paciente.");
        language_spanish.put("FoodDelivery_askName", "Por favor ingresa tu nombre completo:");
        language_spanish.put("FoodDelivery_nameInput", "Nombre Completo");
        language_spanish.put("FoodDelivery_askFood", "Seleccione la comida deseada:");
        language_spanish.put("FoodDelivery_hamburgerBox", "Hamburguesa");
        language_spanish.put("FoodDelivery_hotdogBox", "Perro caliente:");
        language_spanish.put("FoodDelivery_impossibleBurgerBox", "Hamburguesa imposible");
        language_spanish.put("FoodDelivery_otherCheckbox", "Otra/o:");
        language_spanish.put("FoodDelivery_askNumber", "Ingrese su numero de telefono para recibir una confirmacion de la entrega:");
        //Ingrese su número de teléfono para recibir una confirmación de la entrega:
        language_spanish.put("FoodDelivery_phoneInput", "Numero de telefono"); //Número de teléfono
        language_spanish.put("FoodDelivery_askLocation", "Por favor ingrese la ubicacion para la entrega:"); //Por favor ingrese la ubicación para la entrega:
        language_spanish.put("FoodDelivery_requestLocation", "Localizacion"); //Localización
        language_spanish.put("FoodDelivery_submitButton", "Enviar");

//    Medicine Delivery

        language_english.put("MedicineDelivery_title", "Medicine Delivery Request");
        language_english.put("MedicineDelivery_desc", "Please fill in the following fields to place a medicine delivery request.");
        language_english.put("MedicineDelivery_askName", "Please input the patient's full name:");
        language_english.put("MedicineDelivery_nameInput", "Full Name");
        language_english.put("MedicineDelivery_askMed", "Please input the name of the medication:");
        language_english.put("MedicineDelivery_medicationNameInput", "Medication Name");
        language_english.put("MedicineDelivery_askDosage", "Please input the dosage amount:");
        language_english.put("MedicineDelivery_dosageAmountInput", "Dosage Amount");
        language_english.put("MedicineDelivery_askLocation", "Please enter the location of the patient:");
        language_english.put("MedicineDelivery_requestLocation", "Location");
        language_english.put("MedicineDelivery_submitButton", "Submit");

        language_spanish.put("MedicineDelivery_title", "Solicitud de Entrega de Medicamentos");
        language_spanish.put("MedicineDelivery_desc", "Complete los siguientes campos para realizar una solicitud de entrega de medicamentos.");
        language_spanish.put("MedicineDelivery_askName", "Please input the patient's full name:");
        language_spanish.put("MedicineDelivery_nameInput", "Ingrese el nombre completo del paciente:");
        language_spanish.put("MedicineDelivery_askMed", "Ingrese el nombre del medicamento:");
        language_spanish.put("MedicineDelivery_medicationNameInput", "Nombre de la medicacion"); //Nombre de la medicación
        language_spanish.put("MedicineDelivery_askDosage", "Por favor ingrese la cantidad de dosis:");
        language_spanish.put("MedicineDelivery_dosageAmountInput", "Cantidad de dosis");
        language_spanish.put("MedicineDelivery_askLocation", "Introduzca la ubicacion del paciente:"); //Introduzca la ubicación del paciente:
        language_spanish.put("MedicineDelivery_requestLocation", "Localizacion"); //Localización
        language_spanish.put("MedicineDelivery_submitButton", "Enviar");


//Sanitation Request

        language_english.put("SanitationService_title", "Sanitation Request");
        language_english.put("SanitationService_desc", "Please fill in the following fields to place a sanitation request.");
        language_english.put("SanitationService_askName", "Please enter your full name:");
        language_english.put("SanitationService_nameInput", "Full name");
        language_english.put("SanitationService_askUrgency", "Please select the urgency of the request:");
        language_english.put("SanitationService_highUrgency", "High");
        language_english.put("SanitationService_mediumUrgency", "Medium");
        language_english.put("SanitationService_lowUrgency", "Low");
        language_english.put("SanitationService_askReason", "Please enter the reason for this request:");
        language_english.put("SanitationService_reasonInput", "Reason");
        language_english.put("SanitationService_askLocation", "Please enter the location you are requesting to sanitize:");
        language_english.put("SanitationService_requestLocation", "Location");
        language_english.put("SanitationService_submitButton", "Submit");

        language_spanish.put("SanitationService_title", "Solicitud De Saneamiento");
        language_spanish.put("SanitationService_desc", "Complete los siguientes campos para realizar una solicitud de saneamiento.");
        language_spanish.put("SanitationService_askName", "Por favor ingresa tu nombre completo:");
        language_spanish.put("SanitationService_nameInput", "Nombre completo");
        language_spanish.put("SanitationService_askUrgency", "Seleccione la urgencia de la solicitud:");
        language_spanish.put("SanitationService_highUrgency", "Alto / Alta");
        language_spanish.put("SanitationService_mediumUrgency", "Medio");
        language_spanish.put("SanitationService_lowUrgency", "Bajo / Baja");
        language_spanish.put("SanitationService_askReason", "Por favor ingrese el motivo de esta solicitud:");
        language_spanish.put("SanitationService_reasonInput", "Razon"); //Razón
        language_spanish.put("SanitationService_askLocation", "Ingrese la ubicacion que solicita desinfectar:"); //Ingrese la ubicación que solicita desinfectar:
        language_spanish.put("SanitationService_requestLocation", "Localizacion"); //Localización
        language_spanish.put("SanitationService_submitButton", "Enviar");

        //Facilities Maintenance

        language_english.put("FacilitiesMaintenance_title", "Facilities Maintenance");
        language_english.put("FacilitiesMaintenance_desc", "Please fill in the following fields to submit a maintenance request.");
        language_english.put("FacilitiesMaintenance_askName", "Please enter your full name:");
        language_english.put("FacilitiesMaintenance_nameInput", "Full name");
        language_english.put("FacilitiesMaintenance_askDescription", "Please enter a brief description of the desired request:");
        language_english.put("FacilitiesMaintenance_descriptionInput", "Description");
        language_english.put("FacilitiesMaintenance_askUrgency", "Level of urgency:");
        language_english.put("FacilitiesMaintenance_lowUrgency", "Low");
        language_english.put("FacilitiesMaintenance_mediumUrgency", "Medium");
        language_english.put("FacilitiesMaintenance_highUrgency", "High");
        language_english.put("FacilitiesMaintenance_askPhoneNumber", "Please enter your phone number to receive a confirmation for the delivery:");
        language_english.put("FacilitiesMaintenance_phoneInput", "Phone Number");
        language_english.put("FacilitiesMaintenance_askLocation", "Please enter the location for the delivery:");
        language_english.put("FacilitiesMaintenance_requestLocation", "Location");
        language_english.put("FacilitiesMaintenance_submitButton", "Submit");

        language_spanish.put("FacilitiesMaintenance_title", "Mantenimiento De Instalaciones");
        language_spanish.put("FacilitiesMaintenance_desc", "Complete los siguientes campos para enviar una solicitud de mantenimiento.");
        language_spanish.put("FacilitiesMaintenance_askName", "Por favor ingresa tu nombre completo:");
        language_spanish.put("FacilitiesMaintenance_nameInput", "Nombre completo");
        language_spanish.put("FacilitiesMaintenance_askDescription", "Introduzca una breve descripcion de la solicitud deseada:");
        language_spanish.put("FacilitiesMaintenance_descriptionInput", "Descripcion"); //Descripción
        language_spanish.put("FacilitiesMaintenance_askUrgency", "Nivel de urgencia:");
        language_spanish.put("FacilitiesMaintenance_lowUrgency", "Bajo / Baja");
        language_spanish.put("FacilitiesMaintenance_mediumUrgency", "Medio");
        language_spanish.put("FacilitiesMaintenance_highUrgency", "Alto / Alta");
        language_spanish.put("FacilitiesMaintenance_askPhoneNumber", "Por favor ingrese su numero de telefono para recibir una confirmacion de la entrega:"); //Por favor ingrese su número de teléfono para recibir una confirmación de la entrega:
        language_spanish.put("FacilitiesMaintenance_phoneInput", "Numero de telefono"); //Número de teléfono
        language_spanish.put("FacilitiesMaintenance_askLocation", "Por favor ingrese la ubicacion para la entrega:"); //Por favor ingrese la ubicación para la entrega:
        language_spanish.put("FacilitiesMaintenance_requestLocation", "Localizacion"); //Localización
        language_spanish.put("FacilitiesMaintenance_submitButton", "Enviar");

        //Laundry Services

        language_english.put("LaundryServices_title", "Laundry Services Request");
        language_english.put("LaundryServices_desc", "Please fill in the following fields to request laundry services.");
        language_english.put("LaundryServices_askName", "Please enter your full name:");
        language_english.put("LaundryServices_nameInput", "Full name");
        language_english.put("LaundryServices_askType", "Please select load type:");
        language_english.put("LaundryServices_colorsBox", "Colors");
        language_english.put("LaundryServices_whitesBox", "Whites");
        language_english.put("LaundryServices_otherCheckbox", "Other:");
        language_english.put("LaundryServices_askTemperature", "Choose wash temperature:");
        language_english.put("LaundryServices_coldBox", "Cold");
        language_english.put("LaundryServices_warmBox", "Warm");
        language_english.put("LaundryServices_hotBox", "Hot");
        language_english.put("LaundryServices_askPhone", "Please enter your phone number to receive a confirmation for the delivery:");
        language_english.put("LaundryServices_phoneInput", "Phone Number");
        language_english.put("LaundryServices_askLocation", "Please enter the location for delivery:");
        language_english.put("LaundryServices_requestLocation", "Location");
        language_english.put("LaundryServices_submitButton", "Submit");

        language_spanish.put("LaundryServices_title", "Solicitud de servicios de lavanderia"); //Solicitud de servicios de lavandería
        language_spanish.put("LaundryServices_desc", "Por favor complete los siguientes campos para solicitar el servicio de lavanderia."); //Por favor complete los siguientes campos para solicitar el servicio de lavandería.
        language_spanish.put("LaundryServices_askName", "Por favor ingresa tu nombre completo:");
        language_spanish.put("LaundryServices_nameInput", "Nombre completo");
        language_spanish.put("LaundryServices_askType", "Seleccione el tipo de carga:");
        language_spanish.put("LaundryServices_colorsBox", "Colores");
        language_spanish.put("LaundryServices_whitesBox", "Blancos / Blancas");
        language_spanish.put("LaundryServices_otherCheckbox", "Otro / Otra:");
        language_spanish.put("LaundryServices_askTemperature", "Elija la temperatura de lavado:");
        language_spanish.put("LaundryServices_coldBox", "Frio / Fria"); //Frío / Fría
        language_spanish.put("LaundryServices_warmBox", "Calido / Calida"); //Cálido / Cálida
        language_spanish.put("LaundryServices_hotBox", "Caliente");
        language_spanish.put("LaundryServices_askPhone", "Por favor ingrese su numero de telefono para recibir una confirmacion de la entrega:"); //Por favor ingrese su número de teléfono para recibir una confirmación de la entrega:
        language_spanish.put("LaundryServices_phoneInput", "Numero de telefono"); //Número de teléfono
        language_spanish.put("LaundryServices_askLocation", "Introduzca la ubicacion para la entrega:"); //Introduzca la ubicación para la entrega:
        language_spanish.put("LaundryServices_requestLocation", "Localizacion"); //Localización
        language_spanish.put("LaundryServices_submitButton", "Enviar");

        //Patient Transportaion

        language_english.put("PatientTransportation_title", "Patient Transportation Request");
        language_english.put("PatientTransportation_desc", "Please fill in the following fields to transport a patient.");
        language_english.put("PatientTransportation_askName", "Please enter patient's full name:");
        language_english.put("PatientTransportation_nameInput", "Full Name");
        language_english.put("PatientTransportation_askLocation", "Please select the current location of the patient:");
        language_english.put("PatientTransportation_currentLocation", "Current Location");
        language_english.put("PatientTransportation_askDestination", "Please select the destination of the patient:");
        language_english.put("PatientTransportation_destination", "Destination");
        language_english.put("PatientTransportation_askAssistance", "Does the patient need immediate medical assistance?");
        language_english.put("PatientTransportation_yesCheckbox", "Yes");
        language_english.put("PatientTransportation_reasonInput", "Reason");
        language_english.put("PatientTransportation_submitButton", "Submit");

        language_spanish.put("PatientTransportation_title", "Solicitud De Transporte Del Paciente");
        language_spanish.put("PatientTransportation_desc", "Complete los siguientes campos para transportar a un paciente.");
        language_spanish.put("PatientTransportation_askName", "Ingrese el nombre completo del paciente:");
        language_spanish.put("PatientTransportation_nameInput", "Nombre completo");
        language_spanish.put("PatientTransportation_askLocation", "Seleccione la ubicacion actual del paciente:"); //Seleccione la ubicación actual del paciente:
        language_spanish.put("PatientTransportation_currentLocation", "Ubicacion actual"); //Ubicación actual
        language_spanish.put("PatientTransportation_askDestination", "Seleccione el destino del paciente:");
        language_spanish.put("PatientTransportation_destination", "Destino");
        language_spanish.put("PatientTransportation_askAssistance", "El paciente necesita asistencia medica inmediata?"); //¿El paciente necesita asistencia médica inmediata?
        language_spanish.put("PatientTransportation_yesCheckbox", "si"); //sí
        language_spanish.put("PatientTransportation_reasonInput", "Razon"); //Razón
        language_spanish.put("PatientTransportation_submitButton", "Enviar");

        language_spanish.put("Requests_success", "Ha enviado correctamente el formulario. Su solicitud se cumplira en breve."); //Ha enviado correctamente el formulario. Su solicitud se cumplirá en breve.
        language_english.put("Requests_success", "You have successfully submitted the form. Your request will be fulfilled shortly.");

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
