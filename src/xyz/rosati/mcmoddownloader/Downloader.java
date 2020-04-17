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

class Downloader {
    private static final HashMap<String, String> HEADERS = new HashMap<>();

    Downloader() {
        //Put the header information into a HashMap for use when connecting to CurseForge
        HEADERS.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        HEADERS.put("Accept-Language", "en-US,en;q=0.5");
        HEADERS.put("Accept-Encoding", "gzip, deflate, br");
        HEADERS.put("Pragma", "no-cache");
        HEADERS.put("Cache-Control", "no-cache");
    }

    /**
     * Downloads a mod from CurseForge
     * @param projectFileURL The URL to a version of a mod. Example:<br><code>https://curseforge.com/minecraft/mc-mods/[mod name]/[files]/[file id]</code>
     * @param destFilePath The path to where the mod file will be stored.
     * @throws Exception If invalid URL.
     * @throws IOException If network error when downloading the mod.
     */
    void download(String projectFileURL, String destFilePath) throws Exception, IOException {
        if (!validateProjectFileURL(projectFileURL)) { throw new Exception("Invalid URL given."); }

        String filename = getFileName(projectFileURL);
        InputStream in = new URL(Converter.toFCDN(projectFileURL, filename)).openStream();
        Files.copy(in, Paths.get(destFilePath, filename));
    }

    /**
     * Ensures the given project file URL is valid
     * @param url A URL that points to a version of a mod. Example:<br><code>https://curseforge.com/minecraft/mc-mods/[mod name]/[files]/[file id]</code>
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
        //Get HTML of the mod version's project page and return the part with the file name
        return Jsoup.connect(projectFileURL).headers(HEADERS).get().select("h3[class=\"text-primary-500 text-lg\"]").first().text();
    }
}