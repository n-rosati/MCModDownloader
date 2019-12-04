package xyz.rosati.mcmoddownloader;

import java.util.regex.Pattern;

public final class Converter {
    private static String FORGECDN_BASE = "https://media.forgecdn.net/files/";
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

        StringBuilder convertedURL = new StringBuilder(FORGECDN_BASE);

        //TODO: Make this cleaner. It works but it feels hacky
        char[] fileID = {0,0,0,0,0,0,0,0};
        originalURL.getChars(originalURL.lastIndexOf('/') + 1, originalURL.length(), fileID, 0);
        for (int i = 0; i < 7; ++i) {
            if (i == 4) convertedURL.append('/');
            convertedURL.append(fileID[i]);
        }

        convertedURL.append('/').append(filename);
        return convertedURL.toString();
    }
}
