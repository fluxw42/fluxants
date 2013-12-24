import info.fluxprojects.fluxant.diffusion.DiffusionBot;

import java.io.*;

/**
 * Starter bot implementation.
 */
public class MyBot extends DiffusionBot {

    public static void main(String[] args) throws IOException {
        System.setErr(new PrintStream(new FileOutputStream("c:\\fluxant.txt")));
        new MyBot().readSystemInput();
    }

}
