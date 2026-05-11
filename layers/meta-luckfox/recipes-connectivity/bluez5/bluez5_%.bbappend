# bluez5 bbappend – port the Luckfox BlueZ 5 uClibc/musl compatibility fixes
#
# The original Buildroot patches work around missing <wordexp.h> in uClibc by
# substituting an Android compatibility header.  On Yocto with musl, wordexp
# is present (musl provides it) so the include substitution is NOT needed.
#
# This bbappend therefore only applies the flag-correction fix
# (WRDE_APPEND → WRDE_NOCMD) which is a genuine behavioural fix regardless
# of the C library.

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:luckfox-pico-ultra = " \
    file://0001-shell-fix-wordexp-flags.patch \
"

COMPATIBLE_MACHINE = "luckfox-pico-ultra"
