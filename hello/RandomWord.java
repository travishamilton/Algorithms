/* *****************************************************************************
 *  Name:              Travis Hamilton
 *  Last modified:     January 16th, 2023
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = "";
        int i = 1;
        while (!StdIn.isEmpty()) {
            String token = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / (i++))) {
                champion = token;
            }
        }
        StdOut.println(champion);
    }
}
