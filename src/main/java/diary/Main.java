package diary;

import java.util.*;

public class Main {

    public static void kirllicaToUnicode() {

        String[] unicode = {"u0410", "u0411", "u0412", "u0413", "u0414", "u0415", "u0416", "u0417", "u0418", "u0419", "u041A", "u041B", "u041C", "u041D", "u041E", "u041F",
                "u0420", "u0421", "u0422", "u0423", "u0424", "u0425", "u0426", "u0427", "u0428", "u0429", "u042A", "u042B", "u042C", "u042D", "u042E", "u042F",
                "u0430", "u0431", "u0432", "u0433", "u0434", "u0435", "u0436", "u0437", "u0438", "u0439", "u043A", "u043B", "u043C", "u043D", "u043E", "u043F",
                "u0440", "u0441", "u0442", "u0443", "u0444", "u0445", "u0446", "u0447", "u0448", "u0449", "u044A", "u044B", "u044C", "u044D", "u044E", "u044F", " "
        };

        char[] alphabet = {'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я',
                'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', ' '};
        StringBuilder stringBuilder = new StringBuilder();

        Scanner scanner = new Scanner(System.in);
        char[] input = scanner.nextLine().toCharArray();


        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < alphabet.length; j++) {
                if (input[i] == alphabet[j]) {
                    if (j != alphabet.length - 1) {
                        stringBuilder.append("\\" + unicode[j]);
                    } else {
                        stringBuilder.append(unicode[j]);
                    }
                    break;
                }
            }
        }

        System.out.println(stringBuilder.toString());
    }

    public static void main(String[] args) {

        kirllicaToUnicode();
//
//        Calendar instance = Calendar.getInstance();
//        instance.setTime(new java.util.Date());
//        instance.add(Calendar.DAY_OF_MONTH, 1);
//        Date newDate = instance.getTime();
//
//        System.out.println(new java.sql.Date(newDate.getTime()));
    }
}

