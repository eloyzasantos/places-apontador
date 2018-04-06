package br.com.manager.places.util;

import org.apache.commons.lang3.StringUtils;

public class StringSimilarity {
	
	public static boolean isSimilar(String x, String y) {
		
		double difference = StringUtils.getLevenshteinDistance(x, y);
		
		double score = (1 - (difference / Math.max(x.length(), y.length()))) *100;
		
		return score >= 70.00;
	}

}
