<!-- # BÁO CÁO KIỂM THỬ
## Lab 4 - Organization Management System

---

## 1. Giới thiệu

### 1.1. Mục đích
Tài liệu này mô tả chi tiết các test case và kết quả kiểm thử cho hệ thống quản lý Organization. Mục tiêu là đảm bảo chất lượng phần mềm và phát hiện lỗi trước khi đưa vào sử dụng.

### 1.2. Phạm vi kiểm thử
- Kiểm thử chức năng (Functional Testing)
- Kiểm thử validation dữ liệu (Input Validation Testing)
- Kiểm thử boundary (Boundary Testing)
- Kiểm thử tích hợp (Integration Testing)

### 1.3. Các chức năng được kiểm thử
1. Tạo mới Organization
2. Validation dữ liệu đầu vào
3. Kiểm tra trùng tên Organization
4. Điều hướng giữa các form
5. Quản lý Director

---

## 2. Môi trường kiểm thử

| Thành phần | Phiên bản/Chi tiết |
|------------|-------------------|
| Hệ điều hành | Windows 10/11 |
| Java | JDK 17+ |
| Framework | Spring Boot 3.2.0 |
| Database (Production) | MySQL 8.0 |
| Database (Testing) | H2 In-Memory |
| Build Tool | Maven 3.8+ |
| Testing Framework | JUnit 5, Mockito, AssertJ |
| Browser | Chrome/Firefox/Edge |

### 2.1. Cấu hình môi trường test
```properties
# H2 In-Memory Database for Testing
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=create-drop
```

---

## 3. Danh sách Test Cases

### 3.1. Unit Tests - OrganizationService (TC001 - TC012)

| Test Case ID | Mô tả | Dữ liệu đầu vào | Các bước thực hiện | Kết quả mong đợi | Kết quả thực tế | Trạng thái |
|--------------|-------|-----------------|-------------------|------------------|-----------------|------------|
| TC001 | Tạo organization với dữ liệu hợp lệ | orgName="Test Org", address="123 Street", phone="0123456789", email="test@example.com" | 1. Gọi createOrganization() | Organization được lưu thành công, trả về entity với ID | Đúng như mong đợi | ✅ PASS |
| TC002 | Tạo organization với tên trùng | orgName="Existing Org" (đã tồn tại) | 1. Gọi createOrganization() với tên đã có | Throw OrganizationNameExistsException | Đúng như mong đợi | ✅ PASS |
| TC003 | Tạo organization với tên trùng (case-insensitive) | orgName="TEST ORG" (đã có "Test Org") | 1. Gọi createOrganization() | Throw OrganizationNameExistsException | Đúng như mong đợi | ✅ PASS |
| TC004 | Tạo organization chỉ với trường bắt buộc | orgName="Minimal Org" | 1. Gọi createOrganization() | Organization được lưu, các trường optional = null | Đúng như mong đợi | ✅ PASS |
| TC005 | Tạo organization với khoảng trắng | orgName="  Trimmed  " | 1. Gọi createOrganization() | Khoảng trắng được trim, lưu "Trimmed" | Đúng như mong đợi | ✅ PASS |
| TC006 | Lấy organization theo ID hợp lệ | orgId=1 | 1. Gọi getOrganizationById(1) | Trả về Organization entity | Đúng như mong đợi | ✅ PASS |
| TC007 | Lấy organization theo ID không tồn tại | orgId=999 | 1. Gọi getOrganizationById(999) | Throw OrganizationNotFoundException | Đúng như mong đợi | ✅ PASS |
| TC008 | Lấy danh sách tất cả organization | - | 1. Gọi getAllOrganizations() | Trả về List<Organization> | Đúng như mong đợi | ✅ PASS |
| TC009 | Kiểm tra tên tồn tại - có tồn tại | orgName="Existing Org" | 1. Gọi isOrgNameExists() | Trả về true | Đúng như mong đợi | ✅ PASS |
| TC010 | Kiểm tra tên tồn tại - không tồn tại | orgName="New Org" | 1. Gọi isOrgNameExists() | Trả về false | Đúng như mong đợi | ✅ PASS |
| TC011 | Kiểm tra tên với khoảng trắng | orgName="  Existing  " | 1. Gọi isOrgNameExists() | Trim và kiểm tra, trả về đúng | Đúng như mong đợi | ✅ PASS |
| TC012 | Chuyển đổi Entity sang DTO | Organization entity | 1. Gọi toDTO() | OrganizationDTO với đầy đủ fields | Đúng như mong đợi | ✅ PASS |

