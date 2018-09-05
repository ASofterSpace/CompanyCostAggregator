package com.asofterspace.companyCostAggregator;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


public class Country {

	String name;
	
	private TreeMap<String, Category> categories;
	
	static String aggregateBy = "nothing";
	
	
	public Country(String countryName) {
		
		name = countryName;
		
		categories = new TreeMap<>();
	}
	
	public Category getCategory(String categoryName) {
	
		if (categoryName.charAt(1) == '.') {
			categoryName = "0" + categoryName;
		}
	
		switch (aggregateBy) {
			case "aggregated by 3 cat digits":
				// aggregate by prefix (first three)
				categoryName = aggregateByPrefix3(categoryName);
				break;
		
			case "aggregated by 2 cat digits":
				// aggregate by prefix (first two)
				categoryName = aggregateByPrefix2(categoryName);
				break;
			
			case "aggregated by ranges":
				// aggregate by ranges from the screenshot
				categoryName = aggregateByPrefix2(categoryName);
				categoryName = aggregateByRanges(categoryName);
				break;
		}
		
		Category result = categories.get(categoryName);
		
		if (result == null) {
			result = new Category(categoryName);
			categories.put(categoryName, result);
		}
		
		return result;
	}
	
	private String aggregateByPrefix3(String categoryName) {
		return categoryName.substring(0, 3);
	}
	
	private String aggregateByPrefix2(String categoryName) {
		return categoryName.substring(0, 2);
	}
	
	private String aggregateByRanges(String categoryName) {

		switch (categoryName) {
			case "01":
			case "02":
			case "03":
				return "01 to 03";
			case "05":
			case "06":
			case "07":
			case "08":
			case "09":
				return "05 to 09";
			case "10":
			case "11":
			case "12":
				return "10 to 12";
			case "13":
			case "14":
			case "15":
				return "13 to 15";
			case "16":
			case "17":
			case "18":
				return "16 to 18";
			case "22":
			case "23":
				return "22 + 23";
			case "24":
			case "25":
				return "24 + 25";
			case "29":
			case "30":
				return "29 + 30";
			case "31":
			case "32":
			case "33":
				return "31 to 33";
			case "36":
			case "37":
			case "38":
			case "39":
				return "36 to 39";
			case "41":
			case "42":
			case "43":
				return "41 to 43";
			case "45":
			case "46":
			case "47":
				return "45 to 47";
			case "49":
			case "50":
			case "51":
			case "52":
			case "53":
				return "49 to 53";
			case "55":
			case "56":
				return "55 + 56";
			case "58":
			case "59":
			case "60":
				return "58 to 60";
			case "62":
			case "63":
				return "62 + 63";
			case "64":
			case "65":
			case "66":
				return "64 to 66";
			case "69":
			case "70":
			case "71":
				return "69 to 71";
			case "73":
			case "74":
			case "75":
				return "73 to 75";
			case "77":
			case "78":
			case "79":
			case "80":
			case "81":
			case "82":
				return "77 to 82";
			case "87":
			case "88":
				return "87 + 88";
			case "90":
			case "91":
			case "92":
			case "93":
				return "90 to 93";
			case "94":
			case "95":
			case "96":
				return "94 to 96";
			case "97":
			case "98":
				return "97 + 98";
		}
		
		return categoryName;
	}
	
	public void prune() {
	
		TreeMap<String, Category> keepCategories = new TreeMap<>();

		for (Category category : categories.values()) {
			if (category.datapoints >= 10) {
				keepCategories.put(category.name, category);
			}
		}
		
		categories = keepCategories;
	}
	
	public boolean isEmpty() {
		return categories.size() <= 0;
	}
	
	public String toString() {
	
		StringBuilder cats = new StringBuilder();
		
		cats.append(name);
		cats.append("\r\n");
		
		for (Category category : categories.values()) {
			cats.append("  ");
			cats.append(category);
			cats.append("\r\n");
		}
	
		return cats.toString();
	}
	
	public String toCsvString() {
	
		StringBuilder cats = new StringBuilder();
		
		for (Category category : categories.values()) {
			cats.append(name);
			cats.append(",");
			cats.append(category.toCsvString());
			cats.append("\r\n");
		}
	
		return cats.toString();
	}
}
