# Proyek Akhir PPPL - Auto Service

Proyek ini merupakan repositori utama untuk sistem manajemen bengkel dan inventori bernama **Auto Service**. Repositori ini mengintegrasikan seluruh komponen sistem yang terdiri dari backend API, frontend dashboard, dan framework pengujian otomatis end-to-end (E2E) berbasis **Java Maven + Selenium** untuk memenuhi tugas responsi praktikum **Pengujian Perangkat Lunak (PPPL)**.

Sistem dikelola dalam struktur monorepo dengan memanfaatkan Git Submodules untuk modul aplikasi utama, sehingga riwayat commit dan repositori masing-masing layanan tetap terisolasi dan mandiri.

---

## 1. Penjelasan Singkat SUT (System Under Test)

**Auto Service** adalah aplikasi berbasis web yang berfungsi sebagai platform manajemen operasional bengkel otomotif modern. Fitur-fitur utama SUT meliputi:
*   **Autentikasi & Otorisasi**: Login dengan pembagian hak akses (role-based access control) untuk Owner, Admin, Kasir, dan Mekanik.
*   **Manajemen Pelanggan**: Pendataan data pelanggan bengkel beserta riwayat kendaraannya.
*   **Manajemen Kendaraan**: Registrasi dan pencatatan kendaraan milik pelanggan.
*   **Katalog Jasa & Inventori**: Pendataan kategori suku cadang dan pengaturan profil bengkel (Owner).
*   **Manajemen Karyawan**: Pendataan dan pengelolaan akun karyawan bengkel (Owner).

---

## 2. Penjelasan Singkat Test Suite

*Test Suite* dirancang khusus untuk menguji fungsionalitas utama Auto Service dari hulu ke hilir dengan mensimulasikan **login owner** secara terisolasi untuk 5 fitur utama:
1.  **Tambah Kategori Inventori** (Janu - Owner)
2.  **Update Pengaturan Bengkel** (Janu - Owner)
3.  **Tambah Pelanggan Baru** (Fahim - Owner)
4.  **Registrasi Kendaraan Baru** (Akmal - Owner)
5.  **Tambah Karyawan Baru** (Hafidz - Owner)

*   **Framework**: Pengujian ini dibangun menggunakan kombinasi **Java**, **Maven** (build automation), **Selenium WebDriver** (otomasi browser), dan **Cucumber JVM** (untuk framework BDD berbasis Gherkin Syntax).
*   **Pola Desain (Design Pattern)**: Menerapkan **Page Object Model (POM)** secara ketat dengan memisahkan selector/locator elemen UI dari instruksi langkah pengujian (stepdefinitions).
*   **Laporan Otomatis (Automated Report)**: Mengintegrasikan Cucumber HTML Plugin di Maven Surefire yang otomatis menghasilkan laporan visual HTML interaktif di folder `target/cucumber-reports/` setelah tes selesai dijalankan.

---

## 3. Pembagian Tugas Kelompok (QA Automation Team)

Pembagian peran dirancang agar setiap QA Automation Engineer bertanggung jawab penuh terhadap modul pengujian fiturnya masing-masing, dari login hingga penyelesaian fitur:

| Anggota Kelompok | Peran & Tanggung Jawab | File Output Terkait |
| :--- | :--- | :--- |
| **Januarsyah Akbar**<br>(QA Engineer 1) | Inisialisasi dasar Selenium POM & Hooks, E2E Fitur **Tambah Kategori Inventori** dan **Update Pengaturan Bengkel** (Role: Owner). | `BasePage.java`, `LoginPage.java`, `KategoriPage.java`, `PengaturanPage.java`, `AuthSteps.java`, `JanuSteps.java`, `janu.feature` |
| **Fahim**<br>(QA Engineer 2) | Integrasi runner Cucumber JUnit global (`pom.xml`, `TestRunner.java`), dan E2E Fitur **Tambah Pelanggan Baru** (Role: Owner). | `PelangganPage.java`, `FahimSteps.java`, `fahim.feature` |
| **Akmal**<br>(QA Engineer 3) | Perancangan kasus uji dan E2E Fitur **Registrasi Kendaraan Baru** (Role: Owner). | `KendaraanPage.java`, `AkmalSteps.java`, `akmal.feature` |
| **Hafidz**<br>(QA Engineer 4) | Konfigurasi automated report Runner, E2E Fitur **Tambah Karyawan Baru** (Role: Owner), dan pembuatan dokumen bug-reports.md (Bug Report dari 5 fitur). | `KaryawanPage.java`, `HafidzSteps.java`, `hafidz.feature`, `TestRunner.java`, `bug-reports.md` |

