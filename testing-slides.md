class: inverse, center, middle

# SZT-01 - Szoftvertesztelés Java platformon

---

## Java teszt eszközkészlet 1.

* JUnit
* Hamcrest
* AssertJ
* Mockito
* Jacoco
* XmlUnit


---

## Java teszt eszközkészlet 2.

* Arquillian
* DbUnit
* Selenium
* SoapUI
* Postman
* JMeter
* SonarQube, SonarLint
* CI

---

class: inverse, center, middle

# Bevezetés a JUnit használatába

---

## Unit tesztelés

* Forráskód legkisebb egységének tesztelésére
  * OO nyelv (pl. Java) esetén osztály
* Az adott egység helyesen oldja meg a rá bízott feladatot
* Előírt bemenetre az elvárt kimenetet adja-e
* Tipikusan automatizált
* Tipikusan a szoftverfejlesztők írják

---

## Mi nem unit teszt

* Több osztály kapcsolata
* Keretrendszer bevonása, pl. Spring, Java EE
* Adatbázis műveletek bevonása

---

## JUnit

* Java unit teszt keretrendszer
* Kent Beck (Agile Manifesto, Extreme programming), <br /> Erich Gamma (Programtervezési minták)
* Ant, Maven, Gradle, IDE támogatás
* Legelterjedtebb: JUnit 4 (2006)
* JUnit 5 (2017 szeptember)
* Java osztály - _test class_
* `@Test` annotációval ellátott metódus - _test case_
* Assert statikus metódusok


---

## Tesztelendő kód

```java
public class Employee {

  private String name;

  private int yearOfBirth;

  // Konstruktorok

  public int getAge(int atYear) {
    return atYear - yearOfBirth;
  }

  // Getter és setter metódusok

}
```

---

## Manuális ellenőrzés

```java
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

  @Test
  void testGetAge() {
    Employee employee = new Employee("John Doe", 1970);

    int age = employee.getAge(2019);

    System.out.println(age);
  }
}
```

---

## Given - when - then

* Tesztesetek felépítésének egy stílusa
* Behavior-Driven Development (BDD) részeként
* Given: előfeltételek
* When: tesztelendő funkció hívása
* Then: elvárt eredmény

---

## Metódus tesztelés

```java
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

  @Test
  void testGetAge() {
    // Given
    Employee employee = new Employee("John Doe", 1970);

    // When
    int age = employee.getAge(2019);

    // Then
    assertEquals(49, age);
  }
}
```

---

## Metódus tesztelés egybevonva

```java
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class EmployeeTest {

  @Test
  void testGetAge() {
    assertEquals(49,
      new Employee("John Doe", 1970).getAge(2019));
  }
}
```

---

## Maven integráció

* Teszteléshez szükséges könyvtárak `test` scope-pal
* Surefire plugin, `2.22.0` verziótól támogatja a JUnit 5-öt
* Tesztesetek az `src/test/java` könyvtárban, <br /> hozzá tartozó erőforrások a `src/test/resources` könyvtárban


---

## `pom.xml`

```xml
<dependencies>
  <dependency>
    <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter-engine</artifactId>
      <version>${junit5.version}</version>
    <scope>test</scope>
  </dependency>
</dependencies>

<build>
  <plugins>
    <plugin>
      <artifactId>maven-surefire-plugin</artifactId>
      <version>2.22.0</version>
    </plugin>
  </plugins>
</build>
```

---

## IDE integráció

* Összes teszt futtatása, csomag, osztály, metódus
* Elbukott tesztek újrafuttatása
* Debug
* Eredmény
    * Passed (zöld): lefutott, és az assert is sikeresen lefutott
    * Failure (sárga): elbukott az assert
    * Error (piros): kivétel, nem várt hiba

---

class: inverse, center, middle

# Unit tesztelés ígéretei

---

## Unit tesztelés ígéretei

* Hibák mielőbbi megtalálása, ezáltal költségcsökkentés
* Refactoring
* Hibajavításkor regresszió, funkció nem romolhat el, <br /> hiba nem jelenhet meg újra
* Komplex rendszer esetén hamarabb találjuk meg a hibás komponenst
* High cohesion, osztály használható interfésszel rendelkezik
* Komponensek közötti interakció és függőségek mielőbbi átgondolása
* Tiszta, lazán kapcsolt kód, low coupling
* Fejlesztés egyszerűsítése
* Dokumentációként viselkedik

---

class: inverse, center, middle

# Futtatás Mavennel

---

## Futtatás Mavennel

* Default életciklus `test` fázis: `mvn test`
* Surefire plugin `test` goalja fut le
* Tesztek futtatásának kihagyása: `mvn package -DskipTests`
* Csak egy teszt futtatása: <br /> `mvn package -Dtest=EmployeeTest#testGetAge`

---

class: inverse, center, middle

# Tesztesetek életciklusa

---


## Test fixture

* Minden, ami szükséges a teszteset sikeres lefutásához <br /> (inicializálás - ismert állapotba hozás, előfeltételek)
* Kiemelhető külön metódusba
* `@BeforeEach`, `@AfterEach` annotációkkal ellátott metódusok
* `@BeforeAll`, `@AfterAll` annotációkkal ellátott _statikus_ metódusok

---

## Test fixture példa

```java
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

	Employee employee;

	@BeforeEach
	void initEmployee() {
		employee = new Employee("John Doe", 1970);
	}

    @Test
    void testGetAge() {
        assertEquals(49, employee.getAge(2019));
    }

	@Test
	void testWithZeroAge() {
		assertEquals(0, employee.getAge(1970));
	}
}
```

---

## Test fixture külön interfészben

```java
public interface PrintNameCapable {

  @BeforeEach
  default void printName(TestInfo testInfo) {
    System.out.println("Test case display name: "
      + testInfo.getDisplayName());
  }
}
```

```java
public class EmployeeTest
  implements PrintNameCapable {

  // ...

}
```

---

## Izoláltság

* Törekedni az izoláltságra, a unit tesztek függetlenségére
* Izoláltságot megszegi az állapot átvitele teszt esetek között <br /> (pl. statikus attribútumok értékei)
* JUnit támogatás: minden teszteset futtatásakor újra <br /> példányosítja a tesztosztályt

---

## Lefutási sorrend

