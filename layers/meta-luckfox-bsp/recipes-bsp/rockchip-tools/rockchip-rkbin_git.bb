SUMMARY = "Rockchip binary firmware blobs and image packing tools for RV1106"
DESCRIPTION = "Pre-built Rockchip binary blobs (DDR init, miniloader, BL31) and \
               image packing tools required to produce a flashable firmware image \
               for RV1106-based boards such as the Luckfox Pico Ultra."
HOMEPAGE = "https://github.com/rockchip-linux/rkbin"
BUGTRACKER = "https://github.com/rockchip-linux/rkbin/issues"
SECTION = "bsp"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = ""
CVE_PRODUCT = "rockchip:rkbin"

PV = "1.0+git${SRCPV}"

RKBIN_BRANCH ?= "master"

SRC_URI = "git://github.com/rockchip-linux/rkbin.git;protocol=https;branch=${RKBIN_BRANCH};destsuffix=rkbin"

SRCREV = "74213af1e952c4683d2e35952507133b61394862"

S = "${WORKDIR}/rkbin"

inherit deploy

COMPATIBLE_MACHINE = "luckfox-pico-ultra"

RKBIN_DDR ?= "bin/rv11/rv1106_ddr_924MHz_v1.10.bin"
RKBIN_LOADER ?= "bin/rv11/rv1106_miniloader_v1.10.bin"
RKBIN_BL31 ?= "bin/rv11/rv1106_bl31_v1.10.elf"

do_install[noexec] = "1"

do_deploy() {
    install -d ${DEPLOYDIR}/rkbin
    install -m 0644 ${S}/${RKBIN_DDR}    ${DEPLOYDIR}/rkbin/ddr.bin
    install -m 0644 ${S}/${RKBIN_LOADER} ${DEPLOYDIR}/rkbin/loader.bin
    install -m 0644 ${S}/${RKBIN_BL31}   ${DEPLOYDIR}/rkbin/bl31.elf
}

addtask deploy after do_compile before do_build