---

## 4. Struktur Repositori

Struktur direktori utama repositori diatur sebagai berikut:

```text
pppl_uas/
|-- be-opname/                      # [Submodule] Backend API (Express.js, TS, Prisma)
|-- fe-opname/                      # [Submodule] Frontend Dashboard (Next.js, TS, Tailwind)
|-- e2e-testing/                    # [Direktori Root] Framework Pengujian E2E Java Maven
|   |-- src/
|   |   `-- test/
|   |       |-- java/com/autoservice/
|   |       |   |-- pages/          # Design Pattern Page Object Model (POM)
|   |       |   |   |-- BasePage.java
|   |       |   |   |-- LoginPage.java
|   |       |   |   |-- DashboardPage.java
|   |       |   |   |-- KategoriPage.java
|   |       |   |   |-- PengaturanPage.java
|   |       |   |   |-- PelangganPage.java
|   |       |   |   |-- KendaraanPage.java
|   |       |   |   `-- KaryawanPage.java
|   |       |   |-- stepdefinitions/ # Implementasi langkah pengujian Gherkin
|   |       |   |   |-- Hooks.java
|   |       |   |   |-- AuthSteps.java
|   |       |   |   |-- NavigasiSteps.java
|   |       |   |   |-- JanuSteps.java
|   |       |   |   |-- FahimSteps.java
|   |       |   |   |-- AkmalSteps.java
|   |       |   |   `-- HafidzSteps.java
|   |       |   `-- runner/
|   |       |       `-- TestRunner.java # Runner Test JUnit
|   |       `-- resources/features/    # Skenario BDD Gherkin (1 file per anggota)
|   |           |-- janu.feature
|   |           |-- fahim.feature
|   |           |-- akmal.feature
|   |           `-- hafidz.feature
|   |-- docs/                       # Dokumen perencanaan dan laporan bug
|   |   |-- bug-reports.md          # Laporan bug pengujian (BVA & EP)
|   |   `-- test-cases.md           # Rancangan kasus uji (EP & BVA)
|   `-- pom.xml                     # File konfigurasi dependensi Maven
|-- PROGRESS.md                     # Laporan kemajuan pengerjaan tim
|-- TEST_PLANNING.md                # Dokumen rencana uji, skenario lengkap
`-- .gitmodules                     # Konfigurasi Git Submodules
```

---

## 5. Panduan Instalasi dan Penggunaan

### 1. Kloning Repositori beserta Seluruh Submodule

```bash
git clone --recursive https://github.com/januarsyah901/pppl_uas.git
cd pppl_uas
```

### 2. Persiapan: Jalankan Backend & Frontend

Buka **2 terminal** terpisah:

**Terminal 1 — Backend:**
```bash
cd be-opname
npm install
# Pastikan file .env sudah terisi (PORT=4001, DATABASE_URL, dll)
npm run dev
# Backend berjalan di http://localhost:4001
```

**Terminal 2 — Frontend:**
```bash
cd fe-opname
npm install
npm run dev
# Frontend berjalan di http://localhost:3333
```

### 3. Eksekusi Pengujian E2E (e2e-testing)

Buka **Terminal 3**, masuk ke direktori pengujian:

```bash
cd e2e-testing
```

| Perintah | Keterangan |
|---|---|
| `mvn test` | Jalankan **semua** skenario (5 fitur) |
| `mvn test -Dcucumber.filter.tags="@janu"` | Jalankan skenario **Janu** saja (kategori + pengaturan) |
| `mvn test -Dcucumber.filter.tags="@fahim"` | Jalankan skenario **Fahim** saja (pelanggan) |
| `mvn test -Dcucumber.filter.tags="@akmal"` | Jalankan skenario **Akmal** saja (kendaraan) |
| `mvn test -Dcucumber.filter.tags="@hafidz"` | Jalankan skenario **Hafidz** saja (karyawan) |

> **Catatan**: Pastikan backend (`http://localhost:4001`) dan frontend (`http://localhost:3333`) sudah berjalan sebelum menjalankan test.

### 4. Membuat dan Melihat Laporan HTML Otomatis

Setelah pengujian selesai, laporan HTML interaktif tersedia di:
```
e2e-testing/target/cucumber-reports/cucumber.html
```
Buka file tersebut di browser untuk melihat hasil lengkap per skenario.
