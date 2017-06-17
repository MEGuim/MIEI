package Controller;

import Controller.DialogController.AlterarPasswordController;
import GestorEleicoes.GestorEleicoes;


import GestorEleicoes.GestorEleicoes;
import Model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class EleitorController {

    @FXML
    private Button votarButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label nomeLabel;

    @FXML
    private Label datebirthLabel;

    @FXML
    private Label nacionalidadeLabel;

    @FXML
    private Label concelhoLabel;

    @FXML
    private Label distritoLabel;

    @FXML
    private Label localvotoLabel;

    @FXML
    private Button changepasswordButton;

    @FXML
    private TableView<Eleicao> historicoTableView = new TableView<>();

    @FXML
    private TableView<Eleicao> eleicoesEleitorTableView = new TableView<>();


    private ObservableList<Eleicao> activasObservableList;
    private ObservableList<Eleicao> historicoObservableList;



    private Parent parent;
    private Stage stage;
    private Scene scene;
    private GestorEleicoes facade;
    private int codigo;
    private Cidadao uCidadao;

    public void setFacade(GestorEleicoes facade) {this.facade = facade;}

    public void setParent(Parent parent) {this.parent = parent;}

    public void setScene(Scene scene) {this.scene = scene;}

    public void setStage(Stage stage) {this.stage = stage;}


    public void launchController(int codigo){
        this.setCodigo(codigo);
        Eleitor uEleitor = this.facade.getEleitor(codigo);
        this.uCidadao = this.facade.getUtilizador(codigo);
        String nome = uCidadao.getNome();
        Date dateofBirth = uCidadao.getDataNascimento();
        String nacionalidade = uCidadao.getNacionalidade();
        String distrito = uCidadao.getDistrito();
        String concelho = uCidadao.getConcelho();
        String local_voto = uEleitor.getLocalVoto();


        this.historicoObservableList = this.facade.getObservableListHistoricoEleicao(codigo);
        this.historicoTableView.setItems(this.historicoObservableList);

        this.activasObservableList = this.facade.getObservableListEleicoesActivas(codigo);
        this.eleicoesEleitorTableView.setItems(this.activasObservableList);


        this.nomeLabel.setText(nome);
        this.datebirthLabel.setText(dateofBirth.toString());
        this.nacionalidadeLabel.setText(nacionalidade);
        this.distritoLabel.setText(distrito);
        this.concelhoLabel.setText(concelho);
        this.localvotoLabel.setText(local_voto);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void handleChangePasswordButton(ActionEvent actionEvent){

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminController.class.getResource("/View/DialogView/alterarPassword.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Mudar Password");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AlterarPasswordController altera = loader.getController();
            altera.setDialogStage(dialogStage);
            altera.setFacade(facade);

            altera.launchController(this.getCodigo());


            dialogStage.showAndWait();

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void handleLogoutButton(ActionEvent actionEvent){
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText("Tem a certeza que deseja sair?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                startLoginController();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void startLoginController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/login.fxml"));
        Parent login = fxmlLoader.load();

        LoginController loginController = fxmlLoader.getController();
        loginController.setFacade(this.facade);
        loginController.setParent(login);
        loginController.setScene(new Scene(login,300,200));
        loginController.setStage(this.stage);
        loginController.launchController();
    }

    public void handleVotar() {
        try {

            Eleicao eleicao = this.eleicoesEleitorTableView.getSelectionModel().getSelectedItem();
            CadernoVoto cadernoVoto = this.facade._cadernoVotoDAO.getByEleitorAndEleicao(uCidadao.getEleitor(),eleicao.getIdEleicao());
            if (cadernoVoto != null && !cadernoVoto.getVotou()) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(VotarController.class.getResource("/View/boletimTemplate.fxml"));
                TitledPane page = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Boletim Voto");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(this.stage);

                Scene scene = new Scene(page);
                stage.setScene(scene);

                VotarController votarController = loader.getController();
                votarController.setStage(stage);
                votarController.setFacade(facade);
                votarController.setParent(this.parent);

                votarController.launchController(eleicao, uCidadao.getEleitor());

                stage.show();
            } else throw new JaVotouException("Já votou nesta eleição!");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JaVotouException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Eleição");
            alert.setContentText(e.toString());
            alert.show();
        }

    }

    private class JaVotouException extends Throwable {
        public JaVotouException(String s) {
            super(s);
        }
    }
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
