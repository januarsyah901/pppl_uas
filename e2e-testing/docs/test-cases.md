# Test Cases - PPPL Java Automation Suite (Rombak Baru)

Dokumen ini berisi daftar seluruh kasus uji (test cases) yang dirancang untuk pengujian otomatis end-to-end (E2E) berbasis peran pada aplikasi **Auto Service**.

---

## 1. Modul Owner (Januarsyah Akbar)
*Tujuan: Memverifikasi kemampuan Owner dalam mengelola inventori sparepart dan katalog jasa.*

### A. Fitur: Tambah Item di Inventory
| ID | Skenario / Kasus Uji | Langkah Pengujian | Harapan Hasil | Status |
| :--- | :--- | :--- | :--- | :--- |
| **TC-OWN-001** | Tambah item baru dengan data lengkap & valid (EP) | Login Owner $\rightarrow$ Masuk ke menu Inventori Stok $\rightarrow$ Klik Tambah Item $\rightarrow$ Isi form dengan data valid $\rightarrow$ Simpan. | Item baru berhasil tersimpan dan muncul di tabel daftar inventori. | Passed |
| **TC-OWN-002** | Input harga item bernilai negatif (BVA) | Login Owner $\rightarrow$ Masuk ke menu Inventori Stok $\rightarrow$ Klik Tambah Item $\rightarrow$ Isi harga item `-1000` $\rightarrow$ Simpan. | Form menolak, menampilkan pesan kesalahan "Harga tidak boleh bernilai negatif". | Open (BUG-002) |

### B. Fitur: Tambah Jasa di Katalog Jasa
| ID | Skenario / Kasus Uji | Langkah Pengujian | Harapan Hasil | Status |
| :--- | :--- | :--- | :--- | :--- |
| **TC-OWN-003** | Tambah jasa servis baru dengan data valid (EP) | Login Owner $\rightarrow$ Masuk ke menu Katalog Jasa $\rightarrow$ Klik Tambah Jasa $\rightarrow$ Isi data lengkap $\rightarrow$ Simpan. | Jasa servis baru berhasil tercatat di katalog dan siap dipilih saat pendaftaran antrean. | Passed |

---

## 2. Modul Admin (Fahim)
*Tujuan: Memverifikasi kemampuan Admin dalam mengelola pendaftaran kendaraan pelanggan.*

### Fitur: Tambah Antrean Servis
| ID | Skenario / Kasus Uji | Langkah Pengujian | Harapan Hasil | Status |
| :--- | :--- | :--- | :--- | :--- |
| **TC-ADM-001** | Pendaftaran antrean servis baru untuk pelanggan (EP) | Login Admin $\rightarrow$ Masuk ke menu Antrean Servis $\rightarrow$ Klik Tambah Antrean $\rightarrow$ Isi data pelanggan, kendaraan, mekanik, & keluhan $\rightarrow$ Simpan. | Antrean servis berhasil terdaftar dengan status "Menunggu" di papan Kanban. | Passed |
| **TC-ADM-002** | Pendaftaran antrean tanpa memilih mekanik (BVA) | Login Admin $\rightarrow$ Masuk ke menu Antrean Servis $\rightarrow$ Klik Tambah Antrean $\rightarrow$ Kosongkan pilihan mekanik $\rightarrow$ Simpan. | Antrean berhasil dibuat dan ditugaskan ke "Belum Ada Mekanik". | Passed |

---

## 3. Modul Kasir (Akmal)
*Tujuan: Memverifikasi pemrosesan transaksi pembayaran oleh Kasir.*

### Fitur: Buat Transaksi Baru
| ID | Skenario / Kasus Uji | Langkah Pengujian | Harapan Hasil | Status |
| :--- | :--- | :--- | :--- | :--- |
| **TC-KAS-001** | Membuat transaksi baru untuk kendaraan selesai servis (EP) | Login Kasir $\rightarrow$ Masuk ke menu Kasir $\rightarrow$ Pilih antrean berstatus "Selesai Servis" $\rightarrow$ Proses pembayaran dengan nominal pas $\rightarrow$ Selesaikan. | Transaksi tersimpan sebagai "Lunas", antrean keluar dari papan kerja, dan struk tercetak. | Passed |
| **TC-KAS-002** | Pembayaran dengan uang kurang dari total tagihan (BVA) | Login Kasir $\rightarrow$ Masuk ke menu Kasir $\rightarrow$ Pilih antrean servis $\rightarrow$ Masukkan nominal bayar di bawah total tagihan $\rightarrow$ Simpan. | Transaksi ditolak, muncul peringatan "Jumlah pembayaran kurang". | Open (BUG-003) |

---

## 4. Modul Mekanik (Hafidz)
*Tujuan: Memverifikasi pengisian checklist pemeriksaan oleh Mekanik.*

### Fitur: Inspection Checklist
| ID | Skenario / Kasus Uji | Langkah Pengujian | Harapan Hasil | Status |
| :--- | :--- | :--- | :--- | :--- |
| **TC-MEK-001** | Pengisian checklist kondisi fisik kendaraan aktif (EP) | Login Mekanik $\rightarrow$ Masuk ke menu Antrean Servis $\rightarrow$ Pilih kendaraan aktif $\rightarrow$ Klik Mulai Inspeksi $\rightarrow$ Centang item checklist $\rightarrow$ Simpan. | Checklist tersimpan, status antrean berubah menjadi "Selesai Inspeksi" / "Dalam Pengerjaan". | Passed |
| **TC-MEK-002** | Menyimpan checklist kosong tanpa mencentang apa pun (BVA) | Login Mekanik $\rightarrow$ Masuk ke menu Antrean Servis $\rightarrow$ Klik Mulai Inspeksi $\rightarrow$ Langsung klik Simpan tanpa centang. | Sistem memberikan peringatan "Harap centang minimal satu komponen pemeriksaan". | Open (BUG-004) |
