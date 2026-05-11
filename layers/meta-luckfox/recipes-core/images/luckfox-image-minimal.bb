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
    package-management \
    ssh-server-dropbear \
"

IMAGE_INSTALL += "\
    bluez5 \
    bluez5-dev \
    kernel-modules \
    packagegroup-base \
    packagegroup-core-boot \
    python3 \
    util-linux \
"

IMAGE_INSTALL += "udev-rules-luckfox"

EXTRA_USERS_PARAMS = "usermod -P luckfox root;"

IMAGE_ROOTFS_SIZE ?= "524288"

do_image_complete[depends] += "virtual/kernel:do_deploy"
