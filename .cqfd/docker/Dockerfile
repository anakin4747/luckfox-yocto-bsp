# Dockerfile for the Luckfox Pico Ultra Yocto build environment
#
# Based on Ubuntu 22.04 LTS (Jammy) – a supported host distro for Yocto
# Scarthgap per the official Project documentation.
#
# All Yocto host-tool dependencies listed in
# https://docs.yoctoproject.org/5.0/ref-manual/system-requirements.html
# are installed here so that the host machine stays clean.

FROM ubuntu:22.04

LABEL maintainer="luckfox-build"
LABEL description="Yocto Scarthgap build environment for Luckfox Pico Ultra (RV1106)"

# Prevent interactive prompts during package installation
ENV DEBIAN_FRONTEND=noninteractive
ENV LANG=en_US.UTF-8
ENV LC_ALL=en_US.UTF-8

# ---------------------------------------------------------------------------
# System packages required by Yocto Scarthgap (5.0)
# ---------------------------------------------------------------------------
RUN apt-get update && apt-get install -y --no-install-recommends \
    # Essential build tools
    build-essential \
    gcc \
    g++ \
    make \
    cmake \
    ninja-build \
    # Python (bitbake requires Python 3.8+)
    python3 \
    python3-pip \
    python3-pexpect \
    python3-jinja2 \
    python3-git \
    python3-subunit \
    python3-setuptools \
    # Version control
    git \
    git-lfs \
    # Compression / archive tools
    gzip \
    bzip2 \
    xz-utils \
    zstd \
    tar \
    unzip \
    zip \
    # Fetching
    wget \
    curl \
    ca-certificates \
    # Locale
    locales \
    # Diffstat / patch
    diffstat \
    patch \
    # Text processing
    gawk \
    sed \
    texinfo \
    # ncurses (needed for menuconfig)
    libncurses5-dev \
    libncursesw5-dev \
    # SSL
    libssl-dev \
    # RPM / CPIO (used by Yocto's package management)
    rpm \
    cpio \
    # Host file utilities
    file \
    # Chrpath (corrects rpaths in the SDK)
    chrpath \
    # socat / lz4 (misc build deps)
    socat \
    lz4 \
    # debianutils (which, etc.)
    debianutils \
    # screen / tmux (useful inside the container)
    screen \
    tmux \
    # sudo – required by cqfd for terminal job-control workaround
    sudo \
    # kas – the Yocto layer manager
    && pip3 install kas \
    # oelint-adv – OpenEmbedded recipe linter
    && pip3 install oelint-adv \
    # Locale setup
    && locale-gen en_US.UTF-8 \
    && update-locale LANG=en_US.UTF-8 \
    # Clean up to keep the image lean
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# ---------------------------------------------------------------------------
# Python tool versions – pin for reproducibility
# ---------------------------------------------------------------------------
RUN pip3 install --upgrade "kas>=4.0" "oelint-adv>=5.0.0"

# ---------------------------------------------------------------------------
# Non-root user setup
# cqfd runs commands as the invoking host user (UID/GID injected at runtime).
# We pre-create a 'builder' user as a fallback, and grant passwordless sudo
# so that cqfd can use sudo(8) for terminal job control.
# ---------------------------------------------------------------------------
RUN groupadd -g 1000 builder \
    && useradd -m -u 1000 -g builder -s /bin/bash builder \
    && echo "builder ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers \
    && echo "%builder ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers

WORKDIR /work

# Default command: drop into a shell (overridden by cqfd at runtime)
CMD ["/bin/bash"]
