<!-- # Organization Management System - Lab 4

## Mô tả
Hệ thống quản lý Organization được xây dựng cho môn học Software Testing. Ứng dụng cho phép:
- Thêm mới Organization
- Kiểm tra tính hợp lệ của dữ liệu nhập
- Lưu dữ liệu vào cơ sở dữ liệu
- Quản lý Director cho mỗi Organization

## Công nghệ sử dụng
- **Backend**: Java 17, Spring Boot 3.2.0
- **Template Engine**: Thymeleaf
- **Database**: MySQL (production) / H2 (testing & demo)
- **Build Tool**: Maven
- **Testing**: JUnit 5, Mockito, Spring Boot Test

## Cấu trúc project
```
src/
├── main/
│   ├── java/com/lab4/
│   │   ├── controller/          # Web controllers
│   │   ├── dto/                 # Data Transfer Objects
│   │   ├── entity/              # JPA Entities
│   │   ├── exception/           # Custom exceptions
│   │   ├── repository/          # JPA Repositories
│   │   └── service/             # Business logic
│   └── resources/
│       ├── templates/           # Thymeleaf templates
│       └── application.properties
└── test/
    └── java/com/lab4/
        ├── controller/          # Integration tests
        ├── repository/          # Repository tests
        ├── service/             # Service unit tests
        └── validation/          # Validation tests
```

## Cài đặt và chạy

### Yêu cầu
- Java 17+
- Maven 3.6+
- MySQL (optional - có thể dùng H2)

### Cách 1: Chạy với H2 (Demo mode - Không cần MySQL)
```bash
cd D:\Class\Khoa\testting\lab4
mvn spring-boot:run "-Dspring-boot.run.profiles=h2"
```

### Cách 2: Chạy với MySQL
1. Start MySQL Docker container:
```bash
docker run --name mysql-lab4 -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=lab4_db -p 3306:3306 -d mysql:8
```

2. Chạy ứng dụng:
```bash
cd D:\Class\Khoa\testting\lab4
mvn spring-boot:run
```

### Truy cập ứng dụng
- **Web UI**: http://localhost:8080
- **H2 Console** (nếu dùng H2 profile): http://localhost:8080/h2-console

## Chạy Tests
```bash
# Chạy tất cả tests
mvn test

# Chạy tests với report
mvn test -Dsurefire.reportFormat=brief
```

## API Endpoints

| Method | URL | Mô tả |
|--------|-----|-------|
| GET | /organizations | Danh sách organizations |
| GET | /organizations/new | Form tạo mới organization |
| POST | /organizations/save | Lưu organization |
| GET | /organizations/success/{id} | Trang thành công |
| GET | /directors/organization/{id} | Quản lý directors |
| POST | /directors/save | Lưu director |

## Validation Rules

### Organization Name
- Bắt buộc
- Độ dài: 3-255 ký tự
- Không được trùng (không phân biệt hoa/thường)

### Phone (tùy chọn)
- Chỉ chứa số
- Độ dài: 9-12 ký tự

### Email (tùy chọn)
- Đúng định dạng email

## Test Cases
Xem chi tiết trong file `TEST_REPORT.md`

## Author
Lab 4 - Software Testing Course -->
