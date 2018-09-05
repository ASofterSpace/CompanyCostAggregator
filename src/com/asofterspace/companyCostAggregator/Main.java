package com.asofterspace.companyCostAggregator;

import com.asofterspace.toolbox.io.CsvFile;
import com.asofterspace.toolbox.io.File;
import com.asofterspace.toolbox.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class Main {
	
	public final static String PROGRAM_TITLE = "CompanyCostAggregator";
	public final static String VERSION_NUMBER = "0.0.0.1(" + Utils.TOOLBOX_VERSION_NUMBER + ")";
	public final static String VERSION_DATE = "3. September 2018";
	
	public static void main(String[] args) {
	
		compute("non-aggregated");
		compute("aggregated by 2 cat digits");
		compute("aggregated by 3 cat digits");
		compute("aggregated by ranges");
	
	}
	
	private static void compute(String mode) {
	
		Country.aggregateBy = mode;
		
		CsvFile input = new CsvFile("D:/prog/asofterspace/CompanyCostAggregator/data/Arbeitskosten_Schaetzung_Blatt_1.csv");
		
		TreeMap<String, Country> countries = new TreeMap<>();
		
		while (true) {
			List<String> data = input.getContentLineInColumns();
			
			if (data == null) {
				break;
			}
			
			try {
				
				String countryData = data.get(14);
				if (countryData.equals("#N/A") || countryData.equals("#VALUE!") || countryData.equals("0")) {
					continue;
				}
				
				String categoryData = data.get(3);
				if (categoryData.equals("#N/A") || categoryData.equals("#VALUE!") || categoryData.equals("0")) {
					continue;
				}
				
				String employeesStrData = data.get(8);
				if (employeesStrData.equals("#N/A") || employeesStrData.equals("#VALUE!") || employeesStrData.equals("0")) {
					continue;
				}
				Integer employeesData = Integer.parseInt(employeesStrData.replace(",", "").replace(".0", ""));
				
				String expensesStrData = data.get(9);
				if (expensesStrData.equals("#N/A") || expensesStrData.equals("#VALUE!") || expensesStrData.equals("0")) {
					continue;
				}
				Double expensesData = Double.parseDouble(expensesStrData.replace(",", ""));
				
				Country country = countries.get(countryData);
				
				if (country == null) {
					country = new Country(countryData);
					countries.put(countryData, country);
				}
				
				Category category = country.getCategory(categoryData);
				category.addDataPoint(employeesData, expensesData);

			} catch (Exception e) {
				// ignore malformed lines
				continue;
			}
		}
		
		// prune - take out all categories that contain less than 10 datapoints
		
		TreeMap<String, Country> keepCountries = new TreeMap<>();

		for (Country country : countries.values()) {
			country.prune();
			if (!country.isEmpty()) {
				keepCountries.put(country.name, country);
			}
		}
		
		countries = keepCountries;
		
		// save results as TXT
		StringBuilder result = new StringBuilder();
		
		for (Country country : countries.values()) {
			result.append(country.toString());
			result.append("\r\n");
		}
		
		File output = new File("D:/prog/asofterspace/CompanyCostAggregator/data/result " + mode + ".txt");
		output.setContent(result);
		output.save();
		
		// save results as CSV
		StringBuilder csvResult = new StringBuilder();
		
		csvResult.append("county,category,expenses per employee,datapoints");
		csvResult.append("\r\n");
		
		for (Country country : countries.values()) {
			csvResult.append(country.toCsvString());
		}
		
		File csvOutput = new File("D:/prog/asofterspace/CompanyCostAggregator/data/result " + mode + ".csv");
		csvOutput.setContent(csvResult);
		csvOutput.save();
	}
	
}
