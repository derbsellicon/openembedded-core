SUMMARY = "Linux Trace Toolkit Userspace Tracer 2.x"
DESCRIPTION = "The LTTng UST 2.x package contains the userspace tracer library to trace userspace codes."
HOMEPAGE = "http://lttng.org/ust"
BUGTRACKER = "https://bugs.lttng.org/projects/lttng-ust"

LICENSE = "LGPLv2.1+ & MIT & GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=c963eb366b781252b0bf0fdf1624d9e9 \
                    file://snprintf/snprintf.c;endline=32;md5=d3d544959d8a3782b2e07451be0a903c \
                    file://snprintf/various.h;endline=31;md5=89f2509b6b4682c4fc95255eec4abe44"

inherit autotools lib_package manpages

DEPENDS = "liburcu util-linux"
RDEPENDS_${PN}-bin = "python3-core"

# For backwards compatibility after rename
RPROVIDES_${PN} = "lttng2-ust"
RREPLACES_${PN} = "lttng2-ust"
RCONFLICTS_${PN} = "lttng2-ust"

PE = "2"

SRC_URI = "https://lttng.org/files/lttng-ust/lttng-ust-${PV}.tar.bz2 \
           file://lttng-ust-doc-examples-disable.patch \
          "
SRC_URI[md5sum] = "77f3378ba37a36801420bce87b702e9c"
SRC_URI[sha256sum] = "4d541a863f42dfc685ca05024027a442c70d03594c154a43e62bc109b1ea5daf"

CVE_PRODUCT = "ust"

PACKAGECONFIG[manpages] = "--enable-man-pages, --disable-man-pages, asciidoc-native xmlto-native libxslt-native"

do_install_append() {
        # Patch python tools to use Python 3; they should be source compatible, but
        # still refer to Python 2 in the shebang
        sed -i -e '1s,#!.*python.*,#!${bindir}/python3,' ${D}${bindir}/lttng-gen-tp
}