```java
@BeforeEach
void initEmployee() {
	employee = new Employee("John Doe", 1970); // INIT
}

@Test
void testGetAge() {
    assertEquals(49, employee.getAge(2019)); // TC1
}

@Test
void testWithZeroAge() {
	assertEquals(0, employee.getAge(1970)); // TC2
}
```

Példányosítás, INIT, TC1, példányosítás, INIT, TC2

---

## Metódusok futtatásának <br /> sorrendje

* Nem magától érthetődő, de determinisztikus - azaz ismételhető
* Integrációs teszteknél szükség lehet a sorrend meghatározására
	* `@TestMethodOrder` annotációval, <br /> `MethodOrderer` implementációval paraméterezhető
* Sorrendek
	* `MethodOrderer.Alphanumeric` - ábécé sorrendben
	* `MethodOrderer.OrderAnnotation` - `@Order` annotáció <br /> szerint, mely egy egész számmal paraméterezhető
	* `MethodOrderer.Random` - véletlenszerű

---

## Disabled

* Nem futtatja le a környezet
* Osztályra és metódusra is tehető a `@Disabled` annotáció
* Opcionálisan megadható üzenet
* Ne használjunk commentelést
* Ideiglenesen használjuk

---

## Conditional Test Execution

* Feltételes teszt végrehajtás különböző feltételektől függően
* Pl. operációs rendszer, JVM verziószáma, <br /> operációs rendszer környezeti változó,
	Java környezeti változó
* Pl. `@DisabledOnOs(WINDOWS)`

---

class: inverse, center, middle

# Elnevezések

---

## `@DisplayName` annotáció

* Teszt osztályokat és teszt eseteket lehet elnevezni
* Osztályra és metódusra is tehető `@DisplayName` annotáció, <br /> melynek paraméterül adandó a megjelenítendő név
* Pl. IDE-ben jelenik meg

```java
@Test
@DisplayName("Test the calculation of the age with positive number")
void testGetAge() {
	// ...
}
```

---

## Display name generation

* Teszt osztály és teszt metódus nevéből generálja <br /> (pl. kis- és nagybetűk konvertálása, aláhúzásjelek cseréje)
* Megadható a `@DisplayNameGeneration` annotációval
	* Beépített vagy saját implementáció is használható
* Globálisan is konfigurálható

```java
@Test
@DisplayNameGeneration
  (DisplayNameGenerator.ReplaceUnderscores.class)
void get_age_with_positive_number() {
	// ...
}
```

---

class: inverse, center, middle

# Assert

---

## További assert metódusok

* `assertNull()`, `assertNotNull()` `null` vizsgálatra
* `assertEquals()` különböző primitív típusokkal
* `assertNotEquals()` annak ellenőrzésére, hogy nem egyenlőek
* objektumok esetén az `equals()` metódust hívja
* `assertSame()`, `assertNotSame()` hasonlít össze referenciákat
* `assertTrue()`, `assertFalse()` - lehetőleg kerüljük, <br /> helyette `assertEquals()`
* `fail()` - ha nem megfelelő ágra kerül a vezérlés

---

## `assertEquals` lebegőpontos <br /> számoknál

* Számábrázolási pontatlanságok miatt adjunk meg egy `delta` paramétert

```java
assertEquals(1.0, 1.0, 0.005);
```

---

## Assert metódusok több elem <br /> esetén

* `assertArrayEquals` tömbök kezelésére
* `assertIterableEquals` `Iterable`, pl. kollekciók vizsgálatára
* `assertLinesMatch` `List<String>` összehasonlítására, <br /> de nem csak pontos egyezőséget vizsgál, <br /> hanem képes pl. reguláris kifejezésként is értelmezni az elvárt értéket

---

## Egyszerre több assert

* Kiértékeli az összes assertet függetlenül attól, hogy sikeres vagy sikertelen
* Mindegyiknek megjelenik az eredménye

```java
assertAll(
                () -> assertEquals("John Doe", employee.getName()),
                () -> assertEquals(49, employee.getAge(2019))
        );
```

---

## Egymásba ágyazhatóság

```java
assertAll("employee",
  () -> {
      assertNotNull(employee.getName());
      assertAll("name",
              () -> assertTrue(employee.getName().startsWith("John")),
              () -> assertTrue(employee.getName().endsWith("Doe"))
      );
  },
  () -> assertAll("age",
          () -> assertTrue(age > 40),
          () -> assertTrue(age > 50))
);
```

---

## Assert kollekciókon

```java
@Test
void testListEmployees() {
    List<Employee> employees = employeeRepository.listEmployees();

    assertEquals(2, employees.size());
    assertEquals("Jack Doe", employees.get(0).getName());

    assertEquals(List.of("Jack Doe", "John Doe"),
      employees.stream().map(Employee::getName)
        .collect(Collectors.toList()));
}
```

---

## Assert

* JUnit egy korlátozott halmazzal rendelkezik
* Általános célú
    * Hamcrest (népszerűsége csökken, legacy)
    * AssertJ
* Speciális
    * XMLUnit
    * JSONassert
    * DbUnit

---

## Alternatív üzenet

```java
assertEquals(0, employee.getAge(1970), "Age must be zero");
```

Tesztek gyors lefutása érdekében üzenet kiértékelése csak bukó teszt esetén:

```java
assertEquals(1970,
  getAgeFromXmlDocument(document), () -> documentToString(document));
```

---

## Assume

* Előfeltételek ellenőrzésére használjuk
* Ha már nincs értelme tovább futtatni a tesztesetet
* Ha elbukik, akkor nem fut tovább a teszt, de nem jelenti hibásnak
* Az assert metódusokhoz hasonló metódusokkal rendelkezik

---

class: inverse, center, middle

# Kivételkezelés és timeout tesztelése

---

## Kivételkezelés

* Lambda kifejezéssel paraméterezhető

```java
IllegalArgumentException iae = assertThrows(
  IllegalArgumentException.class,
  () -> new Employee("John Doe", 1800));
assertEquals("Invalid year: 1800", iae.getMessage());
```

---

## Timeout

* `Duration` példánnyal és lambda kifejezéssel paraméterezhető
* `assertTimeout` azonos szálon futtatja
* Különböző gépeken más lehet

```java
assertTimeout(ofMinutes(2), () -> employeeService.calculateYearlyReport());
```

Visszatérési értékkel

```java
String result = assertTimeout(ofMinutes(2),
  () -> employeeService.calculateYearlyReport());
```

---

## Preemtively

* `assertTimeoutPreemptively` külön szálon futtatja és megszakítja timeout esetén

