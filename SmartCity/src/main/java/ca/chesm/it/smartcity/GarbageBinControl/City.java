package ca.chesm.it.smartcity.GarbageBinControl;

public class City
{
    private String Name;
    private int id;
    private String address;
    private double bin1,bìn2,bin3;

    public City()
    {

    }

    public City(String name, int id, String address, double bin1, double bìn2, double bin3)
    {
        Name = name;
        this.id = id;
        this.address = address;
        this.bin1 = bin1;
        this.bìn2 = bìn2;
        this.bin3 = bin3;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String name)
    {
        Name = name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public double getBin1()
    {
        return bin1;
    }

    public void setBin1(double bin1)
    {
        this.bin1 = bin1;
    }

    public double getBìn2()
    {
        return bìn2;
    }

    public void setBìn2(double bìn2)
    {
        this.bìn2 = bìn2;
    }

    public double getBin3()
    {
        return bin3;
    }

    public void setBin3(double bin3)
    {
        this.bin3 = bin3;
    }
}
