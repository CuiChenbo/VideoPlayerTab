//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package choi.ccb.com.fragmenttabhostdemo.video.videoplayer;

import android.graphics.Matrix;

class ScaleManager {
    private Size mViewSize;
    private Size mVideoSize;

    ScaleManager(Size viewSize, Size videoSize) {
        this.mViewSize = viewSize;
        this.mVideoSize = videoSize;
    }

    Matrix getScaleMatrix(ScaleType scaleType) {
        switch(scaleType) {
        case NONE:
            return this.getNoScale();
        case FIT_XY:
            return this.fitXY();
        case FIT_CENTER:
            return this.fitCenter();
        case FIT_START:
            return this.fitStart();
        case FIT_END:
            return this.fitEnd();
        case LEFT_TOP:
            return this.getOriginalScale(PivotPoint.LEFT_TOP);
        case LEFT_CENTER:
            return this.getOriginalScale(PivotPoint.LEFT_CENTER);
        case LEFT_BOTTOM:
            return this.getOriginalScale(PivotPoint.LEFT_BOTTOM);
        case CENTER_TOP:
            return this.getOriginalScale(PivotPoint.CENTER_TOP);
        case CENTER:
            return this.getOriginalScale(PivotPoint.CENTER);
        case CENTER_BOTTOM:
            return this.getOriginalScale(PivotPoint.CENTER_BOTTOM);
        case RIGHT_TOP:
            return this.getOriginalScale(PivotPoint.RIGHT_TOP);
        case RIGHT_CENTER:
            return this.getOriginalScale(PivotPoint.RIGHT_CENTER);
        case RIGHT_BOTTOM:
            return this.getOriginalScale(PivotPoint.RIGHT_BOTTOM);
        case LEFT_TOP_CROP:
            return this.getCropScale(PivotPoint.LEFT_TOP);
        case LEFT_CENTER_CROP:
            return this.getCropScale(PivotPoint.LEFT_CENTER);
        case LEFT_BOTTOM_CROP:
            return this.getCropScale(PivotPoint.LEFT_BOTTOM);
        case CENTER_TOP_CROP:
            return this.getCropScale(PivotPoint.CENTER_TOP);
        case CENTER_CROP:
            return this.getCropScale(PivotPoint.CENTER);
        case CENTER_BOTTOM_CROP:
            return this.getCropScale(PivotPoint.CENTER_BOTTOM);
        case RIGHT_TOP_CROP:
            return this.getCropScale(PivotPoint.RIGHT_TOP);
        case RIGHT_CENTER_CROP:
            return this.getCropScale(PivotPoint.RIGHT_CENTER);
        case RIGHT_BOTTOM_CROP:
            return this.getCropScale(PivotPoint.RIGHT_BOTTOM);
        case START_INSIDE:
            return this.startInside();
        case CENTER_INSIDE:
            return this.centerInside();
        case END_INSIDE:
            return this.endInside();
        default:
            return null;
        }
    }

    private Matrix getMatrix(float sx, float sy, float px, float py) {
        Matrix matrix = new Matrix();
        matrix.setScale(sx, sy, px, py);
        return matrix;
    }

    private Matrix getMatrix(float sx, float sy, PivotPoint pivotPoint) {
        switch(pivotPoint) {
        case LEFT_TOP:
            return this.getMatrix(sx, sy, 0.0F, 0.0F);
        case LEFT_CENTER:
            return this.getMatrix(sx, sy, 0.0F, (float)this.mViewSize.getHeight() / 2.0F);
        case LEFT_BOTTOM:
            return this.getMatrix(sx, sy, 0.0F, (float)this.mViewSize.getHeight());
        case CENTER_TOP:
            return this.getMatrix(sx, sy, (float)this.mViewSize.getWidth() / 2.0F, 0.0F);
        case CENTER:
            return this.getMatrix(sx, sy, (float)this.mViewSize.getWidth() / 2.0F, (float)this.mViewSize.getHeight() / 2.0F);
        case CENTER_BOTTOM:
            return this.getMatrix(sx, sy, (float)this.mViewSize.getWidth() / 2.0F, (float)this.mViewSize.getHeight());
        case RIGHT_TOP:
            return this.getMatrix(sx, sy, (float)this.mViewSize.getWidth(), 0.0F);
        case RIGHT_CENTER:
            return this.getMatrix(sx, sy, (float)this.mViewSize.getWidth(), (float)this.mViewSize.getHeight() / 2.0F);
        case RIGHT_BOTTOM:
            return this.getMatrix(sx, sy, (float)this.mViewSize.getWidth(), (float)this.mViewSize.getHeight());
        default:
            throw new IllegalArgumentException("Illegal PivotPoint");
        }
    }

    private Matrix getNoScale() {
        float sx = (float)this.mVideoSize.getWidth() / (float)this.mViewSize.getWidth();
        float sy = (float)this.mVideoSize.getHeight() / (float)this.mViewSize.getHeight();
        return this.getMatrix(sx, sy, PivotPoint.LEFT_TOP);
    }

    private Matrix getFitScale(PivotPoint pivotPoint) {
        float sx = (float)this.mViewSize.getWidth() / (float)this.mVideoSize.getWidth();
        float sy = (float)this.mViewSize.getHeight() / (float)this.mVideoSize.getHeight();
        float minScale = Math.min(sx, sy);
        sx = minScale / sx;
        sy = minScale / sy;
        return this.getMatrix(sx, sy, pivotPoint);
    }

    private Matrix fitXY() {
        return this.getMatrix(1.0F, 1.0F, PivotPoint.LEFT_TOP);
    }

    private Matrix fitStart() {
        return this.getFitScale(PivotPoint.LEFT_TOP);
    }

    private Matrix fitCenter() {
        return this.getFitScale(PivotPoint.CENTER);
    }

    private Matrix fitEnd() {
        return this.getFitScale(PivotPoint.RIGHT_BOTTOM);
    }

    private Matrix getOriginalScale(PivotPoint pivotPoint) {
        float sx = (float)this.mVideoSize.getWidth() / (float)this.mViewSize.getWidth();
        float sy = (float)this.mVideoSize.getHeight() / (float)this.mViewSize.getHeight();
        return this.getMatrix(sx, sy, pivotPoint);
    }

    private Matrix getCropScale(PivotPoint pivotPoint) {
        float sx = (float)this.mViewSize.getWidth() / (float)this.mVideoSize.getWidth();
        float sy = (float)this.mViewSize.getHeight() / (float)this.mVideoSize.getHeight();
        float maxScale = Math.max(sx, sy);
        sx = maxScale / sx;
        sy = maxScale / sy;
        return this.getMatrix(sx, sy, pivotPoint);
    }

    private Matrix startInside() {
        return this.mVideoSize.getHeight() <= this.mViewSize.getWidth() && this.mVideoSize.getHeight() <= this.mViewSize.getHeight() ? this.getOriginalScale(PivotPoint.LEFT_TOP) : this.fitStart();
    }

    private Matrix centerInside() {
        return this.mVideoSize.getHeight() <= this.mViewSize.getWidth() && this.mVideoSize.getHeight() <= this.mViewSize.getHeight() ? this.getOriginalScale(PivotPoint.CENTER) : this.fitCenter();
    }

    private Matrix endInside() {
        return this.mVideoSize.getHeight() <= this.mViewSize.getWidth() && this.mVideoSize.getHeight() <= this.mViewSize.getHeight() ? this.getOriginalScale(PivotPoint.RIGHT_BOTTOM) : this.fitEnd();
    }
}
