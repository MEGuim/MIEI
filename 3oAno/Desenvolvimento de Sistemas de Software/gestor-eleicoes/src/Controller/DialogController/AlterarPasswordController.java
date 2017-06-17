package Controller.DialogController;


import GestorEleicoes.GestorEleicoes;
import Model.Eleitor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.util.Optional;

public class AlterarPasswordController {

    @FXML
    private PasswordField oldpasswordTextField;

    @FXML
    private PasswordField newPasswordTextField;

    public Button passwordConfirmButton;
    public Button cleanPasswordButton;

    private GestorEleicoes facade;

    private String password;
    private String oldPassword;
    private String newPassword;

    private Parent parent;
    private Scene scene;
    private Stage dialogStage;

    public void setFacade(GestorEleicoes facade) {this.facade = facade;}

    public void setParent(Parent parent) {this.parent = parent;}

    public void setScene(Scene scene) {this.scene = scene;}

    public void setDialogStage(Stage dialogStage) {this.dialogStage = dialogStage;}

    private Eleitor eleitor;


    public void launchController(int codigo){
        this.eleitor = this.facade.getEleitor(codigo);
        this.setPassword(eleitor.getPassword());
    }





    public void handlePasswordConfirmButton(ActionEvent actionEvent) {

        this.setOldPassword(this.getOldpasswordTextField().getText());
        this.setNewPassword(this.getNewPasswordTextField().getText());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alteração de password");
        alert.setHeaderText("Atenção!");
        alert.setContentText("Tem a certeza que pretende alterar a password?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if(this.getOldPassword().equals(this.getPassword())){
                this.eleitor.setPassword(this.getNewPassword());
                this.facade.updatePasswordEleitor(this.eleitor.getNumero(),eleitor);
                this.dialogStage.close();
            }
            else{
                Alert novoAlerta = new Alert(Alert.AlertType.ERROR);
                novoAlerta.setTitle("Password errada");
                novoAlerta.setHeaderText("Introduziu a password errada!");
                novoAlerta.setContentText("Tente novamente");
                novoAlerta.show();
            }
        }

    }

    public void handleCleanPasswordButton(ActionEvent actionEvent) {
        this.oldpasswordTextField.clear();this.newPasswordTextField.clear();
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public PasswordField getOldpasswordTextField() {
        return oldpasswordTextField;
    }

    public void setOldpasswordTextField(PasswordField oldpasswordTextField) {
        this.oldpasswordTextField = oldpasswordTextField;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public PasswordField getNewPasswordTextField() {
        return newPasswordTextField;
    }

    public void setNewPasswordTextField(PasswordField newPasswordTextField) {
        this.newPasswordTextField = newPasswordTextField;
    }
}
