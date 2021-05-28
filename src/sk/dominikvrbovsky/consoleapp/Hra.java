package sk.dominikvrbovsky.consoleapp;

import sk.dominikvrbovsky.consoleapp.utilities.PracaSKonzolou;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hra {

    private Karta[] kartyHry; // vsetkych 32 mariasovych kariet potrebnych ku hre
    private Hrac[] hraci; // hraci hry
    private int[] indexiKariet = new int[32]; // pre potrebu miesania kariet
    private Karta kartaNaVrchu; // posledna vylozena karta v hre

    public Hra(){
        PracaSKonzolou.clearScreen();
        System.out.println("_____________________________");
        System.out.println("Vitajte v kartovej hre Farár!");

        this.kartyHry = new Karta[32];
        this.hraci = new Hrac[PracaSKonzolou.pocetHracov()];
        PracaSKonzolou.clearScreen();

        for (int i = 1; i <= this.hraci.length; i++) {
            this.hraci[i - 1] = new Hrac(PracaSKonzolou.zadajMenoHraca(i),PracaSKonzolou.zadajPinHraca(i));
        }

        for (int i = 0; i < this.indexiKariet.length; i++) {
            this.indexiKariet[i] = i; // vytvorenie 32 cisiel v rozmedzi 0-31
        }

        this.vytvorKartyAMiesaj();
        this.rozdajKartyHracom(this.hraci.length);
        this.dajKartuNaVrch();

        if (PracaSKonzolou.potvrdenieZacatiaHry(this.hraci).equals("")) {
            PracaSKonzolou.clearScreen();
            this.start();
        }
    }

    private void start() {
        int counterHracNaRade = this.hraci.length;
        Hrac winner = null;
        int indexKartyNaVylozeniePotiahnutie;

        while (this.kartyHry[31] != null) {
            System.out.println("_______________________________________");
            System.out.println("Vyložená karta: " + this.kartaNaVrchu.getCelyNazovKarty());
            Hrac hracVcykle = this.hraci[counterHracNaRade % this.hraci.length];
            PracaSKonzolou.naRadeJeHrac(this.hraci,counterHracNaRade);

            if (PracaSKonzolou.kontrolaPin(this.hraci, counterHracNaRade).equals(hracVcykle.getPin())) {
                PracaSKonzolou.clearScreen();
                PracaSKonzolou.legenda();
                System.out.println("_______________________________________");
                System.out.println("Vyložená karta: " + this.kartaNaVrchu.getCelyNazovKarty());
                PracaSKonzolou.naRadeJeHrac(this.hraci,counterHracNaRade);
                hracVcykle.vypisKarietHraca();
                indexKartyNaVylozeniePotiahnutie = PracaSKonzolou.potiahniAleboVylozKartu(hracVcykle.getPocetKarietHraca());
            } else {
                PracaSKonzolou.clearScreen();
                System.out.println("Nesprávne heslo! Skuste to znovu");
                continue;
            }


            if (indexKartyNaVylozeniePotiahnutie != 0) {
                Karta kartaNaVylozenie = PracaSKonzolou.najdiKartuNaZakladeZadanehoIndexu(hracVcykle,
                        indexKartyNaVylozeniePotiahnutie);

                if (this.kartaNaVrchu.zhoda(kartaNaVylozenie)) {

                    this.kartaNaVrchu = hracVcykle.vylozKartu(kartaNaVylozenie.getCelyNazovKarty());
                    PracaSKonzolou.clearScreen();

                    if (this.kartaNaVrchu.getCelyNazovKarty().matches(".E")) {
                        System.out.println("Eso! Stojí hráč " + this.hraci[(counterHracNaRade + 1) % this.hraci.length].getMeno());
                        counterHracNaRade++;

                    }

                    if (this.kartaNaVrchu.getCelyNazovKarty().matches(".VII")) {
                        System.out.println("Sedma! Hráč " + this.hraci[(counterHracNaRade + 1) % this.hraci.length].getMeno() +
                                " si berie 3 karty");
                        for (int i = 0; i < 3; i++) {
                            this.hraci[(counterHracNaRade + 1) % this.hraci.length].potiahniKartu(najdiPrvuKartuVKope());
                        }
                    }

                    if (hracVcykle.isWinner()) {
                        winner = hracVcykle;
                        break;
                    }

                } else {
                    PracaSKonzolou.clearScreen();
                    System.out.println("Túto kartu nemôžeš vyložiť. Skús to znovu.");
                    continue;
                }

            } else {
                hracVcykle.potiahniKartu(najdiPrvuKartuVKope());
                PracaSKonzolou.clearScreen();
            }
            counterHracNaRade++;
        }

        if (winner != null) {
            System.out.println("Koniec! Vitaz je: " + winner.getMeno());
        } else if (this.kartyHry[31] == null) {
            System.out.println("Koniec! Dosli karty - Remiza");
        }
    }

    /**
     * Metoda zisti a vrati kartu, ktora ja v prva v kope a tahujuci hrac si ju teda zoberie
     * @return Karta ktoru si tahajuci hrac zoberie z kopy
     */
    private Karta najdiPrvuKartuVKope() {
        Karta prvaKarta = null;
        for (int i = 0; i < this.kartyHry.length; i++) {
            if (this.kartyHry[i] != null) {
                prvaKarta = this.kartyHry[i];
                this.kartyHry[i] = null;
                return prvaKarta;
            }
        }
        return prvaKarta;
    }

    /**
     * Metoda, ktora zmeni poslednu vyhodenu kartu v hre teda kartu, na ktoru sa vyhadzuje
     */
    private void dajKartuNaVrch() {
        this.kartaNaVrchu = najdiPrvuKartuVKope();
    }

    /**
     * Metoda, ktora vytvori vsetkych 32 mariasovych kariet potrebnych ku hre Faraon a jednotlivym kartam priraduje
     * random indexi v poli kartyHry, cim sa karty zamiesaju - zabezpeci sa nahodne zoradenie kariet v poli kartyHry
     *
     */
    private void vytvorKartyAMiesaj() {

        Pattern patternZnak = Pattern.compile(".");
        Matcher matcherZnak = patternZnak.matcher("zžgs");

        Pattern patternPomenovanie = Pattern.compile("(\\p{Upper}+)");
        Matcher matcherPomenovanie = patternPomenovanie.matcher("VII VIII IX X D H K E");


        int counter = 0;
        for (int i = 1; i <= 4; i++) {
            matcherZnak.find();
            for (int j = 1; j <= 8; j++) {
                matcherPomenovanie.find();
                this.kartyHry[randomMiesanie()] = new Karta(matcherZnak.group(), matcherPomenovanie.group());
                counter++;
            }
            matcherPomenovanie.reset();
        }
    }

    /**
     * Algoritmus, ktory vrati vzdy jedno este nepouzite cislo v rozmedzi 0 az 31 (indexy)
     * Sluzi na vytvaranie nahodnych indexov v poli kartyHry na potrebu zamiesania kariet v metode vytvorKartyAMiesaj
     * @return random cislo v rozmedzi 0-31
     */
    private int randomMiesanie() {
        Random rand = new Random();
        int resultInt = 0;
        boolean cyklus = true;

        while (cyklus) { // cyklus opakujuci sa dovtedy, kym sa nenajde este nepouzite cislo v rozmedzi 0-31
            int index = rand.nextInt(32); // najdenie nahodneho cisla v rozmedzi 0-31
            if (this.indexiKariet[index] != -1) { // zistenie, ci uz dane cislo nebolo pouzite
                resultInt = this.indexiKariet[index]; // priradenie este nepouziteho cisla vracajucej premennej tejto metody
                this.indexiKariet[index] = -1; // ked sa cislo pouzije tak sa vyradi priradenim/zmenou hodnoty -1
                return resultInt; // metoda vrati sa najdene random cislo, ktore este nebolo pouzite
            }
        }
        return -1;
    }

    /**
     * metoda, ktora na zaciatku hry rozda 5 kariet vsetkym hracom
     * @param pocetHracov pocet hracov zapojenych do hry
     */
    private void rozdajKartyHracom(int pocetHracov){
        for (int i = 0; i < 5 * pocetHracov; i++) {
            if (this.kartyHry[i] != null) {
                this.hraci[i % pocetHracov].potiahniKartu(this.kartyHry[i]);
                this.kartyHry[i] = null;
            }
        }
    }


}

