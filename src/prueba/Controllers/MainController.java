package prueba.Controllers;

import MathSocket.Funciones;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import prueba.Game.Game;
import prueba.Game.GameState;
import prueba.Game.GameStateListener;
import prueba.NetworkManager.NetworkManager;
import prueba.NetworkManager.Peer;
import prueba.NetworkManager.SupervisorListener;
import prueba.NetworkManager.protocol.GameMessage;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class MainController implements SupervisorListener, GameStateListener {

    private Parent parent;
    private Stage stage;
    private Scene scene;

    private String username;
    private NetworkManager manager;

    private Game game;
    private GameState state;
    private HashMap<String, Game.Action> playerActions;

    private Integer peersAmount;

    @FXML public Label labelUsername;
    @FXML public Label label_tipo_casilla;
    @FXML public Label labelId;
    @FXML public Label label;
    @FXML public Label labelPort;
    @FXML public Button connectButton;
    @FXML public TextField peerHost;
    @FXML public TextField peerPort;
    @FXML public Button buttonM;

    @FXML public Button azul_inicio;
    @FXML public Button azul_0;
    @FXML public Button azul_1;
    @FXML public Button azul_2;
    @FXML public Button azul_3;
    @FXML public Button azul_4;
    @FXML public Button azul_5;
    @FXML public Button azul_6;
    @FXML public Button azul_7;
    @FXML public Button azul_8;
    @FXML public Button azul_9;
    @FXML public Button azul_10;
    @FXML public Button azul_11;
    @FXML public Button azul_12;
    @FXML public Button azul_13;
    @FXML public Button azul_final;
    @FXML public Button btn_trampa;

    @FXML
    Button btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_10, btn_11, btn_12, btn_13, btn_14;

    @FXML
    private Pane Tablero;

    //Instanciamos la clase de las funciones
    Funciones fn;
    //LKL para el tablero del juego
    public LinkedList<String> tablero;
    //Arreglo que contrendra las palabras
    public String[] palabras;

    //cuando cae en trampa
    int pase_Trampa = 0;
    //Solo par ala primera vuelta de sumado el indice del jugador 1
    int pase = 0;
    int num_lanzado = 0;
    int num_jugador = 0;

    int indice_jugador1 = 0;

    //Nombre de la casilla en la que se esta
    String nombre_casilla;

    public MainController(Stage stage) {
        this.stage = stage;

        this.game = new Game();
        this.state = new GameState(this);
        this.playerActions = new HashMap<>();
        this.peersAmount = 0;

        tablero = new LinkedList<>();
        palabras = new String[14];
        fn = new Funciones();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        fxmlLoader.setController(this);
        try
        {
            parent = fxmlLoader.load();
            scene = new Scene(parent, 700, 500);
        }
        catch (IOException e) {
            System.out.println("Something went wrong instantiating MainController e:" + e.toString());
        }
    }

    public void setNetworkManager(NetworkManager manager)
    {
        this.manager = manager;
    }

    public void displayScene(String username, String listeningPort) {
        this.username = username;
        this.game.addPlayer(this.username); // TODO
        this.labelUsername.setText(username);
        this.labelId.setText(manager.getId());
        this.labelPort.setText("Listening on port: " + listeningPort);

        this.stage.setScene(this.scene);
        stage.show();
        stage.setOnCloseRequest(e -> exit(null));

    }

    // Called when the peer connect button is clicked
    public void connectPeer(ActionEvent actionEvent) {
        int port = Integer.parseInt(peerPort.getText());
        String host = peerHost.getText();
        try {
            manager.addPeer(host, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGameStateChanged(GameState.State state) {
        Platform.runLater(() ->
        {
            switch (state)
            {
                case OPEN:
                    /*rockButton.setDisable(false);
                    paperButton.setDisable(false);
                    scissorsButton.setDisable(false);*/
                    connectButton.setDisable(false);
                    break;

                case IN_PROGRESS:
                    connectButton.setDisable(true);
                    break;

                case WAITING_FOR_PEERS:
                    System.out.println("Waiting for all peers to send their actions.." + "\n");
                    break;

                case WAITING_FOR_SELF:
                    connectButton.setDisable(true);
                    System.out.println("Waiting for your action.." + "\n");
                    break;
            }
        });
    }

    @Override
    public void onPeerDisconnected(Peer peer) {
        Platform.runLater(() -> {

            if(this.playerActions.get(peer.getConnectedPeerUsername()) != null)
            {
                this.playerActions.remove(peer.getConnectedPeerUsername());
                this.state.stateUpdate(GameState.StateUpdate.ACTION_REMOVED);
            }
            this.game.removePlayer(peer.getConnectedPeerUsername());

            System.out.println("[PEER] Disconnected peer at " + peer.getHost().getHostAddress() + ":" + peer.getPort() + "\n");
            this.peersAmount = this.peersAmount - 1;
        });
    }

    @Override
    public void onReceiveCommand(Peer peer, String data) {

    }

    @Override
    public void onPeerConnected(Peer peer) {
        Platform.runLater(() -> {
            this.game.addPlayer(peer.getConnectedPeerUsername());
            this.peersAmount = this.peersAmount + 1;
            String line = "[PEER] Added peer at " + peer.getHost().getHostAddress() + ":" + peer.getPort() + "\n";
            System.out.println(line);
        });
    }

    @Override
    public void onNotice(Peer peer, String msg) {
        Platform.runLater(() -> {
            System.out.println("[ERR] Network warning: " + msg + "\n");
        });
    }

    @Override
    public void onReceiveCommand(Peer peer, String operation, String data) {
        Platform.runLater(() -> {
            if (data.equals(GameMessage.actions[GameMessage.ACTION_ROCK])) {
                this.playerActions.put(peer.getConnectedPeerUsername(), Game.Action.ROCK);
            } else if (data.equals(GameMessage.actions[GameMessage.ACTION_PAPER])) {
                this.playerActions.put(peer.getConnectedPeerUsername(), Game.Action.PAPER);
            } else if (data.equals(GameMessage.actions[GameMessage.ACTION_SCISSORS])) {
                this.playerActions.put(peer.getConnectedPeerUsername(), Game.Action.SCISSORS);
            }
            this.state.stateUpdate(GameState.StateUpdate.ACTION_RECEIVED);
            if (peersAmount == this.state.getNumberOfPeerActionsReceived()) {
                this.state.stateUpdate(GameState.StateUpdate.ALL_ACTIONS_RECEIVED);
            }
        });
    }

    public void exit(ActionEvent actionEvent)
    {
        if (manager != null) {
            manager.shutdown();
        }
        Platform.exit();
    }

    @FXML
    public void mostrarTablero(ActionEvent actionEvent) {
        //llenar el arreglo de palabras
        int contador = 1;
        for (int j = 0; j<=6; j++){
            palabras[j] = "Reto"+(j+1);
            if (contador == 1){
                contador ++;
                for (int k = 7; k <=11; k++){
                    palabras[k] = "Trampa";
                    if (contador == 2){
                        for (int n = 12; n<=13; n++){
                            palabras[n] = "Tunel";
                        }
                    }
                }
            }
        }

        //Llenar el tablero con las palabras aleatoriamente
        ArrayList<Integer> num = new ArrayList<Integer>();
        int var;
        //Solo sacamos numeros que no esten repedidos en el randon
        while (num.size() < 14){
            var = fn.Ram();
            if (!num.contains(var)) {
                num.add(var);
                tablero.add(palabras[var]);
            }
        }

        System.out.println("Revisando pase");
        //Mostar en el tablero los palabras
        btn_1.setText(tablero.get(0));
        btn_2.setText(tablero.get(1));
        btn_3.setText(tablero.get(2));
        btn_4.setText(tablero.get(3));
        btn_5.setText(tablero.get(4));
        btn_6.setText(tablero.get(5));
        btn_7.setText(tablero.get(6));
        btn_8.setText(tablero.get(7));
        btn_9.setText(tablero.get(8));
        btn_10.setText(tablero.get(9));
        btn_11.setText(tablero.get(10));
        btn_12.setText(tablero.get(11));
        btn_13.setText(tablero.get(12));
        btn_14.setText(tablero.get(13));

        Tablero.setVisible(true);
    }


    //Para el movimiento del punto azul
    @FXML
    public void Movimiento_Azul(int indice_jugador1){

        //Valida posicion del jugador
        if (indice_jugador1 == 0){
            azul_inicio.setVisible(false);
            //**
            azul_0.setVisible(true);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 1){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            //**
            azul_1.setVisible(true);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 2){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            //**
            azul_2.setVisible(true);
            azul_3.setVisible(false);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 3){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            //**
            azul_3.setVisible(true);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 4){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            //**
            azul_4.setVisible(true);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 5){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_4.setVisible(false);
            //**
            azul_5.setVisible(true);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 6){
            azul_inicio.setVisible(false);
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_4.setVisible(false);
            azul_5.setVisible(false);
            //**
            azul_6.setVisible(true);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 7){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_4.setVisible(false);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            //**
            azul_7.setVisible(true);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 8){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_4.setVisible(false);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            //**
            azul_8.setVisible(true);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }

        if (indice_jugador1 == 9){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_4.setVisible(false);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            //**
            azul_9.setVisible(true);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);

        }
        if (indice_jugador1 == 10){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_4.setVisible(false);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            //**
            azul_10.setVisible(true);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 11){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_4.setVisible(false);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            //*******
            azul_11.setVisible(true);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 12){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_4.setVisible(false);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            //****
            azul_12.setVisible(true);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 13){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_4.setVisible(false);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            //***
            azul_13.setVisible(true);
            //azul_final.setVisible(false);
        }
    }

    @FXML
    public void lanzarDados(javafx.event.ActionEvent actionEvent) throws InterruptedException {
        if (pase_Trampa == 0) {
            System.out.println("Lanzamiento de Dado");
            num_lanzado = fn.Lanzar_Dado(); //Hace funcionar el dado
            label.setText(num_lanzado + " casillas");
            System.out.println(num_lanzado);
        }

        //solo valida la vuelta
        if (pase_Trampa == 1){
            pase_Trampa = 0;
        }

        //solo para esta funcion
        //Ejemplo cuando le da un jugador y luego el otro
        //cambio de variables
        //indice donde se posiciona

        if (num_jugador == 0){
            num_jugador++;

            //****************
            if (pase == 0){
                //Solo sucede la primera vez
                indice_jugador1 = num_lanzado - 1;
                pase ++;

                //Funcion para el movimiento
                Movimiento_Azul(indice_jugador1);

            }else {
                // Validacion si el mayor a los numeros en lista
                if (indice_jugador1 + num_lanzado > 13){
                    indice_jugador1 = 13;
                    azul_9.setVisible(false);
                    azul_10.setVisible(false);
                    azul_11.setVisible(false);
                    azul_12.setVisible(false);
                    azul_13.setVisible(false);
                    azul_final.setVisible(true);
                }else {
                    //Suma de la variable normal
                    if (indice_jugador1 + num_lanzado < 0){
                        indice_jugador1 = 0;
                        azul_inicio.setVisible(true);
                        azul_0.setVisible(false);
                        azul_1.setVisible(false);
                        azul_2.setVisible(false);
                    }else {
                        indice_jugador1 = indice_jugador1 + (num_lanzado);

                        //Funcion para el movimiento
                        Movimiento_Azul(indice_jugador1);
                    }
                }
            }

            System.out.println("Lanzo el jugador 1");
            //label_jugador.setText("Jugador 1");
            //label_jugador.setStyle("-fx-background-color: Blue");
            nombre_casilla = tablero.get(indice_jugador1);
            System.out.println(nombre_casilla);

            //Validacion de las trampas
            if (nombre_casilla.equals("Trampa")){
                //Se habilita el boton de trampa
                //btn_trampa.setVisible(true);
                pase_Trampa = 1;
                num_lanzado = -3;
                num_jugador = 0;
                lanzarDados(actionEvent);
                btn_trampa.setVisible(false);
                System.out.println("**");
                Thread.sleep(2000);
            }


            //implrime la casilla en la que estoy
            label_tipo_casilla.setText(nombre_casilla);

        }else if (num_jugador == 1){
            num_jugador --;
            System.out.println("Lanzo el jugador 2");
            //label_jugador.setText("Jugador 2");
            //label_jugador.setStyle("-fx-background-color: Red");
            System.out.println(tablero.get(num_lanzado-1));
        }

    }
}