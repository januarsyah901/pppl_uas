# Proyek Akhir PPPL - Auto Service

Proyek ini merupakan repositori utama untuk sistem manajemen bengkel dan inventori bernama Auto Service. Repositori ini mengintegrasikan seluruh komponen sistem yang terdiri dari backend api, frontend dashboard, dan framework pengujian otomatis end-to-end (E2E).

Sistem dikelola dalam struktur monorepo dengan memanfaatkan Git Submodules untuk modul aplikasi utama, sehingga riwayat commit dan repositori masing-masing layanan tetap terisolasi dan mandiri.

## Komponen Proyek

Proyek ini terbagi menjadi tiga bagian utama:

1. be-opname (Submodule)
   Layanan Backend API berbasis Node.js yang dibangun menggunakan Express.js, TypeScript, dan Prisma ORM dengan basis data PostgreSQL. Modul ini bertanggung jawab mengelola seluruh logika bisnis, autentikasi, serta interaksi basis data.

2. fe-opname (Submodule)
   Aplikasi Frontend berupa dashboard web interaktif untuk admin dan petugas inventori. Dibangun menggunakan Next.js (React), TypeScript, dan Tailwind CSS untuk antarmuka yang modern dan responsif.

3. e2e-testing (Direktori Root)
   Framework pengujian otomatis berbasis End-to-End (E2E) menggunakan kombinasi Playwright, Cucumber (Behavior-Driven Development / BDD Gherkin), dan pola desain Page Object Model (POM).

## Struktur Repositori

Struktur direktori utama proyek ini diatur sebagai berikut:

pad-2/
|-- be-opname/                      # Submodule Backend API (Express.js)
|-- fe-opname/                      # Submodule Frontend Dashboard (Next.js)
|-- e2e-testing/                    # Framework Pengujian E2E (Playwright & Cucumber)
|   |-- features/                   # Berkas spesifikasi skenario BDD (.feature)
|   |-- page_objects/               # Representasi halaman web (POM)
|   |-- step_definitions/           # Implementasi langkah pengujian Gherkin
|   |-- support/                    # Konfigurasi pembantu, Hooks, dan Reporter
|   |-- package.json                # Manifest dependensi framework pengujian
|   `-- cucumber.js                 # Berkas konfigurasi Cucumber
|-- PROGRESS.md                     # Laporan kemajuan pengerjaan tim
|-- TEST_PLANNING.md                # Dokumen perencanaan pengujian dan skenario BVA/EP
|-- .gitignore                      # Berkas pengabaian Git di tingkat root
`-- .gitmodules                     # Konfigurasi Git Submodules

## Alur Pengujian E2E

Pengujian dilakukan untuk memvalidasi alur bisnis utama sepanjang lima halaman web interaktif:

1. Halaman 1 (Autentikasi / Login)
   Verifikasi pembatasan akses masuk sistem dengan uji batas kredensial (BVA dan EP).

2. Halaman 2 (Dashboard Utama)
   Pemeriksaan komponen ringkasan statistik operasional bengkel, total antrean, dan notifikasi stok kritis.

3. Halaman 3 (Daftar Stok Inventori)
   Pengujian fitur pencarian, penyaringan data suku cadang, dan navigasi menuju proses penyesuaian stok (opname).

4. Halaman 4 (Formulir Stock Opname)
   Validasi input jumlah fisik aktual, perhitungan selisih otomatis, dan pengisian catatan penyesuaian.

5. Halaman 5 (Detail dan Log Aktivitas)
   Verifikasi penyimpanan data opname ke database, sinkronisasi log riwayat aktivitas, dan penutupan sesi (logout).

## Panduan Instalasi dan Penggunaan

### 1. Kloning Repositori

Untuk mengunduh proyek ini beserta seluruh submodul di dalamnya, jalankan perintah berikut secara rekursif:

git clone --recursive https://github.com/januarsyah901/pppl_uas.git
cd pppl_uas

Jika Anda terlanjur melakukan kloning biasa tanpa menyertakan seluruh submodul, jalankan perintah berikut di dalam direktori proyek:

git submodule update --init --recursive

### 2. Konfigurasi Backend (be-opname)

Masuk ke direktori backend, pasang dependensi, lakukan konfigurasi environment, dan jalankan server:

cd be-opname
npm install
cp .env.example .env
npx prisma db push
npm run dev

### 3. Konfigurasi Frontend (fe-opname)

Masuk ke direktori frontend, pasang dependensi, lakukan konfigurasi environment, dan jalankan server Next.js:

cd ../fe-opname
npm install
cp .env.example .env
npm run dev

### 4. Eksekusi Pengujian E2E (e2e-testing)

Masuk ke direktori e2e-testing, pasang dependensi pengujian (Playwright dan Cucumber), lalu jalankan skenario tes:

cd ../e2e-testing
npm install
npx playwright install
npm test

Untuk membuat laporan hasil pengujian dalam format HTML interaktif setelah tes selesai dijalankan, gunakan perintah:

npm run report

Laporan akan dihasilkan secara otomatis di dalam direktori e2e-testing/reports/.

## Pembagian Peran QA Automation Team

Proyek pengujian otomatis ini dikembangkan secara kolaboratif oleh anggota kelompok berikut dengan rincian tanggung jawab masing-masing:

1. Januarsyah Akbar (QA Engineer 1)
   Inisialisasi dasar framework pengujian, pembuatan base class Page Object Model (BasePage.js), konfigurasi Hooks global, serta perancangan dan implementasi pengujian Halaman 1 (Auth/Login Page).

2. Fahim (QA Engineer 2)
   Konfigurasi runner Cucumber, penulisan berkas spesifikasi skenario BDD global (.feature), serta perancangan dan implementasi pengujian Halaman 2 (Dashboard Page).

3. Akmal (QA Engineer 3)
   Perancangan dan implementasi pengujian Halaman 3 (Daftar Stok/Inventory Page) dan Halaman 4 (Formulir Stock Opname bagian input data).

4. Hafidz (QA Engineer 4)
   Konfigurasi laporan otomatis (HTML Reporter), pembuatan berkas laporan bug (BUG_REPORTING.md), serta perancangan dan implementasi pengujian Halaman 4 (Formulir Stock Opname bagian aksi submit) dan Halaman 5 (Detail Opname, Log, dan Logout).
