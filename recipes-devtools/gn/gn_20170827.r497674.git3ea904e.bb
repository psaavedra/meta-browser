require gn.inc

S = "${WORKDIR}/src"

SRC_URI = "https://tmp.igalia.com/chromium-tarballs/chromium-wayland-3ea904e.tar.xz"
SRC_URI += "file://0001-Add-missing-dependancies.patch"

SRC_URI[md5sum] = "de740d6ac438b442a16a77f2ba39407b"
SRC_URI[sha256sum] = "27d386b81666b39088a54a93ca9dbb04892527988422eef2bc94639a09ef6d24"
