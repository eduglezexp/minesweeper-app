package es.ies.puerto.controller.abstractas;

import java.lang.reflect.Method;

import es.ies.puerto.MinesweeperApp;
import es.ies.puerto.config.ConfigManager;
import es.ies.puerto.config.Sesion;
import es.ies.puerto.model.entities.UsuarioEntity;
import es.ies.puerto.model.services.NivelService;
import es.ies.puerto.model.services.ObjetoService;
import es.ies.puerto.model.services.UsuarioPowerupsService;
import es.ies.puerto.model.services.UsuarioService;
import es.ies.puerto.model.services.UsuarioTemasService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public abstract class AbstractController {

    static final String PATH_DB = "src/main/resources/usuarios.db";
    private UsuarioService usuarioService;
    private UsuarioPowerupsService usuarioPowerupsService;
    private UsuarioTemasService usuarioTemasService;
    private NivelService nivelService;
    private ObjetoService objetoService;
    private Scene previousScene;
    private String previousTitle;
    private String previousStyle;
    
    
    /**
     * Constructor por defecto
     */
    public AbstractController() {
        try {
            usuarioService = new UsuarioService(PATH_DB);
            usuarioPowerupsService = new UsuarioPowerupsService(PATH_DB);
            usuarioTemasService = new UsuarioTemasService(PATH_DB);
            nivelService = new NivelService(PATH_DB);
            objetoService = new ObjetoService(PATH_DB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Getters and Setters
     */
    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public UsuarioPowerupsService getUsuarioPowerupsService() {
        return usuarioPowerupsService;
    }

    public UsuarioTemasService getUsuarioTemasService() {
        return usuarioTemasService;
    }

    public NivelService getNivelService() {
        return nivelService;
    }

    public ObjetoService getObjetoService() {
        return objetoService;
    }

    public Scene getPreviousScene() {
        return previousScene;
    }

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    public String getPreviousTitle() {
        return previousTitle;
    }

    public void setPreviousTitle(String titulo) {
        this.previousTitle = titulo;
    }

    public String getPreviousStyle() {
        return previousStyle;
    }

    public void setPreviousStyle(String previousStyle) {
        this.previousStyle = previousStyle;
    }

    /**
     * LoginController
     */
    @FXML
    private Text textUsuarioEmail;

    @FXML
    private TextField textFieldUsuarioEmail;

    @FXML
    private Text textContrasenia;
    
    @FXML
    private PasswordField textFieldPassword;

    @FXML 
    private Text textMensaje;

    @FXML
    private Button openAceptarButton;

    @FXML
    private Button openRegistrarButton;

    @FXML
    private Button openListarUsuariosButton;

    @FXML
    private Button buttonRecuperarContrasenia;
    
    /**
     * RegistroController
     */
    @FXML
    public Text textRegistroTitulo;

    @FXML 
    private TextField textFieldUsuario;

    @FXML 
    private PasswordField textFieldPasswordRepit;

    @FXML 
    private TextField textFieldNombre;

    @FXML 
    private TextField textFieldEmail;

    @FXML 
    private TextField textFieldEmailRepit;

    @FXML
    private Button buttonVolverAtras;

    /**
     * PasswordController
     */
    @FXML 
    private Text textEmail;

    /**
     * UsuariosController
     */
    @FXML
    private Text textListaUsuario;

    @FXML
    private Button buscarUsuariosButton;

    /**
     * ProfileController
     */
    @FXML 
    private Text textUsuario;

    @FXML 
    private Text textNombre;

    @FXML 
    private Text textNivel;

    @FXML
    private Button openEditarButton;

    @FXML
    private Button openJugarButton;

    /**
     * JuegoController
     */
    @FXML
    private StackPane mostrarBotonesVbox;

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
    private Button regresarButton;

    @FXML
    private VBox mostrarEstadisticasVbox;

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
    private StackPane contenedorTablero;

    @FXML
    private Button ayudaButton;

    /**
     * Metodo para cambiar el idioma
     */
    @FXML
    protected void cambiarIdioma() {
        /**
         * LoginController
         */
        if (textUsuarioEmail != null) {
            textUsuarioEmail.setText(ConfigManager.ConfigProperties.getProperty("textUsuarioEmail"));
        }
        if (textFieldUsuarioEmail != null) {
            textFieldUsuarioEmail.setPromptText(ConfigManager.ConfigProperties.getProperty("textFieldUsuarioEmail"));
        }
        if (textContrasenia != null) {
            textContrasenia.setText(ConfigManager.ConfigProperties.getProperty("textContrasenia"));
        }
        if (textFieldPassword != null) {
            textFieldPassword.setPromptText(ConfigManager.ConfigProperties.getProperty("textFieldPassword"));
        }
        if (openAceptarButton != null) {
            openAceptarButton.setText(ConfigManager.ConfigProperties.getProperty("openAceptarButton"));
        }
        if (openRegistrarButton != null) {
            openRegistrarButton.setText(ConfigManager.ConfigProperties.getProperty("openRegistrarButton"));
        }
        if (openListarUsuariosButton != null) {
            openListarUsuariosButton.setText(ConfigManager.ConfigProperties.getProperty("openListarUsuariosButton"));
        }
        if (buttonRecuperarContrasenia != null) {
            buttonRecuperarContrasenia.setText(ConfigManager.ConfigProperties.getProperty("buttonRecuperarContrasenia"));
        }
        /**
         * RegistroController
         */
        if (textRegistroTitulo != null) {
            textRegistroTitulo.setText(ConfigManager.ConfigProperties.getProperty("textRegistroTitulo"));
        }
        if (textFieldUsuario != null) {
            textFieldUsuario.setPromptText(ConfigManager.ConfigProperties.getProperty("textFieldUsuario"));
        }
        if (textFieldPasswordRepit != null) {
            textFieldPasswordRepit.setPromptText(ConfigManager.ConfigProperties.getProperty("textFieldPasswordRepit"));
        }
        if (textFieldNombre != null) {
            textFieldNombre.setPromptText(ConfigManager.ConfigProperties.getProperty("textFieldNombre"));
        }
        if (textFieldEmail != null) {
            textFieldEmail.setPromptText(ConfigManager.ConfigProperties.getProperty("textFieldEmail"));
        }
        if (textFieldEmailRepit != null) {
            textFieldEmailRepit.setPromptText(ConfigManager.ConfigProperties.getProperty("textFieldEmailRepit"));
        }
        if (buttonVolverAtras != null) {
            buttonVolverAtras.setText(ConfigManager.ConfigProperties.getProperty("buttonVolverAtras"));
        }
        /**
         * PasswordController
         */
        if (textEmail != null) {
            textEmail.setText(ConfigManager.ConfigProperties.getProperty("textEmail"));
        }
        /**
         * UsuariosController
         */
        if (textListaUsuario != null) {
            textListaUsuario.setText(ConfigManager.ConfigProperties.getProperty("textListaUsuario"));
        }
        if (buscarUsuariosButton != null) {
            buscarUsuariosButton.setText(ConfigManager.ConfigProperties.getProperty("buscarUsuariosButton"));
        }
        /**
         * ProfileController
         */
        if (textUsuario != null) {
            textUsuario.setText(ConfigManager.ConfigProperties.getProperty("textUsuario"));
        }
        if (textNombre != null) {
            textNombre.setText(ConfigManager.ConfigProperties.getProperty("textNombre"));
        }
        if (textNivel != null) {
            textNivel.setText(ConfigManager.ConfigProperties.getProperty("textNivel"));
        }
        if (openEditarButton != null) {
            openEditarButton.setText(ConfigManager.ConfigProperties.getProperty("openEditarButton"));
        }
        if (openJugarButton != null) {
            openJugarButton.setText(ConfigManager.ConfigProperties.getProperty("openJugarButton"));
        }
    }

    /**
     * Metodo para mostrar una nueva pantalla en la aplicacion
     * @param button sirve para obtener la ventana actual
     * @param fxml La ruta al archivo FXML que define la interfaz de la nueva pantalla
     * @param titulo El titulo que se asignara a la ventana
     */
    public void mostrarPantalla(Button button, String fxml, String titulo) {
        if (button == null || fxml == null || fxml.isEmpty() || titulo == null || titulo.isEmpty()) {
            return;
        }
        try {
            Stage stage = (Stage) button.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MinesweeperApp.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load());
            String cssBase = "/es/ies/puerto/css/style.css";
            scene.getStylesheets().add(getClass().getResource(cssBase).toExternalForm());
            if (Sesion.getCssTemaActivo() != null) {
                scene.getStylesheets().add(getClass().getResource(Sesion.getCssTemaActivo()).toExternalForm());
            }
            AbstractController newController = fxmlLoader.getController();
            newController.setPreviousScene(stage.getScene());
            newController.setPreviousTitle(stage.getTitle());
            textMensaje.setText(" ");
            Image icon = new Image(getClass().getResource("/es/ies/puerto/img/bomb.png").toExternalForm());
            stage.getIcons().add(icon);
            stage.setTitle(titulo);
            stage.setResizable(false);
            stage.setScene(scene);      
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para mostrar una nueva pantalla en la aplicacion
     * @param button sirve para obtener la ventana actual
     * @param fxml La ruta al archivo FXML que define la interfaz de la nueva pantalla
     * @param titulo El titulo que se asignara a la ventana
     * @param usuario contiene los datos que se cargaran en la nueva pantalla
     * Nota: Si se va a usar Json, cambiar el UsuarioEntitySqlite por UsuarioEntityJson
     */
    public void mostrarPantalla(Node node, String fxml, String titulo, UsuarioEntity usuario) {
        if (node == null || fxml == null || fxml.isEmpty() || titulo == null || titulo.isEmpty() || usuario == null) {
            return;
        }
        try {
            Stage stage = (Stage) node.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MinesweeperApp.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load());
            String cssBase = "/es/ies/puerto/css/style.css";
            scene.getStylesheets().add(getClass().getResource(cssBase).toExternalForm());
            if (Sesion.getCssTemaActivo() != null) {
                scene.getStylesheets().add(getClass().getResource(Sesion.getCssTemaActivo()).toExternalForm());
            }
            AbstractController controller = fxmlLoader.getController();
            controller.setPreviousScene(stage.getScene());
            controller.setPreviousTitle(stage.getTitle());
            textMensaje.setText(" ");
            Method method = controller.getClass().getMethod("cargarDatosUsuario", UsuarioEntity.class);
            method.invoke(controller, usuario); 
            Image icon = new Image(getClass().getResource("/es/ies/puerto/img/bomb.png").toExternalForm());
            stage.getIcons().add(icon);
            stage.setTitle(titulo);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para actualizar los estilos de la escena
     * @param scene escena a la que se le aplicaran los estilos
     * @param cssPath ruta del archivo css a aplicar
     */
    public void actualizarEstilosEnEscena(Scene scene, String cssPath) {
        if (scene != null) {
            scene.getStylesheets().removeIf(s -> s.contains("temas")); 
            String temaCss = getClass().getResource(cssPath).toExternalForm();
            scene.getStylesheets().add(temaCss);
        }
    }
}