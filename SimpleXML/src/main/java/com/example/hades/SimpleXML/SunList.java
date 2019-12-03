package com.example.hades.SimpleXML;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "list", strict = false)
@Namespace(reference = "urn:sun:params:xml:ns:students")
public class SunList {

    @Attribute(required = true)
    protected String name;

    // TODO: 2019/12/3  inline = true, inline = true,required = false
    @ElementList(inline = true, required = false)
    protected List<SunEntry> entries;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SunEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<SunEntry> entries) {
        this.entries = entries;
    }
}
