package towerwarspp;

import towerwarspp.preset.ArgumentParser;
import towerwarspp.preset.ArgumentParserException;
import towerwarspp.preset.PlayerType;

/**
 * Created on 12.06.2017.
 *
 * @author dominick
 */
public class MyArgumentParser extends ArgumentParser {
    enum NetworkMode {
        OFFER,
        FIND
    };

    public MyArgumentParser(String[] args) throws ArgumentParserException {
        super(args);
    }

    // ------------------------------------------------------------

    public boolean isAnalyze() throws ArgumentParserException {
        return getFlag("analyze");
    }

    public String getHost() throws ArgumentParserException {
        return (String) getSetting("host");
    }

    public PlayerType getOffer() throws ArgumentParserException {
        return parsePlayerType((String) getSetting("offer"));
    }

    public String getName() throws ArgumentParserException {
        return (String) getSetting("name");
    }
}
