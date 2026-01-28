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
        
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com"));
        assertTrue(BankAccount.isEmailValid("abc_d@mail.cc"));
        assertTrue(BankAccount.isEmailValid("abc-d@mail-archive.com"));

        assertFalse(BankAccount.isEmailValid("abc-@mail.com"));
        assertFalse(BankAccount.isEmailValid("abc..def@mail.com"));
        assertFalse(BankAccount.isEmailValid(".abc@mail.com"));
        assertFalse(BankAccount.isEmailValid("abc#def@mail.com"));

        assertFalse(BankAccount.isEmailValid("abc.def@mail.c"));
        assertFalse(BankAccount.isEmailValid("abc.def@mail"));
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