/*******************************************************************************
 * Copyright 2011-2014 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.nostra13.universalimageloader.cache.memory;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.utils.L;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Limited cache. Provides object storing. Size of all stored bitmaps will not to exceed size limit (
 * {@link #getSizeLimit()}).<br />
 * <br />
 * // 注意：该缓存策略用于存储Bitmap的强引用和弱引用。强引用：会根据缓存size来限制了Bitmap的个数；弱引用：
 * <b>NOTE:</b> This cache uses strong and weak references for stored Bitmaps. Strong references - for limited count of
 * Bitmaps (depends on cache size), weak references - for all other cached Bitmaps.
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @see BaseMemoryCache
 * @since 1.0.0
 */
public abstract class LimitedMemoryCache extends BaseMemoryCache {

	private static final int MAX_NORMAL_CACHE_SIZE_IN_MB = 16;
	private static final int MAX_NORMAL_CACHE_SIZE = MAX_NORMAL_CACHE_SIZE_IN_MB * 1024 * 1024;

	private final int sizeLimit;

	private final AtomicInteger cacheSize;

	/**
	 * Contains strong references to stored objects. Each next object is added last. If hard cache size will exceed
	 * limit then first object is deleted (but it continue exist at {@link #softMap} and can be collected by GC at any
	 * time)
	 */
	// 新的object会被添加到链表最后。如果缓存size超过limit，第一个objec会被删除
	private final List<Bitmap> hardCache = Collections.synchronizedList(new LinkedList<Bitmap>());

	/** @param sizeLimit Maximum size for cache (in bytes) */
	public LimitedMemoryCache(int sizeLimit) {
		this.sizeLimit = sizeLimit;
		cacheSize = new AtomicInteger();
		// maxSize超过16MB会报警
		if (sizeLimit > MAX_NORMAL_CACHE_SIZE) {
			L.w("You set too large memory cache size (more than %1$d Mb)", MAX_NORMAL_CACHE_SIZE_IN_MB);
		}
	}

	@Override
	public boolean put(String key, Bitmap value) {
		boolean putSuccessfully = false;
		// Try to add value to hard cache
		int valueSize = getSize(value);
		int sizeLimit = getSizeLimit();
		int curCacheSize = cacheSize.get();
		if (valueSize < sizeLimit) {
			// 如果当前的缓存size加上要存储的Bitmap的size大于最大缓存值，就删除一个元素。删除哪个元素，看具体算法。
			while (curCacheSize + valueSize > sizeLimit) {
				// 删除的Bitmap。removeNext方法是个基类方法，会在子类中实现，可能是第一个，也可能是最后一个，看具体实现。
				Bitmap removedValue = removeNext();
				if (hardCache.remove(removedValue)) {
					curCacheSize = cacheSize.addAndGet(-getSize(removedValue));
				}
			}
			// 如果超限，先移除元素，保证足够空间可添加新的元素
			// 看harCache是链表，再看链表的add方法，该方法调用了addLastImpl方法，即新元素是添加到链表尾部的
			hardCache.add(value);
			cacheSize.addAndGet(valueSize);

			putSuccessfully = true;
		}
		// Add value to soft cache
		super.put(key, value);
		return putSuccessfully;
	}

	@Override
	public Bitmap remove(String key) {
		Bitmap value = super.get(key);
		if (value != null) {
			if (hardCache.remove(value)) {
				cacheSize.addAndGet(-getSize(value));
			}
		}
		return super.remove(key);
	}

	@Override
	public void clear() {
		hardCache.clear();
		cacheSize.set(0);
		super.clear();
	}

	protected int getSizeLimit() {
		return sizeLimit;
	}

	protected abstract int getSize(Bitmap value);

	protected abstract Bitmap removeNext();
}
