SUMMARY = "VPX multi-format codec"
DESCRIPTION = "The BSD-licensed libvpx reference implementation provides en- and decoders for VP8 and VP9 bitstreams."
HOMEPAGE = "http://www.webmproject.org/code/"
BUGTRACKER = "http://code.google.com/p/webm/issues/list"
SECTION = "libs/multimedia"
LICENSE = "BSD"

LIC_FILES_CHKSUM = "file://LICENSE;md5=d5b04755015be901744a78cc30d390d4"

SRC_URI += "http://storage.googleapis.com/downloads.webmproject.org/releases/webm/${BP}.tar.bz2 \
            file://libvpx-configure-support-blank-prefix.patch \
           "
SRC_URI[md5sum] = "f95a176768a0e1bb4fe42742e27a41af"
SRC_URI[sha256sum] = "d0afbb5eb1ecae68f8d578abace160a97e2e8a230e3028cf4db115d59a695aad"

# ffmpeg links with this and fails
# sysroots/armv4t-oe-linux-gnueabi/usr/lib/libvpx.a(vpx_encoder.c.o)(.text+0xc4): unresolvable R_ARM_THM_CALL relocation against symbol `memcpy@@GLIBC_2.4'
ARM_INSTRUCTION_SET_armv4 = "arm"

CFLAGS += "-fPIC"

export CC
export LD = "${CC}"

VPXTARGET_armv5te = "armv5te-linux-gcc"
VPXTARGET_armv6 = "armv6-linux-gcc"
VPXTARGET_armv7a = "armv7-linux-gcc"
VPXTARGET_aarch64 = "arm64-linux-gcc"
VPXTARGET ?= "generic-gnu"

CONFIGUREOPTS = " \
    --target=${VPXTARGET} \
    --enable-vp9 \
    --enable-libs \
    --disable-install-docs \
    --disable-static \
    --enable-shared \
    --enable-experimental \
    --enable-spatial-svc \
    --prefix=${prefix} \
    --libdir=${libdir} \
    --size-limit=16384x16384 \
"

do_configure() {
    ${S}/configure ${CONFIGUREOPTS}
}

do_install() {
    oe_runmake install DESTDIR=${D}
    chown -R root:root ${D}
}
