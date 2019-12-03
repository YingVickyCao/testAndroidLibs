package com.example.hades.SimpleXML;

import com.example.hades.SimpleXML.bean.GradleRecord;
import com.example.hades.SimpleXML.bean.StuEntry;
import com.example.hades.SimpleXML.bean.StuRecords;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TestStu {
    public static void main(String[] args) {
        new TestStu().test();
    }

    private void test() {
        try {
            String result = getXMLString();
            StuRecords studentRecords = convertXMLStringToBean(result, StuRecords.class);
            printStuRecords(studentRecords);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T convertXMLStringToBean(String result, Class<? extends T> dest) {
        final Serializer serializer = new Persister();
        try {
            return serializer.read(dest, result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getXMLString() throws IOException {
        // ERROR: Exception in thread "main" java.io.FileNotFoundException: stu.xml (No such file or directory)
        // Refs: https://www.jianshu.com/p/7521bfe45001
//        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("stu.xml")));
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(getResourceFileName("stu.xml"))));
        String tmpStr = "";
        String result = "";
        while ((tmpStr = br.readLine()) != null) {
            result = result + tmpStr.trim();
        }
        System.out.print("the xml file is: " + result + "\n");
        return result;
    }

    public static void printStuRecords(StuRecords stuRecords) {
        List<GradleRecord> gradeList = stuRecords.getGradleRecordList();
        for (GradleRecord eachList : gradeList) {
            List<StuEntry> entries = eachList.getEntries();
            System.out.print("\n" + "student Grade: " + eachList.getName() + "\n");
            for (StuEntry stuEntry : entries) {
                printStuEntry(stuEntry);
            }
        }
    }

    private static void printStuEntry(StuEntry stuEntry) {
        if (null == stuEntry) {
            return;
        }
        System.out.print("student ID: " + stuEntry.getId() + "\n");
        System.out.print("student Name: " + stuEntry.getName() + "\n");
        System.out.print("student Gender: " + stuEntry.getGender() + "\n");
        System.out.print("student Phones: " + stuEntry.getPhones() + "\n");
    }

    public static String getResourceFileName(String fileName) {
        return TestStu.class.getClassLoader().getResource(fileName).getPath();
    }
}