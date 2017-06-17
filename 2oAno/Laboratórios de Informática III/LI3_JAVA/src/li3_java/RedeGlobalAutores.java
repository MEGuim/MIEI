package li3_java;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.swing.JOptionPane;

/**
 *
 * RedeAnualAutores:  Classe que guardae gere a informação referente a publicações.
 *
 * @author Bruno Pereira
 * @author João Mano
 * @author Miguel Guimarães
 * @version 2014
 */
public class RedeGlobalAutores implements Serializable {

	private TreeMap<Integer, RedeAnualAutores> redeGlobalAutores;
	private String ficheiroLido;
	private int artigosLidos;
	private int nomesLidos;
	private int nomesDistintos;
	private int menorAno;
	private int maiorAno;
	private int numPubsUmAutor;

	/**
	 * Método construtor vazio.
	 *
	 */
	public RedeGlobalAutores() {
		this.redeGlobalAutores = new TreeMap<>();
		this.ficheiroLido = "";
		this.artigosLidos = 0;
		this.nomesLidos = 0;
		this.nomesDistintos = 0;
		this.menorAno = 20000;
		this.maiorAno = 0;
	}

	/**
	 * Método construtor de cópia.
	 * @param rede - Objecto do tipo RedeGlobalAutores.
	 */
	public RedeGlobalAutores(RedeGlobalAutores rede) {
		this.redeGlobalAutores = (TreeMap<Integer, RedeAnualAutores>) rede.getRedeGlobalAutores();
		this.ficheiroLido = rede.getFicheiroLido();
		this.artigosLidos = rede.getArtigosLidos();
		this.nomesLidos = rede.getNomesLidos();
		this.nomesDistintos = rede.getNomesDistintos();
		this.menorAno = rede.getMenorAno();
		this.maiorAno = rede.getMaiorAno();
	}

	/**
	 * Método que preenche uma RedeGlobalAutores com informação de um ficheiro.
	 * @param fileName - String, nome do ficheiro.
	 * @throws IOException - Signals that an I/O exception of some sort has occurred. 
	 * @throws ClassNotFoundException - Thrown when a class loader is unable to find a class. 
	 */
	public void leFicheiro(String fileName) throws IOException, ClassNotFoundException {
		String endName = fileName.substring(fileName.length() - 4);
		switch (endName) {
			case ".obj":
				this.leFicheiroObj(fileName);
				break;
			case ".txt":
				this.leFicheiroTxt(fileName);
				break;

			default:
				throw new IOException("File error...");

		}
	}
	// Getters

	/**
	 * Método que devolve um map que representa a informação de todos os anos da RedeGlobalAutores.
	 * @return- Map de RedeAnualAutores, de chave Integer com o seu ano.
	 */
	public Map<Integer, RedeAnualAutores> getRedeGlobalAutores() {
		TreeMap<Integer, RedeAnualAutores> aux = new TreeMap<>();
		for (RedeAnualAutores redeAnual : this.redeGlobalAutores.values()) {
			aux.put(redeAnual.getAno(), redeAnual.clone());
		}
		return aux;
	}

	/**
	 * Método que devolve o nome do ficheiro que foi usado para preencher a estrutura.
	 * @return - String com o nome do ficheiro.
	 */
	public String getFicheiroLido() {
		return this.ficheiroLido;
	}

	/**
	 * Método que devolve o número de artigos lidos.
	 * @return - Int, com o número de artigos lidos.
	 */
	public int getArtigosLidos() {
		return this.artigosLidos;
	}

	/**
	 * Método que devolve o número de nomes lidos.
	 * @return - Int, com o número de nomes lidos.
	 */
	public int getNomesLidos() {
		return this.nomesLidos;
	}

	/**
	 * Método que devolve o número de nomes distintos lidos.
	 * @return - Int, com o número de nomes distintos lidos.
	 */
	public int getNomesDistintos() {
		return this.nomesDistintos;
	}

	/**
	 * Método que devolve o menor ano com publicações.
	 * @return - Int, com o menor ano com publicações.
	 */
	public int getMenorAno() {
		return this.menorAno;
	}

