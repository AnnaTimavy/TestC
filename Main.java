
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

class Main {
    private static String input;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите арифметическое выражение:");
        String input = scanner.nextLine();
        try {
            System.out.println(calc(input));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static String calc(@org.jetbrains.annotations.NotNull String input) throws Exception {
        Main.input = input;
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new Exception("Неправильный формат ввода. Ожидается: [число] [операция] [число]");
        }

        String leftOperand = parts[0];
        String operator = parts[1];
        String rightOperand = parts[2];

        boolean areRoman = isRoman(leftOperand) && isRoman(rightOperand);
        boolean areArabic = isArabic(leftOperand) && isArabic(rightOperand);

        if (!(areRoman || areArabic)) {
            throw new Exception("Оба числа должны быть одного типа (оба арабские или оба римские)");
        }

        int num1, num2;
        if (areRoman) {
            num1 = romanToArabic(leftOperand);
            num2 = romanToArabic(rightOperand);
        } else {
            num1 = Integer.parseInt(leftOperand);
            num2 = Integer.parseInt(rightOperand);
        }

        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new Exception("Числа должны быть в диапазоне от 1 до 10 включительно");
        }

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new Exception("Деление на ноль");
                }
                result = num1 / num2;
                break;
            default:
                throw new Exception("Неправильная операция. Доступны только +, -, *, /");
        }

        if (areRoman) {
            if (result < 1) {
                throw new Exception("Результат римских чисел не может быть меньше единицы");
            }
            return arabicToRoman(result);
        } else {
            return String.valueOf(result);
        }
    }

    private static boolean isRoman(String value) {
        return value.matches("[IVXLCDM]+");
    }

    private static boolean isArabic(String value) {
        return value.matches("\\d+");
    }

    private static int romanToArabic(String roman) throws Exception {
        Map<Character, Integer> romanToArabicMap = new HashMap<>();
        romanToArabicMap.put('I', 1);
        romanToArabicMap.put('V', 5);
        romanToArabicMap.put('X', 10);
        romanToArabicMap.put('L', 50);
        romanToArabicMap.put('C', 100);
        romanToArabicMap.put('D', 500);
        romanToArabicMap.put('M', 1000);

        int result = 0;
        for (int i = 0; i < roman.length(); i++) {
            if (i > 0 && romanToArabicMap.get(roman.charAt(i)) > romanToArabicMap.get(roman.charAt(i - 1))) {
                result += romanToArabicMap.get(roman.charAt(i)) - 2 * romanToArabicMap.get(roman.charAt(i - 1));
            } else {
                result += romanToArabicMap.get(roman.charAt(i));
            }
        }
        return result;
    }

    private static String arabicToRoman(int number) {
        String[] romanNumerals = {
                "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
        };
        int[] arabicNumerals = {
                1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1
        };

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arabicNumerals.length; i++) {
            while (number >= arabicNumerals[i]) {
                number -= arabicNumerals[i];
                sb.append(romanNumerals[i]);
            }
        }
        return sb.toString();
    }
}