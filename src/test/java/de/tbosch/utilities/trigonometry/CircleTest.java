package de.tbosch.utilities.trigonometry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CircleTest {

	@Test
	public void testGetCirclePoints() {
		float radius = 100;
		Point middlepoint = new Point(100, 100);
		Circle circle = new Circle(middlepoint, radius);
		Point[] points = circle.getCirclePoints();
		assertEquals(360, points.length);
		assertEquals(new Point(200, 100), points[0]);
		assertEquals(new Point(170, 170), points[45]);
		assertEquals(new Point(100, 200), points[90]);
		assertEquals(new Point(30, 170), points[135]);
		assertEquals(new Point(0, 100), points[180]);
		assertEquals(new Point(30, 30), points[225]);
		assertEquals(new Point(100, 0), points[270]);
		assertEquals(new Point(170, 30), points[315]);
	}

	@Test
	public void testGetTangentPointsWithPoint() throws Exception {
		float radius = 100;
		Point middlepoint = new Point(100, 100);
		Circle circle = new Circle(middlepoint, radius);
		Point point = new Point(200, 200);
		Point[] tangentPoints = circle.getTangentPoints(point);
		assertEquals(100, tangentPoints[0].getX());
		assertEquals(200, tangentPoints[0].getY());
		assertEquals(200, tangentPoints[1].getX());
		assertEquals(100, tangentPoints[1].getY());

		point = new Point(200, 100);
		tangentPoints = circle.getTangentPoints(point);
		assertEquals(200, tangentPoints[0].getX());
		assertEquals(100, tangentPoints[0].getY());
		assertEquals(200, tangentPoints[1].getX());
		assertEquals(100, tangentPoints[1].getY());

		point = new Point(-100, -100);
		tangentPoints = circle.getTangentPoints(point);
		assertEquals(141, tangentPoints[0].getX());
		assertEquals(9, tangentPoints[0].getY());
		assertEquals(9, tangentPoints[1].getX());
		assertEquals(141, tangentPoints[1].getY());
	}

	@Test
	public void testGetTangentPointsWithCircleException() throws Exception {
		assertThrows(IntersectionException.class, () -> {
			float radius = 100;
			Point middlepoint = new Point(100, 100);
			Circle circle = new Circle(middlepoint, radius);
			Circle circle2 = new Circle(middlepoint, radius - 1);
			circle.getTangentPoints(circle2);
		});
	}

}
