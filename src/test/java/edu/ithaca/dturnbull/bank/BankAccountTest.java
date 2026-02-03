package edu.ithaca.dturnbull.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


class BankAccountTest {
    
    // Equivalence class: initial balance after account creation
    @Test
    void getBalance_afterWithdraw() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        bankAccount.withdraw(50);

        assertEquals(150, bankAccount.getBalance(), 0.001);
    }

    // Equivalence class: 0 < amount < balance
    @Test
    void withdraw_validAmount() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
    }

    // Equivalence class: amount == balance (edge case)
    @Test
    void withdraw_exactBalance() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        bankAccount.withdraw(200);

        assertEquals(0, bankAccount.getBalance(), 0.001);
    }

    // Equivalence class: amount > balance
    @Test
    void withdraw_overBalance() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertThrows(InsufficientFundsException.class,
            () -> bankAccount.withdraw(300));
    }
    @Test
    void withdraw_invalidAmounts() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        // Negative amount
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class,
            () -> bankAccount.withdraw(-10));
        // optional: assert something about message
        // assertTrue(ex1.getMessage().contains("Invalid"));

        // More than 2 decimal places
        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class,
            () -> bankAccount.withdraw(10.999));
    }
    
    // EC1: valid deposit amount
    @Test
    void deposit_validAmount() {
        BankAccount bankAccount = new BankAccount("a@b.com", 100);
        bankAccount.deposit(50);
        assertEquals(150, bankAccount.getBalance(), 0.001);
    }

    // EC2: deposit zero (boundary case)
    @Test
    void deposit_zeroAmount() {
        BankAccount bankAccount = new BankAccount("a@b.com", 100);
        bankAccount.deposit(0);
        assertEquals(100, bankAccount.getBalance(), 0.001);
    }

    // EC3: negative deposit
    @Test
    void deposit_negativeAmount() {
        BankAccount bankAccount = new BankAccount("a@b.com", 100);

        assertThrows(IllegalArgumentException.class,
            () -> bankAccount.deposit(-10));
    }

    // EC4: too many decimal places
    @Test
    void deposit_tooManyDecimals() {
        BankAccount bankAccount = new BankAccount("a@b.com", 100);

        assertThrows(IllegalArgumentException.class,
            () -> bankAccount.deposit(10.999));
    }


    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        assertFalse(BankAccount.isEmailValid(""));         // empty string
        
        // Equivalence class: within-bounds username, within-bounds domain, within-bounds tld
        // Border case: NOT border case for username, NOT border case for domain, YES border case for tld
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com"));

        // Equivalence class: within-bounds username, within-bounds domain, within-bounds tld
        // Border case: NOT border case for username, NOT border case for domain, YES border case for tld
        assertTrue(BankAccount.isEmailValid("abc_d@mail.cc"));

        // Equivalence class: within-bounds username, within-bounds domain, within-bounds tld
        // Border case: NOT border case for username, NOT border case for domain, YES border case for tld
        assertTrue(BankAccount.isEmailValid("abc-d@mail-archive.com"));

        // Equivalence class: out-of-bounds username, within-bounds domain, within-bounds tld
        // Border case: NOT border case for username, NOT border case for domain, YES border case for tld
        assertFalse(BankAccount.isEmailValid("abc-@mail.com"));

        // Equivalence class: out-of-bounds username, within-bounds domain, within-bounds tld
        // Border case: NOT border case for username, NOT border case for domain, YES border case for tld
        assertFalse(BankAccount.isEmailValid("abc..def@mail.com"));

        // Equivalence class: out-of-bounds username, within-bounds domain, within-bounds tld
        // Border case: NOT border case for username, NOT border case for domain, YES border case for tld
        assertFalse(BankAccount.isEmailValid(".abc@mail.com"));

        // Equivalence class: out-of-bounds username, within-bounds domain, within-bounds tld
        // Border case: NOT border case for username, NOT border case for domain, YES border case for tld
        assertFalse(BankAccount.isEmailValid("abc#def@mail.com"));

        // Equivalence class: within-bounds username, within-bounds domain, out-of-bounds tld
        // Border case: NOT border case for username, NOT border case for domain, YES border case for tld
        assertFalse(BankAccount.isEmailValid("abc.def@mail.c"));

        // Equivalence class: within-bounds username, within-bounds domain, out-of-bounds tld
        // Border case: NOT border case for username, NOT border case for domain, NOT border case for tld
        assertFalse(BankAccount.isEmailValid("abc.def@mail"));

        // Equivalence class: within-bounds username, out-of-bounds domain, within-bounds tld
        // Border case: NOT border case for username, NOT border case for domain, NOT border case for tld
        assertFalse(BankAccount.isEmailValid("abc.def@mail..com"));
    }

   @Test
    void constructorTest() {
        // Valid case
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);

        // Invalid email
        assertThrows(IllegalArgumentException.class,
            () -> new BankAccount("", 100));

        // Invalid starting balance: negative amount
        assertThrows(IllegalArgumentException.class,
            () -> new BankAccount("a@b.com", -50));

        // Invalid starting balance: more than 2 decimal places
        assertThrows(IllegalArgumentException.class,
            () -> new BankAccount("a@b.com", 10.999));
    }


    // EC1: Valid positive amounts with ≤ 2 decimal places
    @Test
    void isAmountValid_validPositiveAmount() {
        assertTrue(BankAccount.isAmountValid(10.00));   // middle case
        assertTrue(BankAccount.isAmountValid(999.99));  // middle case
        assertTrue(BankAccount.isAmountValid(0.01));    // boundary case
    }

    // EC2: Zero amount (boundary case)
    @Test
    void isAmountValid_zeroAmount() {
        assertTrue(BankAccount.isAmountValid(0.00));    // boundary case
    }

    // EC3: Negative amounts
    @Test
    void isAmountValid_negativeAmount() {
        assertFalse(BankAccount.isAmountValid(-0.01));  // boundary case
        assertFalse(BankAccount.isAmountValid(-10.00)); // middle case
    }

    // EC4: More than two decimal places
    @Test
    void isAmountValid_tooManyDecimalPlaces() {
        assertFalse(BankAccount.isAmountValid(1.001));  // boundary case
        assertFalse(BankAccount.isAmountValid(10.999)); // middle case
    }


}