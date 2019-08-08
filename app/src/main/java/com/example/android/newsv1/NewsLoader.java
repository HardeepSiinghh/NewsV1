package com.example.android.newsv1;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String mUrl;
    public NewsLoader(Context context , String url){
        super(context);
        mUrl = url ;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<News> loadInBackground() {
        if(mUrl== null){
            return null;
        }
        List<News> news = QueryNews.fetchNewsData(mUrl);
        return news ;
    }
}
