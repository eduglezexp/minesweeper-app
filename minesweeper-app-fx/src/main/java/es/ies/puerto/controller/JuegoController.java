package es.ies.puerto.controller;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import es.ies.puerto.config.ConfigManager;
import es.ies.puerto.controller.abstractas.AbstractController;
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
    private TextField extFieldVictorias;

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
    private StackPane contenedorTablero;

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
        if (usuario != null) {

        }
    }

    /**
     * Metodo para iniciar el tablero
     */
    private void iniciarTablero(BoardConfig boardConfig) {
        contenedorTablero.getChildren().clear();
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
        contenedorTablero.getChildren().clear();
        contenedorTablero.getChildren().add(personalizarTableroVbox);
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
            if (minas >= filas * columnas) {
                textMensajePersonalizar.setText("Demasiadas minas para el tamaño del tablero");
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
                    } else if (button == MouseButton.SECONDARY) {
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
            int row = random.nextInt(configActual.filas());
            int col = random.nextInt(configActual.columnas());
            
            if (!celdasProtejidas.contains(row + "," + col) && !minas[row][col]) {
                minas[row][col] = true;
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
    private int contarMinasAdyacentes(int row, int col) {
        int contador = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int nuevaFila = row + i;
                int nuevaColumna = col + j;
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
            gameOver = true;
            pararTemporizador();
            revelarTodasMinas();
            textMensaje.setText("¡Has perdido!");
            return;
        }
        revealEmptyCells(fila, columna);
        revisarVictoria();
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
            textMensaje.setText("¡Has ganado!");
            revelarTodasMinas();
        }
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
    }

    /**
     * Metodo que abre la tienda
     * Se encarga de abrir la tienda para comprar objetos
     */
    @FXML
    protected void onTiendaClick() {
    }

    /**
     * Metodo para personalizar el tablero
     */
    @FXML
    protected void onPersonalizarClick() {
        accederPersonalizarTablero();
        personalizarButton.setVisible(false);
        regresarButton.setVisible(true);
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
    }

    /**
     * Metodo que abre el menu de ayuda
     */
    @FXML
    protected void onAyudaClick() {
        contenedorTablero.getChildren().clear();
        contenedorTablero.getChildren().add(mostrarAyudaVBox);
        mostrarAyudaVBox.setVisible(true);
        textAyuda.setText("Instrucciones del juego:\n" +
                          "1. Haz clic en una celda para descubrirla.\n" +
                          "2. Haz clic derecho para colocar una bandera.\n" +
                          "3. Evita las minas y descubre todas las celdas.");
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