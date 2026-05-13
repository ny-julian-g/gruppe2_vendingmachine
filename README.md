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
- [x] Vorgehensweise nach IPERKA
- [x] Wichtigsten Schritte in Dokumentation festhalten (inkl. schöne Formatierung)
- [x] Code auf GitHub abgelegt
- [x] Teammitglieder und Berufsbildner eingeladen
- [x] Programm gemäss Auftrag realisieren (weiter ausgeführt in DoD)

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

- [x] Anforderungen in Informieren-Teil erfüllt
- [x] Programm läuft wie in [Auftrag](./Anforderungen.md) definiert
- [x] Alle User-Eingaben sind absturzsicher.


## Entscheiden
Wir haben uns entschieden, ein UI statt CLI zu implementieren, da es interessanter ist. Dazu
haben wir Java swing benutzt.

## Realisieren
Beim Realisieren haben wir darauf geachtet, immer Kommentare zu hinterlassen. Dadurch verstehen die anderen 
Teammitglieder
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
Da wir eine DoD gemacht haben und die Anforderungen aufgeschrieben haben, werden wir das Programm basierend auf
diesen Anforderungen kontrollieren. Da dies die Dokumentation ist, werden wir die Vorgehensweise nach IPERKA 
Wichtigste Schritte in Dokumentation festgehalten als erfüllt zählen. Die restlichen Anforderungen im Informieren-
Teil haben wir ebenfalls abgeschlossen. Die Anforderungen, die wir vom Auftrag erhalten haben, funktionieren wie 
beschrieben. Die Usereingaben sind auch absturzsicher, wobei man bei falschen Usereingaben einfach aus dem 
Eingabefeld geworfen wird. 

## Auswerten
Die Auswertung haben wir jeweils individuell geschrieben, um verschiedene Meinungen und Ansichten miteinzubeziehen.

### Fabian
Ich finde das Projekt lief sehr gut. Die Teamarbeit funktionierte auch überraschenderweise gut und wir hatten
nie konflikte. Ich auch überrascht wie viele commits es hatte. Da es keine einzelarbeit ist, muss man
auch kleine änderungen commiten. Ich habe viel gelernt über UI (Swing) In Java. Ich fand das arbeiten mit
kommentaren für dokumentation auch gut. Manchmal hatte es fast zu viele kommentare, aber allgemein hat es mit sicher
geholfen den code zu verstehen.

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
Am Anfang des Projektes haben wir gemeinsam besprochen wer was machen soll. Das fand ich sehr gut, denn so war die Arbeitsaufteilung von Anfang an klar. Vielleicht hätten wir etwas genauer besprechen sollen, welche Funktionen wir usetzen und wer dafürr Verantwortlich ist. Trotzdem hat jedes Gruppenmitglied seinen Teil des Projektes gut erledigt und den anderen bei Problemen geholfen. 
Während dem Projekt haben wir viel über Teams kommuniziert, was ich sehr nützlich fand. So mussten wir uns nicht für jede kleine Frage persönlich treffen, sondern konnten viele Dinge schnell online klären. Dank der guten Kommunikation konnten wir Mergekonfikte vermeiden. 
Was mir nicht so einfach fiel, waren die Kommentare zum Code. Ich schreibe normalerweise sehr selten Kommentare, was sich auch in meinem Code wiederspiegelt. 
Zusammenfassend finde ich, das wir ein gutes Projekt umgesetzt haben.
