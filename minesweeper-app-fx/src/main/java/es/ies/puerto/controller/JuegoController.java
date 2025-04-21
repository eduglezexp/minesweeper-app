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
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public class JuegoController extends AbstractController{

    @FXML
    private StackPane mostrarPantallasVbox;

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
    private VBox personalizarTableroVbox;

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
    private Button comprarMinaFantasmaButton;

    @FXML 
    private Button usarMinaFantasmaButton;

    @FXML 
    private Button comprarEscudoButton;

    @FXML 
    private Button usarEscudoButton;

    @FXML 
    private TextField textFieldPuntosTienda;
    
    @FXML 
    private Button comprarZonaSeguraButton;

    @FXML 
    private Button usarZonaSeguraButton;

    @FXML
    private StackPane contenedorTablero;
    
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
    private Button ayudaButton;

    @FXML
    private Button buttonVolverAtras;

    public record BoardConfig(int filas, int columnas, int minas) {}
    private boolean[][] minas;
    private int[][] minasAdyacentes;
    private boolean gameOver = false;
    private int BanderasColocadas = 0;
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
    private UsuarioEntity usuario;

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
            //textFieldRacha.setText(String.valueOf(usuario.getRachaActual()));
            //textFieldMejorRacha.setText(String.valueOf(usuario.getMejorRacha()));
        }      
    }
    

    /**
     * Metodo para iniciar el tablero
     */
    private void iniciarTablero(BoardConfig boardConfig) {
        personalizarTableroVbox.setVisible(false);
        mostrarEstadisticasVbox.setVisible(false);
        mostrarAyudaVBox.setVisible(false);
        mostrarFinDelJuegoVbox.setVisible(false);
        mostrarTiendaVbox.setVisible(false);
        contenedorTablero.getChildren().removeIf(node -> node instanceof GridPane);
        contenedorTablero.setVisible(true);
        GridPane tablero = crearTablero(boardConfig.filas(), boardConfig.columnas(), boardConfig.minas());
        tablero.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        tablero.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        contenedorTablero.getChildren().add(tablero);
        StackPane.setAlignment(tablero, Pos.CENTER);
    }

    /**
     * Metodo para calcular el tamanio del tablero segun el nivel de dificultad
     */
    private void manejarSeleccion(int indice) {
        ocultarBotones();
        BoardConfig config;
        switch (indice) {
            case 4: // Personalizado
                accederPersonalizarTablero();
                return;
            case 3: // Aleatorio
                config = generarTableroAleatorio();
                break;
            case 2: // Experto
                config = new BoardConfig(18, 12, 43);
                break;
            case 1: // Intermedio
                config = new BoardConfig(14, 10, 28); 
                break;
            default: // Principiante
                config = new BoardConfig(10, 8, 10); 
                break;
        }
        iniciarTablero(config);
    }

    /**
     * Metodo para ocultar los botones de personalizacion
     */
    private void ocultarBotones() {
        personalizarButton.setVisible(false);
        aleatorioButton.setVisible(false);
    }

    /**
     * Metodo para acceder a la pantalla de personalizacion del tablero
     */
    private void accederPersonalizarTablero() {
        contenedorTablero.setVisible(false);
        mostrarAyudaVBox.setVisible(false);
        personalizarTableroVbox.setVisible(true);
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
        double cellWidth = contenedorTablero.getWidth() / columnas;
        double cellHeight = contenedorTablero.getHeight() / filas;
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
            personalizarTableroVbox.setVisible(false);
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
        double cellWidth = contenedorTablero.getWidth() / columnas;
        double cellHeight = contenedorTablero.getHeight() / filas;
        double cellSize = Math.min(cellWidth, cellHeight);
        minas = new boolean[filas][columnas];
        minasAdyacentes = new int[filas][columnas];
        celdas = new Button[filas][columnas];
        gameOver = false;
        primerClick = true;
        BanderasColocadas = 0;
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
                revealEmptyCells(fila, columna);
                revisarVictoria();
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
     * Metodo para mostrar el mensaje de derrota
     */
    private void mostrarDerrota() {
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
            BanderasColocadas--;
        } else {
            if (BanderasColocadas >= minasTotales) return;
            celda.setText("F");
            BanderasColocadas++;
        }
        textBanderas.setText(String.valueOf(minasTotales - BanderasColocadas));
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
        personalizarTableroVbox.setVisible(false);
        contenedorTablero.setVisible(true);
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
        contenedorTablero.setVisible(false);
        mostrarEstadisticasVbox.setVisible(false);
        mostrarAyudaVBox.setVisible(false);
        personalizarTableroVbox.setVisible(true);
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
            mostrarEstadisticasVbox.setVisible(false);
            switch (vistaAnterior) {
                case PERSONALIZACION:
                    personalizarTableroVbox.setVisible(true);
                    break;
                case AYUDA:
                    mostrarAyudaVBox.setVisible(true);
                    break;
                case TIENDA:
                    mostrarTiendaVbox.setVisible(true);
                    break;
                default:
                    contenedorTablero.setVisible(true); 
                    if (primerClick) {
                        pausarTemporizador(false); 
                    } else {
                        pausarTemporizador(true);
                    }
                    break;
            }
        } else {
            if (personalizarTableroVbox.isVisible()) {
                vistaAnterior = VistaActual.PERSONALIZACION;
            } else if (mostrarAyudaVBox.isVisible()) {
                vistaAnterior = VistaActual.AYUDA;
            } else if (mostrarTiendaVbox.isVisible()) {
                vistaAnterior = VistaActual.TIENDA;
            } else {
                vistaAnterior = VistaActual.TABLERO;
                
            }
            personalizarTableroVbox.setVisible(false);
            contenedorTablero.setVisible(false);
            mostrarAyudaVBox.setVisible(false);
            mostrarTiendaVbox.setVisible(false);
            mostrarEstadisticasVbox.setVisible(true);
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
            mostrarAyudaVBox.setVisible(false);
            switch (vistaAnterior) {
                case PERSONALIZACION:
                    personalizarTableroVbox.setVisible(true);
                    break;
                case ESTADISTICAS:
                    mostrarEstadisticasVbox.setVisible(true);
                    break;
                case TIENDA:
                    mostrarTiendaVbox.setVisible(true);
                    break;
                default:
                    contenedorTablero.setVisible(true);
                    if (primerClick) {
                        pausarTemporizador(false); 
                    } else {
                        pausarTemporizador(true);
                    }
                    break;
            }
        } else {
            if (personalizarTableroVbox.isVisible()) {
                vistaAnterior = VistaActual.PERSONALIZACION;
            } else if (mostrarEstadisticasVbox.isVisible()) {
                vistaAnterior = VistaActual.ESTADISTICAS;
            } else if (mostrarTiendaVbox.isVisible()) {
                vistaAnterior = VistaActual.TIENDA;
            } else {
                vistaAnterior = VistaActual.TABLERO;
            }
            personalizarTableroVbox.setVisible(false);
            mostrarEstadisticasVbox.setVisible(false);
            mostrarTiendaVbox.setVisible(false);
            contenedorTablero.setVisible(false);
            mostrarAyudaVBox.setVisible(true);
            pausarTemporizador(false);
        }
    }

    /**
     * Metodo que maneja el evento de clic en el boton de reintentar
     * Reinicia el juego y vuelve a cargar el tablero
     */
    @FXML
    protected void onReintentarClick() {
        mostrarFinDelJuegoVbox.setVisible(false);
        textFinJuego.setVisible(false);
        textResultado.setVisible(false);
        reintentarButton.setVisible(false);
        iniciarTablero(configActual);
    }

    /**
     * Metodo que maneja el evento de clic en el boton de tienda
     */
    @FXML
    private void onTiendaClick() {
        boolean tiendaVisible = mostrarTiendaVbox.isVisible();
        if (tiendaVisible) {
            mostrarTiendaVbox.setVisible(false);
            switch (vistaAnterior) {
                case PERSONALIZACION:
                    personalizarTableroVbox.setVisible(true);
                    break;
                case ESTADISTICAS:
                    mostrarEstadisticasVbox.setVisible(true);
                    break;
                case AYUDA:
                    mostrarAyudaVBox.setVisible(true);
                    break;
                default:
                    contenedorTablero.setVisible(true);
                    if (primerClick) {
                        pausarTemporizador(false); 
                    } else {
                        pausarTemporizador(true);
                    }
                    break;
            }
        } else {
            if (personalizarTableroVbox.isVisible()) {
                vistaAnterior = VistaActual.PERSONALIZACION;
            } else if (mostrarEstadisticasVbox.isVisible()) {
                vistaAnterior = VistaActual.ESTADISTICAS;
            } else if (mostrarAyudaVBox.isVisible()) {
                vistaAnterior = VistaActual.AYUDA;
            } else {
                vistaAnterior = VistaActual.TABLERO;
            }
            personalizarTableroVbox.setVisible(false);
            mostrarEstadisticasVbox.setVisible(false);
            mostrarAyudaVBox.setVisible(false);
            contenedorTablero.setVisible(false);
            mostrarTiendaVbox.setVisible(true);
            pausarTemporizador(false);
        }
    }

    /**
     * Metodo que maneja el evento de clic en el boton de volver a la tienda
     * Vuelve a la tienda y oculta el tablero
     */
    @FXML
    private void onVolverTiendaClick() {
        mostrarTiendaVbox.setVisible(false);
        contenedorTablero.setVisible(true);
    }

    /**
     * Metodo que maneja el evento de clic en el boton de comprar mina fantasma
     * Se encarga de comprar una mina fantasma y actualizar la interfaz
     */
    @FXML
    private void onComprarMinaFantasmaClick() {
        int puntos = usuario.getPuntos();
        if (puntos >= 0) {
            //usuario.setPuntos(puntos - 50);
            minasFantasmaDisponibles++;
            actualizarUI();
            usarMinaFantasmaButton.setText("Usar Mina Fantasma (Disponibles: " + minasFantasmaDisponibles + ")");
        }
    }

    /**
     * Metodo que maneja el evento de clic en el boton de usar mina fantasma
     * Se encarga de usar una mina fantasma y resaltar una mina aleatoria en el tablero
     */
    @FXML
    private void onUsarMinaFantasmaClick() {
        if (minasFantasmaDisponibles > 0 && !primerClick) {
            resaltarMinaAleatoria();
            minasFantasmaDisponibles--;
            actualizarUI();
        }
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

    @FXML
    private void onComprarEscudoClick() {
        if (usuario.getPuntos() >= 0) {
            //usuario.setPuntos(usuario.getPuntos() - 100);
            escudosDisponibles++;
            actualizarUI();
        }
    }

    @FXML
    private void onUsarEscudoClick() {
        if (escudosDisponibles > 0 && !escudoActivado) {
            escudoActivado = true;
            escudosDisponibles--;
            actualizarUI();
        }
    }

    /**
     * Metodo para actualizar la interfaz
     */
    private void actualizarUI() {
        textFieldPuntosTienda.setText(String.valueOf(usuario.getPuntos()));
        usarMinaFantasmaButton.setText("Usar Mina Fantasma (Disponibles: " + minasFantasmaDisponibles + ")");
        usarEscudoButton.setText("Escudo Activado: " + (escudoActivado ? "Sí" : "No") + " | Disponibles: " + escudosDisponibles);
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