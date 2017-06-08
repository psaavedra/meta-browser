include chromium-browser-gn.inc

PR = "dev0"

do_configure_prepend () {
        # Ninja complains if this is not correctly set.
        echo 'LASTCHANGE='9f55c5b2ea4cd2bf834188ce21b1a88c2706e3c2' > build/util/LASTCHANGE
        echo 'LASTCHANGE='9f55c5b2ea4cd2bf834188ce21b1a88c2706e3c2-refs/heads/master@{#473169}' > build/util/LASTCHANGE.blink
}

SRC_URI = "\
        https://tmp.igalia.com/chromium-tarballs/chromium-wayland-9f55c5b.tar.xz \
        file://google-chrome \
        ${@bb.utils.contains('PACKAGECONFIG', 'disable-api-keys-info-bar', 'file://api-keys.patch;patchdir=${WORKDIR}', '', d)} \
        file://google-chrome.desktop \
        file://0004-Create-empty-i18n_process_css_test.html-file-to-avoi.patch \
        file://chromium-wayland/0001-Fix-wayland-window-show-hide.patch \
        ${@bb.utils.contains('PACKAGECONFIG', 'ignore-lost-context', 'file://0001-Remove-accelerated-Canvas-support-from-blacklist.patch', '', d)} \
"

S = "${WORKDIR}/src"

LIC_FILES_CHKSUM = "file://LICENSE;md5=0fca02217a5d49a14dfe2d11837bb34d"
SRC_URI[md5sum] = "ae8966cde764bb4cc783286025171931"
SRC_URI[sha256sum] = "2b1d3baf7967e7d1ab813d9778778b497e593ff710638eff4cbec5057a06bef3"

INSANE_SKIP_${PN}_arm = "already-stripped"
