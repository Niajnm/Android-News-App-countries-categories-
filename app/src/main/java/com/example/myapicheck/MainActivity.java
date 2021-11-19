package com.example.myapicheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    TabLayout tabLayout;
    ListView listView;
    Spinner spinner;
    ProgressBar progressBar;
    ArrayAdapter<String> country;
    CustomAdapter customAdapter;
    String api = "faacf814bdd640c7b9dfb3f98bbf5cb3";
    String URL = "https://newsapi.org/v2/top-headlines";
    String city, category;
    String[] countryarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("News Feed");
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listViwe_id);
        spinner = findViewById(R.id.spinner_id);
        tabLayout = findViewById(R.id.tablayout_id);
        progressBar = findViewById(R.id.progreesBar_id);
        countryarray = getResources().getStringArray(R.array.country_name);
        country = new ArrayAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, countryarray);
        spinner.setAdapter(country);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    category = "general";
                    request();
                } else if (tab.getPosition() == 1) {
                    category = "sports";
                    request();
                } else if (tab.getPosition() == 2) {
                    category = "science";
                    request();
                } else if (tab.getPosition() == 3) {
                    category = "business";
                    request();
                } else if (tab.getPosition() == 4) {
                    category = "entertainment";
                    request();
                } else if (tab.getPosition() == 5) {
                    category = "health";
                    request();
                } else if (tab.getPosition() == 6) {
                    category = "technology";
                    request();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    protected void onResume() {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, countryarray[position] + " selected", Toast.LENGTH_SHORT).show();
                countrySet(countryarray[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menuShare_id) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/app");
            String subject = "This is first app";
            String head = " Download this app - com.example.fullcountryapplication";
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, head);
            startActivity(Intent.createChooser(intent, "Share with"));
            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
        }

        if (item.getItemId() == R.id.menuSetting_id) {
            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.menuSearch_id) {
        }
        if (item.getItemId() == R.id.menuFeedback_id) {
            Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Feedback", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.menuAbout_id) {
            Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void tabselection() {
    }

    private void countrySet(String s) {
        if (s.equals("USA")) {
            city = "us";
            request();
        } else if (s.equals("India")) {
            city = "in";
            request();
        } else if (s.equals("China")) {
            city = "cn";
            request();
        } else if (s.equals("Argentina")) {
            city = "ar";
            request();
        } else if (s.equals("Hongkong")) {
            city = "hk";
            request();
        } else if (s.equals("Canada")) {
            city = "ca";
            request();
        } else if (s.equals("Japan")) {
            city = "jp";
            request();
        } else {
            Toast.makeText(MainActivity.this, "This Country is Developing ", Toast.LENGTH_SHORT).show();
        }
    }

    public void request() {
        RequestParams params = new RequestParams();
        params.put("country", city);
        params.put("category", category);
        //  params.put("q", category);
        params.put("apiKey", api);
        doNetworkPatch(params);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void doNetworkPatch(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // super.onSuccess(statusCode, headers, response);
                //Log.d(TAG, "onSuccess: " + response.toString());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Data Get Success !", Toast.LENGTH_SHORT).show();
                ArrayList<Data> wdata = fromjson(response);
//                for (int i = 0; i < wdata.size(); i++) {
//                    Log.d(TAG, "Title: " + wdata.get(i).head);
//                    Log.d(TAG, "Description: " + wdata.get(i).descib);
//                }

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MainActivity.this, webActivity.class);

                        intent.putExtra("url", wdata.get(position).singleUrl);
                        startActivity(intent);
                    }
                });
                customAdapter = new CustomAdapter(MainActivity.this, wdata);
                listView.setAdapter(customAdapter);
                ;
                //textView.setText(wdata.getHead());
                //textVdesc.setText(wdata.getDescib());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(MainActivity.this, "Failed: " + errorResponse.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public ArrayList<Data> fromjson(JSONObject jsonObject) {
        try {
            /*Data newsdata = new Data();
            newsdata.head = jsonObject.getJSONArray("articles").getJSONObject(0).getString("title");
            newsdata.descib = jsonObject.getJSONArray("articles").getJSONObject(0).getString("description");
*/
            ArrayList<Data> newsArrayList = new ArrayList<>();
            JSONArray jsonArrayList = jsonObject.getJSONArray("articles");
            for (int i = 0; i < jsonArrayList.length(); i++) {
                Data news = new Data();
                news.head = jsonArrayList.getJSONObject(i).getString("title");
                news.descib = jsonArrayList.getJSONObject(i).getString("description");
                news.imgurl = jsonArrayList.getJSONObject(i).getString("urlToImage");
                news.singleUrl = jsonArrayList.getJSONObject(i).getString("url");
                newsArrayList.add(news);
            }
            return newsArrayList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}