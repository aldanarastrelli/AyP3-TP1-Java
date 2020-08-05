package model.Preguntas;

import model.Comportamientos.Comportamiento;

import java.util.List;

public class VerdaderoOFalso extends Pregunta{

    public VerdaderoOFalso(String titulo, List<String> respuesta, Comportamiento comportamiento) {
        super(titulo, respuesta, comportamiento);
    }


    public boolean esRespuestaCorrecta(List<String> respuesta){

        return respuesta.get(0).toUpperCase().equals(respuestaCorrecta.get(0).toUpperCase());
    }
}