package com.user.hilo.adapter;

import android.content.Context;
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
import com.user.hilo.activity.view.MainActivity;
import com.user.hilo.entitys.MainEntity;
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
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.FeedViewHolder> {

    public static final String ACTION_LICK_IMAGE_CLICKED = "action_lick_image_clicked";
    public static final String ACTION_LICK_BUTTON_CLICKED = "action_lick_button_clicked";

    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final int VIEW_TYPE_LOADER = 2;

    private Context context;
    private List<MainEntity> items;

    private OnFeedItemClickListener onFeedItemClickListener;
    private boolean showLoadingView = false;

    public MainRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void updateItems(List<MainEntity> items, boolean animated) {
        this.items = items;
        if (items == null) this.items = new ArrayList<>();
        if (animated) {
            notifyItemRangeChanged(0, items.size());
        } else {
            notifyDataSetChanged();
        }
    }

    public void addItem(MainEntity data, int position) {
        items.add(position, data);
        notifyItemInserted(position);
    }

    public void delItem(int position) {
        if (items != null && items.size() > 0) {
            MainEntity data = items.get(position);
            if (data != null) {
                items.remove(data);
//                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DEFAULT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_main_activity, parent, false);
            FeedViewHolder cellFeedViewHolder = new FeedViewHolder(view);
            setupClickableViews(view, cellFeedViewHolder);
            return cellFeedViewHolder;
        } else if (viewType == VIEW_TYPE_LOADER) {

        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (showLoadingView && position == 0) {
            return VIEW_TYPE_LOADER;
        } else {
            return VIEW_TYPE_DEFAULT;
        }
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        holder.bindData(context, items.get(position), position);

        if (getItemViewType(position) == VIEW_TYPE_LOADER) {

        }
    }

    public void setOnFeedItemClickListener(OnFeedItemClickListener onFeedItemClickListener) {
        this.onFeedItemClickListener = onFeedItemClickListener;
    }

    private void setupClickableViews(final View view, final FeedViewHolder cellFeedViewHolder) {
        cellFeedViewHolder.mBtnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFeedItemClickListener.onMoreClick(v, cellFeedViewHolder.getLayoutPosition());
            }
        });
        cellFeedViewHolder.mBtnComments.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClickListener(View v) {
                onFeedItemClickListener.onCommentsClick(view, cellFeedViewHolder.getLayoutPosition());
            }
        });
        cellFeedViewHolder.mBtnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = cellFeedViewHolder.getAdapterPosition();
                if (adapterPosition != -1) {
                    items.get(adapterPosition).likesCount++;
                    notifyItemChanged(adapterPosition, ACTION_LICK_BUTTON_CLICKED);
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).showLikedSnackbar();
                    }
                }
            }
        });
        cellFeedViewHolder.mIvFeedCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = cellFeedViewHolder.getAdapterPosition();
                if (adapterPosition != -1) {
                    items.get(adapterPosition).likesCount++;
                    notifyItemChanged(adapterPosition, ACTION_LICK_IMAGE_CLICKED);
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).showLikedSnackbar();
                    }
                }
            }
        });
        cellFeedViewHolder.mTvFeedTitle.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClickListener(View v) {
                onFeedItemClickListener.onItemClicked(v, cellFeedViewHolder.getLayoutPosition());
            }
        });
        cellFeedViewHolder.mTvFeedTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onFeedItemClickListener.onItemLongClicked(v, cellFeedViewHolder.getLayoutPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (items == null)
            return 0;
        return items.size();
    }

    @Override
    public void onViewDetachedFromWindow(FeedViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {

        private MainEntity entity;

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
        @Bind(R.id.vBgLike)
        View mVBgLike;
        @Bind(R.id.ivLike)
        ImageView mIvLike;

        public FeedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public MainEntity getEntity() {
            return entity;
        }


        public void bindData(Context context, MainEntity data, int position) {
            entity = data;
            mTvFeedTitle.setText(data.getTitleSubjectName());
            Picasso.with(context).load(data.getImageMipmap()).into(mIvFeedCenter);
            mBtnLike.setImageResource(data.isLiked() ? R.mipmap.ic_heart_red : R.mipmap.ic_heart_outline_grey);
            mTsLikesCounter.setCurrentText(mVImageRoot.getResources().getQuantityString(
                    R.plurals.likes_count, data.likesCount, data.likesCount
            ));
        }
    }

    public interface OnFeedItemClickListener extends RecyclerViewOnItemClickListener {
        void onCommentsClick(View v, int position);

        void onMoreClick(View v, int position);

        void onProfileClick(View v);

        @Override
        void onItemClicked(View view, int position);

        @Override
        void onItemLongClicked(View view, int position);
    }
}
