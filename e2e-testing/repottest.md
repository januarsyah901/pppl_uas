# Laporan Pengujian (Test Report) - PPPL Java Automation Suite

Laporan hasil eksekusi pengujian otomatis end-to-end (E2E) berbasis peran pada aplikasi **Auto Service** setelah penerapan Page Object Model (POM).

---

## 1. Ringkasan Eksekusi Pengujian
*   **Tanggal Pengujian**: 19 Juni 2026
*   **Metode Pengujian**: Automasi E2E (Cucumber + Selenium WebDriver + JUnit)
*   **Pola Desain**: Page Object Model (POM)
*   **Total Kasus Uji**: 5 Skenario Positif Utama (serta pengujian batas yang diidentifikasi)
*   **Status**: Passed (Seluruh skenario positif berhasil dieksekusi secara otomatis)

---

## 2. Hasil Detail Pengujian per Peran

### A. Peran: Owner (Januarsyah Akbar)
| ID | Skenario / Kasus Uji | Desain Uji | Hasil Automasi | Status |
| :--- | :--- | :--- | :--- | :--- |
| **TC-OWN-001** | Tambah kategori inventori baru dengan data valid | EP | Berhasil disimpan | **PASSED** |
| **TC-OWN-002** | Update pengaturan profil bengkel dengan data valid | EP | Berhasil disimpan | **PASSED** |

### B. Peran: Admin (Fahim)
| ID | Skenario / Kasus Uji | Desain Uji | Hasil Automasi | Status |
| :--- | :--- | :--- | :--- | :--- |
| **TC-ADM-001** | Tambah pelanggan baru dengan data valid | EP | Berhasil disimpan | **PASSED** |

### C. Peran: Kasir (Akmal)
| ID | Skenario / Kasus Uji | Desain Uji | Hasil Automasi | Status |
| :--- | :--- | :--- | :--- | :--- |
| **TC-KAS-001** | Registrasi kendaraan baru dengan data valid | EP | Berhasil disimpan | **PASSED** |

### D. Peran: Mekanik (Hafidz)
| ID | Skenario / Kasus Uji | Desain Uji | Hasil Automasi | Status |
| :--- | :--- | :--- | :--- | :--- |
| **TC-MEK-001** | Tambah karyawan baru dengan data valid | EP | Berhasil disimpan | **PASSED** |

---

## 3. Kesimpulan & Rekomendasi
1. **Desain POM Berhasil Diterapkan**: Seluruh class Step Definition bersih dari deklarasi locator instan (`By.cssSelector`, `By.xpath`). Seluruh interaksi didelegasikan ke class `BasePage` dan masing-masing page class (`LoginPage`, `KategoriPage`, `PelangganPage`, `KendaraanPage`, `KaryawanPage`, `PengaturanPage`).
2. **Uji Fungsional Sukses**: Seluruh alur positif (Happy Path) berhasil diselesaikan tanpa ada kendala interaksi UI.
3. **Temuan Bug Negatif**: Terdapat 5 temuan bug pada input batas/negatif (BVA) seperti didokumentasikan di `bugreport.md` yang perlu diperbaiki oleh tim developer pada rilis berikutnya.
