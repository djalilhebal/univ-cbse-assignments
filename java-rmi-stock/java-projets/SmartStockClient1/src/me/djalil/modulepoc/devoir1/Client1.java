package me.djalil.modulepoc.devoir1;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Client1 :
 * <pre>
 * [1] Créer un article dont l’id = « Ordinateur » et la quantité initiale = 0.
 * </pre>
 */
public class Client1 {

    // Host No. 39 ("San Kyu" or "Thank You").
    private static final String THANK_YOU_SERVER = "sankyu.djalil.me"; // "192.168.1.39";

    public static void main(String[] args) {
        System.err.println("[Client1] Started");

        try {
            Registry registry = LocateRegistry.getRegistry(THANK_YOU_SERVER, SmartStockThing.RMI_REGISTRY.port());
            IStock stub = (IStock) registry.lookup(SmartStockThing.STOCK.name());

            // [1]
            stub.creer_article("Ordinateur", 0);
            Etat etat = stub.etat_article("Ordinateur");
            System.err.println("[Client1] Created Article: " + etat);
        } catch (Exception e) {
            System.err.println("[Client1] Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
