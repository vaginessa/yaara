package com.mlieou.yaara.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mlieou on 2/9/18.
 */

public class PlaceholderRecyclerView extends RecyclerView {

    private View mEmptyViewPlaceHolder;

    final private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            checkIfEmpty();
        }
    };

    public PlaceholderRecyclerView(Context context) {
        super(context);
    }

    public PlaceholderRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PlaceholderRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void checkIfEmpty() {
        if (mEmptyViewPlaceHolder != null && getAdapter() != null) {
            final boolean isVisible = getAdapter().getItemCount() == 0;
            mEmptyViewPlaceHolder.setVisibility(isVisible ? VISIBLE : GONE);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null)
            oldAdapter.unregisterAdapterDataObserver(observer);
        super.setAdapter(adapter);
        if (adapter != null)
            adapter.registerAdapterDataObserver(observer);
        checkIfEmpty();
    }

    public void setEmptyView(View emptyView) {
        mEmptyViewPlaceHolder = emptyView;
        checkIfEmpty();
    }
}
