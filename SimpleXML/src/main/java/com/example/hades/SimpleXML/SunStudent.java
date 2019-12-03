package com.example.hades.SimpleXML;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

// TODO: 2019/12/3  @Root(name = "StudentRecords", strict = false)
@Root(name = "StudentRecords", strict = false)
public class SunStudent {
    @ElementList(inline = true, required = false)
    protected List<SunList> list;

    public List<SunList> getList() {
        return list;
    }

    public void setList(List<SunList> list) {
        this.list = list;
    }
}