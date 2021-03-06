package test.modelo;

import model.Exceptions.NoTieneBoostDisponibleException;
import model.FactoryPreguntas.FactoryPreguntas;
import model.Jugador;
import model.Preguntas.Pregunta;
import model.RondaActual;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JugadorTest {

    @Test(expected = NoTieneBoostDisponibleException.class)
    public void JugadorUsaBoostYSeDescuentaUnUsoTest(){

        var jugador1 = new Jugador("Martina");
        var jugador2 = new Jugador("Lautaro");

        List<Jugador> jugadores = new ArrayList<>(){{
            add(jugador1);
            add(jugador2);
        }};

        List<String> respuestaCorrecta = new ArrayList<String>(){{ add("Verdadero"); }};
        List<String> respuestaIncorrecta = new ArrayList<String>(){{ add("Falso"); }};
        List<String> opciones = new ArrayList<>(){{ add("Verdadero");add("Falso");}};
        FactoryPreguntas factory = new FactoryPreguntas();
        Pregunta pregunta = factory.VerdaderoOFalsoConPenalidad("",opciones,respuestaCorrecta);

        var rondaActual = new RondaActual();

        rondaActual.guardarRespuesta(jugador1.getNombre(),respuestaCorrecta,jugador1.elegirBoostDuplicador());
        rondaActual.guardarRespuesta(jugador2.getNombre(),respuestaCorrecta,jugador2.noUsarBoost());
        rondaActual.determinarPuntaje(pregunta,jugadores);

        rondaActual.guardarRespuesta(jugador1.getNombre(),respuestaCorrecta,jugador1.elegirBoostDuplicador());
        rondaActual.guardarRespuesta(jugador2.getNombre(),respuestaCorrecta,jugador2.noUsarBoost());
        rondaActual.determinarPuntaje(pregunta,jugadores);

    }
}
