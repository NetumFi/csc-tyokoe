#!/bin/bash

# This script is registered as service "promote".

# Use "sudo journalctl -u promote -f" to see the service logs.

cd $(dirname $0)

TAG="$1"

cleanup() {
    # kill all processes whose parent is this process
    pkill -P $$
}

trap cleanup EXIT

function handle {
  echo "$1 $2 $3"
  if [ "tag" == "$1" ] && [ "(name=$TAG)" == "$3" ];
  then
    date
    echo "Image $2 was tagged with $TAG, restarting services..."
    docker compose stop csc2022-app
    docker compose rm --force csc2022-app
    docker compose up -d csc2022-app
  fi
}

function listen {
  docker events --filter "type=image" | while read f1 f2 f3 f4 f5; do handle "$f3" "$f4" "$f5"; done
}

echo "Listening to docker events: tagging an image with $TAG shall restart docker compose application"

listen
