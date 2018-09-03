package com.bignerdranch.android.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.criminalintent.Crime;
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.util.Date;
import java.util.UUID;

//Why we need a cursor wrapper class
//A cursor leaves a lot to be desired. All it does is give you raw column values.
//Every time you pull a Crime out of a cursor you need to write code to extract the title, date, etc.
//This CursorWrapper class lets you wrap a Cursor you received from another place and add new methods to it
public class CrimeCursorWrapper extends CursorWrapper {

    //This creates a thin wrapper around a Cursor. It has all the same methods as a Cursor it wraps, and
    //calling those methods does the exact same thing while also allowing us to add new methods that operate
    //on the underlying Cursor
    public CrimeCursorWrapper(Cursor cursor){
        super(cursor);
    }

    //Method that pulls out relevant column data
    public Crime getCrime(){
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.Date));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);

        return crime;
    }
}
