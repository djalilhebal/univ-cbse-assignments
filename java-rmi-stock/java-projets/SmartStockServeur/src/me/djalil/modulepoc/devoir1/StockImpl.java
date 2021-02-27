package me.djalil.modulepoc.devoir1;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Stock implementation.
 *
 * Note:
 * We want to specify the port. We could do it either, or
 * - here in the constructor: `super(SmartStockThing.STOCK.port());`
 * - in the server: `UnicastRemoteObject.exportObject(stockObj, SmartStockThing.STOCK.port());`
 * I have chosen the latter.
 *
 * @see SmartStockThing#STOCK
 */
public class StockImpl /* extends UnicastRemoteObject */ implements IStock {
    private List<Article> articlesList = new ArrayList<>();

    /*
    protected StockImpl() throws RemoteException {
        super();
    }
    */

    // ---

    @Override
    public void creer_article(String id, int Q_initiale) {
        Article newArticle = new Article(id, new Etat(Q_initiale, OffsetDateTime.now()));
        articlesList.add(newArticle);
    }

    @Override
    public boolean vendre(String id, int Q) {
        assert Q > 0 : "Quantity MUST be a positive integer";

        Article requestedArticle = findArticleById(id);
        boolean isAvailable = requestedArticle != null;

        if (!isAvailable || requestedArticle.getEtat().getQ() < Q) {
            return false;
        }

        changerQPar(requestedArticle, -Q);
        return true;
    }

    @Override
    public void approvisionner(String id, int Q) {
        assert Q > 0 : "Quantity MUST be a positive integer";

        Article requestedArticle = findArticleById(id);

        boolean isAvailable = requestedArticle != null;
        if (!isAvailable) { return; }

        changerQPar(requestedArticle, +Q);
    }

    @Override
    public Etat etat_article(String id) {
        Article requestedArticle = findArticleById(id);
        boolean isAvailable = requestedArticle != null;
        if (!isAvailable) { return null; }
        return requestedArticle.getEtat();
    }

    // ---

    /**
     * @param id
     * @return the article or {@code null}
     */
    private Article findArticleById(String id) {
        Article foundArticle = articlesList
                .stream()
                .filter(article -> article.getId().equals(id))
                .findFirst()
                .orElseGet(null);

        return  foundArticle;
    }

    /**
     * Change the quantity by delta (either positive or negative).
     *
     * @param article The article to be operated on
     * @param delta
     */
    private void changerQPar(Article article, int delta) {
        OffsetDateTime newDate = OffsetDateTime.now();
        int newQ = article.getEtat().getQ() + delta;
        Etat newEtat = new Etat(newQ, newDate);
        article.setEtat(newEtat);
    }

}
