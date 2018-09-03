package com.bignerdranch.android.criminalintent.database;

public class CrimeDbSchema {
    //CrimeTable only exists to define the String constants needed to describe the moving pieces of your table definition
    public static final class CrimeTable {
        public static final String NAME = "crimes";


        //Allows you to refer to columns in a safe way
        //CrimeTable.Cols.TITLE is safer to update if you ever need to change the name of that column
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String Date = "date";
            public static final String SOLVED = "solved";
        }
    }
}
