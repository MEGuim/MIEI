package Model;

import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class Eleicao {

	private int idEleicao = -1;
	private Date _dataEleicao;
	private SimpleStringProperty dataColuna;
	private SimpleStringProperty _tipo;
	private boolean activa;
    private SimpleStringProperty modo;
	private int _votos_nulos;
	private int _votos_brancos;

	public Eleicao(){
		this.dataColuna = new SimpleStringProperty();
		this._tipo = new SimpleStringProperty();
		this.activa = false;
		this._votos_nulos=0;
		this._votos_brancos=0;
        this.modo = new SimpleStringProperty("Terminada");
	}

	public int getIdEleicao() {
		return idEleicao;
	}

	public void setIdEleicao(int idEleicao) {
		this.idEleicao = idEleicao;
	}

	public void setDataEleicao(Date aDataEleicao) {
		this._dataEleicao = aDataEleicao;
        this.dataColuna = new SimpleStringProperty(aDataEleicao.toString());
	}

	public Date getDataEleicao() {
		return this._dataEleicao;
	}

	public void setTipo(String aTipo) {
		this._tipo.set(aTipo);
	}

	public String getTipo() { return this._tipo.get(); }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
        if (activa) modo.set("A Decorrer");
        else modo.set("Agendada/Terminada");
    }

    public SimpleStringProperty _tipoProperty() { return this._tipo; }

	public SimpleStringProperty dataColunaProperty() { return this.dataColuna; }

    public SimpleStringProperty modoProperty() { return this.modo; }

    @Override
    public String toString() {
        return "Eleicao " + this.getIdEleicao() + " com " + this.getVotosBrancos() + " votos em branco e com " + this.getVotosNulos() + " votos nulos";
    }

	public int getVotosNulos() {
		return this._votos_nulos;
	}

	public void setVotosNulos(int _votos_nulos) {
		this._votos_nulos = _votos_nulos;
	}

	public int getVotosBrancos() {
		return _votos_brancos;
	}

	public void setVotosBrancos(int _votos_brancos) {
		this._votos_brancos = _votos_brancos;
	}
}