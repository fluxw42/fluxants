package info.fluxprojects.fluxant.core;

import java.util.StringTokenizer;

public class MyScanner extends StringTokenizer {

    public MyScanner(String line) {
        super(line);
    }

    public boolean hasNext() {
        return (hasMoreTokens());
    }

    public boolean hasNextInt() {
        return (hasMoreTokens());
    }

    public String next() {
        return (nextToken());
    }

    public int nextInt() {
        return (new Integer(next()));
    }
}