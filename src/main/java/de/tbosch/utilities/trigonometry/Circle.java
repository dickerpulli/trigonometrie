package de.tbosch.utilities.trigonometry;

/**
 * A normal circle representation with middle point and radius.
 *
 * @author Thomas Bosch (tbosch@gmx.de)
 */
public class Circle {

	private final Point middlePoint;

	private final float radius;

	/**
	 * Constructs the circle.
	 *
	 * @param middlePoint
	 *            The middle point of the circle
	 * @param radius
	 *            The radius of the circle
	 */
	public Circle(Point middlePoint, float radius) {
		this.middlePoint = middlePoint;
		this.radius = radius;
	}

	/**
	 * Gets the points that build a circle.
	 *
	 * @return All 360 points (each single full degree)
	 */
	public Point[] getCirclePoints() {
		// make a point on every degree -> 360 points
		Point[] points = new Point[360];
		for (int i = 0; i < points.length; i++) {
			double rads = i * Math.PI / 180.00;
			double x = Math.cos(rads) * radius;
			double y = Math.sin(rads) * radius;
			points[i] = new Point((int) x + middlePoint.getX(), (int) y + middlePoint.getY());
		}
		return points;
	}

	/**
	 * Gets the two tangent points. Round up or down to the next integer point
	 * coordinate.
	 *
	 * @param point
	 *            The point laying outside the circle
	 * @return The tangent points
	 * @throws IntersectionException
	 *             If the point is inside the circle
	 */
	public Point[] getTangentPoints(Point point) {
		Point vector = new Point(point.getX() - middlePoint.getX(), point.getY() - middlePoint.getY());
		float sqrLength = (float) Math.pow(vector.getX(), 2) + (float) Math.pow(vector.getY(), 2);
		float sqrRadius = (float) Math.pow(radius, 2);
		if (sqrLength >= sqrRadius) {
			float ratio = 1 / sqrLength;
			float dist = (float) Math.sqrt(Math.abs(sqrLength - sqrRadius));
			int x0 = Math.round(middlePoint.getX() + radius * (radius * vector.getX() - vector.getY() * dist) * ratio);
			int y0 = Math.round(middlePoint.getY() + radius * (radius * vector.getY() + vector.getX() * dist) * ratio);
			int x1 = Math.round(middlePoint.getX() + radius * (radius * vector.getX() + vector.getY() * dist) * ratio);
			int y1 = Math.round(middlePoint.getY() + radius * (radius * vector.getY() - vector.getX() * dist) * ratio);

			return new Point[] { new Point(x0, y0), new Point(x1, y1) };
		} else {
			throw new IntersectionException("the point " + point + " is inside the circle: " + this);
		}
	}

