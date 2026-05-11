.PHONY: all build lint shell clean

CQFD := .cqfd/docker/cqfd

# Default target – full Yocto build inside the cqfd container
all: build

build:
	$(CQFD) init
	$(CQFD)
	$(MAKE) lint

# Lint all local layers with oelint-adv
lint:
	$(CQFD) exec scripts/lint.sh

clean:
	$(CQFD) exec kas clean kas/luckfox-pico-ultra.yml

shell:
	$(CQFD) -b shell
