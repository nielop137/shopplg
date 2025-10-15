# NieloShop - Lista Funkcji

## ✅ Zaimplementowane Funkcje

### 🏪 System Sklepów

#### Trzy Typy Sklepów
- ✅ **Vault Shop** - płatność pieniędzmi z ekonomii Vault
- ✅ **Time Shop** - płatność punktami czasu
- ✅ **Wallet Shop** - płatność wirtualną walutą pluginu

#### Funkcje Sklepów
- ✅ GUI z paginacją (45 przedmiotów na stronę)
- ✅ Obsługa kupowania przedmiotów (LPM)
- ✅ Obsługa sprzedawania przedmiotów (PPM)
- ✅ Dynamiczne ceny dla stacków
- ✅ Przyciski nawigacji (poprzednia/następna strona)
- ✅ Przycisk zamknięcia GUI
- ✅ Kolorowe tytuły GUI (konfigurowalne)
- ✅ Animacje (konfigurowalne on/off)

### 💼 System Portfela (Wallet)

- ✅ Niezależna wirtualna waluta od Vault
- ✅ Komenda `/portfel` - sprawdź saldo
- ✅ Komenda `/portfel nadaj <gracz> <ilość>` - dodaj walutę
- ✅ Komenda `/portfel zabierz <gracz> <ilość>` - zabierz walutę
- ✅ Komenda `/portfel ustaw <gracz> <ilość>` - ustaw saldo
- ✅ Startowy balans dla nowych graczy (konfigurowalny)
- ✅ Placeholder: `%nieloshop_wallet%`

### ⏱️ System Punktów Czasowych

#### Podstawowe Funkcje
- ✅ Automatyczne przyznawanie punktów za czas online
- ✅ Konfigurowalne punkty na interwał
- ✅ Konfigurowalny interwał w sekundach
- ✅ Komenda `/czas` - pokaż czas gry i punkty
- ✅ Śledzenie całkowitego czasu gry
- ✅ Placeholder: `%nieloshop_time_points%`
- ✅ Placeholder: `%nieloshop_playtime%`

#### Wykrywanie AFK
- ✅ Detekcja ruchu gracza
- ✅ Konfigurowalny próg AFK (domyślnie 60s)
- ✅ Zatrzymanie przyznawania punktów gdy gracz AFK
- ✅ Opcja wyłączenia detekcji AFK

### 🛠️ System Dodawania Przedmiotów

#### Intuicyjny Interfejs
- ✅ Komenda `/nieloshop add <vault|time|wallet>`
- ✅ Kliknięcie przedmiotu w ekwipunku
- ✅ Wprowadzanie cen przez chat
- ✅ GUI potwierdzenia z podglądem
- ✅ Możliwość anulowania
- ✅ Brak edycji YAML

#### Serializacja Przedmiotów
- ✅ Pełne zachowanie NBT data
- ✅ Kompatybilność z customowymi przedmiotami
- ✅ Wsparcie dla MMOItems
- ✅ Wsparcie dla Oraxen
- ✅ Wsparcie dla ItemsAdder
- ✅ Zachowanie enchanty, lore, display name
- ✅ Zachowanie custom model data
- ✅ Zachowanie item flags

### 💾 System Przechowywania Danych

#### Dwa Typy Storage
- ✅ **SQLite** (domyślny)
  - Plik: `data.db`
  - Automatyczne tworzenie tabeli
  - Connection pooling
  - Bezpieczne zapisy

- ✅ **JSON** (opcjonalny)
  - Plik: `playerdata.json`
  - Pretty printing
  - Łatwy debugging
  - Human-readable

#### Zarządzanie Danymi
- ✅ Automatyczne ładowanie przy starcie
- ✅ Automatyczne zapisywanie przy wyłączeniu
- ✅ Cache w pamięci RAM
- ✅ Komenda `/nieloshop save` - ręczny zapis
- ✅ Zapis danych gracza przy wyjściu
- ✅ Ładowanie danych przy wejściu

### 🎨 Konfiguracja i Personalizacja

#### Pliki Konfiguracyjne
- ✅ `config.yml` - główna konfiguracja
- ✅ `messages.yml` - wszystkie wiadomości
- ✅ `shops.yml` - przedmioty w sklepach
- ✅ Komenda `/nieloshop reload` - przeładowanie

#### Formatowanie Waluty
- ✅ Krótki format (1k, 2.3M, 5B, 12T)
- ✅ Konfigurowalne symbole (k, M, B, T)
- ✅ Konfigurowalna ilość miejsc po przecinku
- ✅ Możliwość wyłączenia krótkiego formatu