	/**
	 * Gets the tangent points of the outer common tangent lines of two circles.
	 *
	 * @param circle
	 *            The second circle
	 * @return The tangent points of the common tangent lines. The first dimension
	 *         are tangent lines and the second dimension are the points that build
	 *         each tangent line.
	 * @throws IntersectionException
	 *             If the given circle is fully inside this circle
	 */
	public Point[][] getOuterTangentPoints(Circle circle) {
		Point[] diffCircleTangentPoints = new Point[2];
		Point middlepointLeft;
		Point middlepointRight;
		float radiusLeft;
		boolean otherDir = false;

		Point middlePoint2 = circle.getMiddlePoint();
		float radius2 = circle.getRadius();
		if (middlePoint.distance(middlePoint2) >= Math.abs(radius - radius2)) {
			if (radius > radius2) {
				otherDir = true;
				Circle diffCircle = new Circle(middlePoint, (radius - radius2));
				diffCircleTangentPoints = diffCircle.getTangentPoints(middlePoint2);
				middlepointLeft = middlePoint2;
				radiusLeft = radius2;
			} else if (radius < radius2) {
				Circle diffCircle = new Circle(middlePoint2, (radius2 - radius));
				diffCircleTangentPoints = diffCircle.getTangentPoints(middlePoint);
				middlepointLeft = middlePoint;
				radiusLeft = radius;
			} else {
				middlepointLeft = middlePoint;
				radiusLeft = radius;
				middlepointRight = middlePoint2;
				diffCircleTangentPoints[0] = middlepointRight;
				diffCircleTangentPoints[1] = middlepointRight;
			}
			Point vector1 = new Point(diffCircleTangentPoints[1].getX() - middlepointLeft.getX(),
					diffCircleTangentPoints[1].getY() - middlepointLeft.getY());
			float v1Length = (float) Math.sqrt(Math.pow(vector1.getX(), 2) + Math.pow(vector1.getY(), 2));
			Point vector2 = new Point(diffCircleTangentPoints[0].getX() - middlepointLeft.getX(),
					diffCircleTangentPoints[0].getY() - middlepointLeft.getY());
			float v2Length = (float) Math.sqrt(Math.pow(vector2.getX(), 2) + Math.pow(vector2.getY(), 2));

			int tangent1_x0, tangent1_y0, tangent1_x1, tangent1_y1;
			int tangent2_x0, tangent2_y0, tangent2_x1, tangent2_y1;
			if (otherDir) {
				tangent2_x1 = Math.round(middlepointLeft.getX() - vector1.getY() * radiusLeft / v1Length);
				tangent2_y1 = Math.round(middlepointLeft.getY() + vector1.getX() * radiusLeft / v1Length);
				tangent1_x1 = Math.round(diffCircleTangentPoints[1].getX() - vector1.getY() * radiusLeft / v1Length);
				tangent1_y1 = Math.round(diffCircleTangentPoints[1].getY() + vector1.getX() * radiusLeft / v1Length);
				tangent2_x0 = Math.round(middlepointLeft.getX() + vector2.getY() * radiusLeft / v2Length);
				tangent2_y0 = Math.round(middlepointLeft.getY() - vector2.getX() * radiusLeft / v2Length);
				tangent1_x0 = Math.round(diffCircleTangentPoints[0].getX() + vector2.getY() * radiusLeft / v2Length);
				tangent1_y0 = Math.round(diffCircleTangentPoints[0].getY() - vector2.getX() * radiusLeft / v2Length);
			} else {
				tangent1_x0 = Math.round(middlepointLeft.getX() - vector1.getY() * radiusLeft / v1Length);
				tangent1_y0 = Math.round(middlepointLeft.getY() + vector1.getX() * radiusLeft / v1Length);
				tangent2_x0 = Math.round(diffCircleTangentPoints[1].getX() - vector1.getY() * radiusLeft / v1Length);
				tangent2_y0 = Math.round(diffCircleTangentPoints[1].getY() + vector1.getX() * radiusLeft / v1Length);
				tangent1_x1 = Math.round(middlepointLeft.getX() + vector2.getY() * radiusLeft / v2Length);
				tangent1_y1 = Math.round(middlepointLeft.getY() - vector2.getX() * radiusLeft / v2Length);
				tangent2_x1 = Math.round(diffCircleTangentPoints[0].getX() + vector2.getY() * radiusLeft / v2Length);
				tangent2_y1 = Math.round(diffCircleTangentPoints[0].getY() - vector2.getX() * radiusLeft / v2Length);
			}

			return new Point[][] { //
					new Point[] { new Point(tangent1_x0, tangent1_y0), new Point(tangent1_x1, tangent1_y1) }, //
					new Point[] { new Point(tangent2_x0, tangent2_y0), new Point(tangent2_x1, tangent2_y1) }//
			};
		} else {
			throw new IntersectionException("the circle " + circle + " is fully inside the circle: " + this);
		}
	}

