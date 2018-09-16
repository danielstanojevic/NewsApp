package com.example.android.newsapp;

//single article
public class Article {

    //Headline of article
    private String mHeadline;

    //Category of article
    private String mCategory;

    //Publish time of article
    private long mTimeInMilliseconds;

    //Url of article
    private String mUrl;

    /**
     * Constructs a new {@link Article} object.
     *
     * @param headline           is the magnitude (size) of the earthquake
     * @param category           is the city category of the earthquake
     * @param timeInMilliseconds is the time in milliseconds (from the Epoch) when the
     *                           earthquake happened
     * @param url                is the link to more details about the earthquake
     */
    Article(String headline, String category, long timeInMilliseconds, String url) {
        mHeadline = headline;
        mCategory = category;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;
    }

    public String getHeadline() {
        return mHeadline;
    }

    public String getCategory() {
        return mCategory;
    }

    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getUrl() {
        return mUrl;
    }

}