	/**
	 * Método que devolve o maior ano com publicações.
	 * @return - Int, com o maior ano com publicações.
	 */
	public int getMaiorAno() {
		return this.maiorAno;
	}

	/**
	 * Método que devolve o número de publicações a solo.
	 * @return - Int, com o número de publicações a solo.
	 */
	public int getNumPubsUmAutor() {
		return this.numPubsUmAutor;
	}

	 /**
	 * Método que preenche uma RedeGlobalAutores com informação de um ficheiro com uma estrutura RedeGlobalAutores já preenchida.
	 * @param fileName - String, nome do ficheiro.
	 * @throws IOException - Signals that an I/O exception of some sort has occurred. 
	 * @throws ClassNotFoundException - Thrown when a class loader is unable to find a class. 
	 * @throws ClassCastException - Thrown to indicate that the code has attempted to cast an object to a subclass of which it is not an instance.
	 */
	public void leFicheiroObj(String fileName) throws IOException, ClassNotFoundException, ClassCastException {
		ObjectInputStream objInput = new ObjectInputStream(new FileInputStream(fileName));
		RedeGlobalAutores redeGlobal = new RedeGlobalAutores((RedeGlobalAutores) objInput.readObject());
		this.redeGlobalAutores = (TreeMap<Integer, RedeAnualAutores>) redeGlobal.getRedeGlobalAutores();
		this.ficheiroLido = redeGlobal.getFicheiroLido();
		this.artigosLidos = redeGlobal.getArtigosLidos();
		this.maiorAno = redeGlobal.getMaiorAno();
		this.menorAno = redeGlobal.getMenorAno();
		this.nomesDistintos = redeGlobal.getNomesDistintos();
		this.nomesLidos = redeGlobal.getNomesLidos();
		this.numPubsUmAutor = redeGlobal.getNumPubsUmAutor();
	}

	 /**
	 * Método que preenche uma RedeGlobalAutores com informação de um ficheiro txt.
	 * @param fileName - String, nome do ficheiro.
	 * @throws FileNotFoundException - Signals that an attempt to open the file denoted by a specified pathname has failed. 
	 */
	public void leFicheiroTxt(String fileName) throws FileNotFoundException, IOException {
		String linha;
		ArrayList<String> linhaTok = new ArrayList<>();
		int ano;
		//Scanner fichScan;
		StringTokenizer sTok;
		HashSet<String> autDist = new HashSet<>();
		this.ficheiroLido = fileName;

		BufferedReader fichScan = new BufferedReader(new FileReader(fileName));
		//fichScan = new Scanner(new FileReader(fileName));
		//fichScan.useDelimiter(System.getProperty("line.separator"));

		while ((linha = fichScan.readLine()) != null) {
			//linha = fichScan.next();
			sTok = new StringTokenizer(linha, ",");

			while (sTok.hasMoreTokens()) {
				linhaTok.add(sTok.nextToken().trim());
			}

			if (linhaTok.size() > 1) {
				this.artigosLidos++;
			}

			if (linhaTok.size() == 2) {
				this.numPubsUmAutor++;
			}

			ano = Integer.valueOf(linhaTok.get(linhaTok.size() - 1));
			if (ano < this.menorAno) {
				this.menorAno = ano;
			}

			if (ano > this.maiorAno) {
				this.maiorAno = ano;
			}

			linhaTok.remove(linhaTok.size() - 1);

			autDist.addAll(linhaTok);
			this.nomesLidos += linhaTok.size();
			this.adicionaPublicacao(ano, linhaTok);

			linhaTok.clear();

		}
		this.nomesDistintos = autDist.size();
	}

	/**
	 * Método que guarda o objecto RedeGlobalAutores num ficheiro.
	 * @param fileName - Nome do ficheiro a guardar.
	 * @throws SecurityException - Thrown by the security manager to indicate a security violation.
	 * @throws IOException - Signals that an I/O exception of some sort has occurred. 
	 */

	public void guardaDados(String fileName) throws SecurityException, IOException {

		FileOutputStream out = new FileOutputStream(fileName + ".obj");
		ObjectOutputStream oout = new ObjectOutputStream(out);

		oout.writeObject(this);
		oout.flush();

	}

