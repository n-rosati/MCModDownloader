package xyz.rosati.mcmoddownloader;

import java.util.regex.Pattern;

public class Converter {
    private static String FORGECDN_BASE = "https://media.forgecdn.com/files/";

    public Converter() {}

    /**
     * Given a CurseForge <b>file</b> URL, convert it to the associated ForgeCDN URL.<br>
     * Example: "https://www.curseforge.com/minecraft/mc-mods/example-mod/files/1234567" becomes "https://media.forgecdn.net/files/1234/567/example-mod.jar"
     * @param originalURL The CurseForge file URL
     * @return The ForgeCDN link to the inputted file.
     */
    public String CFtoFCDN(String originalURL) throws Exception{
        StringBuilder convertedURL = new StringBuilder();
        final String REGEX = "^https:\\/\\/(www\\.)?curseforge\\.com\\/minecraft\\/mc-mods\\/[A-Za-z0-9_-]+\\/files\\/[0-9]+";
        boolean validURL = Pattern.matches(REGEX, originalURL);

        if (!validURL) { throw new Exception("URL not valid."); }




        convertedURL.append(FORGECDN_BASE);

        return convertedURL.toString();
    }
}
