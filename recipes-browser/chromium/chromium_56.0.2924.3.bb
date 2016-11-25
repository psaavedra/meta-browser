include chromium-browser-gn.inc

#Patches removed from c52 because didn't applied:
#        file://0003-Remove-hard-coded-values-for-CC-and-CXX.patch
#        file://chromium/0011-Replace-readdir_r-with-readdir.patch
#        file://chromium/remove-Werror.patch

SRC_URI += "\
        file://chromium/Fix-build-with-wayland-only-ozone-target.patch \
        file://chromium/add_missing_stat_h_include.patch \
        file://0004-Create-empty-i18n_process_css_test.html-file-to-avoi.patch \
        file://0005-Override-root-filesystem-access-restriction.patch \
        ${@bb.utils.contains('PACKAGECONFIG', 'ignore-lost-context', 'file://0001-Remove-accelerated-Canvas-support-from-blacklist.patch', '', d)} \
"

LIC_FILES_CHKSUM = "file://LICENSE;md5=0fca02217a5d49a14dfe2d11837bb34d"
SRC_URI[md5sum] = "3540d0f2bb0a26231d9d05366ff29a87"
SRC_URI[sha256sum] = "f2b038048f11128b210039a3796b4bc79f9e8b71a8720638c495a3bf711b28a6"
