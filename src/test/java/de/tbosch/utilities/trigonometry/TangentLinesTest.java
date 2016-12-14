package de.tbosch.utilities.trigonometry;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Test;

public class TangentLinesTest {

	@Test
	public void testGetTangentPoints() throws Exception {
		Point[] points = TangentLines.getTangentPoints(new Point(100, 100), 100, new Point(200, 200));
		assertEquals(2, points.length);
		assertEquals(100, points[0].x);
		assertEquals(200, points[0].y);
		assertEquals(200, points[1].x);
		assertEquals(100, points[1].y);

		points = TangentLines.getTangentPoints(new Point(100, 100), 100, new Point(0, 0));
		assertEquals(2, points.length);
		assertEquals(100, points[0].x);
		assertEquals(0, points[0].y);
		assertEquals(0, points[1].x);
		assertEquals(100, points[1].y);

		points = TangentLines.getTangentPoints(new Point(100, 100), 100, new Point(50, 50));
		assertEquals(2, points.length);
		assertEquals(0, points[0].x);
		assertEquals(0, points[0].y);
		assertEquals(0, points[1].x);
		assertEquals(0, points[1].y);
	}

	@Test
	public void testGetOuterTangentPoints() throws Exception {
		Point[] points = TangentLines.getOuterTangentPoints(new Point(100, 100), 100, new Point(400, 100), 100);
		assertEquals(4, points.length);
		assertEquals(100, points[0].x);
		assertEquals(200, points[0].y);
		assertEquals(100, points[1].x);
		assertEquals(0, points[1].y);
		assertEquals(400, points[2].x);
		assertEquals(200, points[2].y);
		assertEquals(400, points[3].x);
		assertEquals(0, points[3].y);

		points = TangentLines.getOuterTangentPoints(new Point(100, 100), 100, new Point(200, 100), 100);
		assertEquals(4, points.length);
		assertEquals(100, points[0].x);
		assertEquals(200, points[0].y);
		assertEquals(100, points[1].x);
		assertEquals(0, points[1].y);
		assertEquals(200, points[2].x);
		assertEquals(200, points[2].y);
		assertEquals(200, points[3].x);
		assertEquals(0, points[3].y);

		points = TangentLines.getOuterTangentPoints(new Point(100, 100), 100, new Point(150, 100), 10);
		assertEquals(4, points.length);
		assertEquals(0, points[0].x);
		assertEquals(0, points[0].y);
		assertEquals(0, points[1].x);
		assertEquals(0, points[1].y);
		assertEquals(0, points[2].x);
		assertEquals(0, points[2].y);
		assertEquals(0, points[3].x);
		assertEquals(0, points[3].y);
	}

	@Test
	public void testGetInnerTangentPoints() throws Exception {
		Point[] points = TangentLines.getInnerTangentPoints(new Point(100, 100), 100, new Point(400, 100), 100);
		assertEquals(4, points.length);
		assertEquals(166, points[0].x);
		assertEquals(174, points[0].y);
		assertEquals(166, points[1].x);
		assertEquals(26, points[1].y);
		assertEquals(333, points[2].x);
		assertEquals(175, points[2].y);
		assertEquals(333, points[3].x);
		assertEquals(25, points[3].y);

		points = TangentLines.getInnerTangentPoints(new Point(100, 100), 100, new Point(200, 100), 100);
		assertEquals(4, points.length);
		assertEquals(0, points[0].x);
		assertEquals(0, points[0].y);
		assertEquals(0, points[1].x);
		assertEquals(0, points[1].y);
		assertEquals(0, points[2].x);
		assertEquals(0, points[2].y);
		assertEquals(0, points[3].x);
		assertEquals(0, points[3].y);

		points = TangentLines.getInnerTangentPoints(new Point(100, 100), 100, new Point(150, 100), 10);
		assertEquals(4, points.length);
		assertEquals(0, points[0].x);
		assertEquals(0, points[0].y);
		assertEquals(0, points[1].x);
		assertEquals(0, points[1].y);
		assertEquals(0, points[2].x);
		assertEquals(0, points[2].y);
		assertEquals(0, points[3].x);
		assertEquals(0, points[3].y);
	}

	@Test
	public void testTangentPoints() throws Exception {
		Point[] points = TangentLines.getTangentPoints(new Point(100, 100), 100, new Point(400, 100), 100);
		assertEquals(8, points.length);
		assertEquals(100, points[0].x);
		assertEquals(200, points[0].y);
		assertEquals(100, points[1].x);
		assertEquals(0, points[1].y);
		assertEquals(400, points[2].x);
		assertEquals(200, points[2].y);
		assertEquals(400, points[3].x);
		assertEquals(0, points[3].y);
		assertEquals(166, points[4].x);
		assertEquals(174, points[4].y);
		assertEquals(166, points[5].x);
		assertEquals(26, points[5].y);
		assertEquals(333, points[6].x);
		assertEquals(175, points[6].y);
		assertEquals(333, points[7].x);
		assertEquals(25, points[7].y);

		points = TangentLines.getTangentPoints(new Point(100, 100), 100, new Point(200, 100), 100);
		assertEquals(8, points.length);
		assertEquals(100, points[0].x);
		assertEquals(200, points[0].y);
		assertEquals(100, points[1].x);
		assertEquals(0, points[1].y);
		assertEquals(200, points[2].x);
		assertEquals(200, points[2].y);
		assertEquals(200, points[3].x);
		assertEquals(0, points[3].y);
		assertEquals(0, points[4].x);
		assertEquals(0, points[4].y);
		assertEquals(0, points[5].x);
		assertEquals(0, points[5].y);
		assertEquals(0, points[6].x);
		assertEquals(0, points[6].y);
		assertEquals(0, points[7].x);
		assertEquals(0, points[7].y);

		points = TangentLines.getTangentPoints(new Point(100, 100), 100, new Point(150, 100), 10);
		assertEquals(8, points.length);
		assertEquals(0, points[0].x);
		assertEquals(0, points[0].y);
		assertEquals(0, points[1].x);
		assertEquals(0, points[1].y);
		assertEquals(0, points[2].x);
		assertEquals(0, points[2].y);
		assertEquals(0, points[3].x);
		assertEquals(0, points[3].y);
		assertEquals(0, points[4].x);
		assertEquals(0, points[4].y);
		assertEquals(0, points[5].x);
		assertEquals(0, points[5].y);
		assertEquals(0, points[6].x);
		assertEquals(0, points[6].y);
		assertEquals(0, points[7].x);
		assertEquals(0, points[7].y);
	}

}
