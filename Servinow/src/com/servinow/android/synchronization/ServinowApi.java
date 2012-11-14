package com.servinow.android.synchronization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class ServinowApi {

	protected final static String host = "http://travelme.bloodblog.net/servinow/api";
	protected final static int readTimeout = 30000; //20 seconds.
	protected final static int connectTimeout = 15000; //15 seconds.

	protected String api_url;

	public ServinowApi(String apiCall) {
		api_url = host+apiCall;
	}

	protected InputStream doConnection() throws IOException{
		InputStream is = null;

		URL url = new URL(api_url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(getReadTimeout());
		conn.setConnectTimeout(getConnectTimeout());

		conn.connect();

		is = conn.getInputStream();

		return is;
	}

	public String call() throws IOException{

		InputStream is = doConnection();

		BufferedReader r = new BufferedReader(new InputStreamReader(is));
		StringBuilder total = new StringBuilder();
		String line;
		while ((line = r.readLine()) != null) {
			total.append(line);
		}
		
		//There's no need to do that. Or at least it doesn't break.
		//is.close();

		return total.toString();
	}

	private static int getReadTimeout() {
		return readTimeout;
	}

	private static int getConnectTimeout() {
		return connectTimeout;
	}

}
