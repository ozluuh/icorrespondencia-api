#!/bin/sh

get_deploy_url() {
    # mvn azure-webapp:deploy | grep -oE "https?.*\.net"
    echo "[INFO] Successfully deployed the artifact to https://app-icorrespondencia.azurewebsites.net" | grep -oE "https?.*\.net"
}

get_deploy_url
