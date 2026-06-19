# Proyek Akhir PPPL - Auto Service

Proyek ini merupakan repositori utama untuk sistem manajemen bengkel dan inventori bernama **Auto Service**. Repositori ini mengintegrasikan seluruh komponen sistem yang terdiri dari backend API, frontend dashboard, dan framework pengujian otomatis end-to-end (E2E) berbasis **Java Maven + Selenium** untuk memenuhi tugas responsi praktikum **Pengujian Perangkat Lunak (PPPL)**.

Sistem dikelola dalam struktur monorepo dengan memanfaatkan Git Submodules untuk modul aplikasi utama, sehingga riwayat commit dan repositori masing-masing layanan tetap terisolasi dan mandiri.

---

## 1. Penjelasan Singkat SUT (System Under Test)

**Auto Service** adalah aplikasi berbasis web yang berfungsi sebagai platform manajemen operasional bengkel otomotif modern. Fitur-fitur utama SUT meliputi:
*   **Autentikasi & Otorisasi**: Login admin, kasir, dan petugas dengan pembagian hak akses (role-based access control).
*   **Manajemen Antrean & Kasir**: Pendaftaran antrean kendaraan masuk dan pemrosesan transaksi pembayaran oleh kasir.
*   **Katalog Sparepart & Jasa**: Pendataan suku cadang, oli, ban, serta katalog paket jasa servis bengkel.
*   **Stok Opname (MVP Utama)**: Rekonsiliasi pencatatan stok di sistem dengan jumlah stok fisik aktual yang ada di gudang bengkel untuk meminimalisir selisih.

---

## 2. Penjelasan Singkat Test Suite

*Test Suite* dirancang khusus untuk menguji keandalan alur bisnis MVP utama pada SUT, yaitu alur **Stock Opname dari hulu ke hilir**.

