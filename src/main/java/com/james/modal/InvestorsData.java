package com.james.modal;

public class InvestorsData {
	public String ticker;
	
	public String Price;
	public String PriceChange;
	public String PriceChangePercentage;
	public String Volume;
	public String VolumeChangePercentage;
	
	public String Ranking;
	public String RankingInGroup;
	public String Group;
	
	public String CompositeRating;
	public String EPSRating;
	public String RSRating;
	public String GroupRSRating;
	public String SMRRating;
	public String AccDisRating;
	
	public String TodayLow;
	public String TodayHigh;
	public String Week52RangeLow;
	public String Week52RangeHigh;
	public String PriceChgYTD;
	public String PriceChgLast4Weeks;
	public String Day50AvgVolume;
	public String SharesOutstanding;
	public String Float;
	public String IPODate;
	public String InvestmentBank;
	public String MgmtOwns;
	public String PERatio;
	public String DividendYield;
	public String Alpha;
	public String Beta;
	
	public String EPSDueDate;
	public String EPSChgLastQtr;
	public String Year3EPSGrowthRate;
	public String EPSEstChgCurrentYr;
	public String AnnualROE;
	public String SalesChgLastQtr;
	public String Year3SalesGrowthRate;
	public String Debt;
	public String MarketCap;
	public String ProfitMargin;
	public String Sector;
	public String IndustryGroup;
	public String IndustryGroupRank;
	
	@Override
	public String toString() {
		String output = "";
		output += ticker + "\n";
		output += "\t" + "Price" + "\t" + "PriceChange" + "\t" + "PriceChangePercentage" + "\t" + "Volume" + "\t" + "VolumeChangePercentage" + "\t" + "Ranking" + "\t" + "RankingInGroup" + "\t" + "Group" + "\t" + "CompositeRating" + "\t" + "EPSRating" + "\t" + "RSRating" + "\t" + "GroupRSRating" + "\t" + "SMRRating" + "\t" + "AccDisRating" + "\t" + "TodayLow" + "\t" + "TodayHigh" + "\t" + "Week52RangeLow" + "\t" + "Week52RangeHigh" + "\t" + "PriceChgYTD" + "\t" + "PriceChgLast4Weeks" + "\t" + "Day50AvgVolume" + "\t" + "SharesOutstanding" + "\t" + "Float" + "\t" + "IPODate" + "\t" + "InvestmentBank" + "\t" + "MgmtOwns" + "\t" + "PERatio" + "\t" + "DividendYield" + "\t" + "Alpha" + "\t" + "Beta" + "\t" + "EPSDueDate" + "\t" + "EPSChgLastQtr" + "\t" + "Year3EPSGrowthRate" + "\t" + "EPSEstChgCurrentYr" + "\t" + "AnnualROE" + "\t" + "SalesChgLastQtr" + "\t" + "Year3SalesGrowthRate" + "\t" + "Debt" + "\t" + "MarketCap" + "\t" + "ProfitMargin" + "\t" + "Sector" + "\t" + "IndustryGroup" + "\t" + "IndustryGroupRank" + "\n";
		output += "\t" + Price + "\t" + PriceChange + "\t" + PriceChangePercentage + "\t" + Volume + "\t" + VolumeChangePercentage + "\t" + Ranking + "\t" + RankingInGroup + "\t" + Group + "\t" + CompositeRating + "\t" + EPSRating + "\t" + RSRating + "\t" + GroupRSRating + "\t" + SMRRating + "\t" + AccDisRating + "\t" + TodayLow + "\t" + TodayHigh + "\t" + Week52RangeLow + "\t" + Week52RangeHigh + "\t" + PriceChgYTD + "\t" + PriceChgLast4Weeks + "\t" + Day50AvgVolume + "\t" + SharesOutstanding + "\t" + Float + "\t" + IPODate + "\t" + InvestmentBank + "\t" + MgmtOwns + "\t" + PERatio + "\t" + DividendYield + "\t" + Alpha + "\t" + Beta + "\t" + EPSDueDate + "\t" + EPSChgLastQtr + "\t" + Year3EPSGrowthRate + "\t" + EPSEstChgCurrentYr + "\t" + AnnualROE + "\t" + SalesChgLastQtr + "\t" + Year3SalesGrowthRate + "\t" + Debt + "\t" + MarketCap + "\t" + ProfitMargin + "\t" + Sector + "\t" + IndustryGroup + "\t" + IndustryGroupRank + "\n";
		return output;
	}
	
	public static String getLable() {
		return "Price" + "\t" + "PriceChange" + "\t" + "PriceChangePercentage" + "\t" + "Volume" + "\t" + "VolumeChangePercentage" + "\t" + "Ranking" + "\t" + "RankingInGroup" + "\t" + "Group" + "\t" + "CompositeRating" + "\t" + "EPSRating" + "\t" + "RSRating" + "\t" + "GroupRSRating" + "\t" + "SMRRating" + "\t" + "AccDisRating" + "\t" + "TodayLow" + "\t" + "TodayHigh" + "\t" + "Week52RangeLow" + "\t" + "Week52RangeHigh" + "\t" + "PriceChgYTD" + "\t" + "PriceChgLast4Weeks" + "\t" + "Day50AvgVolume" + "\t" + "SharesOutstanding" + "\t" + "Float" + "\t" + "IPODate" + "\t" + "InvestmentBank" + "\t" + "MgmtOwns" + "\t" + "PERatio" + "\t" + "DividendYield" + "\t" + "Alpha" + "\t" + "Beta" + "\t" + "EPSDueDate" + "\t" + "EPSChgLastQtr" + "\t" + "Year3EPSGrowthRate" + "\t" + "EPSEstChgCurrentYr" + "\t" + "AnnualROE" + "\t" + "SalesChgLastQtr" + "\t" + "Year3SalesGrowthRate" + "\t" + "Debt" + "\t" + "MarketCap" + "\t" + "ProfitMargin" + "\t" + "Sector" + "\t" + "IndustryGroup" + "\t" + "IndustryGroupRank";
	}
}
