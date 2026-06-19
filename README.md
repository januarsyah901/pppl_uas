# Proyek Akhir PPPL - Auto Service

Proyek ini merupakan repositori utama untuk sistem manajemen bengkel dan inventori bernama **Auto Service**. Repositori ini mengintegrasikan seluruh komponen sistem yang terdiri dari backend API, frontend dashboard, dan framework pengujian otomatis end-to-end (E2E) berbasis **Java Maven + Selenium** untuk memenuhi tugas responsi praktikum **Pengujian Perangkat Lunak (PPPL)**.

Sistem dikelola dalam struktur monorepo dengan memanfaatkan Git Submodules untuk modul aplikasi utama, sehingga riwayat commit dan repositori masing-masing layanan tetap terisolasi dan mandiri.

---

## 1. Penjelasan Singkat SUT (System Under Test)

**Auto Service** adalah aplikasi berbasis web yang berfungsi sebagai platform manajemen operasional bengkel otomotif modern. Fitur-fitur utama SUT meliputi:
*   **Autentikasi & Otorisasi**: Login dengan pembagian hak akses (role-based access control) untuk Owner, Admin, Kasir, dan Mekanik.
*   **Manajemen Antrean**: Pendaftaran antrean kendaraan masuk oleh Admin.
*   **Inspeksi Mekanik**: Checklist pemeriksaan kondisi fisik kendaraan oleh Mekanik.
*   **Katalog Jasa & Inventori**: Pendataan suku cadang di gudang (Owner) dan paket jasa servis bengkel (Owner).
*   **Kasir & Pembayaran**: Pemrosesan transaksi pembayaran dan pembuatan invoice (Kasir).

---

## 2. Penjelasan Singkat Test Suite

*Test Suite* dirancang khusus untuk menguji fungsionalitas utama Auto Service dari hulu ke hilir dengan mensimulasikan login per peran/role secara terisolasi untuk 5 fitur utama:
1.  **Tambah Item di Inventory** (Owner)
2.  **Tambah Jasa di Katalog Jasa** (Owner)
3.  **Tambah Antrean Servis** (Admin)
4.  **Buat Transaksi Baru** (Kasir)
5.  **Inspection Checklist** (Mekanik)

*   **Framework**: Pengujian ini dibangun menggunakan kombinasi **Java**, **Maven** (build automation), **Selenium WebDriver** (otomasi browser), dan **Cucumber JVM** (untuk framework BDD berbasis Gherkin Syntax).
*   **Pola Desain (Design Pattern)**: Menerapkan **Page Object Model (POM)** secara ketat dengan memisahkan selector/locator elemen UI dari instruksi langkah pengujian (stepdefinitions).
*   **Laporan Otomatis (Automated Report)**: Mengintegrasikan Cucumber HTML Plugin di Maven Surefire yang otomatis menghasilkan laporan visual HTML interaktif di folder `target/cucumber-reports/` setelah tes selesai dijalankan.

---

## 3. Pembagian Tugas Kelompok (QA Automation Team)

Pembagian peran dirancang agar setiap QA Automation Engineer bertanggung jawab penuh terhadap modul pengujian fiturnya masing-masing, dari login hingga penyelesaian fitur:

| Anggota Kelompok | Peran & Tanggung Jawab | File Output Terkait |
| :--- | :--- | :--- |
| **Januarsyah Akbar**<br>(QA Engineer 1) | Inisialisasi dasar Selenium POM, E2E Fitur **Tambah Item di Inventory** (Role: Owner) dan **Tambah Jasa di Katalog Jasa** (Role: Owner). | `LoginPage.java`, `InventoryPage.java`, `KatalogJasaPage.java`, `AuthSteps.java`, `InventorySteps.java`, `KatalogJasaSteps.java` |
| **Fahim**<br>(QA Engineer 2) | Integrasi runner Cucumber JUnit global, penyusunan berkas skenario BDD Gherkin, dan E2E Fitur **Tambah Antrean Servis** (Role: Admin). | `stock_opname_e2e.feature`, `QueuePage.java`, `QueueSteps.java`, `pom.xml` |
| **Akmal**<br>(QA Engineer 3) | Perancangan kasus uji dan E2E Fitur **Buat Transaksi Baru** (Role: Kasir). | `CashierPage.java`, `CashierSteps.java` |
| **Hafidz**<br>(QA Engineer 4) | Konfigurasi automated report Runner, E2E Fitur **Inspection Checklist** (Role: Mekanik), dan pembuatan dokumen [bug-reports.md](file:///C:/Users/zvwah/OneDrive/Dokumen/KULIAH/TRPL/Sem%204/PPPL/UAS/pppl_uas/e2e-testing/docs/bug-reports.md) (Bug Report dari 5 fitur). | `InspectionPage.java`, `InspectionSteps.java`, `TestRunner.java`, `bug-reports.md` |

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
|   |       |   |   |-- InventoryPage.java
|   |       |   |   |-- KatalogJasaPage.java
|   |       |   |   |-- QueuePage.java
|   |       |   |   |-- CashierPage.java
|   |       |   |   `-- InspectionPage.java
|   |       |   |-- stepdefinitions/ # Implementasi langkah pengujian Gherkin
|   |       |   |   |-- Hooks.java
|   |       |   |   |-- AuthSteps.java
|   |       |   |   |-- InventorySteps.java
|   |       |   |   |-- QueueSteps.java
|   |       |   |   |-- CashierSteps.java
|   |       |   |   `-- InspectionSteps.java
|   |       |   `-- runner/
|   |       |       `-- TestRunner.java # Runner Test JUnit
|   |       `-- resources/features/
|   |           `-- stock_opname_e2e.feature # Skenario BDD Gherkin
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

### 2. Eksekusi Pengujian E2E (e2e-testing)

Masuk ke direktori pengujian, lalu jalankan runner test menggunakan Maven:

```bash
cd e2e-testing
mvn test
```

### 3. Membuat dan Melihat Laporan HTML Otomatis

Setelah pengujian selesai dijalankan, Maven Surefire Plugin dan Cucumber HTML Plugin akan secara otomatis menghasilkan laporan visual HTML interaktif di:
`e2e-testing/target/cucumber-reports/cucumber.html`
