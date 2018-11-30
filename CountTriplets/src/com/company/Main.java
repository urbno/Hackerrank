package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Main {

    static class TripletElement{
        public long value;
        public long count;

        public TripletElement(long value, long count){
            this.value = value;
            this.count = count;
        }

        public TripletElement(){
            this.value = 0;
            this.count = 0;
        }
    }

    static Map<Long, Integer> ordered = new TreeMap<>();

    static void initializeMap(List<Long> list, Map<Long, Integer> initialize) {
        for (int i = 0; i < list.size(); i++) {
            if (!initialize.containsKey(list.get(i))) {
                initialize.put(list.get(i), 1);
            } else {
                int oldV = initialize.get(list.get(i));
                initialize.replace(list.get(i), oldV + 1);
            }
        }
    }

    // Complete the countTriplets function below.
    static long countTriplets(List<Long> list, long r) {
        long totalTriplets = 0;
        List<List<Long>> foundedTriplets = new ArrayList<>();

        /**
         * https://www.devglan.com/corejava/sorting-hashmap-by-key-and-value
         */
        initializeMap(list, ordered);
        List<TripletElement> triples = new ArrayList<>();

        for(Map.Entry<Long,Integer> actual: ordered.entrySet()){
            triples.add(new TripletElement(actual.getKey(), actual.getValue()));
        }
        TripletElement te_1;
        TripletElement te_2;
        TripletElement te_3;
        for(int i = 0; i < triples.size()-2; i++){
            te_1 = triples.get(i);
            for(int j = i+1; j < triples.size()-1; j++){
                te_2 = triples.get(j);
                for(int k = j+1; k < triples.size(); k++){
                    te_3 = triples.get(k);
                    if(te_1.value * r == te_2.value){
                        if(te_2.value * r == te_3.value){
                            totalTriplets += te_1.count
                                            *te_2.count
                                            *te_3.count;
                        }
                    }
                }
            }
        }
        ordered.clear();
        return totalTriplets;
    }


    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] nr = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(nr[0]);

        long r = Long.parseLong(nr[1]);

        List<Long> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Long::parseLong)
                .collect(toList());

        long starTime = System.currentTimeMillis();
        long ans = countTriplets(arr, r);
        long endTime = System.currentTimeMillis();
        String deltaTime = String.valueOf(endTime-starTime);
        System.out.println("answer = " + ans + " - time: " + deltaTime);
    }
}
