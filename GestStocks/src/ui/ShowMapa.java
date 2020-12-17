package ui;

import java.util.List;
import java.util.Map;

public class ShowMapa {
    final int ZONA_RECECAO = 0;
    final int NR_PRATELEIRAS = 10;


    private void firstPrint(){
        int i;
        System.out.print("Zona de receção    ");
        for(i=ZONA_RECECAO+1; i<NR_PRATELEIRAS+1; i++) System.out.print("Prateleira " + i + "   ");
        System.out.printf("\n%3s__________%7s", " ", " ");
        for(i=ZONA_RECECAO+1; i<NR_PRATELEIRAS+1; i++) System.out.printf("%5s%5s", "__________", " ");
    }

    private void secondPrint(){
        int i;
        System.out.printf("\n%2s|%10s|%5s", " ", " ", " ");
        for(i=ZONA_RECECAO+1; i<NR_PRATELEIRAS+1; i++) System.out.printf("|%10s|%3s", "          ", " ");
    }

    private void lastPrint(){
        System.out.printf("\n%3s__________%7s", " ", " ");
        for(int i=ZONA_RECECAO+1; i<NR_PRATELEIRAS+1; i++) System.out.printf("%5s%5s", "__________", " ");
    }

    public void showMapa(){
        firstPrint();
        secondPrint();
        lastPrint();
    }

    public void showPaletesMapa(Map<Integer, String> paletes){
        firstPrint();
        System.out.printf("\n%2s|%10s|%5s", " ", "[1]   ", " ");
        for(int i=ZONA_RECECAO+1; i<NR_PRATELEIRAS+1; i++) {
            String codPalete = paletes.get(i);
            if(codPalete == null) System.out.printf("|%10s|%3s", "          ", " ");
            else System.out.printf("|%6s%4s|%3s", codPalete, " ", " ");
        }
        lastPrint();
    }

    public void verZonaRececao(List<String> paletes_rececao){
        System.out.println("Zona de receção");
        System.out.printf("%3s__________%7s", " ", " ");
        paletes_rececao.forEach(palete -> System.out.printf("\n%2s|%6s%4s|%3s", " ", palete, " ", " "));
        System.out.printf("\n%3s__________%7s\n", " ", " ");
    }

}



