package com.james.runner;

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
import com.james.dao.ConsoleDAO;
import com.james.dao.FileDAO;
import com.james.dao.FileUtil;
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
			case "single-thread": runner.getInvestorsData(input.get("stock-list"), input.get("date"), input.get("output")); break;
			case "multithread": runner.getInvestorsData(input.get("stock-list"), input.get("date"), input.get("output"), input.get("thread")); break;
			default: break;
		}

		Date date2 = new Date();
		System.out.println(date2.getTime() - date1.getTime());

	}
	
	public void getInvestorsData(String stockList, String date, String output) throws Exception {
		Queue<String> queue = FileUtil.getStockListFromFile(stockList);
		
		URLCrawler urlCrawler = new URLCrawler();
		Map<String, String> urlMap= urlCrawler.getQuoteResults(new LinkedList<String>(queue));
		
		while (!queue.isEmpty()) {
			String ticker  = queue.poll();
			InvestorsRunner runner = new InvestorsRunner(ticker, date, output, urlMap.get(ticker));
			runner.run();
		}
	}
	
	public void getInvestorsData(String stockList,  String date, String output, String threadNumber) throws Exception {
		Queue<String> queue = FileUtil.getStockListFromFile(stockList);
		
		URLCrawler urlCrawler = new URLCrawler();
		Map<String, String> urlMap= urlCrawler.getQuoteResults(new LinkedList<String>(queue));
		
		ExecutorService executor = Executors.newFixedThreadPool(Integer.parseInt(threadNumber));

		while (!queue.isEmpty()) {
			String ticker  = queue.poll();
			executor.execute(new InvestorsRunner(ticker, date, output, urlMap.get(ticker)));
		}

		executor.shutdown();
		executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
	}
	
	class InvestorsRunner implements Runnable {
		
		private String ticker; 
		private String finalPath;
		private String url;
		
		public InvestorsRunner(String ticker, String date, String output, String url) {
			this.ticker = ticker;
			this.finalPath = output + "/" + date;
			this.url = url;
		}
		
		@Override
		public void run() {
			if(FileUtil.isFileExist(finalPath + "/" + ticker + ".txt")) return; 
			InvestorsDataCrawler dataCrawler = new InvestorsDataCrawler();
			InvestorsData data = dataCrawler.getInvestorsData(ticker, url);
			ConsoleDAO.output(data, true);
			FileDAO.output(data, finalPath);
		}
		
	}
}
