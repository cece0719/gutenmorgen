#!/bin/bash

TOMCATDIRNAME=tomcat
TOMCATDIR=$HOME/apps/$TOMCATDIRNAME

SCRIPT=$(readlink -f "$0")
SCRIPTPATH=$(dirname "$SCRIPT")
WORKSPACE=$SCRIPTPATH

/home/webservice/workspace/gutenmorgen/go.sh >/dev/null &