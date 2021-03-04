ECHO OFF

REM CD to\the\root\of\the\solution\AppiMailBox
SET server=Server\bin\Debug\Server.exe
SET sender=ClientSender\bin\Debug\ClientSender.exe
SET receiver=ClientReceiver\bin\Debug\ClientReceiver.exe

REM See `COLOR /?`
SET themeGreen=0A
SET themeBlue=30
SET themeBeige=E0

TITLE Senders and stuff
COLOR %themeBeige%

ECHO.
ECHO ---
ECHO.
ECHO Step 1: Server starts.
START "Server" CMD /T:%themeGreen% /K %server%

REM Wait a little for the Server to start and export the remotable object before starting any Sender or Receiver.
REM `SLEEP {{seconds}}` ~= `PING -n {{seconds + 1}} 127.0.0.1 > nul`
PING -n 6 127.0.0.1 > nul

ECHO.
ECHO ---
ECHO.
ECHO Step 2: Senders (two instances of Sender) send messages.
ECHO.
(
  PING -n 4 127.0.0.1 > nul

  ECHO Hieeeeee
  PING -n 4 127.0.0.1 > nul

  ECHO What he calls a Manticore looks to be no more than a shabby, toothless lion. And he has that whole crowd believing that poor old ape with the twisted foot is a Satyr! Illusions, deceptions, mirages! Your Mommy Fortuna cannot truly change things.
  PING -n 4 127.0.0.1 > nul

) | %sender%

ECHO.
(
  PING -n 4 127.0.0.1 > nul

  ECHO Ara ara~
  PING -n 4 127.0.0.1 > nul
) | %sender%

REM Another Sender, to manually test the Receiver's "refresh" functionality
START "Sender (for manual testing)" %sender%

ECHO.
ECHO ---
ECHO.
ECHO Step 3: Receiver receives messages.
ECHO.
START "Receiver" CMD /T:%themeBlue% /K %receiver%

ECHO.
PAUSE
