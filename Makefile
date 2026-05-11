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
	$(CQFD) exec oelint-adv \
		--jobs $(NPROC) \
		--release scarthgap \
		--color \
		layers/meta-luckfox-bsp/recipes-*/*/*.bb \
		layers/meta-luckfox-bsp/recipes-*/*/*.bbappend \
		layers/meta-luckfox/recipes-*/*/*.bb \
		layers/meta-luckfox/recipes-*/*/*.bbappend

clean:
	$(CQFD) exec kas clean kas/luckfox-pico-ultra.yml
