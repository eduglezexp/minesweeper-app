package es.ies.puerto.controller;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import es.ies.puerto.config.ConfigManager;
import es.ies.puerto.config.Sesion;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.controller.enums.VistaActual;
import es.ies.puerto.model.entities.UsuarioEntity;
import es.ies.puerto.model.entities.UsuarioPowerupsEntity;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
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
    private VBox mostrarInventarioVbox;

    @FXML 
    private Text textPuntosInventario;

    @FXML 
    private Button usarMinaFantasmaButton;

    @FXML 
    private Button usarEscudoButton;

    @FXML 
    private Button usarAlquimiaButton;

    @FXML 
    private Label badgeLabelEscudo;

    @FXML 
    private Label badgeLabelFantasma;

    @FXML 
    private Label badgeLabelAlquimia;

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
    private boolean escudoActivado = false;
    private int miliSegundos = 300;
    private UsuarioEntity usuario;
    private double dificultadMultiplier = 1.0;
    private int minasFantasmaUsadas = 0;
    private int escudosUsados = 0;
    private int alquimiaUsadas = 0;
    private boolean modoAlquimiaActivo = false;
    private EventHandler<MouseEvent> alquimiaHandler;
    private static final int COSTO_ESCUDO = 100;
    private static final int COSTO_MINA_FANTASMA = 50;
    private static final int COSTO_ALQUIMIA= 0;
    private static final int COSTO_TEMAS = 150;
    private static final int MAX_POWERUPS = 9;
    private static final int MINA_FANTASMA_ID = 1;
    private static final int ESCUDO_ID = 2;
    private static final int ALQUIMIA_ID = 3;
    private static final int TEMA_OSCURO_ID = 1;
    private static final int TEMA_NATURALEZA_ID = 2;
    private static final int TEMA_RETRO_ID = 3;
    private static final int FLAG_CODE = 0x1F6A9;
    private static final String FLAG_EMOJI = new String(Character.toChars(FLAG_CODE));

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
        if (usuario == null) return;
        textFieldUsuario.setText(usuario.getUser());
        textFieldVictorias.setText(String.valueOf(usuario.getVictorias()));
        textFieldDerrotas.setText(String.valueOf(usuario.getDerrotas()));
        textFieldRacha.setText(String.valueOf(usuario.getRachaActual()));
        textFieldMejorRacha.setText(String.valueOf(usuario.getMejorRacha()));
        actualizarPuntosEnVista(usuario.getPuntos());
        cargarBadgesPowerups();
    }

    /**
     * Metodo para cargar los powerups del usuario
     */
    private void cargarBadgesPowerups() {
        try {
            List<UsuarioPowerupsEntity> lista = getUsuarioPowerupsService()
                .obtenerUsuariosPowerupsPorId(usuario.getId());
            int escudos = lista.stream()
                                  .filter(p -> p.getPowerupId() == ESCUDO_ID)
                                  .findFirst()
                                  .map(UsuarioPowerupsEntity::getCantidad)
                                  .orElse(0);
            int fantasmas = lista.stream()
                                  .filter(p -> p.getPowerupId() == MINA_FANTASMA_ID)
                                  .findFirst()
                                  .map(UsuarioPowerupsEntity::getCantidad)
                                  .orElse(0);
            int alquimia = lista.stream()
                                  .filter(p -> p.getPowerupId() == ALQUIMIA_ID)
                                  .findFirst()
                                  .map(UsuarioPowerupsEntity::getCantidad)
                                  .orElse(0);
            badgeLabelEscudo.setText(String.valueOf(escudos));
            badgeLabelFantasma.setText(String.valueOf(fantasmas));
            badgeLabelAlquimia.setText(String.valueOf(alquimia));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Metodo para iniciar el tablero
     * @param boardConfig configuracion del tablero
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
                config = new BoardConfig(17, 11, 38);
                dificultadMultiplier = 1.5;
                break;
            case 2: // Experto
                config = new BoardConfig(24, 14, 70);
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
            filas = random.nextInt(21) + 6; 
            columnas = random.nextInt(9) + 6;
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
                textMensajePersonalizar.setText(ConfigManager.ConfigProperties.getProperty("errorValores"));
                return;
            }
            int maxMinas = filas * columnas - 9;
            if (minas > maxMinas) {
                textMensajePersonalizar.setText(ConfigManager.ConfigProperties.getProperty("errorMinas") + maxMinas);
                return;
            }
            if (!validarDimensiones(filas, columnas)) {
                textMensajePersonalizar.setText(ConfigManager.ConfigProperties.getProperty("errorDimensiones"));
                return;
            }
            double mineDensity = (double) minas / (filas * columnas);
            dificultadMultiplier = 1.0 + (mineDensity * 4);
            mostrarPersonalizarVbox.setVisible(false);
            personalizarButton.setVisible(true);
            iniciarTablero(new BoardConfig(filas, columnas, minas));
        } catch (NumberFormatException e) {
            textMensajePersonalizar.setText(ConfigManager.ConfigProperties.getProperty("errorNumerosValidos"));
        }
    }

    /**
     * Metodo para inicializar las variables
     * @param filas del tablero
     * @param columnas del tablero
     * @param numeroMinas del tablero
     */
    private void inicializarVariables(int filas, int columnas, int numeroMinas) {
        minas = new boolean[filas][columnas];
        minasAdyacentes = new int[filas][columnas];
        celdas = new Button[filas][columnas];
        gameOver = false;
        primerClick = true;
        banderasColocadas = 0;
        minasTotales = numeroMinas;
        textBanderas.setText(String.valueOf(minasTotales));
        segundosTranscurridos = 0;
    }

    /**
     * Metodo para crear un tablero
     * @param filas del tablero
     * @param columnas del tablero
     * @param numeroMinas del tablero
     * @return tablero
     */
    private GridPane crearTablero(int filas, int columnas, int numeroMinas) {
        GridPane grid = new GridPane();
        double cellWidth = stackPaneContenedorTablero.getWidth() / columnas;
        double cellHeight = stackPaneContenedorTablero.getHeight() / filas;
        double cellSize = Math.min(cellWidth, cellHeight);
        double fontSize = cellSize * 0.35; 
        Font cellFont = Font.font(fontSize);
        inicializarVariables(filas, columnas, numeroMinas);
        actualizarTemporizador();
        pararTemporizador();
        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                Button celda = new Button();
                celda.setFont(cellFont);
                celda.setPrefSize(cellSize, cellSize);
                celda.setMinSize(cellSize, cellSize);
                celda.setMaxSize(cellSize, cellSize);
                celda.setTranslateY(-cellSize * 2);  
                celda.setOpacity(0);
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
                animarIniciarTablero(celda, fila, columna, columnas, cellSize);
            }
        }
        configActual = new BoardConfig(filas, columnas, numeroMinas);
        return grid;
    }

    /**
     * Metodo para animar las celdas del tablero al iniciar el juego
     * @param celda del tablero
     * @param fila del tablero
     * @param columna del tablero
     * @param columnas del tablero
     * @param cellSize del tablero
     */
    private void animarIniciarTablero(Button celda, int fila, int columna, int columnas, double cellSize) {
        TranslateTransition caida = new TranslateTransition(Duration.millis(100), celda);
        caida.setToY(0);
        caida.setDelay(Duration.millis((fila * columnas + columna) * 2.5));
        caida.setInterpolator(Interpolator.EASE_OUT);
    
        FadeTransition aparicion = new FadeTransition(Duration.millis(50), celda);
        aparicion.setToValue(1);
    
        SequentialTransition secuencia = new SequentialTransition(
            new PauseTransition(caida.getDelay()),
            new ParallelTransition(caida, aparicion)
        );
        secuencia.play();
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
        int penalties = (minasFantasmaUsadas * 50) + (escudosUsados * 30) + (alquimiaUsadas * 20);
        double totalLoss = (baseLoss * dificultadMultiplier) + timePenalty + penalties;
        int puntosPerdidos = (int) Math.round(totalLoss);
        int nuevosPuntos = Math.max(0, usuario.getPuntos() - puntosPerdidos);
        usuario.setPuntos(nuevosPuntos);
        int derrotasTotales = usuario.getDerrotas() + 1;
        usuario.setDerrotas(derrotasTotales);
        int currentRacha = usuario.getRachaActual();
        usuario.setRachaActual(0);
        if (currentRacha > usuario.getMejorRacha()) {
            usuario.setMejorRacha(currentRacha);
        }
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
        actualizarPuntosEnVista(nuevosPuntos);
        textFinJuego.setText(ConfigManager.ConfigProperties.getProperty("mensajeDerrota"));
        String estadisticasFormat = ConfigManager.ConfigProperties.getProperty("estadisticasDerrota");
        String estadisticasTexto = MessageFormat.format(estadisticasFormat, 
            baseLoss, 
            timePenalty, 
            penalties, 
            totalLoss,
            nuevosPuntos);
        textResultado.setText(estadisticasTexto);
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
    
        if (FLAG_EMOJI.equals(celda.getText())) {
            celda.setText("");
            banderasColocadas--;
        } else {
            if (banderasColocadas >= minasTotales) return;
            celda.setText(FLAG_EMOJI);
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
                    animarSuperMina(celda);
                    celda.setDisable(true);
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
        int penalties = (minasFantasmaUsadas * 50) + (escudosUsados * 30) + (alquimiaUsadas * 20);
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
        textFieldVictorias.setText(String.valueOf(usuario.getVictorias()));
        textFieldRacha.setText(String.valueOf(usuario.getRachaActual()));
        textFieldMejorRacha.setText(String.valueOf(usuario.getMejorRacha()));
        try {
            getUsuarioService().actualizarPuntosVictorias(puntosTotales, victoriasTotales, 
            rachaActual, usuario.getMejorRacha(), usuario.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
        actualizarPuntosEnVista(puntosTotales);
        textFinJuego.setText(ConfigManager.ConfigProperties.getProperty("mensajeVictoria"));
        String estadisticasFormat = ConfigManager.ConfigProperties.getProperty("estadisticasVictoria");
        String estadisticasTexto = MessageFormat.format(estadisticasFormat, 
            basePoints, 
            dificultadMultiplier, 
            timeBonus, 
            penalties, 
            String.format("%.1f", streakMultiplier), 
            puntosGanados);
        textResultado.setText(estadisticasTexto);
        textFinJuego.setVisible(true);
        textResultado.setVisible(true);
        reintentarButton.setText(ConfigManager.ConfigProperties.getProperty("jugarDeNuevo"));
        reintentarButton.setVisible(true);
        fadeIn(mostrarFinDelJuegoVbox, miliSegundos);
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
     * Metodo  para comprar un powerup
     * Se encarga de comprar un powerup y actualizar la interfaz
     */
    private void comprarPowerUp(int powerupId, int costo, Label badgeLabel) {
        int puntos = usuario.getPuntos();
        int actuales = Integer.parseInt(badgeLabel.getText());

        if (puntos < costo) {
            mostrarMensaje(ConfigManager.ConfigProperties.getProperty("noSuficientesPuntos"));
            return;
        }
        if (actuales >= MAX_POWERUPS) {
            mostrarMensaje(ConfigManager.ConfigProperties.getProperty("maximoItem"));
            return;
        }
        try {
            getUsuarioPowerupsService().comprarPowerUp(usuario, powerupId);
            usuario.setPuntos(puntos - costo);
            actualizarPuntosEnVista(usuario.getPuntos());
            getUsuarioService().actualizarPuntos(usuario.getPuntos(), usuario.getEmail());
            badgeLabel.setText(String.valueOf(actuales + 1));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Actualiza todos los campos de puntos en la vista.
     */
    private void actualizarPuntosEnVista(int puntos) {
        textFieldPuntos.setText(String.valueOf(puntos));
        textPuntosTienda.setText(String.valueOf(puntos));
        textPuntosInventario.setText(String.valueOf(puntos));
    }

    /**
     * Metodo que maneja el evento de clic en el boton de comprar mina fantasma
     * Se encarga de comprar una mina fantasma y actualizar la interfaz
     */
    @FXML
    private void onComprarMinaFantasmaClick() {
        comprarPowerUp(MINA_FANTASMA_ID, COSTO_MINA_FANTASMA, badgeLabelFantasma);
    }

    /**
     * Metodo que maneja el evento de clic en el boton de usar mina fantasma
     * Se encarga de usar una mina fantasma y resaltar una mina aleatoria en el tablero
     */
    @FXML
    private void onUsarMinaFantasmaClick() {
        int minasFantasma = Integer.parseInt(badgeLabelFantasma.getText());
        if (minasFantasma > 0 && !primerClick) {
            minasFantasma--;
            try {
                getUsuarioPowerupsService().actualizarUsuarioPoderes(
                minasFantasma, usuario.getId(), MINA_FANTASMA_ID);
            } catch (Exception e) {
                e.printStackTrace();
            }
            badgeLabelFantasma.setText(String.valueOf(minasFantasma));
            volverAlJuegoInventario();
            resaltarMinaAleatoria();
            return;
        }
        mostrarMensaje(ConfigManager.ConfigProperties.getProperty("noMinasFantasmas"));
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
        comprarPowerUp(ESCUDO_ID, COSTO_ESCUDO, badgeLabelEscudo);
    }

    /**
     * Metodo que maneja el evento de clic en el boton de usar escudo
     * Se encarga de activar el escudo y actualizar la interfaz
     */
    @FXML
    private void onUsarEscudoClick() {
        int escudos = Integer.parseInt(badgeLabelEscudo.getText());
        if (escudos > 0 && !escudoActivado) {
            escudoActivado = true;
            escudos--;
            try {
                getUsuarioPowerupsService().actualizarUsuarioPoderes(
                escudos, usuario.getId(), ESCUDO_ID);
            } catch (Exception e) {
                e.printStackTrace();
            }
            badgeLabelEscudo.setText(String.valueOf(escudos));
            volverAlJuegoInventario();
            return;
        }
        mostrarMensaje(ConfigManager.ConfigProperties.getProperty("noEscudo"));
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
    private void onComprarAlquimiaClick() {
        comprarPowerUp(ALQUIMIA_ID, COSTO_ALQUIMIA, badgeLabelAlquimia);
    }

    /**
     * Metodo que maneja el evento de clic en el boton de usar zona segura
     * Se encarga de revelar las celdas adyacentes a la celda seleccionada
     */
    @FXML
    private void onUsarAlquimiaClick() {
        int alquimia = Integer.parseInt(badgeLabelAlquimia.getText());
        if (alquimia > 0 && !primerClick) {
            modoAlquimiaActivo = true;
            alquimia--;
            try {
                getUsuarioPowerupsService().actualizarUsuarioPoderes(
                alquimia, usuario.getId(), ALQUIMIA_ID);
            } catch (Exception e) {
                e.printStackTrace();
            }
            badgeLabelAlquimia.setText(String.valueOf(alquimia));
            volverAlJuegoInventario();
            activarModoSeleccionAlquimia();
            return;
        }
        mostrarMensaje(ConfigManager.ConfigProperties.getProperty("noAlquimia"));
    }

    /**
     * Metodo que activa el modo de seleccion de alquimia
     * Se encarga de activar el modo de seleccion de alquimia y manejar el evento de clic en las celdas
     */
    private void activarModoSeleccionAlquimia() {
        Scene scene = stackPaneContenedorTablero.getScene();
        scene.setCursor(Cursor.CROSSHAIR);
        alquimiaHandler = event -> {
            if (event.getTarget() instanceof Button celda) {
                Integer fila = GridPane.getRowIndex(celda);
                Integer columna = GridPane.getColumnIndex(celda);
                if (fila != null && columna != null) {
                    manejarConversionAlquimia(fila, columna, celda);
                }
                desactivarModoSeleccionAlquimia();
            }
        };
        stackPaneContenedorTablero.addEventFilter(MouseEvent.MOUSE_CLICKED, alquimiaHandler);
    }

    /**
     * Metodo que maneja la conversion de la celda seleccionada
     * Se encarga de convertir la celda seleccionada en una celda segura o en una super mina
     * @param fila de la celda
     * @param columna de la celda
     * @param celda del seleccionada
     */
    private void manejarConversionAlquimia(int fila, int columna, Button celda) {
        if (!minas[fila][columna]) {
            mostrarMensaje(ConfigManager.ConfigProperties.getProperty("errorAlquimia"));
            desactivarModoSeleccionAlquimia(); 
            return;
        }
        double probabilidad = Math.random();
        try {
            if (probabilidad <= 0.5) {
                convertirEnUtil(fila, columna, celda);
            } else if (probabilidad <= 0.75) {
                convertirEnSuperMina(fila, columna, celda);
            } else {
                convertirEnCeldaSegura(fila, columna, celda);
            }
        } finally {
            desactivarModoSeleccionAlquimia();
        }
    }

    /**
     * Metodo para convertir una celda en una celda util
     * Se encarga de convertir la celda seleccionada en una celda segura y actualizar el contador de minas
     * @param fila de la celda
     * @param columna de la celda
     * @param celda del seleccionada
     */
    private void convertirEnUtil(int fila, int columna, Button celda) {
        int current = Integer.parseInt(badgeLabelAlquimia.getText());
        current++;
        badgeLabelAlquimia.setText(String.valueOf(current));
        try {
            getUsuarioPowerupsService()
                .actualizarUsuarioPoderes(current, usuario.getId(), ALQUIMIA_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        minas[fila][columna] = false;
        minasTotales--;
        textBanderas.setText(String.valueOf(minasTotales - banderasColocadas));
        minasAdyacentes[fila][columna] = contarMinasAdyacentes(fila, columna);
        revelarCelda(fila, columna);
        animarConversionExitosa(celda);
    }
    
    /**
     * Metodo para convertir una celda en una super mina
     * Se encarga de convertir la celda seleccionada en una super mina y actualizar el contador de minas
     * @param fila de la celda
     * @param columna de la celda
     * @param celda del seleccionada
     */
    private void convertirEnSuperMina(int fila, int columna, Button celda) {
        minas[fila][columna] = true;
        minasAdyacentes[fila][columna] = -2; 
        animarSuperMina(celda);
    }
    
    /**
     * Metodo para convertir una celda en una celda segura
     * Se encarga de convertir la celda seleccionada en una celda segura y actualizar el contador de minas
     * @param fila de la celda
     * @param columna de la celda
     * @param celda del seleccionada
     */
    private void convertirEnCeldaSegura(int fila, int columna, Button celda) {
        minas[fila][columna] = false;
        minasTotales--; 
        textBanderas.setText(String.valueOf(minasTotales - banderasColocadas)); 
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int nuevaFila = fila + i;
                int nuevaColumna = columna + j;
                if (nuevaFila >= 0 && nuevaFila < configActual.filas() && 
                    nuevaColumna >= 0 && nuevaColumna < configActual.columnas()) {
                    if (!minas[nuevaFila][nuevaColumna]) {
                        minasAdyacentes[nuevaFila][nuevaColumna] = contarMinasAdyacentes(nuevaFila, nuevaColumna);
                        if (celdas[nuevaFila][nuevaColumna].isDisabled()) {
                            int count = minasAdyacentes[nuevaFila][nuevaColumna];
                            celdas[nuevaFila][nuevaColumna].setText(count > 0 ? String.valueOf(count) : "");
                        }
                    }
                }
            }
        }
        minasAdyacentes[fila][columna] = contarMinasAdyacentes(fila, columna);
        revelarCelda(fila, columna);
        animarCeldaSegura(celda);
    }

    /**
     * Metodo para desactivar el modo seleccion de alquimia 
     * @param fila de la celda
     * @param columna de la celda
     */
    private void desactivarModoSeleccionAlquimia() {
        modoAlquimiaActivo = false;
        Scene scene = stackPaneContenedorTablero.getScene();
        scene.setCursor(Cursor.DEFAULT);
        stackPaneContenedorTablero.removeEventFilter(MouseEvent.MOUSE_CLICKED, alquimiaHandler);
    }

    /**
     * Metodo que anima la conversion exitosa
     * Se encarga de animar la celda al ser convertida en una celda segura
     * @param celda es el boton de la celda
     */
    private void animarConversionExitosa(Button celda) {
        FadeTransition fade = new FadeTransition(Duration.seconds(0.5), celda);
        fade.setFromValue(1.0);
        fade.setToValue(0.3);
        fade.setCycleCount(2);
        fade.setAutoReverse(true);
        
        Timeline colorChange = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(celda.styleProperty(), "-fx-background-color: gold;")),
            new KeyFrame(Duration.seconds(1), new KeyValue(celda.styleProperty(), "-fx-background-color: #cccccc;"))
        );
        
        ParallelTransition transition = new ParallelTransition(fade, colorChange);
        transition.play();
    }

    /**
     * Metodo que anima la super mina
     * Se encarga de animar la super mina al ser seleccionada
     * @param celda es el boton de la celda
     */
    private void animarSuperMina(Button celda) {
        Glow glow = new Glow(0.8);
        celda.setEffect(glow);
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0.1), e -> celda.setStyle("-fx-background-color: #ff0000")),
            new KeyFrame(Duration.seconds(0.2), e -> celda.setStyle("-fx-background-color: #880000"))
        );
        timeline.setCycleCount(4);
        timeline.play();
    }

    /**
     * Metodo que anima la celda segura
     * Se encarga de animar la celda segura al ser seleccionada
     * @param celda es el boton de la celda
     */
    private void animarCeldaSegura(Button celda) {
        ScaleTransition scale = new ScaleTransition(Duration.seconds(0.3), celda);
        scale.setFromX(1.0);
        scale.setFromY(1.0);
        scale.setToX(1.2);
        scale.setToY(1.2);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);
        celda.setStyle("-fx-background-color: #aaffaa;");
        scale.play();
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
     * Metodo que carga la imagen del objeto en la pantalla de informacion
     * @param nombreImagen de la imagen, debe estar en la carpeta img y
     * @param nombreCarpetaImg nombre de la carpeta donde esta la imagen dentro de la carpeta img
     * junto al nombre añadir el formato de la imagen (png, jpg, etc.)
     */
    private void cargarImagen(String nombreCarpetaImg, String nombreImagen) {
        try {
            String imagePath = "/es/ies/puerto/img/" + nombreCarpetaImg +"/"+ nombreImagen;
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
    private void cargarInformacionObjeto(String nombreCarpetaImg, String nombreImagen, String claveObjeto, String claveInformacion) {
        fadeIn(mostrarInformacionVbox, miliSegundos);
        cargarImagen(nombreCarpetaImg, nombreImagen);
        textObjeto.setText(ConfigManager.ConfigProperties.getProperty(claveObjeto));
        textInformacion.setText(ConfigManager.ConfigProperties.getProperty(claveInformacion));
    }

    /**
     * Maneja el evento de clic en el boton de informacion del escudo
     * Muestra la informacion del escudo en la interfaz
     */
    @FXML
    protected void onEscudoInformacionClick() {
        cargarInformacionObjeto("objetos", "escudo.png", "escudo", "infoEscudo");
    }

    @FXML
    protected void onFantasmaInformacionClick() {
        cargarInformacionObjeto("objetos","fantasma.png", "fantasma", "infoFantasma");
    }

    @FXML
    protected void onAlquimiaInformacionClick() {
        cargarInformacionObjeto("objetos","alquimia.png", "alquimia", "infoAlquimia");
    }

    @FXML
    protected void onTemaOscuroInformacionClick() {
        cargarInformacionObjeto("temas","oscuro.png", "oscuro", "infoTemaOscuro");
    }

    @FXML
    protected void onTemaNaturalezaInformacionClick() {
        cargarInformacionObjeto("temas","naturaleza.png", "naturaleza", "infoTemaNaturaleza");
    }

    @FXML
    protected void onTemaRetroInformacionClick() {
        cargarInformacionObjeto("temas","retro.png", "retro", "infoTemaRetro");
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
     * Maneja el evento de clic en el boton de comprar tema
     * Se encarga de comprar un tema y actualizar la interfaz
     */
    private void comprarTema(int temaId, int costo) {
        int puntos = usuario.getPuntos();
        try {
            if (getUsuarioTemasService().tieneUsuarioTema(usuario.getId(), temaId)) {
                mostrarMensaje(ConfigManager.ConfigProperties.getProperty("temaYaComprado"));
                return;
            }
            if (puntos < costo) {
                mostrarMensaje(ConfigManager.ConfigProperties.getProperty("noSuficientesPuntos"));
                return;
            }
            getUsuarioTemasService().comprarTema(usuario, temaId);
            usuario.setPuntos(puntos - costo);
            actualizarPuntosEnVista(usuario.getPuntos());
            getUsuarioService().actualizarPuntos(usuario.getPuntos(), usuario.getEmail());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Maneja el evento de clic en el boton de comprar tema oscuro
     * Se encarga de comprar el tema oscuro y actualizar la interfaz
     */
    @FXML
    protected void onComprarTemaOscuroClick() {
        comprarTema(TEMA_OSCURO_ID, COSTO_TEMAS);
    }

    /**
     * Maneja el evento de clic en el boton de comprar tema naturaleza
     * Se encarga de comprar el tema naturaleza y actualizar la interfaz
     */
    @FXML
    protected void onComprarTemaNaturalezaClick() {
        comprarTema(TEMA_NATURALEZA_ID, COSTO_TEMAS);
    }

    /**
     * Maneja el evento de clic en el boton de comprar tema retro
     * Se encarga de comprar el tema retro y actualizar la interfaz
     */
    @FXML
    protected void onComprarTemaRetroClick() {
        comprarTema(TEMA_RETRO_ID, COSTO_TEMAS);
    }

    /**
     * Metodo para aplicar un tema 
     * @param temaId ID del tema a aplicar
     */
    private void aplicarTema(int temaId) {
        try {
            if (!getUsuarioTemasService().tieneUsuarioTema(usuario.getId(), temaId)) {
                mostrarMensaje(ConfigManager.ConfigProperties.getProperty("errorUsar"));
                return;
            }
            boolean exito = getUsuarioTemasService().actualizarUsuarioTemas(true, usuario.getId(), temaId);
            if (exito) {
                String cssPath = getUsuarioTemasService().obtenerCssPorTemaId(temaId);
                Sesion.setCssTemaActivo(cssPath);
                Platform.runLater(() -> {
                    for (Window window : Window.getWindows()) {
                        if (window instanceof Stage) {
                            Scene scene = ((Stage) window).getScene();
                            actualizarEstilosEnEscena(scene, cssPath);
                        }
                    }
                });
                mostrarMensaje(ConfigManager.ConfigProperties.getProperty("temaAplicado"));
                return;
            } 
            mostrarMensaje(ConfigManager.ConfigProperties.getProperty("errorAplicarTema"));
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarMensaje(ConfigManager.ConfigProperties.getProperty("errorBbdd"));
        }
    }

    /**
     * Maneja el evento de clic en el boton de usar tema oscuro
     * Se encarga de aplicar el tema oscuro al tablero
     */
    @FXML
    protected void onUsarTemaOscuroClick() {
        aplicarTema(TEMA_OSCURO_ID);
    }

    /**
     * Maneja el evento de clic en el boton de usar tema naturaleza
     * Se encarga de aplicar el tema naturaleza al tablero
     */
    @FXML
    protected void onUsarTemaNaturalezaClick() {
        aplicarTema(TEMA_NATURALEZA_ID);
    }

    /**
     * Maneja el evento de clic en el boton de usar tema retro
     * Se encarga de aplicar el tema retro al tablero
     */
    @FXML
    protected void onUsarTemaRetroClick() {
        aplicarTema(TEMA_RETRO_ID);
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