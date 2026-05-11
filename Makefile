.PHONY: all build lint clean

CQFD := .cqfd/docker/cqfd

# Default target – full Yocto build inside the cqfd container
all: build

build:
	$(CQFD) init
	$(CQFD)
	$(MAKE) lint

NPROC := $(shell nproc)

# Lint all local layers with oelint-adv
lint:
	$(CQFD) exec bash -c 'oelint-adv --jobs $(NPROC) --release scarthgap --color --constantmods +KBUILD_DEFCONFIG -- $$(find layers/meta-luckfox-bsp layers/meta-luckfox -name "*.bb" -o -name "*.bbappend" | sort)'

clean:
	$(CQFD) exec kas clean kas/luckfox-pico-ultra.yml
