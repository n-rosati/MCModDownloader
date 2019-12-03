package xyz.rosati.mcmoddownloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class Downloader {
    private Converter converter;

    public Downloader() {
        this.converter = new Converter();
    }

    public void download(String projectFileURL, String destFilePath) throws Exception {
        String filename = "";
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
        return Pattern.matches("^https:\\/\\/(www\\.)?curseforge\\.com\\/minecraft\\/mc-mods\\/[A-Za-z0-9_-]+\\/files\\/[0-9]+", url);
    }

    private String getFileName(String projectFileURL) throws IOException {
        URL url = new URL(projectFileURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Host", "www.curseforge.com");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:70.0) Gecko/20100101 Firefox/70.0");
        con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
        con.setRequestProperty("Connection", "keep-alive");
        con.setRequestProperty("Upgrade-Insecure-Requests", "1");
        con.setRequestProperty("Cookie-Installing-Permission", "required");
        con.setRequestProperty("Pragma", "no-cache");
        con.setRequestProperty("Cache-Control", "no-cache");

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while((inputLine = in.readLine()) != null) {
            content.append(new String (inputLine.getBytes(), StandardCharsets.UTF_8));
        }
        in.close();
        con.disconnect();
        System.out.println("Response status: " + status);

        return new String(content.toString().getBytes(), StandardCharsets.UTF_8);
    }
}