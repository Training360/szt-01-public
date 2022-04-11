# Szoftvertesztelés Java platformon - gyakorlati feladatok

A gyakorlati feladat alapja a kedvenc hely (location),
melyhez tartozik egy
név és koordináták (latitude - szélesség, longitude - hosszúság).

## Bevezetés a JUnit használatába

A `locations` csomagba dolgozz!

Hozz létre egy `Location` osztályt,
`name`, `latitude`, `longitude` attribútumokkal! A `name` attribútum `String` típusú legyen!
A szélességi és hosszúsági koordinátákat
külön `double` típusú attribútummal ábrázold!

Legyenek getter/setter metódusai és konstruktora, ahol mind a
három attribútumát meg lehet adni!

Hozz létre egy `LocationParser` osztályt, melynek feladata szöveges értékből
kinyerni egy kedvenc hely adatait!
Legyen egy `public Location parse(String text)` metódusa, mely a nevet és a
koordinátákat vesszővel elválasztva várja (pl. `Budapest,47.497912,19.040235`)! A tizedeshatároló karakter legyen a
pont! Ez a metódus visszaad egy új példányt, kitöltve a megfelelő attribútum értékekkel.
Írj rá egy `LocationTest` osztályt, valamint egy `testParse()` metódust,
mely ezt a metódust teszteli!

## Futtatás Mavennel

Futtasd le a tesztesetet Mavenből!

## Tesztesetek életciklusa

Írj egy `isOnEquator()` metódust, mely `true` értéket ad vissza, ha a pont az egyenlítőn van (`latitude == 0`).
Írj egy `isOnPrimeMeridian()` metódust, mely `true` értéket ad vissza, ha a
pont a kezdő meridiánon van (`longitude == 0`).

Írj rá teszteseteket! A `@BeforeEach` metódusban hozz létre egy `LocationParser` példányt,
majd tárold el egy attribútumban. A tesztesetek ezt az attribútumot használják!

## Elnevezések

Adj a teszteseteknek olvasható nevet!

## Assert

Hívd meg kétszer a `LocationParser` `parse` metódusát, és ellenőrizd, hogy két különböző példányt ad vissza!

Írj a `Location` osztályba egy `distanceFrom()` metódust, mely a paraméterként átadott másik `Location`-től
való távolságot adja vissza! Használd a következő, Haversine algoritmust (vigyázz, máshogy kell paraméterezni)!

```java
public static double distance(double lat1, double lat2, double lon1,
        double lon2, double el1, double el2) {

    final int R = 6371; // Radius of the earth

    double latDistance = Math.toRadians(lat2 - lat1);
    double lonDistance = Math.toRadians(lon2 - lon1);
    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double distance = R * c * 1000; // convert to meters

    double height = el1 - el2;

    distance = Math.pow(distance, 2) + Math.pow(height, 2);

    return Math.sqrt(distance);
}
```

Írj rá tesztesetet! Ellenőrizd le, hogy Budapest (47.49791,19.04023) és Debrecen (47.52997,21.63916) távolsága légvonalban valóban 195,2 km!

A másikban ellenőrizd le, hogy egy Location távolságát önmagától mérve valóban 0 km távolsággal tér vissza a metódusod!

A `parse()` metódusra írj olyan tesztesetet, mely egyszerre ellenőrzi a `name`, `latitude` és `longitude`
attribútumok értékét!

Írj egy `LocationOperators` osztályt, benne egy `List<Location> filterOnNorth(List<Location>)` metódust,
mely csak az északon lévő pontokat adja vissza (`latitude > 0`)!

Írj rá tesztesetet a `LocationOperatorsTest` osztályban (ellenőrizz a nevekre)!

A teszteléshez hozz létre egy osztályszintű Location listát, majd egy `@BeforeEach` annotációval ellátott metódussal 
töltsd föl három Location-nel, melyek közül egyik a déli féltekén helyezkedik el. A `testNorthLocations()` metódusban 
ellenőrizd, hogy valóban csak az északiakat adja-e vissza a logika.

A `LocationTest` osztályban hozz létre egy `testDifferentLocations()` nevű metódust!
Ebben példányosíts két új locationt úgy, hogy az attribútumaik legyenek azonosak!
Egy `assertAll`-on belül hívd meg egymás után az `assertEquals`-t és az `assertNotSame`-et a két locationre!
A `Location` osztályban override-old úgy az `equals()` metódust, hogy sikeresen lefusson a teszteset!

## Kivételkezelés és timeout tesztelése

A `Location` konstruktora dobjon `IllegalArgumentException` kivételt, ha nem megfelelő számot kap!
A szélesség értéke -90 és 90 közötti, a hosszúság értéke -180 és 180 közötti legyen!
Írj rá teszteseteket!

## Egymásba ágyazás

Hozz létre egy `LocationNestedTest` osztályt, melynek `BeforeEach` annotációval ellátott metódusa
példányosítson egy `LocationParser` osztályt! Majd az egyik belső osztály `@BeforeEach`
annotációval ellátott metódusa hozzon létre egy kedvenc helyet `0,0` koordinátákkal,
egy másik belső osztály pedig `47.497912,19.040235` koordinátákkal! Mindegyikben legyenek teszt metódusok
az `isOnEquator()` és `isOnPrimeMeridian()` metódusokra!

