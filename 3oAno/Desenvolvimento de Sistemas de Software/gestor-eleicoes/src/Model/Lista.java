package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.HashSet;

public class Lista {
    private int idLista = -1;
	private SimpleStringProperty _nome;
	private Image _imagem;
    private int _resultado;
	private HashSet<Cidadao> _candidatos = new HashSet<>();

    public Lista(){
        this._nome = new SimpleStringProperty();
        this._imagem = null;
        this._candidatos = new HashSet<>();
        this._resultado = 0;
    }


    public Lista(String aNome) {
        this._nome = new SimpleStringProperty(aNome);
        this._imagem = null;
        this._candidatos = new HashSet<>();
        this._resultado = 0;
    }

	public int adicionarCandidato() {
		throw new UnsupportedOperationException();
	}

    public int getIdLista() { return this.idLista; }

    public int getResultado(){return this._resultado;}

    public void setIdLista(int idLista) { this.idLista = idLista; }

    public void setResultado(int resultado){this._resultado=resultado;}

    public String getNome() {
        return _nome.get();
    }

    public void setNome(String _nome) {
        this._nome.set(_nome);
    }

    public SimpleStringProperty getNomeProperty() {
        return this._nome;
    }

    public HashSet<Cidadao> getCandidatos (){
        return this._candidatos;
    }

    @Override
    public String toString() {
        return _nome.getValue();
    }
}