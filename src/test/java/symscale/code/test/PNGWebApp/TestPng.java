package symscale.code.test.PNGWebApp;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class TestPng {

	@Test
	public void TesPngStrategy1() {
		PrimesResult primesResult1 = PngStrategy1.getPrimes(1600, 180000);
		PrimesResult primesResult2 = PngStrategy2.getPrimes(1600, 180000);
		PrimesResult primesResult3 = PngStrategy3.getPrimes(1600, 180000);
		System.out.println(primesResult1.getNoOfPrimes());
		System.out.println(primesResult1.getTimeElapsed());
		System.out.println(primesResult2.getNoOfPrimes());
		System.out.println(primesResult2.getTimeElapsed());
		System.out.println(primesResult3.getNoOfPrimes());
		System.out.println(primesResult3.getTimeElapsed());
	}
}
