package sk.dominikvrbovsky.consoleapp.utilities;

import sk.dominikvrbovsky.consoleapp.Hrac;
import sk.dominikvrbovsky.consoleapp.Karta;

import java.util.Scanner;

/**
 * V tejto triede sa nachádzaju metódy zabezpecujuce vypisy textovych retazcov na konzolu a nacitenie hodnot z konzoly
 */
public class PracaSKonzolou {

    public static String konzolaHornik() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        int input = -1;
        System.out.println("Horník! Na čo meníš? [1 - žaluď, 2 - guľa, 3 - zeleň, 4 - srdce]");

        while (input < 0 || input > 4) {
            input = scanner.nextInt();
        }

        switch (input) {
            case 1:
                return "ž";
            case 2:
                return "g";
            case 3:
                return "z";
            case 4:
                return "s";
            default:
                return null;
        }
    }

    public static int pocetHracov(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Zadajte počet hráčov: ");
        int input = Integer.parseInt(scanner.nextLine());
        return input;
    }

    public static String zadajPinHraca(int indexHraca) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Zadajte heslo pre hráča číslo " + indexHraca + ": ");
        String input = scanner.nextLine();
        clearScreen();
        return input;
    }

    public static String kontrolaPin(Hrac[] hraci, int counter) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Zadajte heslo hraca "+ hraci[counter % hraci.length].getMeno() + ": ");
        String input = scanner.nextLine();
        return input;
    }

    public static String zadajMenoHraca(int indexHraca) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Zadajte meno hráča číslo " + indexHraca + ": ");
        String input = scanner.nextLine();
        return input;
    }

    public static String potvrdenieZacatiaHry(Hrac[] hraci) {

        StringBuilder string = new StringBuilder("V hre proti sebe stoja " + hraci.length + " hraci - ");
        for (int i = 0; i < hraci.length;i++) string.append(hraci[i].getMeno()).append(", ");
        string.delete(string.length() - 2,string.length());

        System.out.println(string);
        System.out.println("Každý hráč si zvolil heslo, aby sa predišlo podvádzaniu.");
        System.out.println("V priebehu hry bude v hornej časti konzoly dostupná legenda názvov kariet.");
        System.out.println("Veľa šťastia!");
        System.out.println("_______________________________________");
        System.out.print("Pre začatie hry stlačte ENTER");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }

    public static void naRadeJeHrac(Hrac[] hraci, int counter) {
        System.out.println("Na rade je hráč: " + hraci[counter % hraci.length].getMeno());
    }

    public static int potiahniAleboVylozKartu(int param) {
        Scanner scanner = new Scanner(System.in);
        int input = -1;
        System.out.println("\nZadaj číslo karty, ktorú chceš vyhodiť alebo zadaj '0' pre potiahnutie karty z kopy.");

        while (input < 0 || input > param) {
            input = scanner.nextInt();
        }

        return input;
    }

    public static Karta najdiKartuNaZakladeZadanehoIndexu(Hrac hrac, int index) {
        String menoKarty = hrac.menoKartyHracaNaZakladeIndexu(index);
        for (int i = 0; i < hrac.getKarty().length; i++) {
            if (hrac.getKarty()[i] != null) {
                if (hrac.getKarty()[i].getCelyNazovKarty().equals(menoKarty)) {
                    return hrac.getKarty()[i];
                }
            }
        }
        return null;
    }

    public static void clearScreen() {
        for(int clear = 0; clear < 50; clear++)
        {
            System.out.println() ;
        }
    }

    public static void legenda() {
        System.out.println(
                "Znak karty: \n\t" +
                        "g - guľa \n\t" +
                        "s - srdce \n\t" +
                        "z - zeleň \n\t" +
                        "ž - žaluď"
        );

        System.out.println(
                "Typ karty: \n\t" +
                        "E - Eso \n\t" +
                        "K - Kôň \n\t" +
                        "H - Horník \n\t" +
                        "D - Dolník \n\t" +
                        "VII, VIII, IX, X - Rímske číslo karty"
        );
    }


}
