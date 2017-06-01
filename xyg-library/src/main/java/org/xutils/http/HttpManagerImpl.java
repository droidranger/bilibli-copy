package org.xutils.http;

import org.xutils.HttpManager;
import org.xutils.common.Callback;
import org.xutils.x;

import java.lang.reflect.Type;

/**
 * Created by wyouflf on 15/7/23.
 * HttpManager实现
 */
public final class HttpManagerImpl implements HttpManager {

    private static final Object lock = new Object();
    private static volatile HttpManagerImpl instance;

    private HttpManagerImpl() {
    }

    public static void registerInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new HttpManagerImpl();
                }
            }
        }
        x.Ext.setHttpManager(instance);
    }

    @Override
    public <T> Callback.Cancelable get(RequestParams entity, Callback.CommonCallback<T> callback) {
        return request(HttpMethod.GET, entity, callback);
    }

    @Override
    public <T> Callback.Cancelable post(RequestParams entity, Callback.CommonCallback<T> callback) {
        return request(HttpMethod.POST, entity, callback);
    }

    @Override
    public <T> Callback.Cancelable request(HttpMethod method, RequestParams entity, Callback.CommonCallback<T> callback) {
        entity.setMethod(method);
        Callback.Cancelable cancelable = null;
        // 这里判断了callback是否是Callback.Cancelable实例，我们的例子中这个判断为假。
        // 大家可以看看Callback这个接口，里面定义了好几种回调类型，其中有CommonCallback、TypedCallback、CacheCallback、
        // ProxyCacheCallback、PrepareCallback、ProgressCallback、GroupCallback，这几种回调类型继承了Callback
        // 而Callback.Cancelable并未继承Callback。从名称上可以看出这应该是个cancel回调
        if (callback instanceof Callback.Cancelable) {
            cancelable = (Callback.Cancelable) callback;
        }
        // 实例化HttpTask
        HttpTask<T> task = new HttpTask<T>(entity, cancelable, callback);
        // x.task()获取TaskControllerImpl实例，然后start
        return x.task().start(task);
    }

    @Override
    public <T> T getSync(RequestParams entity, Class<T> resultType) throws Throwable {
        return requestSync(HttpMethod.GET, entity, resultType);
    }

    @Override
    public <T> T postSync(RequestParams entity, Class<T> resultType) throws Throwable {
        return requestSync(HttpMethod.POST, entity, resultType);
    }

    @Override
    public <T> T requestSync(HttpMethod method, RequestParams entity, Class<T> resultType) throws Throwable {
        DefaultSyncCallback<T> callback = new DefaultSyncCallback<T>(resultType);
        return requestSync(method, entity, callback);
    }

    @Override
    public <T> T requestSync(HttpMethod method, RequestParams entity, Callback.TypedCallback<T> callback) throws Throwable {
        entity.setMethod(method);
        HttpTask<T> task = new HttpTask<T>(entity, null, callback);
        return x.task().startSync(task);
    }

    private class DefaultSyncCallback<T> implements Callback.TypedCallback<T> {

        private final Class<T> resultType;

        public DefaultSyncCallback(Class<T> resultType) {
            this.resultType = resultType;
        }

        @Override
        public Type getLoadType() {
            return resultType;
        }

        @Override
        public void onSuccess(T result) {

        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {

        }

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {

        }
    }
}
