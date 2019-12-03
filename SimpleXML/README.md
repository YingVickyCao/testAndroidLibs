# Simple XML

# Basics

- `@Root`

```
@Root(name,strict)
@Root:是外层的标签，
strict = true: 是否精确匹配
```

```
@Namespace(reference = "urn:sun:params:xml:ns:students")
public class entry{

@Root(name = "entry",strict = false)
@Namespace(reference = "urn:sun:params:xml:ns:students")
public class StuEntry {
# name: If 变量 != xml  element name, set name.
```

- `@Element`

```
  @ElementList(entry = "entry",inline = true, required = false)

  entry: 每个子元素的标签名字. 可省略。
  required = true： 是否精确匹配. xml must have the element name ( @Element(name)) , or ERROR
  ValueRequiredException.
  inline: 是否内联，确定元素列表是否相对于父 XML 元素内联。内联元素列表不包含封闭元素。它是元素中一个接一个出现的元素序列。因此，内联元素列表必须没有名称
```

```
@Element(name,required = true)

@ElementList(entry = "entry",inline = true, required = false)
protected List<StuEntry> entries;

@ElementList(entry = "entry",inline = true, required = false)
protected List<StuEntry> entries;

```

- `@Attribute`

```
@Attribute(name = "name", required = false)
```

# Code sample

```
org.simpleframework:simple-xml:2.7.1
```

```
# stu.xml
<?xml version="1.0" encoding="UTF-8"?>
<!--<StudentRecords xmls:soap="http://com.example.hades.SimpleXML">-->
<!--     <soap:value>123</soap:value> -->
<!--        https://blog.csdn.net/Fancy_xty/article/details/52814184-->

<!-- StuRecords -->
<StudentRecords xmls="http://com.example.hades.SimpleXML">
<!-- List<GradleRecord> gradleRecordList -->
<!-- GradleRecord -->
    <list name="grade1">

        <!-- List<StuEntry> entries -->
        <!-- StuEntry -->
        <entry id="1">
            <name>Jobs</name>
            <gender>Boy</gender>
            <phone>phone 1</phone>
            <phone>phone 2</phone>
        </entry>

        <!-- StuEntry -->
        <entry id="2">
            <name2>Helen Keller</name2>
            <gender>Girl</gender>
        </entry>
    </list>
    <list name="grade2">
        <entry id="1">
            <name>Bill</name>
            <gender>Boy</gender>
        </entry>
    </list>

<!--    <im:image height="170">111</im:image>-->
<!--         <xx im:image="170">111</xx> -->
</StudentRecords>
```

```
# StuEntry

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

    @ElementList(entry = "phone", inline = true, required = false)
    protected List<String> phones;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public List<String> getPhones() {
        return phones;
    }
}
```
