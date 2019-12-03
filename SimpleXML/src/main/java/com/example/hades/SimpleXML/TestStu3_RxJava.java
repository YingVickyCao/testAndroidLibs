package com.example.hades.SimpleXML;

import com.example.hades.SimpleXML.bean.StuRecords;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.example.hades.SimpleXML.TestStu.*;

public class TestStu3_RxJava {
    public static void main(String[] args) {
        new TestStu3_RxJava().test();
    }

    private void test() {

        Observable.create(new ObservableOnSubscribe<StuRecords>() {
            @Override
            public void subscribe(ObservableEmitter<StuRecords> emitter) throws Exception { // thread -1
//                Log.d(TAG, "subscribe: " + LogHelper.getThreadInfo());
                String result = getXMLString();
                StuRecords studentRecords = convertXMLStringToBean(result, StuRecords.class);
                emitter.onNext(studentRecords);
            }
        })// TODO: 2019/12/3 when use thread, not invoke onNext
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<StuRecords>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(StuRecords stuRecords) {
                        printStuRecords(stuRecords);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("getStu, onError: " + throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}