package com.example.hades.SimpleXML.bean;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "list", strict = false)
@Namespace(reference = "urn:sun:params:xml:ns:students")
public class GradleRecord {
    @Attribute(required = true)
    protected String name;

    // NullPointerException
    // @ElementList(entry = "stu",inline = true, required = false)
    @ElementList(entry = "entry", inline = true, required = false)
    protected List<StuEntry> entries;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StuEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<StuEntry> entries) {
        this.entries = entries;
    }
}