## Tagek és metaannotációk használata

Hozz létre egy `LocationOperationsFeatureTest` annotációt, és tedd rá a `LocationOperatorsTest` osztályra,
és próbáld csak ezt futtatni IDEA-ból és Mavenből is!

## Tesztesetek ismétlése

Hozd létre a `LocationRepeatTest` osztályt, majd ebbe egy `double[][]` tömbök tömbje attribútumot!  

Vegyél fel benne négy darab olyan tömböt, amelyek három-három számot tartalmaznak! Az első kettő 
a `latitude` és `longitude` értékek, a harmadik pedig `1` vagy `0`, ami egy `true/false` értéket jelöl. 
A harmadik értékek ott legyenek `1`-ek (azaz `true`-k), ahol az első koordináta `0`, tehát igaz, hogy a 
location az egyenlítőn található! Legyen két ilyen a tömbben!  
Hozd létre a következő tesztmetódust: `void testEquator(RepetitionInfo info)`!  
Ez négyszer fusson le, és az annotációban állítsd be a `name` értéket, hogy írja ki a jelenlegi és 
a teljes ciklus számot! (pl. `"1 / 4"`)!  
Ezzel az ismétlődő teszttel menj végig a tömbön, hívd meg az adott `Location` `isOnEquator()` metódusát, 
és vizsgáld, hogy a mellette megadott `boolean` értéket adja-e vissza!

## Paraméterezett tesztek

Hozd létre a következő tesztmetódust: `void testPrimeMeridian(int latitude, int longitude, int expected)`!  
Állítsd be rajta a `ParameterizedTest` annotációt, valamint annak a `name` értéket, hogy írja ki az 
aktuális három értéket (pl. `"Latitude = 50, Longitude = 0, Expected = true"`)!  
Lásd el `MethodSource` annotációval, amely hivatkozzon egy ilyen metódusra: `static Stream<Arguments> getLocations()`!  
Legyen négy `Arguments`, melyek tartalmazzanak két számot és egy boolean értéket az előző feladatban 
leírtakhoz hasonlóan, csak itt a harmadik értékek akkor legyenek `true`-k, ha a második koordináta nulla! 
Teszteld velük az `isOnPrimeMeridian()` metódust!

Hozz létre egy `testPrimeMeridianFromFile()` nevű metódust az előző mintájára!  
Cseréld le a `MethodSource` annotációt a `CsvFileSource`-ra! Az `src/test/resources` mappába hozz 
létre egy `location.csv` fájlt, amiben vedd fel négy sorban ugyanazokat az értékeket, mint az előző feladatban!  

## Dinamikus tesztek

Hozz létre egy paraméterezett tesztet, mely az egyenlítőn lévő kedvenc helyek streamjéből készít
dinamikus teszteseteket úgy, hogy meghívja az `isOnEquator()` metódusukat!

## Tempdirectory extension

Hozz létre egy `LocationsWriter` nevű osztályt, abban egy `void writeLocations(Path file, List<Location> locations)`, 
metódust, mely az első paraméterként megadott fájlba kiírja a második paraméterként megadott helyeket CSV (comma separated values)
formátumban.
A helyeket külön sorba írja ki, és a `name`, `latitude` és `longitude` attribútumok értékeit egymástól vessző (`,`) 
karakterrel elválasztva.

Írj egy tesztesetet, mely teszteli a kiírást, méghozzá úgy, hogy kiír pár `Location` példányt, és 
beolvassa a sorokat egy `List<String>` adatszerkezetbe (használd a `Files.readAllLines()` metódust)! Utána ebben 
a listában ellenőrizd szúrópróbaszerűen pl. a második elemet, hogy megfelelő-e. Pl. `Budapest,47.497912,19.040235`.

## JUnit 4 és 5 használata

(Opcionális.)

Írj egy JUnit 4 tesztesetet, és futtasd le a JUnit 5-ös tesztesetekkel együtt!

## Hamcrest

Hozz létre egy `LocationsReader` osztályt, amibe írj egy metódust, mely egy CSV fájlból felolvassa a benne szereplő kedvenc 
helyeket, majd visszaad egy `Location` listát!  
A CSV fájlban soronként egy `Location` értékei szerepeljenek vesszővel elválasztva. (pl. `Budapest,47.497912,19.040235`)  
Hozz létre egy `LocationsReaderHamcrestTest` osztályt, amiben írj egy `testReadLocations()` metódust, amivel teszteld a beolvasást!

A visszaadott `List<Location>` adatstruktúrára Hamcrest asserteket írj!

## Saját Hamcrest matcher implementálása

(Opcionális.)

Írj egy olyan Hamcrest matchert, mely azt vizsgálja, hogy egy
`Location` legalább egyik koordinátája 0! Legyen a neve `LocationWithZeroCoordinate`!

## AssertJ

Hozz létre egy `LocationsReaderTest` osztályt, amiben írj egy `testReadLocations()` metódust, amivel teszteld a beolvasást!

