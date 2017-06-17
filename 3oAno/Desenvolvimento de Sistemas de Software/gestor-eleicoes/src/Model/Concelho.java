package Model;

import javafx.beans.property.SimpleStringProperty;

public class Concelho {
    private int _id_concelho = -1;
    private SimpleStringProperty _designacao;
    private int _dadosDistrito;

    public Concelho(){this._designacao = new SimpleStringProperty();}
    public Concelho(String designacao) { _designacao.set(designacao);}

    public String getNome() {
        return _designacao.get();
    }

    public void setNome(String designacao) {
        this._designacao.set(designacao);
    }

    public int getIdConcelho(){return this._id_concelho;}

    public void setIdConcelho(int idconcelho){this._id_concelho = idconcelho;}

    public void setDistrito(int d){this._dadosDistrito=d;}

    public int getDistrito (){return this._dadosDistrito;}

    public SimpleStringProperty getDesignacaoProperty(){return this._designacao;}


    @Override
    public String toString() {
        return "Concelho " + this.getNome();
    }
}