package com.one.exercise;

import com.one.exercise.utils.Tools;
import org.apache.ibatis.io.ResolverUtil;

import java.time.LocalDate;
import java.util.*;

public class T {

    public static void main(String[] args) {
        int[] nums = {3, 2, 3, 4};
        int target = 6;
        int[] resutl = twoSum(nums, target);
        if (resutl != null){
            for (int i : resutl) {
                System.out.println(i);
            }
        }else {
            System.out.println("null");
        }

    }

    public static int[] twoSum(int[] nums, int target) {
        // 元素，索引
        Map<Integer, LinkedList<Integer>> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])){
                LinkedList<Integer> linkedList = map.get(nums[i]);
                linkedList.add(i);
            }else {
                LinkedList<Integer> linkedList = new LinkedList();
                linkedList.add(i);
                map.put(nums[i], linkedList);
            }
            int i1 = target - nums[i];

            if (map.containsKey(i1)){
                Iterator<Integer> iterator = map.get(i1).iterator();
                while (iterator.hasNext()) {
                    Integer next = iterator.next();
                    if (next != i){
                        int[] result = {next, i};
                        return result;
                    }
                }
            }
        }
        return null;
    }

    private static void test04(){
        Set<Long> idSet = new TreeSet<>();
        idSet.add((long)1);
        idSet.add((long)4);
        idSet.add((long)2);
        idSet.add((long)3);
        System.out.println(idSet.toString());
        System.out.println(idSet.toArray());
    }

    private static void test03(){
        LocalDate now = LocalDate.now();
        List<String> dateWeek = new ArrayList<>();

        for (int i = 6; i >= 0; i--){
            dateWeek.add(now.minusDays(i).toString());
        }

        System.out.println(dateWeek);
    }

    private static void test01(){
        // 标准
        String s1 = "qe. asdfaskfsd' sfd";

        // 待比较
        String s2 = "qe.asdfakfsd' sfd";

        String item = s2 + "";
        int matching = 0;
        for(int i = 0; i < s1.length(); i++){
            char c1 = s1.charAt(i);
            for (int j = 0; j < item.length(); j++) {
                char c2 = item.charAt(j);

                if (c1 == c2 || c1 == c2 + 32 || c1 == c2 -32){
                    matching++;
                    item = item.substring(0, j) + item.substring(j+1);
                    break;
                }

            }
        }
        System.out.println(matching);
        System.out.println(s1.length());
        System.out.println( (double)matching / s1.length());

        System.out.println(Tools.similarityString(s1,s2));
    }

    private static  void test02(){
        List<Long> t1 = new ArrayList<>();
        t1.add((long) 1);
        t1.add((long) 2);
        t1.add((long) 3);

        Integer i = 1;
        System.out.println(t1.indexOf((long)i));
    }
}
