package towerwarspp.test;

import towerwarspp.preset.ArgumentParser;
import towerwarspp.preset.ArgumentParserException;

public class ArgumentParserTest {
    public static void main(String[] args) {
        try {
            ArgumentParser ap = new ArgumentParser(args);

            System.out.println("size: " + ap.getSize());

            switch (ap.getRed()) {
                case HUMAN:
                    System.out.println("Red chose human player");
                    break;
                case RANDOM_AI:
                    System.out.println("Red chose random player");
                    break;

                // and so on..
            }
        } catch (ArgumentParserException e) {
            // Something went wrong...
            e.printStackTrace();
        }
    }
}
