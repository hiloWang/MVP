package com.user.hilo.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.user.hilo.R;
import com.user.hilo.utils.UIUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.animators.ScaleInBottomAnimator;

/**
 * Created by Administrator on 2016/3/18.
 * item 点击触发动画
 */
public class MainAdapterItemAnimator extends ScaleInBottomAnimator {

    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateDecelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4.0f);
    private int lastAddAnimatedItem = -2;

    Map<RecyclerView.ViewHolder, AnimatorSet> likeAnimationsMap = new HashMap<>();
    Map<RecyclerView.ViewHolder, AnimatorSet> heartAnimationsMap = new HashMap<>();

    /**
     * 重用现有ViewHolder，使得其支持Item的内容动画
     *
     * @param viewHolder
     * @return
     */
    @Override
    public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder) {
        return true;
    }

    @NonNull
    @Override
    public ItemHolderInfo recordPreLayoutInformation(@NonNull RecyclerView.State state,
                                                     @NonNull RecyclerView.ViewHolder viewHolder,
                                                     int changeFlags, @NonNull List<Object> payloads) {
        if (changeFlags == FLAG_CHANGED) {
            for (Object payload : payloads) {
                if (payload instanceof String) {
                    return new MainItemHolderInfo((String) payload);
                }
            }
        }
        return super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads);
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        if (holder.getItemViewType() == MainRecyclerAdapter.VIEW_TYPE_DEFAULT) {
            if (holder.getLayoutPosition() > lastAddAnimatedItem) {
                lastAddAnimatedItem++;
                runEnterAnimation((MainRecyclerAdapter.FeedViewHolder) holder);
                return false;
            }
        }
