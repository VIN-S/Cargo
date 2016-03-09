package com.example.vin_s.cargo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.vin_s.cargo.model.Comment;
import com.example.vin_s.cargo.model.Post;

import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.vin_s.cargo.model.Post;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class ResultList extends AppCompatActivity {

    private LinearLayout loop;
    private List<Post> posts = new ArrayList<Post>();
    private List<Post> newPosts = new ArrayList<Post>();
    private Post selectedPost;
    private DatabaseHelper dbHelper = new DatabaseHelper(this);
    private String sortedItem;
    private Spinner spinner;
    private List<Comment> comments = new ArrayList<Comment>();
    private int commentN = 0;
    private int commentLast = 0;
    private int commentB = 0;
    private int commentA = 0;
    private int num =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);
        loop = (LinearLayout) findViewById(R.id.loopLayout);
        loop.setOrientation(LinearLayout.VERTICAL);
        loop.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        posts = (List<Post>) getIntent().getSerializableExtra("resultList");
        String item = (String) getIntent().getSerializableExtra("item");

        //sortedBy dropdown list
        spinner = (Spinner) findViewById(R.id.sortedBy);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sortedBy, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (item.equals("Date"))
            spinner.setSelection(0);
        else
            spinner.setSelection(1);
        int num = posts.size();

        if (posts.isEmpty()) {
            LinearLayout.LayoutParams lpT = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lpT.setMargins(0, 0, 0, 20);
            TextView one = new TextView(this);
            TextView two = new TextView(this);
            one.setLayoutParams(lpT);
            two.setLayoutParams(lpT);
            one.setTextColor(0XFF000000);
            one.setTextSize(18);
            two.setTextSize(16);
            two.setTextColor(0XFF000000);
            one.setText("Sorry! There s no trip available!");
            two.setText("Please search again!");
            loop.addView(one);
            loop.addView(two);
        } else {
            //rearrange the result list according to the sortedBy item
            if (item.equals("Date")) {
                for (int i = 0; i < posts.size(); i++) {
                    int newSize = newPosts.size();
                    Post p = (Post) posts.get(i);
                    if (newPosts.isEmpty())
                        newPosts.add(p);
                    else if (newPosts.size() == 1) {
                        if (p.getDate().compareTo(newPosts.get(0).getDate()) >= 0)
                            newPosts.add(p);//append at last --[0,1]
                        else
                            newPosts.add(0, p);//add at index 0 -- [1,0]
                    } else if (p.getDate().compareTo(newPosts.get(newSize - 1).getDate()) >= 0) {
                        newPosts.add(p);
                    } else {
                        for (int m = 0; m < newPosts.size() - 1; m++) {
                            if ((p.getDate().compareTo(newPosts.get(m).getDate()) >= 0) &&
                                    (p.getDate().compareTo(newPosts.get(m + 1).getDate()) < 0)) {
                                int position = m + 1;
                                newPosts.add(position, p);
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < posts.size(); i++) {
                    Post p = posts.get(i);
                    try {
                        comments = dbHelper.getCommentsByPostID(p.getId());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    int commentP = comments.size();
                    if (!newPosts.isEmpty()) {
                        try {
                            commentLast = dbHelper.getCommentsByPostID(newPosts.get(newPosts.size() - 1).getId()).size();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    if (newPosts.isEmpty())
                        newPosts.add(p);
                    else if (newPosts.size() == 1) {
                        try {
                            commentN = dbHelper.getCommentsByPostID(newPosts.get(0).getId()).size();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (commentP <= commentN)
                            newPosts.add(p);
                        else
                            newPosts.add(0, p);
                    } else if (commentLast >= commentP) {
                        newPosts.add(p);
                    } else {
                        for (int m = 0; m < newPosts.size() - 1; m++) {
                            try {
                                commentB = dbHelper.getCommentsByPostID(newPosts.get(m).getId()).size();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            try {
                                commentA = dbHelper.getCommentsByPostID(newPosts.get(m + 1).getId()).size();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (commentB >= commentP && commentA <= commentP)
                                newPosts.add(m + 1, p);
                        }
                    }
                }
            }

            //using rearranged posts list
            for (int l = 0; l < newPosts.size(); l++) {
                String postID = (String) newPosts.get(l).getId();

                /*try {
                    selectedPost = dbHelper.getPostByID(postID);
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/

                String org = (String) newPosts.get(l).getOrigin();
                String des = (String) newPosts.get(l).getDest();
                String duration = (String) newPosts.get(l).getDuration();
                String pTitle = (String) newPosts.get(l).getTitle();
                String od = org + " - " + des + ", " + duration;

                //child include child1, view
                LinearLayout child = new LinearLayout(this);
                child.setOrientation(LinearLayout.VERTICAL);
                child.setId(l);
                child.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                //set onclick action


                final MyLovelyOnClickListener button_click = new MyLovelyOnClickListener(newPosts.get(l)) {
                    @Override
                    public void onClick(View v) {
                        showPostPage(v, myLovelyVariable);
                    }
                };
                child.setOnClickListener(button_click);

                //child1 include imageview, finalc
                LinearLayout child1 = new LinearLayout(this);
                child1.setOrientation(LinearLayout.HORIZONTAL);
                child1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

                ImageView image = new ImageView(this);
                image.setImageResource(R.drawable.result_1);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(200, 200);
                lp.setMargins(20, 0, 0, 0);
                image.setLayoutParams(lp);

                //finalc include text1, text2, text3
                LinearLayout finalC = new LinearLayout(this);
                finalC.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams lpC = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                lpC.setMargins(0, 0, 0, 30);
                finalC.setLayoutParams(lpC);

                TextView text1 = new TextView(this);
                TextView text2 = new TextView(this);
                TextView text3 = new TextView(this);
                LinearLayout.LayoutParams lpT = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                lpT.setMargins(30, 0, 0, 0);
                text1.setLayoutParams(lpT);
                text2.setLayoutParams(lpT);
                text3.setLayoutParams(lpT);

                text1.setText((String) od);
                text1.setTextSize(18);
                text1.setTextColor(0XFF000000);
                text2.setText((String) pTitle);
                text2.setTextSize(16);
                text3.setText((String) newPosts.get(l).getSlogan());
                text3.setTextSize(13);

                View view = new View(this);
                LinearLayout.LayoutParams lpV = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 2);
                lpV.setMargins(0, 20, 0, 20);
                view.setLayoutParams(lpV);
                view.setBackgroundColor(0XFF000000);

                finalC.addView(text1);
                finalC.addView(text2);
                finalC.addView(text3);

                child1.addView(image);
                child1.addView(finalC);

                child.addView(child1);
                child.addView(view);

                loop.addView(child);
            }
        }

        final String currentItem = String.valueOf(spinner.getSelectedItem());

        //sotedby refresh
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                sortedItem = String.valueOf(spinner.getSelectedItem());
                // your code here
                /*Toast.makeText(ResultList.this,
                        "SortedItem selected : " +
                                sortedItem,
                        Toast.LENGTH_SHORT).show();
                        */
                if (!currentItem.equals(sortedItem)) {
                    Intent intent = getIntent();
                    intent.putExtra("item", sortedItem);
                    finish();
                    startActivity(intent);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        SharedPreferences prefs = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userName = prefs.getString("nameKey", null);

        if (userName == null) {
            LinearLayout tabBarLayout = (LinearLayout) this.findViewById(R.id.resultlist_tab_bar);
            tabBarLayout.setVisibility(LinearLayout.GONE);
            ScrollView searchScroll = (ScrollView) this.findViewById(R.id.resultlist_scrollView);
            searchScroll.setMinimumHeight(500);
        }
    }


    public class MyLovelyOnClickListener implements OnClickListener {

        Post myLovelyVariable;

        public MyLovelyOnClickListener(Post myLovelyVariable) {
            this.myLovelyVariable = myLovelyVariable;
        }

        @Override
        public void onClick(View v) {
            //read your lovely variable
        }

    }

    /**
     * Called when the user clicks the block
     */
    public void showPostPage(View view, Post num) {
        Intent intent = new Intent(this, PostPage.class);
        intent.putExtra("selectPostOrNot", true);
        intent.putExtra("selectedPost", num);
        startActivity(intent);
    }

    public void redirectToProfile(View view) {
        Intent intent = new Intent(this, MyProfile.class);
        startActivity(intent);
    }

    public void redirectToSearch(View view) {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    public void redirectToCreatePost(View view) {
        Intent intent = new Intent(this, PostActivity.class);
        startActivity(intent);
    }

    public void redirectToHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void logout(View view) {
        showDialog(ResultList.this, "Logout Warning", "Are you going to log out?");
    }

    public void showDialog(final Activity activity, String title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SharedPreferences preferences = getSharedPreferences("MyPrefs", 0);
                preferences.edit().clear().commit();

                Intent in = new Intent(activity, MainActivity.class);
                activity.startActivity(in);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
