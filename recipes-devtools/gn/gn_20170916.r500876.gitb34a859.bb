require gn.inc

S = "${WORKDIR}/src"

SRC_URI = "https://tmp.igalia.com/chromium-tarballs/chromium-wayland-b34a859.tar.xz"
SRC_URI += "file://0001-Remove-deprecated-dependancies.patch"

SRC_URI[md5sum] = "8e38ca00f0245c07119fb59411eef4ea"
SRC_URI[sha256sum] = "caab0aadf241d3fe305da0340bf45d6f0d24e7696436bc04738d24a0b7527c4c"
