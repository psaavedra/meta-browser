include chromium.inc

DESCRIPTION = "Chromium browser"
DEPENDS += "gn-native"

SRC_URI = "\
        http://gsdview.appspot.com/chromium-browser-official/chromium-${PV}.tar.xz \
        file://google-chrome \
        ${@bb.utils.contains('PACKAGECONFIG', 'disable-api-keys-info-bar', 'file://api-keys.patch;patchdir=${WORKDIR}', '', d)} \
        file://google-chrome.desktop \
"

S = "${WORKDIR}/chromium-${PV}"
BPN = "chromium"


# PACKAGECONFIG options
# ^^^^^^^^^^^^^^^^^^^^^
# * use-egl: (on by default)
#       Without this packageconfig, the Chromium build will use GLX for
#       creating an OpenGL context in X11, and regular OpenGL for painting
#       operations. Neither are desirable on embedded platforms. With this
#       packageconfig, EGL and OpenGL ES 2.x are used instead.
#
# * disable-api-keys-info-bar: (off by default)
#       This disables the info bar that warns: "Google API keys are missing".
#       With some builds, missing API keys are considered OK, so the bar needs
#       to go. Conversely, if Chromium is compiled with this option off and
#       the user wishes to disable the warning, the following lines can be
#       added to the "google-chrome" script (see patchset) before the
#       chromium binary is called:
#           export GOOGLE_API_KEY="no"
#           export GOOGLE_DEFAULT_CLIENT_ID="no"
#           export GOOGLE_DEFAULT_CLIENT_SECRET="no"
#
# * component-build: (off by default)
#       Enables component build mode. By default, all of Chromium (with the
#       exception of FFmpeg) is linked into one big binary. The linker step
#       requires at least 8 GB RAM. Component mode was created to facilitate
#       development and testing, since with it, there is not one big binary;
#       instead, each component is linked to a separate shared object. Use
#       component mode for development, testing, and in case the build machine
#       is not a 64-bit one, or has less than 8 GB RAM.
#
# * ignore-lost-context: (off by default)
#       There is a flaw in the HTML Canvas specification. When the canvas'
#       backing store is some kind of hardware resource like an OpenGL
#       texture, this resource might get lost. In case of OpenGL textures,
#       this happens when the OpenGL context gets lost. The canvas should then
#       be repainted, but nothing in the Canvas standard reflects that. This
#       packageconfig is to be used if the underlying OpenGL (ES) drivers do
#       not lose the context, or if losing the context is considered okay
#       (note that canvas contents can vanish then).
#
# * impl-side-painting: (off by default)
#       This is a new painting mechanism. Still in
#       development stages, it can improve performance See
#       http://www.chromium.org/developers/design-documents/impl-side-painting
#       for more.
#
# * kiosk-mode: (off by default)
#       Enable this option if you want your browser to start up full-screen,
#       without any menu bars, without any clutter, and without any initial
#       start-up indicators.
#
# * proprietary-codecs: (off by default)
#       Enable this option if you want to add support for additional proprietary
#       codecs, most notably MPEG standards (h.264, h.265, MP4, MP3, AAC, MPEG-2 ..)
#
# * content-shell: (off by default)
#       Enable building and installing content_shell

PACKAGECONFIG ??= " use-egl \
        ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', '', d)} \
        ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', '', d)} \
        "

# this makes sure the dependencies for the EGL mode are present; otherwise, the configure scripts
# automatically and silently fall back to GLX
PACKAGECONFIG[use-egl] = ",,virtual/egl virtual/libgles2"

# Empty PACKAGECONFIG options listed here to avoid warnings.
# The .bb file should use these to conditionally add patches
# and command-line switches (extra dependencies should not
# be necessary but are OK to add).
PACKAGECONFIG[disable-api-keys-info-bar] = ""
PACKAGECONFIG[component-build] = ""
PACKAGECONFIG[ignore-lost-context] = ""
PACKAGECONFIG[impl-side-painting] = ""
PACKAGECONFIG[kiosk-mode] = ""
PACKAGECONFIG[proprietary-codecs] = ""
PACKAGECONFIG[wayland] = ",,wayland virtual/mesa"
PACKAGECONFIG[x11] = ",,virtual/libx11"
PACKAGECONFIG[content-shell] = ""


