#!/usr/bin/env bash

#  Required by google services plugin

mkdir app/src/release
mkdir app/src/debug

cp ${BUDDYBUILD_SECURE_FILES}/google-services-release.json ${BUDDYBUILD_WORKSPACE}/app/src/release/google-services.json
cp ${BUDDYBUILD_SECURE_FILES}/google-services-debug.json ${BUDDYBUILD_WORKSPACE}/app/src/debug/google-services.json
