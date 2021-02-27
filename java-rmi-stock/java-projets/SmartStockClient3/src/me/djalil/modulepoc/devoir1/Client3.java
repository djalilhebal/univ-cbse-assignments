package me.djalil.modulepoc.devoir1;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Client3 :
 * <pre>
 * [1] Consulte l’état de l’article « ordinateur »
 * [2] ensuite l’approvisionne d’une quantité = 20.
 * </pre>
 */
public class Client3 {

    // Host No. 39 ("San Kyu" or "Thank You").
    private static final String THANK_YOU_SERVER = "sankyu.djalil.me"; // "192.168.1.39";

    public static void main(String[] args) {
        System.err.println("[Client3] Started");

        try {
            Registry registry = LocateRegistry.getRegistry(THANK_YOU_SERVER, SmartStockThing.RMI_REGISTRY.port());
            IStock stub = (IStock) registry.lookup(SmartStockThing.STOCK.name());

            // [1]
            Etat etat = stub.etat_article("Ordinateur");
            System.out.println("Etat: " + etat);

            // [2]
            stub.approvisionner("Ordinateur", 20);

            // DEBUGGING
            Etat etatAfter = stub.etat_article("Ordinateur");
            System.err.println("[Client3] After approvisionner: " + etatAfter);
        } catch (Exception e) {
            System.err.println("[Client3] Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
