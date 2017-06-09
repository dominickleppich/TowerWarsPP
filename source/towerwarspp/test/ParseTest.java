package towerwarspp.test;

import towerwarspp.preset.Move;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created on 27.05.2017.
 *
 * @author dominick
 */
public class ParseTest {
    public static void main(String[] args) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));

        String line;

        while (true) {
            line = rd.readLine();
            if (line.equals("exit"))
                System.exit(0);
            Move m = Move.parseMove(line);
            System.out.println("Parsed Move: " + m);
        }
    }
}
