package com.example.redis;

import org.springframework.data.geo.Point;

class Location {
	String name;
	double latitude;
	double longitude;

	public Location(String name, double latitude, double longitude) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public static Location create(String name, String latiAndLogi) {

		String[] split = latiAndLogi.split(",");
		double latit = Double.parseDouble(split[0]);
		double longi = Double.parseDouble(split[1]);
		return new Location(name, latit, longi);
	}

	public Point toPoint() {
		// 경도, 위도
		return new Point(longitude, latitude);
	}
}
