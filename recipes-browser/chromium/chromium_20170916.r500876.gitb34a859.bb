include chromium-browser-gn.inc

PR = "dev0"

do_configure_prepend () {
        # Ninja complains if this is not correctly set.
        echo 'LASTCHANGE='b34a859e04b2b873a9434809f87aa8c9fb576e01' > build/util/LASTCHANGE
        echo 'LASTCHANGE='b34a859e04b2b873a9434809f87aa8c9fb576e01-refs/heads/master@{#500876}' > build/util/LASTCHANGE.blink
}

SRC_URI = "\
        https://tmp.igalia.com/chromium-tarballs/chromium-wayland-b34a859.tar.xz \
        file://google-chrome \
        ${@bb.utils.contains('PACKAGECONFIG', 'disable-api-keys-info-bar', 'file://api-keys.patch;patchdir=${WORKDIR}', '', d)} \
        file://google-chrome.desktop \
        file://chromium/0001-Rotate-gcc-toolchain-s-build-flags.patch \
        file://0004-Create-empty-i18n_process_css_test.html-file-to-avoi.patch \
        ${@bb.utils.contains('PACKAGECONFIG', 'ignore-lost-context', 'file://0001-Remove-accelerated-Canvas-support-from-blacklist.patch', '', d)} \
"

S = "${WORKDIR}/src"

LIC_FILES_CHKSUM = "file://LICENSE;md5=0fca02217a5d49a14dfe2d11837bb34d"
SRC_URI[md5sum] = "8e38ca00f0245c07119fb59411eef4ea"
SRC_URI[sha256sum] = "caab0aadf241d3fe305da0340bf45d6f0d24e7696436bc04738d24a0b7527c4c"

INSANE_SKIP_${PN}_arm = "already-stripped"
