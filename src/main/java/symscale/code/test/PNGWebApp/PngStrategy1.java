package symscale.code.test.PNGWebApp;

public class PngStrategy1 {

	public static final String ALGORITHM_TYPE = "1";

	private PngStrategy1() {

	}

	public static PrimesResult getPrimes(int lower, int upper) {
		double timeIn = System.nanoTime();
		if (lower == 0 || lower == 1) {
			lower = 2;
		}

		int noOfPrimes = 0;
		/**
		 * Create the list of consecutive numbers between lower and upper (includes
		 * lower).
		 */
		String listString = "";
		int colums =0;
		for (int i = lower; i <= upper; i++) {
			if (isPrime(i)) {
				noOfPrimes++;
				colums++;
				if(colums == 50) {
					listString += i + "\t\n";
					colums=0;
				}else {
					listString += i + "\t";
				}
			}
		}
		int timeElapsed = (int) ((System.nanoTime() - timeIn) / 1000000);
		return new PrimesResult(ALGORITHM_TYPE, (upper - lower), listString, timeElapsed, noOfPrimes);
	}

	private static boolean isPrime(int num) {
		if (num == 2 || num == 3 || num == 5) {
			return true;
		}

		/*
		 * num & 00000001 == 0, checks for even numbers, even numbers LSB is 0.
		 */
		if (num <= 1 || (num & 1) == 0) {
			return false;
		}

		/**
		 * excludes even multiples, check for only odd number multiples
		 */
		for (int i = 3; i * i <= num; i += 2) {
			if (num % i == 0) {
				return false;
			}
		}

		return true;
	}

}
