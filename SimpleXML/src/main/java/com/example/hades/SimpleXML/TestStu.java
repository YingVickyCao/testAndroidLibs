package com.example.hades.SimpleXML;

import com.example.hades.SimpleXML.bean.StuEntry;
import com.example.hades.SimpleXML.bean.GradleRecord;
import com.example.hades.SimpleXML.bean.StuRecords;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TestStu {
    public static void main(String[] args) throws IOException {
        new TestStu().test();
    }

    private void test() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(getResourceFileName("stu.xml"))));
        final Serializer serializer = new Persister();

        String tmpStr = "";
        String result = "";
        while ((tmpStr = br.readLine()) != null) {
            result = result + tmpStr.trim();
        }
        System.out.print("the xml file is: " + result + "\n");
        try {
            StuRecords studentRecords = serializer.read(StuRecords.class, result);
            List<GradleRecord> gradeList = studentRecords.getGradleRecordList();

            for (GradleRecord eachList : gradeList) {
                List<StuEntry> entries = eachList.getEntries();
                System.out.print("\n" + "student Grade: " + eachList.getName() + "\n");
                for (StuEntry stuEntry : entries) {
                    printStuInfo(stuEntry);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printStuInfo(StuEntry stuEntry){
        if (null == stuEntry){
            return;
        }
        System.out.print("student ID: " + stuEntry.getId() + "\n");
        System.out.print("student Name: " + stuEntry.getName() + "\n");
        System.out.print("student Gender: " + stuEntry.getGender() + "\n");
        System.out.print("student Phones: " + stuEntry.getPhones() + "\n");
    }

    private String getResourceFileName(String fileName) {
        return TestStu.class.getClassLoader().getResource(fileName).getPath();
    }
}