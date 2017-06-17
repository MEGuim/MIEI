
package li3_java;

import java.io.Serializable;
import java.util.Arrays;


/**
 *
 * CoAutor:  Classe que guarda informação sobre um Co-Autor.
 *
 * @author Bruno Pereira
 * @author João Mano
 * @author Miguel Guimarães
 * @version 2014
 */
public class CoAutor implements Serializable
{
    private String nome;
    private int numeroPubsComAutor;

	/**
	 * Método construtor vazio.
	 */
	public CoAutor(){
    	this.nome = "";
    	this.numeroPubsComAutor = 0;
    }

	/**
	 * Método construtor parametrizado.
	 * @param nome - String, representa o nome do Co-Autor.
	 */
	public CoAutor(String nome){
    	this.nome = nome;
    	this.numeroPubsComAutor = 1;
    }

	/**
	 *Método construtor de cópia.
	 * @param coAut -  Objecto de tipo CoAutor.
	 */
	public CoAutor(CoAutor coAut){
    	this.nome = coAut.getNome();
    	this.numeroPubsComAutor = coAut.getNumeroPubsComAutor();
    }
    
	/**
	 * Método que devolve o nome do Co-Autor.
	 * @return - String com o nome do Co-Autor.
	 */
	public String getNome(){
        return this.nome;
    }
    
	/**
	 * Método que devolve o número de publicações em conjunto com o Autor.
	 * @return - Int , representa o número de publicações.
	 */
	public int getNumeroPubsComAutor(){
        return this.numeroPubsComAutor;
    }

	/**
	 *  Método que incrementa o número de publicações num valor.
	 */
	public void adicionaPub(){
    	this.numeroPubsComAutor++;
    }
	
	/**
	 * Método que adiciona N publicações.
	 * @param n - valor a aumentar, int.
	 */
	public void adicionaPub(int n){
    	this.numeroPubsComAutor += n;
    }
	
	@Override
	public int hashCode (){
		return Arrays.hashCode(new Object[] { this.nome, this.numeroPubsComAutor });
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null || this.getClass() != obj.getClass())
			return false;
					
		CoAutor co = (CoAutor) obj;
		return (co.getNome().equals(this.nome) && co.getNumeroPubsComAutor() == this.numeroPubsComAutor);
	}

	@Override
    public CoAutor clone(){
    	return new CoAutor(this);
    }
    
                     
	@Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\tNome do Co-Autor: ").append(this.getNome()).append("\n" + "\tNúmero de publicações em comum: ").append(this.getNumeroPubsComAutor()).append("\n");
        return s.toString();
    }   

}
