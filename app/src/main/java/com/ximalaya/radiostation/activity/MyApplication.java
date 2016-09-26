package com.lanou.radiostation.activity;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;


import com.lanou.radiostation.service.ServiceBroadCast;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.List;

public class MyApplication extends Application {

//	public List<String> songsList;//当前播放列表
//	public int songItemPos;//当前播放音乐在列表中的位置
//	public NotificationManager notManager;

	@Override
	public void onCreate() {
		super.onCreate();
		initImageLoder(this);

//		notManager = (NotificationManager) getSystemService
//				(this.NOTIFICATION_SERVICE);
//		Intent intent = new Intent(this, ServiceBroadCast.class);
//		startService(intent);
	}

	/**
	 * 初始化ImageLoder的方法
	 *
	 * @param context
	 */
	private void initImageLoder(Context context) {
		// 设置缓存图片的路径
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"imageloader/Cache");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				// 设置当前线程的优先级
				context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				// 缓存显示不同大小的同一张图片
				.denyCacheImageMultipleSizesInMemory()
				// .discCacheFileNameGenerator(new
				// Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				// 50 Mb sd卡(本地)缓存的最大值
				.diskCacheSize(50 * 1024 * 1024)
				// sd卡缓存
				.diskCache(new UnlimitedDiscCache(cacheDir))
				// 内存缓存
				.memoryCache(new WeakMemoryCache())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();

		/*
		 * getInstance单例模式：在内存中只能存在一个对象 构造方法私有
		 */
		ImageLoader.getInstance().init(config);
	}

}
