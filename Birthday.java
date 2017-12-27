package practice;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
 
public class Birthday {
 
    int n = 0;
    int k = 0;
    int m = 0;
    int clas = 0;
    int count = 0;
    String ab;
    String ac;
    int i = 0;
 
    LinkedList student_list = new LinkedList();
    LinkedList queried_student = new LinkedList();
 
    public Birthday() throws IOException {
        Scanner scan = new Scanner(new File("birthday.txt"));
 
        String x = null;
        scan.nextLine();
 
        while (scan.hasNextLine()) {
            student_list.removeAll(student_list);
 
            if (scan.hasNextLine()) {
                ab = scan.nextLine();
            }
            try {
                k = Integer.parseInt(ab);
            } catch (NumberFormatException e) {
 
            }
            i = 0;
 
            while (k > 0 && scan.hasNextLine()) {
 
                String words[] = scan.nextLine().split(" ");
                Class_Student st = new Class_Student(words[0], words[1], words[2], words[3]);
 
                student_list.add(st);
                count++;
                k--;
            }
 
            if (scan.hasNextLine()) {
                ac = scan.nextLine();
            }
            try {
                m = Integer.parseInt(ac);
            } catch (NumberFormatException e) {
            }
            i = 0;
            while (m > 0 && scan.hasNextLine()) {
                String words[] = scan.nextLine().split(" ");
                Queried_Student st = new Queried_Student(words[0], words[1]);
 
                queried_student.add(st);
                m--;
            }
        clas++;
        System.out.println("\nClass # " + clas + "\n");
 
            new Sorting().closestDate(new Sorting().sortDay(new Sorting().selectionSortMonth(student_list)), queried_student);
             
        }
 
        scan.close();
 
    }
 
    public static void main(String[] args) throws IOException {
 
         new Birthday();
 
    }
}
class Class_Student {
    String firtName;
    String secondName;
    String month;
    String day;
    public Class_Student(String firstName,String secondName,String month,String day){
        this.firtName=firstName;
        this.secondName=secondName;
        this.month=month;
        this.day=day;
    }
    public String getFirstName(){
        return firtName;
    }
    public String getSecondName(){
        return secondName;
    }
    public String getMonth(){
        return month;
    }
    public String getDay(){
        return day;
    }
 
     
}
class Queried_Student {
    String firstName;
    String secondName;
    public Queried_Student(String firstName,String secondName){
        this.firstName=firstName;
        this.secondName=secondName;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getSecondName(){
        return secondName;
    } 
    public Queried_Student(){
         
    }
     
}
 
 
class Sorting {
 
    int month = 0;
    int day = 0;
    Queried_Student st = null;
    int clas = 0;
 
    public Sorting() {
 
    }
    //Arrange the month in ascending order
 
    public LinkedList selectionSortMonth(LinkedList st_list) {
        Class_Student student = null;
        Class_Student student1 = null;
        Month m = new Month();
        for (int i = 0; i < st_list.size() - 1; i++) {
            int index = i;
            for (int j = i + 1; j < st_list.size(); j++) {
                student = (Class_Student) st_list.get(j);
                student1 = (Class_Student) st_list.get(index);
                if (m.monthNo(student.getMonth()) < m.monthNo(student1.getMonth())) {
 
                    index = j; //searching for lowest index  
                }
            }
            Object a = st_list.set(index, st_list.get(i));
            st_list.set(i, a);
 
        }
 
        return st_list;
    }
 
    //Arrange the list by dy in ascending
    public LinkedList sortDay(LinkedList st_list) {
        Month m = new Month();
        Class_Student student = null;
        Class_Student student1 = null;
        for (int i = 0; i < st_list.size() - 1; i++) {
            int index = i;
            for (int j = i + 1; j < st_list.size(); j++) {
                student = (Class_Student) st_list.get(j);
                student1 = (Class_Student) st_list.get(index);
                if (m.monthNo(student.getMonth()) == m.monthNo(student1.getMonth())) {
                    if (Integer.parseInt(student.getDay()) < Integer.parseInt(student1.getDay())) {
                        index = j;
                    }
 
                }
 
            }
            Object a = st_list.set(index, st_list.get(i));
            st_list.set(i, a);
        }
 
        return st_list;
    }
//Find the closest date to the queried student
 