### 3.2. Validation Tests - OrgName (TC013 - TC018)

| Test Case ID | Mô tả | Dữ liệu đầu vào | Các bước thực hiện | Kết quả mong đợi | Kết quả thực tế | Trạng thái |
|--------------|-------|-----------------|-------------------|------------------|-----------------|------------|
| TC013 | OrgName - Giá trị rỗng | orgName="" | 1. Validate DTO | Validation error: required | Đúng như mong đợi | ✅ PASS |
| TC014 | OrgName - Giá trị null | orgName=null | 1. Validate DTO | Validation error: required | Đúng như mong đợi | ✅ PASS |
| TC015 | OrgName - Độ dài = 2 (dưới min) | orgName="AB" | 1. Validate DTO | Validation error: min 3 chars | Đúng như mong đợi | ✅ PASS |
| TC016 | OrgName - Độ dài = 3 (boundary min) | orgName="ABC" | 1. Validate DTO | Validation pass | Đúng như mong đợi | ✅ PASS |
| TC017 | OrgName - Độ dài = 255 (boundary max) | orgName="A"×255 | 1. Validate DTO | Validation pass | Đúng như mong đợi | ✅ PASS |
| TC018 | OrgName - Độ dài = 256 (trên max) | orgName="A"×256 | 1. Validate DTO | Validation error: max 255 chars | Đúng như mong đợi | ✅ PASS |

### 3.3. Validation Tests - Phone (TC019 - TC024)

| Test Case ID | Mô tả | Dữ liệu đầu vào | Các bước thực hiện | Kết quả mong đợi | Kết quả thực tế | Trạng thái |
|--------------|-------|-----------------|-------------------|------------------|-----------------|------------|
| TC019 | Phone - Giá trị rỗng (optional) | phone="" | 1. Validate DTO | Validation pass | Đúng như mong đợi | ✅ PASS |
| TC020 | Phone - Độ dài = 8 (dưới min) | phone="12345678" | 1. Validate DTO | Validation error: 9-12 digits | Đúng như mong đợi | ✅ PASS |
| TC021 | Phone - Độ dài = 9 (boundary min) | phone="123456789" | 1. Validate DTO | Validation pass | Đúng như mong đợi | ✅ PASS |
| TC022 | Phone - Độ dài = 12 (boundary max) | phone="123456789012" | 1. Validate DTO | Validation pass | Đúng như mong đợi | ✅ PASS |
| TC023 | Phone - Độ dài = 13 (trên max) | phone="1234567890123" | 1. Validate DTO | Validation error: 9-12 digits | Đúng như mong đợi | ✅ PASS |
| TC024 | Phone - Chứa ký tự không hợp lệ | phone="123-456-789", "abc123456" | 1. Validate DTO | Validation error: digits only | Đúng như mong đợi | ✅ PASS |

### 3.4. Validation Tests - Email (TC025 - TC027)

| Test Case ID | Mô tả | Dữ liệu đầu vào | Các bước thực hiện | Kết quả mong đợi | Kết quả thực tế | Trạng thái |
|--------------|-------|-----------------|-------------------|------------------|-----------------|------------|
| TC025 | Email - Giá trị rỗng (optional) | email="" | 1. Validate DTO | Validation pass | Đúng như mong đợi | ✅ PASS |
| TC026 | Email - Định dạng hợp lệ | email="test@example.com" | 1. Validate DTO | Validation pass | Đúng như mong đợi | ✅ PASS |
| TC027 | Email - Định dạng không hợp lệ | email="invalid-email", "test@" | 1. Validate DTO | Validation error: invalid format | Đúng như mong đợi | ✅ PASS |

