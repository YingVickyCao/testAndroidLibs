package com.example.hades.SimpleXML;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "entry",strict = false)
// TODO: 2019/12/3
@Namespace(reference = "urn:sun:params:xml:ns:students")
public class SunEntry {
    @Attribute(required = true)
    protected  String id;
    
    // TODO: 2019/12/3  
    @Element(name = "name", required = true)
    protected String name;

    @Element(name = "gender", required = true)
    protected String gender;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
