package com.example.android.newsapp;

//single article
public class Article {

    //Magnitude of earthquake
    private double mMagnitude;

    //Category of earthquake
    private String mLocation;

    //Publish date of article
    private String mDate;

    //Publish time of article
    private long mTimeInMilliseconds;

    //Url of article
    private String mUrl;



    /**
     * Constructs a new {@link Article} object.
     *
     * @param magnitude is the magnitude (size) of the earthquake
     * @param location is the city location of the earthquake
     * @param timeInMilliseconds is the time in milliseconds (from the Epoch) when the
     *  earthquake happened
     */
    public Article(double magnitude, String location, long timeInMilliseconds, String url) {
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;
    }

    public double getMagnitude() {
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

    public String getUrl() {
        return mUrl;
    }

}


