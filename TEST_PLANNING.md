# Rencana Pengujian E2E & Pembagian Tugas Kelompok (Rombak Baru)
## Proyek Akhir Praktikum Pengujian Perangkat Lunak

Dokumen ini adalah acuan resmi perencanaan, pembagian tugas, dan skenario pengujian untuk aplikasi **Auto Service (Manajemen Bengkel & Inventori)** setelah perubahan fungsionalitas pengujian. Pengujian diimplementasikan menggunakan framework **Java Maven + Selenium WebDriver + Cucumber (BDD Gherkin) + Page Object Model (POM)**.

Setiap skenario dirancang untuk diuji secara mandiri dari proses login peran masing-masing (Owner, Admin, Kasir, Mekanik) hingga fitur selesai digunakan (full 1 fitur per skenario).

---

## 1. Pembagian Tugas Kelompok (QA Automation Team)

Pembagian tugas dirancang agar setiap QA Automation Engineer bertanggung jawab penuh atas skenario fitur tertentu, dari login peran terkait hingga verifikasi akhir:

```
                               [ Semua Anggota Kelompok = Automation QA Engineer ]
                                                       │
        ┌──────────────────────────┬───────────────────┴──────────────┬──────────────────────────┐
        ▼                          ▼                                  ▼                          ▼
    [ Janu  ]                   [ Fahim ]                          [ Akmal ]                  [ Hafidz ]
  ├─ Role: Owner              ├─ Role: Admin                     ├─ Role: Kasir             ├─ Role: Mekanik
  ├─ Fitur: Tambah Item       ├─ Fitur: Tambah Antrean           ├─ Fitur: Buat Transaksi   ├─ Fitur: Inspection Checklist
  ├─ Fitur: Tambah Jasa       └─ Test Case Queue                 └─ Test Case Transaksi     ├─ Bug Report (5 Fitur)
  └─ Test Case Owner                                                                        └─ Test Case Checklist
```

### Detail Distribusi Pekerjaan:

#### Janu — *Automation QA Engineer 1 (Role: Owner)*
*   **Tanggung Jawab**:
    *   Inisialisasi dasar POM dan konfigurasi driver Selenium.
    *   Merancang test case & mengimplementasikan POM dan Step Definitions untuk **Fitur Tambah Item di Inventory** & **Fitur Tambah Jasa di Katalog Jasa**.
