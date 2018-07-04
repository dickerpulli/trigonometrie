package de.tbosch.utilities.trigonometry;

/**
 * Circle Class for functions on circles.
 *
 * @author Thomas Bosch (tbosch@gmx.de)
 */
public class Circles {

	/**
	 * No constructor. Only static mathods.
	 */
	private Circles() {
		// utility class
	}

	/**
	 * Gets the vertical distance (y-axis) between two circles (their middle
	 * points).
	 *
	 * @param r1
	 *            the radius of the first circle
	 * @param r2
	 *            the radius of the second circle
	 * @param x
	 *            the x-distance between both middlepoints
	 * @param angleDeg
	 *            the angle between the x-axis and a tangent line between two
	 *            circles (in degrees)
	 * @param outerTangent
	 *            if the outer tangent is meant
	 * @return The y-distance (negative means: the middle point of the second circle
	 *         has a bigger x-value).
	 */
	public static int getVerticalDistance(float r1, float r2, int x, double angleDeg, boolean outerTangent) {
		long y_longMm;
		double betaRad = Math.toRadians(180 - angleDeg);

		double z = r1 / Math.cos(betaRad);
		double v = x - z;

		if (outerTangent) {
			double t = v / Math.tan(betaRad);
			double s = r2 / Math.cos(Math.toRadians(90) - betaRad);
			y_longMm = Math.round(s + t);
		} else {
			double n = v * Math.cos(betaRad);
			double q = n / Math.sin(betaRad);
			double m = (q * r2) / n;
			y_longMm = Math.round(q - m);
		}

		int y;
		if (y_longMm > Integer.MAX_VALUE) {
			throw new IllegalStateException("maximum value (" + Integer.MAX_VALUE + ") reached : " + y_longMm);
		} else {
			y = (int) y_longMm;
		}

		return y;
	}

	/**
	 * Gets the angle between the x-axis and a tangent line between two circles.
	 *
	 * @param dx
	 *            the x-distance of the middlepoints
	 * @param dy
	 *            the y-distance of the middlepoints
	 * @param r1
	 *            the radius of the first circle
	 * @param r2
	 *            the radius of the second circle
	 * @return The angle in radians.
	 */
	public static double getAngleBetweenCircles(float r1, float r2, int dx, int dy) {
		double sum1 = (Math.pow(dy, 2) - Math.pow((r1 - r2), 2)) / (Math.pow(dx, 2) + Math.pow(dy, 2));
		double sum2 = ((r1 - r2) * dx) / (Math.pow(dx, 2) + Math.pow(dy, 2));
		double cos = Math.sqrt(sum1 + Math.pow(sum2, 2)) + sum2;
		return Math.acos(cos);
	}

	/**
	 * Gets the angle between the x-axis and a tangent line between two circles.
	 *
	 * @param circle1
	 *            The first circle.
	 * @param circle2
	 *            The second circle.
	 * @return The angle in radians.
	 */
	public static double getAngleBetweenCircles(Circle circle1, Circle circle2) {
		float r1 = circle1.getRadius();
		float r2 = circle2.getRadius();
		int dx = circle1.getMiddlePoint().getX() - circle2.getMiddlePoint().getX();
		int dy = circle1.getMiddlePoint().getY() - circle2.getMiddlePoint().getY();
		return getAngleBetweenCircles(r1, r2, dx, dy);
	}

}
