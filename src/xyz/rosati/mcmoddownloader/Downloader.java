package xyz.rosati.mcmoddownloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Downloader {
    private static HashMap<String, String> HEADERS = new HashMap<>();


    public Downloader() {
        //Put the header information into a HashMap for use when connecting to CurseForge
        HEADERS.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        HEADERS.put("Accept-Language", "en-US,en;q=0.5");
        HEADERS.put("Accept-Encoding", "gzip, deflate, br");
        HEADERS.put("Pragma", "no-cache");
        HEADERS.put("Cache-Control", "no-cache");
    }

    /**
     * Downloads a mod from CurseForge
     * @param projectFileURL The URL to a version of a mod.<br>Example: https://curseforge.com/minecraft/mc-mods/[mod name]/[files]/[file id]
     * @param destFilePath The path to where the mod file will be stored.
     * @throws Exception Thrown if invalid URL.
     * @throws IOException Thrown if network error when downloading the mod.
     */
    public void download(String projectFileURL, String destFilePath) throws Exception, IOException {
        if (!validateProjectFileURL(projectFileURL)) { throw new Exception("Invalid URL was given."); }
        if (!destFilePath.endsWith("/")) { destFilePath += ("/"); }

        String filename = getFileName(projectFileURL);
        String modURL = Converter.toFCDN(projectFileURL, filename);

        try (InputStream in = new URL(modURL).openStream()){
            Files.copy(in, Paths.get(destFilePath + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        //Get HTML of the mod version's project page
        Document doc = Jsoup.connect(projectFileURL).headers(HEADERS).get();
        //Select the class containing the file name and return it
        return doc.select("h3[class=\"text-primary-500 text-lg\"]").first().text();
    }
}