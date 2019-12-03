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
            StuRecords studentRecords = serializer.read(StuRecords.class, new String(result));
            List<GradleRecord> gradeList = studentRecords.getGradleRecordList();
            for (int gradeNo = 0; gradeNo < gradeList.size(); gradeNo++) {
                GradleRecord eachList = (GradleRecord) (gradeList.get(gradeNo));
                List<StuEntry> entries = eachList.getEntries();
                System.out.print("\n" + "student Grade: " + eachList.getName() + "\n");
                for (int studentID = 0; studentID < entries.size(); studentID++) {
                    StuEntry eachStuEntry = (StuEntry) (entries.get(studentID));
                    System.out.print("student ID: " + eachStuEntry.getId() + "\n");
                    System.out.print("student Name: " + eachStuEntry.getName() + "\n");
                    System.out.print("student Gender: " + eachStuEntry.getGender() + "\n");
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // TODO: 2019/12/3  refactor
    private String getResourceFileName() {
        return TestStu.class.getClassLoader().getResource("stu.xml").getPath();
    }
}