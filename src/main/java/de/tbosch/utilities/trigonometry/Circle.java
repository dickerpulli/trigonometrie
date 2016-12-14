package de.tbosch.utilities.trigonometry;

import java.awt.Point;

/**
 * A normal circle representation with middle point and radius.
 * @author Thomas Bosch (tbosch@gmx.de)
 */
public class Circle {

	private final Point middlePoint;

	private final float radius;

	/**
	 * Constructs the circle.
	 * @param middlePoint The middle point of the circle
	 * @param radius The radius of the circle
	 */
	public Circle(Point middlePoint, float radius) {
		this.middlePoint = middlePoint;
		this.radius = radius;
	}

	/**
	 * Gets the points that build a circle.
	 * @return All 360 points (each single full degree)
	 */
	public Point[] getCirclePoints() {
		// make a point on every degree -> 360 points
		Point[] points = new Point[360];
		for (int i = 0; i < points.length; i++) {
			double rads = i * Math.PI / 180.00;
			double x = Math.cos(rads) * radius;
			double y = Math.sin(rads) * radius;
			points[i] = new Point((int) x + middlePoint.x, (int) y + middlePoint.y);
		}
		return points;
	}

	/**
	 * Gets the two tangent points. Round up or down to the next integer point coordinate.
	 * @param point The point laying outside the circle
	 * @return The tangent points
	 * @throws IntersectionException If the point is inside the circle
	 */
	public Point[] getTangentPoints(Point point) {
		Point[] pArray = new Point[2];
		pArray[0] = new Point();
		pArray[1] = new Point();

		Point vector = new Point(point.x - middlePoint.x, point.y - middlePoint.y);
		float sqrLength = (float) Math.pow(vector.x, 2) + (float) Math.pow(vector.y, 2);
		float sqrRadius = (float) Math.pow(radius, 2);
		if (sqrLength >= sqrRadius) {
			float ratio = 1 / sqrLength;
			float dist = (float) Math.sqrt(Math.abs(sqrLength - sqrRadius));
			pArray[0].x = Math.round(middlePoint.x + radius * (radius * vector.x - vector.y * dist) * ratio);
			pArray[0].y = Math.round(middlePoint.y + radius * (radius * vector.y + vector.x * dist) * ratio);
			pArray[1].x = Math.round(middlePoint.x + radius * (radius * vector.x + vector.y * dist) * ratio);
			pArray[1].y = Math.round(middlePoint.y + radius * (radius * vector.y - vector.x * dist) * ratio);
		} else {
			throw new IntersectionException("the point " + point + " is inside the circle: " + this);
		}

		return pArray;
	}

	/**
	 * Gets the tangent points of the outer common tangent lines of two circles.
	 * @param circle The second circle
	 * @return The tangent points of the common tangent lines. The first dimension are tangent lines and the second dimension are the points that build each
	 *         tangent line.
	 * @throws IntersectionException If the given circle is fully inside this circle
	 */
	public Point[][] getOuterTangentPoints(Circle circle) {
		Point[] tangent1 = new Point[2];
		Point[] tangent2 = new Point[2];
		tangent1[0] = new Point();
		tangent1[1] = new Point();
		tangent2[0] = new Point();
		tangent2[1] = new Point();

		Point[] diffCircleTangentPoints = new Point[2];
		Point middlepointLeft, middlepointRight;
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
				middlepointRight = middlePoint;
			} else if (radius < radius2) {
				Circle diffCircle = new Circle(middlePoint2, (radius2 - radius));
				diffCircleTangentPoints = diffCircle.getTangentPoints(middlePoint);
				middlepointLeft = middlePoint;
				radiusLeft = radius;
				middlepointRight = middlePoint2;
			} else {
				middlepointLeft = middlePoint;
				radiusLeft = radius;
				middlepointRight = middlePoint2;
				diffCircleTangentPoints[0] = middlepointRight;
				diffCircleTangentPoints[1] = middlepointRight;
			}
			Point vector1 = new Point(diffCircleTangentPoints[1].x - middlepointLeft.x, diffCircleTangentPoints[1].y - middlepointLeft.y);
			float v1Length = (float) Math.sqrt(Math.pow(vector1.x, 2) + Math.pow(vector1.y, 2));
			Point vector2 = new Point(diffCircleTangentPoints[0].x - middlepointLeft.x, diffCircleTangentPoints[0].y - middlepointLeft.y);
			float v2Length = (float) Math.sqrt(Math.pow(vector2.x, 2) + Math.pow(vector2.y, 2));

