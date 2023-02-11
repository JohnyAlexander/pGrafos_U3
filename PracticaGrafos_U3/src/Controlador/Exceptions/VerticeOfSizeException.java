package Controlador.Exceptions;

/**
 *
 * @author hilar_c9usj1g
 */
public class VerticeOfSizeException extends Exception{

    /**
     * Creates a new instance of <code>VerticeException</code> without detail
     * message.
     */
    public VerticeOfSizeException() {
    }

    /**
     * Constructs an instance of <code>VerticeException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public VerticeOfSizeException(String msg) {
        super(msg);
    }
}
