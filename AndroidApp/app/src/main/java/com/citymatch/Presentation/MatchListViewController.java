package com.citymatch.Presentation;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.citymatch.ApiService.Service;
import com.citymatch.Domain.Models.MatchItem;
import com.citymatch.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchListViewController extends AppCompatActivity {

    private ListView listView;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MatchItem> matchList;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_list_view);

        userID = Service.getUserId(getApplicationContext());

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        inflater = getLayoutInflater();
        context = getApplicationContext();

        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(matchList != null) {
                    MatchItem item = matchList.get(position);
                    Intent intent = new Intent(context, MatchViewController.class);
                    intent.putExtra(IntentAttribute.USER_ID.toString(), userID);
                    intent.putExtra(IntentAttribute.CITY_ID.toString(), item.getId());
                    startActivity(intent);
                }
            }
        });

        Service.getApiService().getMatchList(userID).enqueue(new MatchListCallback());
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
    }

    @Override
    protected void onPause() {
        ImageLoader.getInstance().destroy();
        super.onPause();
    }

    private class MatchListCallback implements Callback<ArrayList<MatchItem>> {
        @Override
        public void onResponse(Call<ArrayList<MatchItem>> call, Response<ArrayList<MatchItem>> response) {
            matchList = response.body();
            if(matchList != null) {
                MyArrayAdapter adapter = new MyArrayAdapter(getApplicationContext(), matchList);
                listView.setAdapter(adapter);
            }
        }

        @Override
        public void onFailure(Call<ArrayList<MatchItem>> call, Throwable t) {
            t.printStackTrace();
            Toast.makeText(getApplicationContext(), "Database connection error = " + t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private class MyArrayAdapter extends ArrayAdapter<MatchItem> {
        private final ArrayList<MatchItem> matchList;

        public MyArrayAdapter(Context context, ArrayList<MatchItem> matchList) {
            super(context, 0, matchList);
            this.matchList = matchList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return matchList.get(position).populateListItem(inflater, parent);
        }
    }

    protected enum IntentAttribute {
        USER_ID, CITY_ID;
    }
}
