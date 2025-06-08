# Smart Health Console Application

A comprehensive healthcare management system built in Java for managing patients, doctors, and appointments through a console-based interface.

## 📋 Project Overview

The Smart Health Console Application is a robust healthcare management system designed to streamline the management of patients, doctors, and appointments in a healthcare facility. Built with Java using object-oriented programming principles, it provides a user-friendly console interface for healthcare administrators to efficiently manage medical data.

## 🎯 Features

### Patient Management
- **Add Patient**: Register new patients with personal details
- **Update Patient**: Modify existing patient information
- **Delete Patient**: Remove patient records from the system
- **List Patients**: View all registered patients with their details

### Doctor Management
- **Add Doctor**: Register new doctors with specialization
- **Update Doctor**: Modify existing doctor information
- **Delete Doctor**: Remove doctor records from the system
- **List Doctors**: View all registered doctors with their specializations

### Appointment Management
- **Schedule Appointment**: Book appointments between patients and doctors
- **Update Appointment**: Reschedule existing appointments
- **Cancel Appointment**: Remove appointments from the system
- **List Appointments**: View all scheduled appointments with patient and doctor details

### Data Validation
- Name validation (alphabetic characters only, 2-50 characters)
- Contact number validation (10-digit numbers)
- Gender validation (Male/Female/Other)
- Age validation (1-119 years)
- Date validation (YYYY-MM-DD format)
- Time validation (HH:MM format)

### Data Persistence
- File-based data storage using CSV format
- Automatic data directory creation
- Persistent storage across application sessions

## 🏗️ Architecture

The application follows a layered architecture pattern:

```
├── Model Classes (Patient, Doctor, Appointment)
├── Data Access Objects (PatientDAO, DoctorDAO, AppointmentDAO)
├── Service Layer (PatientService, DoctorService, AppointmentService)
├── Utility Classes (InputValidator)
└── Main Application (Console Interface)
```

### Key Components:

- **Model Classes**: Represent core entities with proper encapsulation
- **DAO Pattern**: Handles data persistence and retrieval operations
- **Service Layer**: Contains business logic and validation
- **Input Validation**: Ensures data integrity and consistency
- **Console Interface**: Provides user-friendly menu-driven interaction

## 💻 Code Structure

### Model Classes
```java
// Patient entity with complete CRUD operations
static class Patient {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String contact;
    // Getters, setters, and utility methods
}
```

### Service Layer Example
```java
public void addPatient(String name, int age, String gender, String contact) {
    // Input validation
    if (!InputValidator.isValidName(name)) {
        System.out.println("Invalid name.");
        return;
    }
    // Business logic and data persistence
    int id = dao.getNextId();
    Patient patient = new Patient(id, name, age, gender, contact);
    dao.addPatient(patient);
    System.out.println("Patient added successfully with ID: " + id);
}
```

### Data Validation
```java
static class InputValidator {
    public static boolean isValidName(String name) {
        return name != null && name.matches("[A-Za-z ]{2,50}");
    }
    
    public static boolean isValidContact(String contact) {
        return contact != null && contact.matches("[0-9]{10}");
    }
}
```

## 🚀 Getting Started

### Prerequisites
- Java JDK 8 or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code) or command line

### Installation & Setup

1. **Clone or Download the Project**
   ```bash
   # Create project directory
   mkdir SmartHealthApp
   cd SmartHealthApp
   ```

2. **Create the Java File**
   - Copy the provided code into `SmartHealthApp.java`

3. **Create Data Directory**
   ```bash
   mkdir data
   ```

4. **Compile the Application**
   ```bash
   javac SmartHealthApp.java
   ```

5. **Run the Application**
   ```bash
   java SmartHealthApp
   ```

## 📱 Console Interface Examples

### Main Menu
```
=== Smart Health Console App ===
1. Patient Management
2. Doctor Management
3. Appointment Management
4. Exit
Choose an option: 
```

### Patient Management Example
```
--- Patient Management ---
1. Add Patient
2. Update Patient
3. Delete Patient
4. List Patients
5. Back
Choose an option: 1

Name: John Doe
Age: 30
Gender (Male/Female/Other): Male
Contact (10 digits): 9876543210
Patient added successfully with ID: 1
```

### Doctor Management Example
```
--- Doctor Management ---
1. Add Doctor
2. Update Doctor
3. Delete Doctor
4. List Doctors
5. Back
Choose an option: 1

Name: Dr. Sarah Wilson
Specialization: Cardiology
Contact (10 digits): 9876543211
Doctor added successfully with ID: 1
```

### Appointment Scheduling Example
```
--- Appointment Management ---
1. Schedule Appointment
2. Update Appointment
3. Cancel Appointment
4. List Appointments
5. Back
Choose an option: 1

Patient ID: 1
Doctor ID: 1
Date (YYYY-MM-DD): 2024-12-15
Time (HH:MM): 10:30
Appointment scheduled successfully with ID: 1
```

### Sample Output - List Appointments
```
Appointment List:
ID: 1, Patient: John Doe, Doctor: Dr. Sarah Wilson, Date: 2024-12-15, Time: 10:30
ID: 2, Patient: Jane Smith, Doctor: Dr. Mike Johnson, Date: 2024-12-16, Time: 14:00
```

