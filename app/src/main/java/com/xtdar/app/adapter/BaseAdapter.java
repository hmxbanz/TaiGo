package com.xtdar.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by AMing on 15/11/13.
 * Company RongCloud
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

    protected Context mContext;
    protected List<T> mList;
    protected LayoutInflater mInflater;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public BaseAdapter(Context context) {
        this(context, new ArrayList());
    }

    public BaseAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.mList = data;
        mInflater = LayoutInflater.from(mContext);
    }

    public Context getContext() {
        return this.mContext;
    }

    public void addData(T data) {
        if (data != null) {
            this.mList.add(data);
        }
    }
    public void addData(T data, int position) {
        if (data != null)
        this.mList.add(position, data);
    }
    public void addData(Collection<T> data) {
        if (data != null) {
            this.mList.addAll(data);
        }
    }
    public void addData(T ... collection) {

        for (T t : collection) {
            this.mList.add(t);
        }
    }
    public void addData(int index, Collection<T> data) {
        if (data != null) {
            this.mList.addAll(index, data);
        }
    }

    public void removeData(Collection<T> data) {
        if (data != null) {
            this.mList.removeAll(data);
        }
    }
    public void removeAll() {
        this.mList.clear();
    }
    public void remove(T data) {
        if (data != null) {
            this.mList.remove(data);
        }
    }
    public void remove(int position) {
        this.mList.remove(position);
    }

    public List<T> subData(int index, int count) {
        return this.mList.subList(index, index + count);
    }

    @Override
    public int getCount() {
        if (mList == null)
            return 0;

        return this.mList.size();
    }

    @Override
    public T getItem(int position) {
        if (mList == null)
            return null;

        if (position >= mList.size())
            return null;

        return this.mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
/*    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = newView(mContext, position, parent);
        }
        bindView(view, position, getItem(position));
        return view;
    }*/

    public List<T> getmList() {
        return mList;
    }
    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(View view, int id) {
        return (T) view.findViewById(id);
    }
    public int findPosition(T message) {
        int index = getCount();
        int position = -1;
        while (index-- > 0) {
            if (message.equals(getItem(index))) {
                position = index;
                break;
            }
        }
        return position;
    }
    public int findPosition(long id) {
        int index = getCount();
        int position = -1;
        while (index-- > 0) {
            if (getItemId(index) == id) {
                position = index;
                break;
            }
        }
        return position;
    }
    public void setmList(List<T> mList) {
        this.mList = mList;
    }

//    protected abstract View newView(Context context, int position, ViewGroup group);
//    protected abstract void bindView(View v, int position, T data);
}