	/**
	 * Método que dado um ano e uma lista de nomes adiciona-os à RedeGlobalAutores.
	 * @param ano - Int, ano da publicação.
	 * @param autores - Lista de nomes de Autores.
	 */
	public void adicionaPublicacao(int ano, List<String> autores) {
		if (this.redeGlobalAutores.containsKey(ano)) {
			this.redeGlobalAutores.get(ano).insereAutores(autores);
		} else {
			RedeAnualAutores novoAno = new RedeAnualAutores(ano, autores);
			this.redeGlobalAutores.put(ano, novoAno);
		}
	}

	/**
	 * Método que devolve o número de publicações de um ano.
	 * @param ano - ano a pesquisar.
	 * @return - Número de publicações desse ano.
	 * @throws RedeAnualNotFoundException - É lançada caso o ano pretendido não exista na RedeGlobal.
	 */
	public RedeAnualAutores getPubsAno(int ano) throws RedeAnualNotFoundException {
		if (!this.redeGlobalAutores.containsKey(ano)) {
			throw new RedeAnualNotFoundException("Ano não existente. Ano: " + ano);
		}

		return this.redeGlobalAutores.get(ano).clone();
	}

	/**
	 * Método que devolve uma String com algumas estatisticas.
	 * @return - String com informação de menor e maior anos; número de publicações, autores lidos e únicos.
	 */
	public String statsString() {
		StringBuilder s = new StringBuilder(".......... ESTATÍSTICAS ..........\n\n");
		s.append("Rede de Autores: ").append(this.getFicheiroLido());
		s.append("\nAnos: [").append(this.getMenorAno()).append(", ").append(this.getMaiorAno()).append("]");
		s.append("\nPublicações: ").append(this.getArtigosLidos());
		s.append("\nAutores lidos: ").append(this.getNomesLidos());
		s.append("\nAutores distintos: ").append(this.getNomesDistintos()).append("\n\n");
		//for (RedeAnualAutores ano : this.redeGlobalAutores.values()) {
		//	s.append("\n\t").append(ano.toString()).append("\n");
		//}
		return s.toString();
	}

	/**
	 * Método que devolve uma String com algumas estatisticas referentes aos autores, e número total de autores que publicaram
	 * mais de X artigos no intervalo.
	 * @param x - Número de artigos.
	 * @return - String com informação de número de autores, artigos com apenas um autor, número de autores que apenas e nunca
	 * publicaram a solo.
	 */
	public String consulta12(int x) {
		StringBuilder sb = new StringBuilder();
		sb.append("Nº de autores: ").append(this.getNomesDistintos()).append("\n");
		sb.append("Nº de artigos com apenas um autor: ").append(this.getNumPubsUmAutor()).append("\n");
		sb.append("Nº de autores que apenas publicaram a solo: ").append(this.getNumAutoresSolo()).append("\n");
		sb.append("Nº de autores que nunca publicaram a solo: ").append(this.getNumAutoresNoSolo()).append("\n");
		sb.append("Nº de autores com mais que ").append(x).append(" publicações: ").append(this.getNumAutoresMaisQueXArtigos(x)).append("\n");

		return sb.toString();
	}

	/**
	 * Método que dado um nome de um Autor e um intervalo de anos, conta o seu número de publicações nese intervalo.
	 * @param nome - Nome do Autor a procurar, String.
	 * @param anoInicial - Ano inicial do intervalo a procurar.
	 * @param anoFinal - Ano final do intervalo a procurar.
	 * @return - Número de publicações.
	 */
	public int getNumPubsAutorAnos(String nome, int anoInicial, int anoFinal) {
		int count = 0;
		RedeAnualAutores redeAux;
		for (int i = anoInicial; i <= anoFinal; i++) {
			if (this.redeGlobalAutores.containsKey(i)) {
				redeAux = this.redeGlobalAutores.get(i).clone();
				count += redeAux.numPubsAutor(nome);
			}
		}
		return count;
	}

