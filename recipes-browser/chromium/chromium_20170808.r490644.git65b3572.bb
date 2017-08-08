include chromium-browser-gn.inc

PR = "dev0"

do_configure_prepend () {
        # Ninja complains if this is not correctly set.
        echo 'LASTCHANGE='65b35725c48bc658fe53911f3d248a68393ecf0e' > build/util/LASTCHANGE
        echo 'LASTCHANGE='65b35725c48bc658fe53911f3d248a68393ecf0e-refs/heads/master@{#490644}' > build/util/LASTCHANGE.blink
}

SRC_URI = "\
        https://tmp.igalia.com/chromium-tarballs/chromium-wayland-65b3572.tar.xz \
        file://google-chrome \
        ${@bb.utils.contains('PACKAGECONFIG', 'disable-api-keys-info-bar', 'file://api-keys.patch;patchdir=${WORKDIR}', '', d)} \
        file://chromium/0001-Fix-set-is-already-declared-in-this-scope.patch \
        file://chromium/0002-Fix-void-BoxLayoutExample-RefreshLayoutPanel-is-priv.patch \
        file://google-chrome.desktop \
        file://0004-Create-empty-i18n_process_css_test.html-file-to-avoi.patch \
        ${@bb.utils.contains('PACKAGECONFIG', 'ignore-lost-context', 'file://0001-Remove-accelerated-Canvas-support-from-blacklist.patch', '', d)} \
"

S = "${WORKDIR}/src"

LIC_FILES_CHKSUM = "file://LICENSE;md5=0fca02217a5d49a14dfe2d11837bb34d"
SRC_URI[md5sum] = "551e16f05a2d2444d01c572876466100"
SRC_URI[sha256sum] = "ed2f62b9476ca524d60aa83fb20354d82d61f0cd4574cb3b5126132f0b1875e5"

INSANE_SKIP_${PN}_arm = "already-stripped"
