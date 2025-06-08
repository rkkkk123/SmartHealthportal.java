import java.io.*;
import java.util.*;

public class SmartHealthApp {

    // ==============================
    // Model Classes
    // ==============================
    static class Patient {
        private int id;
        private String name;
        private int age;
        private String gender;
        private String contact;

        public Patient(int id, String name, int age, String gender, String contact) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.contact = contact;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public int getAge() { return age; }
        public String getGender() { return gender; }
        public String getContact() { return contact; }

        public void setName(String name) { this.name = name; }
        public void setAge(int age) { this.age = age; }
        public void setGender(String gender) { this.gender = gender; }
        public void setContact(String contact) { this.contact = contact; }

        @Override
        public String toString() {
            return id + "," + name + "," + age + "," + gender + "," + contact;
        }

        public static Patient fromString(String line) {
            String[] parts = line.split(",");
            return new Patient(
                    Integer.parseInt(parts[0]),
                    parts[1],
                    Integer.parseInt(parts[2]),
                    parts[3],
                    parts[4]
            );
        }
    }

    static class Doctor {
        private int id;
        private String name;
        private String specialization;
        private String contact;

        public Doctor(int id, String name, String specialization, String contact) {
            this.id = id;
            this.name = name;
            this.specialization = specialization;
            this.contact = contact;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public String getSpecialization() { return specialization; }
        public String getContact() { return contact; }

        public void setName(String name) { this.name = name; }
        public void setSpecialization(String specialization) { this.specialization = specialization; }
        public void setContact(String contact) { this.contact = contact; }

        @Override
        public String toString() {
            return id + "," + name + "," + specialization + "," + contact;
        }

        public static Doctor fromString(String line) {
            String[] parts = line.split(",");
            return new Doctor(
                    Integer.parseInt(parts[0]),
                    parts[1],
                    parts[2],
                    parts[3]
            );
        }
    }

    static class Appointment {
        private int id;
        private int patientId;
        private int doctorId;
        private String date;
        private String time;

        public Appointment(int id, int patientId, int doctorId, String date, String time) {
            this.id = id;
            this.patientId = patientId;
            this.doctorId = doctorId;
            this.date = date;
            this.time = time;
        }

        public int getId() { return id; }
        public int getPatientId() { return patientId; }
        public int getDoctorId() { return doctorId; }
        public String getDate() { return date; }
        public String getTime() { return time; }

        public void setDate(String date) { this.date = date; }
        public void setTime(String time) { this.time = time; }

        @Override
        public String toString() {
            return id + "," + patientId + "," + doctorId + "," + date + "," + time;
        }

