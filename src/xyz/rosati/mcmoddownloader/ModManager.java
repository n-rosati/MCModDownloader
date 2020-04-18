package xyz.rosati.mcmoddownloader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

class ModManager {
    private final Downloader downloader;
    private String destLocation;

    ModManager() {
        this.downloader = new Downloader();
        setDestLocation(getSavePath());
    }

    /**
     * Gets a save path from the user.
     * @return A path to where the files should be stored.
     */
    private String getSavePath() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the save location: ");
        String path = sc.next();
        if (!Files.isDirectory(Paths.get(path))) {
            //TODO: Make a more elegant solution
            System.out.print("That path was invalid. Do you want to create it? (Answering anything other than (y)es will end the program.): ");

            if (sc.next().equals("y")) {
                try {
                    Files.createDirectory(Paths.get(path));
                } catch (IOException ioException) {
                    System.out.println("Failed to create directory. Exiting with code (6D6B6 46972).");
                    System.exit(1);
                }
            }
        }
        return path;
    }

    /**
     * Downloads a mod from the specified file URL on CurseForge.
     * @param fileURL The CurseForge file URL. Example:<br><code>https://www.curseforge.com/minecraft/mc-mods/example-mod/files/1234567</code>
     * @throws Exception Exception thrown when an error occurs in the download process.
     */
    void get(String fileURL) throws Exception{
        try {
            this.downloader.download(fileURL, getDestLocation());
        } catch (Exception de) {
            throw new Exception("Error downloading.\n" + de);
        }
    }

    /**
     * Sets the download location.
     * @param destLocation Path to the desired download location
     */
    void setDestLocation(String destLocation) {
        this.destLocation = destLocation;
    }

    String getDestLocation() {
        return this.destLocation;
    }
}
