# Szoftvertesztelés Java platformon - gyakorlati feladatok

A gyakorlati feladat alapja a kedvenc hely (location),
melyhez tartozik egy
név és koordináták (latitude - szélesség, longitude - hosszúság).

## Bevezetés a JUnit használatába

A `locations` csomagba dolgozz!

Hozz létre egy `Location` osztályt,
`name`, `lat`, `lon` attribútumokkal! A `name` attribútum `String` típusú legyen!
A szélességi és hosszúsági koordinátákat
külön `double` típusú attribútummal ábrázold!

Legyenek getter/setter metódusai, és konstruktora, ahol mind a
három attribútumát meg lehet adni!

Hozz létre egy `LocationParser` osztályt, mely feladata szöveges értékből
kinyerni egy kedvenc hely adatait!
Legyen egy `public Location parse(String text)` metódusa, mely a nevet és a
koordinátákat vesszővel elválasztva várja (pl. `Budapest,47.497912,19.040235`)! A tizedeshatároló karakter legyen a
pont! Ez a metódus visszaad egy új példányt, kitöltve a megfelelő attribútum értékekkel.
Írj rá egy `LocationTest` osztályt, valamint egy `testParse()` metódust,
mely ezt a metódust teszteli!

## Futtatás Mavennel

Futtasd le a tesztesetet Mavenből!

## Tesztesetek életciklusa

Írj egy `isOnEquator()` metódust, mely `true` értéket ad vissza, ha a pont az egyenlítőn van (`lat == 0`).
Írj egy `isOnPrimeMeridian()` metódust, mely `true` értéket ad vissza, ha a
pont a kezdő meridiánon van (`lon == 0`).

Írj rá teszteseteket! A `@BeforeEach` metódusban hozz létre egy `LocationParser` példányt,
majd tárold el egy attribútumban. A tesztesetek ezt a példányt hasznosítsák újra!

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

Írj rá tesztesetet! Ellenőrizd a neten található online kalkulátorok alapján.

A `parse()` metódusra írj olyan tesztesetet, mely egyszerre ellenőrzi a `name`, `lat` és `lon`
attribútumok értékét!

Írj egy `LocationOperators` osztályt, benne egy `List<Location> filterOnNorth(List<Location>)` metódust,
mely csak az északon lévő pontokat adja vissza (`lat > 0`)!

Írj rá tesztesetet a `LocationOperatorsTest` osztályban (ellenőrizz a nevekre)!

## Kivételkezelés és timeout tesztelése

A `Location` konstruktora dobjon `IllegalArgumentException` kivételt, ha nem megfelelő számot kap!
A szélesség értéke -90 és 90 közötti, a hosszúság értéke -180 és 180 közötti legyen!
Írj rá teszteseteket!

## Egymásba ágyazás

Hozz létre egy `LocationNestedTest` osztályt, melynek `@BeforeEach` annotációval ellátott metódusa
példányosítson egy `LocationParser` osztályt! Majd az egyik belső osztály `@BeforeEach`
annotációval ellátott metódusa hozzon létre egy kedvenc helyet `0,0` koordinátákkal,
egy másik belső osztály pedig `47.497912,19.040235` koordinátákkal! Mindegyikben legyenek teszt metódusok
az `isOnEquator()` és `isOnPrimeMeridian()` metódusokra!

## Tagek és metaannotációk használata

Hozz létre egy `LocationOperationsFeatureTest` annotációt, és tedd rá arra a `LocationOperatorsTest` osztályra,
és próbáld csak ezt futtatni IDEA-ból és Mavenből is!

## Tesztesetek ismétlése

Teszteld az `isOnEquator()` metódust! Vegyél fel egy tömbbe pár `Location` objektumot, ezek egy része az egyenlítőn legyen!
Tömbök tömbjét használj, azaz vegyél fel egy `boolean` értéket is, hogy mely esetén kell a tesztelendő metódusnak `true`
értéket visszaadnia!

