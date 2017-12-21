package com.james.dao;

import com.james.modal.InvestorsData;

public class ConsoleDAO {
	public static void output(InvestorsData data, boolean brief) {
		if (brief) System.out.println(data.ticker); 
		else System.out.println(data);
	}
}
