package com.lanou.radiostation.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanou.radiostation.R;

import java.util.List;

public class ViewPagerIndicatorthree extends LinearLayout {

	private Paint mPaint;

	private Path mPath;

	private int mTriangleWidth;
	private int mTriangleHeight;

	private static final float RADIO_TRIANGLE_WIDTH = 1/6F;

	private int mInitTranslationX;

	private int mTranslationX;

	private int mTabVisibleCount;

	private List<String> mTitles;

	 private static final int COUNT_DEFAULT_TAB = 3;


	public ViewPagerIndicatorthree(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);

		mTabVisibleCount = a.getInt(R.styleable.ViewPagerIndicator_visible_tab_count,
				COUNT_DEFAULT_TAB);
		if (mTabVisibleCount < 0) {
			mTabVisibleCount = COUNT_DEFAULT_TAB;
		}

		a.recycle();

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.parseColor("#FF0000"));
		mPaint.setStyle(Style.FILL);
		mPaint.setPathEffect(new CornerPathEffect(3));
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {

		canvas.save();

		canvas.translate(mInitTranslationX+mTranslationX, getHeight() + 2);
		canvas.drawPath(mPath, mPaint);

		canvas.restore();

		super.dispatchDraw(canvas);
	}

	public ViewPagerIndicatorthree(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		mTriangleWidth = (int) (w / mTabVisibleCount);
		mInitTranslationX = w / mTabVisibleCount /2 - mTriangleWidth / 2;
		
		initTriangle();
	}
	
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		
		int cCount = getChildCount();
		if (cCount== 0) {
			return;
		}
		for (int i = 0; i < cCount; i++) {
			View view = getChildAt(i);
			LayoutParams lp = (LayoutParams) view.getLayoutParams();
			lp.weight = 0;
			lp.width = getScreenWidth()/mTabVisibleCount;
			view.setLayoutParams(lp);
		
		}
	}
	
	private int getScreenWidth() {
		WindowManager wm = (WindowManager) getContext().
				getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	private void initTriangle() {
		
		mTriangleHeight = 10;
		mPath = new Path();
		mPath.moveTo(0, 0);
		mPath.lineTo(mTriangleWidth, 0);
		mPath.lineTo(mTriangleWidth , -5);
		mPath.lineTo(0 , -5);
		mPath.close();
		
	}

	public void scroll(int arg0, float arg1) {
		int tabWidth = getWidth() / mTabVisibleCount;
		mTranslationX = (int) (tabWidth * (arg0+arg1));
		
		if (arg0 >= mTabVisibleCount - 2 && arg1 > 0 &&
				getChildCount() > mTabVisibleCount) {
			
			
			if (mTabVisibleCount != 1) {
				this.scrollTo((int) ((arg0 - (mTabVisibleCount - 2)) * tabWidth + tabWidth * arg1),
						0);
			}else{
				this.scrollTo(arg0 * tabWidth + (int)(tabWidth * arg1), 0);
			}
			
			
		}
		
		
		invalidate();
	}
	
	public void setTabItemTitles(List<String> titles){
		if (titles != null && titles.size() > 0) {
			this.removeAllViews();
			mTitles = titles;
			for(String title:mTitles){
				addView(generateTextView(title));
			}
		}
	}
	private static final int COLOR_TEXT_NORMAL = 0X77FFFFFF;


	public void setVisibleCount(int count){
		mTabVisibleCount = count;
	}

	private View generateTextView(String title) {
		TextView tv = new TextView(getContext());
		LayoutParams lp = new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		lp.width = getScreenWidth()/mTabVisibleCount;
		tv.setText(title);
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
		tv.setTextColor(COLOR_TEXT_NORMAL);
		tv.setLayoutParams(lp);
		return tv;
	}

}
