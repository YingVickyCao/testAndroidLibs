package com.example.hades.SimpleXML;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class stuTest {
    public static void main(String[] args) throws IOException {
        new stuTest().test();
    }

    private void test() throws IOException {
        String result = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(getResourceFileName())));
        String tmpStr = "";
        final Serializer serializer;
        serializer = new Persister();

        while ((tmpStr = br.readLine()) != null) {
            result = result + tmpStr.trim();
        }
        System.out.print("the xml file is: " + result + "\n");
        try {
            SunStudent studentRecords = serializer.read(SunStudent.class, new String(result));
            List<SunList> gradeList = studentRecords.getList();
            for (int gradeNo = 0; gradeNo < gradeList.size(); gradeNo++) {
                SunList eachList = (SunList) (gradeList.get(gradeNo));
                List<SunEntry> entries = eachList.getEntries();
                System.out.print("\n" + "student Grade: " + eachList.getName() + "\n");
                for (int studentID = 0; studentID < entries.size(); studentID++) {
                    SunEntry eachEntry = (SunEntry) (entries.get(studentID));
                    System.out.print("student ID: " + eachEntry.getId() + "\n");
                    System.out.print("student Name: " + eachEntry.getName() + "\n");
                    System.out.print("student Gender: " + eachEntry.getGender() + "\n");
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // TODO: 2019/12/3  refactor
    private String getResourceFileName() {
        return stuTest.class.getClassLoader().getResource("stu.xml").getPath();
    }
}