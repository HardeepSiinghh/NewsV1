package com.example.android.newsv1;

public class News {
    private String mSectionName ;
    private String mWebTitle ;
    private String mWebUrl ;
    private String mDate;
    private String mAuthorName ;

    public News(String SectionName , String WebTitle , String WebUrl , String TimeInMilliseconds, String AuthorName){
        mSectionName = SectionName ;
        mWebTitle = WebTitle ;
        mWebUrl = WebUrl ;
        mDate = TimeInMilliseconds ;
        mAuthorName= AuthorName;

    }

    public String getmAuthorName() {
        return mAuthorName;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getWebTitle() {
        return mWebTitle;
    }

    public String getWebUrl() {
        return mWebUrl;
    }

    public String getTimeInMilliseconds() {
        return mDate;
    }
}
