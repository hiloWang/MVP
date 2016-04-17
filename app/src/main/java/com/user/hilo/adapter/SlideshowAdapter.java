package com.user.hilo.adapter;

import android.content.Context;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.user.hilo.R;
import com.user.hilo.core.BaseRecyclerViewAdapter;
import com.user.hilo.core.BaseRecyclerViewHolder;
import com.user.hilo.utils.DateUtils;
import com.user.hilo.utils.ToastUtils;

import java.util.Date;

/**
 * Created by Administrator on 2016/4/17.
 */
public class SlideshowAdapter extends BaseRecyclerViewAdapter {

    private ImageView mDailyIv;
    private TextView mDailyTitleTv, mDailyDateTv;

    public static final int VIEW_TYPE_DEFAULT = 1;
    private Context context;
    private final int[] mipmapDaily = new int[]{R.mipmap.center_1, R.mipmap.center_2, R.mipmap.center_3, R.mipmap.center_4, R.mipmap.center_5};

    public SlideshowAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int[] getItemLayouts() {
        return new int[]{
                R.layout.item_slideshow_activity
        };
    }

    @Override
    public void onBindRecycleViewHolder(BaseRecyclerViewHolder viewHolder, int position) {
        int itemViewType = getRecycleViewItemType(position);
        switch (itemViewType) {
            case VIEW_TYPE_DEFAULT:
                bindDefault(viewHolder, position);
                break;
        }
    }

    @Override
    public int getRecycleViewItemType(int position) {
        return VIEW_TYPE_DEFAULT;
    }

    private void bindDefault(BaseRecyclerViewHolder viewHolder, int position) {
        String dailyBean = getItemByPosition(position);
        if (dailyBean == null) return;

        mDailyTitleTv = viewHolder.findViewById(R.id.daily_title_tv);
        mDailyDateTv = viewHolder.findViewById(R.id.daily_date_tv);
        mDailyIv = viewHolder.findViewById(R.id.daily_iv);

        mDailyTitleTv.setText("测试内容" + dailyBean + ": 这脑洞太大我无力承受。[笑cry]");
        mDailyDateTv.setText(DateUtils.date2yyyyMMdd(new Date()));
        mDailyIv.setScaleX(0.f);
        mDailyIv.setScaleY(0.f);
        Picasso.with(context)
                .load(mipmapDaily[(int) (Math.random() * 5)]).into(mDailyIv, new Callback() {
            @Override
            public void onSuccess() {
                mDailyIv.animate()
                        .scaleX(1.f).scaleY(1.f)
                        .setInterpolator(new OvershootInterpolator())
                        .setDuration(1000)
                        .start();
            }

            @Override
            public void onError() {
                ToastUtils.showCenter(context, "Error" + position);
            }
        });
    }
}
