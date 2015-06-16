package com.candyz.candyz.data;

/**
 * Created by u on 16.06.2015.
 */
public class DataAccess
{

    public static class Sugar
    {
        public Sugar(String aName_in, String aDescription_in, String aURL_in)
        {
            myName = aName_in;
            myDescription = aDescription_in;
            myImageURL = aURL_in;
        }
        public String myName;
        public String myDescription;
        public String myImageURL;

    }
}
