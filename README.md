# Applicazione TempStation
`TempStation` è un'applicazione per AndroidThings che permette di visualizzare la temperatura corrente in gradi Celsius, sfruttando il Raspberry Pi 3 e il Rainbow HAT.

## Pre-requisiti Hardware/Software
- [Android Studio 3.+](https://developer.android.com/studio/) con Android 8.1 (API Level 27);
- [Raspberry Pi 3](https://www.raspberrypi.org/products/);
- [Rainbow HAT](https://shop.pimoroni.com/products/rainbow-hat-for-android-things).

## Descrizione dell'applicazione
`TempStation` presenta due versioni: `TempStationV1` e `TempStationV2`.

Entrambe le versioni sono strutturate utilizzando gli *Android Architecture Components* e, in particolare, sono stati utilizzati i componenti `ViewModel` e `LiveData`. Per maggiori dettagli sul loro utilizzo, è possibile visionare questo progetto: [AndroidThings-AndroidArchComponents](https://github.com/FrancescoTaurino/AndroidThings-AndroidArchComponents).

Entrambe le versioni acquisiscono la temperatura corrente tramite il sensore BMP280 (collocato sul Rainbow HAT) e la mostrano all'utente tramite LCD alfanumerico HT16K33 (anch'esso collocato sul Rainbow HAT). La comunicazione con le periferiche avviene tramite i [driver](https://github.com/androidthings/contrib-drivers/tree/master/rainbowhat) per il Rainbow HAT. Si ricorda che:
> these drivers are not production-ready. They are offered as sample implementations of Android Things user space drivers for common peripherals as part of the Developer Preview release. There is no guarantee of correctness, completeness or robustness.

La differenza tra le due versioni consiste nel **quando** la temperatura viene mostrata sullo schermo. 

### Versione V1
La prima versione cattura constantemente la temperatura e la mostra in real-time sullo schermo.  

La GIF seguente mostra l'esecuzione dell'applicazione.

<p align="center">
  <img src="https://francescotaurino.github.io/MediaRepo/TempStation/v1_3.gif" width="400"></p>

L'applicazione esegue correttamente, ma si presenta un problema: il sensore, essendo montato direttamente sul Rainbow HAT, risente di due fonti di calore che alterano la percezione della temperatura, ovvero: 
- il calore generato del Raspberry Pi, sottostante al Rainbow HAT;
- il calore generato dallo schermo, sempre acceso e molto vicino al sensore.

 ### Versione V2
 La seconda versione cerca di limitare il calore generato dallo schermo, in modo tale da abbassare le temperature. Per fare questo, l'applicazione permette di attivare/disattivare la cattura della temperatura e la visualizzazione sullo schermo tramite il tasto `B` del Rainbow HAT.
In questo modo, il sensore e lo schermo non sono constantemente attivi, ma si attivano solo su richiesta dell'utente e per piccole finestre temporali.  

La GIF seguente mostra l’esecuzione dell’applicazione.

<p align="center">
  <img src="https://francescotaurino.github.io/MediaRepo/TempStation/v2_3.gif" width="400"></p>

Il grafico seguente mostra le temperature raggiunte dalle due applicazioni dopo 20 minuti di attività. Entrambe partono da circa 30° e la prima (linea blu) raggiunge quasi i 42° mentre la seconda (linea arancione) supera appena i 37°. Per quanto riguarda la seconda applicazione, sensore e schermo sono stati attivati per due volte (al minuto 5 e 10) e lasciati attivi per 5 minuti.

<p align="center">
  <img src="https://francescotaurino.github.io/MediaRepo/TempStation/log.png"></p>