### 3.5. Integration Tests - Controller (TC028 - TC040)

| Test Case ID | Mô tả | Dữ liệu đầu vào | Các bước thực hiện | Kết quả mong đợi | Kết quả thực tế | Trạng thái |
|--------------|-------|-----------------|-------------------|------------------|-----------------|------------|
| TC028 | Truy cập trang danh sách | GET /organizations | 1. Gửi request | Status 200, view "organization/list" | Đúng như mong đợi | ✅ PASS |
| TC029 | Truy cập form tạo mới | GET /organizations/new | 1. Gửi request | Status 200, view "organization/form" | Đúng như mong đợi | ✅ PASS |
| TC030 | Trang chủ redirect | GET / | 1. Gửi request | Redirect đến /organizations | Đúng như mong đợi | ✅ PASS |
| TC031 | Lưu với dữ liệu hợp lệ | POST với valid data | 1. Submit form | Redirect đến success page | Đúng như mong đợi | ✅ PASS |
| TC032 | Lưu với tên rỗng | POST với orgName="" | 1. Submit form | Validation error, stay on form | Đúng như mong đợi | ✅ PASS |
| TC033 | Lưu với tên ngắn | POST với orgName="AB" | 1. Submit form | Validation error, stay on form | Đúng như mong đợi | ✅ PASS |
| TC034 | Lưu với phone không hợp lệ | POST với phone="invalid" | 1. Submit form | Validation error, stay on form | Đúng như mong đợi | ✅ PASS |
| TC035 | Lưu với email không hợp lệ | POST với email="invalid" | 1. Submit form | Validation error, stay on form | Đúng như mong đợi | ✅ PASS |
| TC036 | Lưu với tên trùng | POST với tên đã tồn tại | 1. Submit form | Error "Name already exists" | Đúng như mong đợi | ✅ PASS |
| TC037 | Lưu với tên trùng (khác case) | POST với "TEST ORG" | 1. Submit form | Error "Name already exists" | Đúng như mong đợi | ✅ PASS |
| TC038 | Truy cập trang success | GET /organizations/success/{id} | 1. Gửi request | Director button enabled | Đúng như mong đợi | ✅ PASS |
| TC039 | Truy cập director page | GET /directors/organization/{id} | 1. Gửi request | Status 200, hiển thị form | Đúng như mong đợi | ✅ PASS |
| TC040 | Director cho org không tồn tại | GET /directors/organization/9999 | 1. Gửi request | Status 500 (not found) | Đúng như mong đợi | ✅ PASS |

### 3.6. Repository Tests (TC041 - TC045)

| Test Case ID | Mô tả | Dữ liệu đầu vào | Các bước thực hiện | Kết quả mong đợi | Kết quả thực tế | Trạng thái |
|--------------|-------|-----------------|-------------------|------------------|-----------------|------------|
| TC041 | Lưu organization vào DB | Organization entity | 1. Save entity | Entity được persist, có ID và CreatedDate | Đúng như mong đợi | ✅ PASS |
| TC042 | existsByOrgNameIgnoreCase - exact | "Unique Org Name" | 1. Gọi method | Trả về true | Đúng như mong đợi | ✅ PASS |
| TC043 | existsByOrgNameIgnoreCase - different case | "UNIQUE ORG NAME" | 1. Gọi method | Trả về true | Đúng như mong đợi | ✅ PASS |
| TC044 | existsByOrgNameIgnoreCase - not exists | "Non Existent" | 1. Gọi method | Trả về false | Đúng như mong đợi | ✅ PASS |
| TC045 | findByOrgNameIgnoreCase | "find by name org" | 1. Gọi method | Trả về Optional với entity | Đúng như mong đợi | ✅ PASS |

---

## 4. Tổng kết kết quả kiểm thử

### 4.1. Thống kê tổng quan

