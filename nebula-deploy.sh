#!/bin/bash
#Nebula server deploy script

#==============   FUNCTION BLOCK   ==============

NEBULA_PID_FILE='nebula.pid'

log() {
    echo $"NebulaDeploy" $1
}

build() {
    log "Building project..."
    mvn clean install
}

deploy() {
    log "Deploy started..."
    cp standard/target/nebula-server_static.jar $1/
    cp -R standard/web/ $1/
    cp standard/launcher.sh $1/
    chmod +x $1/launcher.sh
    if [ ! -f "$1/nebula.properties" ]; then
        log "No config founded. Use default."
        cp standard/nebula.properties $1/
    fi
    cp standard/LICENSE $1/
    cp README.md $1/
}

#================   MAIN BLOCK   ================

mkdir $1
if [ ! -d standard/target ]; then
    build
fi
deploy $1
if [[ "$2" == "launch" ]]; then
    cd $1
    if [ -f $NEBULA_PID_FILE ]; then
        ./launcher.sh stop
    fi
    ./launcher.sh start
else
    log "Deploy completed, use \`nebula-launcher.sh start\` to launch your application."
fi