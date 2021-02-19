using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

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
        public static void LogWithTime(string tag, string msg)
        {
            string now = DateTimeOffset.Now.ToString("O");
            Console.WriteLine("[{0}][{1}] {2}", now, tag, msg);
        }

    }

    // ---

    /**
     * KaiSay. Kaito's cowsay-inspired thing.
     *
     * <code>
     * "  +------------------------------------+ "
     * " <  I like Neru's Whatever, Whatever,  | "
     * "  | Whatever~                          | "
     * "  |....................................| "
     * "  | Sent at yyyy-MM-ddThh:mm:ss.MMMZZZ | "
     * "  +------------------------------------+ "
     * </code>
     */
    public class KaiSay
    {

        private enum ArrawDirection
        {
            Right,
            Left,
            None
        }

        public static string AsMessage(string text)
        {
            return AsMessageBox(text, ArrawDirection.None);
        }
        
        public static string AsSentMessage(string text)
        {
            return AsMessageBox(text, ArrawDirection.Right);
        }

        public  static string AsReceivedMessage(string text)
        {
            return AsMessageBox(text, ArrawDirection.Left);
        }

        private  static string AsMessageBox(string text, ArrawDirection arrow)
        {
            const int TERMINAL_WIDTH = 70;
            const int MAX_INNER_WIDTH = TERMINAL_WIDTH - 4 /* from the start */ - 4 /* from the end */;
            
            IList<string> innerLines = WrapLine(text, MAX_INNER_WIDTH);

            // --- BODY ---
            var outputLines = innerLines
                // FIXME: But why are some lines "empty" in the first place?
                .Where((innerLine) => { return !String.IsNullOrWhiteSpace(innerLine); })
                .Select((innerLine, index) =>
                {
                    string paddedLine = innerLine.PadRight(MAX_INNER_WIDTH - 2, ' ');
                    bool isFirstLine = index == 0; // that contains the arrow
                    if (isFirstLine)
                    {
                        return (arrow == ArrawDirection.Left ? " <  " : "  | ")
                               + paddedLine +
                               (arrow == ArrawDirection.Right ? "  >" : " | ");
                    }
                    return "  | " + paddedLine + " | ";
                }).ToList();

            // NOTE: CSharp's `new String(char, int32)` is like Java's `new String(char).repeat(int)`
            string borderLine = "  +" + new String('-', MAX_INNER_WIDTH) + "+ ";
            // --- BORDER TOP ---
            outputLines.Insert(0, borderLine);
            // --- BORDER BOTTOM ---
            outputLines.Add(borderLine);
            
            string output = String.Join("\r\n", outputLines);
            return output;
        }

        private static  IList<string> WrapLine(string str, int max)
        {
            return str
                .Split("\r\n".ToCharArray())
                // Think: Java's Stream::Map
                .Select(line => GetTextWithNewLines(line, max))
                // Think: Java's Stream::FlatMap
                .SelectMany(wrappedText => wrappedText.Split("\r\n".ToCharArray()))
                .ToList();
        }

        /**
         * NOTE: Works on single lines.
         * 
         * Adapted from https://stackoverflow.com/a/16504017
         */
        private static string GetTextWithNewLines(string value, int charactersToWrapAt)
        {
            if (string.IsNullOrWhiteSpace(value))
                return "";

            value = value.Replace("  ", " ");
            var words = value.Split(' ');
            var sb = new StringBuilder();
            var currString = new StringBuilder();

            foreach (var word in words)
            {
                if (currString.Length + word.Length + 1 < charactersToWrapAt) // The + 1 accounts for spaces
                {
                    sb.AppendFormat(" {0}", word);
                    currString.AppendFormat(" {0}", word);
                }
                else
                {
                    currString.Clear();
                    sb.AppendFormat("{0}{1}", "\r\n", word);
                    currString.AppendFormat(" {0}", word);
                }
            }

            return sb.ToString().Trim();
        }
        
    }

}
