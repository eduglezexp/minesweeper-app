package es.ies.puerto.controller;

import java.sql.SQLException;
import java.util.List;

import es.ies.puerto.config.ConfigManager;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.entities.UsuarioEntity;
import es.ies.puerto.model.services.UsuarioService;
import es.ies.puerto.config.Sesion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public class UsuariosController extends AbstractController{

    @FXML
    private Text textListaUsuario;

    @FXML
    private TextField textFieldBuscarUsuario;

    @FXML
    private Button buscarUsuariosButton;

    @FXML
    private Text textMensaje;

    @FXML
    private ListView<UsuarioEntity> listViewUsuarios; 

    @FXML
    private Button buttonVolverAtras;

    /**
     * Metodo de inicializacion de la interfaz
     */
    @FXML
    public void initialize() {
        cambiarIdioma();
        configurarListView();
        cargarUsuarios();
    }

    /**
     * Configura la visualizacion de la lista de usuarios y
     * define como se mostraran los elementos en la ListView
     */
    private void configurarListView() {
        listViewUsuarios.setCellFactory(param -> new ListCell<UsuarioEntity>() {
        @Override
        protected void updateItem(UsuarioEntity usuario, boolean empty) {
            super.updateItem(usuario, empty);
            if (empty || usuario == null) {
                setText(null);
                return;
            } 
            String idRow = ConfigManager.ConfigProperties.getProperty("idRow");
            String usuarioRow = ConfigManager.ConfigProperties.getProperty("usuarioRow");
            String emailRow = ConfigManager.ConfigProperties.getProperty("emailRow");
            String nombreRow = ConfigManager.ConfigProperties.getProperty("nombreRow");
            String formato = ""+idRow+": %s\n"+usuarioRow+": %s\n"+emailRow+": %s\n"+nombreRow+": %s";
            String texto = String.format(
                formato, 
                usuario.getId(), 
                usuario.getUser(), 
                usuario.getEmail(),
                usuario.getName()
                );
            setText(texto);
            }
        });
    }

    /**
     * Metodo que carga los usuarios desde la base de datos y los muestra en la ListView
     */
    private void cargarUsuarios() {
        try {
            UsuarioService service = getUsuarioService();
            List<UsuarioEntity> listaUsuarios = service.obtenerUsuarios();
            ObservableList<UsuarioEntity> usuarios = FXCollections.observableArrayList(listaUsuarios);
            listViewUsuarios.setItems(usuarios);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para filtrar los usuarios de la lista
     */
    @FXML
    protected void onBuscarUsuarios() {
        if (textFieldBuscarUsuario == null || textFieldBuscarUsuario.getText().trim().isEmpty()) {
            cargarUsuarios();
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorCredencialesVacios"));
            return;
        }
        List<UsuarioEntity> usuariosFiltrados;
        try {
            usuariosFiltrados = getUsuarioService().obtenerUsuarioPorInput(textFieldBuscarUsuario.getText());
            if (usuariosFiltrados == null || usuariosFiltrados.isEmpty()) {
                textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorUsuarioNoEncontrado"));
                cargarUsuarios();
                return;
            }
            listViewUsuarios.setItems(FXCollections.observableArrayList(usuariosFiltrados));
            textMensaje.setText(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja el evento de clic al pulsar un usario de la lista
     * Redirige a la pantalla de perfil
     */
    @FXML
    protected void onMouseClicked() {
        UsuarioEntity usuarioSeleccionado = listViewUsuarios.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado == null) {
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorSelecioneUsuario"));
            return;
        }
        try {
            String cssTema = getUsuarioTemasService().obtenerCssTemaActivo(usuarioSeleccionado.getId());
            if (cssTema == null || cssTema.isBlank()) {
                cssTema = "/es/ies/puerto/css/style.css"; 
            }
            Sesion.setCssTemaActivo(cssTema);
        } catch (SQLException e) {
            e.printStackTrace();
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorObtenerTema"));
        }
        Sesion.setUsuario(usuarioSeleccionado);
        String tituloPantalla = ConfigManager.ConfigProperties.getProperty("profileTitle");
        mostrarPantalla(listViewUsuarios, "profile.fxml", tituloPantalla, usuarioSeleccionado);
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