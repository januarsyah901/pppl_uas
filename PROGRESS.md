# Laporan Progress Pengujian E2E — Fitur Utama (Role-Based Testing)

**Kelompok**: Janu, Fahim, Akmal, Hafidz
**Tanggal**: 19 Juni 2026

---

Laporan ini menyajikan ringkasan progres pengerjaan pengujian otomatis end-to-end (E2E) setelah dilakukan **rombak total** skenario pengujian. Sistem Auto Service kini diuji menggunakan pendekatan simulasi peran Owner yang mengakses 5 fitur mandiri secara terpisah — setiap skenario dimulai dari proses login `owner/owner123` secara independen hingga verifikasi hasil akhir.

---

## Pembagian Kerja & Status Implementasi

### 1. Januarsyah Akbar (QA Engineer 1 — Role: Owner)
*   **Fitur yang Diuji**:
    *   **Fitur 1**: Tambah Kategori Inventori (`/inventori/kategori`)
    *   **Fitur 2**: Update Pengaturan Bengkel (`/pengaturan`)
*   **File yang Dibuat**:
    *   `BasePage.java`, `LoginPage.java`, `DashboardPage.java` — inisialisasi framework POM
    *   `KategoriPage.java`, `PengaturanPage.java` — Page Object Model fitur Janu
    *   `AuthSteps.java`, `JanuSteps.java`, `NavigasiSteps.java` — Step Definitions
    *   `Hooks.java` — lifecycle browser (setup & teardown)
    *   `janu.feature` — skenario BDD Gherkin
*   **Perintah Test**: `mvn test -Dcucumber.filter.tags="@janu"`
*   **Status**: ✅ Selesai (2/2 Passed)

### 2. Fahim (QA Engineer 2 — Role: Owner)
*   **Fitur yang Diuji**:
    *   **Fitur 3**: Tambah Pelanggan Baru (`/pelanggan`)
*   **File yang Dibuat**:
    *   `PelangganPage.java` — Page Object Model fitur Fahim
    *   `FahimSteps.java` — Step Definitions
    *   `fahim.feature` — skenario BDD Gherkin
    *   `pom.xml`, `TestRunner.java` — konfigurasi runner Cucumber JUnit
*   **Perintah Test**: `mvn test -Dcucumber.filter.tags="@fahim"`
*   **Status**: ✅ Selesai (1/1 Passed)

### 3. Akmal (QA Engineer 3 — Role: Owner)
*   **Fitur yang Diuji**:
    *   **Fitur 4**: Registrasi Kendaraan Baru (`/kendaraan`)
*   **File yang Dibuat**:
    *   `KendaraanPage.java` — Page Object Model fitur Akmal
    *   `AkmalSteps.java` — Step Definitions
    *   `akmal.feature` — skenario BDD Gherkin
*   **Perintah Test**: `mvn test -Dcucumber.filter.tags="@akmal"`
*   **Status**: ✅ Selesai (1/1 Passed)

### 4. Hafidz (QA Engineer 4 — Role: Owner)
*   **Fitur yang Diuji**:
    *   **Fitur 5**: Tambah Karyawan Baru (`/karyawan`)
*   **File yang Dibuat**:
    *   `KaryawanPage.java` — Page Object Model fitur Hafidz
    *   `HafidzSteps.java` — Step Definitions
    *   `hafidz.feature` — skenario BDD Gherkin
    *   `bug-reports.md` — laporan bug komprehensif dari 5 fitur
*   **Perintah Test**: `mvn test -Dcucumber.filter.tags="@hafidz"`
*   **Status**: ✅ Selesai (1/1 Passed)

---

## Perintah Lengkap Eksekusi Test

> **Prasyarat**: Backend (`http://localhost:4001`) dan Frontend (`http://localhost:3333`) harus sudah berjalan.

```bash
# Masuk ke direktori e2e-testing
cd e2e-testing

# Jalankan semua skenario sekaligus
mvn test

# Jalankan per anggota
mvn test -Dcucumber.filter.tags="@janu"     # Skenario Janu
mvn test -Dcucumber.filter.tags="@fahim"    # Skenario Fahim
mvn test -Dcucumber.filter.tags="@akmal"    # Skenario Akmal
mvn test -Dcucumber.filter.tags="@hafidz"   # Skenario Hafidz
```

Laporan HTML otomatis tersedia setelah test selesai di:
```
e2e-testing/target/cucumber-reports/cucumber.html
```

---

## File Dependensi & Konfigurasi Utama

Semua berkas pengujian diisolasi sepenuhnya di dalam direktori `e2e-testing/` di root proyek:

*   **Runner Test JUnit**: `e2e-testing/src/test/java/com/autoservice/runner/TestRunner.java`
*   **Skenario Feature** (1 file per anggota):
    *   `janu.feature` — Tambah Kategori + Pengaturan Bengkel
    *   `fahim.feature` — Tambah Pelanggan
    *   `akmal.feature` — Registrasi Kendaraan
    *   `hafidz.feature` — Tambah Karyawan
*   **Page Object (POM)**:
    *   `BasePage.java` (shared)
    *   `LoginPage.java` (shared)
    *   `DashboardPage.java` (shared)
    *   `KategoriPage.java` (Janu)
    *   `PengaturanPage.java` (Janu)
    *   `PelangganPage.java` (Fahim)
    *   `KendaraanPage.java` (Akmal)
    *   `KaryawanPage.java` (Hafidz)
*   **Step Definitions**:
    *   `Hooks.java` (shared — lifecycle browser)
    *   `AuthSteps.java` (shared — login steps)
    *   `NavigasiSteps.java` (shared — navigasi & klik tombol generik)
    *   `JanuSteps.java` (Janu)
    *   `FahimSteps.java` (Fahim)
    *   `AkmalSteps.java` (Akmal)
    *   `HafidzSteps.java` (Hafidz)
*   **Dokumentasi QA**:
    *   `test-cases.md`
    *   `bug-reports.md`
