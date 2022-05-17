# Yleistä

Työkoe-ympäristö provisioidaan ansible-ohjelmiston avulla.

```
$ ansible --version
ansible [core 2.12.2]
  [...]
  python version = 3.10.2 (main, Feb  2 2022, 06:19:27) [Clang 13.0.0 (clang-1300.0.29.3)]
  jinja version = 3.0.3
  libyaml = True

```

Käyttäjällä pitää olla toimiva tunnus (@ `koe7-dv.csc.fi`), TUNNUS on esim. `zvarga`:

```
ansible-playbook -i development.yml verify.yml --extra-vars=ansible_user=TUNNUS
```

# Docker-asennus

```
ansible-playbook -i development.yml docker.yml --extra-vars=ansible_user=TUNNUS
```

* docker
* docker compose
* kehittäjät docker-ryhmään
