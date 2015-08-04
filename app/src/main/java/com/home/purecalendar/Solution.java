// you can also use imports, for example:
// import java.util.*;

// you can use System.out.println for debugging purposes, e.g.
// System.out.println("this is a debug message");

class Solution {
    public int solution(int[] A) {
        int v1=A[0];
        int v2=0;

        int count1 = 1;
        int count2 = 0;
        int maxCount = 0;
        for (int i = 1; i < A.length; i++) {
            if (A[i] == v1)
                count1++;
            else if (count2 == 0) {
                v2 = A[i];
                count2++;
            } else if (A[i] == v2) {
                count2++;
            } else { //the third value is found
                maxCount = Math.max(maxCount, count1+count2);
                v1 = A[i-1];
                v2=A[i];
                count1=count2=1;
            }
        }

        return maxCount;
    }
}