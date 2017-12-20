package com.james.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.james.crawler.InvestorsDataCrawler;
import com.james.crawler.URLCrawler;
import com.james.dao.OutputDAO;
import com.james.modal.InvestorsData;

public class Runner {
	public static void main(String[] args) throws Exception {		
		Date date1 = new Date();

		Map<String, String> input = new HashMap<String, String>();
		for (int i = 0; i < args.length; i++) {
			String[] parts = args[i].trim().split("=");
			input.put(parts[0], parts[1]);
			System.out.println(parts[0] + ": " + parts[1]);
		}

		Runner runner = new Runner();

		switch (input.get("mode")) {
			case "single-thread": runner.getDataSingleThread(input.get("stock-list"), input.get("date"), input.get("output")); break;
			case "multithread": runner.getDataMultiThread(input.get("thread"), input.get("stock-list"), input.get("date"), input.get("output")); break;
			default: break;
		}

		Date date2 = new Date();
		System.out.println(date2.getTime() - date1.getTime());

	}
	
	public void getDataMultiThread(String threadNumber, String stockList,  String date, String output) throws Exception {
		Queue<String> queue = getStockListFromFile(stockList);
		Queue<String> finished = new LinkedList<String>();
		
		URLCrawler urlCrawler = new URLCrawler();
		Map<String, String> urlMap= urlCrawler.getQuoteResults(new LinkedList<String>(queue));
		
		
		ExecutorService executor = Executors.newFixedThreadPool(Integer.parseInt(threadNumber));

		while (!queue.isEmpty()) {
			String ticker = queue.poll();
			if(OutputDAO.isFileExist(output + "/" + date, ticker) || !urlMap.containsKey(ticker)) continue; 
			
			executor.execute(new Runnable() {
				String stock;
				String url;
				String date;
				String output;
				Queue<String> finished;
				public void run() {
					InvestorsDataCrawler dataCrawler = new InvestorsDataCrawler();
					InvestorsData data = dataCrawler.getInvestorsData(stock, url);
					OutputDAO.outputToFile(data, output + "/" + date, stock);
					finished.offer(stock);
					System.err.print(finished.size() + " ");
				}

				private Runnable init(String stock, String date, String output, String url, Queue<String> finished) {
					System.err.print(stock + " ");
					this.stock = stock;
					this.date = date;
					this.output = output;
					this.url = url;
					this.finished = finished;
					return this;
				}
			}.init(ticker, date, output, urlMap.get(ticker), finished));
		
		}

		executor.shutdown();
		executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
	}
	
	public void getDataSingleThread(String stockList, String date, String output) throws Exception {
		Queue<String> queue = getStockListFromFile(stockList);
		
		URLCrawler urlCrawler = new URLCrawler();
		Map<String, String> urlMap= urlCrawler.getQuoteResults(new LinkedList<String>(queue));
		
		while (!queue.isEmpty()) {
			String ticker = queue.poll();
			InvestorsDataCrawler dataCrawler = new InvestorsDataCrawler();
			if(OutputDAO.isFileExist(output + "/" + date, ticker)) continue; 
			InvestorsData data = dataCrawler.getInvestorsData(ticker, urlMap.get(ticker));
			OutputDAO.outputToFile(data, output + "/" + date, ticker);
			System.out.println();
		}
	}
	
	public Queue<String> getStockListFromFile(String stockList) {
		Queue<String> stockQueue = new LinkedList<String>();
				
		try {
			File file = new File(stockList);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stockQueue.add(line.split("\t")[0].replace("-", "."));
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return stockQueue;
	}
}
