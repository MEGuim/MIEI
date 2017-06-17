package Controller;


import GestorEleicoes.GestorEleicoes;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class LoginController {

    public TextField textField_name;
    public PasswordField passwordField_password;

    private GestorEleicoes facade;

    private Parent parent;
    private Scene scene;
    private Stage stage;

    public void setFacade(GestorEleicoes facade) {this.facade = facade;}

    public void setParent(Parent parent) {this.parent = parent;}

    public void setScene(Scene scene) {this.scene = scene;}

    public void setStage(Stage stage) {this.stage = stage;}


    public void launchController() {
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    public void handleLoginAction() throws IOException {

        String text = this.textField_name.getText();
        String password = this.passwordField_password.getText();

        if (text.equals("admin") && (password.equals("admin"))) {
            this.startAdminController();
        } else {
            try {
                Integer codigo = Integer.parseInt(text);
                int loginResult = this.facade.login(codigo,password);
                if (loginResult != 0) this.startEleitorController(codigo);
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Dados Inválidos");
                    alert.setHeaderText("A Password está incorrecta!");
                    String s = "Volte a introduzir os dados ou solicite novos acessos ao Administrador";
                    alert.setContentText(s);
                    alert.show();
                    }
                }catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Dados Inválidos");
                alert.setHeaderText("Número de eleitor contém caracteres inválidos");
                String s = "Introduza um número de eleitor válido";
                alert.setContentText(s);
                alert.show();
            } catch (NullPointerException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Dados Inválidos");
                    alert.setHeaderText("O Utilizador não existe!");
                    String s = "Volte a introduzir os dados ou solicite novos acessos ao Administrador";
                    alert.setContentText(s);
                    alert.show();
            }
        }
    }

    public void handleClearAction() {
        this.textField_name.clear(); this.passwordField_password.clear();
    }

    public void startAdminController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/admin.fxml"));
        Parent admin = fxmlLoader.load();

        AdminController adminController = fxmlLoader.getController();
        adminController.setFacade(this.facade);
        adminController.setParent(admin);
        adminController.setScene(new Scene(admin,800,600));
        adminController.setStage(this.stage);
        adminController.launchController();
    }

    public void startEleitorController(int codigo) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/eleitor.fxml"));
        Parent eleitor = fxmlLoader.load();

        EleitorController eleitorController = fxmlLoader.getController();
        eleitorController.setFacade(this.facade);
        eleitorController.setParent(eleitor);
        eleitorController.setScene(new Scene(eleitor,800,600));
        eleitorController.setStage(this.stage);
        eleitorController.launchController(codigo);

    }

    public void handleEnterPressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            this.handleLoginAction();
        }
    }

}
