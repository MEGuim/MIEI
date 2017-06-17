package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.time.LocalDate;

public class Cidadao {
	private SimpleStringProperty _nome;
	private Date _dataNascimento;
	private SimpleStringProperty _nacionalidade;
	private SimpleIntegerProperty _cartaoCidadao;
	private SimpleIntegerProperty _dadosEleitor;
	private boolean candidato;
	private SimpleStringProperty _concelho;
    private SimpleStringProperty _distrito;
    private SimpleStringProperty dataColuna = null;

    public Cidadao() {
        this._nome = new SimpleStringProperty();
        this._nacionalidade = new SimpleStringProperty();
        this._cartaoCidadao = new SimpleIntegerProperty();
		this._concelho = new SimpleStringProperty();
        this._distrito = new SimpleStringProperty();
        this._dadosEleitor = new SimpleIntegerProperty();
    }

	public int registarCandidato() {
		throw new UnsupportedOperationException();
	}

	public void setNome(String aNome) {this._nome.set(aNome);}

	public String getNome() {
		return this._nome.get();
	}

	public void setDataNascimento(Date aDataNascimento) {
		this._dataNascimento = aDataNascimento;
	}

	public Date getDataNascimento() {
		return this._dataNascimento;
	}

	public void setNacionalidade(String aNacionalidade) {this._nacionalidade.set(aNacionalidade);}

	public String getNacionalidade() {
		return this._nacionalidade.get();
	}

	public void setCartaoCidadao(int aCartaoCidadao) {
		this._cartaoCidadao.set(aCartaoCidadao);
	}

	public Integer getCartaoCidadao() {
		return this._cartaoCidadao.get();
	}

	public void setEleitor(int e) {
		this._dadosEleitor.set(e);
	}

	public int getEleitor() {
		return this._dadosEleitor.get();
	}

	public void setCandidato(boolean b) {
		this.candidato = b;
	}

	public boolean getCandidato() {
		return this.candidato;
	}

	public String getConcelho() { return this._concelho.get();}

	public void setConcelho(String c) { this._concelho.set(c);}

    public String getDistrito() { return this._distrito.get();}

    public void setDistrito(String c) { this._distrito.set(c);}

	public SimpleStringProperty _nomeProperty() { return this._nome;}

    public SimpleIntegerProperty _cartaoCidadaoProperty() { return this._cartaoCidadao; }

	public SimpleStringProperty getNacionalidadeProperty() { return  this._nacionalidade;}

    public SimpleStringProperty _concelhoProperty() { return this._concelho; }

    public SimpleStringProperty _distritoProperty() { return this._distrito; }

    public SimpleStringProperty dataColunaProperty() {
        return new SimpleStringProperty(this._dataNascimento.toString());
    }

    public SimpleIntegerProperty _dadosEleitorProperty() { return this._dadosEleitor; }

	@Override
	public String toString(){
		return "Cidadão: " + this.getNome() + "\nCartão de Cidadão: " + this.getCartaoCidadao() + "\nData de Nascimento: "
				+ this.getDataNascimento().toString() + "\nNacionalidade: " +this.getNacionalidade()
                + "\nDistrito: " + this._distrito.get() + "\nConcelho: " + this._concelho.get();
	}

}