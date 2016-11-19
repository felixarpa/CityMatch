package com.citymatch.Presentation;


import android.nakima.requestslibrary.OnFailure;
import android.nakima.requestslibrary.OnSuccess;
import android.nakima.requestslibrary.Response;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.citymatch.ApiService.CityMatchService;
import com.citymatch.R;

public class MatchListViewController extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_list_view);

        listView = (ListView) findViewById(R.id.list);

        CityMatchService.getInstance().getMatchList(new MyOnSuccess(), new MyOnFailure());
    }

    private class MyOnSuccess implements OnSuccess {

        @Override
        public void onSuccess(Response response) {
        }
    }

    private class MyOnFailure implements OnFailure {

        @Override
        public void onFailure(Response response) {

        }
    }
}
