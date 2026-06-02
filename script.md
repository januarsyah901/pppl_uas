# Script Presentasi Progress Pengujian E2E (Januarsyah Akbar)

Naskah ini dirancang untuk memandu presentasi demo progres pengerjaan praktikum Pengujian Perangkat Lunak. Script dibagi menjadi durasi waktu, visual layar (apa yang ditampilkan), dan narasi lisan.

---

## 🎙️ Detail Alur dan Naskah Presentasi

### 1. Pembukaan dan Latar Belakang (0:00 – 0:45)
**Visual Layar:** Menampilkan halaman depan berkas `PROGRESS.md` atau `TEST_PLANNING.md`.
**Narasi:**
> "Halo semuanya, selamat pagi/siang asisten praktikum dan rekan-rekan sekalian. Saya Januarsyah Akbar. Pada kesempatan kali ini, saya akan mendemonstrasikan progres pengerjaan tugas proyek akhir praktikum Pengujian Perangkat Lunak untuk kelompok kami.
> 
> Tanggung jawab utama saya dalam proyek ini berfokus pada inisiasi dan pembangunan seluruh fondasi awal *testing framework* menggunakan teknologi CucumberJS BDD, Playwright Automation, dan Page Object Model, serta mengimplementasikan skenario uji otomatis untuk Halaman 1, yaitu halaman Login/Otentikasi."

---

### 2. Penjelasan Infrastruktur dan Struktur Folder (0:45 – 1:45)
**Visual Layar:** Menampilkan struktur direktori `e2e-testing/` di VS Code / editor kode.
**Narasi:**
> "Mari kita lihat struktur proyek pengujian yang sudah berhasil dibangun. Seluruh kode pengujian diisolasi sepenuhnya di dalam direktori `e2e-testing/` agar tidak bercampur dengan kode utama aplikasi.
> 
> Ada beberapa file konfigurasi utama yang sudah saya selesaikan:
> 1. **`package.json`**: Berfungsi mengelola dependensi penting seperti `@cucumber/cucumber` untuk mesin BDD, `playwright` untuk otomasi browser, dan `cucumber-html-reporter` untuk laporan pengujian.
> 2. **`cucumber.js`**: Mengatur konfigurasi pencarian file fitur, pemetaan berkas *step definitions*, serta format keluaran data laporan berbentuk JSON.
> 3. **`support/hooks.js`**: Mengontrol siklus hidup browser Playwright. Di sini saya juga mengimplementasikan fungsi otomatis untuk menangkap *screenshot* (tangkapan layar) secara langsung jika ada *step* pengujian yang gagal, yang kemudian disematkan langsung ke laporan akhir."

---

### 3. Pemaparan Desain POM Dasar dan Skenario BDD (1:45 – 3:00)
**Visual Layar:** Membuka berkas `page_objects/BasePage.js`, `page_objects/LoginPage.js`, dan `features/stock_opname_e2e.feature`.
**Narasi:**
> "Selanjutnya untuk arsitektur penulisan kode, kami menerapkan **Page Object Model (POM)** untuk memisahkan logika interaksi halaman dengan skenario tes. 
> 
> Saya membuat **`BasePage.js`** sebagai kelas induk. Berkas ini bertindak sebagai pembungkus (*wrapper*) untuk API bawaan Playwright seperti klik, navigasi, pengisian input, dan menunggu elemen stabil. Hal ini memastikan kode kami *anti-flaky* saat halaman memuat lambat.
> 
> Dari kelas induk tersebut, saya membuat **`LoginPage.js`** khusus untuk mengontrol interaksi di form login, lengkap dengan validasi error.
> 
> Skenario pengujian ditulis menggunakan format Gherkin bahasa Inggris standar di berkas **`stock_opname_e2e.feature`**. Di sini, saya merancang dan menguji skenario login negatif berdasarkan metode *Equivalence Partitioning*, yaitu kasus **`TC-EP-002`** untuk menguji penolakan sistem saat memasukkan kredensial yang salah."

---

### 4. Live Demo Eksekusi Pengujian Otomatis (3:00 – 4:15)
**Visual Layar:** Buka terminal di editor, jalankan perintah `npm test` di dalam folder `e2e-testing/`. Tampilkan log jalannya pengujian hingga selesai.
**Narasi:**
> "Sekarang, mari kita jalankan pengujiannya secara langsung menggunakan perintah `npm test`.
> 
> Saat tes dijalankan, Playwright di latar belakang akan membuka browser Chromium, melakukan navigasi ke `http://localhost:3333/auth/sign-in`, mengetik email salah, kata sandi salah, dan menekan tombol login.
> 
> Bisa kita lihat pada keluaran terminal di layar, skenario negatif **berhasil lulus 100% (Passed)**. Sistem berhasil menangkap pesan kesalahan validasi *"Kredensial tidak cocok"* tepat setelah tombol masuk ditekan. Hal ini membuktikan bahwa *glue code* step definitions dan logika Page Object yang saya rancang telah bekerja dengan sangat akurat dan terhubung sempurna ke frontend aplikasi."

---

### 5. Penutup dan Serah Terima Tugas (4:15 – 4:45)
**Visual Layar:** Menampilkan halaman `TEST_PLANNING.md` di kolom status `TC-EP-002` yang sudah dicentang lulus.
**Narasi:**
> "Dengan selesainya inisiasi arsitektur dasar ini, status pengujian negatif login di berkas perencanaan `TEST_PLANNING.md` telah resmi terverifikasi dan lulus. 
> 
> Seluruh kerangka kerja ini juga sudah 100% siap dan stabil untuk dilanjutkan oleh anggota kelompok lainnya guna mengimplementasikan halaman berikutnya sesuai dengan porsi pembagian tugas masing-masing.
> 
> Demikian presentasi progres dari saya, kurang lebihnya saya mohon maaf. Terima kasih atas perhatiannya, saya kembalikan ke asisten praktikum."

---

## 📋 Panduan Tambahan Sebelum Demonstrasi
*   **Persiapan Awal:** Pastikan backend server (`localhost:3000`) dan frontend Next.js (`localhost:3333`) sudah menyala sebelum demonstrasi dimulai agar robot Playwright bisa melakukan navigasi tanpa kendala koneksi.
*   **Mode Visual (Headed):** Jika asisten ingin melihat browser bergerak secara visual di layar saat demo, ubah opsi `headless: true` menjadi `headless: false` di berkas `support/hooks.js` sebelum menjalankan `npm test`.
