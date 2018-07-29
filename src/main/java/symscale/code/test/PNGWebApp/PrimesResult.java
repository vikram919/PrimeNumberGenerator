package symscale.code.test.PNGWebApp;

public class PrimesResult {

	private final String primesString;
	private final int timeElapsed; /* in milli seconds */
	private final int noOfPrimes;
	private final String algorithmType;
	private final int primeRange;

	public PrimesResult(String algorithtmType, int primesRange, String primesString, int timeElapsed, int noOfPrimes) {
		this.algorithmType = algorithtmType;
		this.primeRange = primesRange;
		this.primesString = primesString;
		this.timeElapsed = timeElapsed;
		this.noOfPrimes = noOfPrimes;
	}

	public int getTimeElapsed() {
		return timeElapsed;
	}

	public int getNoOfPrimes() {
		return noOfPrimes;
	}

	public String getPrimesString() {
		return primesString;
	}

	public String getAlgorithmType() {
		return algorithmType;
	}

	public int getPrimesRange() {
		return primeRange;
	}

}
