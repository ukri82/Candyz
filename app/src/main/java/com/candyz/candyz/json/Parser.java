package com.candyz.candyz.json;

import com.candyz.candyz.data.DataAccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Windows on 02-03-2015.
 */
public class Parser {
    
    private static final String NA = "N.A";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESC = "description";
    private static final String KEY_URL = "links";
    private static final String KEY_SOURCE = "feed_source";
    private static final String KEY_PHOTO_URL = "photo_url";
    private static final String KEY_DATE_TIME = "pub_date";
    private static final String KEY_CATEGORY = "category";

    public static ArrayList<DataAccess.Sugar> parseMoviesJSON(JSONObject response) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        ArrayList<DataAccess.Sugar> listNews = new ArrayList<>();
        if (response != null && response.length() > 0) {
            try {
                JSONArray arraySugars = response.getJSONArray("FeedData");
                for (int i = 0; i < arraySugars.length(); i++) {
                    //long id = -1;
                    String title = NA;
                    String desc = NA;
                    String source = NA;
                    String aURL = NA;
                    String aPhotoURL = NA;
                    String aDateTime = NA;
                    String aCategory = NA;


                    JSONObject currentSugar = arraySugars.getJSONObject(i);


                    //get the title of the current news item
                    if (Utils.contains(currentSugar, KEY_TITLE)) {
                        title = currentSugar.getString(KEY_TITLE);
                    }

                    //get the description of the current news item
                    if (Utils.contains(currentSugar, KEY_DESC)) {
                        desc = currentSugar.getString(KEY_DESC);
                    }

                    //get the source of the current news item
                    if (Utils.contains(currentSugar, KEY_SOURCE)) {
                        source = currentSugar.getString(KEY_SOURCE);
                    }

                    //get the news URL
                    if (Utils.contains(currentSugar, KEY_URL)) {
                        JSONArray aLinks = currentSugar.getJSONArray(KEY_URL);
                        if(aLinks.length() > 0)
                        {
                            aURL = aLinks.getString(0);
                        }
                    }

                    //get the news photo URL
                    if (Utils.contains(currentSugar, KEY_PHOTO_URL)) {
                        aPhotoURL = currentSugar.getString(KEY_PHOTO_URL);
                    }

                    //get the news photo URL
                    if (Utils.contains(currentSugar, KEY_CATEGORY)) {
                        aCategory = currentSugar.getString(KEY_CATEGORY);
                    }



                    //get the datetime of the current news item
                    if (Utils.contains(currentSugar, KEY_DATE_TIME)) {
                        aDateTime = currentSugar.getString(KEY_DATE_TIME);
                    }

                    Date date = null;
                    try {
                        date = dateFormat.parse(aDateTime);
                    } catch (ParseException e) {
                        //a parse exception generated here will store null in the release date, be sure to handle it
                    }

                    DataAccess.Sugar aSugar = new DataAccess.Sugar(title, desc, aURL);

                    //L.t(getActivity(), aSugar + "");
                    //if (id != -1 && !title.equals(NA)) {
                        listNews.add(aSugar);
                    //}
                }

            } catch (JSONException e) {

            }
//                L.t(getActivity(), listMovies.size() + " rows fetched");
        }
        return listNews;
    }


}
