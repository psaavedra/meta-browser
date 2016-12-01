include chromium-browser-gn.inc

PR = "dev0"

do_configure_prepend () {
        # Ninja complains if this is not correctly set.
        echo 'LASTCHANGE=03e06cc174fe2cbba5fab1291442e470e5e94b09' > build/util/LASTCHANGE
        echo 'LASTCHANGE=03e06cc174fe2cbba5fab1291442e470e5e94b09-refs/heads/master@{#435283}' > build/util/LASTCHANGE.blink
}

SRC_URI = "\
        https://tmp.igalia.com/chromium-tarballs/chromium-03e06cc174f.tar.gz \
        file://google-chrome \
        ${@bb.utils.contains('PACKAGECONFIG', 'disable-api-keys-info-bar', 'file://api-keys.patch;patchdir=${WORKDIR}', '', d)} \
        file://google-chrome.desktop \
        file://chromium/Fix-build-with-wayland-only-ozone-target.patch \
        file://chromium/Bundle-some-ash-resources-into-chrome-for-mash-1-use.patch \
        file://chromium/Ozone-platform-wayland-Implement-keyboard-handling.patch \
        file://chromium/add_missing_stat_h_include.patch \
        file://0004-Create-empty-i18n_process_css_test.html-file-to-avoi.patch \
        file://0005-Override-root-filesystem-access-restriction.patch \
        ${@bb.utils.contains('PACKAGECONFIG', 'ignore-lost-context', 'file://0001-Remove-accelerated-Canvas-support-from-blacklist.patch', '', d)} \
"

S = "${WORKDIR}/src"


LIC_FILES_CHKSUM = "file://LICENSE;md5=0fca02217a5d49a14dfe2d11837bb34d"
SRC_URI[md5sum] = "14a4142bd8791873276e01bdd5390b24"
SRC_URI[sha256sum] = "a1f10e9e7f675ad05e386919b22999033210254eab0d7bed8ae8e8611b91f95c"

INSANE_SKIP_${PN}_arm = "already-stripped"
