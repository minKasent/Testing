# Lab 4 - Organization Management System
## Software Testing Course

---

## 📋 Mô tả
Ứng dụng quản lý Organization được xây dựng bằng **Spring Boot 3** với **Thymeleaf** và **MySQL**. Bài tập nhằm đánh giá khả năng phân tích yêu cầu, lập trình ứng dụng và kiểm thử phần mềm.

## 🛠️ Công nghệ sử dụng
- **Backend:** Java 17, Spring Boot 3.2.0
- **Frontend:** Thymeleaf, HTML5, CSS3
- **Database:** MySQL 8.0
- **Testing:** JUnit 5, Mockito, AssertJ, H2 (in-memory)
- **Build:** Maven

## 📁 Cấu trúc Project
```
lab4/
├── src/
│   ├── main/
│   │   ├── java/com/lab4/
│   │   │   ├── controller/      # Controllers
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── entity/          # JPA Entities
│   │   │   ├── exception/       # Custom Exceptions
│   │   │   ├── repository/      # JPA Repositories
│   │   │   ├── service/         # Business Logic
│   │   │   └── OrganizationManagementApplication.java
│   │   └── resources/
│   │       ├── templates/       # Thymeleaf Templates
│   │       │   ├── organization/
│   │       │   └── director/
│   │       └── application.properties
│   └── test/
│       ├── java/com/lab4/       # Test Classes
│       └── resources/
│           └── application-test.properties
├── pom.xml
├── README.md
└── TEST_REPORT.md
```

## 🚀 Hướng dẫn cài đặt

### 1. Yêu cầu hệ thống
- Java JDK 17+
- Maven 3.8+
- MySQL 8.0+ (Docker hoặc local)

### 2. Cấu hình Database
Database MySQL đã được cấu hình với:
- **Host:** localhost:3306
- **Database:** lab4_db
- **Username:** root
- **Password:** 123456

Nếu sử dụng Docker:
```bash
docker run --name mysql-lab4 -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=lab4_db -p 3306:3306 -d mysql:8.0
```

### 3. Build và Run
```bash
# Clone project
cd D:\Class\Khoa\testting\lab4

# Build project
mvn clean install

# Run application
mvn spring-boot:run
```

### 4. Truy cập ứng dụng
- **URL:** http://localhost:8080
- **Organization List:** http://localhost:8080/organizations
- **New Organization:** http://localhost:8080/organizations/new

## 🧪 Chạy Tests

### Chạy tất cả tests
```bash
mvn test
```

### Chạy test cụ thể
```bash
# Unit tests
mvn test -Dtest=OrganizationServiceTest

# Validation tests
mvn test -Dtest=OrganizationValidationTest

# Integration tests
mvn test -Dtest=OrganizationControllerIntegrationTest

# Repository tests
mvn test -Dtest=OrganizationRepositoryTest
```

### Tạo Test Report
```bash
mvn test surefire-report:report
```
Report: `target/site/surefire-report.html`

## 📝 Chức năng

### Organization Management
| Chức năng | Mô tả |
|-----------|-------|
| Thêm mới | Tạo Organization với validation |
| Xem danh sách | Hiển thị tất cả Organizations |
| Quản lý Director | Thêm Directors cho Organization |

### Validation Rules
| Field | Rule |
|-------|------|
| OrgName | Bắt buộc, 3-255 ký tự, không trùng |
| Phone | 9-12 chữ số (nếu nhập) |
| Email | Định dạng email hợp lệ (nếu nhập) |
| Address | Tối đa 255 ký tự |

## 📊 Test Cases Summary
- **Tổng số Test Cases:** 45+
- **Unit Tests:** 12
- **Validation Tests:** 15
- **Integration Tests:** 13
- **Repository Tests:** 5

Chi tiết xem tại: [TEST_REPORT.md](TEST_REPORT.md)

## 📸 Screenshots

### Organization List
![Organization List](screenshots/org-list.png)

### New Organization Form
![New Organization](screenshots/org-form.png)

### Director Management
![Director Management](screenshots/director-form.png)

## 👨‍💻 Tác giả
- **Sinh viên:** [Tên sinh viên]
- **MSSV:** [Mã số sinh viên]
- **Môn học:** Software Testing
- **Lab:** Lab 4

## 📄 License
This project is for educational purposes only.
