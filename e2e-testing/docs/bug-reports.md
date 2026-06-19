# Bug Reports - Auto Service Testing

This document lists all the bug reports found during the testing phase.

---

## Bug Report: BUG-001

*   **Bug ID**: BUG-001
*   **Title**: Ketiadaan Validasi Angka Negatif pada Input Jumlah Fisik Aktual (BVA-001)
*   **Date**: 19 Juni 2026
*   **Environment**: Local Development (fe-opname & be-opname)
*   **Browser**: Chromium (Selenium WebDriver)
*   **Severity**: High
*   **Priority**: High
*   **Preconditions**:
    *   Backend API server (`be-opname`) dan Frontend Next.js (`fe-opname`) sudah berjalan.
    *   Penguji sudah masuk log (login) sebagai Admin/Petugas Gudang.
    *   Terdapat item barang aktif di database (contoh: `"Kampas Rem"`).
*   **Steps to Reproduce**:
    1. Buka browser dan login ke aplikasi.
    2. Navigasi ke menu **Katalog Sparepart** lalu klik sub-menu **Stok Opname** (`/inventori/opname`).
    3. Klik tombol **Mulai Sesi Opname Baru** dan konfirmasi dengan klik **Mulai Sekarang**.
    4. Pada baris item `"Kampas Rem"`, ketik nilai negatif secara manual di input kolom **Aktual** (contoh: `-10`).
    5. Klik tombol **Tutup Sesi & Apply**.
*   **Expected Result**:
    *   Formulir menolak input nilai negatif, menampilkan pesan kesalahan, dan tombol untuk menyelesaikan sesi dinonaktifkan sehingga database tidak berubah.
*   **Actual Result**:
    *   Formulir menerima input negatif, sistem memproses perhitungan selisih, menutup sesi opname, menampilkan pesan toast sukses `"Sesi opname ditutup & stok diupdate!"`, dan stok barang di database berubah menjadi bernilai negatif.
*   **Evidence**:
    *   Stok barang di halaman inventori/database terverifikasi bernilai `-10` setelah sesi opname selesai diterapkan.
*   **Status**: Open
*   **Notes**:
    *   Perlu penambahan validasi pada event `onChange` input di berkas frontend [StockOpnameTable.tsx](file:///Users/fizualstd/Documents/GitHub/pppl_uas/fe-opname/src/features/inventori/components/StockOpnameTable.tsx) dan skema validasi request body di controller backend [opnameController.ts](file:///Users/fizualstd/Documents/GitHub/pppl_uas/be-opname/src/controllers/opnameController.ts).

---

## Bug Report: BUG-002

*   **Bug ID**: BUG-002
*   **Title**: Ketiadaan Pembatasan Karakter Maksimal Password pada Halaman Login (EP-004)
*   **Date**: 19 Juni 2026
*   **Environment**: Local Development (fe-opname & be-opname)
*   **Browser**: Chromium (Selenium WebDriver)
*   **Severity**: Medium
*   **Priority**: Medium
*   **Preconditions**:
    *   Halaman login (`/auth/sign-in`) telah terbuka di browser.
*   **Steps to Reproduce**:
    1. Buka halaman login.
    2. Masukkan email/username valid.
    3. Pada kolom **Password**, masukkan teks acak dengan panjang lebih dari 100 karakter (melebihi batas kelas ekivalen 20 karakter pada rancangan `TC-EP-004`).
    4. Klik tombol **Login**.
*   **Expected Result**:
    *   Frontend mendeteksi input melebihi batas panjang karakter maksimal dan menampilkan pesan validasi *"Password maksimal 20 karakter"*.
*   **Actual Result**:
    *   Formulir login tetap melakukan proses submit request ke backend tanpa melakukan validasi panjang karakter maksimal terlebih dahulu di sisi client-side.
*   **Evidence**:
    *   Pengguna dapat mengetikkan karakter password tanpa batas pada elemen input form login dan tombol submit tetap aktif.
*   **Status**: Open
*   **Notes**:
    *   Perlu penambahan skema validasi panjang karakter minimal (8) dan maksimal (20) menggunakan Zod/Yup validator di form halaman login frontend Next.js.
