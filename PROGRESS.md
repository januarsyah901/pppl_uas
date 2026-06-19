# Laporan Progress Pengujian E2E: Rombak Fitur Utama (Role-Based Testing)

**Kelompok**: Janu, Fahim, Akmal, Hafidz \
**Tanggal**: 19 Juni 2026

---

Laporan ini menyajikan ringkasan progres pengerjaan pengujian otomatis end-to-end (E2E) setelah dilakukan **rombak total** skenario pengujian. Sistem Auto Service kini diuji menggunakan pendekatan simulasi peran pengguna (Owner, Admin, Kasir, Mekanik) yang mengakses 5 fitur mandiri secara terpisah dari login hingga selesai.

---

## Pembagian Kerja & Status Implementasi

### 1. Januarsyah Akbar (QA Engineer 1 - Role: Owner)
*   **Progress**:
    *   Inisialisasi *base class* Selenium POM (`BasePage.java`) dan global `Hooks.java`.
    *   Implementasi POM & Step Definitions untuk skenario login Owner.
    *   Implementasi E2E **Fitur Tambah Item di Inventory** (TC-OWN-001 & TC-OWN-002) dan **Tambah Jasa di Katalog Jasa** (TC-OWN-003).
*   **Status**: Selesai (Passed)

### 2. Fahim (QA Engineer 2 - Role: Admin)
*   **Progress**:
    *   Inisialisasi Maven Cucumber Runner (`TestRunner.java`) dan global dependencies.
    *   Pembuatan file BDD Cucumber Gherkin global (`stock_opname_e2e.feature`).
    *   Implementasi POM & Step Definitions untuk **Fitur Tambah Antrean Servis** (TC-ADM-001 & TC-ADM-002) dengan login sebagai Admin.
*   **Status**: Selesai (Passed)

### 3. Akmal (QA Engineer 3 - Role: Kasir)
*   **Progress**:
    *   Merancang test case batas pembayaran kasir.
    *   Implementasi POM & Step Definitions untuk **Fitur Buat Transaksi Baru** (TC-KAS-001 & TC-KAS-002) dengan login sebagai Kasir.
*   **Status**: Selesai (Passed)

### 4. Hafidz (QA Engineer 4 - Role: Mekanik)
*   **Progress**:
    *   Merancang test case checklist inspeksi kendaraan.
    *   Implementasi POM & Step Definitions untuk **Fitur Inspection Checklist** (TC-MEK-001 & TC-MEK-002) dengan login sebagai Mekanik.
    *   Penyusunan laporan bug komprehensif (`bug-reports.md`) dari hasil pengujian kelima fitur tersebut.
*   **Status**: Selesai (Passed)

---

## File Dependensi & Konfigurasi Utama

Semua berkas pengujian diisolasi sepenuhnya di dalam direktori `e2e-testing/` di root proyek:

*   **Runner Test JUnit**: `e2e-testing/src/test/java/com/autoservice/runner/TestRunner.java`
*   **Skenario Feature**: `e2e-testing/src/test/resources/features/stock_opname_e2e.feature`
*   **Page Object (POM)**:
    *   `BasePage.java`
    *   `LoginPage.java`
    *   `InventoryPage.java`
    *   `KatalogJasaPage.java`
    *   `QueuePage.java`
    *   `CashierPage.java`
    *   `InspectionPage.java`
*   **Step Definitions**:
    *   `Hooks.java`
    *   `AuthSteps.java`
    *   `InventorySteps.java`
    *   `KatalogJasaSteps.java`
    *   `QueueSteps.java`
    *   `CashierSteps.java`
    *   `InspectionSteps.java`
*   **Dokumentasi QA**:
    *   `test-cases.md`
    *   `bug-reports.md`
