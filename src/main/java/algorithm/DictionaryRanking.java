package algorithm;

import java.util.ArrayList;
import java.util.List;

public class DictionaryRanking {
    public static void main(String args[]) throws Exception {
        int num = 1000;
        int stage = 1;
        int curr = stage;
        // 1 10 100 11 110
        List<Integer> integers = new ArrayList<>();
        for (int i = 1; i < num; i++) {
            integers.add(curr);
            // 1 10 100...
            if (curr * 10 < num) {
                curr *= 10;
            }
            // ..., 101, 102, 103, 104...
            else if (curr % 10 != 9) {
                curr ++;
            }
            // ..., 109, 11, 110, 111, ...
            else {
                while ((curr / 10) % 10 == 9) {
                    curr /= 10;
                }
                curr = curr / 10 + 1;
            }
        }
        integers.forEach(integer -> System.out.print(integer + ", "));
    }
}
