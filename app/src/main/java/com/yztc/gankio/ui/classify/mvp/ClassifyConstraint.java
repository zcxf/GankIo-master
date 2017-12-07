package com.yztc.gankio.ui.classify.mvp;

import android.content.Context;

import com.yztc.gankio.net.BaseReslut;
import com.yztc.gankio.ui.classify.ClassifyBean;

import java.util.List;

import rx.Observable;

/**
 * Created by wanggang on 2017/1/16.
 */

public class ClassifyConstraint {

    public interface IClassifyView {
        void onPull(List<ClassifyBean> data);//下拉刷新

        void onPush(List<ClassifyBean> data);//上拉加载

        void loadFinish();

        void loadError(String msg);
    }

    public interface IClassifyPresenter {
        void pull();

        void push();
    }

    public interface IClassifyModel {
        Observable<BaseReslut<List<ClassifyBean>>> loadData(Context context, String type, int page);
    }

}
