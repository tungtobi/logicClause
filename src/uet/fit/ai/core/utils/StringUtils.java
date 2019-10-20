package uet.fit.ai.core.utils;

import uet.fit.ai.core.pos.Term;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class StringUtils {
    public static String reverseCase(String text) {
        char[] chars = text.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (Character.isUpperCase(c))
                chars[i] = Character.toLowerCase(c);
            else if (Character.isLowerCase(c))
                chars[i] = Character.toUpperCase(c);
        }

        return new String(chars);
    }

    public static char reverseCase(char c) {
        if (Character.isUpperCase(c)) {
            c = Character.toLowerCase(c);
        }
        else if (Character.isLowerCase(c)) {
            c = Character.toUpperCase(c);
        }
        return c;
    }

    private static String insertBetweenAll(String toReplace, String replacement) {
        int first = 1, last = toReplace.length() - 1;

        if (first > last)
            return toReplace;

        return new StringBuilder()
                .append(toReplace, 0, first)
                .append(toReplace.substring(first, last).replaceAll("", replacement))
                .append(toReplace, last, last+1)
                .toString();
    }

    public static String convertToExpression(String term) {
        term = insertBetweenAll(term, " | ");

        StringBuilder newTerm = new StringBuilder();
        for (char c : term.toCharArray()) {
            if (Character.isAlphabetic(c) && Character.isUpperCase(c))
                newTerm.append('!').append(Character.toLowerCase(c));
            else
                newTerm.append(c);
        }

        return newTerm.toString();
    }

    public static String termsToString(List<Term> terms) {
        StringBuilder str = new StringBuilder();
        int i;
        for (i = 0; i < terms.size() - 1; i++)
            str.append(terms.get(i)).append(" & ");

        str.append(terms.get(i));
        return str.toString();
    }

    public static boolean isAlphabet(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static String formatOperator(String expr) {
        return expr.replaceAll("-", "!")
                .replaceAll("\\.", "&&")
                .replaceAll("\\+", "||");
    }

    public static String sortAlphabet(String str) {
        char tmp[] = str.toCharArray();

        for (int i = 0; i < tmp.length - 1; i++)
            for (int j = i + 1; j < tmp.length; j++)
                if (Character.toLowerCase(tmp[j]) < Character.toLowerCase(tmp[i])) {
                    char c = tmp[i];
                    tmp[i] = tmp[j];
                    tmp[j] = c;
                }

        return new String(tmp);
    }

    public static String removeDuplicate(String str) {
        char[] chars = str.toCharArray();
        Set<Character> charSet = new LinkedHashSet<>();
        for (char c : chars)
            charSet.add(c);

        StringBuilder sb = new StringBuilder();
        for (Character character : charSet) {
            sb.append(character);
        }

        return sb.toString();
    }
}