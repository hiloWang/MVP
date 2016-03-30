package com.user.hilo.data;

import com.user.hilo.bean.DailyData;
import com.user.hilo.model.DailyModel;
import com.user.hilo.presenter.MainPresenter;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/3/24.
 */
public class DataManager {

    private static DataManager instance;

    private DailyModel dailyModel;
//    private DataModel dataModel;

    private DataManager() {
        dailyModel = DailyModel.getInstance();
    }

    public synchronized static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();

        }
        return instance;
    }

    public Observable<List<DailyData>> getDailyDataByNetWork(MainPresenter.TodayDate currentDate) {
//        return Observable.just(currentDate)
//                .flatMapIterable(MainPresenter.TodayDate::getPastTime)
//                .flatMap(todayDate -> {
//                    return this.dailyModel
//                          .getDaily(todayDate.getYear(), todayDate.getMonth(), todayDate.getDay())
//                          .filter(dailyData -> dailyData.results.testData != null);
//                })
//                .toSortedList((dailyData1, dailyData2) -> {
//                    return dailyData2.results.testData.get(0).createAt.compareTo(dailyData1.results.testData.get(0).createAt);
//                }).compose(RxUtils.applyIOToMainThreadSchedulers());
        return null;
    }

}
