package com.user.hilo.interfaces;

import android.view.View;

/**
 * Created by Administrator on 2016/3/17.
 */
public interface RecyclerViewOnItemClickListener {

    void onItemClicked(View view, int position);

    void onItemLongClicked(View view, int position);
}
