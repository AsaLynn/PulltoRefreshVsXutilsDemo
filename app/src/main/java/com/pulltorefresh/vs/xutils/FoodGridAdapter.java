package com.pulltorefresh.vs.xutils;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by think on 2017/11/22.
 */

public class FoodGridAdapter extends BaseAdapter {


    private List<ListInfo.DataBean> mList;

    public FoodGridAdapter(List<ListInfo.DataBean> list) {
        mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_grid_food, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.titleTv.setText(mList.get(position).getTitle());
        viewHolder.foodStrTv.setText(mList.get(position).getFood_str());
        Uri uri = Uri.parse(mList.get(position).getPic());
        viewHolder.mainSdv.setImageURI(uri);
        return convertView;
    }

    static class ViewHolder {
        protected SimpleDraweeView mainSdv;
        protected TextView titleTv;
        protected TextView foodStrTv;

        ViewHolder(View rootView) {
            initView(rootView);
        }

        private void initView(View rootView) {
            mainSdv = (SimpleDraweeView) rootView.findViewById(R.id.main_sdv);
            titleTv = (TextView) rootView.findViewById(R.id.title_tv);
            foodStrTv = (TextView) rootView.findViewById(R.id.food_str_tv);
        }
    }
}
