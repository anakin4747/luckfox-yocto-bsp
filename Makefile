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
	$(CQFD) exec bash -c 'find layers/meta-luckfox-bsp layers/meta-luckfox -name "*.bb" -o -name "*.bbappend" | sort | xargs oelint-adv --jobs $(NPROC) --release scarthgap --color'

clean:
	$(CQFD) exec kas clean kas/luckfox-pico-ultra.yml
