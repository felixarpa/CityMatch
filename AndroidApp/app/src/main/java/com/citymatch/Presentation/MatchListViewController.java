package com.citymatch.Presentation;


import android.content.Context;
import android.nakima.requestslibrary.OnFailure;
import android.nakima.requestslibrary.OnSuccess;
import android.nakima.requestslibrary.Response;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.citymatch.ApiService.CityMatchService;
import com.citymatch.Domain.Models.MatchItem;
import com.citymatch.Domain.Models.MatchList;
import com.citymatch.R;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MatchListViewController extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_list_view);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

        listView = (ListView) findViewById(R.id.list);

        CityMatchService.getInstance().getMatchList(new MyOnSuccess(), new MyOnFailure());
    }

    //TODO implement onResume & onPause

    private class MyOnSuccess implements OnSuccess {

        @Override
        public void onSuccess(Response response) {
            //String json = response.getMessage().toString();
            String json = "[\n" +
                    "  {\n" +
                    "    \"country\": \"switzerland\",\n" +
                    "    \"name\": \"ginebra\",\n" +
                    "    \"imageURL\": \"http://3dn6i8277y3e2terx93zyfk5.wpengine.netdna-cdn.com/wp-content/uploads/2016/08/epfl_lake.jpg\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"country\": \"spain\",\n" +
                    "    \"name\": \"bcn\",\n" +
                    "    \"imageURL\": \"http://3dn6i8277y3e2terx93zyfk5.wpengine.netdna-cdn.com/wp-content/uploads/2016/08/epfl_lake.jpg\"\n" +
                    "  }\n" +
                    "]";
            Gson gson = new Gson();
            MatchList matchList = gson.fromJson(json, MatchList.class);
            for(MatchItem item : matchList) {
                Log.d("MatchList", item.imageURL);
            }
            MyArrayAdapter adapter = new MyArrayAdapter(getApplicationContext(), matchList);
            listView.setAdapter(adapter);
        }
    }

    private class MyOnFailure implements OnFailure {

        @Override
        public void onFailure(Response response) {

        }
    }

    private class MyArrayAdapter extends ArrayAdapter<MatchItem> {
        private final Context context;
        private final MatchList matchList;

        public MyArrayAdapter(Context context, MatchList matchList) {
            super(context, 0, matchList);
            this.context = context;
            this.matchList = matchList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.e("MatchList", ""+position);
            return matchList.get(position).populateListItem(context, parent);
        }
    }
}
