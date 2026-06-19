Feature: Akmal - Registrasi Kendaraan Baru

  # ============================================================
  # Akmal - Registrasi Kendaraan Baru
  # ============================================================
  @akmal @kendaraan @positive
  Scenario: Akmal - Berhasil mendaftarkan kendaraan baru
    Given pengguna membuka halaman login Auto Service
    When pengguna mengisi username "owner"
    And pengguna mengisi password "owner123"
    And pengguna mengklik tombol login
    Then pengguna seharusnya diarahkan ke halaman Dashboard
    When pengguna navigasi ke halaman "/kendaraan"
    Then pengguna seharusnya berada di halaman Kendaraan
    When pengguna mengklik tombol "Registrasi Baru"
    And pengguna mengisi nomor plat "AB 1234 CD"
    And pengguna mengisi merek kendaraan "Toyota"
    And pengguna mengisi model kendaraan "Avanza"
    And pengguna mengisi tahun kendaraan "2020"
    And pengguna mengisi warna kendaraan "Putih"
    And pengguna memilih pemilik kendaraan
    And pengguna mengklik tombol "Daftarkan Unit"
    Then pengguna seharusnya melihat notifikasi sukses
    And pengguna seharusnya melihat "AB 1234 CD" di daftar kendaraan
