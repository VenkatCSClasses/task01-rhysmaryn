package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if(amount < 0) {
            throw new IllegalArgumentException("Cannot withdraw negative amount");

        } else if (amount <= balance){
            balance -= amount;
            
        } else {
            throw new InsufficientFundsException("Not enough money");
        }

    }


    public static boolean isEmailValid(String email){
        boolean atSymbolFound = false;
        int dotCount = 0;
        int finalDotIndex = -1;

        // Check that there is exactly one '@' symbol and at least one '.' after it
        for (int i = 0; i < email.length(); i++){
            char c = email.charAt(i);
            
            if (c == '@' && !atSymbolFound){
                atSymbolFound = true;
            } else if (c == '@' && atSymbolFound){
                return false;
            }

            if (atSymbolFound && c == '.'){
                dotCount += 1;
                finalDotIndex = i;
            }
        }

        if (dotCount <= 0 || atSymbolFound == false){
            return false;
        }

        // Split email address into relevant substrings
        String username = email.substring(0, email.indexOf('@'));
        String domain = email.substring(email.indexOf('@') + 1);
        String domainSuffix = email.substring(finalDotIndex + 1);

        // Check lengths of substrings
        if (username.length() < 1 || domain.length() < 3 || domainSuffix.length() < 2){
            return false;
        }

        // Username can't start or end with a dot
        if (username.charAt(0) == '.' || username.charAt(username.length() -1) == '.'){
            return false;
        }

        // Check if each substring only contains valid characters
        for (int i = 0; i < username.length(); i++){
            char c = username.charAt(i);
            if (!(Character.isLetterOrDigit(c) || c == '.' || c == '-' || c == '_')){
                return false;
            }
            if (c == '.' && i < username.length() -1 && username.charAt(i + 1) == '.'){
                return false;
            }
            if (i == (username.length() -1) && !Character.isLetterOrDigit(c)){
                return false;
            }
        }

        for (int i = 0; i < domain.length(); i++){
            char c = domain.charAt(i);
            if (!(Character.isLetterOrDigit(c) || c == '-' || c == '.')){
                return false;
            }
            if (c == '.' && i < domain.length() -1 && domain.charAt(i + 1) == '.'){
                return false;
            }
        }

        for (int i = 0; i < domainSuffix.length(); i++){
            char c = domainSuffix.charAt(i);
            if (!Character.isLetter(c)){
                return false;
            }
        }
        
        return true;
    }
}