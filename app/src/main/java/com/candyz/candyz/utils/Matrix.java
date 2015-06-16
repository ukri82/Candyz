package com.candyz.candyz.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Created by u on 16.06.2015.
 */
public class Matrix<DataType>
{
    private int myRows;
    private int myColumns;
    private DataType[] myData;

    Class<?> myClassObj;

    /**
     * Allocate a matrix with the indicated initial dimensions.
     * @param cols The column (horizontal or x) dimension for the matrix
     * @param rows The row (vertical or y) dimension for the matrix
     */
    public Matrix(Class<?> clazz, int rows, int cols)
    {
        myClassObj = clazz;
        this.myRows = rows;
        this.myColumns = cols;
        myData = (DataType[])Array.newInstance(myClassObj, this.myRows * this.myColumns);
    }

    /**
     * Calculates the index of the indicated row and column for
     * a matrix with the indicated width. This uses row-major ordering
     * of the matrix elements.
     * <p>
     * Note that this is a static method so that it can be used independent
     * of any particular data instance.
     * @param col The column index of the desired element
     * @param row The row index of the desired element
     */
    private int getIndex(int row, int col, int width)
    {
        return row * width + col;
    }

    public DataType get(int row, int col)
    {
        return myData[getIndex(row, col,  myColumns)];
    }

    public void set(int row, int col, DataType value) {
        myData[getIndex(row, col, myColumns)] = value;
    }

    public int getNumRows()
    {
        return myRows;
    }

    public int getNumCols()
    {
        return myColumns;
    }

    public void addRow(List<DataType> values)
    {
        if(myColumns == 0)
        {
            myColumns = values.size();
        }

        resize(myRows + 1, myColumns);
        for(int i = 0; i < values.size(); i++)
        {
            set(myRows - 1, i, values.get(i));
        }
    }

    public void addColumn(List<DataType> values)
    {
        if(myRows == 0)
        {
            myRows = values.size();
        }
        resize(myRows, myColumns + 1 );
        for(int i = 0; i < values.size(); i++)
        {
            set(i, myColumns - 1, values.get(i));
        }
    }

    /**
     * Resizes the matrix. The values in the current matrix are placed
     * at the top-left corner of the new matrix. In each dimension, if
     * the new size is smaller than the current size, the data are
     * truncated; if the new size is larger, the remainder of the values
     * are set to 0.
     * @param cols The new column (horizontal) dimension for the matrix
     * @param rows The new row (vertical) dimension for the matrix
     */
    public void resize(int rows, int cols)
    {
        DataType [] newData = (DataType[])Array.newInstance(myClassObj, rows * cols);

        int colsToCopy = Math.min(cols, myColumns);
        int rowsToCopy = Math.min(rows, myRows);
        for (int i = 0; i < rowsToCopy; ++i)
        {
            int oldRowStart = getIndex(i, 0, myColumns);
            int newRowStart = getIndex(i, 0, cols);
            System.arraycopy(myData, oldRowStart, newData, newRowStart, colsToCopy);
        }
        myRows = rows;
        myColumns = cols;
        myData = newData;
    }

    public String toString()
    {
        String aData = "";
        for(int i = 0; i < myRows; i++)
        {
            for(int j = 0; j < myColumns; j++)
            {
                aData = aData + ", " + get(i, j);
            }
            aData = aData + "\n";
        }
        return aData;
    }
}
