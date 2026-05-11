# Linux kernel recipe for Luckfox Pico Ultra (Rockchip RV1106)
#
# The Luckfox SDK ships a Rockchip-patched 5.10 kernel. The upstream source
# is maintained by LuckfoxTECH at:
#   https://github.com/LuckfoxTECH/luckfox-pico  (sysdrv/source/kernel/)
#
# The recipe fetches the kernel from that repository at a pinned commit.
# Update SRCREV when you want to pull a newer snapshot.

SUMMARY     = "Linux kernel for Luckfox Pico Ultra (Rockchip RV1106)"
DESCRIPTION = "Rockchip-patched Linux 5.10 kernel for the Luckfox Pico Ultra \
(RV1106 SoC, ARM Cortex-A7). Fetched from the LuckfoxTECH monorepo and built \
with the board-specific defconfig and a Yocto cfg fragment."
HOMEPAGE    = "https://github.com/LuckfoxTECH/luckfox-pico"
BUGTRACKER  = "https://github.com/LuckfoxTECH/luckfox-pico/issues"
SECTION     = "kernel"
LICENSE     = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

PV = "5.10+git${SRCPV}"

KERNEL_REPO  ?= "git://github.com/LuckfoxTECH/luckfox-pico.git"
KERNEL_PROTO ?= "https"
KBRANCH      ?= "main"

SRC_URI = "\
    git://${@d.getVar('KERNEL_REPO').replace('git://','')};protocol=${KERNEL_PROTO};branch=${KBRANCH};destsuffix=git \
    file://luckfox-pico-ultra.cfg \
"

# Pin the commit SHA for reproducible builds.
# Update together with the KAS lock file whenever you pull new kernel code.
SRCREV = "${AUTOREV}"

# The kernel lives inside a subdirectory of the monorepo.
S = "${WORKDIR}/git/sysdrv/source/kernel"

inherit kernel

COMPATIBLE_MACHINE:luckfox-pico-ultra = "luckfox-pico-ultra"

# Rockchip BSP kernels ship their own defconfig.
# The machine's KBUILD_DEFCONFIG overrides this if set.
KBUILD_DEFCONFIG ?= "luckfox_rv1106_linux_defconfig"

# Kernel features / fragments
KERNEL_FEATURES:append = " cfg/fs/ext4.scc"

do_deploy:append() {
    install -d ${DEPLOYDIR}
}
