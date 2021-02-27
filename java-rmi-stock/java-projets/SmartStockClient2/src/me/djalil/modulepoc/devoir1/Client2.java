package me.djalil.modulepoc.devoir1;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Client2 :
 * <pre>
 * [1] Vend une quantité = 3 de l’article « Ordinateur ».
 * [2] Si la vente réussie, la quantité restante est affichée,
 * [3] sinon un message d’erreur (« Quantité insuffisante ») est affiché.
 * </pre>
 */
public class Client2 {

    // Host No. 39 ("San Kyu" or "Thank You").
    private static final String THANK_YOU_SERVER = "sankyu.djalil.me"; // "192.168.1.39";

    public static void main(String[] args) {
        System.err.println("[Client2] Started");

        try {
            Registry registry = LocateRegistry.getRegistry(THANK_YOU_SERVER, SmartStockThing.RMI_REGISTRY.port());
            IStock stub = (IStock) registry.lookup(SmartStockThing.STOCK.name());

            // [1]
            boolean isSuccessful = stub.vendre("Ordinateur", 3);

            if (isSuccessful) {
                // [2]
                Etat etat = stub.etat_article("Ordinateur");
                System.out.println("[Client2] After vendre: " + etat);
                System.out.println("Quantité: " + etat.getQ());
            } else {
                // [3]
                System.out.println("Quantité insuffisante");
            }

        } catch (Exception e) {
            System.err.println("[Client2] Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
