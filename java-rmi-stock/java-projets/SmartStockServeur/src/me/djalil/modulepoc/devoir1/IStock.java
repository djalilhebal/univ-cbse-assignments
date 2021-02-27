package me.djalil.modulepoc.devoir1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IStock extends Remote {

    /**
     * Ajouter un nouvel article au stock.
     */
    void creer_article(String id, int Q_initiale) throws RemoteException;

    /**
     * Vendre une quantité Q d’un article donné.
     *
     * @return Si la quantité en stock est inférieure à la quantité demandée Q, elle renvoie false.
     *         Sinon, elle renvoie true.
     */
    boolean vendre(String id, int Q) throws RemoteException;

    /**
     * Approvisionner le stock d’une quantité Q d’un article donné.
     */
    void approvisionner(String id, int Q) throws RemoteException;

    /**
     * @return Etat.
     */
    Etat etat_article(String id) throws RemoteException;

}