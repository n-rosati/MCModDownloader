package xyz.rosati.mcmoddownloader;

import java.util.regex.Pattern;

public final class Converter {
    private Converter() {}

    /**
     * Given a CurseForge <b>file</b> URL, convert it to the associated ForgeCDN URL.<br>
     * Example: "https://www.curseforge.com/minecraft/mc-mods/example-mod/files/1234567" becomes "https://media.forgecdn.net/files/1234/567/example-mod.jar"
     * @param originalURL The CurseForge file URL
     * @return The ForgeCDN link to the inputted file.
     */
    public static String toFCDN (String originalURL, String filename) throws Exception{
        //Test if the given file URL is valid
        if (!Pattern.matches("^https://(www\\.)?curseforge\\.com/minecraft/mc-mods/[A-Za-z0-9_-]+/files/[0-9]+", originalURL)) { throw new Exception("URL not valid."); }

        int fileIDStart = originalURL.lastIndexOf("/") + 1;
        return "https://media.forgecdn.net/files/" +
               originalURL.substring(fileIDStart, fileIDStart + 4) +
               '/' +
               originalURL.substring(fileIDStart + 4) +
               '/' +
               filename;
    }
}