	/**
	 * Gets the tangent points of the inner common tangent lines of two circles. The
	 * result is a two dimensional array with the tangent lines in the first
	 * dimension and the points of each tangent line.
	 *
	 * @param circle
	 *            The second circle
	 * @return The tangent points of the common tangent lines. The first dimension
	 *         are tangent lines and the second dimension are the points that build
	 *         each tangent line.
	 * @throws IntersectionException
	 *             If the given circle intersects this circle
	 */
	public Point[][] getInnerTangentPoints(Circle circle) {
		Point[] diffCircleTangentPoints;
		Point middlepointLeft;
		float radiusLeft;
		boolean otherDir = false;

		Point middlePoint2 = circle.getMiddlePoint();
		float radius2 = circle.getRadius();
		if (middlePoint.distance(middlePoint2) >= Math.abs(radius + radius2)) {
			if (radius >= radius2) {
				otherDir = true;
				Circle diffCircle = new Circle(middlePoint, (radius + radius2));
				diffCircleTangentPoints = diffCircle.getTangentPoints(middlePoint2);
				middlepointLeft = middlePoint2;
				radiusLeft = radius2;
			} else {
				Circle diffCircle = new Circle(middlePoint2, (radius2 + radius));
				diffCircleTangentPoints = diffCircle.getTangentPoints(middlePoint);
				middlepointLeft = middlePoint;
				radiusLeft = radius;
			}
			Point vector1 = new Point(diffCircleTangentPoints[1].getX() - middlepointLeft.getX(),
					diffCircleTangentPoints[1].getY() - middlepointLeft.getY());
			float v1Length = (float) Math.sqrt(Math.pow(vector1.getX(), 2) + Math.pow(vector1.getY(), 2));
			Point vector2 = new Point(diffCircleTangentPoints[0].getX() - middlepointLeft.getX(),
					diffCircleTangentPoints[0].getY() - middlepointLeft.getY());
			float v2Length = (float) Math.sqrt(Math.pow(vector2.getX(), 2) + Math.pow(vector2.getY(), 2));

			int tangent1_x0, tangent1_y0, tangent1_x1, tangent1_y1;
			int tangent2_x0, tangent2_y0, tangent2_x1, tangent2_y1;
			if (otherDir) {
				tangent2_x0 = Math.round(middlepointLeft.getX() + vector1.getY() * radiusLeft / v1Length);
				tangent2_y0 = Math.round(middlepointLeft.getY() - vector1.getX() * radiusLeft / v1Length);
				tangent1_x1 = Math.round(diffCircleTangentPoints[1].getX() + vector1.getY() * radiusLeft / v1Length);
				tangent1_y1 = Math.round(diffCircleTangentPoints[1].getY() - vector1.getX() * radiusLeft / v1Length);
				tangent2_x1 = Math.round(middlepointLeft.getX() - vector2.getY() * radiusLeft / v2Length);
				tangent2_y1 = Math.round(middlepointLeft.getY() + vector2.getX() * radiusLeft / v2Length);
				tangent1_x0 = Math.round(diffCircleTangentPoints[0].getX() - vector2.getY() * radiusLeft / v2Length);
				tangent1_y0 = Math.round(diffCircleTangentPoints[0].getY() + vector2.getX() * radiusLeft / v2Length);
			} else {
				tangent1_x1 = Math.round(middlepointLeft.getX() + vector1.getY() * radiusLeft / v1Length);
				tangent1_y1 = Math.round(middlepointLeft.getY() - vector1.getX() * radiusLeft / v1Length);
				tangent2_x0 = Math.round(diffCircleTangentPoints[1].getX() + vector1.getY() * radiusLeft / v1Length);
				tangent2_y0 = Math.round(diffCircleTangentPoints[1].getY() - vector1.getX() * radiusLeft / v1Length);
				tangent1_x0 = Math.round(middlepointLeft.getX() - vector2.getY() * radiusLeft / v2Length);
				tangent1_y0 = Math.round(middlepointLeft.getY() + vector2.getX() * radiusLeft / v2Length);
				tangent2_x1 = Math.round(diffCircleTangentPoints[0].getX() - vector2.getY() * radiusLeft / v2Length);
				tangent2_y1 = Math.round(diffCircleTangentPoints[0].getY() + vector2.getX() * radiusLeft / v2Length);
			}

			return new Point[][] { //
					new Point[] { new Point(tangent1_x0, tangent1_y0), new Point(tangent1_x1, tangent1_y1) }, //
					new Point[] { new Point(tangent2_x0, tangent2_y0), new Point(tangent2_x1, tangent2_y1) }//
			};
		} else {
			throw new IntersectionException("the circle " + circle + " intersects the circle: " + this);
		}
	}

	/**
	 * Gets the tangent points of the common inner and outer tangent lines.
	 *
	 * @param circle
	 *            The second circle
	 * @return The tangent points array - first the outer tangent lines then the
	 *         inner ones.
	 * @throws IntersectionException
	 *             If the given circle is fully inside this circle.
	 */
	public Point[][] getTangentPoints(Circle circle) {
		Point[][] outerTangents = getOuterTangentPoints(circle);
		Point[][] innerTangents;
		try {
			innerTangents = getInnerTangentPoints(circle);
			return new Point[][] { outerTangents[0], outerTangents[1], innerTangents[0], innerTangents[1] };
		} catch (IntersectionException e) {
			// given circle intersects this circle - only give back the outer tangent points
			return new Point[][] { outerTangents[0], outerTangents[1] };
		}
	}

	/**
	 * @return The middle point of the circle
	 */
	public Point getMiddlePoint() {
		return middlePoint;
	}

	/**
	 * @return The circle radius
	 */
	public float getRadius() {
		return radius;
	}

	@Override
	public String toString() {
		return "middle point = " + middlePoint + ", radius = " + radius;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((middlePoint == null) ? 0 : middlePoint.hashCode());
		result = prime * result + Float.floatToIntBits(radius);
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
		Circle other = (Circle) obj;
		if (middlePoint == null) {
			if (other.middlePoint != null)
				return false;
		} else if (!middlePoint.equals(other.middlePoint))
			return false;
		if (Float.floatToIntBits(radius) != Float.floatToIntBits(other.radius))
			return false;
		return true;
	}



}
