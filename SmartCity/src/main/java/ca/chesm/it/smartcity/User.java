/*
    Name:Dung Ly N01327929
    Course: CENG322-RND
    Purpose: Contructer for Users
    Last updated:  09 Oct 2021
*/

package ca.chesm.it.smartcity;

public class User
{

    private String Fullname;
    private int PhoneNo;
    private String email;
    private String password;

    public User()
    {

    }

    public String getFullname()

    {
        return Fullname;
    }

    public void setFullname(String fullname)
    {
        Fullname = fullname;
    }

    public int getPhoneNo()
    {
        return PhoneNo;
    }

    public void setPhoneNo(int phoneNo)
    {
        PhoneNo = phoneNo;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {

        return String.format("{fullname: %s}, {password: %s}",getFullname(),getPassword());
    }

}
