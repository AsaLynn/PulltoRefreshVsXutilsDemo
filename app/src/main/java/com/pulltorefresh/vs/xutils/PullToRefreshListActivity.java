/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.pulltorefresh.vs.xutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.demonstrate.DemonstrateUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public final class PullToRefreshListActivity extends AppCompatActivity/*ListActivity*/ {

    static final int MENU_MANUAL_REFRESH = 0;
    static final int MENU_DISABLE_SCROLL = 1;
    static final int MENU_SET_MODE = 2;
    static final int MENU_DEMO = 3;
    private int page = 1;

    private PullToRefreshListView mPullRefreshListView;
    private ArrayAdapter<String> mAdapter;
    private Gson gson;
    private ListView actualListView;
    private FoodAdapter foodAdapter;
    private List<ListInfo.DataBean> list = new ArrayList<>();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr_list);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("菜单一");
        supportActionBar.setSubtitle("好菜好吃好美味!");


        gson = new GsonBuilder().create();
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);

        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                requestData();

                // Do work to refresh the list here.
            }
        });

        // Add an end-of-list listener
        mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
                Toast.makeText(PullToRefreshListActivity.this, "已经到达最底部了!", Toast.LENGTH_SHORT).show();
            }
        });

        actualListView = mPullRefreshListView.getRefreshableView();
        actualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                int position = pos-1;
                Intent intent = new Intent(PullToRefreshListActivity.this, FoodDetailsActivity.class);
                intent.putExtra(UC.URL_IMG, list.get(position).getPic());
                intent.putExtra(UC.TITLE, list.get(position).getTitle());
                intent.putExtra(UC.ID, list.get(position).getId());
                intent.putExtra(UC.FOOD_STR, list.get(position).getFood_str());
                startActivity(intent);
            }
        });

        // Need to use the Actual ListView when registering for Context Menu
        registerForContextMenu(actualListView);

        /**
         * Add Sound Event Listener
         */
        SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(this);
        soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
        soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
        soundListener.addSoundEvent(State.REFRESHING, R.raw.refreshing_sound);
        mPullRefreshListView.setOnPullEventListener(soundListener);

        requestData();
        //requestData2();
    }

    private void requestData2() {
        x.http().post(new RequestParams() {
            {
                setUri("http://www.qubaobei.com/ios/cf/dish_list.php");
                addParameter("stage_id", "1");
                addParameter("limit", "10");
                addParameter("page", page);
            }
        }, new MyCallback() {
            @Override
            public void onSuccess(String result) {
                DemonstrateUtil.showToastResult(PullToRefreshListActivity.this, "刷新成功!");
                DemonstrateUtil.showLogResult("onSuccess");
                page++;
                ListInfo listInfo = gson.fromJson(result, ListInfo.class);
                List<ListInfo.DataBean> data = listInfo.getData();
                if (list.size() != 0) {
                    list.clear();
                }
                list.addAll(data);
                if (null == foodAdapter) {
//                    foodAdapter = new FoodAdapter(list);
                    foodAdapter = new FoodAdapter(list, PullToRefreshListActivity.this);
                    actualListView.setAdapter(foodAdapter);
                } else {
                    foodAdapter.notifyDataSetChanged();
                }
                mPullRefreshListView.onRefreshComplete();
            }
        });
    }

    private void requestData() {
        //?stage_id=1&limit=10&page=1
        x.http().get(new RequestParams() {
            {
                setUri("http://www.qubaobei.com/ios/cf/dish_list.php");
                addParameter("stage_id", "1");
                addParameter("limit", "10");
                addParameter("page", page);
            }
        }, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                DemonstrateUtil.showLogResult("onCache");
                return false;
            }

            @Override
            public void onSuccess(String result) {
                DemonstrateUtil.showToastResult(PullToRefreshListActivity.this, "刷新成功!");
                DemonstrateUtil.showLogResult("onSuccess");
                //DemonstrateUtil.showToastResult(PullToRefreshListActivity.this, result);
                page++;
                ListInfo listInfo = gson.fromJson(result, ListInfo.class);
                List<ListInfo.DataBean> data = listInfo.getData();
                if (list.size() != 0) {
                    list.clear();
                }
                list.addAll(data);
                if (null == foodAdapter) {
//                    foodAdapter = new FoodAdapter(list);
                    foodAdapter = new FoodAdapter(list, PullToRefreshListActivity.this);
                    actualListView.setAdapter(foodAdapter);
                } else {
                    foodAdapter.notifyDataSetChanged();
                }
                mPullRefreshListView.onRefreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                DemonstrateUtil.showLogResult("onError");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                DemonstrateUtil.showLogResult("onCancelled");
            }

            @Override
            public void onFinished() {
                DemonstrateUtil.showLogResult("onFinished");
            }
        });
    }

    String[] contents = {"不好吃!", "味道一般!", "好吃!", "超赞!", "再来1份!"};

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
//        ListInfo.DataBean item = (ListInfo.DataBean) getListView().getItemAtPosition(info.position);
        //mPullRefreshListView
        ListInfo.DataBean item = list.get(info.position);
        menu.setHeaderTitle("菜名: " + item.getTitle());
        menu.setHeaderIcon(R.mipmap.ic_launcher_round);
        for (int i = 0; i < contents.length; i++) {
            menu.add(0, Menu.FIRST + i, i, contents[i]);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Menu.FIRST + 0:
            case Menu.FIRST + 1:
            case Menu.FIRST + 2:
            case Menu.FIRST + 3:
            case Menu.FIRST + 4:
                DemonstrateUtil.showToastResult(this, contents[item.getOrder()]);
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem disableItem = menu.findItem(MENU_DISABLE_SCROLL);
        disableItem
                .setTitle(mPullRefreshListView.isScrollingWhileRefreshingEnabled() ? "Disable Scrolling while Refreshing"
                        : "Enable Scrolling while Refreshing");

        MenuItem setModeItem = menu.findItem(MENU_SET_MODE);
        setModeItem.setTitle(mPullRefreshListView.getMode() == Mode.BOTH ? "Change to MODE_FROM_START"
                : "Change to MODE_PULL_BOTH");

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case MENU_MANUAL_REFRESH:
                //new GetDataTask().execute();
                mPullRefreshListView.setRefreshing(false);
                break;
            case MENU_DISABLE_SCROLL:
                mPullRefreshListView.setScrollingWhileRefreshingEnabled(!mPullRefreshListView
                        .isScrollingWhileRefreshingEnabled());
                break;
            case MENU_SET_MODE:
                mPullRefreshListView.setMode(mPullRefreshListView.getMode() == Mode.BOTH ? Mode.PULL_FROM_START
                        : Mode.BOTH);
                break;
            case MENU_DEMO:
                mPullRefreshListView.demo();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_MANUAL_REFRESH, 0, "Manual Refresh");
        menu.add(0, MENU_DISABLE_SCROLL, 1,
                mPullRefreshListView.isScrollingWhileRefreshingEnabled() ? "Disable Scrolling while Refreshing"
                        : "Enable Scrolling while Refreshing");
        menu.add(0, MENU_SET_MODE, 0, mPullRefreshListView.getMode() == Mode.BOTH ? "Change to MODE_PULL_DOWN"
                : "Change to MODE_PULL_BOTH");
        menu.add(0, MENU_DEMO, 0, "Demo");
        return super.onCreateOptionsMenu(menu);
    }


}
