package com.candyz.candyz;

import com.candyz.candyz.data.DataAccess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by u on 16.06.2015.
 */
public class ServerInterface
{
    static int myCount = 1;

    static public List<DataAccess.Sugar> getNextChunkRow()
    {
        List<DataAccess.Sugar> aListOfSugars = new ArrayList<>();
        for(int i = 0; i < 10; i++)
        {
            DataAccess.Sugar aSugar = new DataAccess.Sugar("TestMovie-" + myCount++, "This is a test description", "");
            aListOfSugars.add(aSugar);
        }

        return aListOfSugars;
    }


}
