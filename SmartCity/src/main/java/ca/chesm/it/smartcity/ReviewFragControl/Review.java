package ca.chesm.it.smartcity.ReviewFragControl;

public class Review
{

    private String invitefriend;
    private String smile;
    private String though;

    public Review()
    {
    }

    public Review(String invitefriend, String smile, String though)
    {
        this.invitefriend = invitefriend;
        this.smile = smile;
        this.though = though;
    }

    public String getInvitefriend()
    {
        return invitefriend;
    }

    public void setInvitefriend(String invitefriend)
    {
        this.invitefriend = invitefriend;
    }

    public String getSmile()
    {
        return smile;
    }

    public void setSmile(String smile)
    {
        this.smile = smile;
    }

    public String getThough()
    {
        return though;
    }

    public void setThough(String though)
    {
        this.though = though;
    }
}
