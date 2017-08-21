package com.ranger.xyg.demos.rxjava;

import android.widget.TextView;

import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.ui.activity.BaseActivity;
import com.ranger.xyg.library.utils.ToastUtils;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by xyg on 2017/4/7.
 */

public class MoreRxDemosActivity extends BaseActivity {

    @BindView(R.id.tv_text)
    TextView tvText;

    final String[] mWords = {"hello", "I", "am", "your", "friend"};
    final List<String> mWordList = Arrays.asList(mWords);

    // Action类似订阅者，设置TextView
    private Action1<String> mTextAction = new Action1<String>() {
        @Override
        public void call(String s) {
            tvText.setText(s);
        }
    };

    // Action设置Toast
    // Action1代表最终动作, 因而不需要返回值, 并且一个参数
    private Action1<String> mToastAction = new Action1<String>() {
        @Override
        public void call(String s) {
            ToastUtils.showShort(MoreRxDemosActivity.this, s);
        }
    };

    // 设置映射函数
    // Func1代表使用一个参数的函数, 前面是参数, 后面是返回值
    private Func1<List<String>, Observable<String>> mOneLetterFunc = new Func1<List<String>, Observable<String>>() {
        @Override
        public Observable<String> call(List<String> strings) {
            return Observable.from(strings);// 拆分数组
        }
    };

    // 设置大写字母
    private Func1<String, String> mUpperLetterFunc = new Func1<String, String>() {
        @Override
        public String call(String s) {
            return s.toUpperCase();
        }
    };

    private Func2<String, String, String> mMergeStringFunc = new Func2<String, String, String>() {
        @Override
        public String call(String s, String s2) {
            return String.format("%s %s", s, s2);
        }
    };

    @Override
    protected int getContentResId() {
        return R.layout.activity_more_rx_demos;
    }

    @Override
    protected void initViews() {
        super.initViews();
        // 添加字符串，省略Action的其他方法，只使用一个onNext
        // just可以非常简单的获取任何数据, 分发时, 选择使用的线程
        Observable<String> obShow = Observable.just(sayMyName());
        // 先映射，再设置TextView
        obShow.observeOn(AndroidSchedulers.mainThread())
                // map是对输入数据加工, 转换类型, 输入Func1, 准换大写字母
                .map(mUpperLetterFunc)
                .subscribe(mTextAction);

        // 单独显示数组中的每个元素
        // from是读取数组中的值, 每次单独分发, 并分发多次-----拆分数组
        Observable<String> obMap = Observable.from(mWords);
        // 映射之后分发
        obMap.observeOn(AndroidSchedulers.mainThread())
                .map(mUpperLetterFunc).subscribe(mToastAction);

        // 优化过的代码，直接获取数组，再分发，再合并，再显示Toast，Toast顺次执行
        // 使用just分发数组, 则分发数据就是数组, 并不是数组中的元素
        Observable.just(mWordList)
                .observeOn(AndroidSchedulers.mainThread())
                // flatMap把数组转换为单独分发, Func1内部使用from拆分数组
                .flatMap(mOneLetterFunc)
                // reduce把单独分发数据集中到一起, 再统一分发, 使用Func2
                .reduce(mMergeStringFunc)
                // 最终使用Action1显示获得数据
                .subscribe(mToastAction);
    }

    // 创建字符串
    private String sayMyName() {
        return "Hello, I am your friend";
    }

}
