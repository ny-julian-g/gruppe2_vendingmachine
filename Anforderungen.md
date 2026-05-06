# Snackautomat Features und Wartung
Bei einem Snackautomat können Kunden vorrätige Snacks (Riegel, Getränke, Sandwiches usw.)
kaufen. Der Ablauf eines Kaufs ist wie bei einem üblichen Snackautomaten: Zuerst wird das Geld
eingeworfen und danach kann das Produkt über eine eindeutige Produktnummer gewählt werden
(oder umgekehrt, entscheidet euch für eine Implementierung). Daraufhin erhält der Kunde den
gewählten Snack und sein Rückgeld, falls mehr Geld als nötig eingeworfen wurde.
Der Kunde soll auch die Möglichkeit haben den Kaufvorgang jederzeit abzubrechen, worauf der
Kunde seinen Geldbetrag zurückbekommt, sofern bereits eingeworfen. Diese Funktionalität ist
nötig, falls der Kunde zu wenig Geld für einen Snack besitzt und dies zu spät bemerkt.
Diese Art von Automaten müssen ständig gewartet werden. In diesem Fall heisst das, dass
Snacks wiederaufgefüllt (wenn der Lagerbestand eines Snacks niedrig oder 0 ist) oder
ausgetauscht (z.B. Cola durch Pepsi ersetzen), sowie Preisänderungen vorgenommen werden
können. An sich kann jeder Kunde solche Änderungen vornehmen, solange dieser den «Secret
Key» kennt. Wird dieser anstelle einer Produktnummer eingegeben, so erhält der Kunde Zugriff
auf die oben drei genannten Wartungsarbeiten.
Wenn der Snackautomat gestartet wird, ist er leer. Über den "Secret Key" kann einmal eine initiale
Füllung ausgelöst werden.
