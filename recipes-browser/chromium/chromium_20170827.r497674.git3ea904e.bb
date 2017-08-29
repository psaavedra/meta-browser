include chromium-browser-gn.inc

PR = "dev0"

do_configure_prepend () {
        # Ninja complains if this is not correctly set.
        echo 'LASTCHANGE='3ea904e3488e7af8b03e29fc71d9b9998ffc325b' > build/util/LASTCHANGE
        echo 'LASTCHANGE='3ea904e3488e7af8b03e29fc71d9b9998ffc325b-refs/heads/master@{#497674}' > build/util/LASTCHANGE.blink
}

SRC_URI = "\
        https://tmp.igalia.com/chromium-tarballs/chromium-wayland-3ea904e.tar.xz \
        file://google-chrome \
        ${@bb.utils.contains('PACKAGECONFIG', 'disable-api-keys-info-bar', 'file://api-keys.patch;patchdir=${WORKDIR}', '', d)} \
        file://google-chrome.desktop \
        file://chromium/0001-Rotate-gcc-toolchain-s-build-flags.patch \
        file://0004-Create-empty-i18n_process_css_test.html-file-to-avoi.patch \
        ${@bb.utils.contains('PACKAGECONFIG', 'ignore-lost-context', 'file://0001-Remove-accelerated-Canvas-support-from-blacklist.patch', '', d)} \
"

S = "${WORKDIR}/src"

LIC_FILES_CHKSUM = "file://LICENSE;md5=0fca02217a5d49a14dfe2d11837bb34d"
SRC_URI[md5sum] = "de740d6ac438b442a16a77f2ba39407b"
SRC_URI[sha256sum] = "27d386b81666b39088a54a93ca9dbb04892527988422eef2bc94639a09ef6d24"

INSANE_SKIP_${PN}_arm = "already-stripped"
