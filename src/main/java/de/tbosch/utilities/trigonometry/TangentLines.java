package de.tbosch.utilities.trigonometry;

import java.awt.Point;

/**
 * A class for functions on circles with tangent lines.
 * 
 * @author Thomas Bosch (tbosch@gmx.de)
 * @deprecated Use {@link Circle} instead
 */
@Deprecated
public class TangentLines {

	/**
	 * No constructor. Only static mathods.
	 */
	private TangentLines() {
		// utility class
	}

	/**
	 * @deprecated Use {@link Circle#getTangentPoints(Point)} instead
	 */
	@Deprecated
	public static Point[] getTangentPoints(Point middlepoint, float radius, Point point) {
		try {
			return new Circle(middlepoint, radius).getTangentPoints(point);
		}
		catch (IntersectionException e) {
			return new Point[] { new Point(), new Point() };
		}
	}

	/**
	 * @deprecated Use {@link Circle#getOuterTangentPoints(Circle)} instead
	 */
	@Deprecated
	public static Point[] getOuterTangentPoints(Point middlepoint1, float radius1, Point middlepoint2, float radius2) {
		try {
			Point[][] tangentPoints = new Circle(middlepoint1, radius1).getOuterTangentPoints(new Circle(middlepoint2,
					radius2));
			return new Point[] { tangentPoints[0][0], tangentPoints[0][1], tangentPoints[1][0], tangentPoints[1][1] };
		}
		catch (IntersectionException e) {
			return new Point[] { new Point(), new Point(), new Point(), new Point() };
		}
	}

	/**
	 * @deprecated Use {@link Circle#getOuterTangentPoints(Circle)} instead
	 */
	@Deprecated
	public static Point[] getInnerTangentPoints(Point middlepoint1, float radius1, Point middlepoint2, float radius2) {
		try {
			Point[][] tangentPoints = new Circle(middlepoint1, radius1).getInnerTangentPoints(new Circle(middlepoint2,
					radius2));
			return new Point[] { tangentPoints[0][0], tangentPoints[0][1], tangentPoints[1][0], tangentPoints[1][1] };
		}
		catch (IntersectionException e) {
			return new Point[] { new Point(), new Point(), new Point(), new Point() };
		}
	}

	/**
	 * @deprecated Use {@link Circle#getOuterTangentPoints(Circle)} instead
	 */
	@Deprecated
	public static Point[] getTangentPoints(Point middlepoint1, float radius1, Point middlepoint2, float radius2) {
		try {
			Point[][] tangentPoints = new Circle(middlepoint1, radius1).getTangentPoints(new Circle(middlepoint2,
					radius2));
			if (tangentPoints.length == 2) {
				return new Point[] { tangentPoints[0][0], tangentPoints[0][1], tangentPoints[1][0],
						tangentPoints[1][1], new Point(), new Point(), new Point(), new Point() };
			}
			else {
				return new Point[] { tangentPoints[0][0], tangentPoints[0][1], tangentPoints[1][0],
						tangentPoints[1][1], tangentPoints[2][0], tangentPoints[2][1], tangentPoints[3][0],
						tangentPoints[3][1] };
			}
		}
		catch (IntersectionException e) {
			return new Point[] { new Point(), new Point(), new Point(), new Point(), new Point(), new Point(),
					new Point(), new Point() };
		}
	}

}