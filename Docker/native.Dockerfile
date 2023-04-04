FROM ghcr.io/graalvm/graalvm-ce:22.3.1 as builder

WORKDIR /app
COPY . /app

RUN gu install native-image

# BEGIN PRE-REQUISITES FOR STATIC NATIVE IMAGES FOR GRAAL
# SEE: https://github.com/oracle/graal/blob/master/substratevm/StaticImages.md
ARG RESULT_LIB="/staticlibs"

RUN mkdir ${RESULT_LIB} && \
    curl -L -o musl.tar.gz https://musl.libc.org/releases/musl-1.2.1.tar.gz && \
    mkdir musl && tar -xvzf musl.tar.gz -C musl --strip-components 1 && cd musl && \
    ./configure --disable-shared --prefix=${RESULT_LIB} && \
    make && make install && \
    cp /usr/lib/gcc/x86_64-redhat-linux/11/libstdc++.a ${RESULT_LIB}/lib/

ENV PATH="$PATH:${RESULT_LIB}/bin"
ENV CC="musl-gcc"

RUN curl -L -o zlib.tar.gz https://zlib.net/zlib-1.2.13.tar.gz && \
    mkdir zlib && tar -xvzf zlib.tar.gz -C zlib --strip-components 1 && cd zlib && \
    ./configure --static --prefix=${RESULT_LIB} && \
    make && make install
#END PRE-REQUISITES FOR STATIC NATIVE IMAGES FOR GRAAL

RUN mkdir -p /tmp/shared \
    && cp /usr/lib64/libstdc++.so.6.0.29 /tmp/shared/libstdc++.so.6 \
    && cp /usr/lib64/libgcc_s-11-20220421.so.1 /tmp/shared/libgcc_s.so.1 \
    && cp /usr/lib64/libz.so.1.2.11 /tmp/shared/libz.so.1 \
    && cp /usr/lib64/libc.so.6 /tmp/shared/libc.so.6

#RUN ./mvnw -Pnative clean native:compile
RUN ./mvnw -Pnative clean package

#RUN curl -L -o xz.rpm http://mirror.centos.org/centos/8-stream/BaseOS/aarch64/os/Packages/xz-5.2.4-4.el8.aarch64.rpm
#RUN rpm -iv xz.rpm

#RUN curl -L -o upx-amd64_linux.tar.xz https://github.com/upx/upx/releases/download/v4.0.2/upx-4.0.2-amd64_linux.tar.xz
#RUN tar -xvf upx-amd64_linux.tar.xz

#FROM registry.access.redhat.com/ubi8/ubi-minimal as nativebuilder
#RUN mkdir -p /tmp/ssl \
#    && cp /usr/lib64/libstdc++.so.6.0.25 /tmp/ssl/libstdc++.so.6 \
#    && cp /usr/lib64/libgcc_s-8-20210514.so.1 /tmp/ssl/libgcc_s.so.1 \
#    && cp /usr/lib64/libz.so.1.2.11 /tmp/ssl/libz.so.1

#RUN upx-amd64_linux/upx -7 /app/target/mazes

FROM gcr.io/distroless/base

COPY --from=builder /tmp/shared/ /newglibc
ENV LD_LIBRARY_PATH=newglibc:$LD_LIBRARY_PATH

COPY --from=builder /app/target/mazes /mazes

ENTRYPOINT ["/mazes"]
