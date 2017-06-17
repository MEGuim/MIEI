package li3_java;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
/**
 *
 * RedeAnualAutores:  Classe que guarda informação referente a publicações de um ano.
 *
 * @author Bruno Pereira
 * @author João Mano
 * @author Miguel Guimarães
 * @version 2014
 */
public class RedeAnualAutores implements Serializable {

	private int ano;
	private int numAutores;
	private int numPubs;
	private TreeMap<String, Autor> redeAnualAutores;

	/**
	 * Método construtor vazio.
	 */
	public RedeAnualAutores() {
		this.ano = 0;
		this.numAutores = 0;
		this.numPubs = 0;
		this.redeAnualAutores = new TreeMap<>();
	}

	/**
	 * Método construtor parametrizado.
	 * @param ano - Int, representa o ano.
	 * @param listaAutores - Lista de Strings com nomes de Autores.
	 */
	public RedeAnualAutores(int ano, List<String> listaAutores) {
		this.ano = ano;
		this.numAutores = listaAutores.size();
		this.numPubs = 1;
		this.redeAnualAutores = new TreeMap<>();
		for (String autor : listaAutores) {
			Autor aut = new Autor(autor, listaAutores);
			this.redeAnualAutores.put(autor, aut);
		}
	}

	/**
	 * Método construtor de cópia.
	 * @param umaRedeAnualAutores - Objecto do tipo RedeAnualAutores.
	 */
	public RedeAnualAutores(RedeAnualAutores umaRedeAnualAutores) {
		this.ano = umaRedeAnualAutores.getAno();
		this.numAutores = umaRedeAnualAutores.getNumAutores();
		this.numPubs = umaRedeAnualAutores.getNumPubs();
		this.redeAnualAutores = (TreeMap<String, Autor>) umaRedeAnualAutores.getRedeAnualAutores();
	}

	/**
	 * Método que devolve o ano da Rede.
	 * @return - Int, representa o ano.
	 */
	public int getAno() {
		return this.ano;
	}

	/**
	 * Método que devolve o número de Autores desse ano.
	 * @return - int, número de Autores desse ano. 
	 */
	public int getNumAutores() {
		return this.numAutores;
	}

	/**
	 * Método que devolve o número de publicações desse ano.
	 * @return - int, número de publicações desse ano. 
	 */
	public int getNumPubs() {
		return this.numPubs;
	}

	/**
	 * Método que devolve um map que representa os Autores desse ano.
	 * @return- Map de Autores, de chave String com o seu nome.
	 */
	public Map<String, Autor> getRedeAnualAutores() {
		Map<String, Autor> aux = new TreeMap<>();

		for (Autor aut : this.redeAnualAutores.values()) {
			aux.put(aut.getNome(), aut.clone());
		}

		return aux;
	}

	/**
	 * Método que adiiciona uma lista de nomes e adiciona à lista de Autores.
	 * @param autores - Lista de Strings com nomes de Autores.
	 */
	public void insereAutores(List<String> autores) {
		for (String s : autores) {
			if (!this.redeAnualAutores.containsKey(s)) {
				this.numAutores++;
				Autor aut = new Autor(s, autores);
				this.redeAnualAutores.put(s, aut);
				//this.redeAnualAutores.get(s).adicionaCoAutores(autores, this.ano);
			} else {
				this.redeAnualAutores.get(s).adicionaCoAutores(autores);
			}

		}
		this.numPubs++;
	}

	/**
	 * Método que dado um nome de um Autor, devolve o seu número de publicações nesse ano.
	 * @param nomeAutor - String, com o nome de um Autor.
	 * @return - int, número de publicaçoes.
	 */
	public int numPubsAutor(String nomeAutor) {
		return this.redeAnualAutores.get(nomeAutor).getNumeroPubsCoAutores() + this.redeAnualAutores.get(nomeAutor).getNumeroPubsSolo();
	}

	@Override
	public RedeAnualAutores clone() {
		return new RedeAnualAutores(this);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(new Object[]{this.ano, this.numAutores, this.numPubs, this.redeAnualAutores});
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		RedeAnualAutores other = (RedeAnualAutores) obj;
		return (this.ano == other.ano
				&& this.numAutores == other.numAutores
				&& this.numPubs == other.numPubs
				&& this.redeAnualAutores.equals(other.getRedeAnualAutores()));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Ano: ");
		sb.append(this.getAno());
		sb.append("\nNum Autores: ");
		sb.append(this.getNumAutores());
		sb.append("\nNum Publicações: ");
		sb.append(this.getNumPubs());
		sb.append("\n\n");
		for (Autor aut : this.redeAnualAutores.values()) {
			//sb.append("\t");
			sb.append(aut.toString());
			sb.append("\n");
		}
		return sb.toString();
	}

}