## 📁 File Structure

```
SmartHealthApp/
│
├── SmartHealthApp.java          # Main application file
├── data/                        # Data storage directory
│   ├── patients.txt            # Patient records
│   ├── doctors.txt             # Doctor records
│   └── appointments.txt        # Appointment records
│
└── README.md                   # Project documentation
```

## 🔧 Technical Specifications

- **Language**: Java
- **Architecture**: Layered Architecture with DAO Pattern
- **Data Storage**: File-based (CSV format)
- **Interface**: Console-based
- **Design Patterns**: DAO, Service Layer, Factory Pattern
- **Validation**: Comprehensive input validation
- **Error Handling**: Graceful error handling with user feedback

## 📊 Data Format

### Patient Data (patients.txt)
```
1,John Doe,30,Male,9876543210
2,Jane Smith,25,Female,9876543211
```

### Doctor Data (doctors.txt)
```
1,Dr. Sarah Wilson,Cardiology,9876543212
2,Dr. Mike Johnson,Neurology,9876543213
```

### Appointment Data (appointments.txt)
```
1,1,1,2024-12-15,10:30
2,2,2,2024-12-16,14:00
```

## 🛡️ Input Validation Rules

| Field | Validation Rule |
|-------|----------------|
| Name | 2-50 alphabetic characters and spaces |
| Contact | Exactly 10 digits |
| Gender | Male, Female, or Other (case-insensitive) |
| Age | 1-119 years |
| Date | YYYY-MM-DD format |
| Time | HH:MM format |
| Specialization | Minimum 2 characters |

## ⭐ Key Innovative Features

### 1. **Smart Cross-Reference Integration**
One of the standout features of this application is the intelligent cross-referencing system between patients, doctors, and appointments. When displaying appointment lists, the system automatically fetches and displays patient names and doctor names instead of just showing IDs, providing a more user-friendly experience.

**Implementation Highlight:**
```java
public void listAppointments() {
    List<Appointment> appointments = dao.getAllAppointments();
    for (Appointment a : appointments) {
        Patient p = patientDAO.getPatientById(a.getPatientId());
        Doctor d = doctorDAO.getDoctorById(a.getDoctorId());
        System.out.println("ID: " + a.getId() + 
            ", Patient: " + (p != null ? p.getName() : "Unknown") +
            ", Doctor: " + (d != null ? d.getName() : "Unknown") +
            ", Date: " + a.getDate() + ", Time: " + a.getTime());
    }
}
```

**Benefits:**
- Enhanced user experience with meaningful data display
- Maintains data integrity through proper referential checks
- Handles edge cases when referenced entities might not exist
- Demonstrates advanced object-oriented design principles

### 2. **Comprehensive Input Validation Framework**
The application features a robust, centralized validation system that ensures data integrity across all modules. The `InputValidator` class implements regex-based validation with specific business rules for healthcare data.

**Implementation Highlight:**
```java
static class InputValidator {
    // Name validation: Only alphabetic characters and spaces, 2-50 chars
    public static boolean isValidName(String name) {
        return name != null && name.matches("[A-Za-z ]{2,50}");
    }
    
    // Contact validation: Exactly 10 digits for phone numbers
    public static boolean isValidContact(String contact) {
        return contact != null && contact.matches("[0-9]{10}");
    }
    
    // Date validation: Strict YYYY-MM-DD format
    public static boolean isValidDate(String date) {
        return date != null && date.matches("\\d{4}-\\d{2}-\\d{2}");
    }
}
```

**Advanced Features:**
- **Regex Pattern Matching**: Ensures data format consistency
- **Business Rule Enforcement**: Age limits (1-119), gender options, contact format
- **Centralized Validation**: Single source of truth for all validation rules
- **Graceful Error Handling**: User-friendly error messages for invalid inputs
- **Preventive Data Corruption**: Stops invalid data from entering the system

**Real-world Application:**
This validation framework mimics enterprise-level applications where data integrity is crucial, especially in healthcare systems where incorrect data could have serious consequences.

## 🚀 Future Enhancements

- Database integration (MySQL, PostgreSQL)
- GUI interface using JavaFX or Swing
- REST API development
- Advanced search and filtering
- Report generation
- User authentication and authorization
- Appointment conflict detection
- Email/SMS notifications

## 📝 Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is part of an academic assignment and is intended for educational purposes.

## 👨‍💻 Author

**Roshan Kushwaha**
- Institution: Galgotias University
- Program: B.Tech II Semester
- Course: Java Programming Project
- Platform: GUVI

## 🙏 Acknowledgments

- **Galgotias University** for providing the educational framework and resources
- **GUVI** for the project guidance and learning platform
- **Java Community** for extensive documentation and best practices
- **Faculty Members** for their continuous support and mentorship

## 📞 Support

For any queries or support regarding this project, please reach out through:
- University email or student portal
- GUVI platform discussion forums
- Project repository issues section

---

**Note**: This project is developed as part of the Java Programming curriculum at Galgotias University under the GUVI learning platform. It demonstrates the practical application of object-oriented programming concepts, file handling, and software architecture principles in Java.

---

*Built with ❤️ for healthcare management and Java learning*
