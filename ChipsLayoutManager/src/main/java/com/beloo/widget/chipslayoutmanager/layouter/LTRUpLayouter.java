package com.beloo.widget.chipslayoutmanager.layouter;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.View;

class LTRUpLayouter extends AbstractLayouter implements ILayouter {

    private LTRUpLayouter(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    Rect createViewRect(View view) {
        int left = viewRight - getCurrentViewWidth();
        int viewTop = viewBottom - getCurrentViewHeight();

        Rect viewRect = new Rect(left, viewTop, viewRight, viewBottom);
        viewRight = viewRect.left;
        return viewRect;
    }

    @Override
    void onPreLayout() {
        int leftOffsetOfRow = viewRight - getCanvasLeftBorder();
        for (Pair<Rect, View> rowViewRectPair : rowViews) {
            Rect viewRect = rowViewRectPair.first;

            viewRect.left = viewRect.left - leftOffsetOfRow;
            viewRect.right = viewRect.right - leftOffsetOfRow;

            viewTop = Math.min(viewTop, viewRect.top);
            viewBottom = Math.max(viewBottom, viewRect.bottom);
        }
    }

    @Override
    void onAfterLayout() {
        //go to next row, increase top coordinate, reset left
        viewRight = getCanvasRightBorder();
        viewBottom = viewTop;
    }

    @Override
    boolean isAttachedViewFromNewRow(View view) {
        int bottomOfCurrentView = getLayoutManager().getDecoratedBottom(view);
        int rightOfCurrentView = getLayoutManager().getDecoratedRight(view);

        return viewTop >= bottomOfCurrentView
                && rightOfCurrentView > viewRight;
    }

    @Override
    public void onInterceptAttachView(View view) {
        if (viewRight != getCanvasRightBorder() && viewRight - getCurrentViewWidth() < getCanvasLeftBorder()) {
            //new row
            viewRight = getCanvasRightBorder();
            viewBottom = viewTop;
        } else {
            viewRight = getLayoutManager().getDecoratedLeft(view);
        }

        viewTop = Math.min(viewTop, getLayoutManager().getDecoratedTop(view));
    }

    @Override
    int getStart() {
        return getViewTop();
    }

    @Override
    int getEnd() {
        return getViewBottom();
    }

    @Override
    AbstractPositionIterator createPositionIterator() {
        return new DecrementalPositionIterator();
    }


    public static final class Builder extends AbstractLayouter.Builder {
        private Builder() {
        }

        @NonNull
        public LTRUpLayouter build() {
            return new LTRUpLayouter(this);
        }
    }
}
