package com.example.android.newsv1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News>{
    private static final String LOCATION_SEPARATOR = "T";

    public NewsAdapter(Context context , List<News> news){
        super(context , 0 , news );
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_item_file, parent, false);
        }
        News currentNews = getItem(position);
        TextView title = (TextView)listItemView.findViewById(R.id.title_text_view);
        String webTitle = currentNews.getWebTitle();
        title.setText(webTitle);
        TextView sectionTitle = (TextView)listItemView.findViewById(R.id.section_name_text_view);
        String sectionName = currentNews.getSectionName();
        sectionTitle.setText(sectionName);
        String dateobject = currentNews.getTimeInMilliseconds() ;
        String dateOfPublication ;
        if(dateobject.contains(LOCATION_SEPARATOR)){
            String[] parts = dateobject.split(LOCATION_SEPARATOR);
            dateOfPublication = parts[0];
        } else {
            dateOfPublication = dateobject;
        }
        TextView publishedOnDate = (TextView)listItemView.findViewById(R.id.published_on_text_view);
        publishedOnDate.setText(dateOfPublication);
        TextView authorName_text_view = (TextView)listItemView.findViewById(R.id.author_name_text_view);
        String authorName = currentNews.getmAuthorName();
        authorName_text_view.setText(authorName);
        return listItemView ;
    }

}
