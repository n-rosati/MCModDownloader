package xyz.rosati.mcmoddownloader;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the destination for the mods: ");
        ModManager modManager = new ModManager(sc.next());

        System.out.println("This tool uses the file page of a CurseForge project.\n" +
                           "Example: https://www.curseforge.com/minecraft/mc-mods/example-mod/files/1234567.\n" +
                           "Enter `stop` to stop downloading mods.");

        String input;
        do {
            input = sc.next();
            if (input.equalsIgnoreCase("stop")) {
                System.out.println("Exiting...");
                System.exit(0);
            }

            try {
                modManager.download(input);
            } catch (Exception e) {
                System.out.println("Error downloading that mod.\n" + e.toString());
            }
        } while (true);
    }
}
