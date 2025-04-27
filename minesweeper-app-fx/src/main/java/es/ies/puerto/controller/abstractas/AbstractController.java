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
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    @FXML
    private Button abrirWarningButton;

    @FXML 
    private Text textWarning;

    @FXML
    private Button eliminarButton;

    @FXML
    private Button cancelarButton;

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
    private Button tiendaButton;

    @FXML
    private Button personalizarButton;

    @FXML
    private Button aleatorioButton;

    @FXML
    private Button estadisticasButton;

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
    private Button regresarButton;

    @FXML
    private Text textPuntos;

    @FXML
    private Text textVictorias;

    @FXML
    private Text textDerrotas;

    @FXML
    private Text textRacha;

    @FXML
    private Text textMejorRacha;

    @FXML
    private Text textTituloAyuda;

    @FXML
    private Text textAyuda;

    @FXML
    private Text textTienda;

    @FXML
    private Text textPotenciadoresTienda;

    @FXML
    private Text textTemasTienda;

    @FXML 
    private Button volverTiendaButton;

    @FXML 
    private Button usarMinaFantasmaButton;

    @FXML 
    private Button usarEscudoButton;

    @FXML 
    private Button usarAlquimiaButton;

    @FXML 
    private Button usarTemaOscuroButton;

    @FXML 
    private Button usarTemaNaturalezaButton;

    @FXML 
    private Button usarTemaRetroButton;

    @FXML 
    private Button salirInformacionButton;

    @FXML
    private Button reintentarButton;

    @FXML
    private Button ayudaButton;

    @FXML
    private Text textPotenciadoresInventario;

    @FXML
    private Text textInventario;

    @FXML
    private Text textTemasInventario;

    @FXML
    private Button inventarioButton;

    /**
     * JuegoController label ayuda
     */
    @FXML 
    private Label helpTituloAyuda;

    @FXML 
    private Label helpIntro;

    @FXML 
    private Label helpSec1Title;

    @FXML 
    private Label helpSec1Line1;

    @FXML 
    private Label helpSec1Line2;

    @FXML 
    private Label helpSec1Line3;

    @FXML 
    private Label helpSec2Title;

    @FXML 
    private Label helpSec2Line1;

    @FXML 
    private Label helpSec2Line1a;

    @FXML 
    private Label helpSec2Line1b;

    @FXML 
    private Label helpSec2Line2;

    @FXML 
    private Label helpSec2Line2a;

    @FXML 
    private Label helpSec3Title;

    @FXML 
    private Label helpSec3Line1;

    @FXML 
    private Label helpSec3Line2;

    @FXML 
    private Label helpSec3Line3;

    @FXML 
    private Label helpSec3Line3a;

    @FXML 
    private Label helpSec3Line3b;

    @FXML 
    private Label helpSec3Line3c;

    @FXML 
    private Label helpSec3Line4;

    @FXML 
    private Label helpSec3Warning;

    @FXML 
    private Label helpSec4Title;

    @FXML 
    private Label helpSec4Line1;

    @FXML 
    private Label helpSec4Line2;

    @FXML 
    private Label helpOutro;

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
        if (abrirWarningButton != null) {
            abrirWarningButton.setText(ConfigManager.ConfigProperties.getProperty("abrirWarningButton"));
        }
        if (textWarning != null) {
            textWarning.setText(ConfigManager.ConfigProperties.getProperty("textWarning"));
        }
        if (eliminarButton != null) {
            eliminarButton.setText(ConfigManager.ConfigProperties.getProperty("eliminarButton"));
        }
        if (cancelarButton != null) {
            cancelarButton.setText(ConfigManager.ConfigProperties.getProperty("cancelarButton"));
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
        /**
         * JuegoController
         */
        if (tiendaButton != null) {
            tiendaButton.setText(ConfigManager.ConfigProperties.getProperty("tiendaButton"));
        }
        if (personalizarButton != null) {
            personalizarButton.setText(ConfigManager.ConfigProperties.getProperty("personalizarButton"));
        }
        if (aleatorioButton != null) {
            aleatorioButton.setText(ConfigManager.ConfigProperties.getProperty("aleatorioButton"));
        }
        if (estadisticasButton != null) {
            estadisticasButton.setText(ConfigManager.ConfigProperties.getProperty("estadisticasButton"));
        }
        if (textPersonalizarPartida != null) {
            textPersonalizarPartida.setText(ConfigManager.ConfigProperties.getProperty("textPersonalizarPartida"));
        }
        if (textFilas != null) {
            textFilas.setText(ConfigManager.ConfigProperties.getProperty("textFilas"));
        }
        if (textFieldFilas != null) {
            textFieldFilas.setPromptText(ConfigManager.ConfigProperties.getProperty("textFieldFilas"));
        }
        if (textColumnas != null) {
            textColumnas.setText(ConfigManager.ConfigProperties.getProperty("textColumnas"));
        }
        if (textFieldColumnas != null) {
            textFieldColumnas.setPromptText(ConfigManager.ConfigProperties.getProperty("textFieldColumnas"));
        }
        if (textMinas != null) {
            textMinas.setText(ConfigManager.ConfigProperties.getProperty("textMinas"));
        }
        if (textFieldMinas != null) {
            textFieldMinas.setPromptText(ConfigManager.ConfigProperties.getProperty("textFieldMinas"));
        }
        if (regresarButton != null) {
            regresarButton.setText(ConfigManager.ConfigProperties.getProperty("regresarButton"));
        }
        if (textPuntos != null) {
            textPuntos.setText(ConfigManager.ConfigProperties.getProperty("textPuntos"));
        }
        if (textVictorias != null) {
            textVictorias.setText(ConfigManager.ConfigProperties.getProperty("textVictorias"));
        }
        if (textDerrotas != null) {
            textDerrotas.setText(ConfigManager.ConfigProperties.getProperty("textDerrotas"));
        }
        if (textRacha != null) {
            textRacha.setText(ConfigManager.ConfigProperties.getProperty("textRacha"));
        }
        if (textMejorRacha != null) {
            textMejorRacha.setText(ConfigManager.ConfigProperties.getProperty("textMejorRacha"));
        }
        if (textTituloAyuda != null) {
            textTituloAyuda.setText(ConfigManager.ConfigProperties.getProperty("textTituloAyuda"));
        }
        if (textAyuda != null) {
            textAyuda.setText(ConfigManager.ConfigProperties.getProperty("textAyuda"));
        }
        if (textTienda != null) {
            textTienda.setText(ConfigManager.ConfigProperties.getProperty("textTienda"));
        }
        if (textPotenciadoresTienda != null) {
            textPotenciadoresTienda.setText(ConfigManager.ConfigProperties.getProperty("textPotenciadoresTienda"));
        }
        if (textTemasTienda != null) {
            textTemasTienda.setText(ConfigManager.ConfigProperties.getProperty("textTemasTienda"));
        }
        if (usarMinaFantasmaButton != null) {
            usarMinaFantasmaButton.setText(ConfigManager.ConfigProperties.getProperty("usarMinaFantasmaButton"));
        }
        if (usarEscudoButton != null) {
            usarEscudoButton.setText(ConfigManager.ConfigProperties.getProperty("usarEscudoButton"));
        }
        if (usarAlquimiaButton != null) {
            usarAlquimiaButton.setText(ConfigManager.ConfigProperties.getProperty("usarAlquimiaButton"));
        }
        if (usarTemaOscuroButton != null) {
            usarTemaOscuroButton.setText(ConfigManager.ConfigProperties.getProperty("usarTemaOscuroButton"));
        }
        if (usarTemaNaturalezaButton != null) {
            usarTemaNaturalezaButton.setText(ConfigManager.ConfigProperties.getProperty("usarTemaNaturalezaButton"));
        }
        if (usarTemaRetroButton != null) {
            usarTemaRetroButton.setText(ConfigManager.ConfigProperties.getProperty("usarTemaRetroButton"));
        }
        if (salirInformacionButton != null) {
            salirInformacionButton.setText(ConfigManager.ConfigProperties.getProperty("salirInformacionButton"));
        }
        if (reintentarButton != null) {
            reintentarButton.setText(ConfigManager.ConfigProperties.getProperty("reintentarButton"));
        }
        if (ayudaButton != null) {
            ayudaButton.setText(ConfigManager.ConfigProperties.getProperty("ayudaButton"));
        }
        if (textPotenciadoresInventario != null) {
            textPotenciadoresInventario.setText(ConfigManager.ConfigProperties.getProperty("textPotenciadoresInventario"));
        }
        if (textInventario != null) {
            textInventario.setText(ConfigManager.ConfigProperties.getProperty("textInventario"));
        }
        if (textTemasInventario != null) {
            textTemasInventario.setText(ConfigManager.ConfigProperties.getProperty("textTemasInventario"));
        }
        if (inventarioButton != null) {
            inventarioButton.setText(ConfigManager.ConfigProperties.getProperty("inventarioButton"));
        }
        /**
         * JuegoController Texto ayuda
         */
        if (helpTituloAyuda != null) {
            helpTituloAyuda.setText(ConfigManager.ConfigProperties.getProperty("helpTituloAyuda"));
        }
        if (helpIntro != null) {
            helpIntro.setText(ConfigManager.ConfigProperties.getProperty("helpIntro"));
        }
        if (helpSec1Title != null) {
            helpSec1Title.setText(ConfigManager.ConfigProperties.getProperty("helpSec1Title"));
        }
        if (helpSec1Line1 != null) {
            helpSec1Line1.setText(ConfigManager.ConfigProperties.getProperty("helpSec1Line1"));
        }
        if (helpSec1Line2 != null) {
            helpSec1Line2.setText(ConfigManager.ConfigProperties.getProperty("helpSec1Line2"));
        }
        if (helpSec1Line3 != null) {
            helpSec1Line3.setText(ConfigManager.ConfigProperties.getProperty("helpSec1Line3"));
        }
        if (helpSec2Title != null) {
            helpSec2Title.setText(ConfigManager.ConfigProperties.getProperty("helpSec2Title"));
        }
        if (helpSec2Line1 != null) {
            helpSec2Line1.setText(ConfigManager.ConfigProperties.getProperty("helpSec2Line1"));
        }
        if (helpSec2Line1a != null) {
            helpSec2Line1a.setText(ConfigManager.ConfigProperties.getProperty("helpSec2Line1a"));
        }
        if (helpSec2Line1b != null) {
            helpSec2Line1b.setText(ConfigManager.ConfigProperties.getProperty("helpSec2Line1b"));
        }
        if (helpSec2Line2 != null) {
            helpSec2Line2.setText(ConfigManager.ConfigProperties.getProperty("helpSec2Line2"));
        }
        if (helpSec2Line2a != null) {
            helpSec2Line2a.setText(ConfigManager.ConfigProperties.getProperty("helpSec2Line2a"));
        }
        if (helpSec3Title != null) {
            helpSec3Title.setText(ConfigManager.ConfigProperties.getProperty("helpSec3Title"));
        }
        if (helpSec3Line1 != null) {
            helpSec3Line1.setText(ConfigManager.ConfigProperties.getProperty("helpSec3Line1"));
        }
        if (helpSec3Line2 != null) {
            helpSec3Line2.setText(ConfigManager.ConfigProperties.getProperty("helpSec3Line2"));
        }
        if (helpSec3Line3 != null) {
            helpSec3Line3.setText(ConfigManager.ConfigProperties.getProperty("helpSec3Line3"));
        }
        if (helpSec3Line3a != null) {
            helpSec3Line3a.setText(ConfigManager.ConfigProperties.getProperty("helpSec3Line3a"));
        }
        if (helpSec3Line3b != null) {
            helpSec3Line3b.setText(ConfigManager.ConfigProperties.getProperty("helpSec3Line3b"));
        }
        if (helpSec3Line3c != null) {
            helpSec3Line3c.setText(ConfigManager.ConfigProperties.getProperty("helpSec3Line3c"));
        }
        if (helpSec3Line4 != null) {
            helpSec3Line4.setText(ConfigManager.ConfigProperties.getProperty("helpSec3Line4"));
        }
        if (helpSec3Warning != null) {
            helpSec3Warning.setText(ConfigManager.ConfigProperties.getProperty("helpSec3Warning"));
        }
        if (helpSec4Title != null) {
            helpSec4Title.setText(ConfigManager.ConfigProperties.getProperty("helpSec4Title"));
        }
        if (helpSec4Line1 != null) {
            helpSec4Line1.setText(ConfigManager.ConfigProperties.getProperty("helpSec4Line1"));
        }
        if (helpSec4Line2 != null) {
            helpSec4Line2.setText(ConfigManager.ConfigProperties.getProperty("helpSec4Line2"));
        }
        if (helpOutro != null) {
            helpOutro.setText(ConfigManager.ConfigProperties.getProperty("helpOutro"));
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
            String cssBase = "/es/ies/puerto/css/temas/original.css";
            scene.getStylesheets().add(getClass().getResource(cssBase).toExternalForm());
            if (Sesion.getCssTemaActivo() != null) {
                scene.getStylesheets().add(getClass().getResource(Sesion.getCssTemaActivo()).toExternalForm());
            }
            AbstractController newController = fxmlLoader.getController();
            newController.setPreviousScene(stage.getScene());
            newController.setPreviousTitle(stage.getTitle());
            textMensaje.setText(" ");
            Image icon = new Image(getClass().getResource("/es/ies/puerto/img/logo.png").toExternalForm());
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
            String cssBase = "/es/ies/puerto/css/temas/original.css";
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
            Image icon = new Image(getClass().getResource("/es/ies/puerto/img/logo.png").toExternalForm());
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

     /** 
      * Hace un fade-in (opacidad 0→1) y deja el nodo visible 
      */
    public void fadeIn(Node node, double millis) {
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
    public void fadeOut(Node node, double millis) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(millis), node);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(e -> {
            node.setVisible(false);
            node.setOpacity(1);
        });
        fadeTransition.play();
    }
}