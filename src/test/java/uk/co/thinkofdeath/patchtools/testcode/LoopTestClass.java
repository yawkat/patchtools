package uk.co.thinkofdeath.patchtools.testcode;

public class LoopTestClass {

    public static void testMethod() {
        LoopTestClass test = new LoopTestClass();
        int[] hello = new int[5];
    }

    public static int testTwo(int val) {
        if (val == 0) {
            val++;
            val *= 5;
            return val;
        } else if (val > 5) {
            return val / 5;
        }
        return val;
    }

    public static int testThree(int val) {
        for (int i = 0; i < val; i++) {
            val *= 2;
        }
        return val;
    }
}
