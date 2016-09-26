package com.lanou.radiostation.view;/*
 * Copyright (C) 2012 www.amsoft.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 名称：PullToRefreshView.java 描述：下拉刷新和加载更多的View.
 */
public class PullToRefreshView extends LinearLayout {
	/** 时分秒. */
	public static final String dateFormatHMS = "HH:mm:ss";
	/** 上下文. */
	private Context mContext = null;

	/** 下拉刷新的开关. */
	private boolean mEnablePullRefresh = true;

	/** 加载更多的开关. */
	private boolean mEnableLoadMore = true;

	/** x上一次保存的. */
	private int mLastMotionX;

	/** y上一次保存的. */
	private int mLastMotionY;

	/** header view. */
	private ListViewHeader mHeaderView;

	/** footer view. */
	private ListViewFooter mFooterView;

	/** list or grid. */
	private AdapterView<?> mAdapterView;

	/** Scrollview. */
	private ScrollView mScrollView;

	/** header view 高度. */
	private int mHeaderViewHeight;

	/** footer view 高度. */
	private int mFooterViewHeight;

	/** 滑动状态. */
	private int mPullState;

	/** 上滑动作. */
	private static final int PULL_UP_STATE = 0;

	/** 下拉动作. */
	private static final int PULL_DOWN_STATE = 1;

	/** 上一次的数量. */
	private int mCount = 0;

	/** 正在下拉刷新. */
	private boolean mPullRefreshing = false;

	/** 正在加载更多. */
	private boolean mPullLoading = false;

	/** Footer加载更多监听器. */
	private OnFooterLoadListener mOnFooterLoadListener;

	/** Header下拉刷新监听器. */
	private OnHeaderRefreshListener mOnHeaderRefreshListener;

	/** UI设计的基准宽度. */
	public static int UI_WIDTH = 720;

	/** UI设计的基准高度. */
	public static int UI_HEIGHT = 1280;

	/** UI设计的密度. */
	public static int UI_DENSITY = 2;

	/**
	 * 描述：根据屏幕大小缩放.
	 *
	 * @param context
	 *            the context
	 * @param pxValue
	 *            the px value
	 * @return the int
	 */
	public static int scaleValue(Context context, float value) {
		DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
		// 为了兼容尺寸小密度大的情况
		if (mDisplayMetrics.scaledDensity > UI_DENSITY) {
			// 密度
			if (mDisplayMetrics.widthPixels > UI_WIDTH) {
				value = value * (1.3f - 1.0f / mDisplayMetrics.scaledDensity);
			} else if (mDisplayMetrics.widthPixels < UI_WIDTH) {
				value = value * (1.0f - 1.0f / mDisplayMetrics.scaledDensity);
			}
		}
		return scale(mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels,
				value);
	}

	public void setVisible() {
		mFooterView.setVisibility(View.VISIBLE);
	}

	/**
	 * 获取屏幕尺寸与密度.
	 *
	 * @param context
	 *            the context
	 * @return mDisplayMetrics
	 */
	public static DisplayMetrics getDisplayMetrics(Context context) {
		Resources mResources;
		if (context == null) {
			mResources = Resources.getSystem();

		} else {
			mResources = context.getResources();
		}
		// DisplayMetrics{density=1.5, width=480, height=854, scaledDensity=1.5,
		// xdpi=160.421, ydpi=159.497}
		// DisplayMetrics{density=2.0, width=720, height=1280,
		// scaledDensity=2.0, xdpi=160.42105, ydpi=160.15764}
		DisplayMetrics mDisplayMetrics = mResources.getDisplayMetrics();
		return mDisplayMetrics;
	}

	/**
	 * 描述：根据屏幕大小缩放.
	 *
	 * @param displayWidth
	 *            the display width
	 * @param displayHeight
	 *            the display height
	 * @param pxValue
	 *            the px value
	 * @return the int
	 */
	public static int scale(int displayWidth, int displayHeight, float pxValue) {
		if (pxValue == 0) {
			return 0;
		}
		float scale = 1;
		try {
			float scaleWidth = (float) displayWidth / UI_WIDTH;
			float scaleHeight = (float) displayHeight / UI_HEIGHT;
			scale = Math.min(scaleWidth, scaleHeight);
		} catch (Exception e) {
		}
		return Math.round(pxValue * scale + 0.5f);
	}

	/**
	 * 设置PX padding.
	 *
	 * @param view
	 *            the view
	 * @param left
	 *            the left padding in pixels
	 * @param top
	 *            the top padding in pixels
	 * @param right
	 *            the right padding in pixels
	 * @param bottom
	 *            the bottom padding in pixels
	 */
	public static void setPadding(View view, int left, int top, int right,
								  int bottom) {
		int scaledLeft = scaleValue(view.getContext(), left);
		int scaledTop = scaleValue(view.getContext(), top);
		int scaledRight = scaleValue(view.getContext(), right);
		int scaledBottom = scaleValue(view.getContext(), bottom);
		view.setPadding(scaledLeft, scaledTop, scaledRight, scaledBottom);
	}

