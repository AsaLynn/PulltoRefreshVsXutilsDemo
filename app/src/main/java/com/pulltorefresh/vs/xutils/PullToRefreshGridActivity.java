package com.pulltorefresh.vs.xutils;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demonstrate.DemonstrateUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PullToRefreshGridActivity extends AppCompatActivity {

    private int page = 1;
    private LinkedList<String> mListItems;
    private PullToRefreshGridView mPullRefreshGridView;
    private GridView mGridView;
    private ArrayAdapter<String> mAdapter;
    private Gson gson;
    private FoodGridAdapter foodAdapter;
    private boolean isRefresh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh_grid);

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("菜单二");
        supportActionBar.setSubtitle("好菜好吃好美味!");

        gson = new GsonBuilder().create();

        mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.pull_refresh_grid);
        mGridView = mPullRefreshGridView.getRefreshableView();

        //mPullRefreshGridView.setRefreshing(false);
        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                Toast.makeText(PullToRefreshGridActivity.this, "刷新成功!", Toast.LENGTH_SHORT).show();
                //new GetDataTask().execute();
                isRefresh = true;
                page = 1;
                requestData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                Toast.makeText(PullToRefreshGridActivity.this, "加载更多!", Toast.LENGTH_SHORT).show();
                //new GetDataTask().execute();
                if (list.size() != 0) {
                    isRefresh = false;
                    page++;
                    requestData();
                }
            }

        });

//        mListItems = new LinkedList<String>();

        TextView tv = new TextView(this);
        tv.setGravity(Gravity.CENTER);
        tv.setText("Empty View, Pull Down/Up to Add Items");
        mPullRefreshGridView.setEmptyView(tv);

//        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListItems);
//        mGridView.setAdapter(mAdapter);

        //requestData();

        mPullRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PullToRefreshGridActivity.this, FoodDetailsActivity.class);
                intent.putExtra(UC.URL_IMG, list.get(position).getPic());
                intent.putExtra(UC.TITLE, list.get(position).getTitle());
                intent.putExtra(UC.ID, list.get(position).getId());
                intent.putExtra(UC.FOOD_STR, list.get(position).getFood_str());
                startActivity(intent);
            }
        });
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            return mStrings;
        }

        @Override
        protected void onPostExecute(String[] result) {
            mListItems.addFirst("Added after refresh...");
            mListItems.addAll(Arrays.asList(result));
            mAdapter.notifyDataSetChanged();

            // Call onRefreshComplete when the list has been refreshed.
            mPullRefreshGridView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }


    private String[] mStrings = {"Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler"};

    private List<ListInfo.DataBean> list = new ArrayList<>();

    private void requestData() {
        //?stage_id=1&limit=10&page=1
        x.http().get(new RequestParams() {
            {
                setUri("http://www.qubaobei.com/ios/cf/dish_list.php");
                addParameter("stage_id", "1");
                addParameter("limit", "10");
                addParameter("page", page);
            }
        }, new MyCallback() {

            @Override
            public void onSuccess(String result) {
                //DemonstrateUtil.showToastResult(PullToRefreshGridActivity.this, "刷新成功!");
                DemonstrateUtil.showLogResult("onSuccess");
                ListInfo listInfo = gson.fromJson(result, ListInfo.class);
                List<ListInfo.DataBean> data = listInfo.getData();

                if (isRefresh){
                    if (list.size() != 0) {
                        list.clear();
                    }
                    list.addAll(data);
                }else{
                    list.addAll(data);
                }



                if (null == foodAdapter) {
                    foodAdapter = new FoodGridAdapter(list);
                    mGridView.setAdapter(foodAdapter);
                } else {
                    foodAdapter.notifyDataSetChanged();
                }
                mPullRefreshGridView.onRefreshComplete();
            }

        });
    }
}
