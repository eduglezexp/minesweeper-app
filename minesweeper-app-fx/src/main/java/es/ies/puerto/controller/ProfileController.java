package es.ies.puerto.controller;

import java.sql.SQLException;
import java.util.List;

import es.ies.puerto.config.ConfigManager;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.entities.UsuarioEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public class ProfileController extends AbstractController {

    @FXML 
    private Text textUsuario;

    @FXML 
    private Text textNombre;

    @FXML 
    private Text textEmail;

    @FXML
    private TextField textFieldUsuario;

    @FXML
    private TextField textFieldNombre;

    @FXML
    private TextField textFieldEmail;

    @FXML 
    private Text textMensaje;

    @FXML
    private Button openEditarButton;

    @FXML
    private Button openJugarButton;

    @FXML
    private Button buttonVolverAtras;

    /**
     * Metodo de inicializacion de la interfaz
     */
    @FXML
    public void initialize() {
        cambiarIdioma();
    }

    /**
     * Carga los datos del usuario en los campos de la interfaz grafica para Json
     * @param usuario El objeto Usuario con los datos que se mostraran en pantalla
     * Nota: Si se va a usar Json, cambiar el UsuarioEntitySqlite por UsuarioEntityJson
     */
    public void cargarDatosUsuario(UsuarioEntity usuario) {
        if (usuario != null) {
            textFieldUsuario.setText(usuario.getUser());
            textFieldNombre.setText(usuario.getName());
            textFieldEmail.setText(usuario.getEmail());
        }
    }

    /**
     * Maneja el evento de clic en el boton editar
     * Redirige a la pantalla de registro
     */
    @FXML
    protected void openEditarClick() {
        try {
            List<UsuarioEntity> usuarios = getUsuarioService().obtenerUsuarioPorEmailOUser(textFieldUsuario.getText());
            if (!usuarios.isEmpty()) {
                UsuarioEntity usuario = usuarios.get(0);
                String tituloPantalla = ConfigManager.ConfigProperties.getProperty("registroTitle");
                mostrarPantalla(openEditarButton, "registro.fxml", tituloPantalla, usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja el evento de clic en el boton de jugar
     * Redirige a la pantalla del juego
     */
    @FXML
    protected void openJugarClick() {
        try {
            List<UsuarioEntity> usuarios = getUsuarioService().obtenerUsuarioPorEmailOUser(textFieldUsuario.getText());
            if (!usuarios.isEmpty()) {
                UsuarioEntity usuario = usuarios.get(0);
                String tituloPantalla = ConfigManager.ConfigProperties.getProperty("juegoTitle");
                mostrarPantalla(openJugarButton, "juego.fxml", tituloPantalla, usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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