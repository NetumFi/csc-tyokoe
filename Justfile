help:
  @just --list

# https://github.com/casey/just/issues/469
set dotenv-load := true

init:
  @docker volume create csc_db_data > /dev/null 2>&1 || true

up: init
  @docker-compose -f docker-compose-dev.yml up -d
  @./mvnw

up-fedev: init
    @npm start


down:
  @docker-compose -f docker-compose-dev.yml down

clean:
  #!/usr/bin/env sh
  read -r -p "WARNING: This removes the DB volume too, continue? [y/N] " response
  if [[ "$response" =~ ^(yes|y)$ ]]; then
    docker volume rm csc_db_data || true
    rm -r target
  fi
  echo "Clean done"

logs:
   @docker-compose -f docker-compose-dev.yml logs -f
