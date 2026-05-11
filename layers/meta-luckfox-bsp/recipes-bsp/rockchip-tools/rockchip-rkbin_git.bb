# rockchip-rkbin – fetch and install the Rockchip binary blob/firmware tools
# needed to pack a flashable image for RV1106 boards.
#
# The rkbin repository contains the pre-built miniloader (DDR init, trust
# firmware) and the mkimage / rkdeveloptool helpers.

SUMMARY = "Rockchip binary firmware blobs and image packing tools for RV1106"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = ""

inherit deploy

RKBIN_REPO   = "git://github.com/rockchip-linux/rkbin.git"
RKBIN_BRANCH = "master"
SRCREV = "${AUTOREV}"

SRC_URI = "git://${@d.getVar('RKBIN_REPO').replace('git://','')};protocol=https;branch=${RKBIN_BRANCH};destsuffix=rkbin"

S = "${WORKDIR}/rkbin"

PV = "1.0+git${SRCPV}"

COMPATIBLE_MACHINE = "luckfox-pico-ultra"

# RV1106 specific blobs (paths within rkbin tree – update to match release)
RKBIN_DDR   ?= "bin/rv11/rv1106_ddr_924MHz_v1.10.bin"
RKBIN_LOADER ?= "bin/rv11/rv1106_miniloader_v1.10.bin"
RKBIN_BL31  ?= "bin/rv11/rv1106_bl31_v1.10.elf"

do_install[noexec] = "1"

do_deploy() {
    install -d ${DEPLOYDIR}/rkbin
    install -m 0644 ${S}/${RKBIN_DDR}    ${DEPLOYDIR}/rkbin/ddr.bin    || true
    install -m 0644 ${S}/${RKBIN_LOADER} ${DEPLOYDIR}/rkbin/loader.bin || true
    install -m 0644 ${S}/${RKBIN_BL31}   ${DEPLOYDIR}/rkbin/bl31.elf   || true
}

addtask deploy after do_compile before do_build
