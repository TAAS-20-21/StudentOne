# StudentOne

Piattaforma per la gestione di corsi universitari

Per Pasti & Fra:
-Per la parte di front-end seppur abbiate installato il compiler di angular globalmente, per farlo partire è necessario installarlo anche internamente alla cartella di front-end
-Per la parte di back-end ci sono diverse cose da fare:
    -Controllare che la versione da jdk per far partire tutto sia la jdk11. In caso da IntelliJ andate su file -> project structure e impostate la jdk corretta
    -Essendo un maven dovete importare tutte le dipendenze. C'è un tab a sinistra, scritto in verticale "maven". Cliccatelo e di lì, prima andate sulla cartella "lifecycle" e fate doppio click su install; poi, nella cartella "plugins", cliccate "compiler", quindi doppio click su "compiler:compile"
