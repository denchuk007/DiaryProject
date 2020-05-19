package diary.util;

import diary.model.Mark;
import diary.model.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

public class DiaryUtil {

    public static Pair<String[], Mark[][]> getPupilMarks(User currentUser, User foundByIdUser, int month, int year, Map<String, List<Mark>> table,
                                                  int lengthOfMonth, int pupilNumber, Long pupilId) {
        if (pupilNumber != 0) {
            Iterator<User> pupilIterator = currentUser.getPupils().iterator();
            for (int i = 0; i < pupilNumber - 1; i++) {
                pupilIterator.next();
            }
            currentUser = pupilIterator.next();
        } else if (pupilId != 0) {
            currentUser = foundByIdUser;
        }

        List<Mark> marks = new ArrayList<>();

        for (Mark mark : currentUser.getMarks()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mark.getDate());
            int m = calendar.get(Calendar.MONTH) + 1;
            int y = calendar.get(Calendar.YEAR);
            if (m == month && y == year) {
                marks.add(mark);
            }
        }

        for (Mark mark : marks) {
            String subjectTitle = mark.getSubject().getTitle();
            List<Mark> tableList;
            if (table.get(subjectTitle) != null) {
                tableList = table.get(subjectTitle);
            } else {
                tableList = new ArrayList<>();
            }
            tableList.add(mark);
            table.put(subjectTitle, tableList);
        }

