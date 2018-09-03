package com.bignerdranch.android.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.criminalintent.database.CrimeBaseHelper;
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context){
        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    //When you call getWritableDatabase() here, CrimeBaseHelper will do the following
    //Open up /data/data/com.bignerdranch.android.criminalintent/databases/crimeBase.db
    //creating a new database file if it does not already exist
    //If this is the first time the database has been created, call onCreate(SQLiteDatabase), then save
    //out the latest version number
    //If this is not the first time, check the version number in the database. If the version number in
    //CrimeBaseHelper is higher, call onUpgrade(SQLiteDatabase, int, int)
    private CrimeLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
    }

    public void addCrime(Crime c){
        ContentValues values = getContentValues(c);

        //The first argument is the table you want to insert, third argument is the data you want to insert
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public void deleteCrime(Crime crime){

    }

    public List<Crime> getCrimes(){
        return new ArrayList<>();
    }

    public Crime getCrime(UUID id){

        return null;
    }

    //Update rows in the database
    public void updateCrime(Crime crime){
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        //Pass in the table name you want to update, the ContentValues you want to assign to each row
        //you update, a where clause to specify which rows get updated, and values for the arguments in th4 where clause
        mDatabase.update(CrimeTable.NAME, values,
                CrimeTable.Cols.UUID + " = ?",
                new String[] {uuidString});
        //You do not put the uuidString directly into the where clause because the String might contain SQL code.
        //This is because the String may contain SQL code which could be from an SQL injection attack
    }

    private Cursor queryCrimes(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null,  //columns - null selects all columns
                whereClause,
                whereArgs,
                null, //groupBy
                null, //having
                null //orderBy
        );

        return cursor;
    }

    //Writes and updates to databases are done with the assistance of a class called ContentValues.
    //ContentValues is a key-value store class but specifically designed to store the kinds of data SQLite can hold
    private static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.Date, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);

        return values;
    }
}