*   **Target Output File**:
    *   [LoginPage.java](file:///C:/Users/zvwah/OneDrive/Dokumen/KULIAH/TRPL/Sem%204/PPPL/UAS/pppl_uas/e2e-testing/src/test/java/com/autoservice/pages/LoginPage.java)
    *   [InventoryPage.java](file:///C:/Users/zvwah/OneDrive/Dokumen/KULIAH/TRPL/Sem%204/PPPL/UAS/pppl_uas/e2e-testing/src/test/java/com/autoservice/pages/InventoryPage.java)
    *   [KatalogJasaPage.java](file:///C:/Users/zvwah/OneDrive/Dokumen/KULIAH/TRPL/Sem%204/PPPL/UAS/pppl_uas/e2e-testing/src/test/java/com/autoservice/pages/KatalogJasaPage.java)
    *   [AuthSteps.java](file:///C:/Users/zvwah/OneDrive/Dokumen/KULIAH/TRPL/Sem%204/PPPL/UAS/pppl_uas/e2e-testing/src/test/java/com/autoservice/stepdefinitions/AuthSteps.java)
    *   [InventorySteps.java](file:///C:/Users/zvwah/OneDrive/Dokumen/KULIAH/TRPL/Sem%204/PPPL/UAS/pppl_uas/e2e-testing/src/test/java/com/autoservice/stepdefinitions/InventorySteps.java)
    *   `KatalogJasaSteps.java`

#### Fahim — *Automation QA Engineer 2 (Role: Admin)*
*   **Tanggung Jawab**:
    *   Mengonfigurasi runner Cucumber JUnit global (`pom.xml`, `TestRunner.java`).
    *   Penyusunan berkas skenario BDD Gherkin global (`stock_opname_e2e.feature`).
    *   Merancang test case & mengimplementasikan POM dan Step Definitions untuk **Fitur Tambah Antrean Servis**.
*   **Target Output File**:
    *   `QueuePage.java`
    *   `QueueSteps.java`

#### Akmal — *Automation QA Engineer 3 (Role: Kasir)*
*   **Tanggung Jawab**:
    *   Merancang test case & mengimplementasikan POM dan Step Definitions untuk **Fitur Buat Transaksi Baru**.
*   **Target Output File**:
    *   `CashierPage.java`
    *   `CashierSteps.java`

#### Hafidz — *Automation QA Engineer 4 (Role: Mekanik)*
*   **Tanggung Jawab**:
    *   Mengonfigurasi automated report runner dan menyusun dokumen Laporan Bug (`bug-reports.md`) dari total 5 fitur yang diuji.
    *   Merancang test case & mengimplementasikan POM dan Step Definitions untuk **Fitur Inspection Checklist**.
*   **Target Output File**:
    *   `InspectionPage.java`
    *   `InspectionSteps.java`
    *   [bug-reports.md](file:///C:/Users/zvwah/OneDrive/Dokumen/KULIAH/TRPL/Sem%204/PPPL/UAS/pppl_uas/e2e-testing/docs/bug-reports.md)

---

## 2. Alur & Skenario Pengujian Per Peran (5 Fitur)

Setiap fitur memiliki modul login mandiri yang mensimulasikan login pengguna sebelum mengakses halaman utama fitur tersebut.

```mermaid
graph TD
    subgraph Janu (Owner)
    A[Login Owner] --> B[Tambah Item Inventory]
    A --> C[Tambah Jasa Katalog]
    end
    subgraph Fahim (Admin)
    D[Login Admin] --> E[Tambah Antrean Servis]
    end
    subgraph Akmal (Kasir)
    F[Login Kasir] --> G[Buat Transaksi Baru]
    end
    subgraph Hafidz (Mekanik)
    H[Login Mekanik] --> I[Inspection Checklist]
    end
```

### Rincian Skenario BDD Gherkin:

#### 1. Fitur Tambah Item di Inventory (Janu - Owner)
*   **Deskripsi**: Owner login untuk menambahkan produk sparepart/oli baru ke sistem agar siap digunakan dalam operasional.
*   **Alur**: Login Owner $\rightarrow$ Klik menu "Inventori Stok" $\rightarrow$ Klik "Tambah Item" $\rightarrow$ Isi Data Item (Nama, Kode, Harga, Stok Awal) $\rightarrow$ Klik Simpan $\rightarrow$ Verifikasi item muncul di tabel inventori.

#### 2. Fitur Tambah Jasa di Katalog Jasa (Janu - Owner)
*   **Deskripsi**: Owner menambahkan daftar jasa servis baru beserta harganya ke katalog bengkel.
*   **Alur**: Login Owner $\rightarrow$ Klik menu "Katalog Jasa" $\rightarrow$ Klik "Tambah Jasa" $\rightarrow$ Isi Data Jasa (Nama Jasa, Tipe Kendaraan, Estimasi Waktu, Harga) $\rightarrow$ Klik Simpan $\rightarrow$ Verifikasi jasa berhasil tercatat di katalog.

#### 3. Fitur Tambah Antrean Servis (Fahim - Admin)
*   **Deskripsi**: Admin mendaftarkan kendaraan pelanggan yang baru datang ke bengkel ke dalam sistem antrean servis.
*   **Alur**: Login Admin $\rightarrow$ Klik menu "Antrean Servis" $\rightarrow$ Klik "Tambah Antrean" $\rightarrow$ Isi Form (Pelanggan, Kendaraan, Keluhan Utama, Hubungkan Mekanik) $\rightarrow$ Klik Simpan $\rightarrow$ Verifikasi antrean berstatus "Menunggu" muncul di papan Kanban.

#### 4. Fitur Buat Transaksi Baru (Akmal - Kasir)
*   **Deskripsi**: Kasir memproses pembayaran untuk kendaraan yang telah selesai diservis oleh mekanik.
*   **Alur**: Login Kasir $\rightarrow$ Klik menu "Kasir / Transaksi" $\rightarrow$ Pilih kendaraan dari antrean berstatus "Selesai Servis" $\rightarrow$ Hitung total item & jasa $\rightarrow$ Masukkan nominal pembayaran $\rightarrow$ Klik Selesaikan Transaksi $\rightarrow$ Verifikasi status transaksi berubah menjadi "Lunas/Selesai".

#### 5. Fitur Inspection Checklist (Hafidz - Mekanik)
*   **Deskripsi**: Mekanik mengisi checklist kondisi fisik kendaraan saat melakukan pemeriksaan kelayakan kendaraan pelanggan.
*   **Alur**: Login Mekanik $\rightarrow$ Klik menu "Antrean Servis" $\rightarrow$ Pilih kendaraan aktif $\rightarrow$ Klik tombol "Mulai Inspeksi" $\rightarrow$ Centang komponen checklist (Oli, Rem, Aki, Ban, Lampu) $\rightarrow$ Simpan Checklist $\rightarrow$ Verifikasi status pemeriksaan pada antrean berubah menjadi "Selesai Inspeksi".

---

## 3. Hasil & Laporan Bug (Bug Report)
Laporan bug dikompilasi secara menyeluruh oleh Hafidz untuk mendata semua anomali yang ditemukan pada ke-5 skenario pengujian di atas. Laporan disimpan dalam file [bug-reports.md](file:///C:/Users/zvwah/OneDrive/Dokumen/KULIAH/TRPL/Sem%204/PPPL/UAS/pppl_uas/e2e-testing/docs/bug-reports.md).
