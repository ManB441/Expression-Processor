# Expression Processor – Notation Conversion System

A JavaFX‑based application for converting and evaluating mathematical expressions between **infix**, **postfix**, and **prefix** notations.  
Supports both conventional arithmetic operators (`+`, `-`, `*`, `/`, `^`, `%`, `!`) and **user‑defined operators/operands** via external configuration files.

---

## Features

- **Convert between notations**  
  Infix → Postfix / Prefix  
  Postfix → Infix / Prefix  
  Prefix → Infix / Postfix  

- **Evaluate expressions**  
  Supports numbers, parentheses, and operators with precedence.  
  Handles factorial (`!`) and power (`^`).

- **Two operation modes**  
  - *Conventional*: fixed operators and precedence (+, -, *, /, ^, %, !).  
  - *Custom*: operators and precedence rules loaded from text files.

- **File‑based configuration**  
  - **Language file** – defines allowed operands (e.g., `A B C D`).  
  - **Precedence file** – defines custom operators and their priority (e.g., `+ 1`, `* 2`).

- **Graphical user interface (JavaFX)**  
  - Choose notation type, form, and operation.  
  - Load / save language and precedence files.  
  - Generate report files with conversion results.  
  - Clear all fields with one button.

---

## Technologies

- Java 8+  
- JavaFX (for GUI)  
- Custom linked‑list stack and double‑linked list  
- File I/O (FileChooser, PrintWriter, Files)

---

## How to Run

1. **Clone or download** the project.
2. Open the project in your IDE (Eclipse / IntelliJ / NetBeans).  
   Make sure JavaFX is properly configured.
3. Run the `Main` class (contains `Application.launch`).
4. The main window will open.

> *No external libraries are required.*

---

## Usage Example (Conventional Mode)

| Expression (Infix)        | Operation           | Result                     |
|---------------------------|---------------------|----------------------------|
| `3 + 4 * 2`               | Infix → Postfix     | `3 4 2 * +`                |
| `( 1 + 2 ) * 3`           | Infix → Prefix      | `* + 1 2 3`                |
| `5 2 3 * +`               | Postfix → Infix     | `( 5 + ( 2 * 3 ) )`        |
| `* + 1 2 3`               | Prefix → Infix      | `( ( 1 + 2 ) * 3 )`        |
| `3 4 2 * +`               | Evaluate (Postfix)  | `11`                       |

**Important:**  
Expressions must be **space‑separated**.  
- Correct: `3 + 4 * 2`  
- Incorrect: `3+4*2`

Parentheses are allowed only in infix form.

---

## Custom Mode – Setup

1. **Language file** (operands)  
   Create a `.txt` file with operands separated by spaces.  
   Example: `A B C D`  
   Load it using *“Load Language File”*.

2. **Precedence file** (custom operators)  
   Each line: `operator priority`  
   Example:  
