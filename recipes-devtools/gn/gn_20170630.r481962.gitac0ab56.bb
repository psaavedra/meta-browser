require gn.inc

S = "${WORKDIR}/src"

SRC_URI = "https://tmp.igalia.com/chromium-tarballs/chromium-wayland-ac0ab56.tar.xz"
SRC_URI += "file://0001-fix-gn-bootstrap.patch"

SRC_URI[md5sum] = "5edcffee9339fa4e885aadffb14b5a00"
SRC_URI[sha256sum] = "2aacd00b95429a68cb3ce9d5077527c27bb7292cd59eab86ae0210b1876aada5"