//        dispatchAddFinished(holder);
//        return false;
        return super.animateAdd(holder);
    }

    private void runEnterAnimation(final MainRecyclerAdapter.FeedViewHolder holder) {
        int screenHeight = UIUtils.getScreenHeight(null, holder.itemView.getContext());
        holder.itemView.setTranslationY(screenHeight);
        holder.itemView.animate()
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator(3.f))
                .setDuration(700)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        dispatchAddFinished(holder);
                    }
                }).start();
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder,
                                 RecyclerView.ViewHolder newHolder,
                                 ItemHolderInfo preLayoutInfo,
                                 ItemHolderInfo postLayoutInfo) {
        cancelCurrentAnimationIfExists(newHolder);

        if (preLayoutInfo instanceof MainItemHolderInfo) {
            MainItemHolderInfo preInfo = (MainItemHolderInfo) preLayoutInfo;
            MainRecyclerAdapter.FeedViewHolder holder = (MainRecyclerAdapter.FeedViewHolder) newHolder;

            animateHeartButton(holder);
            updateLikesCounter(holder, holder.getEntity().likesCount);
            if (MainRecyclerAdapter.ACTION_LICK_IMAGE_CLICKED.equals(preInfo.updateAction)) {
                animatePhotoLike(holder);
            }
        }
        return false;
    }

    private void cancelCurrentAnimationIfExists(RecyclerView.ViewHolder newHolder) {
        if (likeAnimationsMap.containsKey(newHolder)) {
            likeAnimationsMap.get(newHolder).cancel();
        }
        if (heartAnimationsMap.containsKey(newHolder)) {
            heartAnimationsMap.get(newHolder).cancel();
        }
    }

    private void animateHeartButton(final MainRecyclerAdapter.FeedViewHolder holder) {
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(holder.mBtnLike, "rotation", 0f, 360f);
        rotationAnim.setDuration(300);
        rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

        ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(holder.mBtnLike, "scaleX", 0.2f, 1.0f);
        bounceAnimX.setDuration(300);
        bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

        ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(holder.mBtnLike, "scaleY", 0.2f, 1.0f);
        bounceAnimY.setDuration(300);
        bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
        bounceAnimY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                holder.mBtnLike.setImageResource(R.mipmap.ic_heart_red);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                heartAnimationsMap.remove(holder);
                dispatchChangeFinishedIfAllAnimationsEnded(holder);
            }
        });

        animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);
        animatorSet.start();

        heartAnimationsMap.put(holder, animatorSet);
    }

    private void updateLikesCounter(MainRecyclerAdapter.FeedViewHolder holder, int likesCount) {
        String likesCountTextFrom = holder.mTsLikesCounter.getResources().getQuantityString(
                R.plurals.likes_count, likesCount - 1, likesCount - 1
        );
        holder.mTsLikesCounter.setCurrentText(likesCountTextFrom);
        String likesCountTextTo = holder.mTsLikesCounter.getResources().getQuantityString(
                R.plurals.likes_count, likesCount, likesCount
        );
        holder.mTsLikesCounter.setText(likesCountTextTo);
    }

    private void animatePhotoLike(final MainRecyclerAdapter.FeedViewHolder holder) {
        holder.mVBgLike.setVisibility(View.VISIBLE);
        holder.mIvLike.setVisibility(View.VISIBLE);

        holder.mVBgLike.setScaleX(0.1f);
        holder.mVBgLike.setScaleY(0.1f);
        holder.mVBgLike.setAlpha(1.0f); // 完全不透明
        holder.mIvLike.setScaleX(0.1f);
        holder.mIvLike.setScaleY(0.1f);

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator bgScaleYAnim = ObjectAnimator.ofFloat(holder.mVBgLike, "scaleY", 0.1f, 1.0f);
        bgScaleYAnim.setDuration(200);
        bgScaleYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
        ObjectAnimator bgScaleXAnim = ObjectAnimator.ofFloat(holder.mVBgLike, "scaleX", 0.1f, 1.0f);
        bgScaleXAnim.setDuration(200);
        bgScaleXAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
        ObjectAnimator bgAlphaAnim = ObjectAnimator.ofFloat(holder.mVBgLike, "alpha", 1.f, 0.f);
        bgAlphaAnim.setDuration(200);
        bgAlphaAnim.setStartDelay(150);
        bgAlphaAnim.setInterpolator(DECCELERATE_INTERPOLATOR);

        ObjectAnimator imageScaleUpYAnim = ObjectAnimator.ofFloat(holder.mIvLike, "scaleY", 0.1f, 1.f);
        imageScaleUpYAnim.setDuration(300);
        imageScaleUpYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
        ObjectAnimator imageScaleUpXAnim = ObjectAnimator.ofFloat(holder.mIvLike, "scaleX", 0.1f, 1.f);
        imageScaleUpXAnim.setDuration(300);
        imageScaleUpXAnim.setInterpolator(DECCELERATE_INTERPOLATOR);

        ObjectAnimator imageScaleDownYAnim = ObjectAnimator.ofFloat(holder.mIvLike, "scaleY", 1.f, 0.f);
        imageScaleDownYAnim.setDuration(300);
        imageScaleDownYAnim.setInterpolator(ACCELERATE_INTERPOLATOR);
        ObjectAnimator imageScaleDownXAnim = ObjectAnimator.ofFloat(holder.mIvLike, "scaleX", 1.f, 0.f);
        imageScaleDownXAnim.setDuration(300);
        imageScaleDownXAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

        animatorSet.playTogether(bgScaleYAnim, bgScaleXAnim, bgAlphaAnim, imageScaleUpYAnim, imageScaleUpXAnim);
        animatorSet.play(imageScaleDownYAnim).with(imageScaleDownXAnim).after(imageScaleUpYAnim);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                likeAnimationsMap.remove(holder);
                resetLikeAnimationState(holder);
                dispatchChangeFinishedIfAllAnimationsEnded(holder);
            }
        });

        animatorSet.start();
        likeAnimationsMap.put(holder, animatorSet);
    }

    private void resetLikeAnimationState(MainRecyclerAdapter.FeedViewHolder holder) {
        holder.mVBgLike.setVisibility(View.INVISIBLE);
        holder.mIvLike.setVisibility(View.INVISIBLE);
    }

    private void dispatchChangeFinishedIfAllAnimationsEnded(MainRecyclerAdapter.FeedViewHolder viewHolder) {
        if (likeAnimationsMap.containsKey(viewHolder) || heartAnimationsMap.containsKey(viewHolder)) {
            return;
        }
        dispatchAnimationFinished(viewHolder);
    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder item) {
        super.endAnimation(item);
        cancelCurrentAnimationIfExists(item);
    }

    @Override
    public void endAnimations() {
        super.endAnimations();
        for (AnimatorSet animatorSet : likeAnimationsMap.values()) {
            animatorSet.cancel();
        }
    }

    /**
     * A simple data structure that holds information about an item's bounds.
     * This information is used in calculating item animations.
     */
    public static class MainItemHolderInfo extends ItemHolderInfo {
        public String updateAction;

        public MainItemHolderInfo(String updateAction) {
            this.updateAction = updateAction;
        }
    }
}
