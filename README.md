# Proyek Akhir PPPL - Auto Service

Proyek ini merupakan repositori utama untuk sistem manajemen bengkel dan inventori bernama **Auto Service**. Repositori ini mengintegrasikan seluruh komponen sistem yang terdiri dari backend API, frontend dashboard, dan framework pengujian otomatis end-to-end (E2E) untuk memenuhi tugas responsi praktikum **Pengujian Perangkat Lunak (PPPL)**.

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

*   **Framework**: Pengujian ini dibangun menggunakan kombinasi **Playwright** (sebagai browser automation tool) dan **CucumberJS** (untuk framework BDD berbasis Gherkin Syntax).
*   **Pola Desain (Design Pattern)**: Menerapkan **Page Object Model (POM)** secara ketat dengan memisahkan selector/locator elemen UI ([page_objects](file:///Users/fizualstd/Documents/GitHub/pppl_uas/e2e-testing/page_objects)) dari instruksi langkah pengujian ([step_definitions](file:///Users/fizualstd/Documents/GitHub/pppl_uas/e2e-testing/step_definitions)).
*   **Metode Uji yang Digunakan**:
    1.  **Equivalence Partitioning (EP)**: Digunakan untuk memvalidasi halaman autentikasi/login dengan skenario kelas ekivalen kredensial valid dan tidak valid (TC-EP-001 s.d TC-EP-004).
    2.  **Boundary Value Analysis (BVA)**: Digunakan untuk menguji keandalan kolom input jumlah fisik aktual pada form opname dengan batas nilai minimum (0), di bawah batas bawah (-1), dan batas atas kapasitas (9999) (TC-BVA-001 s.d TC-BVA-006).
*   **Laporan Otomatis (Automated Report)**: Mengintegrasikan `cucumber-html-reporter` yang otomatis mengompilasi file JSON hasil pengujian menjadi laporan visual HTML interaktif, lengkap dengan grafik persentase kelulusan dan tangkapan layar (screenshot) otomatis jika terjadi kegagalan pengujian.

---

## 3. Pembagian Tugas Kelompok (QA Automation Team)

Pembagian peran dirancang secara adil agar setiap QA Automation Engineer bertanggung jawab penuh terhadap modul perencanaan test case, pembuatan Page Object Model (POM), dan implementasi Step Definitions:

| Anggota Kelompok | Peran & Tanggung Jawab | File Output Terkait |
| :--- | :--- | :--- |
| **Januarsyah Akbar**<br>(QA Engineer 1) | Inisialisasi arsitektur dasar pengujian, konfigurasi Hooks global, class dasar [BasePage.js](file:///Users/fizualstd/Documents/GitHub/pppl_uas/e2e-testing/page_objects/BasePage.js), dan E2E Halaman 1 (Auth / Login Page). | `BasePage.js`, `LoginPage.js`, `auth_steps.js`, `hooks.js` |
| **Fahim**<br>(QA Engineer 2) | Integrasi konfigurasi runner Cucumber, penyusunan berkas skenario BDD Gherkin global, dan E2E Halaman 2 (Dashboard Page). | `stock_opname_e2e.feature`, `DashboardPage.js`, `cucumber.js` |
| **Akmal**<br>(QA Engineer 3) | Perancangan test case (EP/BVA), implementasi E2E Halaman 3 (Daftar Stok) dan Halaman 4 (Form Opname - Pengisian Input). | `InventoryPage.js`, `inventory_steps.js` |
| **Hafidz**<br>(QA Engineer 4) | Konfigurasi automated HTML report, pembuatan laporan bug [BUG_REPORTING.md](file:///Users/fizualstd/Documents/GitHub/pppl_uas/e2e-testing/reports/BUG_REPORTING.md), E2E Halaman 4 (Form Opname - Submit) dan Halaman 5 (Detail/Log & Logout). | `OpnameFormPage.js`, `DetailPage.js`, `detail_steps.js`, `reporter.js`, `BUG_REPORTING.md` |

---

## 4. Struktur Repositori

Struktur direktori utama repositori diatur sebagai berikut:

```text
pppl_uas/
|-- be-opname/                      # [Submodule] Backend API (Express.js, TS, Prisma)
|-- fe-opname/                      # [Submodule] Frontend Dashboard (Next.js, TS, Tailwind)
|-- e2e-testing/                    # [Direktori Root] Framework Pengujian E2E (Playwright & Cucumber)
|   |-- features/
|   |   `-- stock_opname_e2e.feature  # Berkas spesifikasi skenario BDD Gherkin
|   |-- page_objects/               # Design Pattern Page Object Model (POM)
|   |   |-- BasePage.js             # Utility interaksi browser dasar (Playwright wrapper)
|   |   |-- LoginPage.js            # Selector & aksi halaman Login
|   |   |-- DashboardPage.js        # Selector & aksi halaman Dashboard
|   |   |-- InventoryPage.js        # Selector & aksi halaman Daftar Stok Opname
|   |   |-- OpnameFormPage.js       # Selector & aksi modal pengisian fisik opname
|   |   `-- DetailPage.js           # Selector & aksi modal log detail riwayat opname
|   |-- step_definitions/           # Implementasi langkah pengujian Gherkin
|   |   |-- auth_steps.js           # Langkah uji terkait Login & Autentikasi
|   |   |-- inventory_steps.js      # Langkah uji terkait Dashboard & Form Input Opname
|   |   `-- detail_steps.js         # Langkah uji terkait Detail Log Riwayat & Logout
|   |-- support/
|   |   |-- hooks.js                # Setup lifecycle browser & Tangkapan Layar jika Gagal
|   |   `-- reporter.js             # Skrip generator Laporan HTML Otomatis
|   |-- reports/                    # Folder output laporan & dokumentasi pengujian
|   |   |-- html/                   # Output visual Cucumber HTML Reporter
|   |   `-- BUG_REPORTING.md        # Laporan temuan bug pengujian (BVA & EP)
|   |-- package.json                # Manifest dependensi framework pengujian
|   `-- cucumber.js                 # Berkas konfigurasi CucumberJS
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

Masuk ke direktori pengujian, pasang dependensi, lalu jalankan runner test:

```bash
cd ../e2e-testing
npm install
npx playwright install
npm test
```

### 5. Membuat dan Melihat Laporan HTML Otomatis

Setelah pengujian selesai dijalankan, Anda dapat meng-compile file JSON menjadi HTML report interaktif dengan perintah:

```bash
npm run report
```

Laporan HTML interaktif akan ter-generate di:
`e2e-testing/reports/html/cucumber_report.html`

Laporan ini dapat dibuka langsung di Google Chrome untuk melihat visualisasi status kelulusan pengujian, grafik performa, serta tangkapan layar kegagalan skenario.
