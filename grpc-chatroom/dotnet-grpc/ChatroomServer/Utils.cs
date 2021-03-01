using System;
using System.Collections.Generic;

namespace KaiStuff
{
    public class Utils {

       /**
        * Log with a timestamp.
        * 
        * ---
        * 
        * Standard date and time format strings | Microsoft Docs
        *     &sect; The round-trip ("O", "o") format specifier
        *     The "O" or "o" standard format [...] complies with ISO 8601.
        * SEE https://docs.microsoft.com/en-us/dotnet/standard/base-types/standard-date-and-time-format-strings#Roundtrip
        */
        public static void Log(string tag, string msg)
        {
            string now = DateTimeOffset.Now.ToString("O");
            Console.Write($"[{now}][{tag}] {msg}\r\n\r\n");
        }

        /**
         * Log once with a time.
         * 
         * PS: Temporary. Written only to hide the noisy logs of polling GetMessages.
         */
        public static void LogOnce(string tag, string msg)
        {
            string str = $"[ONCE][{ tag}] { msg}";
            if (_onceLogHistory.Contains(str))
            {
                return;
            }

            string now = DateTimeOffset.Now.ToString("O");
            Console.Write($"[{now}]{str}\r\n\r\n");
            _onceLogHistory.Add(str);
        }
        private static HashSet<string> _onceLogHistory = new HashSet<string>();

    }
    
}
