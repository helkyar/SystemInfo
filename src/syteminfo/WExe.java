package syteminfo;

public enum WExe {

    USERNAME("whoami"),
    PRODUCT_NAME("reg query \"HKLM\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\" /v ProductName"),
    CPU_NAME("reg query \"HKLM\\HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0\" /v ProcessorNameString"),
    PRODUCT_VS("reg query \"HKLM\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\"
                + "Update\\TargetingInfo\\Installed\\Client.OS.rs2.amd64\" /v Version"),
    SYSTEM_FAMILY("reg query \"HKLM\\HARDWARE\\DESCRIPTION\\System\\BIOS\" /v SystemFamily"),
    SYSTEM_MF("reg query \"HKLM\\HARDWARE\\DESCRIPTION\\System\\BIOS\" /v SystemManufacturer"),
    SYSTEM_MODEL("reg query \"HKLM\\HARDWARE\\DESCRIPTION\\System\\BIOS\" /v SystemProductName"),
    SYSTEM_NAME("reg query \"HKLM\\SYSTEM\\CurrentControlSet\\Control\\ComputerName\\ActiveComputerName\" /v ComputerName"),
    WINDOWS_DI("reg query \"HKLM\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\" /v SystemRoot"),
    CPU_SPEED("reg query \"HKLM\\HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0\" /v ~MHz"),
    GRAPHICS("reg query \"HKLM\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\WinSAT\""),
    CDROM("reg query \"HKLM\\SYSTEM\\CurrentControlSet\\Control\\Class\\{4d36e965-e325-11ce-bfc1-08002be10318}\""),
    CDROM2("reg query \"HKLM\\SYSTEM\\CurrentControlSet\\services\\cdrom\""),
    AUDIO("reg query \"HKCU\\Software\\Microsoft\\Multimedia\\Sound Mapper\""),
    AUDIO2("reg query \"HKCU\\SOFTWARE\\Microsoft\\Speech\\AudioOutput\\TokenEnums\\MMAudioOut\""), //every line is the path
    PERIFERICS("reg query \"HKLM\\System\\MountedDevices\""), //every line after  \DosDevices\C: is a connected one
    IP_CONFIG("ipconfig"),
    CONNEXION("netstat -ano");

    public String path;

    private WExe (String path){
        this.path = path;
    }
}