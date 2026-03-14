# ShiftPokerGame.Kt

## Spielübersicht

Schiebe-Poker ist ein Kartenspiel für **2 bis 4 Spieler*innen** und wird mit einem Standard-Kartendeck aus **52 Karten** gespielt.

Kartenset:

* Farben: Kreuz, Pik, Herz, Karo
* Werte: 2, 3, 4, 5, 6, 7, 8, 9, 10, Bube, Dame, König, Ass

Zu Beginn erhält jede*r Spieler*in:

* **2 verdeckte Karten**
* **3 offene Karten**

Zusätzlich:

* **3 Karten liegen offen in der Mitte**
* Die restlichen Karten bilden den **Nachziehstapel**
* Ein **Ablagestapel** ist anfangs leer

Vor Spielstart legen die Spieler*innen eine Anzahl von **2 bis 7 Runden** fest.

---

# Spielablauf

Das Spiel beginnt mit einem **zufällig ausgewählten Startspieler**. Danach sind alle Spieler*innen **reihum** an der Reihe.

In jedem Zug müssen **zwei Aktionen** durchgeführt werden.
Diese Aktionen werden **nacheinander** ausgeführt.

Mögliche Kombinationen:

* Schieben → Tauschen
* Tauschen → Schieben
* Schieben → Schieben
* Tauschen → Tauschen

---

# Aktionen

## 1. Schieben

Beim Schieben werden die **drei Karten in der Mitte verschoben**.

### Nach links schieben

* Die linke Karte wird auf den **Ablagestapel gelegt**
* Die anderen Karten rücken **nach links**
* Eine neue Karte vom **Nachziehstapel** kommt rechts dazu

### Nach rechts schieben

* Die rechte Karte wird auf den **Ablagestapel gelegt**
* Die anderen Karten rücken **nach rechts**
* Eine neue Karte vom **Nachziehstapel** kommt links dazu

Wenn der Nachziehstapel leer ist, wird der **Ablagestapel gemischt und zum neuen Nachziehstapel**.

---

## 2. Tauschen

Ein*e Spieler*in kann:

* **eine offene Karte mit einer Karte aus der Mitte tauschen**
* **alle drei offenen Karten mit den drei Karten in der Mitte tauschen**
* **auf den Tausch verzichten**

Beim Dreifachtausch gilt:

* linke Karte ↔ linke Karte
* mittlere Karte ↔ mittlere Karte
* rechte Karte ↔ rechte Karte

Die **Reihenfolge der offenen Karten darf nie verändert werden**.

---

# Spielende

Das Spiel endet nach der zuvor festgelegten **Rundenzahl**.

Danach werden alle Karten aufgedeckt und die Hände der Spieler bewertet.

---

# Punktewertung

Die **Hand eines Spielers** besteht aus:

* 2 verdeckten Karten
* 3 offenen Karten

Die Bewertung erfolgt nach den **klassischen Pokerregeln**, von:

* Höchste Karte
  bis
* Royal Flush

Wichtig:
Wenn zwei Spieler die gleiche Kombination haben, gilt dies **immer als Unentschieden**, unabhängig von Kartenwerten.

Beispiele:

* Ass vs. König als höchste Karte → Unentschieden
* Zwei Paare mit unterschiedlichen Werten → Unentschieden


# Features der Anwendung

Das Programm implementiert zusätzlich folgende Funktionen:

### Spieler-Konfiguration

Beim Start können **Spielernamen festgelegt** werden.

### Hotseat-Modus

Alle Spieler spielen **abwechselnd am gleichen Bildschirm**.

### Verdeckte Karten

Die **verdeckten Karten sind nur für den jeweiligen Spieler sichtbar**.
Ein „Nächste*r Spieler*in“-Bildschirm verhindert, dass andere Spieler die Karten sehen.

### Spielprotokoll (Log)

Alle Aktionen werden während des Spiels protokolliert, zum Beispiel:

* Spieler 1 (Alice) hat nach links geschoben
* Spieler 1 (Alice) hat ihre linke Karte mit der mittleren Karte getauscht

### Endergebnis

Nach Spielende wird eine **Rangliste der Spieler** angezeigt.


# Technologien

* Kotlin
* BoardGameWork Framework
* Gradle

