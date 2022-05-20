#!/bin/bash

cleanup() {
    # kill all processes whose parent is this process
    pkill -P $$
}

trap cleanup EXIT

flock -n ./promote.lock ./promote.sh csc2022:promoted
