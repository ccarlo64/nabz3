package com.homeproject.nabz;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "nabaztag.db";

	private static final int SCHEMA_VERSION = 7;

	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		
			String sql = "CREATE TABLE DATI ( _id "
				+ "INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ "ID INTEGER NOT NULL, UTENTE TEXT NOT NULL," 
				+ " PASSWORD TEXT NOT NULL," 
				+ " SERVER TEXT NOT NULL);"; //,"
			    //+ " MAC TEXT NOT NULL);";

				Log.d("db",sql);
				db.execSQL(sql);	
			insertDati(db,"usertest","pwdtest","http://openznab.it");//,"1122334455");

			sql = "CREATE TABLE STREAM ( _id "
			    + "INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ " NAMESTREAM TEXT NOT NULL," 
				+ " URLSTREAM TEXT);";
				Log.d("db",sql);
				db.execSQL(sql);	
        //insertDatiStream(db,"RDS","http://46.37.20.205:8000/rdsmp3");
                insertDemoStreams(db);

            sql = "CREATE TABLE PACKET ( _id "
                    + "INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + " NAMEPACKET TEXT NOT NULL,"
                    + " DATEPACKET TEXT);";
            Log.d("db",sql);
            db.execSQL(sql);
            insertDemoPackets(db);




    }
    private void insertDemoPackets( SQLiteDatabase db ) {
        insertDatiPacket(db,"Reboot","7F09000000FF");
        insertDatiPacket(db,"Karotz 0001","7FCC000001FF");
    }



    private void insertDemoStreams( SQLiteDatabase db ) {
        insertDatiStream(db,"Stop stream"," ");
        insertDatiStream(db,"RDS","http://46.37.20.205:8000/rdsmp3");
        insertDatiStream(db,"JAZZ FM 91","http://206.223.188.169:8002/");
        insertDatiStream(db,"ProgRock","http://184.154.185.181:8000/");
        insertDatiStream(db,"Abc80","http://uk4.internet-radio.com:8045/");
        insertDatiStream(db,"Classic Rock","http://107.155.126.58:17150/");

    }
	public void insertDati( SQLiteDatabase db, String a1, String a2, String a3) //, String a4 )
	{
		ContentValues v = new ContentValues();
		v.put("ID", 1);
		v.put("UTENTE", a1);
		v.put("PASSWORD", a2);
		v.put("SERVER", a3);
		//v.put("MAC", a4);
		db.insert("DATI", null, v);
	}
	public void insertDatiStream( SQLiteDatabase db, String a1, String a2)
	{
		ContentValues v = new ContentValues();
		v.put("NAMESTREAM", a1);
		v.put("URLSTREAM", a2);
		db.insert("STREAM", null, v);
	}
    public void insertDatiPacket( SQLiteDatabase db, String a1, String a2)
    {
        ContentValues v = new ContentValues();
        v.put("NAMEPACKET", a1);
        v.put("DATEPACKET", a2);
        db.insert("PACKET", null, v);
    }

    public void deleteStream( SQLiteDatabase db, String[] r)
	{
		db.delete("STREAM", "_id = ?", r);
	}
    public void deletePacket( SQLiteDatabase db, String[] r)
    {
        db.delete("PACKET", "_id = ?", r);
    }


    public void updateDati( String a1, String a2, String a3) //, String a4 )
	{
		String strFilter = "ID=1";
		SQLiteDatabase db = getWritableDatabase();
		ContentValues v = new ContentValues();
		v.put("UTENTE", a1);
		v.put("PASSWORD", a2);
		v.put("SERVER", a3);
//		v.put("MAC", a4);
		db.update("DATI", v, strFilter, null);
				
	}
	
	public Cursor getSetting()
	{
		SQLiteDatabase db = getReadableDatabase();
		String selectQuery = "SELECT  * FROM DATI";
		Cursor cursor = db.rawQuery(selectQuery, null);
		return cursor;
	}

	public Cursor getStreams()
	{
		SQLiteDatabase db = getReadableDatabase();
		String selectQuery = "SELECT _ID, NAMESTREAM, URLSTREAM FROM STREAM";
		Cursor cursor = db.rawQuery(selectQuery, null);
		return cursor;
	}
    public Cursor getPackets()
    {
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT _ID, NAMEPACKET, DATEPACKET FROM PACKET";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
	{
		Log.d("onUpgrade","ok");
		//arg0.execSQL("DROP TABLE IF EXISTS DATI");
		arg0.execSQL("DROP TABLE IF EXISTS STREAM");

        String sql = "CREATE TABLE STREAM ( _id "
                + "INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " NAMESTREAM TEXT NOT NULL,"
                + " URLSTREAM TEXT);";
        Log.d("db",sql);
        arg0.execSQL(sql);
        insertDemoStreams(arg0);
        sql = "CREATE TABLE PACKET ( _id "
                + "INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " NAMEPACKET TEXT NOT NULL,"
                + " DATEPACKET TEXT);";
        Log.d("db",sql);
        arg0.execSQL(sql);
        insertDemoPackets(arg0);
//onCreate(arg0);
	}
	
	
	public String[] getC()
	{
      String[] reso = new String[3]; //4
      String selectQuery = "SELECT  * FROM DATI WHERE ID = 1";
      SQLiteDatabase db = getReadableDatabase();
      Cursor cursor = db.rawQuery(selectQuery, null);
      if (cursor.moveToFirst()) {
          reso[0] = cursor.getString(2);   
          reso[1] = cursor.getString(3);   
          reso[2] = cursor.getString(4);   
  //        reso[3] = cursor.getString(5);   
      }

 	  return reso;
	}

	public ArrayList<Stream> getStreamData()
	{
	  ArrayList<Stream> sd = new ArrayList<Stream>();
	  
      String selectQuery = "SELECT  * FROM STREAM";
      SQLiteDatabase db = getReadableDatabase();
      Cursor cursor = db.rawQuery(selectQuery, null);

      if (cursor.moveToFirst()) {
          do {      
      sd.add( new Stream(cursor.getString(1),cursor.getString(2)));
      Log.d("getStreamData",cursor.getString(1));              
  } while (cursor.moveToNext());
}

 	  return sd;
	}

    public ArrayList<Packet> getPacketData()
    {
        ArrayList<Packet> sd = new ArrayList<Packet>();

        String selectQuery = "SELECT  * FROM PACKET";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                sd.add( new Packet(cursor.getString(1),cursor.getString(2)));
                Log.d("getPacketData",cursor.getString(1));
            } while (cursor.moveToNext());
        }

        return sd;
    }


/*
	public List<String> getStream1()
	{
	  List<String> sd = new ArrayList<String>();
	  
      String selectQuery = "SELECT  * FROM STREAM";
      SQLiteDatabase db = getReadableDatabase();
      Cursor cursor = db.rawQuery(selectQuery, null);

      if (cursor.moveToFirst()) {
          do {
//   String bookName = cursor.getString(cursor.getColumnIndex("bookTitle"));
              sd.add(cursor.getString(1));
              Log.d("getStream1",cursor.getString(1));              
          } while (cursor.moveToNext());
      }

  /*    
      if (cursor.moveToNext()) {
          sd.add(cursor.getString(1));
          
   Log.d("getStream1",cursor.getString(1));
      }
*/
    /*
 	  return sd;
	}*/
}