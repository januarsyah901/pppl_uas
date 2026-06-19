Feature: Fahim - Manajemen Pelanggan

  # ============================================================
  # Fahim - Tambah Pelanggan Baru
  # ============================================================
  @fahim @pelanggan @positive
  Scenario: Fahim - Berhasil menambahkan pelanggan baru
    Given pengguna membuka halaman login Auto Service
    When pengguna mengisi username "owner"
    And pengguna mengisi password "owner123"
    And pengguna mengklik tombol login
    Then pengguna seharusnya diarahkan ke halaman Dashboard
    When pengguna navigasi ke halaman "/pelanggan"
    Then pengguna seharusnya berada di halaman Pelanggan
    When pengguna mengklik tombol "Tambah Pelanggan"
    And pengguna mengisi nama pelanggan "Budi Santoso"
    And pengguna mengisi nomor WA pelanggan "08198765432"
    And pengguna mengisi alamat pelanggan "Jl. Malioboro No. 12, Yogyakarta"
    And pengguna mengklik tombol "Simpan Pelanggan"
    Then pengguna seharusnya melihat notifikasi sukses
    And pengguna seharusnya melihat "Budi Santoso" di daftar pelanggan