	/**
	 * Método que determina o número de autores que só têm publicações a solo.
	 * @return - Número de autores apenas com publicações a solo.
	 */
	public int getNumAutoresSolo() {
		HashSet<String> verificados = new HashSet<>();
		HashSet<String> solo = new HashSet<>();

		for (RedeAnualAutores redeAnual : this.redeGlobalAutores.values()) {
			for (Autor aut : redeAnual.getRedeAnualAutores().values()) {
				if (aut.getNumeroPubsCoAutores() == 0) {
					if (!verificados.contains(aut.getNome()) || solo.contains(aut.getNome())) {
						solo.add(aut.getNome());
					}
				} else {
					solo.remove(aut.getNome());
				}
				verificados.add(aut.getNome());
			}
		}
		return solo.size();
	}

	/**
	 * Método que determina o número de autores que não têm publicações a solo.
	 * @return - Número de autores sem publicações a solo.
	 */	public int getNumAutoresNoSolo() {
		HashSet<String> verificados = new HashSet<>();
		HashSet<String> noSolo = new HashSet<>();

		for (RedeAnualAutores redeAnual : this.redeGlobalAutores.values()) {
			for (Autor aut : redeAnual.getRedeAnualAutores().values()) {
				if (aut.getNumeroPubsSolo() == 0 && aut.getNumeroPubsCoAutores() != 0 && !verificados.contains(aut.getNome())) {
					noSolo.add(aut.getNome());
				} else {
					noSolo.remove(aut.getNome());
				}

				verificados.add(aut.getNome());
			}
		}
		return noSolo.size();
	}

	/**
	 * Método que determina quantos Autores têm mais de X publicações.
	 * @param x - Número de artigos.
	 * @return - Número de autores com mais de X artigos.
	 */
	public int getNumAutoresMaisQueXArtigos(int x) {
		HashMap<String, Autor> autores = new HashMap<>();
		for (int i = this.menorAno; i <= this.maiorAno; i++) {
			if (this.redeGlobalAutores.containsKey(i)) {
				for (Autor a : this.redeGlobalAutores.get(i).getRedeAnualAutores().values()) {
					if (!autores.containsKey(a.getNome())) {
						autores.put(a.getNome(), a);
					} else {
						autores.get(a.getNome()).incrementaPubs(a.getNumeroPubsSolo(), a.getNumeroPubsCoAutores());
					}
				}
			}
		}
		HashMap<String, Autor> autoresRes = new HashMap<>();
		for (Autor a : autores.values()) {
			if (a.getNumeroPubsCoAutores() + a.getNumeroPubsSolo() > x) {
				autoresRes.put(a.getNome(), a);
			}
		}
		return autoresRes.size();
	}

	/**
	 * Método que para um certo intervalo de anos dado, devolve os nomes dos X autores que mais artigos publicaram.
	 * @param anoInicial - Ano inicial do intervalo a procurar.
	 * @param anoFinal - Ano final do intervalo a procurar.
	 * @param topX - Número de autores.
	 * @return - Lista de nomes de autores.
	 * @throws AnosForaDoIntervaloException - O intervalo de anos introduzido está fora do intervalo de anos no ficheiro.
	 * @throws AnoInicialMaiorQueAnoFinalException - Ano inicial introduzido maior que ano final introduzido.
	 * @throws AnoInvalidoException - É lançada se for introduzido um ano negativo.
	 */
	public Set<String> consulta21a(int anoInicial, int anoFinal, int topX) throws AnosForaDoIntervaloException, AnoInicialMaiorQueAnoFinalException, AnoInvalidoException {
		//TreeSet<Autor> maxPubsAutores = new TreeSet<>(new CompareAutorPubs());
		HashMap<String, Autor> maxPubsAutores = new HashMap<>();
		Iterator<Autor> it;
		
		if (anoInicial < 0 || anoFinal < 0) {
			throw new AnoInvalidoException("Ano inválido. [" + anoInicial + ", " + anoFinal + "]");
		}
		if (anoInicial > anoFinal) {
			throw new AnoInicialMaiorQueAnoFinalException("Ano inicial maior que ano final. [" + anoInicial + ", " + anoFinal + "]");
		}
		if ((anoInicial < this.menorAno && anoFinal < this.menorAno) || (anoInicial > this.maiorAno)) {
			throw new AnosForaDoIntervaloException("Anos fora do intervalo do ficheiro. [" + anoInicial + ", " + anoFinal + "]");
		}

		for (int i = anoInicial; i <= anoFinal; i++) {
			if (this.redeGlobalAutores.containsKey(i)) {
				for (Autor aut : this.redeGlobalAutores.get(i).getRedeAnualAutores().values()) {
					if (maxPubsAutores.containsKey(aut.getNome())) {
						maxPubsAutores.get(aut.getNome()).incrementaPubs(aut.getNumeroPubsSolo(), aut.getNumeroPubsCoAutores());
					} else {
						maxPubsAutores.put(aut.getNome(), aut.clone());
					}
				}
			}
		}
		TreeSet<Autor> novoMaxPubsAutores = new TreeSet<>(new CompareAutorPubs());
		it = maxPubsAutores.values().iterator();
		while (it.hasNext()) {
			novoMaxPubsAutores.add(it.next());
		}
		return this.consulta21aAux(novoMaxPubsAutores, topX);
	}