*   **Framework**: Pengujian ini dibangun menggunakan kombinasi **Java**, **Maven** (build automation), **Selenium WebDriver** (otomasi browser), dan **Cucumber JVM** (untuk framework BDD berbasis Gherkin Syntax).
*   **Pola Desain (Design Pattern)**: Menerapkan **Page Object Model (POM)** secara ketat dengan memisahkan selector/locator elemen UI ([pages](file:///Users/fizualstd/Documents/GitHub/pppl_uas/e2e-testing/src/test/java/com/autoservice/pages)) dari instruksi langkah pengujian ([stepdefinitions](file:///Users/fizualstd/Documents/GitHub/pppl_uas/e2e-testing/src/test/java/com/autoservice/stepdefinitions)).
*   **Metode Uji yang Digunakan**:
    1.  **Equivalence Partitioning (EP)**: Digunakan untuk memvalidasi halaman autentikasi/login dengan skenario kelas ekivalen kredensial valid dan tidak valid (TC-EP-001 s.d TC-EP-004).
    2.  **Boundary Value Analysis (BVA)**: Digunakan untuk menguji keandalan kolom input jumlah fisik aktual pada form opname dengan batas nilai minimum (0), di bawah batas bawah (-1), dan batas atas kapasitas (9999) (TC-BVA-001 s.d TC-BVA-006).
*   **Laporan Otomatis (Automated Report)**: Mengintegrasikan Cucumber HTML Plugin di Maven Surefire yang otomatis menghasilkan laporan visual HTML interaktif di folder `target/cucumber-reports/` setelah tes selesai dijalankan.

---

## 3. Pembagian Tugas Kelompok (QA Automation Team)

Pembagian peran dirancang secara adil agar setiap QA Automation Engineer bertanggung jawab penuh terhadap modul perencanaan test case, pembuatan Page Object Model (POM), dan implementasi Step Definitions:

| Anggota Kelompok | Peran & Tanggung Jawab | File Output Terkait |
| :--- | :--- | :--- |
| **Januarsyah Akbar**<br>(QA Engineer 1) | Inisialisasi arsitektur dasar pengujian Java Maven, konfigurasi Hooks global, class dasar [BasePage.java](file:///Users/fizualstd/Documents/GitHub/pppl_uas/e2e-testing/src/test/java/com/autoservice/pages/BasePage.java), dan E2E Halaman 1 (Auth / Login Page). | `BasePage.java`, `LoginPage.java`, `AuthSteps.java`, `Hooks.java` |
| **Fahim**<br>(QA Engineer 2) | Integrasi konfigurasi runner Cucumber JUnit, penyusunan berkas skenario BDD Gherkin global, dan E2E Halaman 2 (Dashboard Page). | `stock_opname_e2e.feature`, `DashboardPage.java`, `pom.xml` |
| **Akmal**<br>(QA Engineer 3) | Perancangan test case (EP/BVA), implementasi E2E Halaman 3 (Daftar Stok) dan Halaman 4 (Form Opname - Pengisian Input). | `InventoryPage.java`, `InventorySteps.java` |
| **Hafidz**<br>(QA Engineer 4) | Konfigurasi automated report Runner, pembuatan laporan bug [bug-reports.md](file:///Users/fizualstd/Documents/GitHub/pppl_uas/e2e-testing/docs/bug-reports.md), E2E Halaman 4 (Form Opname - Submit) dan Halaman 5 (Detail/Log & Logout). | `OpnameFormPage.java`, `DetailPage.java`, `DetailSteps.java`, `TestRunner.java`, `bug-reports.md` |

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
|   |       |   |   |-- InventoryPage.java
|   |       |   |   |-- OpnameFormPage.java
|   |       |   |   `-- DetailPage.java
|   |       |   |-- stepdefinitions/ # Implementasi langkah pengujian Gherkin
|   |       |   |   |-- Hooks.java
|   |       |   |   |-- AuthSteps.java
|   |       |   |   |-- InventorySteps.java
|   |       |   |   `-- DetailSteps.java
|   |       |   `-- runner/
|   |       |       `-- TestRunner.java # Runner Test JUnit
|   |       `-- resources/features/
|   |           `-- stock_opname_e2e.feature # Skenario BDD Gherkin
|   |-- docs/                       # Dokumen perencanaan dan laporan bug
|   |   |-- bug-reports.md          # Laporan bug pengujian (BVA & EP)
|   |   `-- test-cases.md           # Rancangan kasus uji (EP & BVA)
|   `-- pom.xml                     # File konfigurasi dependensi Maven
|-- PROGRESS.md                     # Laporan kemajuan pengerjaan tim
|-- TEST_PLANNING.md                # Dokumen rencana uji, skenario BVA & EP lengkap
`-- .gitmodules                     # Konfigurasi Git Submodules
```

---

## 5. Panduan Instalasi dan Penggunaan

### 1. Kloning Repositori beserta Seluruh Submodule

Untuk mengunduh proyek ini beserta seluruh submodul di dalamnya secara otomatis, jalankan perintah berikut secara rekursif:

```bash
git clone --recursive https://github.com/januarsyah901/pppl_uas.git
cd pppl_uas
```

Jika terlanjur melakukan kloning biasa tanpa menyertakan submodul, jalankan perintah ini di dalam direktori proyek:

```bash
git submodule update --init --recursive
```

### 2. Konfigurasi & Menjalankan Backend (be-opname)

Masuk ke direktori backend, pasang dependensi, salin environment variables, dan jalankan server API:

```bash
cd be-opname
npm install
cp .env.example .env
# Sesuaikan isi DATABASE_URL dan JWT_SECRET di .env jika perlu
npx prisma db push
npm run dev
```

### 3. Konfigurasi & Menjalankan Frontend (fe-opname)

Masuk ke direktori frontend, pasang dependensi, lakukan konfigurasi environment, dan jalankan server Next.js (berjalan pada port `3333`):

```bash
cd ../fe-opname
npm install
cp .env.example .env
npm run dev
```

### 4. Eksekusi Pengujian E2E (e2e-testing)

> [!TIP]
> **Penting untuk Demo Responsi**: Secara default, pengujian diatur untuk langsung menguji website produksi yang sudah dideploy secara online di: **https://auto-service-jet.vercel.app**.
> Oleh karena itu, saat presentasi responsi, **Anda dan rekan kelompok Anda TIDAK perlu menyalakan backend (`be-opname`), frontend (`fe-opname`), ataupun meng-install database lokal**.
> Anda cukup masuk ke folder `e2e-testing` dan langsung jalankan `mvn test`. Browser otomatis akan menguji website Vercel yang sudah online tersebut.

Masuk ke direktori pengujian, lalu jalankan runner test menggunakan Maven:

```bash
cd e2e-testing
mvn test
```

*Untuk menjalankan Cucumber dry-run saja (memeriksa pemetaan step definitions tanpa membuka browser):*
```bash
mvn test -Dcucumber.options="--dry-run"
```

### 5. Membuat dan Melihat Laporan HTML Otomatis

Setelah pengujian selesai dijalankan, Maven Surefire Plugin dan Cucumber HTML Plugin akan secara otomatis menghasilkan laporan visual HTML interaktif di:
`e2e-testing/target/cucumber-reports/cucumber.html`

Laporan ini dapat dibuka langsung di browser Google Chrome untuk melihat statistik persentase kelulusan skenario, durasi eksekusi, dan tangkapan layar kegagalan skenario.
