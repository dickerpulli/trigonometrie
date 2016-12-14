package de.tbosch.utilities.trigonometry;

import java.awt.Point;

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
	 * @deprecated Use {@link Circle#getCirclePoints()} instead
	 */
	@Deprecated
	public static Point[] getCirclePoints(double radius, Point middlepoint) {
		return new Circle(middlepoint, (float)radius).getCirclePoints();
	}

	/**
	 * @deprecated Use {@link #getVerticalDistance(float, float, int, double, boolean)} instead
	 */
	@Deprecated
	public static int getYCmOfAngleAndXcm(int r1, int r2, int xCm, double angleDeg, boolean outerTangent) {
		return getVerticalDistance(r1, r2, Math.abs(xCm * 10), angleDeg, outerTangent) / 10;
	}

	/**
	 * Gets the vertical distance (y-axis) between two circles (their middle points).
	 * 
	 * @param r1 the radius of the first circle
	 * @param r2 the radius of the second circle
	 * @param x the x-distance between both middlepoints
	 * @param angleDeg the angle between the x-axis and a tangent line between two circles (in degrees)
	 * @param outerTangent if the outer tangent is meant
	 * @return the y-distance (negative means: the middle point of the second circle has a bigger x-value)
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
		}
		else {
			double n = v * Math.cos(betaRad);
			double q = n / Math.sin(betaRad);
			double m = (q * r2) / n;
			y_longMm = Math.round(q - m);
		}

		int y;
		if (y_longMm > Integer.MAX_VALUE) {
			throw new IllegalStateException("maximum value (" + Integer.MAX_VALUE + ") reached : " + y_longMm);
		}
		else {
			y = (int)y_longMm;
		}

		return y;
	}

	/**
	 * @deprecated Use {@link #getAngleBetweenCircles(int, int, int, int)}
	 */
	@Deprecated
	public static double getAngleRadOfYcmAndXcm(int r1, int r2, int xCm, int yCm) {
		int xMm = Math.abs(xCm * 10);
		int yMm = Math.abs(yCm * 10);
		return getAngleBetweenCircles(r1, r2, xMm, yMm);
	}

	/**
	 * Gets the angle between the x-axis and a tangent line between two circles.
	 * 
	 * @param dx the x-distance of the middlepoints
	 * @param dy the y-distance of the middlepoints
	 * @param r1 the radius of the first circle
	 * @param r2 the radius of the second circle
	 * @return the angle in radians
	 */
	public static double getAngleBetweenCircles(int r1, int r2, int dx, int dy) {
		double sum1 = (Math.pow(dy, 2) - Math.pow((r1 - r2), 2)) / (Math.pow(dx, 2) + Math.pow(dy, 2));
		double sum2 = ((r1 - r2) * dx) / (Math.pow(dx, 2) + Math.pow(dy, 2));
		double cos = Math.sqrt(sum1 + Math.pow(sum2, 2)) + sum2;
		return Math.acos(cos);
	}

}