	/**
	 * 缩放文字大小,这样设置的好处是文字的大小不和密度有关， 能够使文字大小在不同的屏幕上显示比例正确
	 *
	 * @param textView
	 *            button
	 * @param sizePixels
	 *            px值
	 * @return
	 */
	public static void setTextSize(TextView textView, float sizePixels) {
		float scaledSize = scaleTextValue(textView.getContext(), sizePixels);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaledSize);
	}

	/**
	 * 描述：根据屏幕大小缩放文本.
	 *
	 * @param context
	 *            the context
	 * @param pxValue
	 *            the px value
	 * @return the int
	 */
	public static int scaleTextValue(Context context, float value) {
		DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
		// 为了兼容尺寸小密度大的情况
		if (mDisplayMetrics.scaledDensity > 2) {
			// 缩小到密度分之一
			// value = value*(1.1f - 1.0f/mDisplayMetrics.scaledDensity);
		}
		return scale(mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels,
				value);
	}

	/**
	 * 测量这个view 最后通过getMeasuredWidth()获取宽度和高度.
	 *
	 * @param view
	 *            要测量的view
	 * @return 测量过的view
	 */
	public static void measureView(View view) {
		ViewGroup.LayoutParams p = view.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		view.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * 描述：获取表示当前日期时间的字符串.
	 *
	 * @param format
	 *            格式化字符串，如："yyyy-MM-dd HH:mm:ss"
	 * @return String String类型的当前日期时间
	 */
	public static String getCurrentDate(String format) {
		Log.d("DateUtil.class", "getCurrentDate:" + format);
		String curDateTime = null;
		try {
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
			Calendar c = new GregorianCalendar();
			curDateTime = mSimpleDateFormat.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return curDateTime;

	}

	/**
	 * 描述：获取src中的图片资源.
	 *
	 * @param src
	 *            图片的src路径，如（“image/arrow.png”）
	 * @return Bitmap 图片
	 */
	public static Bitmap getBitmapFromSrc(String src) {
		Bitmap bit = null;
		try {
			bit = BitmapFactory.decodeStream(PullToRefreshView.class
					.getResourceAsStream(src));
		} catch (Exception e) {
			Log.d("FileUtil.class", "获取图片异常：" + e.getMessage());
		}
		return bit;
	}

	/**
	 * 构造.
	 *
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public PullToRefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * 构造.
	 *
	 * @param context
	 *            the context
	 */
	public PullToRefreshView(Context context) {
		super(context);
		init(context);
	}

	/**
	 * 初始化View.
	 *
	 * @param context
	 *            the context
	 */
	private void init(Context context) {
		mContext = context;
		this.setOrientation(LinearLayout.VERTICAL);
		// 增加HeaderView
		addHeaderView();
	}

	/**
	 * add HeaderView.
	 */
	private void addHeaderView() {
		mHeaderView = new ListViewHeader(mContext);
		mHeaderViewHeight = mHeaderView.getHeaderHeight();
		mHeaderView.setGravity(Gravity.BOTTOM);

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				mHeaderViewHeight);
		// 设置topMargin的值为负的header View高度,即将其隐藏在最上方
		params.topMargin = -(mHeaderViewHeight);
		addView(mHeaderView, params);

	}

	/**
	 * add FooterView.
	 */
	private void addFooterView() {

		mFooterView = new ListViewFooter(mContext);
		mFooterViewHeight = mFooterView.getFooterHeight();

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				mFooterViewHeight);
		addView(mFooterView, params);
	}

	/**
	 * remove FooterView.
	 */
	public void removeFooterView() {
		// removeView(mFooterView);
		mFooterView.setVisibility(View.GONE);
	}

	/**
	 * 在此添加footer view保证添加到linearlayout中的最后.
	 */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		addFooterView();
		initContentAdapterView();
	}

	/**
	 * init AdapterView like ListView, GridView and so on; or init ScrollView.
	 */
	private void initContentAdapterView() {
		int count = getChildCount();
		if (count < 3) {
			throw new IllegalArgumentException(
					"this layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
		}
		View view = null;
		for (int i = 0; i < count - 1; ++i) {
			view = getChildAt(i);
			if (view instanceof AdapterView<?>) {
				mAdapterView = (AdapterView<?>) view;
			}
			if (view instanceof ScrollView) {
				// finish later
				mScrollView = (ScrollView) view;
			}
		}
		if (mAdapterView == null && mScrollView == null) {
			throw new IllegalArgumentException(
					"must contain a AdapterView or ScrollView in this layout!");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * android.view.ViewGroup#onInterceptTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		int x = (int) e.getX();
		int y = (int) e.getY();
		switch (e.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// 首先拦截down事件,记录y坐标
				mLastMotionX = x;
				mLastMotionY = y;
				break;
			case MotionEvent.ACTION_MOVE:
				// deltaY > 0 是向下运动,< 0是向上运动
				int deltaX = x - mLastMotionX;
				int deltaY = y - mLastMotionY;
				// 解决点击与移动的冲突
				if (Math.abs(deltaX) < Math.abs(deltaY) && Math.abs(deltaY) > 10) {
					if (isRefreshViewScroll(deltaY)) {
						return true;
					}
				}

				break;
		}
		return false;
	}

	/*
	 * 如果在onInterceptTouchEvent()方法中没有拦截(即onInterceptTouchEvent()方法中 return
	 * false)则由PullToRefreshView 的子View来处理;否则由下面的方法来处理(即由PullToRefreshView自己来处理)
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int y = (int) event.getY();

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				int deltaY = y - mLastMotionY;
				if (mPullState == PULL_DOWN_STATE) {
					// 执行下拉
					headerPrepareToRefresh(deltaY);
				} else if (mPullState == PULL_UP_STATE) {
					// 执行上拉
					footerPrepareToRefresh(deltaY);
				}
				mLastMotionY = y;
				break;
			// UP和CANCEL执行相同的方法
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				int topMargin = getHeaderTopMargin();
				if (mPullState == PULL_DOWN_STATE) {
					if (topMargin >= 0) {
						// 开始刷新
						headerRefreshing();
					} else {
						// 还没有执行刷新，重新隐藏
						setHeaderTopMargin(-mHeaderViewHeight);
					}
				} else if (mPullState == PULL_UP_STATE) {
					// 控制在什么时候加载更多
					if (Math.abs(topMargin) >= mHeaderViewHeight
							+ mFooterViewHeight) {
						// 开始执行footer 刷新
						footerLoading();
					} else {
						// 还没有执行刷新，重新隐藏
						setHeaderTopMargin(-mHeaderViewHeight);
					}
				}
				break;

		}
		return super.onTouchEvent(event);
	}

	/**
	 * 判断滑动方向，和是否响应事件.
	 *
	 * @param deltaY
	 *            deltaY > 0 是向下运动,< 0是向上运动
	 * @return true, if is refresh view scroll
	 */
	private boolean isRefreshViewScroll(int deltaY) {

		if (mPullRefreshing || mPullLoading) {
			return false;
		}
		// 对于ListView和GridView
		if (mAdapterView != null) {
			// 子view(ListView or GridView)滑动到最顶端
			if (deltaY > 0) {
				// 判断是否禁用下拉刷新操作
				if (!mEnablePullRefresh) {
					return false;
				}
				View child = mAdapterView.getChildAt(0);
				if (child == null) {
					// 如果mAdapterView中没有数据,不拦截
					// return false;

					mPullState = PULL_DOWN_STATE;
					return true;
				}
				if (mAdapterView.getFirstVisiblePosition() == 0
						&& child.getTop() == 0) {
					mPullState = PULL_DOWN_STATE;
					return true;
				}
				int top = child.getTop();
				int padding = mAdapterView.getPaddingTop();
				if (mAdapterView.getFirstVisiblePosition() == 0
						&& Math.abs(top - padding) <= 11) {
					mPullState = PULL_DOWN_STATE;
					return true;
				}

			} else if (deltaY < 0) {
				// 判断是否禁用上拉加载更多操作
				if (!mEnableLoadMore) {
					return false;
				}
				View lastChild = mAdapterView.getChildAt(mAdapterView
						.getChildCount() - 1);
				if (lastChild == null) {
					// 如果mAdapterView中没有数据,不拦截
					// return false;
					mPullState = PULL_UP_STATE;
					return true;
				}
				// 最后一个子view的Bottom小于父View的高度说明mAdapterView的数据没有填满父view,
				// 等于父View的高度说明mAdapterView已经滑动到最后
				if (lastChild.getBottom() <= getHeight()
						&& mAdapterView.getLastVisiblePosition() == mAdapterView
						.getCount() - 1) {
					mPullState = PULL_UP_STATE;
					return true;
				}
			}
		}
		// 对于ScrollView
		if (mScrollView != null) {

			// 子scroll view滑动到最顶端
			View child = mScrollView.getChildAt(0);
			if (deltaY > 0 && mScrollView.getScrollY() == 0) {
				// 判断是否禁用下拉刷新操作
				if (!mEnablePullRefresh) {
					return false;
				}
				mPullState = PULL_DOWN_STATE;
				return true;
			} else if (deltaY < 0
					&& child.getMeasuredHeight() <= getHeight()
					+ mScrollView.getScrollY()) {
				// 判断是否禁用上拉加载更多操作
				if (!mEnableLoadMore) {
					return false;
				}
				mPullState = PULL_UP_STATE;
				return true;
			}
		}
		return false;
	}

	/**
	 * header 准备刷新,手指移动过程,还没有释放.
	 *
	 * @param deltaY
	 *            手指滑动的距离
	 */
	private void headerPrepareToRefresh(int deltaY) {
		if (mPullRefreshing || mPullLoading) {
			return;
		}

		int newTopMargin = updateHeaderViewTopMargin(deltaY);
		// 当header view的topMargin>=0时，说明header view完全显示出来了 ,修改header view 的提示状态
		if (newTopMargin >= 0
				&& mHeaderView.getState() != ListViewHeader.STATE_REFRESHING) {
			// 提示松开刷新
			mHeaderView.setState(ListViewHeader.STATE_READY);

		} else if (newTopMargin < 0 && newTopMargin > -mHeaderViewHeight) {
			// 提示下拉刷新
			mHeaderView.setState(ListViewHeader.STATE_NORMAL);
		}
	}

	/**
	 * footer 准备刷新,手指移动过程,还没有释放 移动footer view高度同样和移动header view
	 * 高度是一样，都是通过修改header view的topmargin的值来达到.
	 *
	 * @param deltaY
	 *            手指滑动的距离
	 */
	private void footerPrepareToRefresh(int deltaY) {
		if (mPullRefreshing || mPullLoading) {
			return;
		}
		int newTopMargin = updateHeaderViewTopMargin(deltaY);
		// 如果header view topMargin 的绝对值大于或等于header + footer 的高度
		// 说明footer view 完全显示出来了，修改footer view 的提示状态
		if (Math.abs(newTopMargin) >= (mHeaderViewHeight + mFooterViewHeight)
				&& mFooterView.getState() != ListViewFooter.STATE_LOADING) {
			mFooterView.setState(ListViewFooter.STATE_READY);
		} else if (Math.abs(newTopMargin) < (mHeaderViewHeight + mFooterViewHeight)) {
			mFooterView.setState(ListViewFooter.STATE_LOADING);
		}
	}

	/**
	 * 修改Header view top margin的值.
	 *
	 * @param deltaY
	 *            the delta y
	 * @return the int
	 */
	private int updateHeaderViewTopMargin(int deltaY) {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		float newTopMargin = params.topMargin + deltaY * 0.3f;
		// 这里对上拉做一下限制,因为当前上拉后然后不释放手指直接下拉,会把下拉刷新给触发了
		// 表示如果是在上拉后一段距离,然后直接下拉
		if (deltaY > 0 && mPullState == PULL_UP_STATE
				&& Math.abs(params.topMargin) <= mHeaderViewHeight) {
			return params.topMargin;
		}
		// 同样地,对下拉做一下限制,避免出现跟上拉操作时一样的bug
		if (deltaY < 0 && mPullState == PULL_DOWN_STATE
				&& Math.abs(params.topMargin) >= mHeaderViewHeight) {
			return params.topMargin;
		}
		params.topMargin = (int) newTopMargin;
		mHeaderView.setLayoutParams(params);
		return params.topMargin;
	}

	/**
	 * 下拉刷新.
	 */
	public void headerRefreshing() {
		mPullRefreshing = true;
		mHeaderView.setState(ListViewHeader.STATE_REFRESHING);
		setHeaderTopMargin(0);
		if (mOnHeaderRefreshListener != null) {
			mOnHeaderRefreshListener.onHeaderRefresh(this);
		}
	}

	/**
	 * 加载更多.
	 */
	private void footerLoading() {
		mPullLoading = true;
		int top = mHeaderViewHeight + mFooterViewHeight;
		setHeaderTopMargin(-top);
		if (mOnFooterLoadListener != null) {
			mOnFooterLoadListener.onFooterLoad(this);
		}
	}

	/**
	 * 设置header view 的topMargin的值.
	 *
	 * @param topMargin
	 *            the new header top margin
	 */
	private void setHeaderTopMargin(int topMargin) {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		params.topMargin = topMargin;
		mHeaderView.setLayoutParams(params);
	}

	/**
	 * header view 完成更新后恢复初始状态.
	 */
	public void onHeaderRefreshFinish() {
		setHeaderTopMargin(-mHeaderViewHeight);
		mHeaderView.setState(ListViewHeader.STATE_NORMAL);
		if (mAdapterView != null) {
			mCount = mAdapterView.getCount();
			// 判断有没有数据
			if (mCount > 0) {
				mFooterView.setState(ListViewFooter.STATE_READY);
			} else {
				mFooterView.setState(ListViewFooter.STATE_EMPTY);
			}
		} else {
			mFooterView.setState(ListViewFooter.STATE_READY);
		}

		mPullRefreshing = false;
	}

	/**
	 * footer view 完成更新后恢复初始状态.
	 */
	public void onFooterLoadFinish() {
		setHeaderTopMargin(-mHeaderViewHeight);
		mHeaderView.setState(ListViewHeader.STATE_NORMAL);
		if (mAdapterView != null) {
			int countNew = mAdapterView.getCount();
			// 判断有没有更多数据了
			if (countNew > mCount) {
				mFooterView.setState(ListViewFooter.STATE_READY);
			} else {
				mFooterView.setState(ListViewFooter.STATE_NO);
			}
		} else {
			mFooterView.setState(ListViewFooter.STATE_READY);
		}

		mPullLoading = false;
	}

	/**
	 * 获取当前header view 的topMargin.
	 *
	 * @return the header top margin
	 */
	private int getHeaderTopMargin() {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		return params.topMargin;
	}

	/**
	 * 设置下拉刷新的监听器.
	 *
	 * @param headerRefreshListener
	 *            the new on header refresh listener
	 */
	public void setOnHeaderRefreshListener(
			OnHeaderRefreshListener headerRefreshListener) {
		mOnHeaderRefreshListener = headerRefreshListener;
	}

	/**
	 * 设置加载更多的监听器.
	 *
	 * @param footerLoadListener
	 *            the new on footer load listener
	 */
	public void setOnFooterLoadListener(OnFooterLoadListener footerLoadListener) {
		mOnFooterLoadListener = footerLoadListener;
	}

	/**
	 * 打开或者关闭下拉刷新功能.
	 *
	 * @param enable
	 *            开关标记
	 */
	public void setPullRefreshEnable(boolean enable) {
		mEnablePullRefresh = enable;
	}

	/**
	 * 打开或者关闭加载更多功能.
	 *
	 * @param enable
	 *            开关标记
	 */
	public void setLoadMoreEnable(boolean enable) {
		mEnableLoadMore = enable;
	}

	/**
	 * 下拉刷新是打开的吗.
	 *
	 * @return true, if is enable pull refresh
	 */
	public boolean isEnablePullRefresh() {
		return mEnablePullRefresh;
	}

	/**
	 * 加载更多是打开的吗.
	 *
	 * @return true, if is enable load more
	 */
	public boolean isEnableLoadMore() {
		return mEnableLoadMore;
	}

	/**
	 * 描述：获取Header View.
	 *
	 * @return the header view
	 */
	public ListViewHeader getHeaderView() {
		return mHeaderView;
	}

	/**
	 * 描述：获取Footer View.
	 *
	 * @return the footer view
	 */
	public ListViewFooter getFooterView() {
		return mFooterView;
	}

	/**
	 * 描述：获取Header ProgressBar，用于设置自定义样式.
	 *
	 * @return the header progress bar
	 */
	public ProgressBar getHeaderProgressBar() {
		return mHeaderView.getHeaderProgressBar();
	}

	/**
	 * 描述：获取Footer ProgressBar，用于设置自定义样式.
	 *
	 * @return the footer progress bar
	 */
	public ProgressBar getFooterProgressBar() {
		return mFooterView.getFooterProgressBar();
	}

	/**
	 * Interface definition for a callback to be invoked when list/grid footer
	 * view should be refreshed.
	 *
	 * @see OnFooterLoadEvent
	 */
	public interface OnFooterLoadListener {

		/**
		 * On footer load.
		 *
		 * @param view
		 *            the view
		 */
		public void onFooterLoad(PullToRefreshView view);
	}

	/**
	 * Interface definition for a callback to be invoked when list/grid header
	 * view should be refreshed.
	 *
	 * @see OnHeaderRefreshEvent
	 */
	public interface OnHeaderRefreshListener {

		/**
		 * On header refresh.
		 *
		 * @param view
		 *            the view
		 */
		public void onHeaderRefresh(PullToRefreshView view);
	}

}

class ListViewHeader extends LinearLayout {

	/** 上下文. */
	private Context mContext;

	/** 主View. */
	private LinearLayout headerView;

	/** 箭头图标View. */
	private ImageView arrowImageView;

	/** 进度图标View. */
	private ProgressBar headerProgressBar;

	/** 箭头图标. */
	private Bitmap arrowImage = null;

	/** 文本提示的View. */
	private TextView tipsTextview;

	/** 时间的View. */
	private TextView headerTimeView;

	/** 当前状态. */
	private int mState = -1;

	/** 向上的动画. */
	private Animation mRotateUpAnim;

	/** 向下的动画. */
	private Animation mRotateDownAnim;

	/** 动画时间. */
	private final int ROTATE_ANIM_DURATION = 180;

	/** 显示 下拉刷新. */
	public final static int STATE_NORMAL = 0;

	/** 显示 松开刷新. */
	public final static int STATE_READY = 1;

	/** 显示 正在刷新.... */
	public final static int STATE_REFRESHING = 2;

	/** 保存上一次的刷新时间. */
	private String lastRefreshTime = null;

	/** Header的高度. */
	private int headerHeight;

	/**
	 * 初始化Header.
	 *
	 * @param context
	 *            the context
	 */
	public ListViewHeader(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * 初始化Header.
	 *
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public ListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/**
	 * 初始化View.
	 *
	 * @param context
	 *            the context
	 */
	private void initView(Context context) {

		mContext = context;

		// 顶部刷新栏整体内容
		headerView = new LinearLayout(context);
		headerView.setOrientation(LinearLayout.HORIZONTAL);
		headerView.setGravity(Gravity.CENTER);

		PullToRefreshView.setPadding(headerView, 0, 10, 0, 10);

		// 显示箭头与进度
		FrameLayout headImage = new FrameLayout(context);
		arrowImageView = new ImageView(context);
		// 从包里获取的箭头图片
		arrowImage = PullToRefreshView.getBitmapFromSrc("image/arrow.png");
		arrowImageView.setImageBitmap(arrowImage);
		// style="?android:attr/progressBarStyleSmall" 默认的样式
		headerProgressBar = new ProgressBar(context, null,
				android.R.attr.progressBarStyle);
		headerProgressBar.setVisibility(View.GONE);

		LayoutParams layoutParamsWW = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWW.gravity = Gravity.CENTER;
		layoutParamsWW.width = PullToRefreshView.scaleValue(mContext, 50);
		layoutParamsWW.height = PullToRefreshView.scaleValue(mContext, 50);
		headImage.addView(arrowImageView, layoutParamsWW);
		headImage.addView(headerProgressBar, layoutParamsWW);

		// 顶部刷新栏文本内容
		LinearLayout headTextLayout = new LinearLayout(context);
		tipsTextview = new TextView(context);
		headerTimeView = new TextView(context);
		headTextLayout.setOrientation(LinearLayout.VERTICAL);
		headTextLayout.setGravity(Gravity.CENTER_VERTICAL);
		PullToRefreshView.setPadding(headTextLayout, 0, 0, 0, 0);
		LayoutParams layoutParamsWW2 = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		headTextLayout.addView(tipsTextview, layoutParamsWW2);
		headTextLayout.addView(headerTimeView, layoutParamsWW2);
		tipsTextview.setTextColor(Color.rgb(255, 255, 255));
		headerTimeView.setTextColor(Color.rgb(255, 255, 255));
		PullToRefreshView.setTextSize(tipsTextview, 30);
		PullToRefreshView.setTextSize(headerTimeView, 27);

		LayoutParams layoutParamsWW3 = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWW3.gravity = Gravity.CENTER;
		layoutParamsWW3.rightMargin = PullToRefreshView
				.scaleValue(mContext, 10);

		LinearLayout headerLayout = new LinearLayout(context);
		headerLayout.setOrientation(LinearLayout.HORIZONTAL);
		headerLayout.setGravity(Gravity.CENTER);

		headerLayout.addView(headImage, layoutParamsWW3);
		headerLayout.addView(headTextLayout, layoutParamsWW3);

		LayoutParams lp = new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.BOTTOM;
		// 添加大布局
		headerView.addView(headerLayout, lp);
		this.addView(headerView, lp);
		// 获取View的高度
		PullToRefreshView.measureView(this);
		headerHeight = this.getMeasuredHeight();

		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
		setState(STATE_NORMAL);
	}

	/**
	 * 设置状态.
	 *
	 * @param state
	 *            the new state
	 */
	public void setState(int state) {
		if (state == mState)
			return;
		if (state == STATE_REFRESHING) {
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.INVISIBLE);
			headerProgressBar.setVisibility(View.VISIBLE);
		} else {
			arrowImageView.setVisibility(View.VISIBLE);
			headerProgressBar.setVisibility(View.INVISIBLE);
		}
		switch (state) {
			case STATE_NORMAL:
				if (mState == STATE_READY) {
					arrowImageView.startAnimation(mRotateDownAnim);
				}
				if (mState == STATE_REFRESHING) {
					arrowImageView.clearAnimation();
				}
				tipsTextview.setText("下拉刷新");

				if (lastRefreshTime == null) {
					lastRefreshTime = PullToRefreshView
							.getCurrentDate(PullToRefreshView.dateFormatHMS);
					headerTimeView.setText("刷新时间：" + lastRefreshTime);
				} else {
					headerTimeView.setText("上次刷新时间：" + lastRefreshTime);
				}
				break;
			case STATE_READY:
				if (mState != STATE_READY) {
					arrowImageView.clearAnimation();
					arrowImageView.startAnimation(mRotateUpAnim);
					tipsTextview.setText("松开刷新");
					headerTimeView.setText("上次刷新时间：" + lastRefreshTime);
					lastRefreshTime = PullToRefreshView
							.getCurrentDate(PullToRefreshView.dateFormatHMS);
				}
				break;
			case STATE_REFRESHING:
				tipsTextview.setText("正在刷新...");
				headerTimeView.setText("本次刷新时间：" + lastRefreshTime);
				break;
			default:
		}

		mState = state;
	}

	/**
	 * 设置header可见的高度.
	 *
	 * @param height
	 *            the new visiable height
	 */
	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LayoutParams lp = (LayoutParams) headerView
				.getLayoutParams();
		lp.height = height;
		headerView.setLayoutParams(lp);
	}

	/**
	 * 获取header可见的高度.
	 *
	 * @return the visiable height
	 */
	public int getVisiableHeight() {
		LayoutParams lp = (LayoutParams) headerView
				.getLayoutParams();
		return lp.height;
	}

	/**
	 * 描述：获取HeaderView.
	 *
	 * @return the header view
	 */
	public LinearLayout getHeaderView() {
		return headerView;
	}

	/**
	 * 设置上一次刷新时间.
	 *
	 * @param time
	 *            时间字符串
	 */
	public void setRefreshTime(String time) {
		headerTimeView.setText(time);
	}

	/**
	 * 获取header的高度.
	 *
	 * @return 高度
	 */
	public int getHeaderHeight() {
		return headerHeight;
	}

	/**
	 * 描述：设置字体颜色.
	 *
	 * @param color
	 *            the new text color
	 */
	public void setTextColor(int color) {
		tipsTextview.setTextColor(color);
		headerTimeView.setTextColor(color);
	}

	/**
	 * 描述：设置背景颜色.
	 *
	 * @param color
	 *            the new background color
	 */
	public void setBackgroundColor(int color) {
		headerView.setBackgroundColor(color);
	}

	/**
	 * 描述：获取Header ProgressBar，用于设置自定义样式.
	 *
	 * @return the header progress bar
	 */
	public ProgressBar getHeaderProgressBar() {
		return headerProgressBar;
	}

	/**
	 * 描述：设置Header ProgressBar样式.
	 *
	 * @param indeterminateDrawable
	 *            the new header progress bar drawable
	 */
	public void setHeaderProgressBarDrawable(Drawable indeterminateDrawable) {
		headerProgressBar.setIndeterminateDrawable(indeterminateDrawable);
	}

	/**
	 * 描述：得到当前状态.
	 *
	 * @return the state
	 */
	public int getState() {
		return mState;
	}

	/**
	 * 设置提示状态文字的大小.
	 *
	 * @param size
	 *            the new state text size
	 */
	public void setStateTextSize(int size) {
		tipsTextview.setTextSize(size);
	}

	/**
	 * 设置提示时间文字的大小.
	 *
	 * @param size
	 *            the new time text size
	 */
	public void setTimeTextSize(int size) {
		headerTimeView.setTextSize(size);
	}

	/**
	 * Gets the arrow image view.
	 *
	 * @return the arrow image view
	 */
	public ImageView getArrowImageView() {
		return arrowImageView;
	}

	/**
	 * 描述：设置顶部刷新图标.
	 *
	 * @param resId
	 *            the new arrow image
	 */
	public void setArrowImage(int resId) {
		this.arrowImageView.setImageResource(resId);
	}

}

