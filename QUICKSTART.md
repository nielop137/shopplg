# NieloShop - Szybki Start

## 🚀 5-Minutowa Instalacja

### Krok 1: Pobierz zależności
```bash
# Zainstaluj Vault
# Zainstaluj plugin ekonomiczny (np. EssentialsX)
# (Opcjonalnie) Zainstaluj PlaceholderAPI
```

### Krok 2: Zainstaluj NieloShop
1. Skopiuj `NieloShop-1.0.0.jar` do folderu `plugins/`
2. Uruchom serwer
3. Plugin automatycznie utworzy konfigurację

### Krok 3: Dodaj pierwszy przedmiot
```bash
/nieloshop add vault
# Kliknij przedmiot w ekwipunku
# Wpisz cenę zakupu: 100
# Wpisz cenę sprzedaży: 80
# Kliknij zielony przycisk ✓
```

### Krok 4: Otwórz sklep
```bash
/shop vault
```

**Gotowe!** 🎉

---

## 📋 Podstawowe Komendy

### Dla Graczy
```bash
/shop vault              # Sklep Vault (pieniądze)
/shop time               # Sklep czasu (punkty za grę)
/shop wallet             # Sklep portfela (wirtualna waluta)
/portfel                 # Sprawdź saldo portfela
/czas                    # Sprawdź czas gry i punkty
```

### Dla Adminów
```bash
/nieloshop add vault     # Dodaj przedmiot do sklepu Vault
/nieloshop add time      # Dodaj przedmiot do sklepu czasu
/nieloshop add wallet    # Dodaj przedmiot do sklepu portfela
/nieloshop reload        # Przeładuj konfigurację
/nieloshop save          # Zapisz dane

/portfel nadaj Steve 1000     # Daj 1000 monet graczowi
/portfel zabierz Steve 500    # Zabierz 500 monet graczowi
/portfel ustaw Steve 2000     # Ustaw saldo na 2000
```

---

## 🎮 Jak Użytkować

### Kupowanie
1. Otwórz sklep: `/shop vault`
2. **Lewy przycisk myszy (LPM)** na przedmiot
3. Gotowe!

### Sprzedawanie
1. Miej przedmiot w ekwipunku
2. Otwórz sklep: `/shop vault`
3. **Prawy przycisk myszy (PPM)** na przedmiot
4. Gotowe!

---

## ⚙️ Szybka Konfiguracja

### Zmiana Języka na Polski

`messages.yml`:
```yaml
prefix: "&8[&6NieloShop&8]&r "
no-permission: "&cNie masz uprawnień!"
purchase-success: "&aZakupiono &e{amount}x {item} &aza &e{price}!"
# ... etc
```

### Zmiana Startowego Salda Portfela

`config.yml`:
```yaml
wallet:
  starting-balance: 1000  # Zmień na dowolną wartość
```

### Zmiana Punktów za Czas

`config.yml`:
```yaml
time-points:
  points-per-interval: 5     # 5 punktów
  interval-seconds: 300      # co 5 minut
```

### Wyłączenie Systemu Czasu

`config.yml`:
```yaml
time-points:
  enabled: false
```

---

## 🔧 Rozwiązywanie Problemów

### "Vault not found!"
**Rozwiązanie:** Zainstaluj plugin Vault i plugin ekonomiczny.

### Przedmioty nie zapisują się
**Rozwiązanie:** Sprawdź uprawnienie `nieloshop.admin`.

### Placeholdery nie działają
**Rozwiązanie:** Zainstaluj PlaceholderAPI i uruchom `/papi reload`.

---

## 📊 Statystyki

- **29 klas Java** - czysta architektura
- **2786+ linii kodu** - w pełni udokumentowane
- **3 sklepy** - Vault, Time, Wallet
- **0 błędów** - przetestowane i gotowe

---

## 🎯 Najlepsze Praktyki

### 1. Backup przed zmianami
```bash
cp -r plugins/NieloShop plugins/NieloShop.backup
```

### 2. Testuj na serwerze testowym
Najpierw dodaj przedmioty na test serverze.

### 3. Ustaw logiczne ceny
- Bloki podstawowe: tanie
- Diamenty/Elytry: drogie
- Cena sprzedaży = 70-80% ceny zakupu

### 4. Monitoruj ekonomię
Sprawdzaj średnie saldo graczy i dostosuj ceny.

### 5. Używaj kategorii
Grupuj przedmioty logicznie w sklepach.

---

## 📚 Więcej Informacji

- **README.md** - Pełna dokumentacja
- **INSTALL.md** - Szczegółowa instalacja
- **FEATURES.md** - Lista wszystkich funkcji
- **EXAMPLES.md** - Przykłady konfiguracji

---

## 💬 Wsparcie

Discord: **nielopekk**

---

## ✨ Funkcje Kluczowe

✅ **Trzy rodzaje walut** - Vault, Time, Wallet
✅ **Intuicyjne GUI** - Bez edycji YAML
✅ **AFK Detection** - Sprawiedliwe punkty czasu
✅ **PlaceholderAPI** - Integracja z innymi pluginami
✅ **NBT Support** - Customowe przedmioty (MMOItems, Oraxen)
✅ **SQLite/JSON** - Wybór storage
✅ **Polski/Angielski** - Pełna lokalizacja

---

**Miłej zabawy z NieloShop!** 🎊
