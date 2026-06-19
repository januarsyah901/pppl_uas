# Laporan Bug (Bug Report) - Auto Service E2E Testing

Dokumen ini mendata seluruh *bug* / anomali yang ditemukan selama pengujian otomatis terhadap fitur Auto Service.

---

## BUG-001: Ketiadaan Validasi Input Harga Negatif pada Halaman Tambah Item
*   **Bug ID**: BUG-001
*   **Fitur**: Tambah Item di Inventory
*   **Severity**: High | **Priority**: High
*   **Preconditions**:
    *   Penguji login sebagai Owner.
    *   Halaman penambahan item baru terbuka.
*   **Steps to Reproduce**:
    1. Klik tombol **Tambah Item** di menu Inventori.
    2. Masukkan nama item `"Oli Shell Helix"`.
    3. Masukkan harga beli/jual bernilai negatif (contoh: `-50000`).
    4. Klik **Simpan**.
*   **Expected Result**:
    *   Sistem memunculkan pesan error "Harga tidak boleh bernilai negatif" dan mencegah penyimpanan data.
*   **Actual Result**:
    *   Sistem berhasil menyimpan item dengan harga negatif, dan data tercatat di database serta tabel inventori.
*   **Status**: Open

---

## BUG-002: Layout Pecah Akibat Ketiadaan Batas Karakter Nama Jasa
*   **Bug ID**: BUG-002
*   **Fitur**: Tambah Jasa di Katalog Jasa
*   **Severity**: Medium | **Priority**: Medium
*   **Preconditions**:
    *   Penguji login sebagai Owner.
*   **Steps to Reproduce**:
    1. Buka menu Katalog Jasa -> Klik **Tambah Jasa**.
    2. Isi kolom nama jasa dengan teks sepanjang lebih dari 200 karakter.
    3. Klik **Simpan**.
*   **Expected Result**:
    *   Sistem membatasi input maksimal 100 karakter atau memotong input secara otomatis.
*   **Actual Result**:
    *   Input diterima, dan nama jasa yang sangat panjang merusak visual/layout kolom tabel katalog jasa.
*   **Status**: Open

---

## BUG-003: Duplikasi Pelat Nomor Kendaraan pada Antrean Aktif
*   **Bug ID**: BUG-003
*   **Fitur**: Tambah Antrean Servis
*   **Severity**: High | **Priority**: High
*   **Preconditions**:
    *   Penguji login sebagai Admin.
    *   Terdapat kendaraan dengan pelat nomor `"AB 1234 CD"` yang sedang dalam status antrean "Menunggu".
*   **Steps to Reproduce**:
    1. Klik **Tambah Antrean** di menu Antrean.
    2. Daftarkan kembali kendaraan dengan pelat nomor yang sama `"AB 1234 CD"`.
    3. Klik **Simpan**.
*   **Expected Result**:
    *   Sistem mendeteksi pelat nomor duplikat yang sedang aktif servis dan memunculkan error "Kendaraan masih dalam antrean aktif".
*   **Actual Result**:
    *   Antrean baru berhasil dibuat sehingga terdapat dua antrean aktif untuk satu kendaraan yang sama secara bersamaan.
*   **Status**: Open

---

## BUG-004: Penerimaan Transaksi Pembayaran Kurang Tanpa Validasi
*   **Bug ID**: BUG-004
*   **Fitur**: Buat Transaksi Baru
*   **Severity**: Critical | **Priority**: High
*   **Preconditions**:
    *   Penguji login sebagai Kasir.
    *   Terdapat antrean siap bayar dengan total tagihan Rp 150.000.
*   **Steps to Reproduce**:
    1. Pilih antrean kendaraan siap bayar di menu Kasir.
    2. Pada input **Jumlah Bayar / Uang Diterima**, masukkan Rp 100.000 (kurang Rp 50.000).
    3. Klik **Bayar & Selesaikan**.
*   **Expected Result**:
    *   Sistem menolak pembayaran dan memunculkan error "Uang bayar kurang dari total tagihan".
*   **Actual Result**:
    *   Transaksi sukses diproses dengan status "Lunas/Selesai" dan memicu nilai kembalian negatif di sistem.
*   **Status**: Open

---

## BUG-005: Pengiriman Checklist Kosong Berhasil Disimpan
*   **Bug ID**: BUG-005
*   **Fitur**: Inspection Checklist
*   **Severity**: Medium | **Priority**: Medium
*   **Preconditions**:
    *   Penguji login sebagai Mekanik.
*   **Steps to Reproduce**:
    1. Buka antrean kendaraan aktif -> klik **Mulai Inspeksi**.
    2. Jangan mencentang satu pun komponen pemeriksaan (rem, ban, aki, oli, dll.).
    3. Langsung klik tombol **Simpan Checklist**.
*   **Expected Result**:
    *   Sistem menampilkan peringatan "Minimal satu pemeriksaan harus dicentang sebelum disimpan".
*   **Actual Result**:
    *   Checklist kosong berhasil disimpan dan status kendaraan berubah menjadi "Selesai Inspeksi".
*   **Status**: Open
