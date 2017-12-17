package com.james.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.james.modal.InvestorsData;

import us.codecraft.xsoup.Xsoup;

public class InvestorsDataCrawler {
	
	public InvestorsData getInvestorsData (String ticker, String url) {
		try {
			InvestorsData investorsData = new InvestorsData();
			Document document = getDocument(url);
			
			getOverViewInfo(document, investorsData);
			getScoreInfo(document, investorsData);
			getStockData(document, investorsData);
			
			return investorsData;
		} catch (Exception e) {
			if (!ifInvestorsError(url)) {
				System.err.println(ticker);
				e.printStackTrace();
			}
			return null;
		}
	}

	private void getOverViewInfo(Document document, InvestorsData investorsData) {		
		investorsData.Price = document.select("#lstPrice").get(0).text();
		investorsData.PriceChange = document.select("#qtPrcChg").get(0).text();
		investorsData.PriceChangePercentage = document.select("#qtPrcPctChg").get(0).text();
		investorsData.Volume = document.select("#NormalCase").get(0).text();
		investorsData.VolumeChangePercentage = document.select(".volPricePer").get(0).text();
		
		if (document.select(".volPricePer .up").size() == 0) investorsData.VolumeChangePercentage = "-" + investorsData.VolumeChangePercentage;
		if (document.select("#qtPrcPctChg.up").size() == 0) investorsData.PriceChange = "-" + investorsData.PriceChange;
		if (document.select("#qtPrcPctChg.up").size() == 0) investorsData.PriceChangePercentage = "-" + investorsData.PriceChangePercentage;		
	}

	private void getScoreInfo(Document document, InvestorsData investorsData) {
		investorsData.Ranking = document.select("#ctl00_ctl00_secondaryContent_leftContent_GrpLeaders_currentRank").get(0).text();
		investorsData.RankingInGroup = document.select("#ctl00_ctl00_secondaryContent_leftContent_GrpLeaders_lblRankN").get(0).text();
		investorsData.Group = Xsoup.compile("//*[@id=\"grpLdrs\"]/div/div[1]/div[1]/div/div").evaluate(document).getElements().get(0).text();
		
		Elements elements = document.select(".smartRating li:nth-child(2n)");
		investorsData.CompositeRating = elements.get(0).text();
		investorsData.EPSRating = elements.get(1).text();
		investorsData.RSRating = elements.get(2).text();
		investorsData.GroupRSRating = elements.get(3).text();
		investorsData.SMRRating = elements.get(4).text();
		investorsData.AccDisRating = elements.get(5).text();
	}

	private void getStockData(Document document, InvestorsData investorsData) {
		investorsData.TodayLow = document.select(".intradayLow").get(0).text();
		investorsData.TodayHigh = document.select(".intradayHigh").get(0).text();
		investorsData.Week52RangeLow = document.select(".low52Week").get(0).text();
		investorsData.Week52RangeHigh = document.select(".high52Week").get(0).text();
		investorsData.TodayHigh = document.select(".intradayHigh").get(0).text();
		investorsData.TodayHigh = document.select(".intradayHigh").get(0).text();
		investorsData.TodayHigh = document.select(".intradayHigh").get(0).text();

		Elements stockData = document.select(".stockDate .stockContent ul");
		investorsData.PriceChgYTD = Xsoup.compile("ul/li[2]").evaluate(stockData.get(3)).getElements().get(0).text();
		investorsData.PriceChgLast4Weeks = Xsoup.compile("ul/li[2]").evaluate(stockData.get(4)).getElements().get(0).text();
		investorsData.Day50AvgVolume = Xsoup.compile("ul/li[2]").evaluate(stockData.get(5)).getElements().get(0).text();
		investorsData.SharesOutstanding = Xsoup.compile("ul/li[2]").evaluate(stockData.get(6)).getElements().get(0).text();
		investorsData.Float = Xsoup.compile("ul/li[2]").evaluate(stockData.get(7)).getElements().get(0).text();
		investorsData.IPODate = Xsoup.compile("ul/li[2]").evaluate(stockData.get(8)).getElements().get(0).text();
		investorsData.InvestmentBank = Xsoup.compile("ul/li[2]").evaluate(stockData.get(9)).getElements().get(0).text();
		investorsData.MgmtOwns = Xsoup.compile("ul/li[2]").evaluate(stockData.get(10)).getElements().get(0).text();
		investorsData.PERatio = Xsoup.compile("ul/li[2]").evaluate(stockData.get(11)).getElements().get(0).text();
		investorsData.DividendYield = Xsoup.compile("ul/li[2]").evaluate(stockData.get(12)).getElements().get(0).text();
		investorsData.Alpha = Xsoup.compile("ul/li[2]").evaluate(stockData.get(13)).getElements().get(0).text();
		investorsData.Beta = Xsoup.compile("ul/li[2]").evaluate(stockData.get(14)).getElements().get(0).text();
		
		Elements companyData = document.select(".stockDate .companyContent ul");
		investorsData.EPSDueDate = Xsoup.compile("ul/li[2]").evaluate(companyData.get(1)).getElements().get(0).text();
		investorsData.EPSChgLastQtr = Xsoup.compile("ul/li[2]").evaluate(companyData.get(2)).getElements().get(0).text();
		investorsData.Year3EPSGrowthRate = Xsoup.compile("ul/li[2]").evaluate(companyData.get(3)).getElements().get(0).text();
		investorsData.EPSEstChgCurrentYr = Xsoup.compile("ul/li[2]").evaluate(companyData.get(4)).getElements().get(0).text();
		investorsData.AnnualROE = Xsoup.compile("ul/li[2]").evaluate(companyData.get(5)).getElements().get(0).text();
		investorsData.SalesChgLastQtr = Xsoup.compile("ul/li[2]").evaluate(companyData.get(6)).getElements().get(0).text();
		investorsData.Year3SalesGrowthRate = Xsoup.compile("ul/li[2]").evaluate(companyData.get(7)).getElements().get(0).text();
		investorsData.Debt = Xsoup.compile("ul/li[2]").evaluate(companyData.get(8)).getElements().get(0).text();
		investorsData.MarketCap = Xsoup.compile("ul/li[2]").evaluate(companyData.get(9)).getElements().get(0).text();
		investorsData.ProfitMargin = Xsoup.compile("ul/li[2]").evaluate(companyData.get(10)).getElements().get(0).text();
		investorsData.Sector = Xsoup.compile("ul/li[2]").evaluate(companyData.get(11)).getElements().get(0).text();
		investorsData.IndustryGroup = Xsoup.compile("ul/li[2]").evaluate(companyData.get(12)).getElements().get(0).text();
		investorsData.IndustryGroupRank = Xsoup.compile("ul/li[2]").evaluate(companyData.get(13)).getElements().get(0).text();
	}
	
	private Document getDocument(String url) {
		Document document = null;
		int retry = 5;
		boolean doesRetry = true;
		
		while(retry != 0 && doesRetry) {
			doesRetry = false;
			try {
				document = Jsoup.connect(url).get();
			} catch (Exception e) {
				doesRetry = true;
				retry--;
			}
		}
		return document;
	}
	
	private boolean ifInvestorsError(String url) {
		try {
			Document document = getDocument(url);
			return "Error".equalsIgnoreCase(document.select(".dots").get(0).text());
		} catch (Exception e) {
			return false;
		}
	}
}