	private Set<String> consulta21aAux(TreeSet<Autor> auts, int topX) {
		TreeSet<String> res = new TreeSet<>();
		if (auts.size() < topX) {
			for (int i = 0; i < auts.size(); i++) {
				res.add(auts.pollFirst().getNome());
			}
		} else {
			for (int i = 0; i < topX; i++) {
				res.add(auts.pollFirst().getNome());
			}
		}

		return res;
	}

	/**
	 * Método que para um certo intervalo de anos dado, devolve os nomes dos X pares de autores com mais artigos publicados em co-autoria.
	 * @param topX - Número de pares
	 * @param anoInicial - Ano inicial do intervalo a procurar.
	 * @param anoFinal - Ano final do intervalo a procurar.
	 * @return - Lista de nomes de pares de autores.
	 * @throws AnosForaDoIntervaloException - O intervalo de anos introduzido está fora do intervalo de anos no ficheiro.
	 * @throws AnoInicialMaiorQueAnoFinalException - Ano inicial introduzido maior que ano final introduzido.
	 * @throws AnoInvalidoException - É lançada se for introduzido um ano negativo.
	 */
	public List<String> consulta21b(int topX, int anoInicial, int anoFinal) throws AnosForaDoIntervaloException, AnoInicialMaiorQueAnoFinalException, AnoInvalidoException {
		HashMap<String, Integer> paresAutores = new HashMap<>();
		Iterator<ParCoAutores> it;
		
		if (anoInicial < 0 || anoFinal < 0) {
			throw new AnoInvalidoException("Ano inválido. [" + anoInicial + ", " + anoFinal + "]");
		}
		if (anoInicial > anoFinal) {
			throw new AnoInicialMaiorQueAnoFinalException("Ano inicial maior que ano final. [" + anoInicial + ", " + anoFinal + "]");
		}
		if ((anoInicial < this.menorAno && anoFinal < this.menorAno) || (anoInicial > this.maiorAno)) {
			throw new AnosForaDoIntervaloException("Anos fora do intervalo do ficheiro. [" + anoInicial + ", " + anoFinal + "]");
		}

		for (int i = anoInicial; i <= anoFinal; i++) {
			if (this.redeGlobalAutores.containsKey(i)) {
				for (Autor aut : this.redeGlobalAutores.get(i).getRedeAnualAutores().values()) {
					for (CoAutor coAut : aut.getCoAutores().values()) {
						String par = aut.getNome().concat(",").concat(coAut.getNome());
						Crono.start();
						if (paresAutores.containsKey(par)) {
							int pubs = paresAutores.get(par) + coAut.getNumeroPubsComAutor();
							paresAutores.put(par, pubs);
						} else {
							paresAutores.put(par, coAut.getNumeroPubsComAutor());
						}
					}
				}
			}
		}
		HashMap<String, Integer> paresAux = new HashMap<>(paresAutores);
		String[] pares;

		for (String s : paresAutores.keySet()) {
			pares = s.split(",");
			String invert = pares[1].concat(",").concat(pares[0]);
			if (paresAux.containsKey(s) && paresAux.containsKey(invert)) {
				paresAux.remove(invert);
			}
		}

		TreeSet<ParCoAutores> novoPares = new TreeSet<>(new CompareParCoAutores());
		for (String s : paresAux.keySet()) {
			pares = s.split(",");
			novoPares.add(new ParCoAutores(pares[0], pares[1], paresAux.get(s)));
		}
		return consulta21bAux(novoPares, topX);
	}

