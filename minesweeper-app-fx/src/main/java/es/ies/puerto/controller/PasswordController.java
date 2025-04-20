package es.ies.puerto.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

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

public class PasswordController extends AbstractController{

    @FXML 
    private Text textEmail;

    @FXML 
    private TextField textFieldEmail;

    @FXML 
    private Text textMensaje;

    @FXML 
    private Button openAceptarButton;

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
     * Metodo que maneja el evento de clic en el boton de recuperacon de contrasenia
     * Verifica si el email ingresado es valido y si existe en el sistema
     * Muestra mensajes de error o exito segun el resultado
     */
    @FXML
    protected void onPasswordButtonClick() {
        if (textFieldEmail == null ||  textFieldEmail.getText().isEmpty()) {
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorEmailVacioNulo"));
            return;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        if (!emailPattern.matcher(textFieldEmail.getText()).matches()) {
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorEmailFormato"));
            return;
        }
        List<UsuarioEntity> email;
        try {
            email = getUsuarioService().obtenerUsuarioPorEmailOUser(textFieldEmail.getText());
            if (email.isEmpty()) {
                textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorEnviarEmail"));
                return;
            }
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("emailEnvioCorrecto"));
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