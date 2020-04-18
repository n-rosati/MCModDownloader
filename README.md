# MCModDownloader

## Usage
1) Enter the location of where you want to store the mod files. This path needs to be the FQPN.
2) Start entering links to the specific version of a mod. For example `https://www.curseforge.com/minecraft/mc-mods/example-mod/files/1234567`
3) Repeat step 2 as many times as needed.
4) Enter `process` to download the entered mods, and enter `quit` to then end the program.

## Notes
The only way that I can get this project to work is by using Selenium. 
This is because of CloudFlare protections on CurseForge's site.

When a mod download is started, a Google Chrome window will briefly pop up, navigate to the URL you inputted, and then will close.
This is just Selenium doing its work, nothing to worry about here.

## License
This project is licensed under the GNU GPLv3 license.
