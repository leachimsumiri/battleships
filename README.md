[![Build Status](https://travis-ci.com/leachimsumiri/battleships.svg?branch=master)](https://travis-ci.com/leachimsumiri/battleships)
[![dockerbuild](https://img.shields.io/docker/build/mirimus/battelships)](https://hub.docker.com/repository/docker/mirimus/battelships)
[![codecov](https://codecov.io/gh/leachimsumiri/battleships/branch/master/graph/badge.svg?token=E1FZPP33YI)](https://codecov.io/gh/leachimsumiri/battleships)

# Battleships Refactoring

For running locally: Note that Java FX was removed from the SDK with Java 11.

## Angabe: Code Review und Refactoring
* Welche „Bad Smells“ sind Ihnen aufgefallen und konnten Sie lösen?
* Welche „Bad Smells“ sind Ihnen aufgefallen und konnten Sie (noch) nicht lösen?
* Wie ist die Trennung zwischen GUI und Business Logik?
* Wo könnten Patterns eingesetzt werden?
* Wie könnte die Applikation verbessert werden?

### Bad Smells Übersicht
    1. Duplizierter Code
    2. Lange Methode
    3. Große Klasse
    4. Lange Parameterliste
    5. Divergierende Änderungen
    6. Schrotkugeln herausoperieren
    7. Neid
    8. Datenklumpen
    9. Neigung zu elementaren Typen
    10. Switch-Anweisungen
    11. Parallele Vererbungshierarchien
    12. Faule Klasse
    13. Spekulative Allgemeinheit
    14. Temporäre Felder
    15. Nachrichtenketten
    16. Vermittler
    17. Unangebrachte Intimität
    18. Alternative Klassen mit verschiedenen
    Schnittstellen
    19. Unvollständige Bibliotheksklasse
    20. Datenklassen
    21. Ausgeschlagenes Erbe
    22. Kommentare