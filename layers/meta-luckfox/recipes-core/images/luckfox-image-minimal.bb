# luckfox-image-minimal – base production image for Luckfox Pico Ultra
#
# Functional equivalent of the Buildroot-generated rootfs:
#   - BusyBox userland
#   - Python 3 + periphery/serial/spi/smbus bindings
#   - Bluetooth (BlueZ 5)
#   - OpenSSH server
#   - Samba (smbd)
#   - htop, nano
#   - D-Bus
#   - telnetd (via BusyBox)

SUMMARY     = "Minimal production image for Luckfox Pico Ultra (RV1106)"
DESCRIPTION = "Minimal production rootfs for the Luckfox Pico Ultra (Rockchip RV1106). \
Provides a BusyBox userland, Python 3 with hardware-interface bindings, BlueZ 5 \
Bluetooth stack, OpenSSH, Samba, D-Bus, telnetd, and supporting utilities."
HOMEPAGE    = "https://github.com/anakin4747/luckfox-yocto-bsp"
SECTION     = "images"
LICENSE     = "MIT"

inherit core-image

# Use BusyBox for the core userland (matches Buildroot default)
IMAGE_FEATURES +="\
    package-management \
    ssh-server-openssh \
"

IMAGE_INSTALL +="\
    \
    packagegroup-core-boot \
    packagegroup-base \
    \
    busybox \
    busybox-telnetd \
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

# Include udev rules so that BT/WiFi devices appear automatically
IMAGE_INSTALL +=" udev-rules-luckfox"

# /etc/hostname is set by the machine conf via hostname_pn-base-files
# Set root password hash (openssl passwd -6 luckfox)
EXTRA_USERS_PARAMS = "usermod -P luckfox root;"

# 512 MiB in KiB
IMAGE_ROOTFS_SIZE ?= "524288"

# Make sure kernel + dtb are deployed alongside the rootfs
do_image_complete[depends] += "virtual/kernel:do_deploy"
