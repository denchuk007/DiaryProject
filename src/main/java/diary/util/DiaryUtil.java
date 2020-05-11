package diary.util;

import diary.controller.Pair;
import diary.model.Mark;
import diary.model.User;

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
}
