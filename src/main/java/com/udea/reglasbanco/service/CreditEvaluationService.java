package com.udea.reglasbanco.service;

import com.udea.reglasbanco.model.CreditRequest;
import com.udea.reglasbanco.model.CreditResponse;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditEvaluationService {
    @Autowired
    private KieContainer kieContainer;

    public CreditResponse evaluateCredit(CreditRequest request) {
        //Creamos la respuesta inicial
        CreditResponse response = new CreditResponse();
        //Creamos una sesion en Drools
        KieSession kieSession = kieContainer.newKieSession();

        try{
            //Insertamos los hechos (request y response) en la sesion
            kieSession.insert(request);
            kieSession.insert(response);
            //Ejecutar todas las reglas
            kieSession.fireAllRules();
        }finally {
            //liberamos la sesion
            kieSession.dispose();
        }
        return response;
    }
}
