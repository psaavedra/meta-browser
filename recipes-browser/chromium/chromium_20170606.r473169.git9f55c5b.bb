include chromium-browser-gn.inc

PR = "dev0"

do_configure_prepend () {
        # Ninja complains if this is not correctly set.
        echo 'LASTCHANGE='ac0ab56fdfe6edad161caa4c657a068390cbba83' > build/util/LASTCHANGE
        echo 'LASTCHANGE='ac0ab56fdfe6edad161caa4c657a068390cbba83-refs/heads/master@{#481962}' > build/util/LASTCHANGE.blink
}

SRC_URI = "\
        https://tmp.igalia.com/chromium-tarballs/chromium-wayland-ac0ab56.tar.xz \
        file://google-chrome \
        ${@bb.utils.contains('PACKAGECONFIG', 'disable-api-keys-info-bar', 'file://api-keys.patch;patchdir=${WORKDIR}', '', d)} \
        file://google-chrome.desktop \
        file://0004-Create-empty-i18n_process_css_test.html-file-to-avoi.patch \
        file://chromium-wayland/0001-Fix-wayland-window-show-hide.patch \
        ${@bb.utils.contains('PACKAGECONFIG', 'ignore-lost-context', 'file://0001-Remove-accelerated-Canvas-support-from-blacklist.patch', '', d)} \
"

S = "${WORKDIR}/src"

LIC_FILES_CHKSUM = "file://LICENSE;md5=0fca02217a5d49a14dfe2d11837bb34d"
SRC_URI[md5sum] = "5edcffee9339fa4e885aadffb14b5a00"
SRC_URI[sha256sum] = "2aacd00b95429a68cb3ce9d5077527c27bb7292cd59eab86ae0210b1876aada5"

INSANE_SKIP_${PN}_arm = "already-stripped"
