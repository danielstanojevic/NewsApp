package com.example.android.newsapp;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Helper methods related to requesting and receiving article data from news api.
 */
public final class QueryUtils {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = ArticleActivity.class.getSimpleName();
    //sample response for news query reference
    //private static final String SAMPLE_RESPONSE = "{\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":2547,\"startIndex\":1,\"pageSize\":10,\"currentPage\":1,\"pages\":255,\"orderBy\":\"relevance\",\"results\":[{\"id\":\"technology/2018/aug/29/coding-algorithms-frankenalgos-program-danger\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2018-08-30T05:00:48Z\",\"webTitle\":\"Franken-algorithms: the deadly consequences of unpredictable code\",\"webUrl\":\"https://www.theguardian.com/technology/2018/aug/29/coding-algorithms-frankenalgos-program-danger\",\"apiUrl\":\"https://content.guardianapis.com/technology/2018/aug/29/coding-algorithms-frankenalgos-program-danger\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"commentisfree/2018/jul/26/tech-healthcare-ethics-artifical-intelligence-doctors-patients\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2018-07-26T11:48:00Z\",\"webTitle\":\"Algorithms may outperform doctors, but theyâ€™re no healthcare panacea | Ivana Bartoletti\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2018/jul/26/tech-healthcare-ethics-artifical-intelligence-doctors-patients\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2018/jul/26/tech-healthcare-ethics-artifical-intelligence-doctors-patients\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"commentisfree/2018/may/13/we-created-poverty-algorithms-wont-make-that-go-away\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2018-05-13T10:00:04Z\",\"webTitle\":\"We created poverty. Algorithms won't make that go away | Virginia Eubanks\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2018/may/13/we-created-poverty-algorithms-wont-make-that-go-away\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2018/may/13/we-created-poverty-algorithms-wont-make-that-go-away\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"world/commentisfree/2018/jul/12/algorithm-privacy-data-surveillance\",\"type\":\"article\",\"sectionId\":\"world\",\"sectionName\":\"World news\",\"webPublicationDate\":\"2018-07-12T09:00:37Z\",\"webTitle\":\"Algorithms are taking over â€“ and woe betide anyone they class as a 'deadbeat' | Zoe Williams\",\"webUrl\":\"https://www.theguardian.com/world/commentisfree/2018/jul/12/algorithm-privacy-data-surveillance\",\"apiUrl\":\"https://content.guardianapis.com/world/commentisfree/2018/jul/12/algorithm-privacy-data-surveillance\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"books/2018/jul/24/ada-lovelace-first-edition-pioneering-algorithm-program\",\"type\":\"article\",\"sectionId\":\"books\",\"sectionName\":\"Books\",\"webPublicationDate\":\"2018-07-24T13:23:22Z\",\"webTitle\":\"First edition of Ada Lovelace's pioneering algorithm sold for Â£95,000\",\"webUrl\":\"https://www.theguardian.com/books/2018/jul/24/ada-lovelace-first-edition-pioneering-algorithm-program\",\"apiUrl\":\"https://content.guardianapis.com/books/2018/jul/24/ada-lovelace-first-edition-pioneering-algorithm-program\",\"isHosted\":false,\"pillarId\":\"pillar/arts\",\"pillarName\":\"Arts\"},{\"id\":\"commentisfree/2018/apr/04/algorithms-powerful-europe-response-social-media\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2018-04-04T05:00:17Z\",\"webTitle\":\"Algorithms have become so powerful we need a robust, Europe-wide response | Marietje Schaake\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2018/apr/04/algorithms-powerful-europe-response-social-media\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2018/apr/04/algorithms-powerful-europe-response-social-media\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"science/2017/nov/03/from-health-to-crimefighting-ai-has-brought-us-to-the-threshold-of-a-new-era\",\"type\":\"article\",\"sectionId\":\"science\",\"sectionName\":\"Science\",\"webPublicationDate\":\"2017-11-03T11:00:22Z\",\"webTitle\":\"The algorithms that are already changing your life\",\"webUrl\":\"https://www.theguardian.com/science/2017/nov/03/from-health-to-crimefighting-ai-has-brought-us-to-the-threshold-of-a-new-era\",\"apiUrl\":\"https://content.guardianapis.com/science/2017/nov/03/from-health-to-crimefighting-ai-has-brought-us-to-the-threshold-of-a-new-era\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"commentisfree/2018/mar/05/algorithms-rate-credit-scores-finances-data\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2018-03-05T06:00:08Z\",\"webTitle\":\"The tyranny of algorithms is part of our lives: soon they could rate everything we do | John Harris\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2018/mar/05/algorithms-rate-credit-scores-finances-data\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2018/mar/05/algorithms-rate-credit-scores-finances-data\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"technology/2018/may/15/twitter-ranking-algorithm-change-trolling-harassment-abuse\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2018-05-15T16:00:23Z\",\"webTitle\":\"Twitter announces global change to algorithm in effort to tackle harassment\",\"webUrl\":\"https://www.theguardian.com/technology/2018/may/15/twitter-ranking-algorithm-change-trolling-harassment-abuse\",\"apiUrl\":\"https://content.guardianapis.com/technology/2018/may/15/twitter-ranking-algorithm-change-trolling-harassment-abuse\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"commentisfree/2018/aug/05/magical-thinking-about-machine-learning-will-not-bring-artificial-intelligence-any-closer\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2018-08-05T06:00:03Z\",\"webTitle\":\"Magical thinking about machine learning wonâ€™t bring the reality of AI any closer | John Naughton\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2018/aug/05/magical-thinking-about-machine-learning-will-not-bring-artificial-intelligence-any-closer\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2018/aug/05/magical-thinking-about-machine-learning-will-not-bring-artificial-intelligence-any-closer\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"}]}}";

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static final int SUCCESS_CODE = 200;

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == SUCCESS_CODE) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                //jsonResponse = String.valueOf(R.string.invalid_response_code_message);
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode() + " URL: " + url);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the article JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    //Helper method to convert date time stamp from ISO 8601 to Unix time
    public static Date fromISO8601UTC(String dateStr) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(tz);

        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            Log.e("QueryUtils", "Problem converting date time from ISO8601 to Unix", e);
        }


        return null;
    }

    /**
     * Query the news api and return a list of {@link Article} objects.
     */
    public static List<Article> fetchArticleData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        //jsonResponse = SAMPLE_RESPONSE; //uncomment this line as well as var declaration at top and comment try block above to use sample query

        // Extract relevant fields from the JSON response and create a list of {@link Article}s
        List<Article> articles = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Article}s
        return articles;
    }

    /**
     * Return a list of {@link Article} objects that has been built up from
     * parsing a JSON response.
     */
    /**
     * Return a list of {@link Article} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Article> extractFeatureFromJson(String articleJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(articleJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding articles to
        List<Article> articles = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(articleJSON).getJSONObject("response");

            // Extract the JSONArray associated with the key called "results",
            // which represents a list of articles.
            JSONArray articleArray = baseJsonResponse.getJSONArray("results");
            Log.e(LOG_TAG, "articleArray results: " + articleArray);
            // For each article in the articleArray, create an {@link Article} object
            for (int i = 0; i < articleArray.length(); i++) {

                // Get a single article at position i within the list of articles
                JSONObject currentArticle = articleArray.getJSONObject(i);

                // Extract the value for the key called "webTitle"
                String headline = currentArticle.getString("webTitle");
                Log.e(LOG_TAG, "Headline: " + headline);
                // Extract the value for the key called "sectionName"
                String category = currentArticle.getString("sectionName");
                Log.e(LOG_TAG, "Category: " + category);
                // Extract the value for the key called "webPublicaitonDate"
                String published = currentArticle.getString("webPublicationDate");
                Log.e(LOG_TAG, "Publication Date: " + published);
                long time = 0;
                if (published != null) {
                    time = fromISO8601UTC(published).getTime();
                }
                Log.e(LOG_TAG, "Time: " + time);
                // Extract the value for the key called "url"
                String url = currentArticle.getString("webUrl");
                Log.e(LOG_TAG, "url: " + url);
                // Create a new {@link Article} object with the headline, category, time,
                // and url from the JSON response.
                Article article = new Article(author, headline, category, time, url);

                // Add the new {@link Article} to the list of articles.
                articles.add(article);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the article JSON results", e);
        }

        // Return the list of articles
        return articles;
    }

}