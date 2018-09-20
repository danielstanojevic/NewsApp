package com.example.android.newsapp;

//single article
public class Article {

    //Author of article
    private String mAuthor;

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
     * @param author             is the
     * @param headline           is the headline of the article
     * @param category           is the category of the article
     * @param timeInMilliseconds is the time in milliseconds when the
     *                           article was published
     * @param url                is the link to the article
     */
    Article(String author, String headline, String category, long timeInMilliseconds, String url) {
        mAuthor = author;
        mHeadline = headline;
        mCategory = category;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;
    }

    public String getAuthor() {
        return mAuthor;
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