```java
String result =
  assertTimeoutPreemptively(ofMinutes(2), () -> employeeService.calculateYearlyReport());
```

---

class: inverse, center, middle

# Egymásba ágyazás

---

## Belső osztályok

* Belső osztályok használatával a tesztesetek között hierarchia építhető fel

```java
public class EmployeeTest {

    Employee employee;

	@Nested
    class WithYearOfBirth1970 {

		@BeforeEach
		void init() {
			employee = new Employee("John Doe", 1970);
		}

		@Test
		void testAge() {
			// ...
		}

	}
}
```

---

## Belső osztályok - folytatás

```java
@Nested
class WithYearOfBirth2000 {

	@BeforeEach
	void init() {
		employee = new Employee("John Doe", 2000);
	}

	@Test
	void testAge() {
		// ...
	}

}
```

* Alkalmas kódmegosztásra is, közös inicializáció

---

class: inverse, center, middle

# Tagek és metaannotációk használata

---

## Tagek

* Egy teszteset ellátható egy vagy több taggel, mely szerint később szűrni lehet

```java
@Test    
@Tag("unit")
@Tag("feature-329")
void testGetAge() {
}
```

---

## Filterelés Mavennel

```xml
<plugin>
	<artifactId>maven-surefire-plugin</artifactId>
	<version>2.22.0</version>
	<configuration>
		<groups>unit</groups>
	</configuration>
</plugin>
```

* Tag expression adható meg
    * vagy (`|`)
    * és (`&`)
    * nem (`!`) operátorok
    * zárójelek használhatóak <br /> (pl. `unit & feature-329 & !long-running`)

---

## Metaannotációk

* JUnit annotációk használata új annotáció létrehozására

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Test
@Tag("unit")
public @interface UnitTest {
}
```

```java
@UnitTest
void testGetAge() {
}
```

---

class: inverse, center, middle

# Tesztesetek ismétlése

---

## Egyszerű ismétlés

```java
@RepeatedTest(5)
void testGetAge() {
  Employee employee = new Employee("John Doe", 2000);
  assertEquals(19, employee.getAge(2019));
}
```

Számozás egytől

```
repetition 1 of 5
repetition 2 of 5
```

---

## Név módosítása

```java
@RepeatedTest(value = 5,
  name = "Get age {currentRepetition}/{totalRepetitions}")
void testGetAge() {
  Employee employee = new Employee("John Doe", 2000);
  assertEquals(19, employee.getAge(2019));
}
```

```
Get age 1/5
Get age 2/5
```

---

## RepetitionInfo injektálás

```java
private int[][] values = {{2000, 0}, {2010, 10}, {2015, 15}, {2050, 50}, {1990, -10}};

@RepeatedTest(value = 5, name = "Get age {currentRepetition}/{totalRepetitions}")
void testGetAge(RepetitionInfo repetitionInfo) {
  Employee employee = new Employee("John Doe", 2000);
  assertEquals(values[repetitionInfo.getCurrentRepetition() - 1][1],
    employee.getAge(values[repetitionInfo.getCurrentRepetition() - 1][0]));
}
```

---

class: inverse, center, middle

# Paraméterezett tesztek

---

## Paraméterezett tesztek

* Ugyanazon teszteset lefuttatása különböző paraméterekkel, értékekkel
* Értékek felolvasása külső forrásból
* Experimental

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-params</artifactId>
    <version>5.5.1</version>
    <scope>test</scope>
</dependency>
```

---

## Egyszerű értékekkel

* `@ParameterizedTest` és `@ValueSource` annotáció
* Automatikus típuskonverzió

```java
@ParameterizedTest
@ValueSource(strings = { "racecar", "radar", "able was I ere I saw elba" })
void palindromes(String candidate) {
    assertTrue(isPalindrome(candidate));
}
```

* További típusokkal: `ints`, `doubles`, stb.
* Null és üres (String és kollekció): <br /> `@NullSource`, `@EmptySource`, `@NullAndEmptySource`

```
[1] racecar
[2] radar
[3] able was I ere I saw elba
```

---

## Enum

* `@EnumSource` annotációval
* Szűrhetők az értékek (akár reguláris kifejezéssel is)

```java
@EnumSource(value = TimeUnit.class, names = { "DAYS", "HOURS" })
```

---

## MethodSource

* `@MethodSource` annotáció
* Hivatkozás egy *statikus* metódusra
* Visszaadhat `Stream`, `Collection`, `Iterator`, `Iterable` példányt
* `@ParameterizedTest` annotációban megadható a teszteset neve

---

## MethodSource példa

```java
@ParameterizedTest(name = "year {0} - age {1}")
@MethodSource("createAges")
void testGetAge(int year, int age) {
    Employee employee = new Employee("John Doe", 2000);
    assertEquals(age, employee.getAge(year));
}

static Stream<Arguments> createAges() {
    return Stream.of(
            arguments(2000, 0),
            arguments(2010, 10),
            arguments(2050, 50),
            arguments(1990, -10)
    );
}
```

---

## ArgumentsSource

```java
@ArgumentsSource(YearAgeProvider.class)
void testGetAge(int year, int age) {
    Employee employee = new Employee("John Doe", 2000);
    assertEquals(age, employee.getAge(year));
}

static class YearAgeProvider implements ArgumentsProvider {
  @Override
  public Stream<? extends Arguments>
      provideArguments(ExtensionContext extensionContext) {
    return Stream.of(
            arguments(2000, 0),
            arguments(2010, 10),
            arguments(2050, 50),
            arguments(1990, -10)
    );
  }
}
```

---

## CSV

* Comma Separated Values
* `@CsvSource` - annotációnak `String[]` a sorok
* `@CsvFileSource` - CSV állomány a classpath-ról
  * karakterkódolás
  * sortörés karakter
  * elválasztó karakter
  * első X sor átugrása

```java
@CsvFileSource(resources = "/get-age.csv")
```

---

class: inverse, center, middle

# Dinamikus tesztek

---

## Dinamikus teszt

```java
@TestFactory
Stream<DynamicTest> arePalindromes() {
  return Stream.of("racecar", "radar", "mom", "dad")
    .map(text ->
       dynamicTest(
       	 "Is palindrome? " + text,
       	 () -> assertTrue(isPalindrome(text))));
}
```

---

## Dinamikus teszt elvárt értékkel