Ismétlődő tesztekkel menj végig a tömbön, hívd meg az adott `Location` `isOnEquator()` metódusát, és vizsgáld, hogy a mellette
megadott `boolean` értéket adja-e vissza.

## Paraméterezett tesztek

Implementáld az előző feladatot az `isOnPrimeMeridian()` metódusra, de most `MethodSource` használatával!

Tegyél ki egy CSV állományba négy koordinátát, valamint a köztük lévő távolságot méterben! (A `Location` neve mindegy.)
Példányosíts le egy `Location` példányt az első két koordinátával, majd egy másikat a harmadik-negyedik paraméterrel,
majd ellenőrizd, hogy a kettő közötti különbség az ötödik mezőben megadott értéket adja-e vissza.

## Dinamikus tesztek

Hozz létre egy paraméterezett tesztet, mely az egyenlítőn lévő kedvenc helyek streamjéből készít
dinamikus teszteseteket úgy, hogy meghívja az `isOnEquator()` metódusukat!

## Tempdirectory extension

Hozz létre egy `LocationService` nevű osztályt, abban egy `void writeLocations(Path file, List<Location> locations)`, 
metódust, mely az első paraméterként megadott fájlba kiírja a második paraméterként megadott helyeket CSV (comma separated values)
formátumban.
A helyeket külön sorba írja ki, és a `name`, `lat` és `lon` attribútumok értékeit egymástól vessző (`,`) karakterrel elválasztva.

Írj egy tesztesetet, mely teszteli a kiírást, méghozzá úgy, hogy kiír pár `Location` példányt, és 
beolvassa a sorokat egy `List<String>` adatszerkezetbe (használd a `Files.readAllLines()` metódust)! Utána ebben 
a listában ellenőrizd szúrópróbaszerűen pl. a második elemet, hogy megfelelő-e. Pl. `Budapest,47.497912,19.040235`.

## JUnit 4 és 5 használata

(Opcionális.)

Írj egy JUnit 4 tesztesetet, és futtasd le a JUnit 5-ös tesztesetekkel együtt!

## Hamcrest

A `LocationService` osztályba írj egy metódust, mely egy CSV fájlból felolvassa a
benne szereplő kedvenc helyeket.

A visszaadott `List<Location>` adatstruktúrára Hamcrest asserteket írj!

## Saját Hamcrest matcher implementálása

(Opcionális.)

Írj egy olyan Hamcrest matchert, mely azt vizsgálja, hogy egy
`Location` legalább egyik koordinátája 0! Legyen a neve `LocationWithZeroCoordinate`!

## AssertJ

A Hamcrest feladatnál írt tesztesetet írd meg úgy is, hogy AssertJ-vel írd meg az
assertet!

## AssertJ kiterjeszthetőség

Definiálj egy olyan `Condition<Location>` feltételt, ami azt vizsgálja, hogy
a kedvenc hely legalább egyik koordinátája 0.

## Mockito

Hozz létre egy `LocationRepository` interfészt, melynek van egy
`Optional<Location> findByName(String name)` metódusa!

Hozz létre egy `DistanceService` osztályt, abban egy `Optional<Double> calculateDistance(String name1, String name2)`
metódust!
A metódus a két névvel hívja meg a repository `findByName()` metódusát!
Amennyiben az egyik is `Optional.empty()` értékkel tér vissza, adjon vissza egy
`Optional.empty()` értéket! Amennyiben egyik sem `empty()`, adja vissza a
kettő közötti távolságot! Egyrészt ellenőrizd, hogy jó névvel lett-e meghívva a
`calculateDistance()` metódus! Másrészt ellenőrizd nem létező nevekkel és
létező nevekkel is a helyes működést. (Mockolni kell a repository-t!)

## Teszt lefedettség

Futtass teszt lefedettség mérést a projekten IDEA és Jacoco segítségével is.
Hasonlítsd össze, hogy ugyanazt az eredményt hozza-e a kettő!

A nem lefedett területekre írj tesztet!
