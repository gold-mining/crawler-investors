package com.james.dao;

import java.io.File;
import java.io.PrintWriter;

import com.james.modal.InvestorsData;

public class OutputDAO {
	public static void outputToFile(InvestorsData data, String filePath, String ticker) {
		PrintWriter writer = createWirter(filePath, ticker);

		writer.println(ticker);
		writer.println("\t" + InvestorsData.getLable());
		writer.println("\t" + data);
		
		writer.close();
	}

	public static void outputToConsole(InvestorsData data, String ticker) {
		System.out.println(ticker);
		System.out.println("\t" + InvestorsData.getLable());
		System.out.println("\t" + data);
	}
	
	public static PrintWriter createWirter(String filePath, String ticker) {
		try {
			new File(filePath).mkdirs();
			PrintWriter writer = new PrintWriter(filePath + "/" + ticker + ".txt", "UTF-8");
			return writer;
		} catch (Exception e) {
			return  null;
		}
	}
	
	public static boolean isFileExist(String filePath, String ticker) {
		File file = new File(filePath + "/" + ticker + ".txt");
		return file.exists() && file.length() != 0;
	}
}