    public void closestDate(LinkedList st_list, LinkedList q_st) {
        Class_Student after = null;
        Class_Student before = null;
        Class_Student student = null;
        Queried_Student student1 = null;
        
        Month m = new Month();
        int februaryMonth = m.isFebruar29(st_list);
        for (int i = 0; i < q_st.size(); i++) {
            student1 = (Queried_Student) q_st.get(i);
 
            for (int j = 0; j < st_list.size(); j++) {
                student = (Class_Student) st_list.get(j);
 
                if ((student.getFirstName().equals(student1.getFirstName())) && (student.getSecondName().equals(student1.getSecondName()))) {
 
                    if (j == 0) {
                        before = (Class_Student) st_list.get(st_list.size() - 1);
                        after = (Class_Student) st_list.get(j + 1);
                        firstStudent(student, before, after, februaryMonth);
                    } else if (j == st_list.size() - 1) {
                        after = (Class_Student) st_list.get(0);
                        before = (Class_Student) st_list.get(j - 1);
 
                        lastStudent(student, before, after, februaryMonth);
                    } else {
                        before = (Class_Student) st_list.get(j - 1);
                        after = (Class_Student) st_list.get(j + 1);
                        if (after.getMonth().equals(before.getMonth()) && student.getMonth().equals(after.getMonth())) {
                            equalMonth(student, before, after);
                        } else {
                            differentMonth(student, before, after, februaryMonth);
                        }
 
                    }
                }
 
            }
        }
    }
//when student is first in the list
 
    public void firstStudent(Class_Student student, Class_Student before, Class_Student after, int februaryMonth) {
        Month m = new Month();
 
        int before_days = 0 + februaryMonth;
        int after_days = 0 + februaryMonth;
        int st_days = 0 + februaryMonth;
 
        for (int d = 1; d <= 12; d++) {
            st_days = st_days + m.monthDays("" + d);
            after_days = after_days + m.monthDays("" + d);
        }
        for (int d = 1; d < m.monthNo(student.getMonth()); d++) {
            st_days = st_days + m.monthDays("" + d);
        }
        st_days = st_days + Integer.parseInt(student.getDay());
 
        for (int d = 1; d < m.monthNo(before.getMonth()); d++) {
            before_days = before_days + m.monthDays("" + d);
        }
        before_days = before_days + Integer.parseInt(before.getDay());
 
        for (int d = 1; d <= 12; d++) {
            after_days = after_days + m.monthDays("" + d);
        }
        after_days = after_days + Integer.parseInt(after.getDay());
 
        int a = st_days - before_days;
        int b = after_days - st_days;
 
        if (a < b) {
            System.out.println(before.getFirstName() + " " + before.getSecondName() + " birthday is closest to " + student.getFirstName() + " " + student.getSecondName());
 
        } else {
            System.out.println(after.getFirstName() + " " + after.getSecondName() + " birthday is closest to " + student.getFirstName() + " " + student.getSecondName());
 
        }
 
    }
 
    //When all three student have same month of birthday
    public void equalMonth(Class_Student student, Class_Student before, Class_Student after) {
        int a = Integer.parseInt(student.getDay()) - Integer.parseInt(before.getDay());
        int b = Integer.parseInt(student.getDay()) - Integer.parseInt(before.getDay());
        if (a < b) {
            System.out.println(before.getFirstName() + " " + before.getSecondName() + " birthday is closest to " + student.getFirstName() + " " + student.getSecondName());
 
        } else {
            System.out.println(after.getFirstName() + " " + after.getSecondName() + " birthday is closest to " + student.getFirstName() + " " + student.getSecondName());
 
        }
 
    }
    //when students have different moth of birthday
 