```java
@TestFactory
Stream<DynamicTest> testGetAge() {
  return Stream
   .of(new Integer[][]{{2000, 0}, {2010, 10}, {2015, 15}, {2050, 50}, {1990, -10}})
   .map(item ->
      dynamicTest(
        "In year " + item[0] + " employee must be " + item[1] + "years old",				
        () -> assertEquals((int) item[1],
        new Employee("John Doe", 2000).getAge(item[0]))));
}
```

---

class: inverse, center, middle

# TempDirectory extension

---

## `@TempDir` annotáció

* Minden teszteset előtt létrehoz ez ideiglenes könyvtárat, és a végén letörli

```java
public class EmployeeWriterTest {

  @TempDir
  Path tempDir;

  @Test
  void testWriteEmployee() throws IOException {
    Path file = tempDir.resolve("john-doe.txt");
    new EmployeeWriter()
      .write(file, new Employee("John Doe", 1970));
    assertEquals("John Doe, 1970", Files.readString(file));
  }
}
```

---

class: inverse, center, middle

# JUnit 4 és 5 használata

---

## JUnit 4 és 5 együttműködése

* JUnit modulok
	* Platform - tesztesetek futtatására
	* Jupiter - JUnit 5 tesztek írására
	* Vinetage - JUnit 4 támogatás
* Maven - függőségek felvétele

---

## `pom.xml`

```xml
<dependency>
	<groupId>org.junit.jupiter</groupId>
	<artifactId>junit-jupiter-engine</artifactId>
	<version>${junit5.version}</version>
	<scope>test</scope>
</dependency>
<dependency>
	<groupId>junit</groupId>
	<artifactId>junit</artifactId>
	<version>${junit4.version}</version>
	<scope>test</scope>
</dependency>
<dependency>
	<groupId>org.junit.vintage</groupId>
	<artifactId>junit-vintage-engine</artifactId>
	<version>${junit5.version}</version>
	<scope>test</scope>
</dependency>
```

---

class: inverse, center, middle

# JUnit legjobb gyakorlatok

---

## F.I.R.S.T elvek

* Fast
    * Ha lassú, a fejlesztő nem futtatja
* Isolated/Independent
    * Tesztesetek között ne tároljunk állapotot
    * Tesztek futtatási sorrendjére ne alapozzunk
* Repeatable

---

## F.I.R.S.T elvek - folytatás

* Self-Validating
    * Ne manuálisan kelljen ellenőrizni
    * Mellőzzük a `System.out.println()` használatát
* Thorough
    * Összes elágazás
    * Kivételes input adatok tesztelése
---

## JUnit tanácsok

* Lehető legkisebb scope, osztály
* Egyszerű tesztek
* Dokumentációs célzat
* Ha tudunk, `assertTrue()` helyett `assertEquals()` metódust használjunk
* Egyszerre egy logikai állítást ellenőrizzünk
    * Törekedjünk egy, kevés assertre
    * Lehet több is, ha összetartoznak

---

## Gyakori hibák <br /> JUnit használatakor

* Szükségtelen assertion
* Assert metódus nem megfelelő formájának használata
* Nem teljes lefedettség
* Külső függőségek
* Nincs unit teszt
* Test infected

---

class: inverse, center, middle

# Hamcrest

---

## Hamcrest

* Harmadik generációs assertek írására un. matcherek használatával
	* Informatívabb hibaüzenet
	* Több lehetőség az összehasonlításra
	* Könnyen használható API
	* Egyszerűbb összehasonlításokból bonyolultabbakat <br /> lehessen összeállítani
	* Saját összehasonlításokat lehessen írni
* Matchers anagrammája

---

## Egyszerű ellenőrzés

```java
assertThat(employee.getAge(2019), equalTo(40));
// instanceOf, nullValue, sameInstance, stb.

assertThat(pi, closeTo(3.14, 0.005));
// greaterThan, greaterThanOrEqualTo, stb.

// Olvashatóságot könnyítve
assertThat(employee.getAge(2019), is(equalTo(40)));

assertThat(employee.getName(),
  startsWithIgnoringCase("john"));
// equalToIgnoringWhiteSpace, containsString,
// endsWith, startsWith
```

---

## JavaBean és logikai kapcsolatok

```java
assertThat(employee, hasProperty("name", equalTo("John Doe")));

assertThat(employee, allOf(hasProperty("name", equalTo("John Doe")),
                hasProperty("yearOfBirth", equalTo(1970))));
// anyOf és not
```

* A `hasProperty` használata nem refactor-tűrő

---

## Kollekciók

```java
// Pontos egyezőséget néz
assertThat(List.of("John", "Jane", "Jack"),
  contains("John", "Jane", "Jack"));
// containsInAnyOrder

// Rész egyezőség
assertThat(List.of("John", "Jane", "Jack"), hasItem("Jane"));

assertThat(List.of(employees),
  hasItem(hasProperty("name", startsWithIgnoringCase("john"))));
// hasEntry, hasKey, hasValue Map esetén
```

---

## `pom.xml` függőség

```xml
<dependency>
    <groupId>org.hamcrest</groupId>
    <artifactId>hamcrest</artifactId>
    <version>${hamcrest.version}</version>
    <scope>test</scope>
</dependency>
```

---

class: inverse, center, middle

# Saját Hamcrest matcher implementálása

---

## Saját matcher implementáció

```java
public class EmployeeWithNameMatcher extends TypeSafeMatcher<Employee> {

  private Matcher<String> matcher;

  public EmployeeWithNameMatcher(Matcher<String> matcher) {
      this.matcher = matcher;
  }

  @Override
  protected boolean matchesSafely(Employee item) {
      return matcher.matches(item.getName());
  }

}
```

---

## Matcher folytatás

```java
@Override
protected void describeMismatchSafely(Employee item, Description mismatchDescription) {
  mismatchDescription
    .appendText(" employee with name ")
    .appendValue(item.getName());
}

@Override
public void describeTo(Description description) {
  description
    .appendText(" employee with name ")
    .appendDescriptionOf(matcher);
}
```

---

## Statikus factory metódus

```java
public static Matcher employeeWithName(Matcher<String> matcher) {
	return new EmployeeWithNameMatcher(matcher);
}
```

---

## Használat

```java
assertThat(employee, employeeWithName(startsWithIgnoringCase("jack")));
```

* Üzenet hiba esetén:

```
java.lang.AssertionError:
Expected:  employee with name a string starting with "jack" ignoring case
     but:  employee with name "John Doe"
```

