
# 💊 Smart Health Console App — Java Project

> **"Empowering Digital Healthcare — Right from Your Console."**  
> Developed by ❤️ **Roshan Kushwaha** — *for GUVI & Galgotias University*

---

## 🧠 Project Overview

**Smart Health Console App** is a **Java-based, file-driven healthcare management system** designed to simulate basic hospital operations such as:

- 👥 Patient registration & updates  
- 👨‍⚕️ Doctor management  
- 📅 Appointment scheduling  
- 🛡 Input validation & error handling  
- 💾 Persistent storage using `.txt` files

Perfect for beginners exploring:
- Object-Oriented Design
- DAO-Service Architecture
- Real-world Java Mini Projects

---

## 🎯 Features at a Glance

| Feature          | Description |
|------------------|-------------|
| 👤 Patient CRUD   | Add, update, list & delete patients with validations |
| 👨‍⚕️ Doctor CRUD   | Manage doctor profiles and specializations |
| 📅 Appointment Booking | Schedule, update, cancel & list doctor-patient appointments |
| ✅ Input Validation | Validates name, age, gender, contact, date, time |
| 💾 File I/O | Uses plain text files for persistent data storage |
| 🧼 Clean Code | Modular design (Model, DAO, Service, Validator) |
| 💻 Console UI | User-friendly console menu-driven system |

---

## 🧪 Sample Console UI Output

```
=== Smart Health Console App ===
1. Patient Management
2. Doctor Management
3. Appointment Management
4. Exit
Choose an option: 1

--- Patient Management ---
1. Add Patient
2. Update Patient
3. Delete Patient
4. List Patients
5. Back
Choose an option: 1
```

---

## 🛠 Sample Code Snippet (UI Menu)

```java
System.out.println("=== Smart Health Console App ===");
System.out.println("1. Patient Management");
System.out.println("2. Doctor Management");
System.out.println("3. Appointment Management");
System.out.println("4. Exit");
System.out.print("Choose an option: ");
String choice = sc.nextLine();
```

---

## 🗂 Folder Structure

```
SmartHealthApp/
├── src/
│   └── SmartHealthApp.java         # Complete Java source
├── data/
│   ├── patients.txt                # Patient storage
│   ├── doctors.txt                 # Doctor storage
│   └── appointments.txt            # Appointments storage
├── README.md
├── .gitignore
└── LICENSE (optional)
```

---

## 🚀 How to Run

1. **Clone the repository**
```bash
git clone https://github.com/your-username/SmartHealthApp.git
cd SmartHealthApp
```

2. **Compile the code**
```bash
javac src/SmartHealthApp.java
```

3. **Run the application**
```bash
java -cp src SmartHealthApp
```

---

## ✨ Planned Enhancements

- [ ] Search functionality (by name/specialization)
- [ ] Export reports to CSV
- [ ] GUI interface (JavaFX/Swing)
- [ ] Doctor availability slots

---

## 🏷 Technologies Used

- Java 8+
- File I/O (BufferedReader/Writer)
- Collections API (List, Map)
- OOP Principles (Inheritance, Encapsulation)
- DAO-Service Architecture

---

## 👨‍🎓 Author

**Roshan Kushwaha**  
_Galgotias University | GUVI Java Project_  
📧 [YourEmail@example.com](mailto:YourEmail@example.com)  
🌐 [GitHub Profile](https://github.com/your-username)

---

## 📜 License

This project is licensed under the [MIT License](LICENSE)

> *"Code with purpose. Heal with logic."* 💖

