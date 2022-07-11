package com.example.citeconsult;

import android.app.Application;

import com.example.citeconsult.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GlobalVariable extends Application {
    private static ArrayList<Instructors> instructorsArrayList = new ArrayList<>();
    private static ArrayList<String> timesArrayList = new ArrayList<>();
    private static ArrayList<String> daysArrayList = new ArrayList<>();
    //morning
    String t1 = "8:00am - 8:30am";
    String t2 = "8:30am - 9:00am";
    String t3 = "9:00am - 9:30am";
    String t4 = "9:30am - 10:00am";
    String t5 = "10:00am - 10:30am";
    String t6 = "10:30am - 11:00am";
    String t7 = "11:00am - 11:30am";
    String t8 = "11:30am - 12:00pm";
    //afternoon
    String t9 = "1:00pm - 1:30pm";
    String t10 = "1:30pm - 2:00pm";
    String t11 = "2:00pm - 2:30pm";
    String t12 = "2:30pm - 3:00pm";
    String t13 = "3:00pm - 3:30pm";
    String t14 = "3:30pm - 4:00pm";
    String t15 = "4:00pm - 4:30pm";
    String t16 = "4:30pm - 5:00pm";
    //days
    String d1 = "Monday";
    String d2 = "Tuesday";
    String d3 = "Wednesday";
    String d4 = "Thursday";
    String d5 = "Friday";

    ArrayList<String> times1 = new ArrayList<>();
    ArrayList<String> days1 = new ArrayList<>();

//    public ArrayList<Instructors> getInstructors() {
//        instructorsArrayList.clear();
//
//
//        List<String> time1 = Arrays.asList(t9,t10,t11,t12,t13,t14,t15,t16);
//        times1.addAll(time1);
//
//
//        List<String> day1 = Arrays.asList(d5);
//        days1.addAll(day1);
//
//        Instructors q1 = new Instructors("Anthony G. Marquez",
//                R.drawable.anthony,
//                times1,
//                days1);
//        instructorsArrayList.add(q1);
//
//        //add instructor2
//        ArrayList<String> times2 = new ArrayList<>();
//        List<String> time2 = Arrays.asList(t5, t6, t7, t8, t11, t12, t13, t14);
//        times2.addAll(time2);
//
//        ArrayList<String> days2 = new ArrayList<>();
//        List<String> day2 = Arrays.asList(d3,d4);
//        days2.addAll(day2);
//
//        Instructors q2 = new Instructors("Arnel B. Ocay", R.drawable.arnel, times2, days2);
//        instructorsArrayList.add(q2);
//
//        //add instructor3
//        ArrayList<String> times3 = new ArrayList<>();
//        List<String> time3 = Arrays.asList(t11, t12, t13, t14);
//        times3.addAll(time3);
//
//        ArrayList<String> days3 = new ArrayList<>();
//        List<String> day3 = Arrays.asList(d2,d5);
//        days3.addAll(day3);
//
//        Instructors q3 = new Instructors("Christian C. Mequin", R.drawable.christian, times3, days3);
//        instructorsArrayList.add(q3);
//
//        //add instructor4
//        ArrayList<String> times4 = new ArrayList<>();
//        List<String> time4 = Arrays.asList(t9,t10,t11, t12);
//        times4.addAll(time4);
//
//        ArrayList<String> days4 = new ArrayList<>();
//        List<String> day4 = Arrays.asList(d3,d5);
//        days4.addAll(day4);
//
//        Instructors q4 = new Instructors("Danilo B. Dorado", R.drawable.danilo, times4, days4);
//        instructorsArrayList.add(q4);
//
//        //add instructor5
//        ArrayList<String> times5 = new ArrayList<>();
//        List<String> time5 = Arrays.asList(t5,t6,t7,t7,t9,t10,t11, t12);
//        times5.addAll(time5);
//
//        ArrayList<String> days5 = new ArrayList<>();
//        List<String> day5 = Arrays.asList(d3,d5);
//        days5.addAll(day5);
//
//        Instructors q5 = new Instructors("Dionnel M. Caguin", R.drawable.dionell, times5, days5);
//        instructorsArrayList.add(q5);
//
//        //add instructor6
//        ArrayList<String> times6 = new ArrayList<>();
//        List<String> time6 = Arrays.asList(t9,t10,t11,t12,t13,t14,t15,t16);
//        times6.addAll(time6);
//
//        ArrayList<String> days6 = new ArrayList<>();
//        List<String> day6 = Arrays.asList(d4);
//        days6.addAll(day6);
//
//        Instructors q6 = new Instructors("Don-don S. Albay", R.drawable.dondon, times6, days6);
//        instructorsArrayList.add(q6);
//
//        //add instructor7
//        ArrayList<String> times7 = new ArrayList<>();
//        List<String> time7 = Arrays.asList(t1,t2,t3,t4);
//        times7.addAll(time7);
//
//        ArrayList<String> days7 = new ArrayList<>();
//        List<String> day7 = Arrays.asList(d4);
//        days7.addAll(day7);
//
//        Instructors q7 = new Instructors("Eherson G. Valdez", R.drawable.eherson, times7, days7);
//        instructorsArrayList.add(q7);
//
//
//        return instructorsArrayList;
//    }
}
