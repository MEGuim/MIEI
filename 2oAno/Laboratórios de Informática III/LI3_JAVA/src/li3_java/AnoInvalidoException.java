package li3_java;

/**
 *
 * 
 *
 * @author Bruno Pereira
 * @author João Mano
 * @author Miguel Guimarães
 * @version 2014
 */
public class AnoInvalidoException extends Exception {

	/**
	 * Creates a new instance of <code>AnoInvalidoException</code> without detail message.
	 */
	public AnoInvalidoException() {
	}

	/**
	 * Constructs an instance of <code>AnoInvalidoException</code> with the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public AnoInvalidoException(String msg) {
		super(msg);
	}
}
