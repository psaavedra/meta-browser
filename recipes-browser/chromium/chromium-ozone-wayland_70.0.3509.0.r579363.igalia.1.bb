require chromium-ozone-wayland-tarball.inc
require chromium-gn.inc

SRC_URI += " \
  file://v8-qemu-wrapper-tools.patch \
  file://v8-v7.0.85-qemu-wrapper.patch;patchdir=v8 \
  file://aarch64-skia-build-fix.patch \
  file://0001-Rotate-gcc-toolchain-s-build-flags.patch \
  file://0001-Fix-Assignment-had-no-effect.patch \
  file://0001-Add-memory-header-when-unique_ptr-is-used.patch \
  file://0001-Don-t-depend-on-minigbm-when-the-gbm-is-not-used.patch \
  file://0001-Use-explicitly-defined-move-constructor-with-noexcep.patch \
  file://0001-IME-for-Platform-integration-remove-chromeos-deps.patch \
  file://0002-IME-for-Wayland-Ozone-Wayland-IME-integration.patch \
  file://0001-Fix-internal-compiler-error-in-convert_move.patch;patchdir=third_party/skia \
  file://0001-ozone-wayland-Explicitly-release-capture-of-destroye.patch \
  file://0001-Fix-operand-1-must-be-an-integer-register.patch \
  file://0001-Fix-kiosk-and-start-maximized-modes-in-AGL.patch \ 
"


REQUIRED_DISTRO_FEATURES = "wayland"

DEPENDS += "\
        libxkbcommon \
        virtual/egl \
        wayland \
"

GN_ARGS += "\
        use_ozone=true \
        ozone_auto_platforms=false \
        ozone_platform_headless=true \
        ozone_platform_wayland=true \
        ozone_platform_x11=false \
        use_xkbcommon=true \
        use_wayland_gbm=false \
        use_system_libwayland=true \
"

# The chromium binary must always be started with those arguments.
CHROMIUM_EXTRA_ARGS_append = " --in-process-gpu --ozone-platform=wayland"
