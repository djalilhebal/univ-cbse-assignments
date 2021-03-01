import java.time.OffsetDateTime;

public class KaiConsole {

    /**
     * Log with time.
     */
    public static void log(String tag, String msg) {
        String now = OffsetDateTime.now().toString();
        System.out.printf("[%s][%s] %s\n", now, tag, msg);
    }

    public static void clearScreen() {
        if (getIsLinux()) {
            System.out.printf("\033c");
        } else {
            // FIXME: Clear the screen properly
            for (int i = 0; i < 250; ++i) System.out.println();
        }
    }

    static private boolean getIsLinux() {
        return
                System.getenv("TERM") != null ||
                System.getProperty("os.name").equalsIgnoreCase("LINUX");
    }

    /**
     * Does the current console/terminal support colored output?
     *
     * TODO: Find a better approach.
     *
     * @return Boolean indicating whether colors are supported.
     */
    public static boolean getIsConsoleColorSupported() {
        final String termValue = System.getenv("TERM");
        final String osValue = System.getProperty("os.name").toUpperCase();
        // log("[getIsConsoleColorSupported] $TERM env: " + termValue);
        // log("[getIsConsoleColorSupported] os.name prop: " + osValue);
        
        if (termValue != null) {
            // PS: Running this test in Intellij IDEA (in its default "runner terminal/tab" as opposed to the dedicated, embedded Terminal),
            //     returns null.
            //      - In Intellij IDEA embedded terminal, it returns "xterm-256color"
            //      - In Konsole v17.12.3, it returns "xterm-256color"
            //      - In XTerm v330, it returns "xterm"
            //      - In `screen`, it returns either "screen" (running in XTerm) or "screen.xterm-256color" (in Konsole).
            return true;
        } else if (osValue.equals("LINUX")) {
            // Let's just assume that any Linux terminal supports ASCII escape codes (colors, specifically)
            return true;
        } else {
            // The safest bet is to assume it doesn't support colors.
            return false;
        }
    }

}
