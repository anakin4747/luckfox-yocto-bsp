SUMMARY = "Linux kernel for Luckfox Pico Ultra (Rockchip RV1106)"
DESCRIPTION = "Rockchip-patched Linux 5.10 kernel for the Luckfox Pico Ultra \
               (RV1106 SoC, ARM Cortex-A7). Fetched from the LuckfoxTECH monorepo \
               and built with the board-specific defconfig and a Yocto cfg fragment."
HOMEPAGE = "https://github.com/LuckfoxTECH/luckfox-pico"
BUGTRACKER = "https://github.com/LuckfoxTECH/luckfox-pico/issues"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
CVE_PRODUCT = "linux_kernel"

PV = "5.10+git${SRCPV}"

KBRANCH ?= "main"

SRC_URI = "\
    git://github.com/LuckfoxTECH/luckfox-pico.git;protocol=https;branch=${KBRANCH};destsuffix=git \
    file://luckfox-pico-ultra.cfg \
"

SRCREV = "824b817f889c2cbff1d48fcdb18ab494a68f69d1"

S = "${WORKDIR}/git/sysdrv/source/kernel"

inherit kernel

COMPATIBLE_MACHINE:luckfox-pico-ultra = "luckfox-pico-ultra"
BBCLASSEXTEND = "machine"

KBUILD_DEFCONFIG ?= "luckfox_rv1106_linux_defconfig"

KERNEL_FEATURES:append = " cfg/fs/ext4.scc"

do_deploy:append() {
    install -d ${DEPLOYDIR}
}