class ListViewFooter extends LinearLayout {

	/** The m context. */
	private Context mContext;

	/** The m state. */
	private int mState = -1;

	/** The Constant STATE_READY. */
	public final static int STATE_READY = 1;

	/** The Constant STATE_LOADING. */
	public final static int STATE_LOADING = 2;

	/** The Constant STATE_NO. */
	public final static int STATE_NO = 3;

	/** The Constant STATE_EMPTY. */
	public final static int STATE_EMPTY = 4;

	/** The footer view. */
	private LinearLayout footerView;

	/** The footer progress bar. */
	private ProgressBar footerProgressBar;

	/** The footer text view. */
	private TextView footerTextView;

	/** The footer content height. */
	private int footerHeight;

	/**
	 * Instantiates a new ab list view footer.
	 *
	 * @param context
	 *            the context
	 */
	public ListViewFooter(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * Instantiates a new ab list view footer.
	 *
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public ListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		setState(STATE_READY);
	}

	/**
	 * Inits the view.
	 *
	 * @param context
	 *            the context
	 */
	private void initView(Context context) {
		mContext = context;

		// 底部刷新
		footerView = new LinearLayout(context);
		// 设置布局 水平方向
		footerView.setOrientation(LinearLayout.HORIZONTAL);
		footerView.setGravity(Gravity.CENTER);
		footerView
				.setMinimumHeight(PullToRefreshView.scaleValue(mContext, 100));
		footerTextView = new TextView(context);
		footerTextView.setGravity(Gravity.CENTER_VERTICAL);
		setTextColor(Color.rgb(107, 107, 107));
		PullToRefreshView.setTextSize(footerTextView, 30);

		PullToRefreshView.setPadding(footerView, 0, 10, 0, 10);

		footerProgressBar = new ProgressBar(context, null,
				android.R.attr.progressBarStyle);
		footerProgressBar.setVisibility(View.GONE);

		LayoutParams layoutParamsWW = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWW.gravity = Gravity.CENTER;
		layoutParamsWW.width = PullToRefreshView.scaleValue(mContext, 50);
		layoutParamsWW.height = PullToRefreshView.scaleValue(mContext, 50);
		layoutParamsWW.rightMargin = PullToRefreshView.scaleValue(mContext, 10);
		footerView.addView(footerProgressBar, layoutParamsWW);

		LayoutParams layoutParamsWW1 = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		footerView.addView(footerTextView, layoutParamsWW1);

		LayoutParams layoutParamsFW = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(footerView, layoutParamsFW);

		// 获取View的高度
		PullToRefreshView.measureView(this);
		footerHeight = this.getMeasuredHeight();
	}

