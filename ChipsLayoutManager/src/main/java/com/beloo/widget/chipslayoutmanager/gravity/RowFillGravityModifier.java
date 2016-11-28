package com.beloo.widget.chipslayoutmanager.gravity;

import android.graphics.Rect;

import com.beloo.widget.chipslayoutmanager.layouter.AbstractLayouter;

class RowFillGravityModifier extends GravityDecorator implements IGravityModifier {

    RowFillGravityModifier(IGravityModifier gravityModifier) {
        super(gravityModifier);
    }

    @Override
    public void modifyChildRect(AbstractLayouter abstractLayouter, Rect childRect) {
        super.modifyChildRect(abstractLayouter, childRect);
        int difference = GravityUtil.getParallelDifference(abstractLayouter);

        if (childRect.left == abstractLayouter.getCanvasLeftBorder() || childRect.left == abstractLayouter.getViewLeft()) {
            //left view of row

            int leftDif = childRect.left - abstractLayouter.getCanvasLeftBorder();
            //press view to left border
            childRect.left -= leftDif;
            childRect.right -= leftDif;

            childRect.right += difference;
            return;
        }

        if (childRect.right == abstractLayouter.getCanvasRightBorder() || childRect.right == abstractLayouter.getViewLeft()) {
            //right view of row

            int rightDif = abstractLayouter.getCanvasRightBorder() - childRect.right;
            //press view to right border
            childRect.left += rightDif;
            childRect.right = abstractLayouter.getCanvasRightBorder();
            childRect.left -= difference;
            return;
        }

        int halfDifference = difference/2;

        childRect.left -= halfDifference;
        childRect.right += halfDifference;
    }
}