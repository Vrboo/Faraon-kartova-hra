package sk.dominikvrbovsky.consoleapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sk.dominikvrbovsky.consoleapp.utilities.*;

/**
 * Predstavuje kartu v hre Faraon
 */
public class Karta {

    /**
     * Znak karty (s - srdce, z - zelen, ž - žalud, g - gula)
     */
    private String znakKarty;

    /**
     * Pomenovanie karty (D - Dolnik, H - Hornik, K - Kon,E - Eso,  Dolnik, VII, VIII, IX, X)
     */
    private String pomenovanieKarty;

    /**
     * znakKarty + pomenovanieKarty
     */
    private String celyNazovKarty;

    public Karta(String znakKarty, String pomenovanieKarty) {
        this.znakKarty = znakKarty;
        this.pomenovanieKarty = pomenovanieKarty;
        this.celyNazovKarty = znakKarty + pomenovanieKarty;
    }

    /**
     * Metóda zistí, či tato Karta moze byt vyhodena na kartu, ktora je posledna vyhodena v hre resp. na kartu na vrchu
     * Karty sa musia zhodovať bud v znaku alebo v type karty prip. hornik mozem vyhodit kedykolvek
     * @param kartaNaVrchu karta, ktorá je posledná vyhodená v hre resp. karta na vrchu
     * @return uspenost vyhodenia tejto Karty na poslednu vyhodenu v hre
     */
    public boolean zhoda(Karta kartaNaVrchu) {
        /*
        Kontrola zhody v znaku kariet (Znak s - srdce, z - zelen, ž - žalud, g - gula)
         */
        Pattern p = Pattern.compile(this.getZnakKarty() + "\\p{Upper}+");
        Matcher m = p.matcher(kartaNaVrchu.getCelyNazovKarty());

        /*
        Kontrola zhody v type kariet (Typ - Eso, Dolnik, VII, X a pod)
         */
        Pattern p1 = Pattern.compile("." + this.getPomenovanieKarty());
        Matcher m1 = p1.matcher(kartaNaVrchu.getCelyNazovKarty());

        /*
        Hornik sa moze vyhodit na hocijaku kartu
         */
        if (kartaNaVrchu.getCelyNazovKarty().matches(".H")) {
            return true;
        }

        if (m.matches() || m1.matches()) {
            return true;
        } else {
            return false;
        }
    }


    public String hornik() {
        this.setZnakKarty(PracaSKonzolou.konzolaHornik());
        return this.getZnakKarty();
    }


    public String getCelyNazovKarty() {
        return  znakKarty + pomenovanieKarty;
    }


    public String getPomenovanieKarty() {
        return pomenovanieKarty;
    }


    public String getZnakKarty() {
        return znakKarty;
    }


    public void setZnakKarty(String znakKarty) {
        this.znakKarty = znakKarty;
    }

}
