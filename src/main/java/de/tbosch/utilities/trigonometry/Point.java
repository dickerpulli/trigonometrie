package de.tbosch.utilities.trigonometry;

/**
 * Representation of a point in a coordinate system.
 *
 * @author Thomas Bosch
 */
public class Point {

	private final long x;

	private final long y;

	/**
	 * Constructs the point.
	 *
	 * @param x
	 *            The x coordinate.
	 * @param y
	 *            The y coordinate.
	 */
	public Point(long x, long y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return The x coordinate.
	 */
	public long getX() {
		return x;
	}

	/**
	 * @return The y coordinate.
	 */
	public long getY() {
		return y;
	}

	/**
	 * Calculate the distance between this and the given point.
	 *
	 * @param p
	 *            The other point.
	 * @return The distance.
	 */
	public double distance(Point p) {
		double px = p.getX() - this.getX();
		double py = p.getY() - this.getY();
		return Math.sqrt(px * px + py * py);
	}

	@Override
	public String toString() {
		return "[x = " + x + ", y = " + y + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (x ^ (x >>> 32));
		result = prime * result + (int) (y ^ (y >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
