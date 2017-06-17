package Model;

import javafx.beans.property.SimpleStringProperty;

public class Eleitor {
	private int _numero = -1;
	private SimpleStringProperty _localVoto;
	private SimpleStringProperty _password;

    public Eleitor() {
        this._localVoto = new SimpleStringProperty();
        this._password = new SimpleStringProperty();
    }

    public void setNumero(int aNumero) {
		this._numero = aNumero;
	}

	public int getNumero() {
		return this._numero;
	}

	public void setLocalVoto(String aLocalVoto) {
		this._localVoto.set(aLocalVoto);
	}

	public String getLocalVoto() {
		return this._localVoto.get();
	}

	public void setPassword(String aPassword) {
		this._password.set(aPassword);
	}

	public String getPassword() {
		return this._password.get();
	}

	public SimpleStringProperty getLocalVotoProperty(){return this._localVoto;}

	public SimpleStringProperty getPasswordProperty(){return this._password;}

	@Override
	public String toString(){
		return "Eleitor:" + this.getNumero() + " Local de Voto" + getLocalVoto();
	}
}