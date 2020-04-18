package xyz.rosati.mcmoddownloader;

public final class Converter {
    private Converter() {}

    /**
     * Given a CurseForge file URL, convert it to the associated ForgeCDN URL.<br>
     * Example: <code>https://www.curseforge.com/minecraft/mc-mods/example-mod/files/1234567</code> becomes <code>https://media.forgecdn.net/files/1234/567/example-mod.jar</code>
     * @param originalURL The CurseForge file URL
     * @return The ForgeCDN link to the inputted file.
     */
    public static String toFCDN (String originalURL, String filename) throws Exception{
        int fileIDStart = originalURL.lastIndexOf("/") + 1;
        return "https://media.forgecdn.net/files/" +
               originalURL.substring(fileIDStart, fileIDStart + 4) +
               '/' +
               originalURL.substring(fileIDStart + 4, fileIDStart + 7) +
               '/' +
               filename;
    }
}
