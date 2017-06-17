package li3_java;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Classe comparador.
 * Compara Autorespor ordem decrescente de número de publicações, caso de igualdade, ordena por ordem alfabética de nomes.
 * @author Bruno Pereira
 * @author João Mano
 * @author Miguel Guimarães
 * @version 2014
 */
public class CompareAutorPubs implements Comparator<Autor>, Serializable
{
   
   @Override
   public int compare(Autor a1, Autor a2){
	if((a2.getNumeroPubsSolo()+a2.getNumeroPubsCoAutores()) > (a1.getNumeroPubsSolo() + a1.getNumeroPubsCoAutores()))
		return 1;
	if((a1.getNumeroPubsSolo()+a1.getNumeroPubsCoAutores()) > (a2.getNumeroPubsSolo() + a2.getNumeroPubsCoAutores()))
		return -1;
	
	return a1.getNome().compareTo(a2.getNome());
	//return (a2.getNumeroPubsCoAutores() + a2.getNumeroPubsSolo()) - (a1.getNumeroPubsCoAutores() + a1.getNumeroPubsSolo());
   }
}
   
  
