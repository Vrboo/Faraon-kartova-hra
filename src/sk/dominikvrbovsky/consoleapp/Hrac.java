package sk.dominikvrbovsky.consoleapp;


import java.util.Arrays;
import java.util.Objects;

/**
 * Predstavuje hráča v hre Faraon
 */
public class Hrac {
    private String meno;

    /**
     * Karty, ktoré má hrač na ruke
     */
    private Karta[] karty;

    /**
     * Pin resp. heslo hráča, ktoré musí hráč v hre zadať, aby mal prístup ku svojim kartám;
     */
    private String pin;

    public Hrac(String meno, String pin) {
        this.karty = new Karta[20];
        this.meno = meno;
        this.pin = pin;
    }

    public String getMeno() {
        return meno;
    }

    public Karta[] getKarty() {
        return karty;
    }

    public String getPin() {
        return pin;
    }

    /**
     * Metóda, predstavuje potiahnutie karty z kopy a zaradenie potiahnutej karty medzi karty hráča
     * @param karta karta, ktorú si potiahol z kopy
     * @return vyjadruje uspesnost, potiahnutia
     */
    public boolean potiahniKartu(Karta karta) {
        for (int i = 0; i < this.karty.length; i++) {
            if (this.karty[i] == null) {
                this.karty[i] = karta;
                return true;
            }
        }
        return false;
    }

    /**
     * Metóda, predstavuje vyhodenie karty resp. zbavenie sa karty
     * @param menoKarty je názov karty, ktorú chce hrac vyhodiť
     * @return Karta, ktorú som vyhodil
     */
    public Karta vylozKartu(String menoKarty) {
        Karta vylozenaKarta = null;
        for (int i = 0; i < this.karty.length; i++) {
            if (this.karty[i] != null) {
                if (this.karty[i].getCelyNazovKarty().equals(menoKarty)) {
                    if (this.karty[i].getCelyNazovKarty().matches(".H")) this.karty[i].hornik();
                    vylozenaKarta = this.karty[i];
                    this.karty[i] = null;
                    return vylozenaKarta;
                }
            }
        }
        System.out.println("Takú kartu nemas");
        return vylozenaKarta;

    }

    /**
     * Metóda vypíše na konzolu všetky karty hráča aj s indexami kariet
     */
    public void vypisKarietHraca() {
        int counter = 1;
        System.out.print("Tvoje karty: ");
        for (int i = 0; i < this.karty.length; i++) {
            if (this.karty[i] != null) {
                System.out.print("     " + counter + ". " +  this.karty[i].getCelyNazovKarty());
                counter++;
            }
        }
    }

    /**
     * Metóda nájde a vráti nazov karty na základe zadaného indexu
     * @param index index karty, ktorej nazov pozadujem
     * @return nazov požadovanej karty
     */
    public String menoKartyHracaNaZakladeIndexu(int index) {
        int counter = 0;

        for (int i = 0; i < this.karty.length; i++) {
            if (this.karty[i] != null)  counter++;
            if (index == counter) return this.karty[i].getCelyNazovKarty();
        }

        return null;
    }

    /**
     * Hrác je vítaz, ak už nema žiadnu kartu
     * @return vyhodnotenie vitazstva
     */
    public boolean isWinner() {
        for (int i = 0; i < this.karty.length; i++) {
            if (this.karty[i] != null)  return false;
        }
        return true;
    }

    public int getPocetKarietHraca() {
        return (int)Arrays.stream(this.karty).filter(Objects::nonNull).count();
    }


}