CHROMIUM_EXTRA_ARGS ?= " \
        ${@bb.utils.contains('PACKAGECONFIG', 'use-egl', '--use-gl=egl', '', d)} \
        ${@bb.utils.contains('PACKAGECONFIG', 'ignore-lost-context', '--gpu-no-context-lost', '', d)} \
        ${@bb.utils.contains('PACKAGECONFIG', 'impl-side-painting', '--enable-gpu-rasterization --enable-impl-side-painting', '', d)} \
        ${@bb.utils.contains('PACKAGECONFIG', 'kiosk-mode', '--start-fullscreen --kiosk --no-first-run', '', d)} \
"

COMPATIBLE_MACHINE = "(.*)"

CPUTARGET_arm = "arm"
CPUTARGET_x86 = "x86"
CPUTARGET_x86-64 = "x64"
CPUTARGET_aarch64 = "arm64"
CPUTARGET_mipsel = "mipsel"
CPUTARGET ?= "unknown_cpu_target"

# Fixme: fix build with v8_use_snapshot=true <http://v8project.blogspot.com.es/2015/09/custom-startup-snapshots.html>

CFLAGS = "${TARGET_CFLAGS}"
CFLAGS := "${@'${CFLAGS}'.replace('-fpie', '-fPIE')}"

CXXFLAGS = "${TARGET_CXXFLAGS}"
CXXFLAGS := "${@'${CFLAGS}'.replace('-fPIE', '-fPIE')}"

EXTRA_OEGN = " \
        gold_path=\"\" \
        enable_nacl=false \
        is_clang=false \
        fatal_linker_warnings=false  \
        v8_use_external_startup_data=false \
        linux_use_bundled_binutils=false \
	use_gold = false \
        ${@bb.utils.contains('PACKAGECONFIG', 'component-build', 'is_component_build=true', 'is_component_build=false', d)} \
        ${@bb.utils.contains('PACKAGECONFIG', 'proprietary-codecs', 'proprietary_codecs=true ffmpeg_branding=\\\"Chrome\\\"', 'proprietary_codecs=false', d)} \
        use_ozone=true \
        use_jumbo_build = true \
        ozone_auto_platforms=false \
        ozone_platform_headless=true \
        enable_package_mash_services=true \
        ${@bb.utils.contains('PACKAGECONFIG', 'wayland', 'ozone_platform_wayland=true', 'ozone_platform_wayland=false', d)} \
        ${@bb.utils.contains('PACKAGECONFIG', 'x11', 'ozone_platform_x11=true', 'ozone_platform_x11=false', d)} \
        ${@bb.utils.contains('PACKAGECONFIG', 'wayland', 'ozone_platform=\\\"wayland\\\"', bb.utils.contains('PACKAGECONFIG', 'x11', 'ozone_platform=\\\"x11\\\"', 'ozone_platform=\\\"headless\\\"', d), d)} \
        v8_use_snapshot=false \
        use_kerberos=false \
        use_cups=false \
        use_gnome_keyring=false \
        treat_warnings_as_errors=false \
        target_cpu=\"${CPUTARGET}\" \
        target_os=\"linux\"  \
        host_toolchain=\"//build/toolchain/cros:host\" \
        custom_toolchain=\"//build/toolchain/cros:target\" \
        v8_snapshot_toolchain=\"//build/toolchain/cros:v8_snapshot\" \
        cros_host_is_clang=false \
        cros_target_ar=\"${AR}\" \
        cros_target_cc=\"${CC}\" \
        cros_target_cxx=\"${CXX}\" \
        cros_target_ld=\"${CXX}\" \
        cros_target_extra_cflags=\"${CFLAGS}\" \
        cros_target_extra_ldflags=\"${TARGET_LDFLAGS}\" \
        cros_target_extra_cxxflags=\"${CXXFLAGS}\" \
        cros_target_extra_cppflags=\"${TARGET_CPPFLAGS}\" \
        cros_v8_snapshot_ar=\"${BUILD_AR}\" \
        cros_v8_snapshot_cc=\"${BUILD_CPP}\" \
        cros_v8_snapshot_cxx=\"${BUILD_CXX}\" \
        cros_v8_snapshot_ld=\"${BUILD_CXX}\" \
        cros_v8_snapshot_extra_cflags=\"${BUILD_CFLAGS}\" \
        cros_v8_snapshot_extra_cxxflags=\"${BUILD_CXXFLAGS}\" \
        cros_v8_snapshot_extra_cppflags=\"${BUILD_CPPFLAGS}\" \
        cros_v8_snapshot_extra_ldflags=\"${BUILD_LDFLAGS}\" \
        cros_host_cc=\"${BUILD_CC}\" \
        cros_host_cxx=\"${BUILD_CXX}\" \
        cros_host_ar=\"${BUILD_AR}\" \
        cros_host_ld=\"${BUILD_CXX}\" \
        cros_host_extra_cflags=\"${BUILD_CFLAGS}\" \
        cros_host_extra_cxxflags=\"${BUILD_CXXFLAGS}\" \
        cros_host_extra_cppflags=\"${BUILD_CPPFLAGS}\" \
        cros_host_extra_ldflags=\"${BUILD_LDFLAGS}\" \
        target_sysroot=\"${STAGING_DIR_TARGET}\" \
"

