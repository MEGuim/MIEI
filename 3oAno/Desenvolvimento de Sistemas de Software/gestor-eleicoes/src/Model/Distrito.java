package Model;

import javafx.beans.property.SimpleStringProperty;

public class Distrito {

    private int _id_distrito=-1;
    private SimpleStringProperty _designacao;

    public Distrito(){this._designacao = new SimpleStringProperty();}

    public Distrito(String nome) {this._designacao.set(nome);}

    public String getNome() {return this._designacao.get();}

    public void setNome(String nome) {this._designacao.set(nome);}

    public void setIdDistrito(int idDistrito){this._id_distrito=idDistrito;}

    public int getIdDistrito(){return this._id_distrito;}

    @Override
    public String toString() {return "Distrito " + this.getNome();}
}
