package com.company.servicedesk.models;

import lombok.Getter;

@Getter
public enum AssetsType {
    DESKTOP("desktop"),
    NOTEBOOK("notebook"),
    MONITOR("monitor"),
    PRINTER("printer"),
    SCANNER("scanner"),
    SERVER("server"),
    NO_BREAK("no_break"),
    PERIPHERALS("peripherals"),
    OPERATIONAL_SYSTEM("operational_system"),
    NAVIGATOR("navigator"),
    ANTIVIRUS("antivirus"),
    FIREWALL("firewall"),
    DATABASE("database"),
    VIRTUAL_MACHINE("virtual_machine"),
    CONTAINER("container"),
    BACKUP("backup"),
    WIFI("wifi"),
    ROUTER("router"),
    SWITCH("switch"),
    VPN("vpn"),
    DHCP("dhcp"),
    DNS("dns"),
    WIRES("wires"),
    NO_ACCESS("no_access"),
    MIGRATIONS("migrations"),
    CORRUPTED("corrupted"),
    LOST_DATA("lost_data");

    private final String assetType;

    AssetsType(String assetType) {
        this.assetType = assetType;
    }
}
