# Script Presentasi Progres Pengujian E2E (Setelah Rombak Fitur)

Naskah ini dirancang untuk memandu presentasi demo progres pengerjaan praktikum Pengujian Perangkat Lunak (PPPL) setelah sistem testing dirombak total menggunakan pendekatan pengujian per peran (role-based testing).

---

## 🎙️ Detail Alur dan Naskah Presentasi Kelompok

### 1. Pembukaan dan Latar Belakang Perubahan (0:00 – 1:00)
**Visual Layar:** Menampilkan halaman utama berkas `TEST_PLANNING.md` yang baru di VS Code.
**Narasi:**
> "Halo semuanya, selamat pagi/siang asisten praktikum dan rekan-rekan sekalian. Kami dari tim QA Automation Auto Service akan mempresentasikan progres pengerjaan tugas proyek akhir kami. 
> 
> Berbeda dengan alur pengujian sebelumnya yang dibagi berdasarkan pemotongan halaman, kali ini kami melakukan **rombak total** dengan menerapkan pengujian berbasis **peran pengguna (role-based testing)** secara end-to-end. Skenario uji disimulasikan secara mandiri dari awal login hingga fitur selesai digunakan (full 1 fitur). Total terdapat 5 skenario fitur utama dengan 4 peran berbeda yang kami uji."

---

### 2. Penjelasan Pembagian Fitur & Peran Anggota (1:00 – 2:30)
**Visual Layar:** Menampilkan tabel Pembagian Tugas Kelompok di file `README.md`.
**Narasi:**
> "Berikut adalah pembagian fitur dan tanggung jawab pengujian dari masing-masing anggota kelompok kami:
> 
> 1. **Januarsyah Akbar** bertanggung jawab atas inisiasi framework Selenium dan menguji peran **Owner** dengan 2 fitur utama: Tambah Item Baru di Inventory dan Tambah Jasa Baru di Katalog Jasa.
> 2. **Fahim** mengonfigurasi runner Cucumber dan menguji peran **Admin** dengan fitur Tambah Antrean Servis baru untuk pelanggan.
> 3. **Akmal** menguji peran **Kasir** dengan skenario pembuatan transaksi pembayaran baru untuk antrean servis yang selesai.
> 4. **Hafidz** menguji peran **Mekanik** dengan fitur pengisian Inspection Checklist kendaraan aktif, sekaligus menyusun laporan bug komprehensif dari hasil pengetesan kelima fitur tersebut."

---

### 3. Pemaparan Skenario BDD Gherkin (2:30 – 4:00)
**Visual Layar:** Membuka berkas skenario BDD Gherkin di `features/stock_opname_e2e.feature`.
**Narasi:**
> "Mari kita lihat berkas spesifikasi skenario BDD kami. Karena setiap pengujian diibaratkan seperti pengguna nyata yang mengakses sistem, maka masing-masing skenario pengujian memiliki langkah login tersendiri sesuai dengan hak akses perannya. 
> 
> Misalnya pada skenario Owner, langkah pertama dimulai dengan login sebagai Owner, masuk ke menu Inventori/Katalog Jasa, melakukan input data, klik simpan, dan memverifikasi data di database. Alur yang sama berlaku untuk Admin, Kasir, dan Mekanik, sehingga pengujian berjalan secara independen dan terisolasi."

---

### 4. Demonstrasi Eksekusi & Laporan Bug (4:00 – 5:30)
**Visual Layar:** Menampilkan terminal yang menjalankan perintah `mvn test` lalu membuka file laporan bug `docs/bug-reports.md`.
**Narasi:**
> "Kami telah menjalankan seluruh rangkaian tes menggunakan Selenium Maven. Hasil pengujian menunjukkan bahwa kerangka kerja telah berjalan dengan sangat baik dan stabil.
> 
> Dari hasil eksekusi pengujian ini, kami mengidentifikasi 5 *bug* utama yang berhasil didokumentasikan di berkas `bug-reports.md`. Beberapa di antaranya adalah:
> - Masalah input harga negatif pada menu Owner.
> - Kerentanan duplikasi pelat nomor pada antrean Admin.
> - Kasir dapat memproses transaksi dengan nominal kurang dari tagihan.
> - Mekanik dapat menyimpan checklist inspeksi dalam kondisi kosong.
> 
> Laporan bug ini sangat membantu developer kami untuk meningkatkan kualitas dan validasi data di sisi aplikasi utama."

---

### 5. Penutup (5:30 – 6:00)
**Visual Layar:** Menampilkan grafik kesimpulan atau halaman laporan HTML otomatis Cucumber.
**Narasi:**
> "Sebagai kesimpulan, transisi ke pengujian berbasis peran ini memberikan cakupan uji (test coverage) yang jauh lebih realistis karena meniru alur kerja operasional bengkel sesungguhnya di lapangan. Seluruh kode pengujian kami telah diatur rapi dan siap didemonstrasikan lebih lanjut.
> 
> Sekian presentasi dari kelompok kami. Terima kasih atas perhatian asisten praktikum dan rekan-rekan sekalian."
