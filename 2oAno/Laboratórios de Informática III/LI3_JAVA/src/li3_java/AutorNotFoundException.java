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
public class AutorNotFoundException extends Exception {

	/**
	 * Creates a new instance of <code>AutorNotFoundException</code> without detail message.
	 */
	public AutorNotFoundException() {
	}

	/**
	 * Constructs an instance of <code>AutorNotFoundException</code> with the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public AutorNotFoundException(String msg) {
		super(msg);
	}
}
