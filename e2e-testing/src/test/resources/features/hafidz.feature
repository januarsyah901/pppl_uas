Feature: Hafidz - Manajemen Karyawan

  # ============================================================
  # Hafidz - Tambah Karyawan Baru
  # ============================================================
  @hafidz @karyawan @positive
  Scenario: Hafidz - Berhasil menambahkan karyawan baru
    Given pengguna membuka halaman login Auto Service
    When pengguna mengisi username "owner"
    And pengguna mengisi password "owner123"
    And pengguna mengklik tombol login
    Then pengguna seharusnya diarahkan ke halaman Dashboard
    When pengguna navigasi ke halaman "/karyawan"
    Then pengguna seharusnya berada di halaman Karyawan
    When pengguna mengklik tombol "Tambah Karyawan"
    And pengguna mengisi nama karyawan "Hendra Wijaya"
    And pengguna mengisi username karyawan "hendra_w"
    And pengguna memilih jabatan karyawan "Admin"
    And pengguna mengisi nomor WA karyawan "08155443322"
    And pengguna mengisi password karyawan "123456"
    And pengguna mengklik tombol "Simpan Karyawan"
    Then pengguna seharusnya melihat notifikasi sukses
    And pengguna seharusnya melihat "Hendra Wijaya" di daftar karyawan
