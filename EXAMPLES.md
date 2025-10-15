# NieloShop - Przykłady Konfiguracji

## Przykład 1: Podstawowa Konfiguracja

### config.yml
```yaml
storage-type: SQLITE

time-points:
  enabled: true
  points-per-interval: 1
  interval-seconds: 60
  afk-threshold-seconds: 60
  stop-on-afk: true

wallet:
  enabled: true
  starting-balance: 100

currency-format:
  use-short-format: true
  thousand: k
  million: M
  billion: B
  trillion: T
  decimal-places: 1

gui:
  vault-shop-title: "&6&lVault Shop"
  time-shop-title: "&b&lTime Shop"
  wallet-shop-title: "&a&lWallet Shop"
  enable-animations: true
  confirmation-enabled: true
  confirmation-title: "&e&lConfirm Purchase"

debug: false
```

## Przykład 2: Serwer Survival

### config.yml
```yaml
storage-type: SQLITE

time-points:
  enabled: true
  points-per-interval: 5
  interval-seconds: 300
  afk-threshold-seconds: 120
  stop-on-afk: true

wallet:
  enabled: true
  starting-balance: 1000

currency-format:
  use-short-format: true
  thousand: k
  million: mln
  billion: mld
  trillion: bln
  decimal-places: 2

gui:
  vault-shop-title: "&2&l⛏ Survival Shop ⛏"
  time-shop-title: "&3&l⏰ Time Shop ⏰"
  wallet-shop-title: "&e&l💰 Premium Shop 💰"
  enable-animations: true
  confirmation-enabled: true
  confirmation-title: "&6&lPotwierdzenie Zakupu"

debug: false
```

## Przykład 3: Serwer Creative/Minigames

### config.yml
```yaml
storage-type: JSON

time-points:
  enabled: false
  points-per-interval: 1
  interval-seconds: 60
  afk-threshold-seconds: 60
  stop-on-afk: true

wallet:
  enabled: true
  starting-balance: 5000

currency-format:
  use-short-format: false

gui:
  vault-shop-title: "&c&lShop"
  time-shop-title: "&b&lTime Shop"
  wallet-shop-title: "&a&lCoins Shop"
  enable-animations: false
  confirmation-enabled: false

debug: false
```

## Przykład 4: Serwer RP/Economy

### config.yml
```yaml
storage-type: SQLITE

time-points:
  enabled: true
  points-per-interval: 2
  interval-seconds: 180
  afk-threshold-seconds: 300
  stop-on-afk: true

wallet:
  enabled: true
  starting-balance: 500

currency-format:
  use-short-format: true
  thousand: tyś
  million: mln
  billion: mld
  trillion: bln
  decimal-places: 1

gui:
  vault-shop-title: "&6&l⚜ Sklep Miejski ⚜"
  time-shop-title: "&b&l⌚ Sklep Czasu ⌚"
  wallet-shop-title: "&e&l👛 Portfel 👛"
  enable-animations: true
  confirmation-enabled: true
  confirmation-title: "&a&lPotwierdź Transakcję"

debug: false
```

## Przykład 5: Polski Serwer

### messages.yml (fragment)
```yaml
prefix: "&8[&6NieloShop&8]&r "

no-permission: "&cNie masz uprawnień do tego!"
player-only: "&cTa komenda może być użyta tylko przez graczy!"
player-not-found: "&cGracz nie znaleziony!"
invalid-amount: "&cNieprawidłowa kwota! Podaj poprawną liczbę."
reload-success: "&aKonfiguracja przeładowana pomyślnie!"
save-success: "&aDane zapisane pomyślnie!"

shop-vault-opened: "&aOtworzono sklep Vault!"
shop-time-opened: "&aOtworzono sklep czasu!"
shop-wallet-opened: "&aOtworzono sklep portfela!"
shop-empty: "&cTen sklep jest pusty!"
insufficient-funds: "&cNie masz wystarczająco {currency}!"
purchase-success: "&aZakupiono &e{amount}x {item} &aza &e{price} {currency}&a!"
sell-success: "&aSprzedano &e{amount}x {item} &aza &e{price} {currency}&a!"

wallet-balance: "&aTwoje saldo: &e{balance}"
time-points-info: "&aCzas gry: &e{time}\n&aPunkty czasowe: &e{points}"

currency-vault: "pieniędzy"
currency-time: "punktów czasu"
currency-wallet: "monet"
```

## Przykład 6: Angielski Serwer

### messages.yml (fragment)
```yaml
prefix: "&8[&6NieloShop&8]&r "

no-permission: "&cYou don't have permission to do that!"
player-only: "&cThis command can only be used by players!"
player-not-found: "&cPlayer not found!"
invalid-amount: "&cInvalid amount! Please enter a valid number."
reload-success: "&aConfiguration reloaded successfully!"
save-success: "&aData saved successfully!"

shop-vault-opened: "&aOpened Vault shop!"
shop-time-opened: "&aOpened Time shop!"
shop-wallet-opened: "&aOpened Wallet shop!"
shop-empty: "&cThis shop is empty!"
insufficient-funds: "&cYou don't have enough {currency}!"
purchase-success: "&aPurchased &e{amount}x {item} &afor &e{price} {currency}&a!"
sell-success: "&aSold &e{amount}x {item} &afor &e{price} {currency}&a!"

wallet-balance: "&aYour wallet balance: &e{balance}"
time-points-info: "&aTime played: &e{time}\n&aTime points: &e{points}"

currency-vault: "money"
currency-time: "time points"
currency-wallet: "coins"
```

