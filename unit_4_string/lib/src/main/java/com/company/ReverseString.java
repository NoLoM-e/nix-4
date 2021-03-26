package com.company;


public class ReverseString {

    public static String reverse(String src){
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < src.length(); i++) {
            StringBuilder word = new StringBuilder();

            while (i != src.length() && src.charAt(i) != ' '){
                word.append(src.charAt(i));
                i++;
            }
            for (int j = word.length() - 1; j >= 0; j--) {
                result.append(word.charAt(j));
            }
            result.append(" ");
        }


        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    public static String reverse(String src, String dest){
        return src.replaceAll(dest, ReverseString.reverse(dest));
    }

    public static String reverse(String src, int firstIndex, int lastIndex){
        String sub = src.substring(firstIndex, lastIndex + 1);
        return src.replace(sub, ReverseString.reverse(sub));
    }


}