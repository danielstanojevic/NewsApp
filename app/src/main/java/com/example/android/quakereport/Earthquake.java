package com.example.android.quakereport;

//single earthquake
public class Earthquake {

    //Magnitude of earthquake
    private String mMagnitude;

    //Location of earthquake
    private String mLocation;

    //Date of earthquake
    private String mDate;

    //Time of earthquake
    private long mTimeInMilliseconds;

    /**
     * Constructs a new {@link Earthquake} object.
     *
     * @param magnitude is the magnitude (size) of the earthquake
     * @param location is the city location of the earthquake
     * @param timeInMilliseconds is the time in milliseconds (from the Epoch) when the
     *  earthquake happened
     */
    public Earthquake(String magnitude, String location, long timeInMilliseconds) {
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
    }

    public String getMagnitude() {
        return mMagnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getDate() {
        return mDate;
    }

    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }


}


