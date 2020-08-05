package test.modelo.AgruparTest;

import model.FactoryPreguntas.FactoryPreguntas;
import model.Juego;
import model.Preguntas.Pregunta;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class AgruparTest {

    @Test
    public void PreguntaAgruparPuedeCrearseIndicandoRespuestaCorrectaTest() {

        List<String> respuestaCorrecta = new ArrayList<>();
        respuestaCorrecta.add("Pan");
        respuestaCorrecta.add("Hamburguesa");
        respuestaCorrecta.add("\0");
        respuestaCorrecta.add("Agua");
        respuestaCorrecta.add("Coca-Cola");
        respuestaCorrecta.add("\0");

        FactoryPreguntas factory = new FactoryPreguntas();
        Pregunta pregunta = factory.Agrupar("Bebidas/Comidas",respuestaCorrecta);

        assertTrue((pregunta).esRespuestaCorrecta(respuestaCorrecta));
    }

    @Test
    public void PreguntaAgruparRecibeListRtasYAsignaCorrectamentePuntosAJugadoresTest(){

        var juego = new Juego();

        juego.crearJugador("Marcos");
        juego.crearJugador("Evelyn");

        List<String> respuestaCorrecta = new ArrayList<String>(){{ add("Pan"); add("Hamburguesa"); add("\0"); add("Agua"); add("Coca-Cola"); add("\0");}};
        List<String> respuestaIncorrecta = new ArrayList<String>(){{ add("Agua"); add("Hamburguesa"); add("\0"); add("Pan"); add("Coca-Cola"); add("\0"); }};
        FactoryPreguntas factory = new FactoryPreguntas();
        Pregunta pregunta = factory.Agrupar("Bebidas/Comidas",respuestaCorrecta);

        Map<String, List<String>> respuestas = new HashMap<String, List<String>>(){{
            put("Marcos",respuestaIncorrecta);
            put("Evelyn",respuestaCorrecta);
        }};

        Map<String, Integer> puntajeEsperado = new HashMap<String, Integer>(){{
            put("Marcos",0);
            put("Evelyn",1);
        }};

        Map<String, Integer> puntajeObtenido = pregunta.determinarPuntaje(respuestas);

        assertTrue(puntajeObtenido.equals(puntajeEsperado));

    }
}