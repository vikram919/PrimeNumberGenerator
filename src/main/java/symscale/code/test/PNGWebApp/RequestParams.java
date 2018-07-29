package symscale.code.test.PNGWebApp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class RequestParams {

	private final String algorithmType;
	private final int lowerBound;
	private final int upperBound;
	private final byte[] finalBA;

	public RequestParams(String algorithmType, int lowerBound, int upperBound) {
		this.algorithmType = algorithmType;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write(Integer.valueOf(algorithmType));
		try {
			bos.write(ByteBuffer.allocate(Integer.BYTES).putInt(lowerBound).array());
			bos.write(ByteBuffer.allocate(Integer.BYTES).putInt(upperBound).array());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.finalBA = bos.toByteArray();
	}

	public RequestParams(byte[] requestPayload) {
		this.finalBA = requestPayload;
		ByteArrayInputStream bis = new ByteArrayInputStream(requestPayload);
		this.algorithmType = Integer.toString(bis.read());
		byte[] lowerBoundBA = new byte[Integer.BYTES];
		bis.read(lowerBoundBA, 0, Integer.BYTES);
		this.lowerBound = ByteBuffer.wrap(lowerBoundBA).getInt();
		byte[] upperBoundBA = new byte[Integer.BYTES];
		bis.read(upperBoundBA, 0, Integer.BYTES);
		this.upperBound = ByteBuffer.wrap(upperBoundBA).getInt();
	}

	public String getAlgorithmType() {
		return algorithmType;
	}

	public int getLowerBound() {
		return lowerBound;
	}

	public int getUpperBound() {
		return upperBound;
	}

	public byte[] getBA() {
		return finalBA;
	}
}
