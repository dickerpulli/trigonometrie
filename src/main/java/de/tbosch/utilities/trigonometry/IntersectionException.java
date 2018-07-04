package de.tbosch.utilities.trigonometry;

/**
 * Represents the error that a point or a circle is inside the circle to compare
 * with.
 *
 * @author Thomas Bosch (tbosch@gmx.de)
 */
public class IntersectionException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	/**
	 * Contructs a new exception.
	 *
	 * @param msg
	 *            The message
	 */
	public IntersectionException(String msg) {
		super(msg);
	}

}
