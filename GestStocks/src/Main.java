import model.GestStocksFacade;

public class Main {

    public static void main(String[] args) {
        GestStocksFacade facade = new GestStocksFacade(false, false);
        //facade.createMapa();
        facade.addThings();


    }
}
