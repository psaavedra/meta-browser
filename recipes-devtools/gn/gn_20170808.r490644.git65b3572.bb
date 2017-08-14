require gn.inc

S = "${WORKDIR}/src"

SRC_URI = "https://tmp.igalia.com/chromium-tarballs/chromium-wayland-65b3572.tar.xz"
SRC_URI += "file://0001-Remove-deprecated-includes.patch"

SRC_URI[md5sum] = "551e16f05a2d2444d01c572876466100"
SRC_URI[sha256sum] = "ed2f62b9476ca524d60aa83fb20354d82d61f0cd4574cb3b5126132f0b1875e5"
