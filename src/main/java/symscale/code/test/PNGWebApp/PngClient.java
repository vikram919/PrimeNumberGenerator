package symscale.code.test.PNGWebApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class PngClient {

	private static final String argsString = new StringBuilder()
			.append("************************************************************\n")
			.append("Welcome to PNG web client \n")
			.append("************************************************************\n")
			.append("This client is using the http Web service to get the primes between two numbers\n")
			.append("Pass the arguments to the programs before running\n")
			.append("(I). Url of the web server ex: http://localhost:8080/png \n")
			.append("(II). Type of PNG algorithm ex: 1. for slower algorithm, 2. for second 3. for third\n")
			.append("************************************************************").toString();
	private static final String welcomeString = new StringBuilder()
			.append("************************************************************\n")
			.append("Welcome to PNG web client \n")
			.append("************************************************************\n")
			.append("This client is using the http Web service to get the primes between two numbers\n")
			.append("************************************************************").toString();

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println(argsString);
			System.exit(0);
		}
		System.out.println(welcomeString);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter the lower bound for primes:");
		int lowerBound = scanner.nextInt();
		System.out.println("Please enter the upper bound for primes: ");
		int upperBound = scanner.nextInt();
		HttpClient client = HttpClientBuilder.create().build();
		HttpPut request = new HttpPut(args[0]);
		RequestParams requestParams = new RequestParams(args[1], lowerBound, upperBound);
		request.setEntity(new ByteArrayEntity(requestParams.getBA()));
		try {
			HttpResponse response = client.execute(request);
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String payload = "";
			while ((payload = br.readLine()) != null) {
				System.out.println(payload);
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		}

	}
}