---

class: inverse, center, middle

# AssertJ

---

## AssertJ

* Fluent assertion
* IDE code complete
* Saját assert írható és generálható

---

## Egyszerű assert

```java
import static org.assertj.core.api.Assertions.*;

assertThat(employee.getName()).isEqualTo("John Doe");
// isEqualToIgnoringCase

assertThat(employee.getName()).startsWith("John")
                           .endsWith("Doe");
```

* Fluent

---

## Kollekciók

```java
assertThat(employeeNames)
  .hasSize(2)
  .contains("John Doe", "Jack Doe")
  .doesNotContain("Jane Doe");
// containsOnly

assertThat(employees)
  .extracting("name")
  .contains("John Doe", "Jack Doe");
assertThat(employees)
  .extracting(Employee::getName)
  .contains("John Doe");
```

---

## Tuple és szűrés

```java
assertThat(employees)
  .extracting(Employee::getName, Employee::getYearOfBirth)
  .contains(tuple("John Doe", 1970),
    tuple("Jack Doe", 1980));

assertThat(employees)
  .filteredOn(e -> e.getName().contains("a"))
  .extracting(Employee::getName)
  .containsOnly("Jack Doe");
```

---

## Üzenetek testreszabása

Description: hibaüzenet előtt jelenik meg szögletes zárójelben

```java
assertThat(employee.getAge())
  .as("check %s's age", employee.getName())
  .isEqualTo(40);
```

Hibaüzenet felülbírálása

```java
assertThat(employee.getName())
  .withFailMessage("should be %s", "John Doe")
  .isEqualTo("John Doe");
```

---

## Soft assertion

```java
SoftAssertions softly = new SoftAssertions();
softly.assertThat(employee.getName()).isEqualTo("John Doe");
softly.assertThat(employee.getAge()).isEqualTo(40);
softly.assertAll();
```

```java
@ExtendWith(SoftAssertionsExtension.class)
public class EmployeeTest {

  @Test
  public void testEmployee(SoftAssertions softly) {
    Employee employee = ...

    softly.assertThat(employee.getName())
      .isEqualTo("John Doe");
    softly.assertThat(employee.getAge())
      .isEqualTo(40);
    // Nem kell assertAll() hívás!
  }
}
```

* Összegyűjti a hibákat

---

## Assumption

```java
assumeThat(employee.getName()).isEqualTo("John Doe");
```

---

## `pom.xml`

```xml
<dependency>
  <groupId>org.assertj</groupId>
  <artifactId>assertj-core</artifactId>
  <version>${assertj.version}</version>
  <scope>test</scope>
</dependency>
```

---

class: inverse, center, middle

# AssertJ kiterjeszthetőség

---

## Condition

```java
Condition<Employee> familyNameDoe =
 new Condition<>(e-> e.getName().endsWith("Doe"), "family name is Doe");
assertThat(employee).has(familyNameDoe);
// is, has, doesNotHave
// not, allOf, anyOf - több condition
// are, have, areAtLeast, areAtMost, areExactly - kollekció elemeire
```

---

## Saját assert implementálása

```java
public class EmployeeAssert extends AbstractAssert<EmployeeAssert, Employee> {

  public static EmployeeAssert assertThat(Employee employee) {
    return new EmployeeAssert(employee);
  }

  public EmployeeAssert(Employee employee) {
    super(employee, EmployeeAssert.class);
  }

  public EmployeeAssert hasName(String name) {
    if (!Objects.equals(actual.getName(), name)) {
      failWithMessage(
        "Expected employees name " +
          "to be <%s> but was <%s>",
    	name, actual.getName());
    }

    return this;
  }
}
```

---

class: inverse, center, middle

# Mockito

---

## Külső függőségek

* SUT = system under test - tesztelendő osztály, de lehetnek külső függőségei
* Kicserélni tesztelésre előkészített implementációkkal: test double
    * Dummy: nem csinál semmit (leforduljon)
    * Fake: működő implementáció, de nem éles: <br /> pl. adatbázis helyett in-memory implementáció
    * Stubs: előkészített válaszokkal rendelkezik, <br /> nem használható általánosan
    * Mock: regisztrálja, hogy milyen hívások <br /> történtek rajta, milyen paraméterekkel

---

## Unit tesztek csoportosítása

* Állapot alapú: a megfelelő bemenetre az elvárt kimenetet kapjuk eredményül
* Viselkedés alapú: a megfelelő metódusok hívódtak a megadott bemenetre

---

## Mockito

* Test double példányok generálására
* Úgy látszanak, mint az eredeti példány
	* Definiálható, hogy milyen metódushívásra mit adjon vissza
	* Ellenőrizhető, hogy melyik metódus és milyen paraméterrel lett hívva

---

## Függőség

![UML diagram](images/mock.png)

---

## Service és Repository

```java
public class EmployeeService {

  private EmployeeRepository employeeRepository;

  public EmployeeService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  public boolean createEmployee(String name, int yearOfBirth) {
    name = name.trim();
    Optional<Employee> employee = employeeRepository
      .findEmployeeByName(name);
    if (employee.isPresent()) {
      return false;
    }
    else {
      employeeRepository.saveEmployee(
        new Employee(name, yearOfBirth));
      return true;
    }
  }
}
```

---

## Repository

```java
public class EmployeeRepository {

  private List<Employee> employees = new ArrayList<>();

  public void saveEmployee(Employee employee) {
      employees.add(employee);
  }

  public Optional<Employee>
      findEmployeeByName(String name) {
    return employees
      .stream()
      .filter(e -> e.getName().equals(name))
      .findFirst();
  }
}
```

---

## Service test mock objektummal

```java
@Test
void testSaveEmployee() {
	EmployeeRepository repository = mock(EmployeeRepository.class);
	EmployeeService service = new EmployeeService(repository);
	boolean created = service.createEmployee("John Doe", 1970);
	assertTrue(created);
}
```

---

## Dependency injection

```java
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

  @Mock
  EmployeeRepository repository;

  @InjectMocks
  EmployeeService service;

  @Test
  void testSaveEmployee() {
    boolean created =
      service.createEmployee("John Doe", 1970);
    assertTrue(created);
  }
}
```

---

## Visszatérési érték definiálása

```java
when(repo.findEmployeeByName(anyString()))
                .thenReturn(Optional.of(new Employee("John Doe", 1970)));

boolean created = service.createEmployee("John Doe", 1970);
assertFalse(created);
```

