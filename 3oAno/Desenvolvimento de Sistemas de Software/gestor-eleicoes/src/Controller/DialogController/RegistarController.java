package Controller.DialogController;

import GestorEleicoes.GestorEleicoes;
import Model.Cidadao;
import Model.Concelho;
import Model.Distrito;
import Model.Eleitor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

public class RegistarController {

    public TextField nome;
    public TextField cartaoCidadao;
    public ChoiceBox<String> distrito;
    public ChoiceBox<String> concelho;
    public DatePicker dataNascimento;
    public TextField nacionalidade;
    public TextField numeroEleitor;
    public Button genElectorNumber;
    public Button okButton;
    public Button clearButton;

    private ObservableList<String> distritos;
    private ObservableList<String> concelhos;

    private Parent parent;
    private Scene scene;
    private Stage dialogStage;
    private String modo;
    private boolean candidato;

    private GestorEleicoes facade;

    private Eleitor eleitor;

    private String eleitorNome;
    private Date eleitorDataNasc;
    private String eleitorCartaoCidadao;
    private String eleitorNacionalidade;
    private String eleitorDistrito;
    private String eleitorConcelho;

    public void setFacade(GestorEleicoes facade) {this.facade = facade;}

    public void setParent(Parent parent) {this.parent = parent;}

    public void setScene(Scene scene) {this.scene = scene;}

    public void setDialogStage(Stage dialogStage) {this.dialogStage = dialogStage;}

    public void setModo(String modo) { this.modo = modo; }

    public void setEleitor(Eleitor eleitor) { this.eleitor = eleitor; }

    public void launchController() {

        this.okButton.setDisable(true);

        if (modo.equals("Morada")) {
            Cidadao cidadao = this.facade._cidadaoDAO.getByEleitor(eleitor.getNumero());

            this.nome.setText(cidadao.getNome());
            this.cartaoCidadao.setText(String.valueOf(cidadao.getCartaoCidadao()));
            this.dataNascimento.setValue(cidadao.getDataNascimento().toLocalDate());
            this.nacionalidade.setText(cidadao.getNacionalidade());
            this.distrito.setValue(cidadao.getDistrito());
            this.numeroEleitor.setText(String.valueOf(cidadao.getEleitor()));
            this.candidato = cidadao.getCandidato();

            this.eleitorNome = this.nome.getText();
            LocalDate date = this.dataNascimento.getValue();
            this.eleitorDataNasc = Date.valueOf(date);
            this.eleitorCartaoCidadao = this.cartaoCidadao.getText();
            this.eleitorNacionalidade = this.nacionalidade.getText();
            this.eleitorDistrito = this.distrito.getValue();

            this.nome.setDisable(true);
            this.cartaoCidadao.setDisable(true);
            this.dataNascimento.setDisable(true);
            this.nacionalidade.setDisable(true);
            this.numeroEleitor.setDisable(true);
            this.genElectorNumber.setDisable(true);
            this.clearButton.setDisable(true);
            this.okButton.setDisable(false);
        }

        this.distritos = this.facade.getObservableListDistritos();
        this.distrito.setItems(distritos);

        this.distrito.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                concelhos = facade.getObservableListConcelhos(newValue);
                concelho.setItems(concelhos);
            }
        });



    }

    public void handleSubmitButton(ActionEvent actionEvent) {

        Cidadao cidadao = new Cidadao();
        cidadao.setCartaoCidadao(Integer.parseInt(this.eleitorCartaoCidadao));
        cidadao.setNome(this.eleitorNome);
        cidadao.setDataNascimento(this.eleitorDataNasc);
        cidadao.setNacionalidade(this.eleitorNacionalidade);
        cidadao.setEleitor(this.eleitor.getNumero());
        if (modo.equals("Morada")) {
            cidadao.setCandidato(candidato);
            try {
                String distrito = this.distrito.getSelectionModel().getSelectedItem();
                String concelho = this.concelho.getSelectionModel().getSelectedItem();

                if (distrito == null) throw new Exception("Campo Distrito não pode ser vazio");
                if (concelho == null) throw new Exception("Campo Concelho não pode ser vazio");

                cidadao.setConcelho(concelho);
                cidadao.setDistrito(distrito);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Campos Nulos");
                alert.setContentText(e.toString());
                alert.show();
            }
        }
        else {
            cidadao.setCandidato(false);
            cidadao.setConcelho(this.eleitorConcelho);
            cidadao.setDistrito(this.eleitorDistrito);
        }

        try {
            if (modo.equals("Registar"))
                this.facade.registarCidadao(cidadao,false);
            else if (modo.equals("Morada"))
                this.facade.registarCidadao(cidadao,true);
        } catch (GestorEleicoes.CidadaoJaRegistadoException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Cartão Cidadão Existente");
            alert.setContentText(e.toString());
            alert.show();
        }

        this.dialogStage.close();
    }

    public void handleClearButton(ActionEvent actionEvent) {
        this.nome.clear();
        this.cartaoCidadao.clear();
        this.distrito.getSelectionModel().clearSelection();
        this.concelho.getSelectionModel().clearSelection();
        this.dataNascimento.getEditor().clear();
        this.nacionalidade.clear();
    }

    public void handleGenerateAction(ActionEvent actionEvent) {



        this.eleitorNome = this.nome.getText();

        LocalDate date = this.dataNascimento.getValue();

        this.eleitorCartaoCidadao = this.cartaoCidadao.getText();

        this.eleitorNacionalidade = this.nacionalidade.getText();

        this.eleitorDistrito = this.distrito.getSelectionModel().getSelectedItem();

        this.eleitorConcelho = this.concelho.getSelectionModel().getSelectedItem();

        try {
            if (nome == null) throw new Exception("Campo Nome não pode ser vazio");
            if (date == null) throw new Exception("Campo Data não pode ser vazio");
            if (cartaoCidadao == null) throw new Exception("Campo Cartão Cidadão não pode ser vazio");
            if (nacionalidade == null) throw new Exception("Campo Nacionalidade não pode ser vazio");
            if (distrito == null) throw new Exception("Campo Distrito não pode ser vazio");
            if (concelho == null) throw new Exception("Campo Concelho não pode ser vazio");

            this.eleitorDataNasc = Date.valueOf(date);

            this.okButton.setDisable(false);
            this.clearButton.setDisable(true);
            this.genElectorNumber.setDisable(true);

            this.nome.setEditable(false);
            this.dataNascimento.setEditable(false);
            this.dataNascimento.setDisable(true);
            this.cartaoCidadao.setEditable(false);
            this.nacionalidade.setEditable(false);
            this.distrito.setDisable(true);
            this.concelho.setDisable(true);

            this.eleitor = new Eleitor();

            TextInputDialog textInputDialog = new TextInputDialog("");
            textInputDialog.setTitle("Password");
            textInputDialog.setContentText("Please enter your prefered password:");

            Optional<String> result = textInputDialog.showAndWait();
            result.ifPresent(this.eleitor::setPassword);

            this.eleitor.setLocalVoto(this.eleitorConcelho);

            this.eleitor = this.facade._eleitorDAO.put(this.eleitor.getNumero(), this.eleitor);

            this.numeroEleitor.setText(String.valueOf(this.eleitor.getNumero()));

        } catch (NullPointerException e) {
            System.out.println("NULL");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Campos Nulos");
            alert.setContentText(e.toString());
            alert.show();
        }
    }

}
