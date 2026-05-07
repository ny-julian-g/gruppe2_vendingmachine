# Verkaufsautomat Projekt

## Inhaltsverzeichniss
- [Verkaufsautomat Projekt](#verkaufsautomat-projekt)
  - [Inhaltsverzeichniss](#inhaltsverzeichniss)
  - [Informieren](#informieren)
    - [Anforderungen](#anforderungen)
    - [Aufteilung des Programmes](#aufteilung-des-programmes)
  - [Planen](#planen)
    - [Aufgabeneinteilung](#aufgabeneinteilung)
    - [DoD](#dod)
  - [Entscheiden](#entscheiden)
  - [Realisieren](#realisieren)
  - [Kontrollieren](#kontrollieren)
  - [Auswerten](#auswerten)
    - [Fabian](#fabian)
    - [Julian](#julian)
    - [Liliane](#liliane)


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

Wir haben folgendes UML zum ganzen erstellt:
![diagram.drawio (2).png](diagram.drawio%20%282%29.png)

### DoD
Um später zu kontrollieren, ob wir das Projekt abgeschlossen haben, definieren wir hier folgende Anforderungen:

- [ ] Anforderungen in Informieren-Teil erfüllt
- [ ] Programm läuft wie in [Auftrag](./Anforderungen.md) definiert
- [ ] Alle User-Eingaben sind absturzsicher.


## Entscheiden
Wir haben uns entschieden, ein UI statt CLI zu implementieren, da es interessanter ist. Dazu
haben wir Java swing benutzt.

## Realisieren
Beim Realisieren haben wir darauf geachtet, immer Kommentare zu hinterlassen. Dadurch verstehen die anderen Teammitglieder
die Funktionen, ohne den ganzen Code lesen zu müssen.

Für den Secret-Key haben wir eine File benutzt, die den salt und hash eines Password
enthält. Dies ist kryptographisch sicher, und man kann mit dem Inhalt der Datei
überprüfen, ob ein Passwort korrekt ist.

Wir haben implementiert, dass beim UI die normalen aktionen (snack kaufen) durch den UI -> Customer ->
SnackMachine -> SnackInventory fliessen, was die logik einfacher macht und das EVA-Prinzip umsetzt. Aber bei den
Admin-funktionen geht es von UI direkt zum Inventory, da diese keine logik enthalten.

Jegliche falschen Eingaben werden überprüft und mit einer Exception gehandelt, die im Frontend angezeigt wird. Dadurch
konnten wir unser Programm "idiotensicher" machen und Abstürze verhindern. 
  
## Kontrollieren


## Auswerten
Die Auswertung haben wir jeweils individuell geschrieben, um verschiedene Meinungen und Ansichten miteinzubeziehen.

### Fabian

### Julian
Ich fand es gut, dass wir uns am Anfang gedanken gemacht haben, wie wir das Projekt aufbauen wollen und wer was macht, damit
legten wir die Grundsteine für die anschliessende Arbeit. Dabei wäre es jedoch besser gewesen, genauer zu definieren, welche
elementaren Funktionen wir brauchen, um das Programm laufen zu lassen, das hätte direkt eine saubere abtrennung der Logik und
dem UI nach dem EVA-Prinzip ermöglicht. Ausserdem hätte ich es im nachhinein sinnvoll gefunden, einen dev Branch zu machen, wobei
dieser ebenfalls feature Branches haben würde, um eine saubere Entwicklung zu ermöglichen, wobei wir es bei unserem Fall nicht zwingend
brauchten. Allgemein fand ich, dass wir ein gutes Zusammenspiel hatten. Wir konnten uns früh aufteilen und alle arbeiteten in
etwa gleich viel am Projekt. Das wir mit Kommentaren arbeiteten war für mich auch ungewohnt, ich konnte mich aber schnell daran
gewöhnen und es half definitiv, den Code zu lesen, wobei ich fand, dass der Code meines Teams einfach zu lesen war.

### Liliane 
