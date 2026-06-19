Feature: End-to-End Stock Opname and Inventory Workflow
  As a warehouse staff / workshop admin,
  I want to be able to log in, search for spare parts, perform stock opname adjustments,
  and view the activity log history to ensure physical stock matches the system.

  Background:
    Given the user opens the Auto Service login page

  @negative @equivalence_partitioning
  Scenario: Failed login authentication with invalid credentials (EP)
    When the user enters email "wrong@service.com"
    And the user enters password "password123"
    And the user clicks the login button
    Then the user should see an error message "Kredensial tidak cocok"

  @positive @boundary_value_analysis @end_to_end
  Scenario: Complete end-to-end stock opname workflow (E2E)
    # Page 1: Login
    When the user enters email "admin@service.com"
    And the user enters password "password123"
    And the user clicks the login button
    
    # Page 2: Dashboard
    Then the user should be redirected to the Dashboard page
    And the user should see the stock summary widgets on the Dashboard
    When the user clicks the "Inventori Stok" menu in the sidebar
    
    # Page 3: Stock Inventory List
    Then the user should be redirected to the Stock List page
    And the user should see the spare parts inventory table
    When the user searches for "Kampas Rem"
    And the user clicks the "Opname" action button for that item
    
    # Page 4: Stock Opname Form
    Then the user should be redirected to the Stock Opname Form page
    And the user should see the item name "Kampas Rem" displayed on the form
    When the user enters physical quantity "15"
    And the user enters notes "Penyesuaian stok fisik rutin bulanan Mei 2026"
    And the user clicks the "Simpan Opname" button
    
    # Page 5: Detail & Log Opname
    Then the user should see a success notification "Opname berhasil disimpan"
    And the user should be redirected to the Opname History Log page
    And the user should see the latest log entry with quantity "15" and notes "Penyesuaian stok fisik rutin bulanan Mei 2026"
    
    # Logout Session
    When the user clicks the "Logout" button in the sidebar
    Then the user should be redirected back to the Auto Service login page
