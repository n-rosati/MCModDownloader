package xyz.rosati.mcmoddownloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Downloader {
    private Converter converter;

    public Downloader() {
        this.converter = new Converter();
    }

    public void download(String projectFileURL, String destFilePath) throws Exception {
        String filename;
        if (!validateProjectFileURL(projectFileURL)) { throw new Exception("Invalid URL was given."); }

        try {
            filename = getFileName(projectFileURL);
        } catch (IOException e) {
            throw new Exception("Error downloading site HTML.\n" + e);
        }

        System.out.println("filename: " + filename);
    }

    private boolean validateProjectFileURL(String url) {
        //File URL in the format https://curseforge.com/minecraft/mc-mods/[mod name]/[files]/[file id]
        System.out.println(url);
        return Pattern.matches("^https://(www\\.)?curseforge\\.com/minecraft/mc-mods/[A-Za-z0-9_-]+/files/[0-9]+", url);
    }

    private String getFileName(String projectFileURL) throws IOException {
        //Put the header information into a hashmap for use when connecting to CurseForge
        HashMap<String, String> header = new HashMap<>();
        header.put("Host", "www.curseforge.com");
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:70.0) Gecko/20100101 Firefox/70.0");
        header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        header.put("Accept-Language", "en-US,en;q=0.5");
        header.put("Accept-Encoding", "gzip, deflate, br");
        header.put("Connection", "keep-alive");
        header.put("Upgrade-Insecure-Requests", "1");
        header.put("Cookie-Installing-Permission", "required");
        header.put("Pragma", "no-cache");
        header.put("Cache-Control", "no-cache");
        //Get HTML of the mod version's project page
        Document doc = Jsoup.connect(projectFileURL).headers(header).get();

        return doc.select("h3[class=\"text-primary-500 text-lg\"]").first().text();
    }
}