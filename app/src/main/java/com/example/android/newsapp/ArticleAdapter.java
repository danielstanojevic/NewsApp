package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;


public class ArticleAdapter extends ArrayAdapter<Article> {
    private static final String HEADLINE_SEPARATOR = " | ";

    //constructor
    ArticleAdapter(Context context, List<Article> articles) {
        super(context, 0, articles);
    }

    //returns list item view of articles
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.article_list_item, parent, false);
        }

        //find article at the current position
        Article currentArticle = getItem(position);

        assert currentArticle != null;

        String authorHeader;
        String article_author;
        String headline = currentArticle.getHeadline();
        String category = currentArticle.getCategory();
        article_author = currentArticle.getAuthor();
        authorHeader = article_author;

        //find textView with viewid category
        TextView locationView = (TextView) listItemView.findViewById(R.id.category);

        //display the category of the current article in that textview
        locationView.setText(category);

        //find textView with viewid headline
        TextView primaryView = (TextView) listItemView.findViewById(R.id.headline);

        //display the primary headline of the current article in that textview
        primaryView.setText(headline);

        //find textView with viewid author
        TextView authorView = (TextView) listItemView.findViewById((R.id.author));

        //display the author of the current article in that textview
        authorView.setText(authorHeader);

        // Create a new Date object from the time in milliseconds of the article publication
        Date dateObject = new Date(currentArticle.getTimeInMilliseconds());

        //find textView with viewid date
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);

        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);

        // Display the date of the current article in that TextView
        dateView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);

        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);

        // Display the time of the current article in that TextView
        timeView.setText(formattedTime);

        return listItemView;
    }


    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