        String[] subjectsTitle = new String[table.size()];
        Mark[][] marksTable = new Mark[table.size()][lengthOfMonth];
        Iterator<String> iterator = table.keySet().iterator();
        for (int i = 0; i < table.size(); i++) {
            subjectsTitle[i] = iterator.next();
            for (int j = 1; j <= lengthOfMonth; j++) {
                Calendar calendar = Calendar.getInstance();
                if (!table.get(subjectsTitle[i]).isEmpty()) {
                    for (Mark mark : table.get(subjectsTitle[i])) {
                        calendar.setTime(mark.getDate());

                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        if (day == j) {
                            marksTable[i][j] = mark;
                            table.get(subjectsTitle[i]).remove(mark);
                            break;
                        }
                    }
                }
            }
        }
        return new Pair(subjectsTitle, marksTable);
    }

    public static User[] getPupils(User currentUser) {
        User[] pupils = new User[currentUser.getPupils().size()];
        Iterator<User> pupilIterator = currentUser.getPupils().iterator();
        for (int i = 0; i < pupils.length; i++) {
            pupils[i] = pupilIterator.next();
        }
        return pupils;
    }


    public static String[] getDaysOfWeek(int year, int month, int lengthOfMonth) {
        String[] daysOfWeek = new String[lengthOfMonth];
        for (int i = 0; i < lengthOfMonth; i++) {
            int dayOfWeek = LocalDate.of(year, month, i + 1).getDayOfWeek().getValue();
            switch (dayOfWeek) {
                case 1 : daysOfWeek[i] = "Пн"; break;
                case 2 : daysOfWeek[i] = "Вт"; break;
                case 3 : daysOfWeek[i] = "Ср"; break;
                case 4 : daysOfWeek[i] = "Чт"; break;
                case 5 : daysOfWeek[i] = "Пт"; break;
                case 6 : daysOfWeek[i] = "Сб"; break;
                case 7 : daysOfWeek[i] = "Вс"; break;
            }
        }

        return daysOfWeek;
    }


    public static Pair<String[], String[][]> getAnalyze(User currentUser, int year) {
        Calendar calendar = Calendar.getInstance();
        Map<String, List<Mark>> table = new HashMap<>();

        //оценки за год по предметам
        for (Mark mark : currentUser.getMarks()) {
            calendar.setTime(mark.getDate());
            int y = calendar.get(Calendar.YEAR);
            if (y == year) {
                String subjectTitle = mark.getSubject().getTitle();
                List<Mark> tableList;
                if (table.get(subjectTitle) != null) {
                    tableList = table.get(subjectTitle);
                } else {
                    tableList = new ArrayList<>();
                }
                tableList.add(mark);
                table.put(subjectTitle, tableList);
            }
        }

        String[] subjectsTitle = new String[table.size()];
        Iterator<String> iterator = table.keySet().iterator();
        String[][] resultTable = new String[table.size()][12];

        for (int i = 0; i < table.size(); i++) {
            subjectsTitle[i] = iterator.next();

            for (int j = 1; j <= 12; j++) {
                double sum = 0;
                int marksCounter = 0;
                if (!table.get(subjectsTitle[i]).isEmpty()) {
                    for (Mark mark : table.get(subjectsTitle[i])) {
                        if (mark.getValue() != 0) {
                            calendar.setTime(mark.getDate());
                            int m = calendar.get(Calendar.MONTH) + 1;

                            if (m == j) {
                                sum += mark.getValue();
                                marksCounter++;
                            }
                        }
                    }
                }

                if (sum == 0) {
                    resultTable[i][j - 1] = "-";
                } else {
                    BigDecimal bigDecimal = new BigDecimal(sum / marksCounter);
                    bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
                    resultTable[i][j - 1] = String.valueOf(bigDecimal.doubleValue());
                }
            }
        }

        return new Pair(subjectsTitle, resultTable);
    }


    public static List<String> getBirthdays(User currentUser, List<User> allUsers) {

        List<String> birthdayList = new ArrayList<>();
        for (User user : allUsers) {
            if (!user.getUsername().equals(currentUser.getUsername()) && !user.getRoles().iterator().next().getName().equals("ROLE_ADMIN") && !user.getRoles().iterator().next().getName().equals("ROLE_PARENT")) {
                Calendar instance = Calendar.getInstance();
                instance.setTime(new java.util.Date());
                instance.add(Calendar.DAY_OF_MONTH, 1);
                java.util.Date newDate = instance.getTime();

                if (user.getBirthday().toString().equals(new java.sql.Date(newDate.getTime()).toString())) {
                    if (user.getRoles().iterator().next().getName().equals("ROLE_PUPIL")) {
                        birthdayList.add("Завтра ученик " + user.getName() + " " + user.getSurname() + " (" + user.getClassroom().getDigit()
                                + user.getClassroom().getWord() + " класс) празднует день рождения!");
                    } else if (user.getRoles().iterator().next().getName().equals("ROLE_PUPIL")) {
                        birthdayList.add("Завтра учитель " + user.getName() + " " + user.getSurname() + " (" + user.getClassroom().getDigit()
                                + user.getClassroom().getWord()+ " класс) празднует день рождения!");
                    }
                } else if (user.getBirthday().toString().equals(new java.sql.Date(new java.util.Date().getTime()).toString())) {
                    if (user.getRoles().iterator().next().getName().equals("ROLE_PUPIL")) {
                        birthdayList.add("Сегодня ученик " + user.getName() + " " + user.getSurname() + " (" + user.getClassroom().getDigit()
                                + user.getClassroom().getWord() + " класс) празднует день рождения!");
                    } else if (user.getRoles().iterator().next().getName().equals("ROLE_PUPIL")) {
                        birthdayList.add("Сегодня учитель " + user.getName() + " " + user.getSurname() + " (" + user.getClassroom().getDigit()
                                + user.getClassroom().getWord() + " класс) празднует день рождения!");
                    }
                }
            }
        }

        return birthdayList;
    }


    public static String[][] getTotalAnalyze(String[][] currentUserResultTable, String[] currentUserSubjectsTitle, List<User> allPupils, User currentUser, int year) {
        List<String[][]> table = new ArrayList<>();
        List<String[]> subjectsTitle = new ArrayList<>();

        for (User pupil : allPupils) {
            if (pupil.getClassroom().getDigit().equals(currentUser.getClassroom().getDigit())
                    && !pupil.getRoles().iterator().next().getName().equals("ROLE_TEACHER")) {
                subjectsTitle.add(DiaryUtil.getAnalyze(pupil, year).first);

                if (subjectsTitle.get(subjectsTitle.size() - 1).length != currentUserSubjectsTitle.length) {
                    continue;
                }

                int errorCounter = 0;
                for (int i = 0; i < currentUserSubjectsTitle.length; i++) {
                    if (!currentUserSubjectsTitle[i].equals(subjectsTitle.get(subjectsTitle.size() - 1)[i])) {
                        errorCounter++;
                    }
                }

                if (errorCounter == 0) {
                    table.add(DiaryUtil.getAnalyze(pupil, year).second);
                }
            }
        }

        String[][] resultTable = new String[currentUserResultTable.length][12];

        for (int i = 0; i < currentUserResultTable.length; i++) {
            for (int j = 0; j < 12; j++) {
                double sum = 0;
                int markCounter = 0;
                for (int k = 0; k < table.size(); k++) {
                    if (!table.get(k)[i][j].equals("-")) {
                        sum += Double.parseDouble(table.get(k)[i][j]);
                        markCounter++;
                    }
                }

                if (sum == 0) {
                    resultTable[i][j] = "-";
                } else {
                    resultTable[i][j] = String.valueOf(sum / markCounter);
                }
            }
        }

        return resultTable;
    }
}
