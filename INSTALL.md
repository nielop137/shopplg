# NieloShop - Instalacja i Kompilacja

## Wymagania do kompilacji

- Java 17 lub nowsza
- Maven 3.6+
- Git (opcjonalnie)

## Kompilacja

### 1. Pobierz kod źródłowy

```bash
git clone <repository-url>
cd NieloShop
```

### 2. Kompiluj plugin

```bash
mvn clean package
```

### 3. Znajdź skompilowany plik

Plik JAR będzie w: `target/NieloShop-1.0.0.jar`

## Instalacja na serwerze

### 1. Wymagania serwera

- Minecraft Server: Spigot lub Paper 1.16.5 - 1.21+
- Java 17+
- Plugin **Vault** (wymagany)
- Plugin ekonomiczny (np. EssentialsX)
- Plugin **PlaceholderAPI** (opcjonalny)

### 2. Instalacja

1. Umieść `NieloShop-1.0.0.jar` w folderze `plugins/`
2. Upewnij się, że Vault i plugin ekonomiczny są zainstalowane
3. Uruchom lub zrestartuj serwer
4. Plugin utworzy folder `plugins/NieloShop/` z plikami konfiguracyjnymi

### 3. Pierwsze uruchomienie

Po pierwszym uruchomieniu zostaną utworzone pliki:

```
plugins/NieloShop/
├── config.yml       - Główna konfiguracja
├── messages.yml     - Wiadomości (PL/EN)
├── shops.yml        - Przedmioty w sklepach (auto-zarządzane)
└── data.db          - Baza danych SQLite (lub playerdata.json)
```

### 4. Konfiguracja

1. Edytuj `config.yml` według potrzeb:
   - Typ storage (SQLite lub JSON)
   - Ustawienia punktów czasowych
   - Formatowanie waluty
   - Tytuły GUI

2. Edytuj `messages.yml` aby dostosować wiadomości

3. Uruchom `/nieloshop reload` aby załadować zmiany

### 5. Dodawanie przedmiotów do sklepów

```bash
/nieloshop add vault    # Dodaj przedmiot do sklepu Vault
/nieloshop add time     # Dodaj przedmiot do sklepu Time
/nieloshop add wallet   # Dodaj przedmiot do sklepu Wallet
```

Następnie:
1. Kliknij przedmiot w ekwipunku
2. Wpisz cenę zakupu w chacie (lub 0)
3. Wpisz cenę sprzedaży w chacie (lub 0)
4. Zatwierdź w GUI

## Konfiguracja PlaceholderAPI

Jeśli używasz PlaceholderAPI, dostępne są placeholdery:

```
%nieloshop_wallet%        - Saldo portfela gracza
%nieloshop_time_points%   - Punkty czasowe gracza
%nieloshop_playtime%      - Całkowity czas gry
```

## Rozwiązywanie problemów

### Plugin się nie włącza

**Problem:** `Vault not found! Disabling plugin...`
**Rozwiązanie:** Zainstaluj plugin Vault i plugin ekonomiczny (np. EssentialsX)

### Błąd przy dodawaniu przedmiotu

**Problem:** Przedmiot nie zapisuje się w sklepie
**Rozwiązanie:** Sprawdź logi serwera, upewnij się że masz uprawnienie `nieloshop.admin`

### Baza danych nie działa

**Problem:** Błędy związane z SQLite
**Rozwiązanie:**
1. Sprawdź czy folder `plugins/NieloShop/` ma uprawnienia zapisu
2. Zmień `storage-type: JSON` w config.yml

### Placeholdery nie działają

**Problem:** Placeholdery pokazują `%nieloshop_wallet%` zamiast wartości
**Rozwiązanie:**
1. Zainstaluj PlaceholderAPI
2. Uruchom `/papi reload`

## Aktualizacja

1. Zatrzymaj serwer
2. Utwórz backup folderu `plugins/NieloShop/`
3. Zastąp stary JAR nowym
4. Uruchom serwer
5. Sprawdź logi czy wszystko działa

## Migracja danych

### Z JSON na SQLite

1. Zatrzymaj serwer
2. Zmień w `config.yml`: `storage-type: SQLITE`
3. Uruchom serwer
4. Plugin automatycznie migruje dane

### Ze SQLite na JSON

1. Zatrzymaj serwer
2. Zmień w `config.yml`: `storage-type: JSON`
3. Uruchom serwer
4. Plugin automatycznie migruje dane

## Wsparcie

W razie problemów:
- Discord: **nielopekk**
- Sprawdź logi serwera w `logs/latest.log`
- Upewnij się, że używasz wspieranych wersji

## Licencja

Plugin stworzony do użytku prywatnego.
Wszelkie prawa należą do autora: Nielopekk
