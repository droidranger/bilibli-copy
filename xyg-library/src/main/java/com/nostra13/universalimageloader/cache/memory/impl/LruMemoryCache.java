package com.nostra13.universalimageloader.cache.memory.impl;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.memory.MemoryCache;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A cache that holds strong references to a limited number of Bitmaps. Each time a Bitmap is accessed, it is moved to
 * the head of a queue. When a Bitmap is added to a full cache, the Bitmap at the end of that queue is evicted and may
 * become eligible for garbage collection.<br />
 * <br />
 * 注意：该缓存算法只用于强引用存储Bitmap的情形
 * <b>NOTE:</b> This cache uses only strong references for stored Bitmaps.
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.8.1
 */
// 该缓存算法是：1.缓存有最大值  2.最新访问的Bitmap放在队列头部  3.缓存超过最大值，就把队列end的Bitmap删除掉
public class LruMemoryCache implements MemoryCache {

	// LinkedHashMap维护的是一个按顺序存放的双向链表，是有序的
	private final LinkedHashMap<String, Bitmap> map;

	private final int maxSize;
	/** Size of this cache in bytes */
	private int size;

	/** @param maxSize Maximum sum of the sizes of the Bitmaps in this cache */
	public LruMemoryCache(int maxSize) {
		if (maxSize <= 0) {
			throw new IllegalArgumentException("maxSize <= 0");
		}
		this.maxSize = maxSize;
		// 第一个参数是初始容量；0.75是加载因子；最后一个参数是用来控制元素的顺序的：true: 是访问的顺序，也就是谁最先访问，就排在第一位  false:存放顺序，就是你put元素的时候的顺序
		// 加载因子是哈希表在其容量自动增加之前可以达到多满的一种尺度。
		// 当哈希表中的条目数超出了加载因子与当前容量的乘积时，则要对该哈希表进行 rehash 操作（即重建内部数据结构），
		// 从而哈希表将具有大约两倍的桶数。通常，默认加载因子是 0.75, 这是在时间和空间成本上寻求一种折衷。
		// 加载因子过高虽然减少了空间开销，但同时也增加了查询成本（在大多数 HashMap 类的操作中，包括 get 和 put 操作，都反映了这一点）。
		// 在设置初始容量时应该考虑到映射中所需的条目数及其加载因子，以便最大限度地减少 rehash 操作次数。
		// 如果初始容量大于最大条目数除以加载因子，则不会发生 rehash 操作。
		this.map = new LinkedHashMap<String, Bitmap>(0, 0.75f, true);
	}

	/**
	 * Returns the Bitmap for {@code key} if it exists in the cache. If a Bitmap was returned, it is moved to the head
	 * of the queue. This returns null if a Bitmap is not cached.
	 */
	@Override
	public final Bitmap get(String key) {
		if (key == null) {
			throw new NullPointerException("key == null");
		}

		synchronized (this) {
			return map.get(key);
		}
	}

	/** Caches {@code Bitmap} for {@code key}. The Bitmap is moved to the head of the queue. */
	@Override
	public final boolean put(String key, Bitmap value) {
		if (key == null || value == null) {
			throw new NullPointerException("key == null || value == null");
		}

		synchronized (this) {
			// sizeOf计算新的Bitmap的size
			size += sizeOf(key, value);
			// 如果key以前就存在，说明缓存过该图片，在LinkedHashMap的preModify方法中会把新的bitmap移到头部，并返回oldbitmap返回
			Bitmap previous = map.put(key, value);
			if (previous != null) {
                // 如果之前缓存过该图片，就将减掉之前缓存的图片size
				size -= sizeOf(key, previous);
			}
		}

		trimToSize(maxSize);
		return true;
	}

	/**
	 * Remove the eldest entries until the total of remaining entries is at or below the requested size.
	 *
	 * @param maxSize the maximum size of the cache before returning. May be -1 to evict even 0-sized elements.
	 */
	private void trimToSize(int maxSize) {
		while (true) {
			String key;
			Bitmap value;
			synchronized (this) {
				if (size < 0 || (map.isEmpty() && size != 0)) {
					throw new IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
				}
                // 如果size小于maxSize，什么也不处理
				if (size <= maxSize || map.isEmpty()) {
					break;
				}
                // size大于maxSize，就获取第一个元素，并remove第一个元素
				Map.Entry<String, Bitmap> toEvict = map.entrySet().iterator().next();
				if (toEvict == null) {
					break;
				}
				key = toEvict.getKey();
				value = toEvict.getValue();
				map.remove(key);
				size -= sizeOf(key, value);
			}
		}
	}

	/** Removes the entry for {@code key} if it exists. */
	@Override
	public final Bitmap remove(String key) {
		if (key == null) {
			throw new NullPointerException("key == null");
		}

		synchronized (this) {
			Bitmap previous = map.remove(key);
			if (previous != null) {
				size -= sizeOf(key, previous);
			}
			return previous;
		}
	}

	@Override
	public Collection<String> keys() {
		synchronized (this) {
			return new HashSet<String>(map.keySet());
		}
	}

	@Override
	public void clear() {
		trimToSize(-1); // -1 will evict 0-sized elements
	}

	/**
	 * Returns the size {@code Bitmap} in bytes.
	 * <p/>
	 * An entry's size must not change while it is in the cache.
	 */
	private int sizeOf(String key, Bitmap value) {
		return value.getRowBytes() * value.getHeight();
	}

	@Override
	public synchronized final String toString() {
		return String.format("LruCache[maxSize=%d]", maxSize);
	}
}