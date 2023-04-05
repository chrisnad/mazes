FROM ghcr.io/graalvm/native-image:ol8-java17-22 as builder

WORKDIR /app
COPY . /app

RUN ./mvnw -Pnative clean package

## UPX
FROM gruebel/upx:latest as upx
COPY --from=builder /app/target/mazes /mazes

RUN upx --best --lzma /mazes
## UPX

## newglibc libraries
FROM registry.access.redhat.com/ubi8/ubi-minimal:8.7-1107 as glibcprovider
RUN mkdir -p /tmp/newglibc \
&& cp /usr/lib64/libgcc_s-8-20210514.so.1 /tmp/newglibc/libgcc_s.so.1 \
&& cp /usr/lib64/libstdc++.so.6.0.25 /tmp/newglibc/libstdc++.so.6 \
&& cp /usr/lib64/libz.so.1.2.11 /tmp/newglibc/libz.so.1
## newglibc libraries

FROM gcr.io/distroless/base

COPY --from=glibcprovider /tmp/newglibc/ /newglibc
ENV LD_LIBRARY_PATH=newglibc:$LD_LIBRARY_PATH

COPY --from=upx /mazes /mazes

ENTRYPOINT ["/mazes"]
