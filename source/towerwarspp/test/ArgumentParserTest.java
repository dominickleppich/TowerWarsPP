package towerwarspp.test;

import towerwarspp.preset.ArgumentParser;
import towerwarspp.preset.ArgumentParserException;

/**
 * Created on 09.06.2017.
 *
 * @author dominick
 */
public class ArgumentParserTest {
    public static void main(String[] args) throws ArgumentParserException {
        ArgumentParser ap = new ArgumentParser(args);

        System.out.println("local: " + ap.isLocal());
        System.out.println("network: " + ap.isNetwork());
        System.out.println("size: " + ap.getSize());
    }
}
