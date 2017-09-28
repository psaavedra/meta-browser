include chromium-browser-gn.inc

PR = "dev0"

do_configure_prepend () {
        # Ninja complains if this is not correctly set.
        echo 'LASTCHANGE='8eb8619a8d76d7fed9ace05a337d57c09d201d92' > build/util/LASTCHANGE
        echo 'LASTCHANGE='8eb8619a8d76d7fed9ace05a337d57c09d201d92-refs/heads/master@{#499098}' > build/util/LASTCHANGE.blink
}

SRC_URI = "\
        https://tmp.igalia.com/chromium-tarballs/chromium-wayland-8eb8619a.tar.xz \
        file://google-chrome \
        ${@bb.utils.contains('PACKAGECONFIG', 'disable-api-keys-info-bar', 'file://api-keys.patch;patchdir=${WORKDIR}', '', d)} \
        file://google-chrome.desktop \
        file://chromium/0001-Rotate-gcc-toolchain-s-build-flags.patch \
        file://0004-Create-empty-i18n_process_css_test.html-file-to-avoi.patch \
        ${@bb.utils.contains('PACKAGECONFIG', 'ignore-lost-context', 'file://0001-Remove-accelerated-Canvas-support-from-blacklist.patch', '', d)} \
"

S = "${WORKDIR}/src"

LIC_FILES_CHKSUM = "file://LICENSE;md5=0fca02217a5d49a14dfe2d11837bb34d"
SRC_URI[md5sum] = "2a83227fcdb52b162014384e6550e8cb"
SRC_URI[sha256sum] = "1f394b48dc5f3815d051f7af724873ff0785086578aadc0d8d4395e10217be27"

INSANE_SKIP_${PN}_arm = "already-stripped"