			if (otherDir) {
				tangent2[1].x = Math.round(middlepointLeft.x - vector1.y * radiusLeft / v1Length);
				tangent2[1].y = Math.round(middlepointLeft.y + vector1.x * radiusLeft / v1Length);
				tangent1[1].x = Math.round(diffCircleTangentPoints[1].x - vector1.y * radiusLeft / v1Length);
				tangent1[1].y = Math.round(diffCircleTangentPoints[1].y + vector1.x * radiusLeft / v1Length);
				tangent2[0].x = Math.round(middlepointLeft.x + vector2.y * radiusLeft / v2Length);
				tangent2[0].y = Math.round(middlepointLeft.y - vector2.x * radiusLeft / v2Length);
				tangent1[0].x = Math.round(diffCircleTangentPoints[0].x + vector2.y * radiusLeft / v2Length);
				tangent1[0].y = Math.round(diffCircleTangentPoints[0].y - vector2.x * radiusLeft / v2Length);
			} else {
				tangent1[0].x = Math.round(middlepointLeft.x - vector1.y * radiusLeft / v1Length);
				tangent1[0].y = Math.round(middlepointLeft.y + vector1.x * radiusLeft / v1Length);
				tangent2[0].x = Math.round(diffCircleTangentPoints[1].x - vector1.y * radiusLeft / v1Length);
				tangent2[0].y = Math.round(diffCircleTangentPoints[1].y + vector1.x * radiusLeft / v1Length);
				tangent1[1].x = Math.round(middlepointLeft.x + vector2.y * radiusLeft / v2Length);
				tangent1[1].y = Math.round(middlepointLeft.y - vector2.x * radiusLeft / v2Length);
				tangent2[1].x = Math.round(diffCircleTangentPoints[0].x + vector2.y * radiusLeft / v2Length);
				tangent2[1].y = Math.round(diffCircleTangentPoints[0].y - vector2.x * radiusLeft / v2Length);
			}
		} else {
			throw new IntersectionException("the circle " + circle + " is fully inside the circle: " + this);
		}

		return new Point[][] { tangent1, tangent2 };
	}

	/**
	 * Gets the tangent points of the inner common tangent lines of two circles. The result is a two dimensional array with the tangent lines in the first
	 * dimension and the points of each tangent line.
	 * @param circle The second circle
	 * @return The tangent points of the common tangent lines. The first dimension are tangent lines and the second dimension are the points that build each
	 *         tangent line.
	 * @throws IntersectionException If the given circle intersects this circle
	 */
	public Point[][] getInnerTangentPoints(Circle circle) {
		Point[] tangent1 = new Point[2];
		Point[] tangent2 = new Point[2];
		tangent1[0] = new Point();
		tangent1[1] = new Point();
		tangent2[0] = new Point();
		tangent2[1] = new Point();

		Point[] diffCircleTangentPoints = new Point[2];
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
			Point vector1 = new Point(diffCircleTangentPoints[1].x - middlepointLeft.x, diffCircleTangentPoints[1].y - middlepointLeft.y);
			float v1Length = (float) Math.sqrt(Math.pow(vector1.x, 2) + Math.pow(vector1.y, 2));
			Point vector2 = new Point(diffCircleTangentPoints[0].x - middlepointLeft.x, diffCircleTangentPoints[0].y - middlepointLeft.y);
			float v2Length = (float) Math.sqrt(Math.pow(vector2.x, 2) + Math.pow(vector2.y, 2));

			if (otherDir) {
				tangent2[0].x = Math.round(middlepointLeft.x + vector1.y * radiusLeft / v1Length);
				tangent2[0].y = Math.round(middlepointLeft.y - vector1.x * radiusLeft / v1Length);
				tangent1[1].x = Math.round(diffCircleTangentPoints[1].x + vector1.y * radiusLeft / v1Length);
				tangent1[1].y = Math.round(diffCircleTangentPoints[1].y - vector1.x * radiusLeft / v1Length);
				tangent2[1].x = Math.round(middlepointLeft.x - vector2.y * radiusLeft / v2Length);
				tangent2[1].y = Math.round(middlepointLeft.y + vector2.x * radiusLeft / v2Length);
				tangent1[0].x = Math.round(diffCircleTangentPoints[0].x - vector2.y * radiusLeft / v2Length);
				tangent1[0].y = Math.round(diffCircleTangentPoints[0].y + vector2.x * radiusLeft / v2Length);
			} else {
				tangent1[1].x = Math.round(middlepointLeft.x + vector1.y * radiusLeft / v1Length);
				tangent1[1].y = Math.round(middlepointLeft.y - vector1.x * radiusLeft / v1Length);
				tangent2[0].x = Math.round(diffCircleTangentPoints[1].x + vector1.y * radiusLeft / v1Length);
				tangent2[0].y = Math.round(diffCircleTangentPoints[1].y - vector1.x * radiusLeft / v1Length);
				tangent1[0].x = Math.round(middlepointLeft.x - vector2.y * radiusLeft / v2Length);
				tangent1[0].y = Math.round(middlepointLeft.y + vector2.x * radiusLeft / v2Length);
				tangent2[1].x = Math.round(diffCircleTangentPoints[0].x - vector2.y * radiusLeft / v2Length);
				tangent2[1].y = Math.round(diffCircleTangentPoints[0].y + vector2.x * radiusLeft / v2Length);
			}
		} else {
			throw new IntersectionException("the circle " + circle + " intersects the circle: " + this);
		}

		return new Point[][] { tangent1, tangent2 };
	}

	/**
	 * Gets the tangent points of the common inner and outer tangent lines.
	 * @param circle The second circle
	 * @return The tangent points array - first the outer tangent lines then the inner ones.
	 * @throws IntersectionException If the given circle is fully inside this circle.
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

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "middle point = [" + middlePoint.x + ", " + middlePoint.y + "], radius = " + radius;
	}

}
