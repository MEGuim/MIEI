package li3_java;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * ´ParCoAutores:  Classe que guarda informação sobre um par de Autores.
 *
 * @author Bruno Pereira
 * @author João Mano
 * @author Miguel Guimarães
 * @version 2014
 */
public class ParCoAutores implements Serializable {

	private String autor1;
	private String autor2;
	private int numPubs;
	//private int ultimoAno;

	/**
	 * Método construtor vazio.
	 */
	public ParCoAutores() {
		this.autor1 = "";
		this.autor2 = "";
		this.numPubs = 0;
		//this.ultimoAno = 0;
	}

	/**
	 * Método construtor parametrizado.
	 * @param a1 - String, com o nome de um Autor.
	 * @param a2 - String, com o nome de um Autor.
	 * @param x - Int, número de publicações em comum.
	 */
	public ParCoAutores(String a1, String a2, int x){//, int ultAno) {
		this.autor1 = a1;
		this.autor2 = a2;
		this.numPubs = x;
		//this.ultimoAno = ultAno;
	}

	/**
	 * Método construtor de cópia.
	 * @param par - Objecto de tipo  ParCoAutores.
	 */
	public ParCoAutores(ParCoAutores par) {
		this.autor1 = par.getNome1();
		this.autor2 = par.getNome2();
		this.numPubs = par.getNumPubs();
		//this.ultimoAno = par.getUltimoAno();
	}

	/**
	 * Método que devolve o nome do 1º Autor.
	 * @return - String, nome do Autor.
	 */
	public String getNome1() {
		return this.autor1;
	}

	/**
	 * Método que devolve o nome do 1º Autor.
	 * @return - String, nome do Autor.
	 */
	public String getNome2() {
		return this.autor2;
	}

	/**
	 * Método que devolve o número de publicações em comum do ParCoAutores.
	 * @return - Int, número de publicações.
	 */
	public int getNumPubs() {
		return this.numPubs;
	}

	/**
	 * Adiciona X publicações ao ParCoAutores.
	 * @param x - Int, valor a adicionar.
	 */
	public void addPubs(int x) {
		this.numPubs += x;
	}

	@Override
	public ParCoAutores clone() {
		return new ParCoAutores(this);
	}

	@Override
	public boolean equals(Object par) {
		if (this == par) {
			return true;
		}
		if ((par == null) || (this.getClass() != par.getClass())) {
			return false;
		}

		ParCoAutores p = (ParCoAutores) par;
		return ((this.getNome1().equals(p.getNome1()) && this.getNome2().equals(p.getNome2())) || (this.getNome1().equals(p.getNome2()) && this.getNome2().equals(p.getNome1())));
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(new Object[] { this.autor1, this.autor2, this.numPubs });
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("\tNome dos Autor 1: ").append(this.getNome1()).append("\n\tNome do Autor 2: ").append(this.getNome2()).append("\n\tNúmero de publicações em comum: ").append(this.getNumPubs()).append("\n");
		return s.toString();
	}

}
