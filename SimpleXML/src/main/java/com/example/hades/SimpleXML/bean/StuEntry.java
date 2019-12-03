package com.example.hades.SimpleXML.bean;


import org.simpleframework.xml.*;

/*
    Case 1 :
     @Namespace(reference = "urn:sun:params:xml:ns:students")
     public class entry{

    Case 2 : Recommended
    @Root(name = "entry",strict = false)
    @Namespace(reference = "urn:sun:params:xml:ns:students")
    public class StuEntry {
 */

// strict default value is true
@Root(name = "entry", strict = false)
@Namespace(reference = "urn:sun:params:xml:ns:students")
public class StuEntry {
    @Attribute()
    protected String id;

    /*
    Case 1: Recommended
    // required  default value is true.
    @Element (required = false)
    protected String name; // value

    Case 2:
    // ERROR: org.simpleframework.xml.core.ValueRequiredException: Unable to satisfy @org.simpleframework.xml.Element(name=, type=void, data=false, required=true) on field 'stuName' protected java.lang.String com.example.hades.SimpleXML.bean.StuEntry.stuName for class com.example.hades.SimpleXML.bean.StuEntry at line 1
    @Element
    protected String stuName;

    Case 3:
    // ERROR:org.simpleframework.xml.core.ValueRequiredException: Unable to satisfy @org.simpleframework.xml.Element(name=name, type=void, data=false, required=true) on field 'stuName' protected java.lang.String com.example.hades.SimpleXML.bean.StuEntry.stuName for class com.example.hades.SimpleXML.bean.StuEntry at line 1
    // Reason: <entry id="2">  has  name2, but name.
    // Entry must have name. If not have, throw ValueRequiredException.
    Element(name = "name", required = true)
    protected String stuName;

    Case 4:
    @Element(name = "name", required = false)
    protected String stuName;  // If xml have <name> ?  value : null

    Case 5:
    @Element(name = "name4", required = false)
    protected String stuName;   // null. xml not have name4
     */
    @Element(required = false)
    protected String name;

    @Element
    protected String gender;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }
}