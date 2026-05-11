.PHONY: all build lint shell clean

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
	$(CQFD) exec bash -c 'if grep -rn "nooelint" layers/meta-luckfox-bsp layers/meta-luckfox; then echo "ERROR: nooelint suppressions are forbidden"; exit 1; fi'
	$(CQFD) exec bash -c 'oelint-adv --jobs $(NPROC) --release scarthgap --color --constantmods +oelint-constantmods.json --suppress oelint.var.bbclassextend -- $$(find layers/meta-luckfox-bsp layers/meta-luckfox -name "*.bb" -o -name "*.bbappend" | sort)'

clean:
	$(CQFD) exec kas clean kas/luckfox-pico-ultra.yml

shell:
	$(CQFD) -b shell
