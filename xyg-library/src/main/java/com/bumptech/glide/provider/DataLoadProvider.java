package com.bumptech.glide.provider;

import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.ResourceEncoder;

import java.io.File;

/**
 * DataLoadProvider用于提供各种编码器或解码器，用于Data和Resource之间的编解码。
 *
 * A load provider that provides the necessary encoders and decoders to decode a specific type of resource from a
 * specific type of data.
 *
 * @param <T> The type of data the resource will be decoded from.
 * @param <Z> The type of resource that will be decoded.
 */
public interface DataLoadProvider<T, Z> {

    /**
     * File--->Resource   返回解码器，用于解码来自磁盘缓存的资源
     * Returns the {@link ResourceDecoder} to use to decode the resource from the disk cache.
     */
    ResourceDecoder<File, Z> getCacheDecoder();

    /**
     * Data---->Resource  返回解码器，用于从原始数据中解码资源
     * Returns the {@link ResourceDecoder} to use to decode the resource from the original data.
     */
    ResourceDecoder<T, Z> getSourceDecoder();

    /**
     * Data---->File  返回编码器，用于将原始数据写到磁盘缓存
     * Returns the {@link Encoder} to use to write the original data to the disk cache.
     */
    Encoder<T> getSourceEncoder();

    /**
     * Resource---->File   返回编码器，用于将已解码和已转换的资源写到磁盘缓存
     * Returns the {@link ResourceEncoder} to use to write the decoded and transformed resource
     * to the disk cache.
     */
    ResourceEncoder<Z> getEncoder();
}
