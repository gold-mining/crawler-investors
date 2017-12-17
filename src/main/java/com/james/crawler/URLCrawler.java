package com.james.crawler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class URLCrawler {

	public Map<String, String> getQuoteResults(Queue<String> tickers) throws InterruptedException {
		Map<String, String> tickerUrlMap = new ConcurrentHashMap<String, String>();

		ExecutorService executor = Executors.newFixedThreadPool(10);

		while (!tickers.isEmpty()) {
			executor.execute(new Runnable() {
				String stock;
				Map<String, String> tickerUrlMap;

				public void run() {
					try {
						String url = getQuoteResult(stock);
						tickerUrlMap.put(stock, url);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				private Runnable init(String stock, Map<String, String> tickerUrlMap) {
					this.stock = stock;
					this.tickerUrlMap = tickerUrlMap;
					return this;
				}
			}.init(tickers.poll(), tickerUrlMap));
		}

		executor.shutdown();
		executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

		return tickerUrlMap;
	}

	public String getQuoteResult(String ticker) throws Exception {
		int retry = 5;
		
		while (retry != 0) {
			String url = "https://research.investors.com/services/AutoSuggest.asmx/GetQuoteResults";
	
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);
	
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("q", ticker.replace(".", "")));
			urlParameters.add(new BasicNameValuePair("limit", "1"));
	
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
	
			HttpResponse response = client.execute(post);
	
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	
			String line = "";
			while ((line = rd.readLine()) != null) {
				if (line.contains("Url")) {
					return line.trim().replace("<Url>", "").replace("</Url>", "");
				}
			}
			retry--;
		}

		throw new Exception(ticker + " not found");
	}
}
