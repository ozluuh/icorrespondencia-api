#!/bin/sh

get_deploy_url() {
    cat temp/log.txt | grep -oE "https?.*\.net"
    rm -f log.txt
}

make_deploy() {
    mvn azure-webapp:deploy > temp/log.txt
    if [[ ! -e "temp/log.txt" ]]; then
        return 1
    fi
}

"$@"
