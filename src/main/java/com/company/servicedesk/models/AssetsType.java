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
    BROWSER("browser"),
    NAVIGATOR("navigator"),
    ERP("erp"),
    APPLICATION("application"),
    DRIVER("driver"),
    LICENSE("license"),
    ANTIVIRUS("antivirus"),
    FIREWALL("firewall"),
    DATABASE("database"),
    VIRTUAL_MACHINE("virtual_machine"),
    CONTAINER("container"),
    BACKUP("backup"),
    WIFI("wifi"),
    INTERNET("internet"),
    ACCESS_POINT("access_point"),
    NETWORK_CABLE("network_cable"),
    ROUTER("router"),
    SWITCH("switch"),
    VPN("vpn"),
    DHCP("dhcp"),
    DNS("dns"),
    USER_ACCOUNT("user_account"),
    EMAIL_ACCOUNT("email_account"),
    DOMAIN_ACCOUNT("domain_account"),
    AD_ACCOUNT("ad_account"),
    VPN_ACCOUNT("vpn_account"),
    SYSTEM_ACCOUNT("system_account"),
    APPLICATION_ACCOUNT("application_account"),
    ADMINISTRATIVE_ACCOUNT("administrative_account"),
    SHARED_ACCOUNT("shared_account"),
    GROUP("group"),
    PERMISSION("permission"),
    ROLE("role"),
    MFA("mfa"),
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