python() {
    if d.getVar('CHROMIUM_ENABLE_WAYLAND', True) == '1':
        d.appendVar('GYP_DEFINES', ' %s ' % d.getVar('CHROMIUM_WAYLAND_GYP_DEFINES', True))
    else:
        d.appendVar('GYP_DEFINES', ' %s ' % d.getVar('CHROMIUM_X11_GYP_DEFINES', True))

    # Set 'is_debug' according to $CHROMIUM_BUILD_TYPE
    if d.getVar('CHROMIUM_BUILD_TYPE', True) == 'Release':
        d.appendVar('EXTRA_OEGN', ' %s ' % 'is_official_build=true')
    else:
        d.appendVar('EXTRA_OEGN', ' %s ' % 'is_debug=true')
}

DEPENDS += " nss"

do_configure() {
        # Run GN
        gn gen out/${CHROMIUM_BUILD_TYPE} --args="${EXTRA_OEGN}"
}

do_compile() {
        # Build with ninja
        ninja -v -C ${S}/out/${CHROMIUM_BUILD_TYPE} ${PARALLEL_MAKE} chrome chrome_sandbox mash:all
}

do_install_append() {
        # Add extra command line arguments to google-chrome script by modifying
        # the dummy "CHROME_EXTRA_ARGS" line
        sed -i "s/^CHROME_EXTRA_ARGS=\"\"/CHROME_EXTRA_ARGS=\"${CHROMIUM_EXTRA_ARGS}\"/" ${D}${bindir}/google-chrome

        # update ROOT_HOME with the root user's $HOME
        sed -i "s#ROOT_HOME#${ROOT_HOME}#" ${D}${bindir}/google-chrome

        # Always adding this libdir (not just with component builds), because the
        # LD_LIBRARY_PATH line in the google-chromium script refers to it
        install -d ${D}${libdir}/${BPN}/
        if [ -n "${@bb.utils.contains('PACKAGECONFIG', 'component-build', 'component-build', '', d)}" ]; then
                install -m 0755 ${B}/out/${CHROMIUM_BUILD_TYPE}/lib/*.so ${D}${libdir}/${BPN}/
        fi
}
