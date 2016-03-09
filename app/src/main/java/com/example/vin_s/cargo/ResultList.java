package com.example.vin_s.cargo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
    private Post selectedPost;
    private DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);
        loop = (LinearLayout) findViewById(R.id.loopLayout);
        loop.setOrientation(LinearLayout.VERTICAL);
        loop.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        posts = (List<Post>) getIntent().getSerializableExtra("resultList");
        int num = posts.size();
        if(posts.isEmpty()) {
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
        }
        else {
            for(int l=0; l<num; l++)
            {
                String postID = (String) posts.get(l).getId();
                try {
                    selectedPost = dbHelper.getPostByID(postID);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String org = (String)posts.get(l).getOrigin();
                String des = (String)posts.get(l).getDest();
                String duration = (String) posts.get(l).getDuration();
                String pTitle = (String)posts.get(l).getTitle();
                String od = org + " - " + des + ", " + duration;

                //child include child1, view
                LinearLayout child = new LinearLayout(this);
                child.setOrientation(LinearLayout.VERTICAL);
                child.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                //set onclick action
                OnClickListener button_click = new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPostPage(v);
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
                text3.setText((String) posts.get(l).getSlogan());
                text3.setTextSize(13);

                View view = new View(this);
                LinearLayout.LayoutParams lpV = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,2);
                lpV.setMargins(0,20,0, 20);
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

        SharedPreferences prefs = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userName = prefs.getString("nameKey", null);

        if(userName == null){
            LinearLayout tabBarLayout= (LinearLayout)this.findViewById(R.id.resultlist_tab_bar);
            tabBarLayout.setVisibility(LinearLayout.GONE);
            ScrollView searchScroll = (ScrollView)this.findViewById(R.id.resultlist_scrollView);
            searchScroll.setMinimumHeight(500);
        }
    }

    /** Called when the user clicks the block */
    public void showPostPage(View view) {
        Intent intent = new Intent(this, PostPage.class);
        intent.putExtra("selectPostOrNot", true);
        intent.putExtra("selectedPost" , selectedPost);
        startActivity(intent);
    }

    public void redirectToProfile(View view){
        Intent intent = new Intent(this, MyProfile.class);
        startActivity(intent);
    }

    public void redirectToSearch(View view){
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    public void redirectToCreatePost(View view){
        Intent intent = new Intent(this, PostActivity.class);
        startActivity(intent);
    }

    public void redirectToHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