Kivétel:

```java
when(repo.findEmployeeByName(anyString()))
  .thenThrow(
    new IllegalStateException("Constraint violation"));
```

---

## Metódushívás ellenőrzése

```java
verify(repo).saveEmployee(any());
```

```java
verify(repo, never()).saveEmployee(any());
```

---

## Paraméter ellenőrzése

```java
ArgumentCaptor<Employee> employeeCaptor =
  ArgumentCaptor.forClass(Employee.class);
verify(repo).saveEmployee(employeeCaptor.capture());

assertEquals("John Doe", employeeCaptor.getValue().getName());
assertEquals(1970, employeeCaptor.getValue().getYearOfBirth());
```

```java
verify(repo).saveEmployee(argThat(e -> e.getName().equals("John Doe")));
```

Generikus típussal:

```java
ArgumentCaptor<List<Employee>> employeeListCaptor
  = ArgumentCaptor.forClass((Class) List.class);
```

---

## `pom.xml`

```xml
<dependency>
	<groupId>org.mockito</groupId>
	<artifactId>mockito-junit-jupiter</artifactId>
	<version>${mockito.version}</version>
	<scope>test</scope>
</dependency>
```

---

class: inverse, center, middle

# Teszt lefedettség

---

## Teszt lefedettség

* A tesztek futtatása során a forráskód mely része fut
	* Utasítások
	* Lehetséges ágak (feltételek esetén)
* 70-80%-os lefedettség jónak mondható
* White box testing
* Manuális tesztelésnél is alkalmazható
* IDE

---

## Jacoco


```xml
<plugin>
	<groupId>org.jacoco</groupId>
	<artifactId>jacoco-maven-plugin</artifactId>
	<version>${jacoco.version}</version>
	<executions>
		<execution>
			<id>default-prepare-agent</id>
			<goals>
				<goal>prepare-agent</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```

* Java agent alapon
* Gyűjtés a `jacoco.exec` fájlba
* Riport kérése az `mvn jacoco:report` paranccsal

---

class: inverse, center, middle

# XmlUnit

---

## XmlUnit

* XML összehasonlítás
* XPath kifejezések
* Séma validáció

---

class: inverse, center, middle

# Integrációs tesztelés Spring Boot használatával

---

## `pom.xml` függőségek

```xml
<dependencies>

	...

	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-test</artifactId>
		<scope>test</scope>
		<exclusions>
			<exclusion>
				<groupId>junit</groupId>
				<artifactId>junit</artifactId>
			</exclusion>
		</exclusions>
	</dependency>

	<dependency>
		<groupId>org.junit.jupiter</groupId>
		<artifactId>junit-jupiter-engine</artifactId>
		<version>${junit.version}</version>
		<scope>test</scope>
	</dependency>
</dependencies>
```

---

## `pom.xml` pluginek

```xml
<build>
	<plugins>
		<plugin>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-maven-plugin</artifactId>
		</plugin>
		<plugin>
			<artifactId>maven-surefire-plugin</artifactId>
			<version>2.22.1</version>
		</plugin>
	</plugins>
</build>
```

---

## Spring `TestExecutionListener`

---

class: inverse, center, middle

# Integrációs tesztelés Arquillian használatával

---

## Arquillian

* Integrációs és funkcionális tesztek futtatása eredeti futtatókörnyezet (konténeren) belül
	* Java EE alkalmazásszerver
	* Servlet container (Tomcat, Jetty, stb.)
	* CDI implementáció
	* OSGi konténer
* Tulajdonságai
	* Konténer életciklusának vezérlése (indítás, leállítás, telepítés)
	* Tesztek felruházása új lehetőségekkel (pl. dependency injection)

---

## Célkitűzések

* Legyen hasonlóan egyszerű, mint a unit tesztek futtatása
* Támogassa a konténer cserélhetőséget
* IDE-ből és build eszközből is futtatható legyen
* Létező teszt eszközökhöz integrálható legyen

---

## Működési mód

* In-container működési mód
* Teszt osztályonként elkészít egy telepítőcsomagot, melyben benne van a teszt eset is
* Telepíti a szerverre
* Konténeren belül futtatja
	* `System.out.println` alkalmazásszerver napló állományában jelenik meg
