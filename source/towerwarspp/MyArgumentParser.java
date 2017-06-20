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

    public boolean isHelp() throws ArgumentParserException {
        return getFlag("help");
    }

    public boolean isAnalyze() throws ArgumentParserException {
        return getFlag("analyze");
    }

    public String getHost() throws ArgumentParserException {
        return (String) getSetting("host");
    }

    public int getPort() throws ArgumentParserException {
        return Integer.parseInt((String) getSetting("port"));
    }

    public PlayerType getOffer() throws ArgumentParserException {
        return parsePlayerType((String) getSetting("offer"));
    }

    public String getName() throws ArgumentParserException {
        return (String) getSetting("name");
    }

    public boolean isAiDebug() throws ArgumentParserException {
        return getFlag("aidebug");
    }
}
