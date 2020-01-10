package xyz.kongzz.countdownview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;

/**
 * @author Kong
 * @date 2020-01-10
 * @desc 渐变加载进度条
 */
public class ProgressRing extends View {

    // 进度条起始颜色
    private int mProgressStartColor;
    // 进度条结束颜色
    private int mProgressEndColor;
    // 背景起始颜色
    private int mBgStartColor;
    // 背景中间颜色
    private int mBgMidColor;
    // 背景结束颜色
    private int mBgEndColor;
    // 进度条的进度值
    private float mProgress;
    // 进度条中间文字
    private String mText;
    // 进度条的宽度
    private float mProgressWidth;
    // 起始角度
    private int mStartAngle;
    // 扫视角度
    private int mSweepAngle;
    // 单位角度
    private float mUnitAngle;
    // 绘制的高度
    private int mMeasureHeight;
    // 绘制的宽度
    private int mMeasureWidth;
    // 背景Paint
    private Paint mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    // 进度Paint
    private Paint mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

    private RectF mRectF;
    private Paint mPaint;
    private Paint mMainCirclePaint;
    private Paint mWhiteCirclePaint;

    // 文字字体
    private Typeface mFace;
    // 当前进度
    private float mCurProgress = 0;

    private Context mContext;

    public ProgressRing(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ProgressRing);

        mProgressStartColor = ta.getColor(R.styleable.ProgressRing_pr_progress_start_color, Color.YELLOW);
        mProgressEndColor = ta.getColor(R.styleable.ProgressRing_pr_progress_end_color, mProgressStartColor);
        mBgStartColor = ta.getColor(R.styleable.ProgressRing_pr_bg_start_color, Color.LTGRAY);
        mBgMidColor = ta.getColor(R.styleable.ProgressRing_pr_bg_mid_color, mBgStartColor);
        mBgEndColor = ta.getColor(R.styleable.ProgressRing_pr_bg_end_color, mBgStartColor);
        mProgress = ta.getInt(R.styleable.ProgressRing_pr_progress, 80);
        mProgressWidth = ta.getDimension(R.styleable.ProgressRing_pr_progress_width, 8f);
        mStartAngle = ta.getInt(R.styleable.ProgressRing_pr_start_angle, -90);
        mSweepAngle = ta.getInt(R.styleable.ProgressRing_pr_sweep_angle, 360);
        mText = ta.getString(R.styleable.ProgressRing_pr_show_text);

        ta.recycle();

        mUnitAngle = (float) (mSweepAngle / 100.0);

        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setStrokeCap(Paint.Cap.ROUND);
        mBgPaint.setStrokeWidth(mProgressWidth);

        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        mProgressPaint.setStrokeWidth(mProgressWidth);

        mFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/text_backwards.ttf");

        mPaint = new Paint();
        mPaint.setTextSize(dp2px(30));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(Color.BLACK);
        mPaint.setTypeface(mFace);
        mPaint.setAntiAlias(true);

        mMainCirclePaint = new Paint();
        mMainCirclePaint.setColor(getResources().getColor(R.color.bg_ring));
        mMainCirclePaint.setStyle(Paint.Style.FILL);
        mMainCirclePaint.setStrokeWidth(8);

        mWhiteCirclePaint = new Paint();
        mWhiteCirclePaint.setColor(getResources().getColor(R.color.color_white));
        mWhiteCirclePaint.setStyle(Paint.Style.FILL);
        mWhiteCirclePaint.setStrokeWidth(8);

        mText = "";
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasureWidth = getMeasuredWidth();
        mMeasureHeight = getMeasuredHeight();
        if (mRectF == null) {
            float halfProgressWidth = mProgressWidth / 2;
            mRectF = new RectF(halfProgressWidth + getPaddingLeft() + dp2px(26),
                    halfProgressWidth + getPaddingTop() + dp2px(26),
                    mMeasureWidth - halfProgressWidth - getPaddingRight() - dp2px(26),
                    mMeasureHeight - halfProgressWidth - getPaddingBottom() - dp2px(26));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCurProgress = mProgress;
        // 绘制背景
        canvas.drawCircle(getWidth() >> 1, getHeight() >> 1,
                dp2px(89), mMainCirclePaint);
        canvas.drawCircle(getWidth() >> 1, getHeight() >> 1,
                dp2px(54), mWhiteCirclePaint);
        // 绘制进度
        drawProgress(canvas);
        // 绘制文字
        drawText(canvas);
    }

    /**
     * 绘制文字
     */
    private void drawText(Canvas canvas) {
        Rect bounds = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), bounds);
        int height = bounds.height();
        canvas.drawText(mText, canvas.getWidth() >> 1, (canvas.getHeight() >> 1) + (height >> 1), mPaint);
    }

    /**
     * 绘制进度条
     */
    private void drawProgress(Canvas canvas) {
        for (int i = 0, end = (int) (mCurProgress * mUnitAngle); i <= end; i++) {
            mProgressPaint.setColor(getGradient(i / (mCurProgress * mUnitAngle),
                    mProgressStartColor, mProgressEndColor));
            canvas.drawArc(mRectF,
                    mSweepAngle + mStartAngle - i,
                    1,
                    false,
                    mProgressPaint);
        }
    }

    /**
     * 设置进度
     */
    public void setProgress(@FloatRange(from = 0, to = 100) float progress) {
        this.mProgress = progress;
        invalidate();
    }

    /**
     * 获取当前进度
     */
    public float getProgress() {
        return mProgress;
    }

    /**
     * 设置文字
     */
    public void setText(String text) {
        mText = text;
    }

    /**
     * 获取当前渐变颜色
     */
    public int getGradient(float fraction, int startColor, int endColor) {
        if (fraction > 1) {
            fraction = 1;
        }
        int alphaStart = Color.alpha(startColor);
        int redStart = Color.red(startColor);
        int blueStart = Color.blue(startColor);
        int greenStart = Color.green(startColor);
        int alphaEnd = Color.alpha(endColor);
        int redEnd = Color.red(endColor);
        int blueEnd = Color.blue(endColor);
        int greenEnd = Color.green(endColor);
        int alphaDifference = alphaEnd - alphaStart;
        int redDifference = redEnd - redStart;
        int blueDifference = blueEnd - blueStart;
        int greenDifference = greenEnd - greenStart;
        int alphaCurrent = (int) (alphaStart + fraction * alphaDifference);
        int redCurrent = (int) (redStart + fraction * redDifference);
        int blueCurrent = (int) (blueStart + fraction * blueDifference);
        int greenCurrent = (int) (greenStart + fraction * greenDifference);
        return Color.argb(alphaCurrent, redCurrent, greenCurrent, blueCurrent);
    }

    public int dp2px(final float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
