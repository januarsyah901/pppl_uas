# Script Presentasi Progres Pengujian E2E (Setelah Rombak Fitur)

Naskah ini dirancang untuk memandu presentasi demo progres pengerjaan praktikum Pengujian Perangkat Lunak (PPPL) setelah sistem testing dirombak total. Setiap skenario pengujian disimulasikan secara mandiri dari awal login hingga fitur selesai digunakan (full 1 fitur, 1 file per anggota).

---

## 🎙️ Detail Alur dan Naskah Presentasi Kelompok

### 1. Pembukaan dan Latar Belakang Perubahan (0:00 – 1:00)
**Visual Layar:** Menampilkan halaman utama berkas `TEST_PLANNING.md` di VS Code.
**Narasi:**
> "Halo semuanya, selamat pagi/siang asisten praktikum dan rekan-rekan sekalian. Kami dari tim QA Automation Auto Service akan mempresentasikan progres pengerjaan tugas proyek akhir kami.
>
> Berbeda dengan alur pengujian sebelumnya, kali ini kami melakukan **rombak total** dengan menerapkan pengujian berbasis **fitur operasional bengkel** secara end-to-end. Setiap skenario uji dimulai dari proses login menggunakan akun owner, kemudian mengakses halaman fitur terkait, mengisi form, dan memverifikasi hasilnya. Total terdapat **5 skenario fitur** yang dibagi rata kepada 4 anggota — Janu mendapat 2 fitur, sedangkan Fahim, Akmal, dan Hafidz masing-masing mendapat 1 fitur."

---

### 2. Penjelasan Pembagian Fitur & Peran Anggota (1:00 – 2:30)
**Visual Layar:** Menampilkan tabel Pembagian Tugas Kelompok di file `README.md`.
**Narasi:**
> "Berikut adalah pembagian fitur dan tanggung jawab pengujian dari masing-masing anggota kelompok kami:
>
> 1. **Januarsyah Akbar** bertanggung jawab atas inisialisasi framework Selenium dan menguji 2 fitur: **Tambah Kategori Inventori** dan **Update Pengaturan Bengkel**.
> 2. **Fahim** mengonfigurasi runner Cucumber JUnit dan menguji fitur **Tambah Pelanggan Baru**.
> 3. **Akmal** menguji fitur **Registrasi Kendaraan Baru** beserta alur pemilihan pemilik kendaraan dari sistem.
> 4. **Hafidz** menguji fitur **Tambah Karyawan Baru** dengan pengisian form lengkap termasuk pemilihan jabatan, sekaligus menyusun laporan bug komprehensif dari hasil pengetesan kelima fitur tersebut."

---

### 3. Pemaparan Skenario BDD Gherkin (2:30 – 4:00)
**Visual Layar:** Membuka berkas skenario BDD Gherkin, misalnya `features/janu.feature`.
**Narasi:**
> "Mari kita lihat berkas spesifikasi skenario BDD kami. Kami memisahkan file feature per anggota — ada `janu.feature`, `fahim.feature`, `akmal.feature`, dan `hafidz.feature` — sehingga setiap anggota memiliki skenario yang mandiri dan terisolasi.
>
> Pada setiap skenario, langkah pertama selalu dimulai dengan login menggunakan akun `owner` dan password `owner123`, lalu diarahkan ke halaman fitur terkait, mengisi form yang disediakan, dan memverifikasi notifikasi keberhasilan. Pendekatan ini memastikan setiap skenario dapat dijalankan secara independen tanpa bergantung pada skenario lain."

---

### 4. Demonstrasi Eksekusi Test (4:00 – 5:30)
**Visual Layar:** Menampilkan terminal yang menjalankan perintah `mvn test` lalu membuka file laporan HTML.
**Narasi:**
> "Kami telah menjalankan seluruh rangkaian tes menggunakan Selenium Maven. Untuk menjalankan test per anggota, cukup gunakan perintah berikut dari folder `e2e-testing`:
>
> - `mvn test -Dcucumber.filter.tags="@janu"` untuk skenario Janu
> - `mvn test -Dcucumber.filter.tags="@fahim"` untuk skenario Fahim
> - `mvn test -Dcucumber.filter.tags="@akmal"` untuk skenario Akmal
> - `mvn test -Dcucumber.filter.tags="@hafidz"` untuk skenario Hafidz
> - `mvn test` untuk menjalankan semua skenario sekaligus
>
> Hasil pengujian skenario Janu telah berhasil dijalankan dengan status **2/2 PASSED**. Laporan HTML otomatis dihasilkan di folder `target/cucumber-reports/cucumber.html` yang menampilkan ringkasan visual setiap langkah skenario.
>
> Dari hasil eksekusi, kami juga mengidentifikasi beberapa anomali yang telah didokumentasikan di `bug-reports.md` oleh Hafidz."

---

### 5. Penutup (5:30 – 6:00)
**Visual Layar:** Menampilkan laporan HTML otomatis Cucumber di browser.
**Narasi:**
> "Sebagai kesimpulan, pendekatan pengujian berbasis fitur operasional ini memberikan cakupan uji yang lebih realistis karena meniru alur kerja operasional bengkel sesungguhnya. Setiap anggota memiliki file feature dan step definitions tersendiri sehingga pengembangan pengujian dapat dilakukan secara paralel tanpa konflik.
>
> Seluruh kode pengujian telah diatur rapi menggunakan pola Page Object Model dan siap didemonstrasikan lebih lanjut. Terima kasih atas perhatian asisten praktikum dan rekan-rekan sekalian."
