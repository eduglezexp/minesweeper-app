package es.ies.puerto.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import es.ies.puerto.config.ConfigManager;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.controller.enums.VistaActual;
import es.ies.puerto.model.entities.UsuarioEntity;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public class JuegoController extends AbstractController{

    @FXML
    private StackPane stackPaneMostrarPantallas;

    @FXML
    private Button tiendaButton;

    @FXML
    private ComboBox<String> comboDificultad;

    @FXML
    private Button personalizarButton;

    @FXML
    private Button aleatorioButton;

    @FXML
    private Text textBanderas;

    @FXML
    private Text textTemporizador;

    @FXML
    private ImageView imageViewBandera;

    @FXML
    private ImageView imageViewFotoPerfil;

    @FXML
    private Button estadisticasButton;

    @FXML
    private VBox mostrarPersonalizarVbox;

    @FXML
    private Text textPersonalizarPartida;

    @FXML
    private Text textFilas;

    @FXML
    private TextField textFieldFilas;

    @FXML
    private Text textColumnas;

    @FXML 
    private TextField textFieldColumnas;

    @FXML
    private Text textMinas;
    
    @FXML 
    private TextField textFieldMinas;

    @FXML
    private Text textMensajePersonalizar;

    @FXML
    private Button openAceptarButton;

    @FXML
    private Button regresarButton;

    @FXML
    private VBox mostrarEstadisticasVbox;

    @FXML
    private Text textUsuario;

    @FXML 
    private TextField textFieldUsuario;

    @FXML
    private Text textPuntos;

    @FXML 
    private TextField textFieldPuntos;

    @FXML
    private Text textVictorias;

    @FXML 
    private TextField textFieldVictorias;

    @FXML
    private Text textDerrotas;

    @FXML 
    private TextField textFieldDerrotas;

    @FXML
    private Text textRacha;

    @FXML 
    private TextField textFieldRacha;

    @FXML
    private Text textMejorRacha;

    @FXML
    private VBox mostrarAyudaVBox;

    @FXML
    private Text textTituloAyuda;

    @FXML
    private Text textAyuda;

    @FXML 
    private TextField textFieldMejorRacha;

    @FXML 
    private VBox mostrarTiendaVbox;

    @FXML 
    private Text textPuntosTienda;

    @FXML 
    private Button comprarMinaFantasmaButton;

    @FXML 
    private Button comprarEscudoButton;
    
    @FXML 
    private Button comprarZonaSeguraButton;

    @FXML 
    private Text textMensajeTienda;

    @FXML 
    private Button volverTiendaButton;

    @FXML
    private VBox mostrarInventarioVbox;

    @FXML 
    private Text textPuntosInventario;

    @FXML 
    private Button usarMinaFantasmaButton;

    @FXML 
    private Button usarEscudoButton;

    @FXML 
    private Button usarZonaSeguraButton;

    @FXML 
    private Label badgeLabelEscudo;

    @FXML 
    private Label badgeLabelFantasma;

    @FXML 
    private Label badgeLabelZonaSegura;

    @FXML
    private VBox mostrarInformacionVbox;

    @FXML
    private ImageView imageViewObjeto;

    @FXML
    private Text textObjeto;       

    @FXML
    private Text textInformacion;

    @FXML 
    private Button salirInformacionButton;
    
    @FXML
    private VBox mostrarFinDelJuegoVbox;

    @FXML
    private Text textFinJuego;

    @FXML
    private Text textResultado;

    @FXML
    private Button reintentarButton;

    @FXML
    private Text textMensaje;

    @FXML
    private StackPane stackPaneContenedorTablero;

    @FXML
    private VBox mostrarMensajeVbox;

    @FXML
    private Button ayudaButton;

    @FXML
    private Button inventarioButton;

    @FXML
    private Button buttonVolverAtras;
    
    public record BoardConfig(int filas, int columnas, int minas) {}
    private boolean[][] minas;
    private int[][] minasAdyacentes;
    private boolean gameOver = false;
    private int banderasColocadas = 0;
    private int minasTotales;
    private BoardConfig configActual;
    private boolean primerClick = true;
    private Timeline timeline;
    private int segundosTranscurridos = 0;
    private Button[][] celdas;
    private VistaActual vistaAnterior = VistaActual.TABLERO;
    private int minasFantasmaDisponibles = 0;
    private int escudosDisponibles = 0;
    private boolean escudoActivado = false;
    private int zonasSegurasDisponibles = 0;
    private int miliSegundos = 300;
    private UsuarioEntity usuario;
    private double dificultadMultiplier = 1.0;
    private int minasFantasmaUsadas = 0;
    private int escudosUsados = 0;
    private int zonasSegurasUsadas = 0;

    /**
     * Metodo que se ejecuta al iniciar el controlador
     * Se encarga de cambiar el idioma de la interfaz
     */
    @FXML
    public void initialize() {
        comboDificultad.getItems().add(ConfigManager.ConfigProperties.getProperty("comboPrincipiante"));
        comboDificultad.getItems().add(ConfigManager.ConfigProperties.getProperty("comboIntermedio"));
        comboDificultad.getItems().add(ConfigManager.ConfigProperties.getProperty("comboExperto"));
        comboDificultad.getItems().add(ConfigManager.ConfigProperties.getProperty("comboAleatorio"));
        comboDificultad.getItems().add(ConfigManager.ConfigProperties.getProperty("comboPersonalizado"));
        cambiarIdioma();
        String dificultad = ConfigManager.ConfigProperties.getProperty("comboPrincipiante"); 
        comboDificultad.setValue(dificultad);
        Platform.runLater(() -> manejarSeleccion(0));
    }

    /**
     * Carga los datos del usuario en la vista
     * Inicializa nivel puntos victorias derrotas y comienza el juego
     */
    public void cargarDatosUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
        if (usuario != null) {
            textFieldUsuario.setText(usuario.getUser());
            textFieldPuntos.setText(String.valueOf(usuario.getPuntos()));
            textFieldVictorias.setText(String.valueOf(usuario.getVictorias()));
            textFieldDerrotas.setText(String.valueOf(usuario.getDerrotas()));
            textFieldRacha.setText(String.valueOf(usuario.getRachaActual()));
            textFieldMejorRacha.setText(String.valueOf(usuario.getMejorRacha()));
        }      
    }

    /**
     * Metodo para iniciar el tablero
     */
    private void iniciarTablero(BoardConfig boardConfig) {
        deshabilitarVboxes();
        stackPaneContenedorTablero.getChildren().removeIf(node -> node instanceof GridPane);
        fadeIn(stackPaneContenedorTablero, miliSegundos);
        GridPane tablero = crearTablero(boardConfig.filas(), boardConfig.columnas(), boardConfig.minas());
        tablero.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        tablero.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        stackPaneContenedorTablero.getChildren().add(tablero);
        StackPane.setAlignment(tablero, Pos.CENTER);
    }

    /**
     * Metodo para desactivar los vboxes
     */
    private void deshabilitarVboxes() {
        fadeOut(mostrarPersonalizarVbox, miliSegundos);
        fadeOut(mostrarEstadisticasVbox, miliSegundos);
        fadeOut(mostrarAyudaVBox, miliSegundos);
        fadeOut(mostrarFinDelJuegoVbox, miliSegundos);
        fadeOut(mostrarTiendaVbox, miliSegundos);
        fadeOut(mostrarInventarioVbox, miliSegundos);
        fadeOut(mostrarMensajeVbox, miliSegundos);
    }

    /**
     * Metodo para calcular el tamanio del tablero segun el nivel de dificultad
     */
    private void manejarSeleccion(int indice) {
        ocultarBotones();
        BoardConfig config;
        switch (indice) {
            case 0: // Principiante
                config = new BoardConfig(10, 8, 10);
                dificultadMultiplier = 1.0;
                break;
            case 1: // Intermedio
                config = new BoardConfig(14, 10, 28);
                dificultadMultiplier = 1.5;
                break;
            case 2: // Experto
                config = new BoardConfig(18, 12, 43);
                dificultadMultiplier = 2.0;
                break;
            case 3: // Aleatorio
                config = generarTableroAleatorio();
                dificultadMultiplier = 1.2;
                break;
            case 4: // Personalizado
                accederPersonalizarTablero();
                return;
            default:
                config = new BoardConfig(10, 8, 10);
                dificultadMultiplier = 1.0;
                break;
        }
        iniciarTablero(config);
    }

    /**
     * Metodo para ocultar los botones de personalizacion
     */
    private void ocultarBotones() {
        fadeOut(personalizarButton, miliSegundos);
        aleatorioButton.setVisible(false);
    }

    /**
     * Metodo para acceder a la pantalla de personalizacion del tablero
     */
    private void accederPersonalizarTablero() {
        fadeOut(stackPaneContenedorTablero, miliSegundos);
        fadeOut(mostrarEstadisticasVbox, miliSegundos);
        fadeOut(mostrarAyudaVBox, miliSegundos);
        fadeOut(mostrarTiendaVbox, miliSegundos);
        fadeOut(mostrarInventarioVbox, miliSegundos);
        fadeIn(mostrarPersonalizarVbox, miliSegundos);
    }

    /**
     * Metodo para generar un tablero aleatorio
     */
    private BoardConfig generarTableroAleatorio() {
        Random random = new Random();
        int filas;
        int columnas;
        do {
            filas = random.nextInt(13) + 6; 
            columnas = random.nextInt(7) + 6;
        } while (!validarDimensiones(filas, columnas));
        int minas = (filas * columnas) / 5; 
        aleatorioButton.setVisible(true);
        return new BoardConfig(filas, columnas, minas);
    }

    /**
     * Metodo para validar las dimensiones de las celdas del tablero
     * @param filas del tablero
     * @param columnas del tablero
     * @return true si las dimensiones son validas, false en caso contrario
     */
    private boolean validarDimensiones(int filas, int columnas) {
        double cellWidth = stackPaneContenedorTablero.getWidth() / columnas;
        double cellHeight = stackPaneContenedorTablero.getHeight() / filas;
        return cellWidth >= 20 && cellHeight >= 20; 
    }

    /**
     * Metodo para pedir al usuario un tablero personalizado
     * @return tablero personalizado
     */
    @FXML
    private void onAceptarPersonalizadoClick() {
        try {
            int filas   = Integer.parseInt(textFieldFilas.getText());
            int columnas= Integer.parseInt(textFieldColumnas.getText());
            int minas   = Integer.parseInt(textFieldMinas.getText());
            if (filas <= 0 || columnas <= 0 || minas <= 0) {
                textMensajePersonalizar.setText("Los valores deben ser mayores que cero");
                return;
            }
            int maxMinas = filas * columnas - 9;
            if (minas > maxMinas) {
                textMensajePersonalizar.setText("Demasiadas minas. Máximo permitido: " + maxMinas);
                return;
            }
            if (!validarDimensiones(filas, columnas)) {
                textMensajePersonalizar.setText("Dimensiones demasiado grandes para la ventana");
                return;
            }
            double mineDensity = (double) minas / (filas * columnas);
            dificultadMultiplier = 1.0 + (mineDensity * 4);
            mostrarPersonalizarVbox.setVisible(false);
            personalizarButton.setVisible(true);
            iniciarTablero(new BoardConfig(filas, columnas, minas));
        } catch (NumberFormatException e) {
            textMensajePersonalizar.setText("Debes introducir números válidos");
        }
    }

    /**
     * Metodo para crear un tablero
     * @param filas del tablero
     * @param columnas del tablero
     * @return tablero
     */
    private GridPane crearTablero(int filas, int columnas, int numeroMinas) {
        GridPane grid = new GridPane();
        double cellWidth = stackPaneContenedorTablero.getWidth() / columnas;
        double cellHeight = stackPaneContenedorTablero.getHeight() / filas;
        double cellSize = Math.min(cellWidth, cellHeight);
        minas = new boolean[filas][columnas];
        minasAdyacentes = new int[filas][columnas];
        celdas = new Button[filas][columnas];
        gameOver = false;
        primerClick = true;
        banderasColocadas = 0;
        minasTotales = numeroMinas;
        textBanderas.setText(String.valueOf(minasTotales));
        segundosTranscurridos = 0;
        actualizarTemporizador();
        pararTemporizador();
        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                Button celda = new Button();
                celda.setPrefSize(cellSize, cellSize);
                celda.setStyle("-fx-font-size: 14;");
                celdas[fila][columna] = celda;
                final int filaActual = fila;
                final int columnaActual = columna;
                celda.setOnMouseClicked(event -> {
                    if (gameOver) return;
                    MouseButton button = event.getButton();
                    if (button == MouseButton.PRIMARY) {
                        revelarCelda(filaActual, columnaActual);
                        celda.setStyle("-fx-background-color: grey; -fx-opacity: 1;");
                    } else if (button == MouseButton.SECONDARY && !primerClick) {
                        colocarBandera(filaActual, columnaActual, celda);
                    }
                });
                grid.add(celda, columna, fila);
            }
        }
        configActual = new BoardConfig(filas, columnas, numeroMinas);
        return grid;
    }

    /**
     * Metodo para colocar las minas en el tablero
     * Se encarga de colocar las minas en el tablero evitando la primera celda seleccionada por el usuario
     * @param filaEvitar mina
     * @param columnaEvitar mina
     */
    private void colocarMinasEvitarPrimeraCasilla(int filaEvitar, int columnaEvitar) {
        Random random = new Random();
        int minesToPlace = minasTotales;
        Set<String> celdasProtejidas = new HashSet<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int fila = filaEvitar + i;
                int columna = columnaEvitar + j;
                if (fila >= 0 && fila < configActual.filas() && 
                    columna >= 0 && columna < configActual.columnas()) {
                    celdasProtejidas.add(fila + "," + columna);
                }
            }
        }
        while (minesToPlace > 0) {
            int fila = random.nextInt(configActual.filas());
            int columna = random.nextInt(configActual.columnas());
            
            if (!celdasProtejidas.contains(fila + "," + columna) && !minas[fila][columna]) {
                minas[fila][columna] = true;
                minesToPlace--;
            }
        }
        calcularMinasAdyacentes();
    }
    
    /**
     * Metodo para calcular las minas adyacentes a cada celda
     */
    private void calcularMinasAdyacentes() {
        for (int i = 0; i < configActual.filas(); i++) {
            for (int j = 0; j < configActual.columnas(); j++) {
                minasAdyacentes[i][j] = minas[i][j] ? -1 : contarMinasAdyacentes(i, j);
            }
        }
    }
    
    /**
     * Metodo para contar las minas adyacentes a una celda
     * @param fila de la celda  
     * @param columna de la celda
     * @return numero de minas adyacentes
     */
    private int contarMinasAdyacentes(int fila, int columna) {
        int contador = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int nuevaFila = fila + i;
                int nuevaColumna = columna + j;
                if (nuevaFila >= 0 && nuevaFila < configActual.filas() && 
                    nuevaColumna >= 0 && nuevaColumna < configActual.columnas()) {
                    if (minas[nuevaFila][nuevaColumna]) contador++;
                }
            }
        }
        return contador;
    }
    
    /**
     * Metodo para revelar una celda
     * Se encarga de revelar la celda seleccionada y mostrar el numero de minas adyacentes
     * @param fila de la celda
     * @param columna de la celda
     */
    private void revelarCelda(int fila, int columna) {
        if (gameOver || celdas[fila][columna].isDisabled()) return;
    
        if (primerClick) {
            primerClick = false;
            colocarMinasEvitarPrimeraCasilla(fila, columna);
            EmpezarTiempo();
        }
    
        if (minas[fila][columna]) {
            if (escudoActivado) {
                minas[fila][columna] = false;
                calcularMinasAdyacentes();
                escudoActivado = false;
                banderasColocadas++;
                textBanderas.setText(String.valueOf(minasTotales - banderasColocadas));
            } else {
                gameOver = true;
                pararTemporizador();
                revelarTodasMinas();
                mostrarDerrota();
            }
        }
        revealEmptyCells(fila, columna);
        revisarVictoria();
    }

    /**
     * Método para mostrar el mensaje de derrota
     */
    private void mostrarDerrota() {
        int baseLoss = 100;
        int timePenalty = segundosTranscurridos * 2;
        int penalties = (minasFantasmaUsadas * 50) + (escudosUsados * 30) + (zonasSegurasUsadas * 20);
        double totalLoss = (baseLoss * dificultadMultiplier) + timePenalty + penalties;
        int puntosPerdidos = (int) Math.round(totalLoss);
        int nuevosPuntos = Math.max(0, usuario.getPuntos() - puntosPerdidos);
        usuario.setPuntos(nuevosPuntos);
        int derrotasTotales = usuario.getDerrotas() + 1;
        usuario.setDerrotas(derrotasTotales);
        usuario.setRachaActual(0);
        if (usuario.getRachaActual() > usuario.getMejorRacha()) {
            usuario.setMejorRacha(usuario.getRachaActual());
        }
        textFieldPuntos.setText(String.valueOf(usuario.getPuntos()));
        textFieldDerrotas.setText(String.valueOf(derrotasTotales));
        textFieldRacha.setText("0");
        textFieldMejorRacha.setText(String.valueOf(usuario.getMejorRacha()));
        try {
            getUsuarioService().actualizarPuntosDerrotas(nuevosPuntos, 
            derrotasTotales, usuario.getMejorRacha(), usuario.getEmail()
            );
        } catch (Exception e) {
            e.printStackTrace();
        } 
        textFinJuego.setText("Fin del juego");
        textResultado.setText("Derrota");
        textFinJuego.setVisible(true);
        textResultado.setVisible(true);
        reintentarButton.setVisible(true);
        mostrarFinDelJuegoVbox.setVisible(true);
        mostrarFinDelJuegoVbox.toFront();
    }

    /**
     * Metodo para revelar las celdas vacias
     * @param fila de la celda
     * @param columna de la celda
     */
    private void revealEmptyCells(int fila, int columna) {
        if (fila < 0 || fila >= configActual.filas() || 
            columna < 0 || columna >= configActual.columnas() || 
            celdas[fila][columna].isDisabled()) {
            return;
        }
    
        Button celda = celdas[fila][columna];
        celda.setDisable(true);
        celda.setStyle("-fx-background-color: grey; -fx-opacity: 1;");
    
        if (minasAdyacentes[fila][columna] > 0) {
            celda.setText(String.valueOf(minasAdyacentes[fila][columna]));
            return;
        }
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                revealEmptyCells(fila + i, columna + j);
            }
        }
    }
    
    /**
     * Metodo para colocar una bandera en la celda
     * @param filas de la celda
     * @param columna de la celda
     * @param celda del seleccionada
     */
    private void colocarBandera(int filas, int columna, Button celda) {
        if (gameOver || celda.isDisabled()) return;
    
        if (celda.getText().equals("F")) {
            celda.setText("");
            banderasColocadas--;
        } else {
            if (banderasColocadas >= minasTotales) return;
            celda.setText("F");
            banderasColocadas++;
        }
        textBanderas.setText(String.valueOf(minasTotales - banderasColocadas));
    }
    
    /**
     * Metodo para revelar todas las minas al perder
     * Se encarga de mostrar todas las minas en el tablero y deshabilitar las celdas
     */
    private void revelarTodasMinas() {
        for (int i = 0; i < configActual.filas(); i++) {
            for (int j = 0; j < configActual.columnas(); j++) {
                if (minas[i][j]) {
                    Button celda = celdas[i][j];
                    celda.setText("X");
                    celda.setDisable(true);
                    celda.setStyle("-fx-background-color: red; -fx-opacity: 1;");
                }
            }
        }
    }
    
    /**
     * Metodo para revisar si el jugador ha ganado
     */
    private void revisarVictoria() {
        int noCeldasMinas = configActual.filas() * configActual.columnas() - minasTotales;
        int revelarCeldas = 0;
        for (int i = 0; i < configActual.filas(); i++) {
            for (int j = 0; j < configActual.columnas(); j++) {
                if (celdas[i][j].isDisabled() && !minas[i][j]) {
                    revelarCeldas++;
                }
            }
        }
        if (revelarCeldas == noCeldasMinas) {
            gameOver = true;
            pararTemporizador();
            mostrarVictoria();
            revelarTodasMinas();
        }
    }

    /**
     * Metodo para mostrar el mensaje de victoria
     */
    private void mostrarVictoria() {
        int basePoints = 100;
        int timeBonus = Math.max(0, 200 - segundosTranscurridos);
        int penalties = (minasFantasmaUsadas * 50) + (escudosUsados * 30) + (zonasSegurasUsadas * 20);
        double totalBeforeStreak = (basePoints * dificultadMultiplier) + timeBonus - penalties;
        double streakMultiplier = Math.pow(1.1, usuario.getRachaActual());
        int puntosGanados = (int) Math.round(totalBeforeStreak * streakMultiplier);
        int puntosTotales = usuario.getPuntos() + puntosGanados;
        usuario.setPuntos(puntosTotales);
        int victoriasTotales = usuario.getVictorias() + 1;
        usuario.setVictorias(victoriasTotales);
        int rachaActual = usuario.getRachaActual() + 1;
        usuario.setRachaActual(rachaActual);
        if (usuario.getRachaActual() > usuario.getMejorRacha()) {
            usuario.setMejorRacha(usuario.getRachaActual());
        }
        textFieldPuntos.setText(String.valueOf(usuario.getPuntos()));
        textFieldVictorias.setText(String.valueOf(usuario.getVictorias()));
        textFieldRacha.setText(String.valueOf(usuario.getRachaActual()));
        textFieldMejorRacha.setText(String.valueOf(usuario.getMejorRacha()));
        try {
            getUsuarioService().actualizarPuntosVictorias(puntosTotales, victoriasTotales, 
            rachaActual, usuario.getMejorRacha(), usuario.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
        textFinJuego.setText("¡Has Ganado!");
        textResultado.setText("Ganador");
        textFinJuego.setVisible(true);
        textResultado.setVisible(true);
        reintentarButton.setVisible(true);
        mostrarFinDelJuegoVbox.setVisible(true);
        mostrarFinDelJuegoVbox.toFront();
    }
    
    /**
     * Metodo para iniciar el temporizador
     * Se encarga de iniciar el temporizador y actualizar el tiempo transcurrido cada segundo
     */
    private void EmpezarTiempo() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            segundosTranscurridos++;
            actualizarTemporizador();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    
    /**
     * Metodo para actualizar el temporizador
     */
    private void actualizarTemporizador() {
        int minutos = segundosTranscurridos / 60;
        int segundos = segundosTranscurridos % 60;
        textTemporizador.setText(String.format("%02d:%02d", minutos, segundos));
    }
    
    /**
     * Metodo para detener el temporizador
     */
    private void pararTemporizador() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    /**
     * Metodo para pausar el temporizador
     */
    public void pausarTemporizador(boolean estaPausado) {
        if (timeline == null) return; 
        if (estaPausado) {
            timeline.play(); 
            estaPausado = false;
        } else {
            timeline.pause(); 
            estaPausado = true;
        }
    }

    /**
     * Metodo para cambiar la dificultad de la partida
     */
    @FXML
    protected void seleccionarDificultadClick() {
        int indice = comboDificultad.getSelectionModel().getSelectedIndex();
        manejarSeleccion(indice);
    }

    /**
     * Metodo que regresa al juego
     * Se encarga de regresar al juego y ocultar el menu de personalizacion
     */
    @FXML
    protected void onRegresarClick() {
        fadeOut(mostrarPersonalizarVbox, miliSegundos);
        fadeIn(stackPaneContenedorTablero, miliSegundos);
        regresarButton.setVisible(false);
        personalizarButton.setVisible(true);
        textMensajePersonalizar.setText("");
        pausarTemporizador(true);
    }

    /**
     * Metodo para personalizar el tablero
     */
    @FXML
    protected void onPersonalizarClick() {
        fadeOut(stackPaneContenedorTablero, miliSegundos);
        fadeOut(mostrarEstadisticasVbox, miliSegundos);
        fadeOut(mostrarAyudaVBox, miliSegundos);
        fadeOut(mostrarTiendaVbox, miliSegundos);
        fadeOut(mostrarInventarioVbox, miliSegundos);
        fadeIn(mostrarPersonalizarVbox, miliSegundos);
        personalizarButton.setVisible(false);
        regresarButton.setVisible(true);
        textMensajePersonalizar.setText("");
        pausarTemporizador(false);
    }

    /**
     * Metodo para generar un tablero aleatorio
     */
    @FXML
    protected void onAleatorioClick() {
        BoardConfig config = generarTableroAleatorio();
        iniciarTablero(config);
    }

    /**
     * Metodo para ver las estadisticas del usuario
     */
    @FXML
    protected void onEstadisticasClick() {
        boolean estadisticasVisible = mostrarEstadisticasVbox.isVisible();
        if (estadisticasVisible) {
            fadeOut(mostrarEstadisticasVbox, miliSegundos);
            switch (vistaAnterior) {
                case PERSONALIZACION: 
                    fadeIn(mostrarPersonalizarVbox, miliSegundos); 
                    break;
                case AYUDA:          
                    fadeIn(mostrarAyudaVBox, miliSegundos);      
                    break;
                case TIENDA:         
                    fadeIn(mostrarTiendaVbox, miliSegundos);     
                    break;
                case INVENTARIO:     
                    fadeIn(mostrarInventarioVbox, miliSegundos); 
                    break;
                default:             
                    fadeIn(stackPaneContenedorTablero, miliSegundos); 
                    if (primerClick) {
                        pausarTemporizador(false); 
                    } else {
                        pausarTemporizador(true);
                    }
                    break;
            }
        } else {
            if (mostrarPersonalizarVbox.isVisible()) {
                vistaAnterior = VistaActual.PERSONALIZACION;
            } else if (mostrarInventarioVbox.isVisible()) {
                vistaAnterior = VistaActual.INVENTARIO;
            } else if (mostrarEstadisticasVbox.isVisible()) {
                vistaAnterior = VistaActual.ESTADISTICAS;
            } else {
                vistaAnterior = VistaActual.TABLERO;
            }
            fadeOut(mostrarPersonalizarVbox, miliSegundos);
            fadeOut(mostrarAyudaVBox, miliSegundos);
            fadeOut(mostrarTiendaVbox, miliSegundos);
            fadeOut(mostrarInventarioVbox, miliSegundos);
            fadeOut(stackPaneContenedorTablero, miliSegundos);
            fadeIn(mostrarEstadisticasVbox, miliSegundos);
            pausarTemporizador(false);
        }
    }

    /**
     * Metodo que abre el menu de ayuda
     */
    @FXML
    protected void onAyudaClick() {
        boolean ayudaVisible = mostrarAyudaVBox.isVisible();
        if (ayudaVisible) {
            fadeOut(mostrarAyudaVBox, miliSegundos);
            switch (vistaAnterior) {
                case PERSONALIZACION: 
                    fadeIn(mostrarPersonalizarVbox, miliSegundos); 
                    break;
                case ESTADISTICAS:   
                    fadeIn(mostrarEstadisticasVbox, miliSegundos); 
                    break;
                case TIENDA:         
                    fadeIn(mostrarTiendaVbox, miliSegundos);      
                    break;
                case INVENTARIO:     
                    fadeIn(mostrarInventarioVbox, miliSegundos);  
                    break;
                default:             
                    fadeIn(stackPaneContenedorTablero, miliSegundos); 
                    if (primerClick) {
                        pausarTemporizador(false); 
                    } else {
                        pausarTemporizador(true);
                    }
                    break;
            }
        } else {
            if (mostrarPersonalizarVbox.isVisible()) {
                vistaAnterior = VistaActual.PERSONALIZACION;
            } else if (mostrarEstadisticasVbox.isVisible()) {
                vistaAnterior = VistaActual.ESTADISTICAS;
            } else if (mostrarTiendaVbox.isVisible()) {
                vistaAnterior = VistaActual.TIENDA;
            } else {
                vistaAnterior = VistaActual.TABLERO;
            }
            fadeOut(mostrarPersonalizarVbox, miliSegundos);
            fadeOut(mostrarEstadisticasVbox, miliSegundos);
            fadeOut(mostrarTiendaVbox, miliSegundos);
            fadeOut(mostrarInventarioVbox, miliSegundos);
            fadeOut(stackPaneContenedorTablero, miliSegundos);
            fadeIn(mostrarAyudaVBox, miliSegundos);
            pausarTemporizador(false);
        }
    }

    /**
     * Metodo que maneja el evento de clic en el boton de reintentar
     * Reinicia el juego y vuelve a cargar el tablero
     */
    @FXML
    protected void onReintentarClick() {
        fadeOut(mostrarFinDelJuegoVbox, miliSegundos);
        fadeOut(textFinJuego, miliSegundos);
        fadeOut(textResultado, miliSegundos);
        fadeOut(reintentarButton, miliSegundos);
        iniciarTablero(configActual);
    }

    /**
     * Metodo que maneja el evento de clic en el boton de tienda
     */
    @FXML
    private void onTiendaClick() {
        boolean tiendaVisible = mostrarTiendaVbox.isVisible();
        if (tiendaVisible) {
            fadeOut(mostrarTiendaVbox, miliSegundos);
            switch (vistaAnterior) {
                case PERSONALIZACION:
                    fadeIn(mostrarPersonalizarVbox, miliSegundos);
                    break;
                case ESTADISTICAS:
                    fadeIn(mostrarEstadisticasVbox, miliSegundos);
                    break;
                case AYUDA:
                    fadeIn(mostrarAyudaVBox, miliSegundos);
                    break;
                case INVENTARIO:
                    fadeIn(mostrarInventarioVbox, miliSegundos);
                    break;
                default:
                    fadeIn(stackPaneContenedorTablero, miliSegundos);
                    if (primerClick) {
                        pausarTemporizador(false); 
                    } else {
                        pausarTemporizador(true);
                    }
                    break;
            }
        } else {
            if (mostrarPersonalizarVbox.isVisible()) {
                vistaAnterior = VistaActual.PERSONALIZACION;
            } else if (mostrarEstadisticasVbox.isVisible()) {
                vistaAnterior = VistaActual.ESTADISTICAS;
            } else if (mostrarAyudaVBox.isVisible()) {
                vistaAnterior = VistaActual.AYUDA;
            } else {
                vistaAnterior = VistaActual.TABLERO;
            }
            fadeOut(mostrarPersonalizarVbox, miliSegundos);
            fadeOut(mostrarAyudaVBox, miliSegundos);
            fadeOut(mostrarEstadisticasVbox, miliSegundos);
            fadeOut(mostrarInventarioVbox, miliSegundos);
            fadeOut(stackPaneContenedorTablero, miliSegundos);
            fadeIn(mostrarTiendaVbox, miliSegundos);
            pausarTemporizador(false);
        }
    }

    /**
     * Metodo que maneja el evento de clic en el boton de volver a la tienda
     * Vuelve a la tienda y oculta el tablero
     */
    @FXML
    private void onVolverTiendaClick() {
        fadeOut(mostrarTiendaVbox, miliSegundos);
        fadeIn(stackPaneContenedorTablero, miliSegundos);
    }

    /**
     * Metodo que maneja el evento de clic en el boton de comprar mina fantasma
     * Se encarga de comprar una mina fantasma y actualizar la interfaz
     */
    @FXML
    private void onComprarMinaFantasmaClick() {
        int puntos = usuario.getPuntos();
        if (puntos < 0) {
            mostrarMensaje("No tienes suficientes puntos para comprar una mina fantasma.");
            return;
        } else if (minasFantasmaDisponibles >= 9) {
            mostrarMensaje("No puedes tener más de 9 minas fantasma.");
            return;
        }
        //usuario.setPuntos(puntos - 50);
        textPuntosTienda.setText(String.valueOf(usuario.getPuntos()));
        textPuntosInventario.setText(String.valueOf(usuario.getPuntos()));
        minasFantasmaDisponibles++;
        badgeLabelFantasma.setText(String.valueOf(minasFantasmaDisponibles));
    }

    /**
     * Metodo que maneja el evento de clic en el boton de usar mina fantasma
     * Se encarga de usar una mina fantasma y resaltar una mina aleatoria en el tablero
     */
    @FXML
    private void onUsarMinaFantasmaClick() {
        if (minasFantasmaDisponibles > 0 && !primerClick) {
            minasFantasmaUsadas++;
            minasFantasmaDisponibles--;
            badgeLabelFantasma.setText(String.valueOf(minasFantasmaDisponibles));
            volverAlJuegoInventario();
            resaltarMinaAleatoria();
            return;
        }
        mostrarMensaje("No tienes minas fantasma disponibles o ya has usado una.");
    }

    /**
     * Metodo que resalta una mina aleatoria en el tablero
     * Se encarga de resaltar una mina aleatoria en el tablero durante 5 segundos
     */
    private void resaltarMinaAleatoria() {
        List<int[]> minasPosiciones = new ArrayList<>();
        for (int i = 0; i < configActual.filas(); i++) {
            for (int j = 0; j < configActual.columnas(); j++) {
                if (minas[i][j]) {
                    minasPosiciones.add(new int[]{i, j});
                }
            }
        }
        if (!minasPosiciones.isEmpty()) {
            Random random = new Random();
            int[] posicion = minasPosiciones.get(random.nextInt(minasPosiciones.size()));
            Button celda = celdas[posicion[0]][posicion[1]];
            String estiloOriginal = celda.getStyle();
            celda.setStyle(estiloOriginal + "-fx-background-color: yellow;");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
                celda.setStyle(estiloOriginal);
            }));
            timeline.play();
        }
    }
    
    /**
     * Metodo que maneja el evento de clic en el boton de comprar escudo
     * Se encarga de comprar un escudo y actualizar la interfaz
     */
    @FXML
    private void onComprarEscudoClick() {
        int puntos = usuario.getPuntos();
        if (puntos < 0) {
            mostrarMensaje("No tienes suficientes puntos para comprar un escudo.");
            return;
        } else if (escudosDisponibles >= 9) {
            mostrarMensaje("No puedes tener más de 9 escudos.");
            return;
        }
        try {
            getUsuarioPowerupsService().comprarPowerUp(usuario, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //usuario.setPuntos(usuario.getPuntos() - 100);
        textPuntosTienda.setText(String.valueOf(usuario.getPuntos()));
        textPuntosInventario.setText(String.valueOf(usuario.getPuntos()));
        escudosDisponibles++;
        badgeLabelEscudo.setText(String.valueOf(escudosDisponibles));
    }

    /**
     * Metodo que maneja el evento de clic en el boton de usar escudo
     * Se encarga de activar el escudo y actualizar la interfaz
     */
    @FXML
    private void onUsarEscudoClick() {
        if (escudosDisponibles > 0 && !escudoActivado) {
            escudoActivado = true;
            escudosUsados++;
            escudosDisponibles--;
            try {
                getUsuarioPowerupsService().actualizarUsuarioPoderes(escudosDisponibles, usuario.getId(), 2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            badgeLabelEscudo.setText(String.valueOf(escudosDisponibles));
            volverAlJuegoInventario();
            return;
        }
        mostrarMensaje("No tienes escudos disponibles o ya está activado uno.");
    }

    /**
     * Metodo que ee encarga de volver al juego y ocultar el inventario
     */
    private void volverAlJuegoInventario() {
        fadeOut(mostrarInventarioVbox, miliSegundos);
        fadeIn(stackPaneContenedorTablero, miliSegundos);
    }

    /**
     * Metodo para mostrar un mensaje en la interfaz
     * @param mensaje a mostrar
     */
    private void mostrarMensaje(String mensaje) {
        textMensaje.setText(mensaje);
        fadeIn(mostrarMensajeVbox, miliSegundos);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), 
            e -> fadeOut(mostrarMensajeVbox, miliSegundos))
        );
        timeline.play();
    }

    /**
     * Metodo que maneja el evento de clic en el boton de comprar zona segura
     * Se encarga de comprar una zona segura y actualizar la interfaz
     */
    @FXML
    private void onComprarZonaSeguraClick() {
        int puntos = usuario.getPuntos();
        if (puntos >= 0) {
            //usuario.setPuntos(usuario.getPuntos() - 80);
            zonasSegurasDisponibles++;
        }
    }

    /**
     * Metodo que maneja el evento de clic en el boton de usar zona segura
     * Se encarga de revelar las celdas adyacentes a la celda seleccionada
     */
    @FXML
    private void onUsarZonaSeguraClick() {
        
    }

    /**
     * Metodo que maneja el evento de clic en el boton de inventario
     * Se encarga de mostrar u ocultar el inventario del usuario
     */
    @FXML
    private void onInventarioClick() {
        boolean inventarioVisible = mostrarInventarioVbox.isVisible();
        if (inventarioVisible) {
            fadeOut(mostrarInventarioVbox, miliSegundos);
            switch (vistaAnterior) {
                case PERSONALIZACION: 
                    fadeIn(mostrarPersonalizarVbox, miliSegundos); 
                    break;
                case ESTADISTICAS:   
                    fadeIn(mostrarEstadisticasVbox, miliSegundos); 
                    break;
                case AYUDA:          
                    fadeIn(mostrarAyudaVBox, miliSegundos);      
                    break;
                case TIENDA:         
                    fadeIn(mostrarTiendaVbox, miliSegundos);     
                    break;
                default:             
                    fadeIn(stackPaneContenedorTablero, miliSegundos); 
                    if (primerClick) {
                        pausarTemporizador(false); 
                    } else {
                        pausarTemporizador(true);
                    }
                    break;
            }
        } else {
            if (mostrarPersonalizarVbox.isVisible()) {
                vistaAnterior = VistaActual.PERSONALIZACION;
            } else if (mostrarEstadisticasVbox.isVisible()) {
                vistaAnterior = VistaActual.ESTADISTICAS;
            } else if (mostrarAyudaVBox.isVisible()) {
                vistaAnterior = VistaActual.AYUDA;
            } else if (mostrarTiendaVbox.isVisible()) {
                vistaAnterior = VistaActual.TIENDA;
            } else {
                vistaAnterior = VistaActual.TABLERO;
            } 
            fadeOut(mostrarPersonalizarVbox, miliSegundos);
            fadeOut(mostrarEstadisticasVbox, miliSegundos);
            fadeOut(mostrarAyudaVBox, miliSegundos);
            fadeOut(mostrarTiendaVbox, miliSegundos);
            fadeOut(stackPaneContenedorTablero, miliSegundos);
            fadeIn(mostrarInventarioVbox, miliSegundos);
            pausarTemporizador(false);
        }
    }

     /** 
      * Hace un fade-in (opacidad 0→1) y deja el nodo visible 
      */
    private void fadeIn(Node node, double millis) {
        node.setOpacity(0);
        node.setVisible(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(millis), node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    /** 
     * Hace un fade-out (1→0) y al terminar oculta el nodo
     * 
     */
    private void fadeOut(Node node, double millis) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(millis), node);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(e -> {
            node.setVisible(false);
            node.setOpacity(1);
        });
        fadeTransition.play();
    }

    /**
     * Metodo que carga la imagen del objeto en la pantalla de informacion
     * @param nombreImagen de la imagen, debe estar en la carpeta img y
     * junto al nombre añadir el formato de la imagen (png, jpg, etc.)
     */
    private void cargarImagen(String nombreImagen) {
        try {
            String imagePath = "/es/ies/puerto/img/" + nombreImagen;
            Image image = new Image(getClass().getResource(imagePath).toExternalForm());
            imageViewObjeto.setImage(image);
        } catch (NullPointerException e) {
            System.err.println("Error: Image not found at the specified path.");
            imageViewObjeto.setImage(null);
        }
    }

    /**
     * Metodo que carga la informacion del objeto en la pantalla de informacion
     * @param nombreImagen nombre de la imagen, debe estar en la carpeta img y
     * junto al nombre añadir el formato de la imagen (png, jpg, etc.)
     * @param claveObjeto es la clave del objeto en el archivo de propiedades de idiomas
     * @param claveInformacion es la clave de la informacion en el archivo de propiedades de idiomas
     */
    private void cargarInformacionObjeto(String nombreImagen, String claveObjeto, String claveInformacion) {
        fadeIn(mostrarInformacionVbox, miliSegundos);
        cargarImagen(nombreImagen);
        textObjeto.setText(ConfigManager.ConfigProperties.getProperty(claveObjeto));
        textInformacion.setText(ConfigManager.ConfigProperties.getProperty(claveInformacion));
    }

    /**
     * Maneja el evento de clic en el boton de informacion del escudo
     * Muestra la informacion del escudo en la interfaz
     */
    @FXML
    protected void onEscudoInformacionClick() {
        cargarInformacionObjeto("usuario.png", "escudo", "infoEscudo");
    }

    @FXML
    protected void onFantasmaInformacionClick() {
        cargarInformacionObjeto("usuario.png", "fantasma", "infoFantasma");
    }

    /**
     * Metodo para salir de la pantalla de informacion del objeto
     * Se encarga de ocultar la pantalla de informacion y volver a la tienda o inventario
     */
    @FXML
    protected void onSalirInfomacionClick() {
        fadeOut(mostrarInformacionVbox, miliSegundos);
    }

    /**
     * Maneja el evento de clic en el boton de volver atras
     * Redirige a la pantalla anterior
     */
    @FXML
    protected void onVolverAtrasClick() {
        Stage stage = (Stage) buttonVolverAtras.getScene().getWindow();
        if (getPreviousScene() != null) {
            stage.setTitle(getPreviousTitle());
            stage.setScene(getPreviousScene());
        } else {
            textMensaje.setText("errorPreviousScene");
        }
    }
}