package com.example.vin_s.cargo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.vin_s.cargo.model.Comment;
import com.example.vin_s.cargo.model.Person;
import com.example.vin_s.cargo.model.Post;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private static final String TABLE_COMMENT = "comment";

    //Post Column names
    private static final String KEY_TITLE = "title";
    private static final String KEY_SLOGAN = "slogan";
    private static final String KEY_CARTYPE = "carType";
    private static final String KEY_ORIGIN = "origin";
    private static final String KEY_DEST = "destination";
    private static final String KEY_DATE = "date";
    private static final String KEY_NUMBEROFSEATS = "numberOfSeats";
    private static final String KEY_SEATLEFT = "seatsLeft";
    private static final String KEY_DETAILS = "details";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_REQUIREMENTS = "requirements";

    //People Column names
    private static final String KEY_NAME = "name";
    private static final String KEY_INTRO = "intro";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    //Comment Column names
    private static final String KEY_POSTID = "postID";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_DATE_OF_COMMENT = "dateOfComment";

    //Shared Column names
    private static final String KEY_ID = "ID";
    private static final String KEY_OWNERID = "ownerID";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create people statements
    private static final String CREATE_TABLE_PEOPLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_PEOPLE + "(" + KEY_ID + " TEXT PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_INTRO + " TEXT," + KEY_EMAIL + "  TEXT," + KEY_PASSWORD + " TEXT)";

    //Create table statements
    private static final String CREATE_TABLE_POST = "CREATE TABLE IF NOT EXISTS "
            + TABLE_POST + "(" + KEY_ID + " TEXT PRIMARY KEY," + KEY_TITLE
            + " TEXT," + KEY_SLOGAN + " TEXT," + KEY_CARTYPE + " TEXT," + KEY_ORIGIN + " TEXT," + KEY_DEST + " TEXT,"
            +  KEY_DATE + " DATE," + KEY_NUMBEROFSEATS
            + " INT," + KEY_SEATLEFT + " INT," + KEY_DETAILS
            + " TEXT," +  KEY_DURATION + " TEXT," + KEY_REQUIREMENTS
            + " TEXT," + KEY_OWNERID + " TEXT, FOREIGN KEY (" + KEY_OWNERID + ") REFERENCES " + TABLE_PEOPLE + "(" + KEY_ID + "))";

    //Create comment statements
    private static final String CREATE_TABLE_COMMENT = "CREATE TABLE IF NOT EXISTS "
            + TABLE_COMMENT + "(" + KEY_ID + " TEXT PRIMARY KEY," + KEY_POSTID
            + " TEXT," + KEY_OWNERID + " TEXT," + KEY_CONTENT + "  TEXT," + KEY_DATE_OF_COMMENT + " DATE, FOREIGN KEY (" + KEY_POSTID + ") REFERENCES " + TABLE_POST + "(" + KEY_ID + "))";

    //Drop Person Table
    private static final String DROP_TABLE_PEOPLE = "DROP TABLE " + TABLE_PEOPLE;

    //Drop Post Table
    private static final String DROP_TABLE_POST = "DROP TABLE " + TABLE_POST;

    //Drop Comment Table
    private static final  String DROP_TABLE_COMMENT = "DROP TABLE " + TABLE_COMMENT;

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_POST);
        db.execSQL(CREATE_TABLE_PEOPLE);
        db.execSQL(CREATE_TABLE_COMMENT);
