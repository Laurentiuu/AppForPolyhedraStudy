import model.Poliedru;


public class Operatii {

    Poliedru poliedru = new Poliedru();
    Double lungimeBaza;
    Double latimeBaza;
    Double apotema;
    Double inaltime;
    String date = new String();

    public Operatii(String s) {
        String parts[] = s.split(" ");
        this.poliedru.setTip(parts[0]);
        this.lungimeBaza = Double.parseDouble(parts[1]);
        this.latimeBaza = Double.parseDouble(parts[2]);
        this.apotema = Double.parseDouble(parts[3]);
        this.inaltime = Double.parseDouble(parts[4]);
    }

    public String calcArieLaterala() {
        if (this.poliedru.getTip().equals("Prisma")) {
            Double a1 = 3 * this.lungimeBaza * this.inaltime;
            Double a2 = 4 * this.lungimeBaza * inaltime;
            Double a3 = 6 * this.lungimeBaza * this.inaltime;
            this.date = a1 + " " + a2 + " " + a3;
            return this.date;
        }

        if (this.poliedru.getTip().equals("ParalelipipedDreptunghic")) {
            Double a = 2 * (this.lungimeBaza * this.inaltime + this.latimeBaza * this.inaltime);
            this.date = a.toString();
            return this.date;
        }
        if (this.poliedru.getTip().equals("Cub")) {

            Double a = 4 * (this.lungimeBaza);
            this.date = a.toString();
            return this.date;
        }
        if (this.poliedru.getTip().equals("PiramidaPatrulatera")) {

            Double a = (4 * this.lungimeBaza * this.apotema) / 2;
            this.date = a.toString();
            return this.date;
        }
        if (this.poliedru.getTip().equals("Tetraedru")) {

            Double a = (3 * this.lungimeBaza * this.apotema) / 2;
            this.date = a.toString();
            return this.date;
        }
        if (this.poliedru.getTip().equals("TrunchiDePiramida")) {

            Double a = (((this.lungimeBaza * 4) + (this.latimeBaza * 4)) * this.apotema) / 2;
            this.date = a.toString();
            return this.date;
        }
        return null;
    }

    public String calcArieBaza() {
        if (this.poliedru.getTip().equals("Prisma")) {
            Double a1 = (this.lungimeBaza * this.lungimeBaza * 1.73) / 4;
            Double a2 = this.lungimeBaza * this.lungimeBaza;
            Double a3 = (3 * this.lungimeBaza * this.lungimeBaza * 1.73) / 2;
            this.date = a1 + " " + a2 + " " + a3;
            return this.date;
        }
        if (this.poliedru.getTip().equals("ParalelipipedDreptunghic")) {

            Double a = this.lungimeBaza * this.latimeBaza;
            this.date = a.toString();
            return this.date;
        }
        if (this.poliedru.getTip().equals("Cub")) {

            Double a = this.lungimeBaza * this.lungimeBaza;
            this.date = a.toString();
            return this.date;
        }
        if (this.poliedru.getTip().equals("PiramidaPatrulatera")) {

            Double a = this.lungimeBaza * this.lungimeBaza;
            this.date = a.toString();
            return this.date;
        }
        if (this.poliedru.getTip().equals("Tetraedru")) {

            Double a = (this.lungimeBaza * this.lungimeBaza * 1.73) / 4;
            this.date = a.toString();
            return this.date;
        }
        if (this.poliedru.getTip().equals("TrunchiDePiramida")) {

            Double a = this.lungimeBaza * this.lungimeBaza;
            this.date = a.toString();
            return this.date;
        }

        return null;
    }

