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

# Infrastruktuurin asennus

```
ansible-playbook -i development.yml -i vault.yml infrastructure.yml --extra-vars=ansible_user=TUNNUS 
```

# Unix-palvelut (Infra- ja sovellus)

Infrastruktuuri kokonaisuutena (tietokannat, web-proxy, yms.) ja sovellus voidaan hallita UNIX-palveluina (`serviced`). 

```
ansible-playbook -i development.yml service-infra.yml --extra-vars=ansible_user=TUNNUS TAG 
ansible-playbook -i development.yml service-app.yml --extra-vars=ansible_user=TUNNUS TAG 
```

Missä TAG on: 
```
* install   -- asenna palvelu
* up        -- käynnistä
* down      -- pysäytä
* remove    -- poista kokonaan
* enabled   -- käynnistyy automaattisesti
* disabled  -- ei käynnisty automaattisesti
```
