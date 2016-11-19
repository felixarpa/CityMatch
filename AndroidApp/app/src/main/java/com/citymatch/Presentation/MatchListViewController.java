package com.citymatch.Presentation;


import android.content.Context;
import android.content.Intent;
import android.nakima.requestslibrary.OnFailure;
import android.nakima.requestslibrary.OnSuccess;
import android.nakima.requestslibrary.Response;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.citymatch.ApiService.CityMatchService;
import com.citymatch.Domain.Models.MatchItem;
import com.citymatch.Domain.Models.MatchList;
import com.citymatch.R;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MatchListViewController extends AppCompatActivity {

    private ListView listView;
    private Context context;
    private LayoutInflater inflater;
    private MatchList matchList;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_list_view);

        //TODO getUserID

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
                    intent.putExtra("userID", userID);
                    intent.putExtra("cityID", item.cityID);
                    startActivity(intent);
                }
            }
        });

        CityMatchService.getInstance().getMatchList(userID, new MyOnSuccess(), new MyOnFailure());
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

    private class MyOnSuccess implements OnSuccess {

        @Override
        public void onSuccess(Response response) {
            String json = response.getMessage().toString();
            Gson gson = new Gson();
            matchList = gson.fromJson(json, MatchList.class);
            MyArrayAdapter adapter = new MyArrayAdapter(getApplicationContext(), matchList);
            listView.setAdapter(adapter);
        }
    }

    private class MyOnFailure implements OnFailure {

        @Override
        public void onFailure(Response response) {
            Toast.makeText(getApplicationContext(), "Database connection error = " + response.getStatusCode(), Toast.LENGTH_SHORT).show();
        }
    }

    private class MyArrayAdapter extends ArrayAdapter<MatchItem> {
        private final MatchList matchList;

        public MyArrayAdapter(Context context, MatchList matchList) {
            super(context, 0, matchList);
            this.matchList = matchList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return matchList.get(position).populateListItem(inflater, parent);
        }
    }
}
