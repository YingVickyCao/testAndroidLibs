package com.example.hades.SimpleXML.bean;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;
@Root(name = "StudentRecords", strict = false)
public class StuRecords {
    @ElementList(inline = true, required = false)
    protected List<GradleRecord> gradleRecordList;

    public List<GradleRecord> getGradleRecordList() {
        return gradleRecordList;
    }
}