* Eredményt kijuttatja a teszt futtató keretrendszernek
* A `@BeforeClass` és `@AfterClass` metódusok a kliens JVM-ben futnak
* Egyelőre nem képes ugyanazt a deploymentet több teszt osztályon át használni (fejlesztés folyamatban: [ARQ-197](https://issues.jboss.org/browse/ARQ-197))

---

## Teszt eset

* Un. _micro deployment_ összeállítása `ShrinkWrap` segítségével
	* Mi kerüljön be a jar, war, ear állományba, mely telepítésre kerül
	* Akár pár komponens (esetleg mock függőségekkel)
	* Kisebb scope - hiba könnyebben azonosítható
* Konténer szolgáltatások elérése
	* Dependency injection (field és teszt metódus paraméterre is)
	* `@Inject`, `@Resource`, `@EJB`, `@PersistenceContext` és `@PersistenceUnit` annotációk

---

## Teszt eset implementáció

```java
@RunWith(Arquillian.class)
public class NameTrimmerIntegrationTest {

    @Inject
    private NameTrimmer nameTrimmer;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(NameTrimmer.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void testTrim() {
        assertEquals("John Doe", nameTrimmer.trimName("  John Doe  "));
    }

}
```

---

## `pom.xml`

```xml
 <dependency>
	<groupId>org.jboss.arquillian.core</groupId>
	<artifactId>arquillian-core-api</artifactId>
	<version>1.4.1.Final</version>
	<scope>test</scope>
</dependency>
<dependency>
	<groupId>org.jboss.arquillian.junit</groupId>
	<artifactId>arquillian-junit-container</artifactId>
	<version>1.4.1.Final</version>
	<scope>test</scope>
</dependency>
<dependency>
	<groupId>org.wildfly.arquillian</groupId>
	<artifactId>wildfly-arquillian-container-remote</artifactId>
	<version>2.1.1.Final</version>
	<scope>test</scope>
</dependency>
```

---

## Deployment ellenőrzése

* `System.out.println(webArchive.toString(true));`
* `src/test/resources/arquillian.xml`

```xml
<?xml version="1.0"?>
<arquillian>
    <engine>
        <property name="deploymentExportPath">target/deployments</property>
    </engine>
</arquillian>
```

---

class: inverse, center, middle

# Adatbázis réteg integrációs tesztelése Arquilliannal

---

## Adatbázis tesztelés

* Nem unit teszteljük
* Konténerben érdemes a konténer által biztosított szolgáltatások miatt
	* Pl. deklaratív tranzakciókezelés
	* Dependency injection, pl. `@PersistenceUnit`

---

## Idempotencia és izoláltság

* Tesztesetek egymásra hatással vannak
    * Állapot: pl. adatbázis
* Ugyanazon tesztkörnyezeten több tesztelő vagy harness dolgozik
* Megoldás:
    * Teszteset "rendet tesz" maga előtt, un. set-up
    * "Rendet tesz" maga után, un. tear down
		* Rollback?
    * Test fixture
        * Legszélsőségesebb megoldás: adatbázis törlése

---

## Deployment

* Entitások és `Dao` osztály
* `persistence.xml`

```java
WebArchive webArchive =
        ShrinkWrap.create(WebArchive.class)
                .addClasses(Employee.class, EmployeeDaoBean.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
```

---

## Adatbázis inicializálás

* Séma inicializáció
  * Alkalmazáson kívül
  * JPA schema generation
  * Alkalmazáson belül: Flyway vagy Liquibase - `DataSource` injektálható
* Adat inicializáció
  * JDBC, SQL utasításokkal - `DataSource` injektálható
  * JPA-val - `@PersistenceContext EntityManager em;` használható
  * `@Transactional` annotáció csak a `@Test` annotációval ellátott metódusra tehető
  * Dao-val
  * DbUnit használatával, `arquillian-persistence-dbunit` Arquillian extension

---

## Séma inicializáció alkalmazáson belül

* Adott library (Flyway vagy Liquibase) deploymentben elhelyezendő (Maven függőség alapján)
* Konfigurációs állományok deploymentben elhelyezendőek

```xml
<dependency>
    <groupId>org.jboss.shrinkwrap.resolver</groupId>
    <artifactId>shrinkwrap-resolver-impl-maven</artifactId>
    <version>3.1.3</version>
    <scope>test</scope>
</dependency>
```

```java
.addAsLibraries(Maven.configureResolver().loadPomFromFile("pom.xml")
  .resolve("org.flywaydb:flyway-core").withoutTransitivity().asSingleFile())
```

---

## Resource állományok

A `resources` könyvtárban lévő állományok a deploymentbe

```java
Files.walk(Paths.get("src/main/resources"))
        .filter(p -> !p.toString().contains("META-INF"))
        .filter(p -> Files.isRegularFile(p))
        .map(p -> Paths.get("src/main/resources").relativize(p))
        .map(p -> p.toString().replace("\\", "/"))
        .forEach(s -> webArchive.addAsResource(s, s));
```

---

## Adatbázis inicializálás JDBC-vel

```java
@RunWith(Arquillian.class)
public class EmployeeDaoBeanIntegrationTest {

  // ...

  @Resource(lookup = "java:/jdbc/EmployeeDS")
  private DataSource dataSource;

  @Before
  public void init() throws Exception {
      try (Connection c = dataSource.getConnection();
           PreparedStatement ps = c.prepareStatement("delete from employees")) {
           ps.executeUpdate();           
      }
      try (Connection c = dataSource.getConnection();
           PreparedStatement ps = c.prepareStatement("insert into employees(name) values (?)")) {
           ps.setString("name", "John Doe");
           ps.executeUpdate();           
           ps.setString("name", "Jack Doe");
           ps.executeUpdate();           
      }
  }  
}
```

---

## Adatbázis inicializálás Dao-val

```java
@RunWith(Arquillian.class)
public class EmployeeDaoBeanIntegrationTest {

  // ...

  @Resource(lookup = "java:/jdbc/EmployeeDS")
  private DataSource dataSource;

  @Inject
  private EmployeeDaoBean employeeDaoBean;

  @Before
  public void init() throws Exception {
      try (Connection c = dataSource.getConnection();
           PreparedStatement ps = c.prepareStatement("delete from employees")) {
           ps.executeUpdate();           
      }
      employeeDaoBean.saveEmployee(new Employee("John Doe"));
      employeeDaoBean.saveEmployee(new Employee("Jack Doe"));
  }  
}
```

---

## Teszt eset

```java
@RunWith(Arquillian.class)
public class EmployeeDaoBeanIntegrationTest {

  @Inject
  private EmployeeDaoBean employeeDaoBean;

  @Test
  public void testFindEmployees() {
    List<Employee> employees = employeeDaoBean.findEmployees();

    // Assert esetén vigyázzunk a sorrendre: order by
    assertEquals(Arrays.asList("Jack Doe", "John Doe"), employees.stream()
                .map(Employee::getName).collect(Collectors.toList()));
  }
}
```

---

## Teszt eset - inicializáció a given részben

```java
@RunWith(Arquillian.class)
public class EmployeeDaoBeanIntegrationTest {

  @Inject
  private EmployeeDaoBean employeeDaoBean;

  @Test
  public void testFindEmployees() {
    // Given
    employeeDaoBean.saveEmployee(new Employee("John Doe"));
    employeeDaoBean.saveEmployee(new Employee("Jack Doe"));

    // When
    List<Employee> employees = employeeDaoBean.findEmployees();

    // Then
    assertEquals(Arrays.asList("Jack Doe", "John Doe"), employees.stream()
                .map(Employee::getName).collect(Collectors.toList()));
  }
}
```


---

class: inverse, center, middle

# DbUnit

---

## DbUnit

* JUnitra épülő tesztelésre használható library
* Használható
    * Adatbázis iniciakizálására
    * Adatbázis elvárt állapotának ellenőrzésére
* Adatok betöltése különböző forrásokból
    * Adatbázis
    * XML
    * Excel

---

## Adatbázis tartalom leírása XML-ben

```xml
<?xml version="1.0" encoding="UTF-8"?>
<dataset>
    <table name="employees">
        <column>id</column>
        <column>emp_name</column>
        <row>
            <value>1</value>
            <value>John Doe</value>
        </row>
        <row>
            <value>2</value>
            <value>Jack Doe</value>
        </row>
    </table>
</dataset>
```

---

## Adatbázis inicializáció

```java
@Test
public void testFindEmployees() throws Exception {
    DatabaseOperation.CLEAN_INSERT.execute(new DatabaseDataSourceConnection(dataSource),
            new XmlDataSet(
                EmployeeDaoBeanIntegrationTest.class.getResourceAsStream("/employees.xml")));

    List<Employee> employees = employeeDaoBean.findEmployees();
    assertEquals(Arrays.asList("Jack Doe", "John Doe"), employees.stream()
            .map(Employee::getName).collect(Collectors.toList()));
}
```

---

## Adatbázis ellenőrzés - id mező nélkül

```java
@Test
public void testSaveEmployee() throws Exception {
    DatabaseOperation.DELETE_ALL.execute(new DatabaseDataSourceConnection(dataSource),
        new XmlDataSet(
            EmployeeDaoBeanIntegrationTest.class.getResourceAsStream("/employees.xml")));

    employeeDaoBean.saveEmployee(new Employee("John Doe"));
    employeeDaoBean.saveEmployee(new Employee("Jack Doe"));

    ITable expectedTable = DefaultColumnFilter
            .includedColumnsTable((new XmlDataSet(EmployeeDaoBeanIntegrationTest.class
            .getResourceAsStream("/employees.xml"))
            .getTable("employees")), new String[]{"emp_name"});
    ITable databaseTable = DefaultColumnFilter
            .includedColumnsTable(new DatabaseDataSourceConnection(dataSource)
            .createDataSet().getTable("employees"), new String[]{"emp_name"});

    new DbUnitAssert().assertEquals(expectedTable, databaseTable);
}
```

---

## Arquillian

```xml
<dependency>
    <groupId>org.dbunit</groupId>
    <artifactId>dbunit</artifactId>
    <version>2.6.0</version>
    <scope>test</scope>
</dependency>
```

```java
.addAsResource("employees.xml", "employees.xml")
.addAsLibraries(Maven.configureResolver().loadPomFromFile("pom.xml")
  .resolve("org.dbunit:dbunit").withoutTransitivity().asSingleFile())
```

---

class: inverse, center, middle

# JSF integrációs tesztelése Arquilliannal

---

## Java EE API problémák

* Nem tartották szem előtt a tesztelhetőséget

```java
@Model
public class EmployeesController {

    @Inject
    private EmployeeServiceBean employeeServiceBean;

    public String addEmployee() {
        employeeServiceBean.saveEmployee(name);
        FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().put("successMessage", "Employee has created!");
        return "employees.xhtml?faces-redirect=true";
    }

}
```

---

## Problémák


```java
FacesContext.getCurrentInstance().getExternalContext()
        .getFlash().put("successMessage", "Employee has created!");
```

* Statikus metódus: nem mockolható
* Demeter törvénye: "csak a közvetlen barátaiddal beszélgess"
  * Trainwreck: kapcsolódó objektum belső struktúrájának kihasználása
* Nem unit és integrációs tesztelhető

---

## Megoldás

```java
public interface FacesContextProvider {
    void setFlashAttribute(String key, Object value);
}

public class InContainerFacesContextProvider implements FacesContextProvider {

    @Override
    public void setFlashAttribute(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().put(key, value);
    }
}
```

* Mockolható

---

## Tesztelés

```java
public class FakeFacesContextProvider implements FacesContextProvider {

    private Map<String, Object> flashAttribute = new HashMap<>();

    @Override
    public void setFlashAttribute(String key, Object value) {
        flashAttribute.put(key, value);
    }

    public Map<String, Object> getFlashAttribute() {
        return flashAttribute;
    }
}
```

* Mock implementáció pl. Mockito használatával
* Csak ezt az implementációt beletenni a micro deploymentbe
* Csere pl. CDI `@Alternative` annotációval

---

class: inverse, center, middle

# Java EE security Arquilliannal

---

## Probléma

```java
@RolesAllowed("admin")
public Employee saveEmployee(String name) {

}
```

Java EE nem támogatja a programozott bejelentkezést

---

## Megoldás

* Alkalmazásszerver függő megvalósítás programozott bejelentkezésre
* Hívó EJB alkalmazása

```java
@Stateless
@RunAs("admin")
@PermitAll
public class AdminRunnerBean {

    public <V> V call(Callable<V> callable) throws Exception {
        return callable.call();
    }
}
```

---

## EJB használata

```java
@RunWith(Arquillian.class)
public class EmployeeDaoBeanIntegrationTest {

  @Inject
  private AdminRunnerBean adminRunnerBean;

  @Inject
  private EmployeeDaoBean employeeDaoBean;

  public void testFindEmployees() {
    List<Employee> employees = adminRunnerBean.call(employeeDaoBean::listEmployees);

    // assert
  }

}
```

---

class: inverse, center, middle

# Arquillian paraméterezése

---

## Konténer paraméterezése

* Alapértelmezett értékek, de felülbírálható a `src/test/resources/arquillian.xml` fájlban

```xml
<?xml version="1.0"?>
<arquillian>

    <container qualifier="wildfly" default="true">
        <configuration>
            <property name="managementAddress">192.168.40.50</property>
            <!-- Pl. foglalt port esetén -->
            <property name="managementPort">10090</property>
        </configuration>
    </container>

</arquillian>
```

---

## Konténer paraméterezése Mavenből

```xml
<?xml version="1.0"?>
<arquillian>
    <engine>
        <property name="deploymentExportPath">target/deployments</property>
    </engine>

    <container qualifier="wildfly" default="true">
        <configuration>
            <property name="managementAddress">${wildfly.host}</property>
        </configuration>
    </container>

</arquillian>
```

---

## Surefire plugin - pom.xml

```xml
<properties>
  <wildfly.host>localhost</wildfly.host>
</properties>

<build>
  <plugins>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <configuration>
            <systemPropertyVariables>
                <wildfly.host>${wildfly.host}</wildfly.host>
            </systemPropertyVariables>
        </configuration>
    </plugin>
  </plugins>
</build>
```

`mvn test -Dwildfly.host=192.168.40.50`

---

class: inverse, center, middle

# Webszolgáltatás tesztelés

---

## SoapUI

---

## Postman

---

## JMeter

* Teljesítmény/terhelésteszt, stresszteszt
* Különböző protokollok
* Javaban implementált
* Kiterjeszthető
* Swing felület
* Elosztott tesztelés
* Felvétel

---

## Selenium

---

## SonarQube

* SonarLint

---

## CI

* Jenkins
