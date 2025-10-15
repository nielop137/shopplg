# NieloShop - Build Instructions

## Prerequisites

### Required Software
- **Java Development Kit (JDK) 17 or higher**
  - Download: https://adoptium.net/
  - Verify: `java -version`

- **Apache Maven 3.6 or higher**
  - Download: https://maven.apache.org/download.cgi
  - Verify: `mvn -version`

- **Git** (optional, for version control)
  - Download: https://git-scm.com/
  - Verify: `git --version`

---

## Building from Source

### Step 1: Clone/Download Repository

#### Option A: Using Git
```bash
git clone <repository-url>
cd NieloShop
```

#### Option B: Manual Download
1. Download source code
2. Extract to folder
3. Open terminal in folder

### Step 2: Build with Maven

```bash
# Clean previous builds and compile
mvn clean package
```

This will:
1. Clean the `target/` directory
2. Download dependencies
3. Compile source code
4. Run resource filtering
5. Create shaded JAR
6. Output: `target/NieloShop-1.0.0.jar`

### Step 3: Verify Build

```bash
# Check if JAR exists
ls -lh target/NieloShop-1.0.0.jar
```

Expected output:
```
-rw-r--r-- 1 user user ~100K Oct 15 19:00 target/NieloShop-1.0.0.jar
```

---

## Build Variants

### Development Build
```bash
mvn clean package
```
Standard build for testing.

### Release Build
```bash
mvn clean package -DskipTests
```
Skip tests for faster build.

### Clean Install
```bash
mvn clean install
```
Install to local Maven repository.

---

## Troubleshooting

### Problem: "mvn: command not found"
**Solution:** Maven not installed or not in PATH.
- Install Maven: https://maven.apache.org/install.html
- Add to PATH environment variable

### Problem: "JAVA_HOME not set"
**Solution:** Set JAVA_HOME environment variable.

**Windows:**
```cmd
set JAVA_HOME=C:\Program Files\Java\jdk-17
```

**Linux/Mac:**
```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
```

### Problem: "Failed to download dependencies"
**Solution:** Check internet connection or use Maven offline mode.
```bash
mvn clean package -o
```

### Problem: "Compilation failure"
**Solution:** Ensure Java 17+ is being used.
```bash
mvn -version  # Check Maven Java version
java -version # Check system Java version
```

---

## Project Structure

```
NieloShop/
├── src/
│   └── main/
│       ├── java/              # Source code
│       └── resources/         # Configuration files
├── target/                    # Build output (generated)
│   └── NieloShop-1.0.0.jar   # Compiled plugin
├── pom.xml                    # Maven configuration
└── *.md                       # Documentation
```

---

## Maven Configuration

### Dependencies
All dependencies are automatically downloaded by Maven:

- **Spigot API** (1.16.5-R0.1-SNAPSHOT) - provided
- **Vault API** (1.7) - provided
- **PlaceholderAPI** (2.11.5) - provided

### Repositories
- SpigotMC: https://hub.spigotmc.org/nexus/
- PlaceholderAPI: https://repo.extendedclip.com/
- JitPack: https://jitpack.io/

---

## IDE Setup

### IntelliJ IDEA
1. Open IntelliJ IDEA
2. File → Open → Select `pom.xml`
3. Import as Maven project
4. Wait for dependencies download
5. Build: Build → Build Project

### Eclipse
1. Open Eclipse
2. File → Import → Maven → Existing Maven Projects
3. Select project folder
4. Wait for dependencies download
5. Build: Project → Build Project

### Visual Studio Code
1. Install "Java Extension Pack"
2. Install "Maven for Java"
3. Open project folder
4. Trust project
5. Build: Terminal → `mvn clean package`

---

## Continuous Integration

### GitHub Actions (example)
```yaml
name: Build

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Build with Maven
        run: mvn clean package
```

---

## Version Management

### Update Version
Edit `pom.xml`:
```xml
<version>1.0.1</version>
```

Also update in `plugin.yml`:
```yaml
version: '1.0.1'
```

### Create Release
```bash
# Tag version
git tag v1.0.0
git push origin v1.0.0

# Build release
mvn clean package

# Upload target/NieloShop-1.0.0.jar to releases
```

---

## Build Time

**Typical build time:**
- First build: ~30-60 seconds (downloading dependencies)
- Subsequent builds: ~5-10 seconds

---

## Output Details

### Generated Files
```
target/
├── classes/                   # Compiled .class files
├── generated-sources/         # Generated sources
├── maven-archiver/           # Maven metadata
├── maven-status/             # Build status
├── NieloShop-1.0.0.jar       # Final plugin JAR
└── original-NieloShop-1.0.0.jar  # Pre-shade JAR
```

### JAR Contents
```
NieloShop-1.0.0.jar
├── pl/nielopekk/nieloshop/   # Compiled classes
│   ├── NieloShop.class
│   ├── commands/
│   ├── gui/
│   ├── listeners/
│   ├── managers/
│   ├── models/
│   ├── storage/
│   └── utils/
├── plugin.yml                 # Plugin metadata
├── config.yml                 # Default config
├── messages.yml               # Default messages
└── shops.yml                  # Default shops
```

---

## Quality Checks

### Code Style
```bash
# Format code (if checkstyle plugin added)
mvn checkstyle:check
```

### Dependencies
```bash
# List dependencies
mvn dependency:tree

# Check for updates
mvn versions:display-dependency-updates
```

### Size Analysis
```bash
# Analyze JAR size
du -h target/NieloShop-1.0.0.jar
```

---

## Development Build

For development with auto-reload:

1. Build plugin:
```bash
mvn clean package
```

2. Copy to test server:
```bash
cp target/NieloShop-1.0.0.jar ~/testserver/plugins/
```

3. Reload in-game:
```
/reload confirm
```

**Note:** Use `/reload` only for testing. For production, restart server.

---

## Support

If you encounter build issues:
1. Check Maven version: `mvn -version`
2. Check Java version: `java -version`
3. Clean Maven cache: `mvn clean`
4. Delete `.m2/repository` cache
5. Contact: Discord **nielopekk**

---

## Success!

After successful build:
```
[INFO] BUILD SUCCESS
[INFO] Total time:  10.123 s
[INFO] Finished at: 2025-10-15T19:00:00+00:00
```

Your plugin is ready: `target/NieloShop-1.0.0.jar` 🎉
