package Model;


public class CadernoVoto {
	private boolean _votou;
	private int _eleicao = -1;
	private int _eleitor = -1;


	public int getEleicao(){return this._eleicao;}

	public int getEleitor(){return this._eleitor;}

	public void setEleitor(int e){
		this._eleitor=e;
	}

	public void setEleicao(int e){
		this._eleicao=e;
	}

	public void setVotou(boolean aVotou) {
		this._votou = aVotou;
	}

	public boolean getVotou() {
		return this._votou;
	}
}