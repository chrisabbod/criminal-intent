package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.criminalintent.database.CrimeBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private List<Crime> mCrimes;
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
        mCrimes = new ArrayList<>();
    }

    public void addCrime(Crime c){
        mCrimes.add(c);
    }

    public void deleteCrime(Crime crime){
        mCrimes.remove(crime);
    }

    public List<Crime> getCrimes(){
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for (Crime crime : mCrimes){
            if(crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }
}
