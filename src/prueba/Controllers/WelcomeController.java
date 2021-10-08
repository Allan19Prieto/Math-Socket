package prueba.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import prueba.Main;
import prueba.NetworkManager.NetworkManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    @FXML
    public TextField nombreJugador;
    @FXML
    public TextField puertoJugador;
    @FXML
    public TextField displayScene;
    @FXML
    private Button nextButton;

    private NetworkManager manager;

    private Parent parent;
    private Stage stage;
    private Scene scene;

    //Metodo para mostrar la pantalla y llamar la grafica
    public WelcomeController(Stage stage) {
        this.stage = stage;

        //llamamos a la parate grafica
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("welcome.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = fxmlLoader.load();
            scene = new Scene(parent, 500, 475);
        } catch (IOException e) {
            // Tomamos la execcion
        }
    }

    //Hacemos la escena visible
    public void displayScene() {
        this.stage.setScene(this.scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // no permite el enfoque automatico en los primeros campos
        Platform.runLater(() -> nombreJugador.getParent().requestFocus());

        // Validaciones para mensajes
        final ContextMenu usernameValidator = new ContextMenu();
        usernameValidator.setAutoHide(true);
        usernameValidator.getStyleClass().add("error");

        final ContextMenu listeningPortValidator = new ContextMenu();
        listeningPortValidator.setAutoHide(true);
        listeningPortValidator.getStyleClass().add("error");

        final ContextMenu openingListeningPort = new ContextMenu();
        openingListeningPort.setAutoHide(true);
        openingListeningPort.getStyleClass().add("error");

        //Validaciones para este campo
        nombreJugador.focusedProperty().addListener(
                (arg0, oldPropertyValue, newPropertyValue) -> {
                    if (newPropertyValue) {
                        // Clearing message if any
//                        actiontarget.setText("");
                        // Hiding the error message
                        usernameValidator.hide();
                    }
                });

        nextButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                //Aviso de que fue presionado
                System.out.println("["+ nombreJugador.getText()+"]" + " Next pressed!");

                //Pasamos el nombre a una variable y el puerto de escucha
                String username = nombreJugador.getText();
                Integer listeningPort = null;

                //Validaciones para el nomnre de usuario
                if (username == null || username.equals(""))
                {
                    usernameValidator.getItems().clear();
                    MenuItem it = new MenuItem("Por favor ingrese su nombre");
                    usernameValidator.getItems().add(it);
                    usernameValidator.show(nombreJugador, Side.BOTTOM, 0, 0);

                    return;
                }

                // Validaciones para el puerto y convertirlo a integer
                try
                {
                    listeningPort = Integer.valueOf(puertoJugador.getText());
                }
                catch (NumberFormatException nfEx)
                {
                    listeningPortValidator.getItems().clear();
                    MenuItem it = new MenuItem("Puerto no valido");
                    listeningPortValidator.getItems().add(it);
                    listeningPortValidator.show(puertoJugador, Side.BOTTOM, 0, 0);
                }

                try
                {
                    //Pasamos los datos que necesitamos para el jugador
                    MainController mainController = new MainController(stage);
                    manager = new NetworkManager(listeningPort, username, mainController);
                    manager.setupServer();
                    manager.startNetworking();

                    mainController.setNetworkManager(manager);
                    mainController.displayScene(username, listeningPort.toString());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    openingListeningPort.getItems().clear();
                    MenuItem it = new MenuItem("No se puede escuchar en el puerto: " + listeningPort);
                    openingListeningPort.getItems().add(it);
                    openingListeningPort.show(puertoJugador, Side.BOTTOM, 0, 0);
                }

            }

        });
    }

}
