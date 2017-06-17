package Controller.DialogController;

import Controller.AdminController;
import GestorEleicoes.GestorEleicoes;
import Model.Eleicao;
import Model.EleicaoListaCandidato;
import Model.Lista;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class AgendarController {


    @FXML
    private Button newListButton;
    @FXML
    private Button delListButton;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ChoiceBox<String> tipoBox;
    @FXML
    private DatePicker dataPicker;
    @FXML
    private ListView<Lista> listView = new ListView<>();

    private ObservableList<Lista> observableList;
    private List<EleicaoListaCandidato> elcList;
    List<Lista> listaList = new ArrayList<>();

    private Parent parent;
    private Scene scene;
    private Stage dialogStage;

    private GestorEleicoes facade;

    public void setFacade(GestorEleicoes facade) {this.facade = facade;}

    public void setParent(Parent parent) {this.parent = parent;}

    public void setScene(Scene scene) {this.scene = scene;}

    public void setDialogStage(Stage dialogStage) {this.dialogStage = dialogStage;}


    public void setListELC(List<EleicaoListaCandidato> elcList) {
        this.elcList = elcList;
    }

    public void launchController() {

        ObservableList<String> observableListTipo = FXCollections.observableArrayList();
        observableListTipo.add("Presidencial");
        observableListTipo.add("Legislativas");
        this.tipoBox.setItems(observableListTipo);

        this.newListButton.disableProperty().bind(
                Bindings.or(this.tipoBox.valueProperty().isNull(), this.dataPicker.valueProperty().isNull()));

        this.delListButton.setDisable(true);

    }


    public void handleOKAction(ActionEvent actionEvent) {
         try {
             if (this.observableList.isEmpty() || this.listaList.size() == 0)
                 throw new EleicaoSemListasException("Eleicao sem nenhuma lista");

            Eleicao e = new Eleicao();
            e.setDataEleicao(Date.valueOf(this.dataPicker.getValue()));
            e.setTipo(this.tipoBox.getValue());

            e = this.facade._eleicaoDAO.put(e.getIdEleicao(), e);

            for (EleicaoListaCandidato elc : this.elcList)
                elc.setEleicaoIdEleicao(e.getIdEleicao());

             this.dialogStage.close();

         } catch (EleicaoSemListasException e) {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(e.toString());
             alert.setContentText("Insira pelo menos uma lista antes de continuar");
             alert.show();
         }
    }

    public void handleCancelAction(ActionEvent actionEvent) {
        dialogStage.close();
    }


    public void handleNewList(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ListaController.class.getResource("/View/DialogView/adicionarLista.fxml"));
            TabPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ListaController listaController = loader.getController();
            listaController.setDialogStage(dialogStage);
            listaController.setFacade(facade);
            listaController.setListELC(elcList);
            listaController.setListLista(listaList);

            listaController.launchController();

            dialogStage.showAndWait();

            this.observableList = FXCollections.observableList(listaList);
            this.listView.setItems(this.observableList);

            this.delListButton.setDisable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDelList(ActionEvent actionEvent) {
        if (this.listaList.size() == 0)
            this.delListButton.setDisable(true);

        Lista l = this.listView.getSelectionModel().getSelectedItem();
        if (l != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(("Remover Lista"));
            alert.setHeaderText("A lista selecionada está prestes a ser removida");
            alert.setContentText("Pretende continuar?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Iterator<EleicaoListaCandidato> it = this.elcList.iterator();
                while (it.hasNext()) {
                    EleicaoListaCandidato e = it.next();
                    if (e.getListaIdLista() == l.getIdLista())
                        it.remove();
                }
                this.listaList.remove(l);
                this.facade._listaDAO.remove(l.getIdLista());
            }

            this.observableList = FXCollections.observableList(listaList);
            this.listView.setItems(this.observableList);
        }

    }

    private class EleicaoSemListasException extends Throwable {
        public EleicaoSemListasException(String s) {
            super(s);
        }
    }
}
