package Controller;


import Controller.DialogController.AgendarController;
import Controller.DialogController.RegistarController;
import GestorEleicoes.GestorEleicoes;
import Model.*;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

public class AdminController {



    @FXML
    private TableView<Eleicao> eleicoesTableView = new TableView<>();

    @FXML
    private TableView<Cidadao> eleitoresTableView = new TableView<>();

    @FXML
    private TableView<Cidadao> candidatosTableView = new TableView<>();

    @FXML
    private TextField filtro;
    @FXML
    private TextField eleicaoFiltro;

    @FXML
    private TextField ccCandidato;

    private ObservableList<Eleicao> eleicaoObservableList;
    private ObservableList<Cidadao> cidadaoObservableList;
    private ObservableList<Cidadao> candidatosObservableList;

    private Parent parent;
    private Scene scene;
    private Stage stage;

    private GestorEleicoes facade;

    public void setFacade(GestorEleicoes facade) {this.facade = facade;}

    public void setParent(Parent parent) {this.parent = parent;}

    public void setScene(Scene scene) {this.scene = scene;}

    public void setStage(Stage stage) {this.stage = stage;}


    public void launchController() {
        this.eleicaoObservableList = this.facade.getObservableListEleicoes();
        this.updateEleicoesTable(eleicaoObservableList);

        this.cidadaoObservableList = this.facade.getObservableListCidadaos();
        this.updateEleitoresTable(cidadaoObservableList);

        this.candidatosObservableList = this.facade.getObservableListCandidatos();
        this.candidatosTableView.setItems(this.candidatosObservableList);

        this.eleitoresTableView.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2)
                handleConsultVoter();
        });

        this.eleicoesTableView.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2)
                handleConsultResult();
        });

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void handleScheduleElection(ActionEvent actionEvent) {

        try {

            List<EleicaoListaCandidato> elcList = new ArrayList<>();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminController.class.getResource("/View/DialogView/agendarEleicao.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Agendar Eleição");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AgendarController agendarController = loader.getController();
            agendarController.setDialogStage(dialogStage);
            agendarController.setFacade(facade);
            agendarController.setListELC(elcList);

            agendarController.launchController();


            dialogStage.showAndWait();

            this.facade.agendarEleicao(elcList);

            this.eleicaoObservableList = this.facade.getObservableListEleicoes();
            this.updateEleicoesTable(eleicaoObservableList);

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public void handleCancelElection(ActionEvent actionEvent) {
        Eleicao eleicao = this.eleicoesTableView.getSelectionModel().getSelectedItem();
        if (eleicao != null) {
            this.facade.cancelarEleicao(eleicao.getIdEleicao());
        }

        this.eleicaoObservableList = this.facade.getObservableListEleicoes();
        this.updateEleicoesTable(eleicaoObservableList);
    }


    public void handleMarcarEleicaoActiva(ActionEvent actionEvent) {
        Eleicao eleicao = this.eleicoesTableView.getSelectionModel().getSelectedItem();
        if (eleicao != null) {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Marcar Eleição");
                alert.setHeaderText("Esta acção não pode ser anulada");
                alert.setContentText("Tem a certeza que pretende marcar esta eleição?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    this.facade.marcarEleicaoActiva(eleicao);
                }

            } catch (GestorEleicoes.EleicaoActivaException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Eleição ");
                alert.setContentText(e.toString());
                alert.show();
            }
        }
    }



    public void handleRegisterVoter(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RegistarController.class.getResource("/View/DialogView/registarEleitor.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Registar Eleitor");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.stage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            RegistarController registarController = loader.getController();
            registarController.setDialogStage(dialogStage);
            registarController.setFacade(facade);
            registarController.setModo("Registar");

            registarController.launchController();

            dialogStage.showAndWait();

            this.cidadaoObservableList = this.facade.getObservableListCidadaos();
            this.updateEleitoresTable(cidadaoObservableList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleConsultVoter() {
        Cidadao cidadao = this.eleitoresTableView.getSelectionModel().getSelectedItem();
        if (cidadao != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dados Eleitor");
            alert.setHeaderText(null);
            alert.setContentText(cidadao.toString());

            alert.showAndWait();
        }
    }

    public void handleChangeAddress(ActionEvent actionEvent) {
        Cidadao cidadao = this.eleitoresTableView.getSelectionModel().getSelectedItem();
        if (cidadao != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(RegistarController.class.getResource("/View/DialogView/registarEleitor.fxml"));
                AnchorPane page = loader.load();

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Editor Morada Eleitor");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(this.stage);

                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                RegistarController registarController = loader.getController();
                registarController.setDialogStage(dialogStage);
                registarController.setFacade(facade);
                registarController.setModo("Morada");
                registarController.setEleitor(this.facade._eleitorDAO.get(cidadao.getEleitor()));

                registarController.launchController();

                dialogStage.showAndWait();

                this.cidadaoObservableList = this.facade.getObservableListCidadaos();
                this.updateEleitoresTable(cidadaoObservableList);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleMarcarCandidato(ActionEvent actionEvent) {
        Integer cc = Integer.parseInt(this.ccCandidato.getText());
        if (this.facade._cidadaoDAO.containsKey(cc)) {
            Cidadao cidadao = this.facade._cidadaoDAO.get(cc);
            if (!cidadao.getCandidato()) {
                cidadao.setCandidato(true);
                this.facade._cidadaoDAO.put(cidadao.getCartaoCidadao(),cidadao);

                this.candidatosObservableList = this.facade.getObservableListCandidatos();
                this.candidatosTableView.setItems(this.candidatosObservableList);
            }
        }
    }

    public void handleConsultResult() {
        Eleicao eleicao = this.eleicoesTableView.getSelectionModel().getSelectedItem();
        if (eleicao != null) {

            List<EleicaoListaCandidato> elcList = this.facade._eleicaoListaCandidatoDAO.getByEleicao(eleicao.getIdEleicao());
            TreeSet<Integer> listas = new TreeSet<>();
            for (EleicaoListaCandidato e : elcList) {
                Integer idLista = e.getListaIdLista();
                if (!(listas.contains(idLista))) {
                    listas.add(idLista);
                }
            }

            String resultados = "";
            for (Integer idLista: listas) {
                Lista l = this.facade._listaDAO.get(idLista);
                resultados = resultados + l.getNome() + ": " + l.getResultado() + "\n\t";
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dados Eleição");
            alert.setHeaderText(null);
            alert.setContentText(eleicao.toString() + "\n\t" + resultados);

            alert.showAndWait();
        }
    }

    public void updateEleitoresTable(ObservableList<Cidadao> cidadaoObservableList) {
        FilteredList<Cidadao> filteredData = new FilteredList<>(cidadaoObservableList, p -> true);
        filtro.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredData.setPredicate(cidadao -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String cartaoCidadao = String.valueOf(cidadao.getCartaoCidadao());
                return cartaoCidadao.contains(newValue);
            });
        }));

        SortedList<Cidadao> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(eleitoresTableView.comparatorProperty());
        eleitoresTableView.setItems(sortedData);
    }

    public void updateEleicoesTable(ObservableList<Eleicao> eleicaoObservableList) {
        FilteredList<Eleicao> filteredData = new FilteredList<>(eleicaoObservableList, p -> true);
        eleicaoFiltro.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredData.setPredicate(eleicao -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String idEleicao = String.valueOf(eleicao.getIdEleicao());
                return idEleicao.contains(newValue);
            });
        }));

        SortedList<Eleicao> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(eleicoesTableView.comparatorProperty());
        eleicoesTableView.setItems(sortedData);
    }


    public void handleFinalizeButton(ActionEvent actionEvent) {
        Eleicao eleicao = this.eleicoesTableView.getSelectionModel().getSelectedItem();
        if (eleicao != null) {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Finalizar Eleição");
                alert.setHeaderText("Esta acção não pode ser anulada");
                alert.setContentText("Tem a certeza que pretende finalizar esta eleição?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    this.facade.marcarEleicaoFinalizada(eleicao);
                }

            } catch (GestorEleicoes.EleicaoFinalizadaException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Eleição ");
                alert.setContentText(e.toString());
                alert.show();
            }
        }
    }

    public void handleLogout(ActionEvent actionEvent) {
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
}