	/**
	 * 设置当前状态.
	 *
	 * @param state
	 *            the new state
	 */
	public void setState(int state) {

		if (state == STATE_READY) {
			footerView.setVisibility(View.VISIBLE);
			footerTextView.setVisibility(View.VISIBLE);
			footerProgressBar.setVisibility(View.GONE);
			footerTextView.setText("载入更多");
		} else if (state == STATE_LOADING) {
			footerView.setVisibility(View.VISIBLE);
			footerTextView.setVisibility(View.VISIBLE);
			footerProgressBar.setVisibility(View.VISIBLE);
			footerTextView.setText("正在加载...");
		} else if (state == STATE_NO) {
			footerView.setVisibility(View.GONE);
			footerTextView.setVisibility(View.VISIBLE);
			footerProgressBar.setVisibility(View.GONE);
			footerTextView.setText("没有了！");
		} else if (state == STATE_EMPTY) {
			footerView.setVisibility(View.GONE);
			footerTextView.setVisibility(View.GONE);
			footerProgressBar.setVisibility(View.GONE);
			footerTextView.setText("没有数据");
		}
		mState = state;
	}

	/**
	 * Gets the visiable height.
	 *
	 * @return the visiable height
	 */
	public int getVisiableHeight() {
		LayoutParams lp = (LayoutParams) footerView
				.getLayoutParams();
		return lp.height;
	}

