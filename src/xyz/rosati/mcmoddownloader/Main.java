package xyz.rosati.mcmoddownloader;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("This tool downloads a file pointed to by a CurseForge file URL.\n" +
                                   "Example: https://www.curseforge.com/minecraft/mc-mods/example-mod/files/1234567.\n" +
                                   "Enter `stop` to stop downloading mods.");

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the destination for the mods: ");
        ModManager modManager = new ModManager();

        String input;
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
                System.out.println("Error downloading that mod.\n" + e.toString());
                continue;
            }

            System.out.println("Done!");
        } while (true);
    }
}
