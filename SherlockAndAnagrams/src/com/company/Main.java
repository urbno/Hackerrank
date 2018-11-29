package com.company;

import java.util.*;

public class Main {

    /**
     * what kind of characters are there in it
     * and how much per type of it
     * pl: aabbbc --> a-2, b-3, c-1
     */
    static Map<String, Integer> textRangeCounter = new HashMap<>();
    /**
     * this contains the text all possible part variations
     * abba --> a,b,b,a, ab,bb,ba, abb,bba
     */
    static List<String> textWindows = new ArrayList<>();
    static HashMap<Character, Integer> mapS1 = new HashMap<>();
    static HashMap<Character, Integer> mapS2 = new HashMap<>();

    static void initializeMap(List<String> list, Map<String, Integer> initialize) {
        for (int i = 0; i < list.size(); i++) {
            if (!initialize.containsKey(list.get(i))) {
                initialize.put(list.get(i), 1);
            } else {
                int oldV = initialize.get(list.get(i));
                initialize.replace(list.get(i), oldV + 1);
            }
        }
    }

    static String getRangeFromString(String text, int from, int to) {
        String part = "";
        if (from == to) {
            return text.charAt(from) + "";
        } else {
            for (int i = from; i < to; i++) {
                part += text.charAt(i) + "";
            }
        }
        return part;
    }

    static List<String> getWordsByActualRange(String text, int range) {
        List<String> actualList = new ArrayList<>();
        int start = 0;
        int end = range;

        for (int i = 0; i < text.length() - range + 1; i++) {
            actualList.add(getRangeFromString(text, start, end));
            start++;
            end++;
        }

        return actualList;
    }

    static void putStringToCharMap(String text, HashMap<Character, Integer> map) {
        for (int i = 0; i < text.length(); i++) {
            Character actualChar = text.charAt(i);
            if (!map.containsKey(actualChar)) {
                map.put(actualChar, 1);
            } else {
                int oldV = map.get(actualChar);
                map.replace(actualChar, oldV + 1);
            }
        }
    }

    static boolean compareStringWithMap(String s1, String s2) {


        Thread thread1 = new Thread() {
            public void run() {
                putStringToCharMap(s1, mapS1);
            }
        };

        Thread thread2 = new Thread() {
            public void run() {
                putStringToCharMap(s2, mapS2);
            }
        };

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (mapS1.equals(mapS2)) {
            mapS1.clear();
            mapS2.clear();
            return true;
        } else {
            mapS1.clear();
            mapS2.clear();
            return false;
        }
    }

    static int sherlockAndAnagrams(String s) {
        int totalAnagrams = 0;
        for (int i = 1; i < s.length(); i++) {
            textWindows.addAll(getWordsByActualRange(s, i));
        }
        initializeMap(textWindows, textRangeCounter);

        for (int i = 0; i < textWindows.size() - 1; i++) {
            String slow = textWindows.get(i);
            for (int j = i + 1; j < textWindows.size(); j++) {
                String fast = textWindows.get(j);
                if (slow.length() == fast.length()) {
                    if (compareStringWithMap(slow, fast)) {
                        totalAnagrams++;
                    }
                }
            }
        }
        textWindows.clear();
        textRangeCounter.clear();
        return totalAnagrams;
    }


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int result = 0;
        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        long startTime = System.currentTimeMillis();
        for (int qItr = 0; qItr < q; qItr++) {
            String s = scanner.nextLine();

            result = sherlockAndAnagrams(s);
            long finishTime = System.currentTimeMillis();
            String runTime = String.valueOf(finishTime - startTime);

            System.out.println("\n" + "result = " + result + " - time: " + runTime);

        }

        scanner.close();

    }
}
