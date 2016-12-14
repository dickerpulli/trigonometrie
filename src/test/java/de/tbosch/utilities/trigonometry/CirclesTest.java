package de.tbosch.utilities.trigonometry;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Test;

public class CirclesTest {

	@Test
	public void testGetCirclePoints() {
		double radius = 100;
		Point middlepoint = new Point(100, 100);
		Point[] points = Circles.getCirclePoints(radius, middlepoint);
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
	public void testGetYCmOfAngleAndXcm() {
		int y = Circles.getYCmOfAngleAndXcm(200, 200, 200, 135, true);
		assertEquals(200, y);
	}

	@Test
	public void testGetAngleRadOfYcmAndXcm() {
		double angle = Circles.getAngleRadOfYcmAndXcm(200, 200, 200, 200);
		assertEquals(45.00, Math.toDegrees(angle), 0.0009);
	}

	@Test
	public void testGetVerticalDistance() {
		int y = Circles.getVerticalDistance(200, 200, 500, 135, true);
		assertEquals(500, y);

		y = Circles.getVerticalDistance(200, 200, 500, 45, true);
		assertEquals(-500, y);

		y = Circles.getVerticalDistance(200, 200, 500, 78.69, true);
		assertEquals(-100, y);
	}

	@Test
	public void testGetAngleBetweenCircles() {
		double angle = Circles.getAngleBetweenCircles(200, 200, 500, 500);
		assertEquals(45.00, Math.toDegrees(angle), 0.0009);

		angle = Circles.getAngleBetweenCircles(200, 200, 500, 100);
		assertEquals(78.69, Math.toDegrees(angle), 0.0009);
	}

}
