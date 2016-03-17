package com.user.hilo.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import butterknife.ButterKnife;

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
        this.items = items;
        if (items == null) this.items = new ArrayList<>();
        if (animated) {
            notifyItemRangeChanged(0, items.size());
        } else {
            notifyDataSetChanged();
        }
    }

    public void addItem(String data, int position) {
        items.add(position, data);
        notifyItemInserted(position);
    }

    public void delItem(int position) {
        if (items != null && items.size() > 0) {
            String data = items.get(position);
            if (!TextUtils.isEmpty(data)) {
                items.remove(data);
                notifyItemRemoved(position);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_main_activity, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        initEvents(holder);
        holder.bindData(context, items, position);
    }

    private void initEvents(final ViewHolder holder) {
        if (onItemClickListener != null) {
            holder.mIvFeedCenter.setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                protected void onNoDoubleClickListener(View v) {
                    onItemClickListener.onItemClicked(v, holder.getLayoutPosition());
                }
            });
            holder.mTvFeedTitle.setOnLongClickListener(new View.OnLongClickListener() {
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

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
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
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Context context, List<String> items, int position) {
            mTvFeedTitle.setText(items.get(position));
            Picasso.with(context).load(R.mipmap.chip).into(mIvFeedCenter);
//            itemView.setBackgroundColor(Color.parseColor("#efefef"));
        }


    }
}
