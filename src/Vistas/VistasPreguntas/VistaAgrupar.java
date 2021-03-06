package Vistas.VistasPreguntas;

import Controladores.Botones.BotonGuardarRespuesta;
import Controladores.ControladorDeTurno;
import Controladores.EntradasUsuario.EntradaAgrupar;
import Controladores.EntradasUsuario.EntradaUsuario;
import Controladores.Sistema;
import Controladores.Timer;
import Vistas.Boton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import model.Jugador;
import model.Preguntas.Pregunta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class VistaAgrupar extends VistaPregunta{

    public VistaAgrupar(Pregunta pregunta, ControladorDeTurno controladorDeTurno){
        super(pregunta,controladorDeTurno);

        Jugador jugador = controladorDeTurno.getactual();

        List<String> opcionesPregunta = pregunta.getOpciones();
        EntradaAgrupar entradaUsuario = new EntradaAgrupar(pregunta,opcionesPregunta,jugador);

        Map<String,ComboBox> opcionesComboBox = entradaUsuario.getOpciones();

        GridPane grid = new GridPane();

        // timer
        Timer timer = new Timer();
        Label timerLabel = timer.getLabel();
        timerLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        timerLabel.setTextAlignment(TextAlignment.LEFT);
        timerLabel.setTextFill(Color.web("red"));
        List<GridPane> elementosHabilitados = new ArrayList<>() {{ add(grid);}};
        controladorDeTurno.setTimerGrid(timer,20,elementosHabilitados);

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(50);
        grid.setVgap(10);
        int i = 0;
        for(Map.Entry<String, ComboBox> elemento : opcionesComboBox.entrySet()){
            Label nombreOpcion = new Label(elemento.getKey());
            nombreOpcion.setFont(Font.font("Tahoma", 12));
            nombreOpcion.setTextFill(Color.web("black"));

            final ComboBox comboBox = elemento.getValue();
            grid.addRow(i,nombreOpcion,comboBox);
            i++;
        }
        this.getChildren().add(0,timerLabel);
        this.getChildren().addAll(grid);

        Label textoElegirBoost = new Label("Elija un Boost");
        textoElegirBoost.setFont(Font.font("Tahoma", 10));
        textoElegirBoost.setTextFill(Color.web("black"));
        final ComboBox comboBoxBoost = entradaUsuario.getBoosts();

        Boton botonGuardarRespuesta  = new Boton("Guardar Respuesta", new BotonGuardarRespuesta(controladorDeTurno,entradaUsuario));

        this.getChildren().addAll(textoElegirBoost, comboBoxBoost, botonGuardarRespuesta);

        Boton botonMute = Sistema.getBotonMute();
        this.getChildren().addAll(botonMute);
    }

    public void setFondo() {
        fondo = new Canvas(800, 800);
        fondo.getGraphicsContext2D().setFill(Color.WHITESMOKE);
        fondo.getGraphicsContext2D().fillRect(0, 0, 720, 420);
        centro = new Pane(fondo);
    }

    public void setBotonGuardarRespuesta(ControladorDeTurno controladorDeTurno, EntradaUsuario entradaUsuario){

        Boton botonGuardarRespuesta  = new Boton("Guardar Respuesta", new BotonGuardarRespuesta(controladorDeTurno,entradaUsuario));
        this.getChildren().add(4, botonGuardarRespuesta);
    }

}

