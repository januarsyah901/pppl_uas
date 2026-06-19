# Test Cases - PPPL Java Automation Suite

The following tables list all the test cases designed and implemented in this automation test suite.

## ## 1. Authentication & Access Tests (EP)
Tested under: `src/test/resources/features/stock_opname_e2e.feature`

| ID | Page / Portal | Scenario | Steps | Expected Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **TC-EP-001** | Login Page | Failed login with invalid email | Input wrong email, input valid password, click login | Show error "Kredensial tidak cocok" | Passed |
| **TC-EP-002** | Login Page | Success login with valid credentials | Input valid email, input valid password, click login | Redirected to Dashboard Page | Passed |
| **TC-EP-003** | Login Page | Failed login with short password | Input email, input password < 8 chars, click login | Validation error "Password minimal 8 karakter" | Open |
| **TC-EP-004** | Login Page | Failed login with too long password | Input email, input password > 20 chars, click login | Validation error "Password maksimal 20 karakter" | Open (BUG-002) |

---

## ## 2. Stock Opname & Boundary Value Analysis Tests (BVA)
Tested under: `src/test/resources/features/stock_opname_e2e.feature`

*Asumsi batas input stok fisik: non-negatif integer, batas atas kapasitas gudang = 9999 unit.*

| ID | Input Nilai Aktual | Kategori Batas | Harapan Hasil | Status |
| :--- | :--- | :--- | :--- | :--- |
| **TC-BVA-001** | `-1` | Di bawah batas minimum | Form menolak input, tombol submit tidak aktif | Open (BUG-001) |
| **TC-BVA-002** | `0` | Nilai batas bawah (Minimum) | Berhasil disubmit, sistem mencatat stok habis (0) | Passed |
| **TC-BVA-003** | `1` | Tepat di atas batas bawah | Berhasil disubmit, sistem mencatat stok = 1 | Passed |
| **TC-BVA-004** | `9998` | Tepat di bawah batas atas | Berhasil disubmit, sistem mencatat stok = 9998 | Passed |
| **TC-BVA-005** | `9999` | Nilai batas atas (Maksimum) | Berhasil disubmit, sistem mencatat stok = 9999 | Passed |
| **TC-BVA-006** | `10000` | Di atas batas maksimum | Form menolak, muncul error "Nilai melebihi kapasitas" | Open |