//        db.execSQL(CREATE_TABLE_PPL_POST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEOPLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENT);
        // create new tables
        onCreate(db);
    }

    public long createPost(Post post) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, post.getId());
        values.put(KEY_OWNERID, post.getOwnerID());
        values.put(KEY_ORIGIN, post.getOrigin());
        values.put(KEY_DEST, post.getDest());
        values.put(KEY_DATE, dateFormatter.format(post.getDate()));
        values.put(KEY_TITLE, post.getTitle());
        values.put(KEY_SLOGAN, post.getSlogan());
        values.put(KEY_CARTYPE,post.getCarType());
        values.put(KEY_NUMBEROFSEATS, post.getNumberOfSeats());
        values.put(KEY_SEATLEFT, post.getSeatsLeft());
        values.put(KEY_DETAILS, post.getDetails());
        values.put(KEY_DURATION, post.getDuration());
        values.put(KEY_REQUIREMENTS, post.getRequirements());

        //for testing use only
        SQLiteDatabase dbtest = this.getWritableDatabase();
        ContentValues test = new ContentValues();
        test.put(KEY_ID, "1");
        test.put(KEY_INTRO, "我傻逼我自豪");
        test.put(KEY_NAME, "傻逼孙狗");
        test.put(KEY_EMAIL, "sb123");
        test.put(KEY_PASSWORD, "sb123");
        dbtest.replace(TABLE_PEOPLE, null, test);

        // insert row
        long todo_id = db.insert(TABLE_POST, null, values);
        return todo_id;
    }


    //get all posts
    public List<Post> getAllPosts() throws ParseException {
        List<Post> posts = new ArrayList<Post>();
        String selectQuery = "SELECT  * FROM " + TABLE_POST;
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(!c.moveToFirst()){
            return posts;
        }

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
        c.close();
        return posts;
    }

    //get all posts by origin, destination and departure date (includeing "any")
    public List<Post> getAllPostsByOD (String org, String des, String dDate) throws ParseException {
        List<Post> posts = new ArrayList<Post>();

        DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        Date depDate = df1.parse(dDate);
        String temp = df2.format(depDate);


        //origin = any
        if(org.equals("Any")&&!des.equals("Any")){
            DateFormat dd1 = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat dd2 = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String selectQuery = "SELECT  * FROM " + TABLE_POST + " WHERE "
                    + KEY_DEST + " = '" + des +"'";

            Log.e(LOG, selectQuery);

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);

            if(!c.moveToFirst()){
                return posts;
            }

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    Post p = new Post();
                    p.setId(c.getString(c.getColumnIndex(KEY_ID)));
                    p.setOwnerID(c.getString(c.getColumnIndex(KEY_OWNERID)));
                    p.setOrigin((c.getString(c.getColumnIndex(KEY_ORIGIN))));
                    p.setDest(c.getString(c.getColumnIndex(KEY_DEST)));
                    p.setDate(df.parse(c.getString(c.getColumnIndex(KEY_DATE))));
                    p.setDuration(c.getString(c.getColumnIndex(KEY_DURATION)));
                    p.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
                    p.setSlogan(c.getString(c.getColumnIndex(KEY_SLOGAN)));
                    p.setCarType(c.getString(c.getColumnIndex(KEY_CARTYPE)));
                    p.setDetails(c.getString(c.getColumnIndex(KEY_DETAILS)));
                    p.setNumberOfSeats(c.getInt(c.getColumnIndex(KEY_NUMBEROFSEATS)));
                    p.setRequirements(c.getString(c.getColumnIndex(KEY_REQUIREMENTS)));
                    p.setSeatsLeft(c.getInt(c.getColumnIndex(KEY_SEATLEFT)));
                    // adding to todo list

                    Date tempD = p.getDate(); //dd-MM-yyyy
                    Date inputD = dd2.parse(dDate); //dd-MM-yyyy Date
                    String temp1 = dd1.format(tempD); //yyyy-MM-dd String
                    String temp2 = dd1.format(inputD); //yyyy-MM-dd String
                    Date target = dd1.parse(temp1); //yyyy-MM-dd Date
                    Date inputDate = dd1.parse(temp2); //yyyy-MM-dd Date
                    if(target.compareTo(inputDate)>=0)
                        posts.add(p);
                } while (c.moveToNext());
            }
            c.close();
            return posts;
        }//destination = any
        else if(des.equals("Any")&&!org.equals("Any")){
            DateFormat dd1 = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat dd2 = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String selectQuery = "SELECT  * FROM " + TABLE_POST + " WHERE "
                    + KEY_ORIGIN + " = '" + org +"'";
            Log.e(LOG, selectQuery);
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);

            if(!c.moveToFirst()){
                return posts;
            }

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    Post p = new Post();
                    p.setId(c.getString(c.getColumnIndex(KEY_ID)));
                    p.setOwnerID(c.getString(c.getColumnIndex(KEY_OWNERID)));
                    p.setOrigin((c.getString(c.getColumnIndex(KEY_ORIGIN))));
                    p.setDest(c.getString(c.getColumnIndex(KEY_DEST)));
                    p.setDate(df.parse(c.getString(c.getColumnIndex(KEY_DATE))));
                    p.setDuration(c.getString(c.getColumnIndex(KEY_DURATION)));
                    p.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
                    p.setSlogan(c.getString(c.getColumnIndex(KEY_SLOGAN)));
                    p.setCarType(c.getString(c.getColumnIndex(KEY_CARTYPE)));
                    p.setDetails(c.getString(c.getColumnIndex(KEY_DETAILS)));
                    p.setNumberOfSeats(c.getInt(c.getColumnIndex(KEY_NUMBEROFSEATS)));
                    p.setRequirements(c.getString(c.getColumnIndex(KEY_REQUIREMENTS)));
                    p.setSeatsLeft(c.getInt(c.getColumnIndex(KEY_SEATLEFT)));
                    // adding to todo list

                    Date tempD = p.getDate(); //dd-MM-yyyy
                    Date inputD = dd2.parse(dDate); //dd-MM-yyyy Date
                    String temp1 = dd1.format(tempD); //yyyy-MM-dd String
                    String temp2 = dd1.format(inputD); //yyyy-MM-dd String
                    Date target = dd1.parse(temp1); //yyyy-MM-dd Date
                    Date inputDate = dd1.parse(temp2); //yyyy-MM-dd Date
                    if(target.compareTo(inputDate)>=0)
                        posts.add(p);
                } while (c.moveToNext());
            }
            c.close();
            return posts;
        }//both origin and destination are any
        else if(org.equals("Any") && des.equals("Any")){
            DateFormat dd1 = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat dd2 = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String selectQuery = "SELECT * FROM post";
            Log.e(LOG, selectQuery);

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);

            if(!c.moveToFirst()){
                return posts;
            }

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    Post p = new Post();
                    p.setId(c.getString(c.getColumnIndex(KEY_ID)));
                    p.setOwnerID(c.getString(c.getColumnIndex(KEY_OWNERID)));
                    p.setOrigin((c.getString(c.getColumnIndex(KEY_ORIGIN))));
                    p.setDest(c.getString(c.getColumnIndex(KEY_DEST)));
                    p.setDate(df.parse(c.getString(c.getColumnIndex(KEY_DATE))));
                    p.setDuration(c.getString(c.getColumnIndex(KEY_DURATION)));
                    p.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
                    p.setSlogan(c.getString(c.getColumnIndex(KEY_SLOGAN)));
                    p.setCarType(c.getString(c.getColumnIndex(KEY_CARTYPE)));
                    p.setDetails(c.getString(c.getColumnIndex(KEY_DETAILS)));
                    p.setNumberOfSeats(c.getInt(c.getColumnIndex(KEY_NUMBEROFSEATS)));
                    p.setRequirements(c.getString(c.getColumnIndex(KEY_REQUIREMENTS)));
                    p.setSeatsLeft(c.getInt(c.getColumnIndex(KEY_SEATLEFT)));
                    // adding to todo list

                    Date tempD = p.getDate(); //dd-MM-yyyy
                    Date inputD = dd2.parse(dDate); //dd-MM-yyyy Date
                    String temp1 = dd1.format(tempD); //yyyy-MM-dd String
                    String temp2 = dd1.format(inputD); //yyyy-MM-dd String
                    Date target = dd1.parse(temp1); //yyyy-MM-dd Date
                    Date inputDate = dd1.parse(temp2); //yyyy-MM-dd Date
                    if(target.compareTo(inputDate)>=0)
                        posts.add(p);

                } while (c.moveToNext());
            }
            c.close();
            return posts;
        }//both not any
        else{
            DateFormat dd1 = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat dd2 = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String selectQuery = "SELECT  * FROM " + TABLE_POST + " WHERE "
                    + KEY_ORIGIN + " = '" + org + "'" + " AND "
                    + KEY_DEST + " = '" + des +"'";

            Log.e(LOG, selectQuery);

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);

            if(!c.moveToFirst()){
                return posts;
            }

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    Post p = new Post();
                    p.setId(c.getString(c.getColumnIndex(KEY_ID)));
                    p.setOwnerID(c.getString(c.getColumnIndex(KEY_OWNERID)));
                    p.setOrigin((c.getString(c.getColumnIndex(KEY_ORIGIN))));
                    p.setDest(c.getString(c.getColumnIndex(KEY_DEST)));
                    p.setDate(df.parse(c.getString(c.getColumnIndex(KEY_DATE))));
                    p.setDuration(c.getString(c.getColumnIndex(KEY_DURATION)));
                    p.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
                    p.setSlogan(c.getString(c.getColumnIndex(KEY_SLOGAN)));
                    p.setCarType(c.getString(c.getColumnIndex(KEY_CARTYPE)));
                    p.setDetails(c.getString(c.getColumnIndex(KEY_DETAILS)));
                    p.setNumberOfSeats(c.getInt(c.getColumnIndex(KEY_NUMBEROFSEATS)));
                    p.setRequirements(c.getString(c.getColumnIndex(KEY_REQUIREMENTS)));
                    p.setSeatsLeft(c.getInt(c.getColumnIndex(KEY_SEATLEFT)));
                    // adding to todo list

                    Date tempD = p.getDate(); //dd-MM-yyyy
                    Date inputD = dd2.parse(dDate); //dd-MM-yyyy Date
                    String temp1 = dd1.format(tempD); //yyyy-MM-dd String
                    String temp2 = dd1.format(inputD); //yyyy-MM-dd String
                    Date target = dd1.parse(temp1); //yyyy-MM-dd Date
                    Date inputDate = dd1.parse(temp2); //yyyy-MM-dd Date
                    if(target.compareTo(inputDate)>=0)
                        posts.add(p);
                } while (c.moveToNext());
            }
            c.close();
            return posts;
        }
    }

    //get Post by postID
    public Post getPostByID(String postID) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Post p = new Post();
        String selectQuery = "SELECT  * FROM " + TABLE_POST + " WHERE "
                + KEY_ID + " = '" + postID + "'";
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                p.setId(c.getString(c.getColumnIndex(KEY_ID)));
                p.setOwnerID(c.getString(c.getColumnIndex(KEY_OWNERID)));
                p.setOrigin((c.getString(c.getColumnIndex(KEY_ORIGIN))));
                p.setDest(c.getString(c.getColumnIndex(KEY_DEST)));
                p.setDate(df.parse(c.getString(c.getColumnIndex(KEY_DATE))));
                p.setDuration(c.getString(c.getColumnIndex(KEY_DURATION)));
                p.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
                p.setSlogan(c.getString(c.getColumnIndex(KEY_SLOGAN)));
                p.setCarType(c.getString(c.getColumnIndex(KEY_CARTYPE)));
                p.setDetails(c.getString(c.getColumnIndex(KEY_DETAILS)));
                p.setNumberOfSeats(c.getInt(c.getColumnIndex(KEY_NUMBEROFSEATS)));
                p.setRequirements(c.getString(c.getColumnIndex(KEY_REQUIREMENTS)));
                p.setSeatsLeft(c.getInt(c.getColumnIndex(KEY_SEATLEFT)));
                // adding to todo list
            } while (c.moveToNext());
        }
        c.close();
        return p;
    }

    //get comments by postID
    public List<Comment> getCommentsByPostID(String postID) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        List<Comment> comments = new ArrayList<Comment>();
        String selectQuery = "SELECT  * FROM " + TABLE_COMMENT + " WHERE "
                + KEY_POSTID + " = '" + postID + "'";
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if(!c.moveToFirst()){
            return comments;
        }else{
            if (c.moveToFirst()) {
                do {
                    Comment comment = new Comment();
                    comment.setContent(c.getString(c.getColumnIndex(KEY_CONTENT)));
                    comment.setDateOfComment(df.parse(c.getString(c.getColumnIndex(KEY_DATE_OF_COMMENT))));
                    comment.setId(c.getString(c.getColumnIndex(KEY_ID)));
                    comment.setOwnerID(c.getString(c.getColumnIndex(KEY_OWNERID)));
                    comment.setPostID(c.getString(c.getColumnIndex(KEY_POSTID)));
                    comments.add(comment);
                    // adding to todo list
                } while (c.moveToNext());
            }
        }
        c.close();
        return comments;
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

    //Retrieve all users
    public List<Person> getAllUsers(){
        List<Person> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_PEOPLE;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Person user = new Person();
                user.setId(c.getString((c.getColumnIndex(KEY_ID))));
                user.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
                user.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                user.setIntro(c.getString(c.getColumnIndex(KEY_INTRO)));
                user.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
                users.add(user);
            } while (c.moveToNext());
        }
        c.close();
        return users;
    }

    //Retrieve particular user
    public Person getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_PEOPLE + " WHERE EMAIL = '" + email + "'";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()){

            Person user = new Person();
            user.setId(c.getString((c.getColumnIndex(KEY_ID))));
            user.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
            user.setName((c.getString(c.getColumnIndex(KEY_NAME))));
            user.setIntro(c.getString(c.getColumnIndex(KEY_INTRO)));
            user.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
            c.close();
            return user;

        }else{
            c.close();
            return null;
        }
    }

    //Retrieve particular user
    public Person getUserByID(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_PEOPLE + " WHERE ID = '" + id + "'";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null){
            c.moveToFirst();

            Person user = new Person();
            user.setId(c.getString((c.getColumnIndex(KEY_ID))));
            user.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
            user.setName((c.getString(c.getColumnIndex(KEY_NAME))));
            user.setIntro(c.getString(c.getColumnIndex(KEY_INTRO)));
            user.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
            return user;

        }else{
            return null;
        }
    }

    //create comment
    public long createComment(Comment comment){
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.US);

        ContentValues values = new ContentValues();
        values.put(KEY_ID, comment.getId());
        values.put(KEY_OWNERID, comment.getOwnerID());
        values.put(KEY_POSTID, comment.getPostID());
        values.put(KEY_CONTENT, comment.getContent());
        values.put(KEY_DATE_OF_COMMENT, dateFormatter.format(comment.getDateOfComment()));

        // insert row
        long todo_id = db.insert(TABLE_COMMENT, null, values);
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

    //drop comment table
    public void dropComment(SQLiteDatabase db){
        db.execSQL(DROP_TABLE_COMMENT);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
   //helpful DB tutorial http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
} 