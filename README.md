# Pixel Manipulator

A Java-based image editor with both a command-line interface (CLI) and a graphical user interface (GUI). Supports loading, transforming, and saving images in multiple formats.

---

## Features

- Load and save images in **PPM, JPEG, PNG, and BMP** formats
- Apply image operations:
  - **Brighten** / **Darken**
  - **Greyscale** (Red, Green, Blue, Luma, Intensity, Value channels)
- Interactive **Swing GUI** with image preview and operation controls
- Scriptable **CLI** for batch processing

---

## Project Structure

```
pixel-manipulator/
├── src/
│   ├── ImageModifier.java          # Entry point
│   ├── controller/                 # CLI and GUI controllers
│   ├── commands/                   # Command pattern implementations
│   │   ├── load/                   # Format-specific load strategies
│   │   ├── save/                   # Format-specific save strategies
│   │   └── operations/             # Brighten, darken, greyscale commands
│   ├── model/
│   │   ├── image/                  # Image and Pixel representations
│   │   ├── operations/             # Image transformation logic
│   │   ├── read/                   # PPM and binary image readers
│   │   └── write/                  # PPM and binary image writers
│   ├── storage/                    # In-memory image storage (HashMap)
│   └── view/                       # Swing GUI components and panels
└── test/
    ├── TestModel.java
    ├── TestLoadAndSaveCommands.java
    └── TestOperationsCommands.java
```

---

## Getting Started

### Requirements

- Java 11 or higher
- A Java IDE (IntelliJ, Eclipse) or `javac` / `java` on your PATH

### Compile

```bash
javac -d out -sourcepath src src/ImageModifier.java
```

### Run — GUI Mode

Launch with no arguments to open the graphical interface:

```bash
java -cp out ImageModifier
```

### Run — CLI Mode

Pass a script file as an argument to run commands in batch:

```bash
java -cp out ImageModifier script.txt
```

---

## CLI Usage

Commands are entered one per line. Syntax:

| Command | Syntax | Description |
|---|---|---|
| Load | `load <filepath> <name>` | Load an image from disk |
| Save | `save <filepath> <name>` | Save a stored image to disk |
| Brighten | `brighten <value> <name> <dest>` | Increase brightness by value |
| Darken | `darken <value> <name> <dest>` | Decrease brightness by value |
| Red Greyscale | `red-component <name> <dest>` | Greyscale using red channel |
| Green Greyscale | `green-component <name> <dest>` | Greyscale using green channel |
| Blue Greyscale | `blue-component <name> <dest>` | Greyscale using blue channel |
| Luma | `luma-component <name> <dest>` | Weighted greyscale (0.2126R + 0.7152G + 0.0722B) |
| Intensity | `intensity-component <name> <dest>` | Average of RGB channels |
| Value | `value-component <name> <dest>` | Max of RGB channels |

### Example Script

```
load ./images/cat.png cat
brighten 50 cat cat-bright
luma-component cat cat-luma
save ./output/cat-bright.png cat-bright
save ./output/cat-luma.png cat-luma
```

---

## Supported Formats

| Format | Load | Save |
|---|---|---|
| PPM (P3) | Yes | Yes |
| JPEG | Yes | Yes |
| PNG | Yes | Yes |
| BMP | Yes | Yes |

---

## Architecture

The project follows an **MVC architecture** combined with the **Command** and **Strategy** patterns.

- **Model** — Image data (`ImageImpl`, `Pixel`), transformation logic (`AbstractOperations` hierarchy), and format-specific I/O strategies
- **View** — Swing GUI (`MainViewWindow`) with modular panels for loading, displaying, and operating on images
- **Controller** — `CommandController` (CLI) and `GUIController` (GUI) both route to `CommandMap`, which acts as a command registry
- **Commands** — Each operation is encapsulated as a command object implementing `ICommands`, keeping controllers thin and operations easily extensible

Adding a new image format requires only a new reader/writer strategy class and a one-line registration in `CommandMap`.

---

## Running Tests

```bash
javac -cp .:junit.jar -d out test/*.java src/**/*.java
java -cp .:junit.jar:out org.junit.runner.JUnitCore TestModel TestLoadAndSaveCommands TestOperationsCommands
```

> **Note:** Ensure test image assets are present at the paths expected by the test files before running.

---

## Author

Drew Hill
