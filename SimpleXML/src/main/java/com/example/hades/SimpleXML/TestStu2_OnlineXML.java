package com.example.hades.SimpleXML;

import com.example.hades.SimpleXML.bean.StuRecords;
import com.example.hades.SimpleXML.service.StuService;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.io.IOException;

import static com.example.hades.SimpleXML.TestStu.printStuRecords;

public class TestStu2_OnlineXML {
    public static void main(String[] args) throws IOException {
        new TestStu2_OnlineXML().test();
    }

    private void test() throws IOException {
        StuService.getInstance().getTestService()
                .getStu()
                // TODO: 2019/12/3 when use thread, not invoke onNext 
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