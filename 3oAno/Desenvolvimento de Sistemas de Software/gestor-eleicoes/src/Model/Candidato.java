package Model;

import javafx.beans.property.SimpleStringProperty;
import sun.java2d.pipe.SpanShapeRenderer;


public class Candidato {
	private int _numero = -1;
	private SimpleStringProperty _partido;
	private SimpleStringProperty _hierarquia;

	public Candidato(){
		this._partido = new SimpleStringProperty();
		this._hierarquia = new SimpleStringProperty();
	}

	public Candidato(String partido, String hierarquia){
		this._partido = new SimpleStringProperty(partido);
		this._hierarquia = new SimpleStringProperty(hierarquia);
	}

	public void setNumero(int aNumero) {
		this._numero = aNumero;
	}

	public int getNumero() {
		return this._numero;
	}

	public void setPartido(String aPartido) {
		this._partido.set(aPartido);
	}

	public String getPartido() {
		return this._partido.get();
	}

	public void setHierarquia(String aHierarquia) {
		this._hierarquia.set(aHierarquia);
	}

	public String getHierarquia() {
		return this._hierarquia.get();
	}

	public SimpleStringProperty getPartidoProperty(){return this._partido;}

	public SimpleStringProperty getHierarquiaProperty(){return this._hierarquia;}


	@Override
	public String toString(){
		return "Candidato " + this.getNumero() + " do Partido " +this.getPartido() + " na posição " + this.getHierarquia();
	}
}