A `LocationsReader` osztályba írj egy metódust (`filterLocationsBeyondArcticCircle()`), mely kap egy Location listát, majd 
stream segítségével kiszűri az északi sarkkörön (66.57 fok) túli koordinátával rendelkezőket, és ezen helyeket adja vissza egy listában!  
A tesztelésnél használd az előző feladat beolvasó metódusát, és módosítsd a CSV fájt, hogy tartalmazzon kettő lokációt, 
ami megfelel a feltételnek! (Ha szükséges, módosítsd az előző tesztesetet, hogy lefusson!)  
Írj egy tesztesetet, mely egy fluent ellenőrzés során kigyűjti a helyek neveit, és megvizsgálja a következőket:

* pontosan mennyi elemet tartalmaz
* tartalmaz egy bizonyos elemet
* nem tartalmaz egy bizonyos elemet
* csak a felsorolt elemeket tartalmazza

Az előző tesztmetódusba vegyél fel még egy `assertThat`-et, és fluent módon végezd el a következőket:

* A sarkörön túli lokációk listáját szűrd tovább, hogy csak azokat adja vissza, ahol a hosszúsági és szélességi értékek azonosak. (Ehhez a CSV fájlban módosítsd az egyik sort, ha kell!)
* Ezt követően nyerd ki belőle a `name` és `latitude` párost!
* Végül ellenőrizd, hogy valóban csak azt az egy eredményt tartalmazza, amit kell!
* Egy új tesztmetódusban példányosíts egy új Location-t "Abc" névvel, majd két `assertThat()`-tel vizsgáld meg, hogy
  a neve `b`-vel kezdődik-e, majd hogy `b`-vel végződik-e! Futtasd le a tesztesetet, és figyeld meg, hogy csak az 
  első hibás ellenőrzést írja ki!  
Ezután írd át, hogy a `SoftAssertion` használatával mindkét hiba megjelenjen a konzolon! Próbáld ki az `assertAll`-al 
  és az`@ExtendWith` annotációval is!

## AssertJ kiterjeszthetőség

Definiálj egy olyan `Condition<Location>` feltételt a `testWithCondition()` metódusba, ami azt vizsgálja, hogy a 
kedvenc hely legalább egyik koordinátája 0!

Írj egy saját Assertet `LocationAssert` néven, amely tartalmaz egy `isNearTheArcticCircle()` nevű metódust, 
mely azt vizsgálja, hogy az aktuális Location `latitude` értéke legfeljebb két fokkal tér el az északi sarkkörtől!  
Állíts be saját hibaüzenetet is!  
Írj teszteseteket erre az assertre! Ellenőrizd a hibás lefutást is, hogy megfelelően jelenik-e meg az üzenet!

## Mockito

Hozz létre egy `LocationsRepository` interfészt, melynek van egy
`Optional<Location> findByName(String name)` metódusa!

Hozz létre egy `LocationsService` osztályt, abban egy `Optional<Double> calculateDistance(String name1, String name2)`
metódust!
A metódus a két névvel hívja meg a repository `findByName()` metódusát!
Amennyiben az egyik is `Optional.empty()` értékkel tér vissza, adjon vissza egy
`Optional.empty()` értéket! Amennyiben egyik sem `empty()`, adja vissza a
kettő közötti távolságot! 

Írj teszteket az alábbi esetekre:

- empty Optionalt kapsz vissza, ha nem állítod be a mockolást
- az első Stringnek megfelelő várost nem állítasz be az adatok között
- a második Stringnek megfelelő várost nem állítasz be az adatok között
- a két String megegyezik, a kapott távolság 0
- a két String nem egyezik meg, a kapott távolság egyenlő az elvárttal  

Mindegyik esetben teszteld, hogy a kimockolt osztályod metódusai pontosan az elvárt alkalommal lettek meghívva!

A `LocationsRepository` tartalmazzon egy `Optional<Double> findLatitudeByName(String name)` metódust is!

A `LocationsService` tartalmazzon egy `public boolean isOnNorthernHemisphere(String name)` metódust is, 
mely meghívja a `findLatitudeByName`-et. Ha ennek a visszatérési értéke üres, dobjon egy `IllegalArgumentException`-t! 
Ha nem üres, akkor megvizsgálja a kapott `latitude` értéket, és ha az az északi féltekén található, `true`-t ad vissza, 
ellenkező esetben `false`-t.

Írj három külön tesztet a `isOnNorthernHemisphere` metódusra:

- a visszatérési érték `true`, amennyiben `42` a `latitude` értéke
- a visszatérési érték `false`, amennyiben `-42` a `latitude` értéke
- hibát dob, amennyiben amennyiben `Optional.empty()` a `latitude` értéke

Ezeknek a teszteknek a végén is ellenőrizd, hogy a kimockolt osztályod metódusai pontosan az elvárt alkalommal lettek meghívva!

## Tesztlefedettség

Futtass tesztlefedettség mérést a projekten IDEA és Jacoco segítségével is.
Hasonlítsd össze, hogy ugyanazt az eredményt hozza-e a kettő!

A nem lefedett területekre írj tesztet!