    public void differentMonth(Class_Student student, Class_Student before, Class_Student after, int februaryMonth) {
        int st_days = 0 + februaryMonth;
        int before_days = 0 + februaryMonth;
        int after_days = 0 + februaryMonth;
        Month m = new Month();
 
        for (int d = 1; d < m.monthNo(student.getMonth()); d++) {
            st_days = st_days + m.monthDays("" + d);
 
        }
        st_days = st_days + Integer.parseInt(student.getDay());
 
        for (int d = 1; d < m.monthNo(before.getMonth()); d++) {
            before_days = +before_days + m.monthDays("" + d);
 
        }
        before_days = Integer.parseInt(before.getDay());
 
        for (int d = 1; d < m.monthNo(after.getMonth()); d++) {
            after_days = after_days + m.monthDays("" + d);
 
        }
        after_days = +Integer.parseInt(after.getDay());
        int a = st_days - before_days;
        int b = after_days - st_days;
        if (a < b) {
            System.out.println(before.getFirstName() + " " + before.getSecondName() + " birthday is closest to " + student.getFirstName() + " " + student.getSecondName());
 
        } else {
            System.out.println(after.getFirstName() + " " + after.getSecondName() + " birthday is closest to " + student.getFirstName() + " " + student.getSecondName());
 
        }
 
    }
    //when student is last in the list
 
    public void lastStudent(Class_Student student, Class_Student before, Class_Student after, int februaryMonth) {
        int before_days = 0 + februaryMonth;
        int after_days = 0 + februaryMonth;
        int st_days = 0 + februaryMonth;
        Month m = new Month();
        for (int d = 1; d <= 12; d++) {
            after_days = after_days + m.monthDays("" + d);
        }
 
        for (int d = 1; d < m.monthNo(student.getMonth()); d++) {
            st_days = st_days + m.monthDays("" + d);
        }
        st_days = st_days + Integer.parseInt(student.getDay());
 
        for (int d = 1; d < m.monthNo(before.getMonth()); d++) {
            before_days = before_days + m.monthDays("" + d);
        }
        before_days = before_days + Integer.parseInt(before.getDay());
 
        for (int d = 1; d < m.monthNo(after.getMonth()); d++) {
            after_days = after_days + m.monthDays("" + d);
        }
        after_days = after_days + Integer.parseInt(after.getDay());
 
        int a = st_days - before_days;
        int b = after_days - st_days;
        if (a < b) {
            System.out.println(before.getFirstName() + " " + before.getSecondName() + " birthday is closest to " + student.getFirstName() + " " + student.getSecondName());
 
        } else {
            System.out.println(after.getFirstName() + " " + after.getSecondName() + " birthday is closest to " + student.getFirstName() + " " + student.getSecondName());
 
        }
 
    }
 
}
 
class Month {
 
    public Month() {
 
    }
 
    public int monthNo(String month) {
        switch (month) {
            case "JANUARY":
                return 1;
            case "FEBRUARY":
                return 2;
            case "MARCH":
                return 3;
            case "APRIL":
                return 4;
            case "MAY":
                return 5;
            case "JUNE":
                return 6;
            case "JULY":
                return 7;
            case "AUGUST":
                return 8;
            case "SEPTEMBER":
                return 9;
            case "OCTOBER":
                return 10;
            case "NOVEMBER":
                return 11;
            default:
                return 12;
        }
 
    }
 
    public int monthDays(String month) {
        switch (month) {
            case "1":
                return 31;
            case "2":
                return 28;
            case "3":
                return 31;
            case "4":
                return 30;
            case "5":
                return 31;
            case "6":
                return 30;
            case "7":
                return 31;
            case "8":
                return 31;
            case "9":
                return 30;
            case "10":
                return 31;
            case "11":
                return 30;
            default:
                return 31;
        }
 
    }
 
    public int isFebruar29(LinkedList st) {
        Class_Student student = null;
        int x = 0;
        for (int i = 0; i < st.size(); i++) {
            student = (Class_Student) st.get(i);
            if (student.getMonth().equals("FEBRUARY") && student.getDay().equals("29")) {
                x = 1;
                break;
            }
 
        }
        return x;
    }
}
 
 
 
 
/**
 *
 * 
 */
class Test {
 
    /**
     * the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Birthday student=new Birthday();
       // System.out.println(student.student_list.toString());
    }
     
}