	private List<String> consulta21bAux(Set<ParCoAutores> p, int topX) {
		ArrayList<String> res = new ArrayList<>();
		TreeSet<ParCoAutores> pares = (TreeSet<ParCoAutores>) p;
		if (pares.size() >= topX) {
			for (int i = 0; i < topX; i++) {
				res.add(pares.pollFirst().toString());
			}
		} else {
			for (int i = 0; i < pares.size(); i++) {
				res.add(pares.pollFirst().toString());
			}
		}

		return res;
	}

	/**
	 * Método que dado uma lista de autores (de tamanho máximo 3), devolve devolve os co-autores comuns a esses autores.
	 * @param anoInicial - Ano inicial do intervalo a procurar.
	 * @param anoFinal - Ano final do intervalo a procurar.
	 * @param listaAutores -  Lista de nomes de Autores, (tamanho máximo 3).
	 * @return - Lista de nomes dos co-autores (por ordem alfabética).
	 * @throws AnosForaDoIntervaloException - O intervalo de anos introduzido está fora do intervalo de anos no ficheiro.
	 * @throws AnoInicialMaiorQueAnoFinalException - Ano inicial introduzido maior que ano final introduzido.
	 * @throws AnoInvalidoException - É lançada se for introduzido um ano negativo.
	 */
	public Set<String> consulta21c(int anoInicial, int anoFinal, ArrayList<String> listaAutores) throws AnosForaDoIntervaloException, AnoInicialMaiorQueAnoFinalException, AnoInvalidoException {
		ArrayList<String> autor1 = new ArrayList<>();
		ArrayList<String> autor2 = new ArrayList<>();
		TreeSet<String> autores = new TreeSet<>();

		if (anoInicial < 0 || anoFinal < 0) {
			throw new AnoInvalidoException("Ano inválido. [" + anoInicial + ", " + anoFinal + "]");
		}
		if (anoInicial > anoFinal) {
			throw new AnoInicialMaiorQueAnoFinalException("Ano inicial maior que ano final. [" + anoInicial + ", " + anoFinal + "]");
		}
		if ((anoInicial < this.menorAno && anoFinal < this.menorAno) || (anoInicial > this.maiorAno)) {
			throw new AnosForaDoIntervaloException("Anos fora do intervalo do ficheiro. [" + anoInicial + ", " + anoFinal + "]");
		}
		Autor aut;

		for (int i = anoInicial; i <= anoFinal; i++) {
			if (this.redeGlobalAutores.containsKey(i)) {
				if (this.redeGlobalAutores.get(i).getRedeAnualAutores().containsKey(listaAutores.get(0))) {
					aut = this.redeGlobalAutores.get(i).getRedeAnualAutores().get(listaAutores.get(0));
					for (CoAutor coaut : aut.getCoAutores().values()) {
						autor1.add(coaut.getNome());
					}
				}
				if (this.redeGlobalAutores.get(i).getRedeAnualAutores().containsKey(listaAutores.get(1))) {
					aut = this.redeGlobalAutores.get(i).getRedeAnualAutores().get(listaAutores.get(1));
					for (CoAutor coaut : aut.getCoAutores().values()) {
						autor2.add(coaut.getNome());
					}
				}
			}
		}

		for (String s : autor1) {
			if (autor2.contains(s)) {
				autores.add(s);
			}
		}

		if (listaAutores.size() == 3) {
			autor2.clear();
			for (int i = anoInicial; i <= anoFinal; i++) {
				if (this.redeGlobalAutores.get(i).getRedeAnualAutores().containsKey(listaAutores.get(2))) {
					aut = this.redeGlobalAutores.get(i).getRedeAnualAutores().get(listaAutores.get(2));
					for (CoAutor coaut : aut.getCoAutores().values()) {
						autor2.add(coaut.getNome());
					}
				}
			}
		}

		for (String s : autor2) {
			if (!autores.contains(s)) {
				autores.remove(s);
			}
		}
		return autores;
	}

