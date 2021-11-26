package ca.chesm.it.smartcity.models;

import java.io.Serializable;

public class City implements Serializable
{
    private String Name;
    private int id;
    private String address;
    private double bin1,bin2,bin3;

    public City()
    {

    }

    public City(String name, int id, String address, double bin1, double bin2, double bin3)
    {
        Name = name;
        this.id = id;
        this.address = address;
        this.bin1 = bin1;
        this.bin2 = bin2;
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

    public double getBin2()
    {
        return bin2;
    }

    public void setBin2(double bin2)
    {
        this.bin2 = bin2;
    }

    public double getBin3()
    {
        return bin3;
    }

    public void setBin3(double bin3)
    {
        this.bin3 = bin3;
    }

    @Override
    public String toString()
    {
        return "City{" +
                "Name='" + Name + '\'' +
                ", id=" + id +
                ", address='" + address + '\'' +
                ", bin1=" + bin1 +
                ", bin2=" + bin2 +
                ", bin3=" + bin3 +
                '}';
    }
}
