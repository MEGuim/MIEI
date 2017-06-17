package li3_java;

import java.io.Serializable;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Autor:  Classe que guarda informação sobre um Autor.
 *
 * @author Bruno Pereira
 * @author João Mano
 * @author Miguel Guimarães
 * @version 2014
 */
public class Autor implements Serializable {

	private String nome;
	private int numeroPubsSolo;
	private int numeroPubsCoAutores;
	private TreeMap<String, CoAutor> coAutores;
	//private long start;
	//private long end;

	/**
	 * Método construtor vazio.
	 */
	public Autor() {
		this.nome = "";
		this.numeroPubsSolo = 0;
		this.numeroPubsCoAutores = 0;
		this.coAutores = new TreeMap<>();
	}

	/**
	 * Método construtor parametrizado.
	 * @param nome - String, representa o nome do Autor.
	 */
	public Autor(String nome) {
		this.nome = nome;
		this.numeroPubsSolo = 1;
		this.numeroPubsCoAutores = 0;
		this.coAutores = new TreeMap<>();
	}

	/**
	 * Método construtor parametrizado.
	 * @param nome - String, representa o nome do Co-Autor.
	 * @param auts - Lista de nomes de Autores.
	 */
	public Autor(String nome, List<String> auts) {
		this.nome = nome;
		if (auts.size() > 1) {
			this.numeroPubsSolo = 0;
			this.numeroPubsCoAutores = 1;
		} else {
			this.numeroPubsSolo = 1;
			this.numeroPubsCoAutores = 0;
		}

		this.coAutores = new TreeMap<>();
		for (String s : auts) {
			if (!s.equals(nome)) {
				CoAutor coAut = new CoAutor(s);
				this.coAutores.put(s, coAut);
				//this.coAutores.get(s).adicionaPub();
			}
		}
	}

	/**
	 * Método construtor de cópia.
	 * @param aut - Objecto de tipo Autor.
	 */
	public Autor(Autor aut) {
		this.nome = aut.getNome();
		this.numeroPubsSolo = aut.getNumeroPubsSolo();
		this.numeroPubsCoAutores = aut.getNumeroPubsCoAutores();
		this.coAutores = new TreeMap<>();

		for (CoAutor coAut : aut.getCoAutores().values()) {
			this.coAutores.put(coAut.getNome(), coAut.clone());
		}
	}

	/**
	 * Método que devolve o nome do Autor.
	 * @return - String com o nome do Co-Autor.
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Método que devolve o número de publicações a solo.
	 * @return - Int , representa o número de publicações.
	 */
	public int getNumeroPubsSolo() {
		return this.numeroPubsSolo;
	}

	/**
	 * Método que devolve o número de publicações com outros Autores.
	 * @return - Int , representa o número de publicações.
	 */
	public int getNumeroPubsCoAutores() {
		return this.numeroPubsCoAutores;
	}

	/**
	 * Método que devolve um map que representa os Co-Autores deste Autor.
	 * @return - Map de Co-Autores, de chave String com o nome do Co-Autor, para valores, de tipo CoAutor.
	 */
	public Map<String, CoAutor> getCoAutores() {
		TreeMap<String, CoAutor> aux = new TreeMap<>();

		for (CoAutor coAut : this.coAutores.values()) {
			aux.put(coAut.getNome(), coAut.clone());
		}

		return aux;
	}

	/**
	 * Método que adiiciona uma lista de nomes e adiciona à lista de Co-Autores.
	 * @param autores - Lista de Strings com nomes de Co-Autores.
	 */
	public void adicionaCoAutores(List<String> autores) {
		this.adicionaPub(autores);

		for (String s : autores) {
			if (!s.equals(this.nome)) {
				if (this.coAutores.containsKey(s)) {
					this.coAutores.get(s).adicionaPub();
				} else {
					CoAutor coAut = new CoAutor(s);
					this.coAutores.put(s, coAut);
					//this.coAutores.get(s).adicionaPub();
				}
			}
		}
	}

	/**
	 * Adiciona uma publicação, dado uma lista de nomes de Autores, caso a lista tenha tamanho maior que um, incrementa numeroPubsCoAutores.
	 * @param autores - Lista de Strings com nomes de Co-Autores.
	 */
	public void adicionaPub(List<String> autores) {
		if (autores.size() > 1) {
			this.numeroPubsCoAutores++;
		} else {
			this.numeroPubsSolo++;
		}
	}

	/**
	 * Método que dados dois ints, incrementa, numeroPubsSolo e numeroPubsCoAutores.
	 * @param nPubsSolo - valor a incrementar para numeroPubsSolo, int.
	 * @param nPubsCoAutor - valor a incrementar para numeroPubsCoAutores, int.
	 */
	public void incrementaPubs(int nPubsSolo, int nPubsCoAutor) {
		this.numeroPubsSolo += nPubsSolo;
		this.numeroPubsCoAutores += nPubsCoAutor;
	}

	@Override
	public boolean equals(Object a) {
		if (this == a) {
			return true;
		}

		if (a == null || this.getClass() != a.getClass()) {
			return false;
		}

		Autor aut = (Autor) a;
		return this.getNome().equals(aut.getNome());
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(new Object[] {this.nome, this.numeroPubsCoAutores, this.numeroPubsSolo, this.coAutores});
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Nome do Autor: ");
		s.append(this.getNome());
		s.append("\n");
		s.append("Número de publicações a solo: ");
		s.append(this.getNumeroPubsSolo());
		s.append("\nNúmero de publicações com co-autores: ");
		s.append(this.getNumeroPubsCoAutores());
		s.append("\nLista de Co-Autores:\n");
		for (CoAutor coAut : this.coAutores.values()) {
			s.append(coAut.toString()).append("\n");
		}
		return s.toString();
	}

	@Override
	public Autor clone() {
		return new Autor(this);
	}
}
