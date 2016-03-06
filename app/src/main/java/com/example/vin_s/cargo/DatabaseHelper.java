package com.example.vin_s.cargo;

import com.example.vin_s.cargo.model.Post;
import com.example.vin_s.cargo.model.Person;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
 
    // Logcat tag
    private static final String LOG = "DatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "CargoDB";
 
    // Table Names
    private static final String TABLE_POST = "post";
    private static final String TABLE_PEOPLE = "person";

    //Post Column names
    private static final String KEY_ORIGIN = "origin";
    private static final String KEY_DEST = "destination";
    private static final String KEY_DATE = "date";
    private static final String KEY_OWNERID = "ownerID";

    //People Column names
    private static final String KEY_NAME = "name";
    private static final String KEY_INTRO = "intro";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    
    //Shared Column names
    private static final String KEY_ID = "ID";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    
    private static final String CREATE_TABLE_PEOPLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_PEOPLE + "(" + KEY_ID + " TEXT PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_INTRO + " TEXT," + KEY_EMAIL + "  TEXT," + KEY_PASSWORD + " TEXT)";

    //Create table statements
    private static final String CREATE_TABLE_POST = "CREATE TABLE IF NOT EXISTS "
            + TABLE_POST + "(" + KEY_ID + " TEXT PRIMARY KEY," + KEY_ORIGIN
            + " TEXT," + KEY_DEST + " TEXT," + KEY_DATE
            + " DATE," + KEY_OWNERID + " TEXT, FOREIGN KEY (" + KEY_OWNERID + ") REFERENCES " + TABLE_PEOPLE + "(" + KEY_ID + "))";

    //Drop Person Table
    private static final String DROP_TABLE_PEOPLE = "DROP TABLE " + TABLE_PEOPLE;

    //Drop Post Table
    private static final String DROP_TABLE_POST = "DROP TABLE " + TABLE_POST;

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_POST);
        db.execSQL(CREATE_TABLE_PEOPLE);
//        db.execSQL(CREATE_TABLE_PPL_POST);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEOPLE);
 
        // create new tables
        onCreate(db);
    }
    
    public long createPost(Post post) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);;
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_ID, post.getId());
        values.put(KEY_OWNERID, post.getOwnerID());
        values.put(KEY_ORIGIN, post.getOrigin());
        values.put(KEY_DEST, post.getDest());
        values.put(KEY_DATE, dateFormatter.format(post.getDate()));

 
        // insert row
        long todo_id = db.insert(TABLE_POST, null, values);
        return todo_id;
    }
    
    
    //get one post
    public Post getPost(String post_id) throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();
        DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
 
        String selectQuery = "SELECT  * FROM " + TABLE_POST + " WHERE "
                + KEY_ID + " = " + post_id;
 
        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
 
        if (c != null)
            c.moveToFirst();

        Post p = new Post();
        p.setId(c.getString((c.getColumnIndex(KEY_ID))));
        p.setOwnerID(c.getString(c.getColumnIndex(KEY_OWNERID)));
        p.setOrigin((c.getString(c.getColumnIndex(KEY_ORIGIN))));
        p.setDest(c.getString(c.getColumnIndex(KEY_DEST)));
        p.setDate(df.parse(c.getString(c.getColumnIndex(KEY_DATE))));
 
        return p;
    }
    
    //get all posts
    public List<Post> getAllPosts() throws ParseException {
        List<Post> posts = new ArrayList<Post>();
        String selectQuery = "SELECT  * FROM " + TABLE_POST;
        DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
     
        Log.e(LOG, selectQuery);
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Post p = new Post();
                p.setId(c.getString(c.getColumnIndex(KEY_ID)));
                p.setOwnerID(c.getString(c.getColumnIndex(KEY_OWNERID)));
                p.setOrigin((c.getString(c.getColumnIndex(KEY_ORIGIN))));
                p.setDest(c.getString(c.getColumnIndex(KEY_DEST)));
                p.setDate(df.parse(c.getString(c.getColumnIndex(KEY_DATE))));
     
                // adding to todo list
                posts.add(p);
            } while (c.moveToNext());
        }
     
        return posts;
    }
    
    //get all posts by Origin 
    public List<Post> getAllPostsO (String org) throws ParseException {
        List<Post> posts = new ArrayList<Post>();
        DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
        String selectQuery = "SELECT  * FROM " + TABLE_POST + " WHERE "
                + KEY_ORIGIN + " = '" + org +"'";
     
        Log.e(LOG, selectQuery);
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Post p = new Post();
                p.setId(c.getString(c.getColumnIndex(KEY_ID)));
                p.setOwnerID(c.getString(c.getColumnIndex(KEY_OWNERID)));
                p.setOrigin((c.getString(c.getColumnIndex(KEY_ORIGIN))));
                p.setDest(c.getString(c.getColumnIndex(KEY_DEST)));
                p.setDate(df.parse(c.getString(c.getColumnIndex(KEY_DATE))));
     
                // adding to todo list
                posts.add(p);
            } while (c.moveToNext());
        }
     
        return posts;
    }
    
    public List<Post> getAllPostsD (String des) throws ParseException {
        List<Post> posts = new ArrayList<Post>();
        DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
        String selectQuery = "SELECT  * FROM " + TABLE_POST + " WHERE "
                + KEY_DEST + " = '" + des +"'";
     
        Log.e(LOG, selectQuery);
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Post p = new Post();
                p.setId(c.getString(c.getColumnIndex(KEY_ID)));
                p.setOwnerID(c.getString(c.getColumnIndex(KEY_OWNERID)));
                p.setOrigin((c.getString(c.getColumnIndex(KEY_ORIGIN))));
                p.setDest(c.getString(c.getColumnIndex(KEY_DEST)));
                p.setDate(df.parse(c.getString(c.getColumnIndex(KEY_DATE))));
     
                // adding to todo list
                posts.add(p);
            } while (c.moveToNext());
        }
     
        return posts;
    }

    //create person
    public long createPerson(Person person){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, person.getId());
        values.put(KEY_INTRO, person.getIntro());
        values.put(KEY_NAME, person.getName());
        values.put(KEY_EMAIL, person.getEmail());
        values.put(KEY_PASSWORD, person.getPassword());

        // insert row
        long todo_id = db.insert(TABLE_PEOPLE, null, values);
        return todo_id;
    }

    //drop person table
    public void dropPerson(SQLiteDatabase db){
        db.execSQL(DROP_TABLE_PEOPLE);
    }

    //drop post table
    public void dropPost(SQLiteDatabase db){
        db.execSQL(DROP_TABLE_POST);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
   //helpful DB tutorial http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
} 