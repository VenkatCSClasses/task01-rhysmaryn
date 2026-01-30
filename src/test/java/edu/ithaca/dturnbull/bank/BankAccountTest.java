package edu.ithaca.dturnbull.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
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
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}