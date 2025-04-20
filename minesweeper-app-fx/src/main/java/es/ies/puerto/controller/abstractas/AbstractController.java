package es.ies.puerto.controller.abstractas;

import java.lang.reflect.Method;

import es.ies.puerto.MinesweeperApp;
import es.ies.puerto.config.ConfigManager;
import es.ies.puerto.model.entities.UsuarioEntity;
import es.ies.puerto.model.services.NivelService;
import es.ies.puerto.model.services.ObjetoService;
import es.ies.puerto.model.services.UsuarioService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public abstract class AbstractController {

    static final String PATH_DB = "src/main/resources/usuarios.db";
    private UsuarioService usuarioService;
    private NivelService nivelService;
    private ObjetoService objetoService;
    private Scene previousScene;
    private String previousTitle;
    
    /**
     * Constructor por defecto
     */
    public AbstractController() {
        try {
            usuarioService = new UsuarioService(PATH_DB);
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
            AbstractController newController = fxmlLoader.getController();
            newController.setPreviousScene(stage.getScene());
            newController.setPreviousTitle(stage.getTitle());
            textMensaje.setText(" ");
            scene.getStylesheets().add(getClass().getResource("/es/ies/puerto/css/style.css").toExternalForm());
            //Image icon = new Image(getClass().getResource("/es/ies/puerto/img/icon.png").toExternalForm());
            //stage.getIcons().add(icon);
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
            scene.getStylesheets().add(getClass().getResource("/es/ies/puerto/css/style.css").toExternalForm());
            AbstractController controller = fxmlLoader.getController();
            controller.setPreviousScene(stage.getScene());
            controller.setPreviousTitle(stage.getTitle());
            textMensaje.setText(" ");
            Method method = controller.getClass().getMethod("cargarDatosUsuario", UsuarioEntity.class);
            method.invoke(controller, usuario); 
            //Image icon = new Image(getClass().getResource("/es/ies/puerto/img/icon.png").toExternalForm());
            //stage.getIcons().add(icon);
            stage.setTitle(titulo);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}