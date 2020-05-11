package diary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String str = "11A";
        String classroomDigit = String.join("", str.split("\\D"));
        String classroomWord = String.join("", str.split("\\d"));
        System.out.println(classroomDigit + "\n" + classroomWord);
        System.out.println("dasd");
    }
}

