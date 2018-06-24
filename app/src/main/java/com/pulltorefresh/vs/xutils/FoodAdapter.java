package com.pulltorefresh.vs.xutils;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by think on 2017/11/17.
 */

public class FoodAdapter extends BaseAdapter {

    private List<ListInfo.DataBean> mData;
    private Context mContext;

    public FoodAdapter(List<ListInfo.DataBean> data) {
        mData = data;

    }

    public FoodAdapter(List<ListInfo.DataBean> data, Context context) {
        this(data);
        mContext = context;
    }


    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_food, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.titleTv.setText(mData.get(position).getTitle());
        viewHolder.foodStrTv.setText(mData.get(position).getFood_str());
        viewHolder.numTv.setText(mData.get(position).getNum()+"");
        viewHolder.collectNumTv.setText(mData.get(position).getId());
        Uri uri = Uri.parse(mData.get(position).getPic());
        viewHolder.mainSdv.setImageURI(uri);
        return convertView;
    }

    static

    class ViewHolder {
        protected SimpleDraweeView mainSdv;
        protected TextView titleTv;
        protected TextView foodStrTv;
        protected TextView numTv;
        protected TextView collectNumTv;

        ViewHolder(View rootView) {
            initView(rootView);
        }

        private void initView(View rootView) {
            mainSdv = (SimpleDraweeView) rootView.findViewById(R.id.main_sdv);
            titleTv = (TextView) rootView.findViewById(R.id.title_tv);
            foodStrTv = (TextView) rootView.findViewById(R.id.food_str_tv);
            numTv = (TextView) rootView.findViewById(R.id.num_tv);
            collectNumTv = (TextView) rootView.findViewById(R.id.collect_num_tv);
        }
    }
}
