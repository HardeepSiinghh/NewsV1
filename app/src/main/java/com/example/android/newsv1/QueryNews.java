package com.example.android.newsv1;

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
import java.util.ArrayList;
import java.util.List;

public  final class QueryNews {
    private static final String LOG_TAG = QueryNews.class.getSimpleName();
    private QueryNews(){}

    public static List<News> fetchNewsData(String requestUrl){
        URL url = createURL(requestUrl);
        String jsonResponse = null ;
        try{
            jsonResponse = makeHttpRequest(url);
        }
        catch(IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<News> news = extractFromJSON(jsonResponse);
        return news ;
    }
    private static URL createURL(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(20000 /* milliseconds */);
            urlConnection.setConnectTimeout(25000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
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
    private static List<News> extractFromJSON(String newsJSON){
        if (TextUtils.isEmpty(newsJSON)){
            return null;
        }
        List<News> news = new ArrayList<>();
        try {

            JSONObject baseJSONResponse = new JSONObject(newsJSON);
            JSONObject newsArrayObject = baseJSONResponse.getJSONObject("response");
            JSONArray newsArray = newsArrayObject.getJSONArray("results");
            for (int i = 0 ; i<newsArray.length(); i++){

                JSONObject currentNews = newsArray.getJSONObject(i);
                String sectionName = currentNews.getString("sectionName");
                String webTitle = currentNews.getString("webTitle");
                String webUrl = currentNews.getString("webUrl");
                String time = currentNews.getString("webPublicationDate");
                JSONArray authorTagsArray = currentNews.getJSONArray("tags");

                String contributorName="";
                for (int j = 0; j<authorTagsArray.length(); j++){
                    JSONObject currentNewsAuthorName = authorTagsArray.getJSONObject(j);
                    contributorName+= currentNewsAuthorName.getString("webTitle")+" | ";

                }
                News news1 = new News(sectionName, webTitle, webUrl, time ,contributorName );
                news.add(news1);



            }
        }
        catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return  news ;
    }
}
