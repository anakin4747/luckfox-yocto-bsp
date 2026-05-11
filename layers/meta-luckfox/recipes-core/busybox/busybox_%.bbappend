# busybox bbappend – enable telnetd for Luckfox Pico Ultra
#
# Mirrors the Buildroot BusyBox config change (patch 3) which enables:
#   - CONFIG_TELNETD (standalone telnet daemon on port 23)
#   - FEATURE_TELNETD_STANDALONE
#   - FEATURE_TELNETD_INETD_WAIT

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:luckfox-pico-ultra = " \
    file://telnetd.cfg \
"

COMPATIBLE_MACHINE = "luckfox-pico-ultra"
