package symscale.code.test.PNGWebApp;

public class PngStrategy3 {

	public static final String ALGORITHM_TYPE = "3";
	static boolean[] array = null;
	static int noOfPrimes = 0;
	static int columns = 0;
	static int[] firstSegmentPrimes = null;

	private PngStrategy3() {

	}

	public static PrimesResult getPrimes(int lower, int upper) {
		double timeIn = System.nanoTime();

		int initPrimesCount = 0;
		int delta = (int) Math.sqrt(upper);

		array = new boolean[delta+1];
		firstSegmentPrimes = new int[delta+1];

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
		String listString = "";
		int range = upper - lower;
		int segment = range/delta;
		int lastSegmentVal = 0;
		for (int q = 0; q < delta; q++) {
			listString += task(lower+(segment * q), lower+(segment * (q + 1)), firstSegmentPrimes, initPrimesCount);
			if(q==(delta-1)) {
				lastSegmentVal = delta * (q + 1);
			}
		}
		listString += task(lastSegmentVal, upper, firstSegmentPrimes, initPrimesCount);
		int timeElapsed = (int) ((System.nanoTime() - timeIn) / 1000000);
		return new PrimesResult(ALGORITHM_TYPE, (upper - lower), listString, timeElapsed, noOfPrimes);
	}
	
	public static String task(int lower, int upper, int[] firstSegmentedPrimes, int firstPrimesLen) {
		int range = (upper - lower);
		array = new boolean[range];

		/* set all the values in array to 1 */
		for (int i = 0; i < range; i++) {
			array[i] = true;
		}

		/* run segmented sieve */
		for (int k = 0; k < firstPrimesLen; k++) {
			int div = lower / firstSegmentPrimes[k];
			div *= firstSegmentPrimes[k];

			/*
			 * iterate with each element of firstsegment primes to eliminate its
			 * corresponding composite numbers
			 */
			while (div < upper) {
				if (div >= lower && firstSegmentPrimes[k] != div) {
					array[div - lower] = false;
				}
				/* increment it by element value */
				div += firstSegmentPrimes[k];
			}
		}

		String listString = "";
		for (int i = 0; i < range; i++) {
			if (array[i] && (i + lower) != 1) {
				noOfPrimes++;
				columns++;
				if (columns == 5) {
					int val = i+lower;
					listString += val+ "\t\n";
					columns = 0;
				} else {
					int val = i+lower;
					listString += val + "\t";
				}
			}
		}
		return listString;
	}
}
