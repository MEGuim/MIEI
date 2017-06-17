package Controller.DialogController;

import GestorEleicoes.GestorEleicoes;
import Model.Candidato;
import Model.Cidadao;
import Model.EleicaoListaCandidato;
import Model.Lista;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.adapter.JavaBeanStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Iterator;
import java.util.List;

/**
 * Created by davide
 */

public class ListaController {
    public TextField nomeLista;
    public TextField nrCandidato;
    public TextField partido;
    public TextField hierarquia;
    public Button adicionarCandidatoButton;
    public Button removerCandidatoButton;
    public Button okButton;
    public Button cancelButton;

    public TableView<Cidadao> listaCandidatos;
    public TableColumn<String, String> partidoColuna;
    public TableColumn<String, String> hierarquiaColuna;

    private ObservableList<Cidadao> candidatosObservableList;

    public ListView<Cidadao> candidatosListView;

    private List<EleicaoListaCandidato> elcList;
    private List<Lista> listLista;

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

    public void setListLista(List<Lista> listLista) {
        this.listLista = listLista;
    }

    public void launchController() {
        this.candidatosObservableList = FXCollections.observableArrayList();
        this.listaCandidatos.setItems(candidatosObservableList);

        this.candidatosListView.setItems(this.facade.getObservableListCandidatos());

    }

    public void handleAdicionarCandidato(ActionEvent actionEvent) {

        Cidadao cidadao;

        try {
            int cartaoCidadao = Integer.parseInt(this.nrCandidato.getText());
            String partido = this.partido.getText();
            String hierarquia = this.hierarquia.getText();

            if (this.facade._cidadaoDAO.containsKey(cartaoCidadao)) {
                cidadao = this.facade._cidadaoDAO.get(cartaoCidadao);
                if (cidadao.getCandidato()) {
                    for (EleicaoListaCandidato elc : this.elcList) {
                        if (elc.getCidadaoCartaoCidadao() == cidadao.getCartaoCidadao())
                            throw new CidadaoInseridoNumaListaException("Cidadão já inserido numa lista nesta eleição");
                    }
                    EleicaoListaCandidato newElc = new EleicaoListaCandidato();
                    newElc.setCidadaoCartaoCidadao(cartaoCidadao);
                    newElc.setHierarquia(hierarquia);
                    newElc.setPartido(partido);
                    this.elcList.add(newElc);

                    this.candidatosObservableList.add(cidadao);

                    this.partidoColuna.setCellValueFactory(p -> new SimpleStringProperty(partido));
                    this.hierarquiaColuna.setCellValueFactory(p -> new SimpleStringProperty(hierarquia));

                }
            } else throw new CidadaoNaoRegistadoException("Cidadão não se encontra registado no sistema");
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dados Inválidos");
            alert.setHeaderText("Número de candidato contém caracteres inválidos");
            String s = "Introduza um número de eleitor válido";
            alert.setContentText(s);
            alert.show();
        } catch (CidadaoInseridoNumaListaException | CidadaoNaoRegistadoException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(e.toString());
            alert.setContentText("Escolha um número de cidadão válido");
            alert.show();
        }
    }

    public void handleRemoverCandidato(ActionEvent actionEvent) {
        try {

            Cidadao cidadao = this.listaCandidatos.getSelectionModel().getSelectedItem();
            if (cidadao != null) {

                int cartaoCidadao = cidadao.getCartaoCidadao();

                if (this.facade._cidadaoDAO.containsKey(cartaoCidadao)) {
                    for (EleicaoListaCandidato e : elcList) {
                        if (e.getCidadaoCartaoCidadao() == cartaoCidadao) {
                            this.elcList.remove(e);
                            this.candidatosObservableList.remove(cidadao);
                            break;
                        }
                    }
                } else throw new CidadaoNaoRegistadoException("Cidadão não se encontra registado no sistema");
            }
        } catch (CidadaoNaoRegistadoException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(e.toString());
            alert.setContentText("Escolha um número de cidadão válido");
            alert.show();
        }
    }

    public void handleOkLista(ActionEvent actionEvent) {
        Lista l = new Lista();
        l.setNome(this.nomeLista.getText());

        l = this.facade._listaDAO.put(l.getIdLista(), l);

        for (EleicaoListaCandidato elc : this.elcList)
            if (elc.getListaIdLista() == -1)
                elc.setListaIdLista(l.getIdLista());

        this.listLista.add(l);

        this.dialogStage.close();

    }

    public void handleCancelLista(ActionEvent actionEvent) {

        dialogStage.close();
    }

    private class CidadaoInseridoNumaListaException extends Throwable {
        public CidadaoInseridoNumaListaException(String s) {
            super(s);
        }
    }

    private class CidadaoNaoRegistadoException extends Throwable {
        public CidadaoNaoRegistadoException(String s) {
            super(s);
        }
    }
}
