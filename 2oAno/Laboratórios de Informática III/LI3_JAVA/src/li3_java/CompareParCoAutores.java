package li3_java;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Classe comparador.
 * Compara ParCoAutores por ordem decrescente de número de publicações, caso de igualdade, ordena por ordem alfabética de nomes dos 1º Autores.
 * @author Bruno Pereira
 * @author João Mano
 * @author Miguel Guimarães
 * @version 2014
 */
public class CompareParCoAutores implements Comparator<ParCoAutores>, Serializable
{

	@Override
	public int compare(ParCoAutores a1, ParCoAutores a2){		
		if((a1.getNumPubs() > (a2.getNumPubs())))
			return -1;
		
		if((a2.getNumPubs() > (a1.getNumPubs())))
			return 1;
		
		return a1.getNome1().compareTo(a2.getNome1());
		//return 0;
	}
}