package symscale.code.test.PNGWebApp;

public class PngStrategy3 {

	public static final String ALGORITHM_TYPE = "3";

	private PngStrategy3() {

	}

	public static PrimesResult getPrimes(int lower, int upper) {
		double timeIn = System.nanoTime();
		boolean[] array = null;
		int[] firstSegmentPrimes = null;

		int initPrimesCount = 0;
		int delta = (int) Math.sqrt(upper);

		array = new boolean[delta + 1];
		firstSegmentPrimes = new int[delta + 1];

		/* initialize */
		for (int i = 2; i <= delta; i++) {
			array[i] = true;
		}

		/* extract primes between 2 and sqt using the regulat sieve */
		for (int c = 2; c <= delta; c++) {
			if (array[c]) {
				firstSegmentPrimes[initPrimesCount] = c;
				initPrimesCount++;
				for (int k = c * c; k <= delta; k += c) {
					array[k] = false;
				}
			}
		}

		int range = (upper - lower + 1);
		array = new boolean[range];

		/* set all the values in array to 1 */
		for (int i = 0; i < range; i++) {
			array[i] = true;
		}

		/* run segmented sieve */
		for (int k = 0; k < initPrimesCount; k++) {
			int div = lower / firstSegmentPrimes[k];
			div *= firstSegmentPrimes[k];

			/*
			 * iterate with each element of firstsegment primes to eliminate its
			 * corresponding composite numbers
			 */
			while (div <= upper) {
				if (div >= lower && firstSegmentPrimes[k] != div) {
					array[div - lower] = false;
				}
				/* increment it by element value */
				div += firstSegmentPrimes[k];
			}
		}

		/* count the number of primes */
		int noOfPrimes = 0;

		/* returns the list of primes */
		int colums = 0;
		String listString = "";

		for (int i = 0; i < range; i++) {
			if (array[i] && (i + lower) != 1) {
				noOfPrimes++;
				colums++;
				if (colums == 50) {
					listString += i + lower + "\t\n";
					colums = 0;
				} else {
					listString += i + lower + "\t";
				}
			}
		}
		int timeElapsed = (int) ((System.nanoTime() - timeIn) / 1000000);
		return new PrimesResult(ALGORITHM_TYPE, (upper - lower), listString, timeElapsed, noOfPrimes);
	}
}
