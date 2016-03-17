package com.user.hilo.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.user.hilo.R;
import com.user.hilo.interfaces.OnNoDoubleClickListener;
import com.user.hilo.interfaces.RecyclerViewOnItemClickListener;
import com.user.hilo.view.SquaredFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/3/17.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<String> items;

    public MainRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void updateItems(List<String> items) {
        if (items == null)
            this.items = new ArrayList<>();
        this.items = items;
    }

    public void updateItems(List<String> items, boolean animated) {
        if (items == null)
            this.items = new ArrayList<>();
        this.items = items;
        if (animated) {
            notifyItemRangeChanged(0, items.size());
        } else {
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_main_activity, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        initEvents(holder);
        holder.bindData(context, items, R.color.design_bgcolor, position);
    }

    private void initEvents(final ViewHolder holder) {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                protected void onNoDoubleClickListener(View v) {
                    onItemClickListener.onItemClicked(v, holder.getLayoutPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClicked(v, holder.getLayoutPosition());
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (items == null)
            return 0;
        return items.size();
    }

    private RecyclerViewOnItemClickListener onItemClickListener;
    public void setRecyclerOnItemClickedListener(RecyclerViewOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvFeedTitle)
        TextView mTvFeedTitle;
        @Bind(R.id.ivFeedCenter)
        ImageView mIvFeedCenter;
        @Bind(R.id.vImageRoot)
        SquaredFrameLayout mVImageRoot;
        @Bind(R.id.ivFeedBottom)
        ImageView mIvFeedBottom;
        @Bind(R.id.btnLike)
        ImageButton mBtnLike;
        @Bind(R.id.btnComments)
        ImageButton mBtnComments;
        @Bind(R.id.btnMore)
        ImageButton mBtnMore;
        @Bind(R.id.tsLikesCounter)
        TextSwitcher mTsLikesCounter;
        @Bind(R.id.card_view)
        CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void bindData(Context context, List<String> items, int bgColor, int position) {
            mTvFeedTitle.setText(items.get(position));
            itemView.setBackgroundColor(bgColor);
            Picasso.with(context).load(R.mipmap.chip).into(mIvFeedCenter);
        }
    }
}