        public static Appointment fromString(String line) {
            String[] parts = line.split(",");
            return new Appointment(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]),
                    parts[3],
                    parts[4]
            );
        }
    }

    // ==============================
    // Utility Class
    // ==============================
    static class InputValidator {
        public static boolean isValidName(String name) {
            return name != null && name.matches("[A-Za-z ]{2,50}");
        }

        public static boolean isValidContact(String contact) {
            return contact != null && contact.matches("[0-9]{10}");
        }

        public static boolean isValidGender(String gender) {
            return gender != null && (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female") || gender.equalsIgnoreCase("Other"));
        }

        public static boolean isValidAge(int age) {
            return age > 0 && age < 120;
        }

        public static boolean isValidSpecialization(String specialization) {
            return specialization != null && specialization.length() >= 2;
        }

        public static boolean isValidDate(String date) {
            return date != null && date.matches("\\d{4}-\\d{2}-\\d{2}");
        }

        public static boolean isValidTime(String time) {
            return time != null && time.matches("\\d{2}:\\d{2}");
        }
    }

    // ==============================
    // DAO Classes
    // ==============================
    static class PatientDAO {
        private static final String FILE_PATH = "data/patients.txt";

        public List<Patient> getAllPatients() {
            List<Patient> patients = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.trim().isEmpty())
                        patients.add(Patient.fromString(line));
                }
            } catch (IOException e) {
                // File may not exist yet, that's fine
            }
            return patients;
        }

        public void saveAllPatients(List<Patient> patients) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (Patient p : patients) {
                    bw.write(p.toString());
                    bw.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error saving patients: " + e.getMessage());
            }
        }

        public void addPatient(Patient patient) {
            List<Patient> patients = getAllPatients();
            patients.add(patient);
            saveAllPatients(patients);
        }

        public void updatePatient(Patient patient) {
            List<Patient> patients = getAllPatients();
            for (int i = 0; i < patients.size(); i++) {
                if (patients.get(i).getId() == patient.getId()) {
                    patients.set(i, patient);
                    break;
                }
            }
            saveAllPatients(patients);
        }

        public void deletePatient(int id) {
            List<Patient> patients = getAllPatients();
            patients.removeIf(p -> p.getId() == id);
            saveAllPatients(patients);
        }

        public Patient getPatientById(int id) {
            for (Patient p : getAllPatients()) {
                if (p.getId() == id) return p;
            }
            return null;
        }

        public int getNextId() {
            List<Patient> patients = getAllPatients();
            int maxId = 0;
            for (Patient p : patients) {
                if (p.getId() > maxId) maxId = p.getId();
            }
            return maxId + 1;
        }
    }

    static class DoctorDAO {
        private static final String FILE_PATH = "data/doctors.txt";

        public List<Doctor> getAllDoctors() {
            List<Doctor> doctors = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.trim().isEmpty())
                        doctors.add(Doctor.fromString(line));
                }
            } catch (IOException e) {
                // File may not exist yet, that's fine
            }
            return doctors;
        }

        public void saveAllDoctors(List<Doctor> doctors) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (Doctor d : doctors) {
                    bw.write(d.toString());
                    bw.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error saving doctors: " + e.getMessage());
            }
        }

        public void addDoctor(Doctor doctor) {
            List<Doctor> doctors = getAllDoctors();
            doctors.add(doctor);
            saveAllDoctors(doctors);
        }

        public void updateDoctor(Doctor doctor) {
            List<Doctor> doctors = getAllDoctors();
            for (int i = 0; i < doctors.size(); i++) {
                if (doctors.get(i).getId() == doctor.getId()) {
                    doctors.set(i, doctor);
                    break;
                }
            }
            saveAllDoctors(doctors);
        }

        public void deleteDoctor(int id) {
            List<Doctor> doctors = getAllDoctors();
            doctors.removeIf(d -> d.getId() == id);
            saveAllDoctors(doctors);
        }

        public Doctor getDoctorById(int id) {
            for (Doctor d : getAllDoctors()) {
                if (d.getId() == id) return d;
            }
            return null;
        }

        public int getNextId() {
            List<Doctor> doctors = getAllDoctors();
            int maxId = 0;
            for (Doctor d : doctors) {
                if (d.getId() > maxId) maxId = d.getId();
            }
            return maxId + 1;
        }
    }

    static class AppointmentDAO {
        private static final String FILE_PATH = "data/appointments.txt";

        public List<Appointment> getAllAppointments() {
            List<Appointment> appointments = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.trim().isEmpty())
                        appointments.add(Appointment.fromString(line));
                }
            } catch (IOException e) {
                // File may not exist yet, that's fine
            }
            return appointments;
        }

        public void saveAllAppointments(List<Appointment> appointments) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (Appointment a : appointments) {
                    bw.write(a.toString());
                    bw.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error saving appointments: " + e.getMessage());
            }
        }

        public void addAppointment(Appointment appointment) {
            List<Appointment> appointments = getAllAppointments();
            appointments.add(appointment);
            saveAllAppointments(appointments);
        }

        public void updateAppointment(Appointment appointment) {
            List<Appointment> appointments = getAllAppointments();
            for (int i = 0; i < appointments.size(); i++) {
                if (appointments.get(i).getId() == appointment.getId()) {
                    appointments.set(i, appointment);
                    break;
                }
            }
            saveAllAppointments(appointments);
        }

        public void deleteAppointment(int id) {
            List<Appointment> appointments = getAllAppointments();
            appointments.removeIf(a -> a.getId() == id);
            saveAllAppointments(appointments);
        }

        public Appointment getAppointmentById(int id) {
            for (Appointment a : getAllAppointments()) {
                if (a.getId() == id) return a;
            }
            return null;
        }

        public int getNextId() {
            List<Appointment> appointments = getAllAppointments();
            int maxId = 0;
            for (Appointment a : appointments) {
                if (a.getId() > maxId) maxId = a.getId();
            }
            return maxId + 1;
        }
    }

    // ==============================
    // Service Classes
    // ==============================
    static class PatientService {
        private PatientDAO dao = new PatientDAO();

        public void addPatient(String name, int age, String gender, String contact) {
            if (!InputValidator.isValidName(name)) {
                System.out.println("Invalid name.");
                return;
            }
            if (!InputValidator.isValidAge(age)) {
                System.out.println("Invalid age.");
                return;
            }
            if (!InputValidator.isValidGender(gender)) {
                System.out.println("Invalid gender.");
                return;
            }
            if (!InputValidator.isValidContact(contact)) {
                System.out.println("Invalid contact number.");
                return;
            }
            int id = dao.getNextId();
            Patient patient = new Patient(id, name, age, gender, contact);
            dao.addPatient(patient);
            System.out.println("Patient added successfully with ID: " + id);
        }

        public void updatePatient(int id, String name, int age, String gender, String contact) {
            Patient patient = dao.getPatientById(id);
            if (patient == null) {
                System.out.println("Patient not found.");
                return;
            }
            if (!InputValidator.isValidName(name) || !InputValidator.isValidAge(age) ||
                    !InputValidator.isValidGender(gender) || !InputValidator.isValidContact(contact)) {
                System.out.println("Invalid input(s).");
                return;
            }
            patient.setName(name);
            patient.setAge(age);
            patient.setGender(gender);
            patient.setContact(contact);
            dao.updatePatient(patient);
            System.out.println("Patient updated successfully.");
        }

        public void deletePatient(int id) {
            Patient patient = dao.getPatientById(id);
            if (patient == null) {
                System.out.println("Patient not found.");
                return;
            }
            dao.deletePatient(id);
            System.out.println("Patient deleted successfully.");
        }

        public void listPatients() {
            List<Patient> patients = dao.getAllPatients();
            if (patients.isEmpty()) {
                System.out.println("No patients found.");
                return;
            }
            System.out.println("Patient List:");
            for (Patient p : patients) {
                System.out.println("ID: " + p.getId() + ", Name: " + p.getName() +
                        ", Age: " + p.getAge() + ", Gender: " + p.getGender() +
                        ", Contact: " + p.getContact());
            }
        }
    }

    static class DoctorService {
        private DoctorDAO dao = new DoctorDAO();

        public void addDoctor(String name, String specialization, String contact) {
            if (!InputValidator.isValidName(name)) {
                System.out.println("Invalid name.");
                return;
            }
            if (!InputValidator.isValidSpecialization(specialization)) {
                System.out.println("Invalid specialization.");
                return;
            }
            if (!InputValidator.isValidContact(contact)) {
                System.out.println("Invalid contact number.");
                return;
            }
            int id = dao.getNextId();
            Doctor doctor = new Doctor(id, name, specialization, contact);
            dao.addDoctor(doctor);
            System.out.println("Doctor added successfully with ID: " + id);
        }

        public void updateDoctor(int id, String name, String specialization, String contact) {
            Doctor doctor = dao.getDoctorById(id);
            if (doctor == null) {
                System.out.println("Doctor not found.");
                return;
            }
            if (!InputValidator.isValidName(name) || !InputValidator.isValidSpecialization(specialization) ||
                    !InputValidator.isValidContact(contact)) {
                System.out.println("Invalid input(s).");
                return;
            }
            doctor.setName(name);
            doctor.setSpecialization(specialization);
            doctor.setContact(contact);
            dao.updateDoctor(doctor);
            System.out.println("Doctor updated successfully.");
        }

        public void deleteDoctor(int id) {
            Doctor doctor = dao.getDoctorById(id);
            if (doctor == null) {
                System.out.println("Doctor not found.");
                return;
            }
            dao.deleteDoctor(id);
            System.out.println("Doctor deleted successfully.");
        }

        public void listDoctors() {
            List<Doctor> doctors = dao.getAllDoctors();
            if (doctors.isEmpty()) {
                System.out.println("No doctors found.");
                return;
            }
            System.out.println("Doctor List:");
            for (Doctor d : doctors) {
                System.out.println("ID: " + d.getId() + ", Name: " + d.getName() +
                        ", Specialization: " + d.getSpecialization() +
                        ", Contact: " + d.getContact());
            }
        }
    }

    static class AppointmentService {
        private AppointmentDAO dao = new AppointmentDAO();
        private PatientDAO patientDAO = new PatientDAO();
        private DoctorDAO doctorDAO = new DoctorDAO();

        public void addAppointment(int patientId, int doctorId, String date, String time) {
            Patient patient = patientDAO.getPatientById(patientId);
            Doctor doctor = doctorDAO.getDoctorById(doctorId);

            if (patient == null) {
                System.out.println("Invalid patient ID.");
                return;
            }
            if (doctor == null) {
                System.out.println("Invalid doctor ID.");
                return;
            }
            if (!InputValidator.isValidDate(date)) {
                System.out.println("Invalid date format. Use YYYY-MM-DD.");
                return;
            }
            if (!InputValidator.isValidTime(time)) {
                System.out.println("Invalid time format. Use HH:MM.");
                return;
            }
            int id = dao.getNextId();
            Appointment appointment = new Appointment(id, patientId, doctorId, date, time);
            dao.addAppointment(appointment);
            System.out.println("Appointment scheduled successfully with ID: " + id);
        }

        public void updateAppointment(int id, String date, String time) {
            Appointment appointment = dao.getAppointmentById(id);
            if (appointment == null) {
                System.out.println("Appointment not found.");
                return;
            }
            if (!InputValidator.isValidDate(date) || !InputValidator.isValidTime(time)) {
                System.out.println("Invalid date or time format.");
                return;
            }
            appointment.setDate(date);
            appointment.setTime(time);
            dao.updateAppointment(appointment);
            System.out.println("Appointment updated successfully.");
        }

        public void deleteAppointment(int id) {
            Appointment appointment = dao.getAppointmentById(id);
            if (appointment == null) {
                System.out.println("Appointment not found.");
                return;
            }
            dao.deleteAppointment(id);
            System.out.println("Appointment cancelled successfully.");
        }

        public void listAppointments() {
            List<Appointment> appointments = dao.getAllAppointments();
            if (appointments.isEmpty()) {
                System.out.println("No appointments found.");
                return;
            }
            System.out.println("Appointment List:");
            for (Appointment a : appointments) {
                Patient p = patientDAO.getPatientById(a.getPatientId());
                Doctor d = doctorDAO.getDoctorById(a.getDoctorId());
                System.out.println("ID: " + a.getId() + ", Patient: " + (p != null ? p.getName() : "Unknown") +
                        ", Doctor: " + (d != null ? d.getName() : "Unknown") +
                        ", Date: " + a.getDate() + ", Time: " + a.getTime());
            }
        }
    }

    // ==============================
    // Main Application
    // ==============================
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PatientService patientService = new PatientService();
        DoctorService doctorService = new DoctorService();
        AppointmentService appointmentService = new AppointmentService();

        while (true) {
            System.out.println("\n=== Smart Health Console App ===");
            System.out.println("1. Patient Management");
            System.out.println("2. Doctor Management");
            System.out.println("3. Appointment Management");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    patientMenu(sc, patientService);
                    break;
                case "2":
                    doctorMenu(sc, doctorService);
                    break;
                case "3":
                    appointmentMenu(sc, appointmentService);
                    break;
                case "4":
                    System.out.println("Thank you for using Smart Health Console App!");
                    System.out.println("Author: Roshan Kushwaha");
                    System.out.println("Galgotias University Java Project by GUVI");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void patientMenu(Scanner sc, PatientService service) {
        while (true) {
            System.out.println("\n--- Patient Management ---");
            System.out.println("1. Add Patient");
            System.out.println("2. Update Patient");
            System.out.println("3. Delete Patient");
            System.out.println("4. List Patients");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Age: ");
                    int age = Integer.parseInt(sc.nextLine());
                    System.out.print("Gender (Male/Female/Other): ");
                    String gender = sc.nextLine();
                    System.out.print("Contact (10 digits): ");
                    String contact = sc.nextLine();
                    service.addPatient(name, age, gender, contact);
                    break;
                case "2":
                    System.out.print("Patient ID: ");
                    int upId = Integer.parseInt(sc.nextLine());
                    System.out.print("New Name: ");
                    String upName = sc.nextLine();
                    System.out.print("New Age: ");
                    int upAge = Integer.parseInt(sc.nextLine());
                    System.out.print("New Gender: ");
                    String upGender = sc.nextLine();
                    System.out.print("New Contact: ");
                    String upContact = sc.nextLine();
                    service.updatePatient(upId, upName, upAge, upGender, upContact);
                    break;
                case "3":
                    System.out.print("Patient ID: ");
                    int delId = Integer.parseInt(sc.nextLine());
                    service.deletePatient(delId);
                    break;
                case "4":
                    service.listPatients();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void doctorMenu(Scanner sc, DoctorService service) {
        while (true) {
            System.out.println("\n--- Doctor Management ---");
            System.out.println("1. Add Doctor");
            System.out.println("2. Update Doctor");
            System.out.println("3. Delete Doctor");
            System.out.println("4. List Doctors");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Specialization: ");
                    String specialization = sc.nextLine();
                    System.out.print("Contact (10 digits): ");
                    String contact = sc.nextLine();
                    service.addDoctor(name, specialization, contact);
                    break;
                case "2":
                    System.out.print("Doctor ID: ");
                    int upId = Integer.parseInt(sc.nextLine());
                    System.out.print("New Name: ");
                    String upName = sc.nextLine();
                    System.out.print("New Specialization: ");
                    String upSpec = sc.nextLine();
                    System.out.print("New Contact: ");
                    String upContact = sc.nextLine();
                    service.updateDoctor(upId, upName, upSpec, upContact);
                    break;
                case "3":
                    System.out.print("Doctor ID: ");
                    int delId = Integer.parseInt(sc.nextLine());
                    service.deleteDoctor(delId);
                    break;
                case "4":
                    service.listDoctors();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void appointmentMenu(Scanner sc, AppointmentService service) {
        while (true) {
            System.out.println("\n--- Appointment Management ---");
            System.out.println("1. Schedule Appointment");
            System.out.println("2. Update Appointment");
            System.out.println("3. Cancel Appointment");
            System.out.println("4. List Appointments");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Patient ID: ");
                    int patientId = Integer.parseInt(sc.nextLine());
                    System.out.print("Doctor ID: ");
                    int doctorId = Integer.parseInt(sc.nextLine());
                    System.out.print("Date (YYYY-MM-DD): ");
                    String date = sc.nextLine();
                    System.out.print("Time (HH:MM): ");
                    String time = sc.nextLine();
                    service.addAppointment(patientId, doctorId, date, time);
                    break;
                case "2":
                    System.out.print("Appointment ID: ");
                    int upId = Integer.parseInt(sc.nextLine());
                    System.out.print("New Date (YYYY-MM-DD): ");
                    String upDate = sc.nextLine();
                    System.out.print("New Time (HH:MM): ");
                    String upTime = sc.nextLine();
                    service.updateAppointment(upId, upDate, upTime);
                    break;
                case "3":
                    System.out.print("Appointment ID: ");
                    int delId = Integer.parseInt(sc.nextLine());
                    service.deleteAppointment(delId);
                    break;
                case "4":
                    service.listAppointments();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}

