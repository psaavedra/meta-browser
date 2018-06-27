require chromium-ozone-wayland-tarball.inc
require chromium-gn.inc

SRC_URI += " \
 file://0001-ozone-wayland-Explicitly-release-capture-of-destroye.patch \
 file://0001-Fix-kiosk-and-start-maximized-modes-in-AGL.patch \
 file://0001-Rotate-gcc-toolchain-s-build-flags.patch \ 
 file://0001-Fix-Assignment-had-no-effect.patch \
 file://0001-GCC-use-Wformat-for-openh264.patch \
 file://0001-Use-SK_ARM_HAS_NEON-guard-in-skia-for-float-instruct.patch;patchdir=third_party/skia \
 file://0001-GCC-fix-size_t-does-not-name-a-type.patch;patchdir=third_party/angle \
 file://0001-third_party-Make-it-possible-to-build-chromium-with-.patch \
 file://0001-ozone-wayland-IME-Handle-backspace-button.patch \
 file://0001-fixup-ozone-wayland-IME-Handle-backspace-button.patch \
"

REQUIRED_DISTRO_FEATURES = "wayland"

DEPENDS += "\
        libxkbcommon \
        virtual/egl \
        wayland \
"

GN_ARGS += "\
        enable_mus=true \
        use_ozone=true \
        use_system_libwayland=true \
        ozone_auto_platforms=false \
        ozone_platform_headless=true \
        ozone_platform_wayland=true \
        ozone_platform_x11=false \
        use_xkbcommon=true \
"

# The chromium binary must always be started with those arguments.
CHROMIUM_EXTRA_ARGS_append = " --enable-features=Mash --ozone-platform=wayland"
