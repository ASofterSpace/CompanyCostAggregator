package com.asofterspace.companyCostAggregator;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


public class Category {

	String name;
	
	int datapoints = 0;
	private int employeeSum = 0;
	private double expenseSum = 0;
	
	
	public Category(String categoryName) {
		
		name = categoryName;
	}
	
	public void addDataPoint(int employees, double expenses) {
		
		employeeSum += employees;
		expenseSum += expenses;
		datapoints++;
	}
	
	public String toString() {
		return name + ": " + (expenseSum / employeeSum) + " (" + datapoints + " datapoints)";
	}
	
	public String toCsvString() {
		return name + "," + (expenseSum / employeeSum) + "," + datapoints;
	}
}