	/**
	 * 隐藏footerView.
	 */
	public void hide() {
		LayoutParams lp = (LayoutParams) footerView
				.getLayoutParams();
		lp.height = 0;
		footerView.setLayoutParams(lp);
		footerView.setVisibility(View.GONE);
	}

	/**
	 * 显示footerView.
	 */
	public void show() {
		footerView.setVisibility(View.VISIBLE);
		LayoutParams lp = (LayoutParams) footerView
				.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		footerView.setLayoutParams(lp);
	}

	/**
	 * 描述：设置字体颜色.
	 *
	 * @param color
	 *            the new text color
	 */
	public void setTextColor(int color) {
		footerTextView.setTextColor(color);
	}

	/**
	 * 描述：设置字体大小.
	 *
	 * @param size
	 *            the new text size
	 */
	public void setTextSize(int size) {
		footerTextView.setTextSize(size);
	}

	/**
	 * 描述：设置背景颜色.
	 *
	 * @param color
	 *            the new background color
	 */
	public void setBackgroundColor(int color) {
		footerView.setBackgroundColor(color);
	}

	/**
	 * 描述：获取Footer ProgressBar，用于设置自定义样式.
	 *
	 * @return the footer progress bar
	 */
	public ProgressBar getFooterProgressBar() {
		return footerProgressBar;
	}

	/**
	 * 描述：设置Footer ProgressBar样式.
	 *
	 * @param indeterminateDrawable
	 *            the new footer progress bar drawable
	 */
	public void setFooterProgressBarDrawable(Drawable indeterminateDrawable) {
		footerProgressBar.setIndeterminateDrawable(indeterminateDrawable);
	}

	/**
	 * 描述：获取高度.
	 *
	 * @return the footer height
	 */
	public int getFooterHeight() {
		return footerHeight;
	}

	/**
	 * 设置高度.
	 *
	 * @param height
	 *            新的高度
	 */
	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LayoutParams lp = (LayoutParams) footerView
				.getLayoutParams();
		lp.height = height;
		footerView.setLayoutParams(lp);
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public int getState() {
		return mState;
	}

}
