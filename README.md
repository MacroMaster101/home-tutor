# 🏠 MetaTutor - Online Tutoring Management Platform

MetaTutor is a Java Maven web application designed for running an online tutoring marketplace. It facilitates student signups, tutor portfolios, interactive course enrollments, card/bank transaction pipelines, rating systems, and comprehensive administrative dashboards.

---

## 🛠️ Architecture Overview

The application follows the classic Model-View-Controller (MVC) architectural pattern, utilizing JSP for dynamic template rendering, Java Servlets for operational logic, and a high-performance flat-file database system for persistence.

```mermaid
graph TD
    subgraph Client Layer
        Browser["🌐 Web Browser"]
    end

    subgraph Controller Layer
        AdminServ["⚙️ AdminServlet & AdminDashboardServlet"]
        CourseServ["⚙️ CourseManagementServlet"]
        TutorServ["⚙️ TutorManagementServlet"]
        StudentServ["⚙️ StudentServlet & LoginServlet"]
    end

    subgraph Persistence Layer (Flat-File DB)
        S_File["💾 students.txt (Semicolon Delimited)"]
        T_File["💾 tutors.txt (Comma Delimited)"]
        C_File["💾 courses.txt (Pipe Delimited)"]
        SC_File["💾 studentCourses.txt (Pipe Delimited)"]
        P_File["💾 payment.txt (Pipe Delimited)"]
    end

    Browser <-->|HTTP Requests / Responses| AdminServ
    Browser <-->|HTTP Requests / Responses| CourseServ
    Browser <-->|HTTP Requests / Responses| TutorServ
    Browser <-->|HTTP Requests / Responses| StudentServ

    AdminServ <-->|Reads & Writes| S_File
    AdminServ <-->|Reads & Writes| T_File
    CourseServ <-->|Reads & Writes| C_File
    CourseServ <-->|Reads & Writes| SC_File
    TutorServ <-->|Reads & Writes| T_File
    StudentServ <-->|Reads & Writes| S_File
```

---

## ✨ Features

### 👩‍🎓 Student Portal
- Safe custom profile registration, portal login, password validation, and profile picture uploads.
- Access student dashboards, search for ongoing courses, view detailed course curriculum, and self-manage profile files.
- Enroll in premium courses, execute payments, and submit tutor ratings.

### 👨‍🏫 Tutor Portal
- Self-registration with detailed academic qualifications, credentials, and custom descriptions.
- Manage tutoring classes, list student enrollments, post new courses, and edit course details.

### 💳 Transaction Pipeline
- Supports **Card** and **Bank Transfer** checkout methods.
- Includes step-by-step OTP generation and OTP verification screens to simulate a secure financial checkout.

### 🛠️ Administrative Dashboards
- Secure login credentials (`admin` / `1234`).
- Statistics dashboard showing total system enrollment counts, active students, and financial values.
- Unified sidebar menu connecting students, tutors, courses, enrollments, and payment logging screens.

---

## 🧰 Technology Stack

- **Core Runtime**: Java 8 / Java 24 (OpenSDK compliant).
- **Dependency Management**: Maven.
- **Web Layer**: Java Servlets (Servlet 4.0.1) & JavaServer Pages (JSP / JSTL 1.2).
- **Styling & Assets**: Vanilla CSS, Bootstrap 5 (CDN), and FontAwesome 6 icons.
- **Local Application Server**: Apache Tomcat 9.0.x.

---

## 📁 Repository Directory Structure

```text
src/main/java/
  ├── admin/       Admin controllers, dashboards, and transaction reversers
  ├── course/      Course model definitions, file helper tools, and controllers
  ├── payment/     Payment verification models, OTP generators, and confirmation servlets
  ├── rating/      Course rating definitions and servlet submitters
  ├── student/     Student model definitions, database service layer, and profiles
  └── tutor/       Tutor definition, binary search tree helper, and profiles

src/main/webapp/
  ├── WEB-INF/     web.xml descriptors alongside delimited flat-file database stores
  ├── images/      All system graphics, student and tutor profile pictures, and course banner assets
  └── *.jsp        Administrative, Student, and Tutor views
```

---

## 🚀 Deployment Instructions

### 1. Build the Package
From the project workspace root, run the Maven build pipeline to clean directories and assemble the web archive:
```bash
mvn clean package
```

### 2. Run Locally via Tomcat
- Configure an **Apache Tomcat 9.0.x** configuration in your IDE (such as IntelliJ IDEA).
- Point the deployment target to the exploded WAR artifact: `home-tutor:war exploded`.
- Start the server. The application will deploy locally at the following context root:
```text
http://localhost:8080/home_tutor_war_exploded/
```

---

## 💾 Flat-File Database Specifications

Data is saved as flat text records under `src/main/webapp/WEB-INF/` with custom delimiter formatting:

| Database File | File Delimiter | Key Fields |
| :--- | :--- | :--- |
| `students.txt` | Semicolon (`;`) | `stdId`, `name`, `userName`, `email`, `phone`, `address`, `password` (SHA-256), `course`, `dob`, `profilePicPath` |
| `tutors.txt` | Comma (`,`) | `tutorId`, `username`, `name`, `subject`, `email`, `contact`, `campusName`, `degreeCourse`, `degreeLevel`, `address`, `password` (SHA-256), `about`, `profileImage` |
| `courses.txt` | Pipe (`\|`) | `courseId`, `tutorId`, `tutorName`, `tutorSubject`, `courseName`, `description`, `level`, `imagePath`, `price`, `duration` |
| `studentCourses.txt` | Pipe (`\|`) | `studentUsername`, `courseId`, `courseName`, `imagePath`, `price`, `duration`, `paidStatus` |
| `payment.txt` | Pipe (`\|`) | `studentUsername`, `courseId`, `courseName`, `paymentMethod`, `amount`, `date`, `status` |
| `ratings.txt` | Pipe (`\|`) | `studentUsername`, `ratingValue` |

---

## 🧹 Repo Hygiene & Standards

- **Asset Standardization**: The single `images/` directory serves as the unified storage for all system graphics, student and tutor profile avatars, and course banners for structural simplicity and design consistency.
- **Styling Rules**: External Bootstrap bundles are avoided; pages request identical responsive builds from verified CDNs for visual consistency and instantaneous load speeds.
