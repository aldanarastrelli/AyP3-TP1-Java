package model.Preguntas;

import model.Comportamientos.Comportamiento;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Pregunta {

    protected String titulo;
    protected List<String> opciones;
    protected List<String> respuestaCorrecta;
    protected Comportamiento comportamiento;

    public Pregunta(String titulo, List<String> respuesta, Comportamiento comportamiento){
        respuestaCorrecta = respuesta;
        this.comportamiento = comportamiento;
        this.titulo = titulo;
    }

    private void agregarOpcion(String opcion){
        opciones.add(opcion);
    }

    public Map<String, Integer> determinarPuntaje(Map<String, List<String>> respuestas){

        var puntajes = new HashMap<String, Integer>();
        var jugadores = respuestas.keySet().stream();

        jugadores.forEach(j->
                puntajes.put
                        (j,
                                esRespuestaCorrecta(respuestas.get(j)) ?
                                        comportamiento.calcularPuntaje(1,0, true) :
                                        comportamiento.calcularPuntaje(0,1, false))

        );

        return puntajes;
    }

    public abstract boolean esRespuestaCorrecta(List<String> respuesta);

    public String getPregunta(){
        return titulo;
    }
}