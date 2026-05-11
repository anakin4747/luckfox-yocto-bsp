# U-Boot bbappend for Luckfox Pico Ultra
#
# Rockchip RV1106 boards typically use a Rockchip miniloader + U-Boot SPL
# combination rather than vanilla U-Boot.  This bbappend provides the
# board-specific defconfig and any patches needed on top of the mainline
# u-boot recipe (if used).
#
# If you switch to Rockchip's fork of U-Boot, replace the SRC_URI and
# SRCREV in a dedicated u-boot-rockchip_%.bb recipe instead.

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

COMPATIBLE_MACHINE = "luckfox-pico-ultra"

# Rockchip RV1106 U-Boot defconfig (adjust to match the vendor defconfig name)
UBOOT_MACHINE:luckfox-pico-ultra = "luckfox_pico_ultra_defconfig"

# Deploy the U-Boot image so it can be packed into the Rockchip firmware image
UBOOT_SUFFIX ?= "bin"
