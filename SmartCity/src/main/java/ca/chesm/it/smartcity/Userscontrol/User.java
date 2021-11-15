/*
    Name:Dung Ly N01327929
    Course: CENG322-RND
    Purpose: Contructer for Users
    Last updated:  09 Oct 2021
*/

package ca.chesm.it.smartcity.Userscontrol;

public class User
{

    private String Fullname;
    private long PhoneNo;
    private String email;
    private String password;
    private String face;
    private String through;
    private String commend;
    private String uid;


    public User(String uid,String face, String through, String commend)
    {
        this.uid = uid;
        this.face = face;
        this.through = through;
        this.commend = commend;
    }

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

    public long getPhoneNo()
    {
        return PhoneNo;
    }

    public void setPhoneNo(long phoneNo)
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

    public String getFace()
    {
        return face;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }

    public void setFace(String face)
    {
        this.face = face;
    }

    public String getThrough()
    {
        return through;
    }

    public void setThrough(String through)
    {
        this.through = through;
    }

    public String getCommend()
    {
        return commend;
    }

    public void setCommend(String commend)
    {
        this.commend = commend;
    }


    @Override
    public String toString()
    {

        return String.format("{fullname: %s}, {password: %s}",getFullname(),getPassword());
    }

}
