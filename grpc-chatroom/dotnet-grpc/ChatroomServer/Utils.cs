using System;
using System.Collections.Generic;

namespace KaiStuff
{
    public class Utils {

        /**
         * Returns the current timestamp with timezone.
         * Named a la PostgreSQL's `TIMESTAMPTZ`.
         *
         * ---
         *
         * > The "O" or "o" standard format [...] complies with ISO 8601.
         * > 
         * > -- Standard date and time format strings, "The round-trip ("O", "o") format specifier" section
         * >    (https://docs.microsoft.com/en-us/dotnet/standard/base-types/standard-date-and-time-format-strings#Roundtrip)
         */
        public static string NowZ() {
            DateTimeOffset nowObj = DateTimeOffset.UtcNow;
            // ISO 8601-compliant datetime format.
            string nowStr = nowObj.ToString("O");
            // `ToString("O")` returns the timestamp with the full offset,
            // even for +00:00, while 'Z' would be better (shorter by 5 characters).
            string nowZ = nowStr.Replace("+00:00", "Z");
            return nowZ;
        }

       /**
        * Log with timestamp.
        */
        public static void Log(string tag, string msg)
        {
            Console.Write($"[{NowZ()}][{tag}] {msg}\r\n\r\n");
        }

        /**
         * Log once with a time.
         * 
         * PS: Written only to hide the noisy logs of polling GetMessages.
         */
        public static void LogOnce(string tag, string msg)
        {
            string str = $"[ONCE][{tag}] {msg}";
            if (_onceLogHistory.Contains(str))
            {
                return;
            }

            Console.Write($"[{NowZ()}]{str}\r\n\r\n");
            _onceLogHistory.Add(str);
        }

        private static HashSet<string> _onceLogHistory = new HashSet<string>();

    }
    
}
