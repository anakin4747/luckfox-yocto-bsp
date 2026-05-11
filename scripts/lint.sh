#!/bin/sh
set -eu

LAYERS="layers/meta-luckfox-bsp layers/meta-luckfox"

# shellcheck disable=SC2046
oelint-adv \
	--jobs "$(nproc)" \
	--release scarthgap \
	--color \
	--constantmods +oelint-constantmods.json \
	--suppress oelint.var.badimagefeature.allow-root-login \
	--suppress oelint.var.badimagefeature.empty-root-password \
	--suppress oelint.var.bbclassextend \
	-- $(find $LAYERS -name "*.bb" -o -name "*.bbappend" | sort)
