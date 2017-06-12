package towerwarspp;

import towerwarspp.preset.ArgumentParser;
import towerwarspp.preset.ArgumentParserException;

/**
 * Created on 12.06.2017.
 *
 * @author dominick
 */
public class MyArgumentParser extends ArgumentParser {
    public MyArgumentParser(String[] args) throws ArgumentParserException {
        super(args);
    }

    // ------------------------------------------------------------

    public boolean isDebug() throws ArgumentParserException {
        return getFlag("debug");
    }
}
