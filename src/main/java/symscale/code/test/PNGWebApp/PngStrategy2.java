package symscale.code.test.PNGWebApp;

public class PngStrategy2 {
	public static final String ALGORITHM_TYPE = "2";

	private PngStrategy2() {

	}

	public static PrimesResult getPrimes(int lower, int upper) {
		double timeIn = System.nanoTime();
		/*
		 * while you initialize boolean array all elements assigned to false by default
		 */
		boolean[] primes = new boolean[upper + 1];

		if (lower == 1 || lower == 0) {
			lower = 2;
		}

		/* set the numbers of the given range to true rest all will be false */
		for (int i = lower; i <= upper; i++) {
			primes[i] = true;
		}
		for (int j = 2; j <= Math.sqrt(upper); j++) {
			for (int k = j * j; k <= upper; k += j) {
				/* strike of multiples of initial prime in the given range */
				if (primes[k]) {
					primes[k] = false;
				}
			}
		}

		/* count the number of primes */
		int noOfPrimes = 0;

		/* returns the list of primes */
		int colums = 0;
		String listString = "";
		for (int l = lower; l <= upper; l++) {
			if (primes[l]) {
				noOfPrimes++;
				colums++;
				if (colums == 50) {
					listString += l + "\t\n";
					colums = 0;
				} else {
					listString += l + "\t";
				}
			}
		}
		int timeElapsed = (int) ((System.nanoTime() - timeIn) / 1000000);
		return new PrimesResult(ALGORITHM_TYPE, (upper - lower), listString, timeElapsed, noOfPrimes);
	}
}
