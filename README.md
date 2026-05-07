# Vendingmaschine Projekt

## Inhaltsverzeichniss
## Informieren
Um zu wissen, was wir machen müssen, ist es hilfreich eine Liste, aller Anforderungen zu haben:
### Anforderungen
- [ ] Vorgehensweise nach IPERKA
- [ ] Wichtigsten Schritte in Dokumentation festhalten (inkl. schöne Formatierung)
- [x] Code auf GitHub abgelegt
- [x] Teammitglieder und Berufsbildner eingeladen
- [ ] Programm gemäss Auftrag realisieren (weiter ausgeführt in DoD)

### Aufteilung des Programmes
Aufteilung gemäss UML
- UI
- SnackMachine (preis Logik)
- SnackInventory
- SecretKeyAuthenticator

## Planen
### Aufgabeneinteilung
SecretKeyAuthenticator -> Fabian
UI/Design -> Liliane,
Inventory -> Julian,
Snack -> Julian + Fabian,
SnackMachine -> Fabian,
Customer -> Fabian

wir haben folgendes UML zu dem ganzen erstellt
![diagram.drawio (2).png](diagram.drawio%20%282%29.png)
### DoD
Um später zu kontrollieren, ob wir das Projekt abgeschlossen haben, definieren wir hier folgende Anforderungen:

- [ ] Anforderungen in Informieren-Teil erfüllt
- [ ] Programm läuft wie in [Auftrag](./Anforderungen.md) definiert
- [ ] Alle User-Eingaben sind absturzsicher.


## Entscheiden
Wir haben uns entschieden, eine UI statt CLI zu implementiere, da es interessanter ist. Dazu
haben wir Java swing benutzt.

## Realisieren
Beim Realisieren haben wir darauf geachtet, Immer Kommentare zu hinterlassen. Das
ist das die anderen Teammitglieder die Funktionen verstehen, ohne den ganzen Code
zu lesen.

Für den Secret key haben wir eine File benutzt, die den salt und hash eines Password
enthält. Dies ist kryptographisch sicher, und man kann mit dem inhalt der datei
überprüfen, ob ein passwort korrekt ist.

Wir haben implementiert, dass beim UI die normalen aktionen (snack kaufen) durch den UI -> Customer ->
SnackMachine -> SnackInventory fliessen, was die logik einfacher macht und das EVA-Prinzip umsetzt. Aber bei den Admin-funktionen
geht es von UI direkt zum Inventory, da diese keine logik enthalten.

Jegliche falschen Eingaben werden überprüft und mit einer Exception gehandelt, die im Frontend angezeigt wird. Dadurch konnten wir unser Programm "idiotensicher" machen und Abstürze verhindern. 
  
## Kontrollieren


## Auswerten
