package me.djalil.modulepoc.devoir1;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * <i lang="fr">Responsable de la publication des objets distribués de type StockImpl.<i/>
 */
public class StockServeur {

    // Host No. 39 ("San Kyu" or "Thank You") has a DHCP reservation, so it won't change.
    // sankyu.djalil.me references it indirectly (through Dynamic DNS / CNAME kurage.duckdns.org).
    // This DDNS entry is periodically updated by KurageBot.js.
    static final String THANK_YOU_LOCAL = "192.168.1.39";
    static final String THANK_YOU_EXTERNAL = "sankyu.djalil.me";
    static final String THANK_YOU_SERVER = THANK_YOU_EXTERNAL;

    public static void main(String[] args){

        System.setProperty("java.rmi.server.hostname", THANK_YOU_SERVER);

       try {
           System.err.println("[Serveur] Started");

            System.err.println("[Serveur] Creating the registry...");
            Registry reg = LocateRegistry.createRegistry(SmartStockThing.RMI_REGISTRY.port());

            System.err.println("[Serveur] Initializing and exporting a StockImpl object...");
            StockImpl obj = new StockImpl();
            UnicastRemoteObject.exportObject(obj, SmartStockThing.STOCK.port());
            reg.rebind(SmartStockThing.STOCK.name(), obj);

            System.err.println("[Serveur] Ready");
        } catch (RemoteException e) {
            System.err.println("[Serveur] Exception: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