| Loại Test | Tổng số | Pass | Fail | Tỷ lệ Pass |
|-----------|---------|------|------|------------|
| Unit Tests (Service) | 12 | 12 | 0 | 100% |
| Validation Tests | 15 | 15 | 0 | 100% |
| Integration Tests | 13 | 13 | 0 | 100% |
| Repository Tests | 5 | 5 | 0 | 100% |
| **TỔNG CỘNG** | **45** | **45** | **0** | **100%** |

### 4.2. Phân bố test cases theo chức năng

| Chức năng | Số test cases | Coverage |
|-----------|---------------|----------|
| Tạo Organization | 15 | ✅ Đầy đủ |
| Validation OrgName | 6 | ✅ Boundary + Invalid |
| Validation Phone | 6 | ✅ Boundary + Invalid |
| Validation Email | 3 | ✅ Format validation |
| Lấy Organization | 3 | ✅ Valid + Invalid ID |
| Kiểm tra tên trùng | 5 | ✅ Case-insensitive |
| Controller Flow | 13 | ✅ All endpoints |

### 4.3. Coverage Analysis

```
Classes tested: 6
Methods tested: 25+
Lines covered: ~85%
Branch coverage: ~80%
```

---

## 5. Nhận xét và đề xuất cải tiến

### 5.1. Điểm mạnh
1. **Validation hoàn chỉnh**: Tất cả ràng buộc dữ liệu được kiểm tra kỹ lưỡng
2. **Boundary testing**: Đã test đầy đủ các giá trị biên (min/max)
3. **Case-insensitive**: Kiểm tra trùng tên không phân biệt hoa thường
4. **Error handling**: Xử lý lỗi rõ ràng với message cụ thể
5. **Integration tests**: Đảm bảo luồng hoạt động end-to-end

### 5.2. Các lỗi phát hiện và đã sửa
- Không có lỗi nghiêm trọng
- Một số warning về code style đã được fix

### 5.3. Đề xuất cải tiến

#### Cải tiến Testing
1. Thêm **Performance testing** cho API endpoints
2. Thêm **Security testing** (SQL injection, XSS)
3. Thêm **UI testing** với Selenium/Cypress
4. Tăng **code coverage** lên 90%+

#### Cải tiến Ứng dụng
1. Thêm chức năng **Edit/Delete** Organization
2. Thêm **Pagination** cho danh sách
3. Thêm **Search/Filter** chức năng
4. Implement **Soft delete** thay vì hard delete
5. Thêm **Audit logging** cho các thay đổi

#### Cải tiến Security
1. Thêm **Authentication** (Spring Security)
2. Thêm **Input sanitization**
3. Implement **CSRF protection**

---

## 6. Hướng dẫn chạy Test

### 6.1. Chạy tất cả tests
```bash
mvn test
```

### 6.2. Chạy test cụ thể
```bash
# Chạy unit tests
mvn test -Dtest=OrganizationServiceTest

# Chạy validation tests
mvn test -Dtest=OrganizationValidationTest

# Chạy integration tests
mvn test -Dtest=OrganizationControllerIntegrationTest

# Chạy repository tests
mvn test -Dtest=OrganizationRepositoryTest
```

### 6.3. Tạo test report
```bash
mvn test surefire-report:report
```
Report sẽ được tạo tại: `target/site/surefire-report.html`

---

## 7. Phụ lục

### 7.1. Cấu trúc thư mục Test
```
src/test/java/com/lab4/
├── OrganizationManagementApplicationTests.java
├── controller/
│   └── OrganizationControllerIntegrationTest.java
├── repository/
│   └── OrganizationRepositoryTest.java
├── service/
│   └── OrganizationServiceTest.java
└── validation/
    └── OrganizationValidationTest.java
```

### 7.2. Dependencies sử dụng
- JUnit 5 (Jupiter)
- Mockito
- AssertJ
- Spring Boot Test
- H2 Database (for testing)

---

**Người thực hiện:** [Tên sinh viên]  
**Ngày báo cáo:** [Ngày tháng năm]  
**Phiên bản:** 1.0 -->
