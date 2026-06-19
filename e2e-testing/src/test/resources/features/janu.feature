Feature: Januarsyah - Manajemen Kategori Inventori dan Pengaturan Bengkel

  # ============================================================
  # Janu - Fitur 1: Tambah Kategori Inventori
  # ============================================================
  @janu @kategori @positive
  Scenario: Janu - Berhasil menambahkan kategori inventori baru
    Given pengguna membuka halaman login Auto Service
    When pengguna mengisi username "owner"
    And pengguna mengisi password "owner123"
    And pengguna mengklik tombol login
    Then pengguna seharusnya diarahkan ke halaman Dashboard
    When pengguna navigasi ke halaman "/inventori/kategori"
    Then pengguna seharusnya berada di halaman Kategori
    When pengguna mengklik tombol "Tambah Kategori"
    And pengguna mengisi nama kategori "Oli & Pelumas"
    And pengguna mengklik tombol "Simpan Kategori"
    Then pengguna seharusnya melihat notifikasi sukses

  # ============================================================
  # Janu - Fitur 2: Update Pengaturan Bengkel
  # ============================================================
  @janu @pengaturan @positive
  Scenario: Janu - Berhasil memperbarui pengaturan profil bengkel
    Given pengguna membuka halaman login Auto Service
    When pengguna mengisi username "owner"
    And pengguna mengisi password "owner123"
    And pengguna mengklik tombol login
    Then pengguna seharusnya diarahkan ke halaman Dashboard
    When pengguna navigasi ke halaman "/pengaturan"
    Then pengguna seharusnya berada di halaman Pengaturan
    When pengguna mengisi nama bengkel "Bengkel Maju Jaya"
    And pengguna mengisi nomor WA bengkel "08123456789"
    And pengguna mengisi alamat bengkel "Jl. Sudirman No. 10, Yogyakarta"
    And pengguna mengklik tombol "Simpan Profil"
    Then pengguna seharusnya melihat notifikasi sukses
