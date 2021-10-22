#!/bin/sh

get_deploy_url() {
    cat log.txt | grep -oE "https?.*\.net"
    rm -f log.txt
}

make_deploy() {
    mvn azure-webapp:deploy > log.txt
}

"$@"
