package Utils;

import java.text.DecimalFormat;

public class Util {
	public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		DecimalFormat df = new DecimalFormat("0.00"); 
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit.equals("K")) {
			dist = dist * 1.609344;
		} else if (unit.equals("N")) {
			dist = dist * 0.8684;
		}
		//System.out.println( lat1+" "+lon1+" "+lat2+" "+lon2);
		if(Double.isNaN(dist))
			return 0.0;
		return (Double.parseDouble(df.format(dist)));
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::  This function converts decimal degrees to radians             :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	public static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::  This function converts radians to decimal degrees             :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	public static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
}