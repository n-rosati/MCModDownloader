package xyz.rosati.mcmoddownloader;

public class ModManager {
    private Downloader downloader;
    private String destLocation;

    public ModManager(String destination) {
        this.downloader = new Downloader();
        this.destLocation = destination;
    }

    /**
     * Downloads a mod from the specified file URL on CurseForge.
     * @param projectFileURL The CurseForge file URL. Example: https://www.curseforge.com/minecraft/mc-mods/example-mod/files/1234567
     * @throws Exception Exception thrown when an error occurs in the download process.
     */
    public void download (String projectFileURL) throws Exception{
        try {
            this.downloader.download(projectFileURL, this.destLocation);
        } catch (Exception de) {
            throw new Exception("Error downloading from ForgeCDN.\n" + de);
        }
    }
}
