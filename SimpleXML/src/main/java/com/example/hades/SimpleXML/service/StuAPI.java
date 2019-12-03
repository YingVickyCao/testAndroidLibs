package com.example.hades.SimpleXML.service;

import com.example.hades.SimpleXML.bean.StuRecords;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface StuAPI {

    // https://gitee.com/YingVickyCao/testAndroidLibs/raw/master/SimpleXML/src/main/resources/stu.xml
    @GET("stu.xml")
    Observable<StuRecords> getStu();
}
