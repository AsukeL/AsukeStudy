
import java.util.Scanner;

public class Main {
    public static int convertRomanToArabic(String romanNum) {
        int arabicNum = 0;
        int prev = 0;
        for (int i = romanNum.length() - 1; i >= 0; i--) {
            int current = RomanNumeral.valueOf(String.valueOf(romanNum.charAt(i))).getValue();
            if (current < prev) {
                arabicNum -= current;
            } else {
                arabicNum += current;
            }
            prev = current;
        }
        return arabicNum;
    }

    public static String convertArabicToRoman(int arabicNum) {
        if (arabicNum < 1 || arabicNum > 3999) {
            throw new IllegalArgumentException("Number out of range.");
        }

        String[] romanTens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] romanOnes = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        return romanTens[(arabicNum % 100) / 10] +
                romanOnes[arabicNum % 10];
    }

    public static String calculate(String input) {
        String[] tokens = input.split(" ");
        int[] operands = new int[2];
        boolean isRoman = false;
        try {
            operands[0] = Integer.parseInt(tokens[0]);
            operands[1] = Integer.parseInt(tokens[2]);
        } catch (NumberFormatException e) {
            int romanOperand1 = convertRomanToArabic(tokens[0]);
            int romanOperand2 = convertRomanToArabic(tokens[2]);
            operands[0] = romanOperand1;
            operands[1] = romanOperand2;
            isRoman = true;
        }
        if (operands[0] < 1 || operands[0] > 10 || operands[1] < 1 || operands[1] > 10) {
            throw new IllegalArgumentException("Operands must be between 1 and 10.");
        }
        int result = switch (tokens[1]) {
            case "+" -> operands[0] + operands[1];
            case "-" -> operands[0] - operands[1];
            case "*" -> operands[0] * operands[1];
            case "/" -> operands[0] / operands[1];
            default -> throw new IllegalArgumentException("Invalid operator.");
        };
        if (isRoman) {
            if (result < 1) {
                throw new IllegalArgumentException("Roman numerals cannot represent zero or negative numbers.");
            }
            return convertArabicToRoman(result);
        } else {
            return String.valueOf(result);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.equals("exit")) {
            System.out.println("Enter an expression (or \"exit\" to quit):");
            input = scanner.nextLine();
            if (!input.equals("exit")) {
                String result = calculate(input);
                System.out.println(result);
            }
        }
        scanner.close();
    }

}