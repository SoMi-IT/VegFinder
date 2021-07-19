package com.somi.vegfinder.account;

public class CustomAccount {

    private String UID;
    private String email;
    private String psw;
    private String name;
    private String surname;
    private boolean isAdmin;


    public CustomAccount(String _email, String _psw) {

        email = _email;
        psw = _psw;

    }//CustomAccount


    public CustomAccount() { }//CustomAccount


    public String getUID() { return UID; }
    public String getEmail() { return email; }
    public String getPsw() { return psw; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public boolean isAdmin() { return isAdmin; }


    public void setUID(String _uid) { UID = _uid; }
    public void setEmail(String _email) { email = _email; }
    public void setPsw(String _psw) { psw = _psw; }
    public void setName(String _name) { name = _name; }
    public void setSurname(String _surname) { surname = _surname; }
    public void setAdmin(boolean _isAdmin) { isAdmin = _isAdmin; }


}//CustomAccount
