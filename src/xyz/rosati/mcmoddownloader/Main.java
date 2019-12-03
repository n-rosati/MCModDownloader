package xyz.rosati.mcmoddownloader;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the destination for the mods: ");
//        ModManager modManager = new ModManager(sc.next());
        ModManager modManager = new ModManager("C:\\Users\\Nicholas\\Desktop");

        System.out.println("This tool takes in URLs from CurseForge in the format `https://www.curseforge.com/minecraft/mc-mods/example-mod/files/1234567`.\nEnter `stop` to stop downloading mods.");

        String input;
        boolean exit = false;
//        do {
//            input = sc.next();
            input = "https://www.curseforge.com/minecraft/mc-mods/jei/files/2803400";
            if (input.equals("stop")) { exit = true; }

            try {
                modManager.download(input);
            } catch (Exception e) {
                System.out.println(e);
            }
//        } while (!exit);
    }
}
