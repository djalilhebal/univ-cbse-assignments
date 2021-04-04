// package me.djalil.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.OffsetDateTime;

/**
 * A fluent API client for DuckDNS, the no-nonsense dynamic DNS service.
 *
 * <aside><pre>
 * TIP:
 * Consider using environment variables instead of writing sensitive data (e.g. tokens) in code.
 * See: https://help.ubuntu.com/community/EnvironmentVariables
 * </aside></pre>
 *
 * <strong><pre>
 * - NOTE: To stop the 'updater' thread, use the {@link #interrupt()} method.
 * - NOTE: Besides `OffsetDateTime` (of Java 1.8, used in `log(String)`, this class should work on at least Java 1.7+.
 *         We can always make it optional using a "conditional import" trick (with `Class.forName(String)`).
 * </pre></strong>
 *
 * <section>
 *      <h2>Examples</h2>
 *
 *      Example 1: If running as part of alongside a server (e.g. Java RMI server).
 *       <pre><code class="language-java">
 *          new DuckUpdater()
 *              .setDomain( System.getenv("MY_DUCKDNS_DOMAIN") )
 *              .setToken( System.getenv("MY_DUCKDNS_TOKEN") )
 *              .setEvery(60) // 60 minutes, an hour
 *              .start();
 *      </code></pre>
 *
 *      Example 2: If running standalone.
 *      <pre><code class="language-java">
 *      // ...
 *
 *      public static void main(String[] args){
 *
 *         DuckUpdater d =
 *             new DuckUpdater()
 *                 .setDomain( System.getenv("MY_DUCKDNS_DOMAIN") )
 *                 .setToken( System.getenv("MY_DUCKDNS_TOKEN") )
 *                 ;
 *         try {
 *             d.start();
 *             d.join();
 *         } catch (InterruptedException ignoredException) {}
 *
 *      }
 *     </code></pre>
 * </section>
 *
 * <section>
 *      <h2>Changelog</h2>
 *      <pre>
 *      - 2021-01-31 Use `OffsetDateTime` instead of `LocalDateTime` (better for debugging stuff across timezones).
 *      - 2021-01-20 Adapted my `DuckDNS.js` module (from KurageBot.js).
 *      </pre>
 * </section>
 *
 * @author Abdeldjalil Hebal
 * @license WTFPL
 */
public class DuckUpdater extends Thread {
    final static String TAG = DuckUpdater.class.getSimpleName();
    final static boolean DEBUG = true;
    final static String USER_AGENT = "DuckUpdater.java v2020-01-31";

    private String domain;
    private String token;
    private String ip = null;

    private long every = 30;
    private boolean isSilent = !DEBUG;

    // ---

    /** The DuckDNS domain to update. Example: "something.duckdns.org" or just "something". */
    public DuckUpdater setDomain(String domain) { this.domain = domain; return this; }

    /** Set your DuckDNS token. */
    public DuckUpdater setToken(String token) { this.token = token; return this; }

    /** Set an explicit IP address. Set null to use your current IP (as the DuckDNS server sees it). Default: null. */
    public DuckUpdater setIp(String ip) { this.ip = ip; return this; }

    /** Should we log stuff for debugging? */
    public DuckUpdater setIsSilent(boolean isSilent) { this.isSilent = isSilent; return this; }

    /** Update the domain's dynamic DNS entry `every` X minutes. Default: 30 minutes. */
    public DuckUpdater setEvery(long every) {
        assert every > 0 : "The 'every' period MUST NOT be zero";
        this.every = every;
        return this;
    }

    // ---

    @Override
    public void run() {
        // Sanity checks
        assert domain != null : "DuckDNS domain MUST be set";
        assert token != null : "DuckDNS token MUST be set";

        // https://duckdns.org/update/$YOUR_DOMAIN/$YOUR_TOKEN[/$YOUR_EXPLICIT_ADDRESS_MAYBE]
        String optionalIpPart = ip == null ? "" : "/" + ip;
        String url = String.format("https://duckdns.org/update/%s/%s", domain, token) + optionalIpPart;

        while (!isInterrupted()) {
            log("Updating...");
            String reply = fetchAsText(url);
            log("Got: " + reply);
            if ("OK".equals(reply)) {
                log("Update successful.");
            } else if ("KO".equals(reply)) {
                log("Update failed.");
            } else {
                log("Unexpected reply: " + reply);
            }

            delay();
        }

        log("Stopped.");
    }

    private void delay() {
        if (isInterrupted()) return;
        try {
            Thread.sleep(every * 1000 * 60);
        } catch (InterruptedException ignoredException) {}
    }

    /**
     * Think: `await fetch(url).then(response => response.text());`
     *
     * <aside><pre>
     * NOTE:
     * A GET request with HTTPS is secure enough since we are not in the browser.
     * See: <a href="https://stackoverflow.com/questions/499591">"Are HTTPS URLs encrypted?" - Stack Overflow"</a>
     * </pre></aside>
     *
     * @return The response as String, or null if something wrong happened.
     */
    private String fetchAsText(String url) {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection) urlObj.openConnection();
            httpConn.setRequestMethod("GET"); // GET is the default
            httpConn.setRequestProperty("User-Agent", USER_AGENT);

            // Initiate the request
            int responseCode = httpConn.getResponseCode();
            if (responseCode != 200) {
                return null;
            }

            // This is a try-with-resources statement.
            //   See https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
            try ( InputStreamReader in = new InputStreamReader(httpConn.getInputStream()) ) {
                StringBuilder sb = new StringBuilder();
                int c;
                while ((c = in.read()) != -1) {
                    sb.append((char)c);
                }
                String text = sb.toString();
                return text;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void log(String str) {
        if (!isSilent) {
            String timestamp = OffsetDateTime.now().toString();
            String output = String.format("[%s][%s] %s", TAG, timestamp, str);
            System.err.println(output);
        }
    }

}