	/**
	 * Método que devolve os autores que publicaram em todos os anos de um intervalo dado.
	 * @param anoInicial - Ano inicial do intervalo a procurar.
	 * @param anoFinal - Ano final do intervalo a procurar.
	 * @return - Lista de nomes de autores (por ordem alfabética)
	 * @throws AnosForaDoIntervaloException - O intervalo de anos introduzido está fora do intervalo de anos no ficheiro.
	 * @throws AnoInicialMaiorQueAnoFinalException - Ano inicial introduzido maior que ano final introduzido.
	 * @throws AnoInvalidoException - É lançada se for introduzido um ano negativo.
	 */
	public Set<String> consulta21d(int anoInicial, int anoFinal) throws AnosForaDoIntervaloException, AnoInicialMaiorQueAnoFinalException, AnoInvalidoException {
		TreeSet<String> autores = new TreeSet<>();
		HashMap<String, Autor> autsHash;
		boolean add = true;

		if (anoInicial < 0 || anoFinal < 0) {
			throw new AnoInvalidoException("Ano inválido. [" + anoInicial + ", " + anoFinal + "]");
		}
		if (anoInicial > anoFinal) {
			throw new AnoInicialMaiorQueAnoFinalException("Ano inicial maior que ano final. [" + anoInicial + ", " + anoFinal + "]");
		}
		if ((anoInicial < this.menorAno && anoFinal < this.menorAno) || (anoInicial > this.maiorAno)) {
			throw new AnosForaDoIntervaloException("Anos fora do intervalo do ficheiro. [" + anoInicial + ", " + anoFinal + "]");
		}

		int menosAutores = 164250;
		int anoMenosAutores = 0;

		for (int i = anoInicial; i <= anoFinal; i++) {
			if (this.redeGlobalAutores.containsKey(i)) {
				int numAutores = this.redeGlobalAutores.get(i).getRedeAnualAutores().values().size();
				if (numAutores <= menosAutores) {
					menosAutores = numAutores;
					anoMenosAutores = i;
				}
			}
		}
		//System.out.println("Ano: " + anoMenosAutores + "\nAutores: " + menosAutores + "\n\n");

		TreeSet<String> aux = new TreeSet<String>(this.redeGlobalAutores.get(anoMenosAutores).getRedeAnualAutores().keySet());

		//Crono.start();
		for (int i = anoInicial; i <= anoFinal; i++) {
			Crono.start();
			TreeSet<String> aux2 = new TreeSet<>(aux);
			if ((this.redeGlobalAutores.containsKey(i)) && (i != anoMenosAutores)) {
				TreeSet<String> auts = new TreeSet<>(this.redeGlobalAutores.get(i).getRedeAnualAutores().keySet());
				for (String aut : aux2) {
					if (!(auts.contains(aut))) {
						aux.remove(aut);
					}
				}
			}
		}
		return aux;
	}

