Feature: End-to-End Single-Role Workshop Management Workflow

  @janu @settings @positive
  Scenario: Success updating workshop settings (Janu)
    Given the user opens the Auto Service login page
    When the user enters username "owner"
    And the user enters password "owner123"
    And the user clicks the login button
    Then the user should be redirected to the Dashboard page
    When the user clicks the "Pengaturan" menu in the sidebar
    Then the user should be redirected to the Settings page
    When the user enters workshop name "Bengkel Simple"
    And the user enters workshop phone "08123456789"
    And the user enters workshop address "Jl. Malioboro No. 24"
    And the user clicks the "Simpan Profil" button
    Then the user should see a success notification "Pengaturan berhasil disimpan"

  @janu @kategori @positive
  Scenario: Success adding a new category (Janu)
    Given the user opens the Auto Service login page
    When the user enters username "owner"
    And the user enters password "owner123"
    And the user clicks the login button
    Then the user should be redirected to the Dashboard page
    When the user clicks the "Kategori & Satuan" menu in the sidebar
    Then the user should be redirected to the Category Management page
    When the user clicks the "Tambah Kategori" button
    And the user enters category name "Oli & Pelumas"
    And the user enters category description "Semua jenis oli mesin dan pelumas"
    And the user clicks the "Simpan Kategori" button
    Then the user should see a success notification "Kategori berhasil ditambahkan"
    And the user should see the category "Oli & Pelumas" in the category table

  @fahim @pelanggan @positive
  Scenario: Success adding a new customer (Fahim)
    Given the user opens the Auto Service login page
    When the user enters username "owner"
    And the user enters password "owner123"
    And the user clicks the login button
    Then the user should be redirected to the Dashboard page
    When the user clicks the "Pelanggan" menu in the sidebar
    Then the user should be redirected to the Customer Management page
    When the user clicks the "Tambah Pelanggan" button
    And the user enters customer name "Budi Santoso"
    And the user enters customer phone "08123456789"
    And the user enters customer address "Jl. Malioboro No. 12"
    And the user clicks the "Simpan Pelanggan" button
    Then the user should see a success notification "Pelanggan berhasil ditambahkan"
    And the user should see the customer "Budi Santoso" in the customer table

  @akmal @karyawan @positive
  Scenario: Success adding a new employee (Akmal)
    Given the user opens the Auto Service login page
    When the user enters username "owner"
    And the user enters password "owner123"
    And the user clicks the login button
    Then the user should be redirected to the Dashboard page
    When the user clicks the "Karyawan" menu in the sidebar
    Then the user should be redirected to the Employee Management page
    When the user clicks the "Tambah Karyawan" button
    And the user enters employee name "Hendra Wijaya"
    And the user enters employee username "hendra_w"
    And the user enters employee phone "08198765432"
    And the user enters employee password "hendra123"
    And the user clicks the "Simpan Karyawan" button
    Then the user should see a success notification "Karyawan berhasil ditambahkan"
    And the user should see the employee "Hendra Wijaya" in the employee table

  @hafidz @kendaraan @positive
  Scenario: Success adding a new vehicle (Hafidz)
    Given the user opens the Auto Service login page
    When the user enters username "owner"
    And the user enters password "owner123"
    And the user clicks the login button
    Then the user should be redirected to the Dashboard page
    When the user clicks the "Kendaraan" menu in the sidebar
    Then the user should be redirected to the Vehicle Management page
    When the user clicks the "Registrasi Baru" button
    And the user enters plate number "AB 1234 CD"
    And the user enters vehicle brand "Toyota"
    And the user enters vehicle model "Avanza"
    And the user enters vehicle year "2020"
    And the user clicks the "Daftarkan Unit" button
    Then the user should see a success notification "Kendaraan berhasil ditambahkan"
    And the user should see the vehicle "AB 1234 CD" in the vehicle table
