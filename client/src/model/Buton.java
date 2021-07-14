package model;

public class Buton extends Butoane{
    String b = new String();
    private static Buton obj;
    private Buton() {}

    public static Buton getInstance()
    {
        if (obj==null)
            obj = new Buton();
        return obj;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }
}