#### Wiadomości
- ✅ Pełne wsparcie kodów kolorów `&`
- ✅ System placeholderów w wiadomościach
- ✅ Polski język (domyślny)
- ✅ Angielski język (w pliku)
- ✅ Łatwa translacja na inne języki

### 🔐 System Uprawnień

#### Podstawowe Uprawnienia
- ✅ `nieloshop.use` - otwieranie sklepów
- ✅ `nieloshop.admin` - komendy admina
- ✅ `nieloshop.reload` - reload konfiguracji

#### Uprawnienia Portfela
- ✅ `nieloshop.portfel.use` - sprawdzanie własnego portfela
- ✅ `nieloshop.portfel.give` - dodawanie waluty
- ✅ `nieloshop.portfel.take` - zabieranie waluty
- ✅ `nieloshop.portfel.set` - ustawianie salda

### 🔌 Integracje

#### Vault (Wymagane)
- ✅ Integracja z ekonomią
- ✅ Automatyczne wykrywanie
- ✅ Obsługa transakcji
- ✅ Sprawdzanie salda

#### PlaceholderAPI (Opcjonalne)
- ✅ Rejestracja expansion
- ✅ 3 placeholdery (wallet, time_points, playtime)
- ✅ Automatyczna integracja
- ✅ Format waluty w placeholderach

### 📊 Funkcje Techniczne

#### Architektura
- ✅ Czysta architektura MVC
- ✅ Separation of concerns
- ✅ Dependency injection
- ✅ Singleton pattern dla managera
- ✅ Factory pattern dla storage

#### Bezpieczeństwo
- ✅ SQL injection prevention
- ✅ Walidacja danych wejściowych
- ✅ Bezpieczne transakcje
- ✅ Error handling
- ✅ Try-catch bloki

#### Wydajność
- ✅ Cache w pamięci RAM
- ✅ Asynchroniczne zapisywanie
- ✅ Batch operations
- ✅ Optymalne query SQL
- ✅ Minimalizacja I/O

#### Kompatybilność
- ✅ Minecraft 1.16.5 - 1.21+
- ✅ Spigot API
- ✅ Paper API
- ✅ Java 17+
- ✅ Wsteczna kompatybilność

### 📝 Dokumentacja

- ✅ Javadoc w każdej klasie
- ✅ Komentarze w kodzie
- ✅ README.md z instrukcjami
- ✅ INSTALL.md z instalacją
- ✅ FEATURES.md (ten plik)
- ✅ Przykładowe konfiguracje

### 🎯 Dodatkowe Funkcje

#### Komendy
- ✅ Tab completion dla wszystkich komend
- ✅ Aliasy komend (`/nshop`, `/ns`)
- ✅ Help message z listą komend
- ✅ Walidacja argumentów
- ✅ Przyjazne komunikaty błędów

#### GUI
- ✅ Glass pane jako wypełnienie
- ✅ Ikony z custom lore
- ✅ Hover tooltips
- ✅ Click handling
- ✅ Inventory protection

#### Event Handling
- ✅ Player join/quit
- ✅ Player movement (AFK detection)
- ✅ Inventory click
- ✅ Chat input
- ✅ Graceful shutdown

## 📈 Statystyki Projektu

- **Liczba plików Java:** 29
- **Liczba pakietów:** 7
- **Linie kodu:** ~3000+
- **Pliki konfiguracyjne:** 3
- **Wspierane wersje MC:** 1.16.5 - 1.21+

## 🚀 Gotowe do Produkcji

Plugin jest w pełni funkcjonalny i gotowy do użycia na serwerze produkcyjnym:

✅ Wszystkie główne funkcje zaimplementowane
✅ System błędów i logowania
✅ Bezpieczne transakcje
✅ Optymalizacja wydajności
✅ Pełna dokumentacja
✅ Konfigurowalne ustawienia
✅ Multi-currency support
✅ AFK detection
✅ PlaceholderAPI support
✅ Custom items support

## 🔮 Możliwe Przyszłe Rozszerzenia

Funkcje które można dodać w przyszłości:
- [ ] GUI do masowej edycji przedmiotów
- [ ] Import/export sklepów do plików
- [ ] Rabaty czasowe (Happy Hour)
- [ ] Limity zakupów na gracza
- [ ] Historia transakcji
- [ ] Top lista (najbogatsi gracze)
- [ ] Zniżki dla rangów/permisji
- [ ] Multi-świat support
- [ ] API dla innych pluginów
- [ ] WebAPI / REST endpoint