## Scenariusze Użycia

### Scenariusz 1: Dodanie Diamentu do Sklepu Vault

1. Admin wykonuje: `/nieloshop add vault`
2. Admin klika diament w ekwipunku
3. W chacie wpisuje: `100` (cena zakupu)
4. W chacie wpisuje: `80` (cena sprzedaży)
5. W GUI klika zielony przycisk potwierdzenia

**Efekt:** Diament pojawia się w sklepie Vault za 100, można go sprzedać za 80.

### Scenariusz 2: Gracz Kupuje Przedmiot

1. Gracz wykonuje: `/shop vault`
2. Gracz klika LPM na przedmiot
3. System sprawdza saldo
4. Jeśli wystarczy - pobiera pieniądze i daje przedmiot
5. Gracz widzi komunikat o sukcesie

### Scenariusz 3: Gracz Sprzedaje Przedmiot

1. Gracz ma przedmiot w ekwipunku
2. Gracz wykonuje: `/shop vault`
3. Gracz klika PPM na przedmiot w sklepie
4. System sprawdza czy ma przedmiot
5. Jeśli tak - zabiera przedmiot i dodaje pieniądze
6. Gracz widzi komunikat o sukcesie

### Scenariusz 4: Admin Nadaje Walutę

```bash
/portfel nadaj Steve 1000
```

**Efekt:** Gracz Steve otrzymuje 1000 monet do portfela.

### Scenariusz 5: Sprawdzanie Czasu Gry

```bash
/czas
```

**Efekt:** Gracz widzi:
```
Czas gry: 5h 30m 15s
Punkty czasowe: 330
```

## Integracja z PlaceholderAPI

### Scoreboard
```yaml
title: "&6&lMój Serwer"
lines:
  - ""
  - "&7Pieniądze: &a$%vault_eco_balance_formatted%"
  - "&7Portfel: &e%nieloshop_wallet%"
  - "&7Punkty: &b%nieloshop_time_points%"
  - ""
  - "&7Czas gry: &f%nieloshop_playtime%"
  - ""
```

### Tab List
```yaml
header:
  - "&6&l⚔ Witaj na serwerze! ⚔"
  - "&7Saldo: &a$%vault_eco_balance_formatted%"
  - "&7Portfel: &e%nieloshop_wallet%"

footer:
  - "&7Online: &f%server_online%/%server_max_players%"
  - "&7TPS: &f%server_tps%"
```

### Chat Format
```yaml
format: "%luckperms_prefix%%player_name%%luckperms_suffix%&7: &f%message%"
hover:
  - "&7Pieniądze: &a$%vault_eco_balance_formatted%"
  - "&7Portfel: &e%nieloshop_wallet% monet"
  - "&7Czas gry: &b%nieloshop_playtime%"
```

## Uprawnienia dla Rang

### Gracz (Default)
```yaml
permissions:
  - nieloshop.use
  - nieloshop.portfel.use
```

### Moderator
```yaml
permissions:
  - nieloshop.use
  - nieloshop.portfel.use
  - nieloshop.portfel.give
  - nieloshop.portfel.take
```

### Admin
```yaml
permissions:
  - nieloshop.*
```

## Przykładowe Sklepy

### Sklep Survival
- Bloki ziemi - 1$ za 64
- Kamień - 5$ za 64
- Drewno dębowe - 10$ za 32
- Diamenty - 100$ za 1
- Elytry - 5000$ za 1

### Sklep Czasowy
- Klucze do skrzynek - 100 punktów
- Booster XP - 50 punktów
- Teleport do domu - 10 punktów
- Zmiana nicku - 500 punktów

### Sklep Premium (Wallet)
-Prefix VIP - 10000 monet
- Komendy /fly - 50000 monet
- Dostęp do areny - 5000 monet
- Pet - 25000 monet

## Migracja z Innych Pluginów

### Z ChestShop
1. Wyeksportuj listę przedmiotów
2. Dla każdego przedmiotu użyj `/nieloshop add vault`
3. Ustaw odpowiednie ceny
4. Usuń stare chestshopy

### Z EssentialsX /sell
1. Sprawdź ceny w konfigu Essentials
2. Dodaj przedmioty do NieloShop z tymi samymi cenami
3. Wyłącz /sell w Essentials

## Tips & Tricks

### 1. Backup przed zmianami
```bash
cp -r plugins/NieloShop plugins/NieloShop.backup
```

### 2. Test na serwerze testowym
Zawsze testuj nowe przedmioty na test serverze przed dodaniem na produkcji.

### 3. Monitoruj ekonomię
Sprawdzaj regularnie średnie saldo graczy i dostosuj ceny.

### 4. Używaj kategorii
Dodaj przedmioty w logicznych grupach (bloki, narzędzia, jedzenie).

### 5. Balansuj ceny
Cena sprzedaży = 70-80% ceny zakupu (aby grać było opłacalne).
