package test.modelo.VoFTest;

import model.Exceptions.NoTieneBoostDisponibleException;
import model.FactoryPreguntas.FactoryPreguntas;
import model.Juego;
import model.Jugador;
import model.Preguntas.Pregunta;
import model.Preguntas.VerdaderoOFalso;
import model.RondaActual;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VoFConPenalidadTest {

    @Test
    public void PreguntaVoFConPenalidadPuedeCrearseIndicandoRespuestaCorrectaTest() {

        List<String> respuestaCorrecta = new ArrayList<>();
        respuestaCorrecta.add("Verdadero");

        List<String> opciones = new ArrayList<>(){{add("Verdadero");add("Falso");}};
        FactoryPreguntas factory = new FactoryPreguntas();
        Pregunta pregunta = factory.VerdaderoOFalsoConPenalidad("",opciones,respuestaCorrecta);

        assertTrue(((VerdaderoOFalso) pregunta).esRespuestaCorrecta(respuestaCorrecta));
    }

    @Test
    public void PreguntaVoFConPenalidadRecibeListRtasYAsignaCorrectamentePuntosAJugadoresTest(){

        var juego = new Juego();

        juego.crearJugador("Marcos");
        juego.crearJugador("Evelyn");

        List<String> respuestaCorrecta = new ArrayList<String>(){{ add("Verdadero"); }};
        List<String> respuestaIncorrecta = new ArrayList<String>(){{ add("Falso"); }};
        List<String> opciones = new ArrayList<>(){{add("Verdadero");add("Falso");}};
        FactoryPreguntas factory = new FactoryPreguntas();
        Pregunta pregunta = factory.VerdaderoOFalsoConPenalidad("",opciones,respuestaCorrecta);

        Map<String, List<String>> respuestas = new HashMap<String, List<String>>(){{
            put("Marcos",respuestaIncorrecta);
            put("Evelyn",respuestaCorrecta);
        }};

        Map<String, Integer> puntajeEsperado = new HashMap<String, Integer>(){{
            put("Marcos",-1);
            put("Evelyn",1);
        }};

        Map<String, Integer> puntajeObtenido = pregunta.determinarPuntaje(respuestas);

        assertTrue(puntajeObtenido.equals(puntajeEsperado));

    }

    @Test
    public void JugadorEligeRespuestaVaciaYNoLeAsignaPuntos(){
        var juego = new Juego();

        Jugador jugador1 = juego.crearJugador("Marcos");
        Jugador jugador2 = juego.crearJugador("Evelyn");
        RondaActual rondaActual = juego.crearRondaActual();

        List<String> respuestaCorrecta = new ArrayList<String>(){{ add("Verdadero"); }};
        List<String> respuestaIncorrecta = new ArrayList<String>(){};
        List<String> opciones = new ArrayList<>(){{add("Verdadero");add("Falso");}};
        FactoryPreguntas factory = new FactoryPreguntas();
        Pregunta pregunta = factory.VerdaderoOFalsoConPenalidad("",opciones,respuestaCorrecta);

        rondaActual.guardarRespuesta(jugador1.getNombre(), respuestaCorrecta, jugador1.noUsarBoost());
        rondaActual.guardarRespuesta(jugador2.getNombre(), respuestaIncorrecta, jugador2.noUsarBoost());

        juego.guardarPreguntaActual(pregunta);

        juego.calcularPuntaje();

        assertEquals(1, (int) jugador1.getPuntos());
        assertEquals(-1, (int) jugador2.getPuntos());
    }

    @Test
    public void PreguntaVoFConPenalidadYJugadorUsaMultiplicadorPorDosDevuelvePuntajeDuplicado(){
        var juego = new Juego();

        Jugador jugador1 = juego.crearJugador("Marcos");
        Jugador jugador2 = juego.crearJugador("Evelyn");
        RondaActual rondaActual = juego.crearRondaActual();

        List<String> respuestaCorrecta = new ArrayList<String>(){{ add("Verdadero"); }};
        List<String> respuestaIncorrecta = new ArrayList<String>(){{ add("Falso"); }};
        List<String> opciones = new ArrayList<>(){{add("Verdadero");add("Falso");}};
        FactoryPreguntas factory = new FactoryPreguntas();
        Pregunta pregunta = factory.VerdaderoOFalsoConPenalidad("",opciones,respuestaCorrecta);

        rondaActual.guardarRespuesta(jugador1.getNombre(), respuestaCorrecta, jugador1.elegirBoostDuplicador());
        rondaActual.guardarRespuesta(jugador2.getNombre(), respuestaIncorrecta, jugador2.noUsarBoost());

        juego.guardarPreguntaActual(pregunta);

        juego.calcularPuntaje();

        Integer puntajeIncorrecto = -1;
        assertTrue( jugador1.getPuntos().equals((Integer) 2) && jugador2.getPuntos().equals(puntajeIncorrecto));

    }

    @Test(expected = NoTieneBoostDisponibleException.class)
    public void JugadorNoPuedeUsarMultiplicadorPorDosMasDeUnaVez(){
        var juego = new Juego();

        Jugador jugador1 = juego.crearJugador("Marcos");
        Jugador jugador2 = juego.crearJugador("Evelyn");
        RondaActual rondaActual = juego.crearRondaActual();

        List<String> respuestaCorrecta = new ArrayList<String>(){{ add("Verdadero"); }};
        List<String> respuestaIncorrecta = new ArrayList<String>(){{ add("Falso"); }};
        List<String> opciones = new ArrayList<>(){{add("Verdadero");add("Falso");}};
        FactoryPreguntas factory = new FactoryPreguntas();
        Pregunta pregunta = factory.VerdaderoOFalsoConPenalidad("",opciones,respuestaCorrecta);

        rondaActual.guardarRespuesta(jugador1.getNombre(), respuestaCorrecta, jugador1.elegirBoostDuplicador());
        rondaActual.guardarRespuesta(jugador2.getNombre(), respuestaIncorrecta, jugador2.noUsarBoost());

        juego.guardarPreguntaActual(pregunta);

        juego.calcularPuntaje();
        juego.calcularPuntaje();
    }

    @Test
    public void PreguntaVoFConPenalidadYJugadorUsaMultiplicadorPorTresDevuelvePuntajeTriplicado(){
        var juego = new Juego();

        Jugador jugador1 = juego.crearJugador("Marcos");
        Jugador jugador2 = juego.crearJugador("Evelyn");
        RondaActual rondaActual = juego.crearRondaActual();

        List<String> respuestaCorrecta = new ArrayList<String>(){{ add("Verdadero"); }};
        List<String> respuestaIncorrecta = new ArrayList<String>(){{ add("Falso"); }};
        List<String> opciones = new ArrayList<>(){{add("Verdadero");add("Falso");}};
        FactoryPreguntas factory = new FactoryPreguntas();
        Pregunta pregunta = factory.VerdaderoOFalsoConPenalidad("",opciones,respuestaCorrecta);

        rondaActual.guardarRespuesta(jugador1.getNombre(), respuestaCorrecta, jugador1.elegirBoostTriplicador());
        rondaActual.guardarRespuesta(jugador2.getNombre(), respuestaIncorrecta, jugador2.noUsarBoost());

        juego.guardarPreguntaActual(pregunta);

        juego.calcularPuntaje();

        Integer puntajeIncorrecto = -1;
        assertTrue( jugador1.getPuntos().equals((Integer) 3) && jugador2.getPuntos().equals(puntajeIncorrecto));

    }

    @Test(expected = NoTieneBoostDisponibleException.class)
    public void JugadorNoPuedeUsarMultiplicadorPorTresMasDeUnaVez(){
        var juego = new Juego();

        Jugador jugador1 = juego.crearJugador("Marcos");
        Jugador jugador2 = juego.crearJugador("Evelyn");
        RondaActual rondaActual = juego.crearRondaActual();

        List<String> respuestaCorrecta = new ArrayList<String>(){{ add("Verdadero"); }};
        List<String> respuestaIncorrecta = new ArrayList<String>(){{ add("Falso"); }};
        List<String> opciones = new ArrayList<>(){{add("Verdadero");add("Falso");}};
        FactoryPreguntas factory = new FactoryPreguntas();
        Pregunta pregunta = factory.VerdaderoOFalsoConPenalidad("",opciones,respuestaCorrecta);

        rondaActual.guardarRespuesta(jugador1.getNombre(), respuestaCorrecta, jugador1.elegirBoostTriplicador());
        rondaActual.guardarRespuesta(jugador2.getNombre(), respuestaIncorrecta, jugador2.noUsarBoost());

        juego.guardarPreguntaActual(pregunta);

        juego.calcularPuntaje();
        juego.calcularPuntaje();
    }

    @Test
    public void DosJugadoresConRespuestasDistintasUsanBoostMultiplicadorYDevuelvePuntajeMultiplicado(){
        var juego = new Juego();

        Jugador jugador1 = juego.crearJugador("Marcos");
        Jugador jugador2 = juego.crearJugador("Evelyn");
        RondaActual rondaActual = juego.crearRondaActual();

        List<String> respuestaCorrecta = new ArrayList<String>(){{ add("Verdadero"); }};
        List<String> respuestaIncorrecta = new ArrayList<String>(){{ add("Falso"); }};
        List<String> opciones = new ArrayList<>(){{add("Verdadero");add("Falso");}};
        FactoryPreguntas factory = new FactoryPreguntas();
        Pregunta pregunta = factory.VerdaderoOFalsoConPenalidad("",opciones,respuestaCorrecta);

        rondaActual.guardarRespuesta(jugador1.getNombre(), respuestaCorrecta, jugador1.elegirBoostTriplicador());
        rondaActual.guardarRespuesta(jugador2.getNombre(), respuestaIncorrecta, jugador2.elegirBoostDuplicador());

        juego.guardarPreguntaActual(pregunta);

        juego.calcularPuntaje();

        assertTrue( jugador1.getPuntos().equals((Integer) 3) && jugador2.getPuntos().equals((Integer) (-2)));
    }

    @Test
    public void JugadorUsaMultiplicadorx2PeroRespuestaIncorrectaEntoncesPierde2Puntos(){
        var juego = new Juego();

        Jugador jugador1 = juego.crearJugador("Marcos");
        Jugador jugador2 = juego.crearJugador("Evelyn");
        RondaActual rondaActual = juego.crearRondaActual();

        List<String> respuestaCorrecta = new ArrayList<String>(){{ add("Verdadero"); }};
        List<String> respuestaIncorrecta = new ArrayList<String>(){{ add("Falso"); }};
        List<String> opciones = new ArrayList<>(){{add("Verdadero");add("Falso");}};
        FactoryPreguntas factory = new FactoryPreguntas();
        Pregunta pregunta = factory.VerdaderoOFalsoConPenalidad("",opciones,respuestaCorrecta);

        rondaActual.guardarRespuesta(jugador1.getNombre(), respuestaIncorrecta, jugador1.elegirBoostDuplicador());
        rondaActual.guardarRespuesta(jugador2.getNombre(), respuestaIncorrecta, jugador2.elegirBoostDuplicador());

        juego.guardarPreguntaActual(pregunta);

        juego.calcularPuntaje();
        assertTrue( jugador1.getPuntos().equals((Integer) (-2)));
    }

    @Test
    public void JugadorUsaMultiplicadorx3PeroRespuestaIncorrectaEntoncesPierde3Puntos(){
        var juego = new Juego();

        Jugador jugador1 = juego.crearJugador("Marcos");
        Jugador jugador2 = juego.crearJugador("Evelyn");
        RondaActual rondaActual = juego.crearRondaActual();

        List<String> respuestaCorrecta = new ArrayList<String>(){{ add("Verdadero"); }};
        List<String> respuestaIncorrecta = new ArrayList<String>(){{ add("Falso"); }};
        List<String> opciones = new ArrayList<>(){{add("Verdadero");add("Falso");}};
        FactoryPreguntas factory = new FactoryPreguntas();
        Pregunta pregunta = factory.VerdaderoOFalsoConPenalidad("",opciones,respuestaCorrecta);

        rondaActual.guardarRespuesta(jugador1.getNombre(), respuestaIncorrecta, jugador1.elegirBoostTriplicador());
        rondaActual.guardarRespuesta(jugador2.getNombre(), respuestaIncorrecta, jugador2.elegirBoostDuplicador());

        juego.guardarPreguntaActual(pregunta);

        juego.calcularPuntaje();
    }
}

