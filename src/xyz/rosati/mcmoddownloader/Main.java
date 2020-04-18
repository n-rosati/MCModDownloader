package xyz.rosati.mcmoddownloader;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Scanner;

public class Main {

    public static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        //Disable logging
        LOGGER.setLevel(Level.OFF);
        LogManager.getLogger("io.github.bonigarcia").setLevel(Level.OFF);
        LogManager.getLogger("org.apache").setLevel(Level.OFF);
        System.setProperty("webdriver.chrome.silentOutput", "true");

        System.out.println("This tool downloads a file pointed to by a CurseForge file URL.\n" +
                                   "Example: https://www.curseforge.com/minecraft/mc-mods/example-mod/files/1234567.\n" +
                                   "Enter `stop` to stop downloading mods.");

        ModManager modManager = new ModManager();

        String input;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("> ");
            input = sc.next();
            if (input.equalsIgnoreCase("stop")) {
                System.out.println("Exiting...");
                System.exit(0);
            }

            try {
                modManager.get(input);
            } catch (Exception e) {
                System.out.println("Error downloading mod.\n" + e.toString());
                continue;
            }

            System.out.println("Done!");
        } while (true);
    }
}
