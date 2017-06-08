package towerwarspp.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by dominick on 5/29/17.
 */
public class StringTest {
    public static void main(String[] args) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String line = rd.readLine();
            System.out.println("String: \"" + line + "\" (" + line.isEmpty() + ")");
        }
    }
}