	/**
	 * Método que conta o número de linhas em duplicado de um ficheiro .txt.
	 * @return - Número de linhas em duplicado.
	 */
	public int getLinhasDuplicado() {
		HashSet<String> linhas = new HashSet<>();
		int numLinhas = 0;
		Scanner fichScan;

		try {
			fichScan = new Scanner(new FileReader(this.ficheiroLido));
			fichScan.useDelimiter(System.getProperty("line.separator"));
			while (fichScan.hasNext()) {
				linhas.add(fichScan.next());
				numLinhas++;
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

		return numLinhas - linhas.size();
	}

	/**
	 * Método que dado uma letra, lista todos os autores com nomes começados por essa letra.
	 * @param c - Char, letra a procurar.
	 * @return - Lista de Autores (por ordem alfabética).
	 */
	public Set<String> consulta22b(char c) {
		TreeSet<String> autoresLetra = new TreeSet<>();
		for (RedeAnualAutores redeAnual : this.redeGlobalAutores.values()) {
			for (String nome : redeAnual.getRedeAnualAutores().keySet()) {
				if (nome.startsWith("" + c)) {
					autoresLetra.add(nome);
				}
			}
		}

		return autoresLetra;
	}

	/**
	 * Método que para um dado autor e um dado ano, devolve a lista de todos os coautores desse autor nesse ano e o seu número total de publicações.
	 * @param autor - String com o nome do Autor a procurar.
	 * @param ano - Int, ano a procurrar.
	 * @return - Lista de Strings em que na primeira posição está o nº total de publicações do autor e nas restantes o nome dos co-autores.
	 * @throws AutorNotFoundException - É lançada se o autor que o utilizador introduz não existir no ficheiro.
	 * @throws AnoInvalidoException - É lançada se for introduzido um ano negativo.
	 */
	public List<String> consulta22c(String autor, int ano) throws AutorNotFoundException, AnoInvalidoException {
		RedeAnualAutores redeAnual;
		Autor aut;
		ArrayList<String> aux = new ArrayList<>();
		if (this.redeGlobalAutores.containsKey(ano)) {
			redeAnual = this.redeGlobalAutores.get(ano).clone();
			if (redeAnual.getRedeAnualAutores().containsKey(autor)) {
				aut = redeAnual.getRedeAnualAutores().get(autor).clone();
				aux.add(String.valueOf(aut.getNumeroPubsCoAutores()));
				for (CoAutor coAut : aut.getCoAutores().values()) {
					aux.add(coAut.getNome());
				}
				
				return aux;
			} else {
				throw new AutorNotFoundException("Autor não existente...");
			}
		} else {
			throw new AnoInvalidoException("Ano não existente no ficheiro...");
		}
	}

	/**
	 * Método que dado um nome de um Autor, devolve uma lista com os nomes de todos os seus co-autores.
	 * @param autor - String, nome do Autor a procurar.
	 * @return - Lista com os nomes de co-autores.
	 * @throws AutorNotFoundException - É lançada se o autor que o utilizador introduz não existir no ficheiro.
	 */
	public Set<String> consulta22d(String autor) throws AutorNotFoundException {
		TreeSet<String> coAutores = new TreeSet<>();
		boolean existeAutor = false;
		for (RedeAnualAutores redeAnual : this.getRedeGlobalAutores().values()) {
			if (redeAnual.getRedeAnualAutores().containsKey(autor)) {
				existeAutor = true;
				for (CoAutor coAut : redeAnual.getRedeAnualAutores().get(autor).getCoAutores().values()) {
					coAutores.add(coAut.getNome());
				}
			}
		}
		if (!existeAutor) {
			throw new AutorNotFoundException("Autor não existente...");
		}

		return coAutores;
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(new Object[] { this.artigosLidos, this.ficheiroLido, this.maiorAno, this.menorAno, this.nomesDistintos, this.nomesLidos, this.numPubsUmAutor, this.redeGlobalAutores });
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}		
		
		RedeGlobalAutores r = (RedeGlobalAutores) obj;
		
		return (this.artigosLidos == r.getArtigosLidos() &&
				this.ficheiroLido.equals(r.getFicheiroLido()) &&
				this.maiorAno == r.getMaiorAno() &&
				this.menorAno == r.getMenorAno() &&
				this.nomesDistintos == r.getNomesDistintos() &&
				this.nomesLidos == r.getNomesLidos() &&
				this.numPubsUmAutor == r.getNumPubsUmAutor() &&
				this.redeGlobalAutores.equals(r.getRedeGlobalAutores()));
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("Rede de Autores: " + this.getFicheiroLido());
		s.append("\nAnos: [").append(this.getMenorAno()).append(", ").append(this.getMaiorAno()).append("]");
		s.append("\nPublicações: ").append(this.getArtigosLidos());
		s.append("\nAutores lidos: ").append(this.getNomesLidos());
		s.append("\nAutores distintos: ").append(this.getNomesDistintos());
		for (RedeAnualAutores ano : this.redeGlobalAutores.values()) {
			s.append("\n\t").append(ano.toString()).append("\n");
		}
		return s.toString();
	}

}
