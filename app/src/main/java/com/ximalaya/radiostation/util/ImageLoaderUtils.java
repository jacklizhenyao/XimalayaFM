package com.lanou.radiostation.util;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.lanou.radiostation.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

/**
 * ImageLoder的工具类 1、设置了加载图片的一些规则 2、设置是否使用缓存
 *
 * @author dell
 *
 */
public class ImageLoaderUtils {

	// 加载图片
	public static void getImageByloader(
			String url, ImageView image) {
		DisplayImageOptions options =
				new DisplayImageOptions.Builder()
						// 设置图片在下载期间显示的图片
						.showImageOnLoading(
								R.mipmap.ic_launcher)
						// 设置图片Uri为空或是错误的时候显示的图片
						.showImageForEmptyUri(
								R.mipmap.ic_launcher)
						// 设置图片加载/解码过程中错误时候显示的图片
						.showImageOnFail(
								R.mipmap.ic_launcher)
						// 设置下载的图片是否缓存在内存中
						.cacheInMemory(true)
						// 设置下载的图片是否缓存在SD卡中
						.cacheOnDisk(true)
						.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
						// 图片质量
						.bitmapConfig(Bitmap.Config.RGB_565)
						// 图片加载好后渐入的动画时间
						// .displayer(new FadeInBitmapDisplayer(1000))//
						// 设置成圆角图片
						// .displayer(new RoundedBitmapDisplayer(20))
						.build();

		// 加载图片
		ImageLoader.getInstance().displayImage(
				url,
				image,
				options,
				new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String arg0, View arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLoadingFailed(String arg0, View arg1,
												FailReason arg2) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLoadingComplete(String arg0,
												  View arg1, Bitmap arg2) {
						// Toast.makeText(context, "完成", 0).show();
					}

					@Override
					public void onLoadingCancelled(String arg0,
												   View arg1) {
						// TODO Auto-generated method stub

					}
				}, new ImageLoadingProgressListener() {

					@Override
					public void onProgressUpdate(String arg0,
												 View arg1, int arg2, int arg3) {
						// Toast.makeText(context, "加载",0).show();
					}
				});
		// }

		// 清除内存缓存
		// ImageLoader.getInstance().clearMemoryCache();

		// 清除本地缓存
		// ImageLoader.getInstance().clearDiscCache();

	}

}
