SUMMARY = "Minimal development image for Luckfox Pico Ultra (RV1106)"
DESCRIPTION = "Minimal development rootfs for the Luckfox Pico Ultra (Rockchip RV1106). \
               Provides a BusyBox userland, Python 3, BlueZ 5 Bluetooth stack, \
               Dropbear SSH, and kernel modules."
HOMEPAGE = "https://github.com/anakin4747/luckfox-yocto-bsp"
BUGTRACKER = "https://github.com/anakin4747/luckfox-yocto-bsp/issues"
SECTION = "images"
CVE_PRODUCT = "luckfox:luckfox-image-minimal"
LICENSE = "MIT"

inherit core-image

IMAGE_FEATURES += "\
    allow-root-login \
    empty-root-password \
    package-management \
    ssh-server-dropbear \
"

IMAGE_INSTALL += "\
    bluez5 \
    kernel-modules \
    python3 \
    util-linux \
"
