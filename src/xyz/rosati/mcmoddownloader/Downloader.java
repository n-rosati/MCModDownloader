package xyz.rosati.mcmoddownloader;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Pattern;

class Downloader {
    Downloader() {}

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
     */
    //TODO: Try and make this process more transparent to the user.
    private String getFileName(String projectFileURL) {
        //Set up a Chrome window and turn off logging
        WebDriverManager.chromiumdriver().setup();
        WebDriver driver = new ChromeDriver();

        //Open Chrome and get the file name on the page
        driver.get(projectFileURL);
        Document doc = Jsoup.parse(driver.getPageSource());
        driver.close();
        driver.quit();

        return doc.getElementsByClass("text-primary-500 text-lg").first().text();
    }
}