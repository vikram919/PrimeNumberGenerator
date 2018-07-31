package symscale.code.test.PNGWebApp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class PngServer {

	static Connection con;
	static final int HTTP_PORT = 8081;

	public static void main(String[] args) {

		initDbTable();
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(HTTP_PORT), 0);
			HttpContext context = server.createContext("/png");
			context.setHandler(new PngServer().new MyHandler());
			server.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// adds transaction record to the in-memory data base
	private static void populateRecord(int noOfPrimes, int primesRange, String algorithmType, int timeElapsed) {
		String updateRecord = "INSERT INTO Records "
				+ "(time, range, Elapsed_Time, Algorithm_Choosen, Primes_Returned) " + "values (NOW(), " + primesRange
				+ ", " + timeElapsed + ", '" + algorithmType + "', " + noOfPrimes + ");";
		try {
			con.createStatement().executeUpdate(updateRecord);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void initDbTable() {
		try {
			String createTable = getCommand("sql/table.sql");
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			con = DriverManager.getConnection("jdbc:hsqldb:file:db-data/mydatabase", "SA", "");

			// create table
			con.createStatement().executeUpdate(createTable);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static String getCommand(String fname) {
		File file = new File(fname);
		String string = null;
		try {
			string = FileUtils.readFileToString(file, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return string;
	}

	class MyHandler implements HttpHandler {
		public void handle(HttpExchange exchange) throws IOException {
			InputStream in = exchange.getRequestBody();
			int size = in.available();
			byte[] requestPayload = new byte[size];
			in.read(requestPayload, 0, size);
			if (exchange.getRequestMethod().equals("PUT")) {
				RequestParams requestParams = new RequestParams(requestPayload);
				switch (requestParams.getAlgorithmType()) {
				case PngStrategy1.ALGORITHM_TYPE:
					PrimesResult primesResult = PngStrategy1.getPrimes(requestParams.getLowerBound(),
							requestParams.getUpperBound());
					sendResponse(exchange, primesResult);
					break;
				case PngStrategy2.ALGORITHM_TYPE:
					PrimesResult primesResult2 = PngStrategy2.getPrimes(requestParams.getLowerBound(),
							requestParams.getUpperBound());
					sendResponse(exchange, primesResult2);
					break;
				case PngStrategy3.ALGORITHM_TYPE:
					PrimesResult primesResult3 = PngStrategy3.getPrimes(requestParams.getLowerBound(),
							requestParams.getUpperBound());
					sendResponse(exchange, primesResult3);
					break;
				}

			}
		}
	}

	private static void sendResponse(HttpExchange exchange, PrimesResult primesResult) throws IOException {
		populateRecord(primesResult.getNoOfPrimes(), primesResult.getPrimesRange(), primesResult.getAlgorithmType(),
				primesResult.getTimeElapsed());
		String response = primesResult.getPrimesString();
		exchange.sendResponseHeaders(200, response.length());
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}
