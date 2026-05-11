SUMMARY = "Minimal production image for Luckfox Pico Ultra (RV1106)"
DESCRIPTION = "Minimal production rootfs for the Luckfox Pico Ultra (Rockchip RV1106). \
               Provides a BusyBox userland, Python 3 with hardware-interface bindings, BlueZ 5 \
               Bluetooth stack, OpenSSH, Samba, D-Bus, and supporting utilities."
HOMEPAGE = "https://github.com/anakin4747/luckfox-yocto-bsp"
BUGTRACKER = "https://github.com/anakin4747/luckfox-yocto-bsp/issues"
SECTION = "images"
CVE_PRODUCT = "luckfox:luckfox-image-minimal"
LICENSE = "MIT"

inherit core-image

IMAGE_FEATURES += "\
    package-management \
    ssh-server-openssh \
"

IMAGE_INSTALL += "\
    \
    packagegroup-core-boot \
    packagegroup-base \
    \
    busybox \
    \
    python3 \
    python3-pip \
    python3-aiohttp \
    python3-click \
    python3-jinja2 \
    python3-pillow \
    python3-pyserial \
    python3-spidev \
    python3-smbus2 \
    python3-periphery \
    \
    bluez5 \
    bluez5-dev \
    \
    openssh \
    openssh-sftp-server \
    \
    samba \
    samba-base \
    \
    dbus \
    dbus-glib \
    \
    htop \
    nano \
    \
    e2fsprogs \
    e2fsprogs-resize2fs \
    util-linux \
    \
    kernel-modules \
"

IMAGE_INSTALL += "udev-rules-luckfox"

EXTRA_USERS_PARAMS = "usermod -P luckfox root;"

IMAGE_ROOTFS_SIZE ?= "524288"

do_image_complete[depends] += "virtual/kernel:do_deploy"
