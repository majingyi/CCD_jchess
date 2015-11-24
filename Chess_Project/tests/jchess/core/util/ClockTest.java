package jchess.core.util;

import junit.framework.Assert;

import org.junit.Test;

public class ClockTest {

	private int	listernerCalled	= 0;

	@Test
	public void testClock() throws Exception {
		Clock clock = new Clock(0);
		Assert.assertEquals(0, clock.getRemainingTime());

		clock = new Clock(100);
		Assert.assertEquals(100, clock.getRemainingTime());

		boolean exception = false;
		try {
			clock = new Clock(-1);
		} catch (Exception e) {
			Assert.assertEquals("Time needs to be greater or equal than 0.", e.getMessage());
			exception = true;
		}
		Assert.assertTrue(exception);
	}

	@Test
	public void testToString() throws Exception {
		Clock clock = new Clock(0);
		Assert.assertEquals("00:00", clock.toString());

		clock = new Clock(12);
		Assert.assertEquals("00:12", clock.toString());

		clock = new Clock(61);
		Assert.assertEquals("01:01", clock.toString());

		clock = new Clock(611);
		Assert.assertEquals("10:11", clock.toString());

		clock = new Clock(3601);
		Assert.assertEquals("60:01", clock.toString());

		clock = new Clock(7201);
		Assert.assertEquals("120:01", clock.toString());
	}

	@Test
	public void testStart() throws Exception {
		Clock clock = new Clock(5);
		Assert.assertEquals(5, clock.getRemainingTime());
		clock.start();
		Thread.sleep(900);
		Assert.assertEquals(4, clock.getRemainingTime());
		Thread.sleep(900);
		Assert.assertEquals(3, clock.getRemainingTime());
		Thread.sleep(900);
		Assert.assertEquals(2, clock.getRemainingTime());
		Thread.sleep(900);
		Assert.assertEquals(1, clock.getRemainingTime());
	}

	@Test
	public void testStartTwice() throws Exception {
		Clock clock = new Clock(5);
		clock.start();

		boolean exception = false;
		try {
			clock.start();
		} catch (Exception e) {
			exception = true;
			Assert.assertEquals("Clock Thread is already running.", e.getMessage());
		}
		Assert.assertTrue(exception);
	}

	@Test
	public void testStop() throws Exception {
		Clock clock = new Clock(5);
		Assert.assertFalse(clock.isClockRunning());
		clock.start();
		Thread.sleep(500);
		Assert.assertTrue(clock.isClockRunning());
		clock.stop();
		Thread.sleep(500);
		Assert.assertFalse(clock.isClockRunning());
	}

	@Test
	public void testPause() throws Exception {
		Clock clock = new Clock(5);
		Assert.assertEquals(5, clock.getRemainingTime());
		clock.start();
		Thread.sleep(900);
		Assert.assertEquals(4, clock.getRemainingTime());
		Thread.sleep(900);
		Assert.assertEquals(3, clock.getRemainingTime());
		clock.pause();
		Thread.sleep(900);
		Assert.assertEquals(3, clock.getRemainingTime());
		Thread.sleep(900);
		Assert.assertEquals(3, clock.getRemainingTime());
	}

	@Test
	public void testResume() throws Exception {
		Clock clock = new Clock(5);
		Assert.assertEquals(5, clock.getRemainingTime());
		clock.start();
		Thread.sleep(900);
		Assert.assertEquals(4, clock.getRemainingTime());
		Thread.sleep(900);
		Assert.assertEquals(3, clock.getRemainingTime());
		clock.pause();
		Thread.sleep(900);
		Assert.assertEquals(3, clock.getRemainingTime());
		clock.resume();
		Thread.sleep(900);
		Assert.assertEquals(2, clock.getRemainingTime());
		Thread.sleep(900);
		Assert.assertEquals(1, clock.getRemainingTime());
	}

	@Test
	public void testAddClockListener() throws Exception {
		Clock clock = new Clock(1);
		clock.addClockListener(new IClockListener() {

			@Override
			public void timeOver(Clock clock) {
				listernerCalled = 1;
			}
		});
		clock.start();

		// after 2 seconds listener should have been called
		Thread.sleep(2000);

		Assert.assertEquals(1, listernerCalled);
	}

	@Test
	public void testSetTime() throws Exception {
		Clock clock = new Clock(5);
		Assert.assertEquals(5, clock.getRemainingTime());
		clock.setTime(55);
		clock.start();
		Assert.assertEquals(55, clock.getRemainingTime());
		clock.setTime(15);
		Assert.assertEquals(15, clock.getRemainingTime());
		Thread.sleep(1000);
		Assert.assertEquals(15, clock.getRemainingTime());
	}
}