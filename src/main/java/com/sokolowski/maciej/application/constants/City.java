package com.sokolowski.maciej.application.constants;

public enum City {
	    London("London", 51.509865, -0.118092);
	 
	    private final String name;
	    private final double latitude;
	    private final double longitude;
	 
	    City(String name, double latitude, double longitude) {
	        this.name = name;
	        this.latitude = latitude;
	        this.longitude = longitude;
	    }
	 
	    public String getName() {
	        return name;
	    }
	 
	    public Double getLatitude() {
	        return latitude;
	    }
	    
	    public Double getLongitude() {
	        return longitude;
	    }
}
