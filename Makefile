.PHONY: all build lint shell clean

CQFD := .cqfd/docker/cqfd

build:
	$(CQFD) init
	$(CQFD)
	$(MAKE) lint

lint:
	$(CQFD) exec scripts/lint.sh

clean:
	$(CQFD) exec kas clean kas/luckfox-pico-ultra.yml

shell:
	$(CQFD) run kas shell
