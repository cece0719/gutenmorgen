#!/bin/bash

export TOMCATDIRNAME=tomcat
export TOMCATDIR=$HOME/apps/$TOMCATDIRNAME

export SCRIPT=$(readlink -f "$0")
export SCRIPTPATH=$(dirname "$SCRIPT")
export WORKSPACE=$SCRIPTPATH



/home/webservice/workspace/gutenmorgen/go.sh >/dev/null &