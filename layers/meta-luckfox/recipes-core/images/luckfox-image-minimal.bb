SUMMARY = "Minimal production image for Luckfox Pico Ultra (RV1106)"
DESCRIPTION = "Minimal production rootfs for the Luckfox Pico Ultra (Rockchip RV1106). \
               Provides a BusyBox userland, Python 3 with hardware-interface bindings, BlueZ 5 \
               Bluetooth stack, Dropbear SSH, Samba, D-Bus, and supporting utilities."
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
    bluez5-dev \
    kernel-modules \
    python3 \
    util-linux \
"
