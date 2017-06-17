package Controller;

import GestorEleicoes.GestorEleicoes;
import Model.CadernoVoto;
import Model.Eleicao;
import Model.EleicaoListaCandidato;
import Model.Lista;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.*;


public class VotarController {


    @FXML
    private AnchorPane pane = new AnchorPane();


    private Parent parent;
    private Scene scene;
    private Stage stage;

    private GestorEleicoes facade;

    private List<RadioButton> listaButton;

    public void setFacade(GestorEleicoes facade) {this.facade = facade;}
    public void setParent(Parent parent) {this.parent = parent;}
    public void setScene(Scene scene) {this.scene = scene;}
    public void setStage(Stage stage) {this.stage = stage;}

    private Eleicao eleicao;
    private int idEleitor;

    public void launchController(Eleicao eleicao, int idEleitor) {
        this.eleicao = eleicao;
        this.idEleitor = idEleitor;

        ArrayList<EleicaoListaCandidato> elcList = (ArrayList<EleicaoListaCandidato>) this.facade._eleicaoListaCandidatoDAO.getByEleicao(eleicao.getIdEleicao());
        TreeSet<Integer> listas = new TreeSet<>();
        for (EleicaoListaCandidato e : elcList) {
            Integer idLista = e.getListaIdLista();
            if (!(listas.contains(idLista))) {
                listas.add(idLista);
            }
        }

        this.pane.setPrefSize(200,200);

        this.listaButton = new ArrayList<>();

        RadioButton r; double d = 30.0;

        for (Integer id: listas) {
            r = new RadioButton(this.facade._listaDAO.get(id).getNome());
            AnchorPane.setTopAnchor(r, d); d+=30;
            AnchorPane.setLeftAnchor(r, 20.0);

            this.pane.getChildren().add(r);

            this.listaButton.add(r);
        }
    }

    public void handleVoto() {

        CadernoVoto cadernoVoto = this.facade._cadernoVotoDAO.getByEleitorAndEleicao(this.idEleitor, eleicao.getIdEleicao());
        cadernoVoto.setVotou(true);

        // Key == 1 => Update
        this.facade._cadernoVotoDAO.put(1, cadernoVoto);

        Lista l;

        List<RadioButton> selected = new ArrayList<>();
        for (RadioButton r: listaButton) {
            if (r.isSelected())
                selected.add(r);
        }

        if (selected.size() == 1) {
            String tmp = selected.get(0).getText();
            l = this.facade._listaDAO.getByNome(tmp);
            l.setResultado(l.getResultado()+1);
            this.facade._listaDAO.put(l.getIdLista(), l);

        } else if (selected.size() == 0){
            this.eleicao.setVotosBrancos(this.eleicao.getVotosBrancos()+1);

        } else {
            this.eleicao.setVotosNulos(this.eleicao.getVotosNulos()+1);
        }

        this.stage.close();
    }
}
