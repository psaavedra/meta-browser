require chromium-ozone-wayland.inc

SRC_URI[md5sum] = "15bb89b0465406f514ed64ad9c0aa95c"
SRC_URI[sha256sum] = "129bf333af591417ede9d1f5fb34aba923f00b5a87b417c7deb4f9afe83f7195"

SRC_URI += " \
 file://0001-Use-v8-qemu-wrapper.sh-on-v8-context-snapshot-creati.patch \
 file://V8-qemu-wrapper-patch.version-6.5.73.patch;patchdir=v8 \
 file://0001-Rotate-gcc-toolchain-s-build-flags.patch \
 file://0001-Remove-conflicting-wformat-flag.patch \
 file://0001-Fix-non-mac-build-of-chrome_binary.patch \
 file://0001-GCC-build-explicitely-call-this-on-lambda-expression.patch \
 file://0001-Fix-vcvt_f16_f32-is-not-declared-in-this-scope.patch;patchdir=third_party/skia \
 file://0001-Ozone-Wayland-IME-integration.patch \
"
