package GestorEleicoes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import Controller.LoginController;

public class Main extends Application {

    private GestorEleicoes facade = new GestorEleicoes();

    @Override
    public void start(Stage stage) throws Exception{

        this.facade.loadDistritos();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/login.fxml"));
        Parent login = fxmlLoader.load();


        LoginController loginController = fxmlLoader.getController();

        loginController.setFacade(facade);
        loginController.setParent(login);
        loginController.setScene(new Scene(login,300,200));
        loginController.setStage(stage);
        loginController.launchController();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