    public String calcArieTotala() {
        if (this.poliedru.getTip().equals("Prisma")) {

            Double al1 = 3 * this.lungimeBaza * this.inaltime;
            Double ab1 = (this.lungimeBaza * this.lungimeBaza * 1.73) / 4;
            Double a1 = al1 + 2 * ab1;

            Double al2 = 4 * this.lungimeBaza * this.inaltime;
            Double ab2 = this.lungimeBaza * this.lungimeBaza;
            Double a2 = al2 + 2 * ab2;

            Double al3 = 6 * this.lungimeBaza * this.inaltime;
            Double ab3 = (3 * this.lungimeBaza * this.lungimeBaza * 1.73) / 2;
            Double a3 = al3 + 2 * ab3;
            this.date = a1 + " " + a2 + " " + a3;
            return this.date;
        }
        if (this.poliedru.getTip().equals("ParalelipipedDreptunghic")) {

            Double al = 2 * (this.lungimeBaza * this.inaltime + this.latimeBaza * this.inaltime);
            Double ab = this.lungimeBaza * this.latimeBaza;
            Double a = al + 2 * ab;
            this.date = a.toString();
            return this.date;
        }
        if (this.poliedru.getTip().equals("Cub")) {

            Double a = 6 * this.lungimeBaza * this.lungimeBaza;
            this.date = a.toString();
            return this.date;
        }
        if (this.poliedru.getTip().equals("PiramidaPatrulatera")) {

            Double a = ((4 * this.lungimeBaza * this.apotema) / 2) + (this.lungimeBaza * this.lungimeBaza);
            this.date = a.toString();
            return this.date;
        }
        if (this.poliedru.getTip().equals("Tetraedru")) {

            Double a = ((this.lungimeBaza * this.lungimeBaza * 1.73) / 4) + ((3 * this.lungimeBaza * this.apotema) / 2);
            this.date = a.toString();
            return this.date;
        }
        if (this.poliedru.getTip().equals("TrunchiDePiramida")) {

            Double a = (this.lungimeBaza * this.lungimeBaza) + (this.latimeBaza * this.latimeBaza) + ((((this.lungimeBaza * 4) + (this.latimeBaza * 4)) * this.apotema) / 2);
            this.date = a.toString();
            return this.date;
        }
        return null;
    }

    public String calcVolum() {
        if (this.poliedru.getTip().equals("Prisma")) {

            Double ab1 = (this.lungimeBaza * this.lungimeBaza * 1.73) / 4;
            Double a1 = ab1 * this.inaltime;
            Double ab2 = this.lungimeBaza * this.lungimeBaza;
            Double a2 = ab2 * this.inaltime;
            Double ab3 = (3 * this.lungimeBaza * this.lungimeBaza * 1.73) / 2;
            Double a3 = ab3 * this.inaltime;
            this.date = a1 + " " + a2 + " " + a3;
            return this.date;
        }
        if (this.poliedru.getTip().equals("ParalelipipedDreptunghic")) {

            Double ab = this.lungimeBaza * this.latimeBaza;
            Double a = ab * this.inaltime;
            this.date = a.toString();
            return this.date;
        }
        if (this.poliedru.getTip().equals("Cub")) {

            Double a = this.lungimeBaza * this.lungimeBaza * this.lungimeBaza;
            this.date = a.toString();
            return this.date;
        }
        if (this.poliedru.getTip().equals("PiramidaPatrulatera")) {

            Double a = (this.lungimeBaza * this.lungimeBaza * this.inaltime) / 3;
            this.date = a.toString();
            return this.date;
        }
        if (this.poliedru.getTip().equals("Tetraedru")) {

            Double a = (((this.lungimeBaza * this.lungimeBaza * 1.73) / 4) * this.inaltime) / 3;
            this.date = a.toString();
            return this.date;
        }
        if (this.poliedru.getTip().equals("TrunchiDePiramida")) {

            Double a = (this.inaltime * ((this.lungimeBaza * this.lungimeBaza) + (this.latimeBaza * this.latimeBaza) + Math.sqrt(this.latimeBaza * this.latimeBaza * this.lungimeBaza * this.lungimeBaza))) / 3;
            this.date = a.toString();
            return this.date;
        }
        return null;
    }
}
