package com.example.hades.SimpleXML;

import com.example.hades.SimpleXML.bean.GradleRecord;
import com.example.hades.SimpleXML.bean.StuEntry;
import com.example.hades.SimpleXML.bean.StuRecords;
import com.example.hades.SimpleXML.service.StuService;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.io.IOException;
import java.util.List;

public class TestStu2 {
    public static void main(String[] args) throws IOException {
        new TestStu2().test();
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
                List<GradleRecord> gradeList = stuRecords.getGradleRecordList();
                for (GradleRecord eachList : gradeList) {
                    List<StuEntry> entries = eachList.getEntries();
                    System.out.print("\n" + "student Grade: " + eachList.getName() + "\n");
                    for (StuEntry stuEntry : entries) {
                        TestStu.printStuInfo(stuEntry);
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("getStu, onError: "+ throwable.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}