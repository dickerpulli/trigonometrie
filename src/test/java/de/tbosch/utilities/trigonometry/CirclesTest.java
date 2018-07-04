package de.tbosch.utilities.trigonometry;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CirclesTest {

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
