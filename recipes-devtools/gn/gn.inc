SUMMARY = "Meta-build system from Google"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0fca02217a5d49a14dfe2d11837bb34d"

DEPENDS = "python-native ninja-native"

S = "${WORKDIR}/chromium-${PV}"

# bootstrap.py --no_clean hardcodes the build location to out_bootstrap.
# Omitting --no_clean causes the script to create a temporary directory with a
# random name outside the build directory, so we choose the lesser of the two
# evils.
B = "${S}/out_bootstrap"

# The build system expects the linker to be invoked via the compiler. If we use
# the default value for BUILD_LD, it will fail because it does not recognize
# some of the arguments passed to it.
BUILD_LD = "${CXX}"

do_configure[noexec] = "1"

do_compile() {
      python ${S}/tools/gn/bootstrap/bootstrap.py --verbose --no-clean -s --no-rebuild
}

do_install() {
      install -d ${D}${bindir}
      install -m 0755 ${S}/out/Release/gn ${D}${bindir}/gn
}

BBCLASSEXTEND = "native nativesdk"
