# Luckfox Pico Ultra – Yocto Build

Yocto Kirkstone (4.0 LTS) build for the **Luckfox Pico Ultra** (Rockchip RV1106, ARM Cortex-A7).  
Managed by **[kas](https://github.com/siemens/kas)** for layer/config management and **[cqfd](https://github.com/savoirfairelinux/cqfd)** for containerised builds.

## Directory structure

```
yocto-build/
├── .cqfd/
│   └── docker/
│       └── Dockerfile          # Ubuntu 22.04 build environment image
├── .cqfdrc                     # cqfd project config (build commands, flavors)
├── kas/
│   ├── luckfox-pico-ultra.yml      # KAS main config (layers, local.conf)
│   └── luckfox-pico-ultra.lock.yml # KAS lock file (pinned commits)
└── layers/
    ├── meta-luckfox-bsp/       # BSP layer – kernel, bootloader, rkbin blobs
    └── meta-luckfox/           # Machine/integration layer – machine conf,
                                #   distro, image recipes, bbappends
```

## Prerequisites

| Tool | Minimum version |
|------|----------------|
| Docker | 20.10 |
| cqfd | 5.9 |
| (optional) kas | 4.0 – already installed inside the container |

Install cqfd on the host:
```bash
curl -LO https://github.com/savoirfairelinux/cqfd/releases/download/v5.10.2/cqfd_5.10.2_all.deb
sudo apt install ./cqfd_5.10.2_all.deb
```

## Quick start

```bash
cd yocto-build

# 1. Build the Docker image (first time only, or after Dockerfile changes)
cqfd init

# 2. Full build (uses the pinned KAS lock file)
cqfd

# 3. Open an interactive shell inside the build container
cqfd -b shell
```

## Build flavors

| Flavor | Command | Description |
|--------|---------|-------------|
| *(default)* | `cqfd` | Full kas build with lock file |
| `image-minimal` | `cqfd -b image-minimal` | Build `luckfox-image-minimal` target only |
| `shell` | `cqfd -b shell` | Interactive bash session in container |
| `lock` | `cqfd -b lock` | Regenerate `kas/luckfox-pico-ultra.lock.yml` |
| `kas-menu` | `cqfd -b kas-menu` | Open kas interactive menu |

## Running kas directly (without cqfd)

If you have kas installed on the host or inside a kas container:

```bash
# Build
kas build kas/luckfox-pico-ultra.yml:kas/luckfox-pico-ultra.lock.yml

# Drop into a configured bitbake shell
kas shell kas/luckfox-pico-ultra.yml:kas/luckfox-pico-ultra.lock.yml

# Refresh the lock file
kas lock kas/luckfox-pico-ultra.yml --output kas/luckfox-pico-ultra.lock.yml
```


## Layers

### `meta-luckfox-bsp` (BSP layer, priority 10)

Low-level board support for the Rockchip RV1106:

| Recipe | Purpose |
|--------|---------|
| `linux-luckfox_5.10.bb` | Rockchip BSP kernel from LuckfoxTECH GitHub |
| `u-boot-luckfox_%.bbappend` | Board-specific U-Boot defconfig |
| `rockchip-rkbin_git.bb` | Pre-built Rockchip miniloader/BL31 blobs |

### `meta-luckfox` (Machine/integration layer, priority 11)

| File/Recipe | Purpose |
|-------------|---------|
| `conf/machine/luckfox-pico-ultra.conf` | Machine definition (Cortex-A7, kernel, serial) |
| `conf/distro/luckfox.conf` | Distro config (musl, systemd, connman) |
| `recipes-core/images/luckfox-image-minimal.bb` | Production rootfs image |
| `recipes-connectivity/bluez5/bluez5_%.bbappend` | BlueZ wordexp flag fix |
| `recipes-core/busybox/busybox_%.bbappend` | Enable telnetd |

## Deploy outputs

After a successful build, artefacts are in:

```
build/tmp/deploy/images/luckfox-pico-ultra/
├── luckfox-image-minimal-luckfox-pico-ultra.ext4   # rootfs
├── luckfox-image-minimal-luckfox-pico-ultra.tar.bz2
├── zImage                                           # kernel
├── rv1106g-luckfox-pico-ultra.dtb                  # device tree
└── modules-luckfox-pico-ultra.tgz                  # kernel modules
```

Use Rockchip's `rkdeveloptool` or the vendor flash scripts to write these
to the board.

## Lock file management

The `kas/luckfox-pico-ultra.lock.yml` pins the exact git commit SHAs for
all fetched layers.  To update to the latest upstream commits:

```bash
cqfd -b lock
# or: kas lock kas/luckfox-pico-ultra.yml --output kas/luckfox-pico-ultra.lock.yml
git add kas/luckfox-pico-ultra.lock.yml
git commit -m "kas: bump Kirkstone layer commits"
```

## Sstate / download cache

Shared caches are placed **outside** the build directory to survive `kas clean`:

```
../downloads/     # DL_DIR  – fetched source tarballs
../sstate-cache/  # SSTATE_DIR – shared-state cache
```

Mount these on fast local storage for best performance.
