package xyz.rosati.mcmoddownloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Downloader {

    public Downloader() {}

    /**
     * Downloads a mod from CurseForge
     * @param projectFileURL The URL to a version of a mod.<br>Example: https://curseforge.com/minecraft/mc-mods/[mod name]/[files]/[file id]
     * @param destFilePath The path to where the mod file will be stored.
     * @throws Exception Thrown if invalid URL.
     * @throws IOException Thrown if network error when downloading the mod.
     */
    public void download(String projectFileURL, String destFilePath) throws Exception, IOException {
        if (!validateProjectFileURL(projectFileURL)) { throw new Exception("Invalid URL was given."); }

        String filename = getFileName(projectFileURL);
        String modURL = Converter.toFCDN(projectFileURL, filename);

        System.out.println("url: " + modURL);
    }

    /**
     * Ensures the given project file URL is valid
     * @param url A URL that points to a version of a mod.<br>Example: https://curseforge.com/minecraft/mc-mods/[mod name]/[files]/[file id]
     * @return True if valid, false if invalid
     */
    private boolean validateProjectFileURL(String url) {
        return Pattern.matches("^https://(www\\.)?curseforge\\.com/minecraft/mc-mods/[A-Za-z0-9_-]+/files/[0-9]+(/)?", url);
    }

    /**
     * Gets the file name for a given CurseForge project file URL
     * @param projectFileURL A URL pointing to a specific version of a mod
     * @return A string to the file name associated with the given project mod version
     * @throws IOException Thrown if errors occurs when sending GET request
     */
    private String getFileName(String projectFileURL) throws IOException {
        //Put the header information into a HashMap for use when connecting to CurseForge
        HashMap<String, String> header = new HashMap<>();
        header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        header.put("Accept-Language", "en-US,en;q=0.5");
        header.put("Accept-Encoding", "gzip, deflate, br");
        header.put("Pragma", "no-cache");
        header.put("Cache-Control", "no-cache");
        //Get HTML of the mod version's project page
        Document doc = Jsoup.connect(projectFileURL).headers(header).get();
        //Select the class containing the file name and return it
        return doc.select("h3[class=\"text-primary-500 text-lg\"]").first().text();
    }
}