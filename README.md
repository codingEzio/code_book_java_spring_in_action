
### What *Cassandra* differs from the relational database

- Columns: *yes*, Rows: *not necessarily*
    > We got data types prepared, but the data is *optional*
- Databases are splited across multiple partitions instead of one single place
- Data duplication are common in order to optimize for *reading*
    > Denormalization is the new *normal* (duplication across multiple tables)

### Switch to NoSQL - Cassandra

1. `pom.xml`
    - Add dependency

        ```xml
        <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-data-cassandra</artifactId>
        </dependency>
        ```

    - Remove old DB-related dependency

        ```xml
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
            <artifactId>spring-boot-starter-data-cassandra</artifactId>
        </dependency>
        ```

2. Get *Docker* on your machine first (whether by GUI or package manager)
3. Get *Cassandra*

    ```bash
    docker network create cassandra-taco
    docker network ls

    # Build a container based on the image 'cassandra' (latest version)
    # and name it 'cassandra-taco', run it at background (shell detached)
    # yet exposing and mapping the ports to the host (macOS). Also, we'd
    # use the network we just created.
    docker run --name    cassandra-taco \
               --network cassandra-taco \
               --publish 9042:9042      \
               --detach                 \
               cassandra:latest
   
    docker container ls           # list of containers and their status
    docker container stop CONT_ID # if you wanna re-create one
    ```

4. Configure *Cassandra*

    ```bash
    # Run a container based the image 'cassandra' we downloaded (which is
    # also the latest version, but we don't need the :latest this time),
    # and connect to the cassandra server we've runned (detached to the
    # background) that is in the same network named 'cassandra-taco'.
    # Also, this time we run this container in the '-it' way, the way
    # we could interactive with it (plus the 'cqlsh', we're directly
    # being put in the Cassandra shell). Last thing, the '--rm' means
    # this container would be automatically removed once we exited it,
    # if you want an another try-out of cqlsh, just create another one.
    docker run --interactive --tty      \
               --network cassandra-taco \
               --rm                     \
               cassandra                \
               cqlsh cassandra-taco     
    ```

    ```bash
    # These commands were executed in the 'cqlsh' shell
    # Make sure you combine this into one single line before copy-n-paste
    CREATE KEYSPACE tacocloud
        WITH replication = {'class': 'SimpleStrategy', 'replication_factor: 1}
        AND durable_writes = true;
    ```

5. Configure *Spring*

    ```bash
    spring.data.cassandra.keyspace-name=taco_cloud
    spring.data.cassandra.schema-action=recreate
    spring.data.cassandra.local-datacenter=datacenter1
    ```

### Switch to JDBC that powered by *Spring Data*

1. `pom.xml`

    ```xml
    <!-- Change to this -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jdbc</artifactId>
    </dependency>
    ```

2. Changing *Object*`Repository`

    ```java
    // Old :: an interface with methods to implement
    Iterable<Ingredient> findAll();
    Optional<Ingredient> findById(String id);
    Ingredient save(Ingredient ingredient);


    // New :: 'extends' alone suffices, removed the methods'
    ... extends CrudRepository<Ingredient, String> { .. }
    ```

3. Adding annotations to the objects for Spring Data
    > Besides `id`, the other objects were ***implicitly*** being mapped
    - `TacoOrder.java`
    - `Ingredient.java`
    - `Taco.java`

### Writing Database Operations for `Orders`

- Files to add
  1. ```OrderRepository```: Interface for database operations
  2. ```IngredientRef```: Link `Taco` and `Ingredient` together
  3. ```OrderRepositoryJdbc```: Implementation for the `Order` interface

### How Would You Save an `TacoOrder`?

1. It seems like you only need a `save()`, actually you need
2. to save `Taco`
3. to save the relationship between `Taco` and `Ingredient` (ref)

### If Your App couldn't Run (till page 73)

- It's because the author did not state the changes explicitly

- Core changes to make ([`Ingredient.java`](https://github.com/habuma/spring-in-action-6-samples/blob/33acc6497f9a98a3ba8dfe980fd00b87ce0dee5b/ch03/tacos-jdbctemplate/src/main/java/tacos/Ingredient.java#L9) and [`DesignTacoController.java`](https://github.com/habuma/spring-in-action-6-samples/blob/33acc6497f9a98a3ba8dfe980fd00b87ce0dee5b/ch03/tacos-jdbctemplate/src/main/java/tacos/web/DesignTacoController.java#L78))

```java
// Ingredient.java
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)


// DesignTacoController.java
return StreamSupport.stream(ingredients.spliterator(), false)
        .filter(i -> i.getType().equals(type))
        .collect(Collectors.toList());
```

### Disable Template Caching

- Add these to `application.properties`

```bash
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=file:src/main/resources/templates/
```

### If Your Page Is Quite Simple

- Before: *one route* -> *one controller file* plus the template file

```java
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
```

- Now: *routes* <-> *lines* in *one file*

```java
public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("home");
    registry.addViewController("/about").setViewName("aboutus");
}
```

### Apply Validation in Spring MVC

1. Add the Spring Validation starter to the `pom.xml`
2. Declare rules on the class that is to be validated (`Taco`, `TacoOrder`)
3. Specify the controller that require validation (`processTaco`, `processOrder`)
4. Modify the views to display validation errors

### About How Do Spring Find the Templates

- The [returned string will be used to resolve](https://stackoverflow.com/a/54619725/6273859) the template file

  ```java
  public class OrderController {
      @GetMapping("/current")
      public String orderForm() {
          return "orderForm"; // => orderForm.html
      } 
  } 
  ```

### About Working with *Checkboxes* in Templates

- Same story like Django, but different dialects

  ```html
  <!-- Purely for CSS selector to do the styling -->
  <div class="ingredient-group" id="proteins">

    <h3>Pick your protein:</h3>

    <!-- 
      Grab all ingredients of a specific type (Type.PROTEIN),
      then do iterations on it (i.e. options for the checkbox)
    -->
    <div th:each="ingredient: ${protein}">

      <!-- 
        *{}
          protein -> ingredients

        ${}
          protein -> ingredients .id
          protein -> ingredients .name
       -->
      <input
        type="checkbox"
        th:field="*{ingredients}"
        th:value="${ingredient.id}"
      />
      <span th:text="${ingredient.name}">INGREDIENT</span> <br />
      
    </div>

  </div>
  ```

### About the *View*

- For the view libraries like *Thymeleaf*
  - They are decoupled from particular frameworks
  - They are not aware with Spring's model abstraction
  - They can not work with the data places in the `Model`
  - They **could** work with *servlet request attributes*
  - They copy the data (from `Model`) into *request attributes*
  - They read the data in the *request attributes* instead of the `Model`

### About the *Model*

> Don't over think it.

- It's *an object*
- It [**supplies** data to the *View*](https://www.baeldung.com/spring-mvc-model-model-map-model-view#model).
- It **ferries data** between *controller*s and *view*s
- It would be copied into the servlet request attributes
  > then the view could find them and use it to render a page
- It doesn't equal to the database interaction.
- There's still a gap between the *Model* (conceptually ) and the *actual database operation code*
  - If you write SQL or ORM code, database
  - If you are not, then it's just model/view code

### About the *`DesignTacoController`*

- The root class annotations
  - `Slf4j`: Logging
  - `Controller`: 自`View`接請求,自處理,轉`Model`
  - `RequestMapping`: Specify [an *parent* mapping](https://stackoverflow.com/q/22702568/6273859) for the potential *mappings* inside the class
  - `SessionAttribute`: #TODO 透徹理解再添
- What to return when accessing */design*
- Template variables: `taco()`, `order()`
- Template methods: `addIngredientsToModel()`
- Private utility methods: `filterByType()`

### Design the Domain

> Just one more step to the database design

- Gotta have the base `Taco`
  - `name`
  - `Ingredient`
    - `id`
    - `name`
    - `type`
- `Type` of the `Ingredient`
  - `WRAP`
  - `PROTEIN`
  - `CHEESE`
  - `VEGGIES`
  - `SAUCE`
- Purchasing Info `TacoOrder`
  - `deliveryName`
  - `deliveryStreet`
  - `deliveryCity`
  - `deliveryState`
  - `deliveryZip`
  - `ccNumber`
  - `ccExpiration`
  - `ccCVV`

### About *Lombok*

> Reduce boilerplate code [like](https://github.com/rayanht/paprika) [dataclasses in Python](https://docs.python.org/3/library/dataclasses.html#module-contents)

- It generates methods like *getters*, *setters*, `toString()`, `equals()` etc.
- Add it to your project
  > Install the plugin, whether it's [VS Code](https://marketplace.visualstudio.com/items?itemName=GabrielBB.vscode-lombok) or [IDEA CE](https://plugins.jetbrains.com/plugin/6317-lombok)

  ```xml
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
  </dependency>
  ```

### Most Used Commands

> e.g. command `mvn`, script `.mvnw`

- Edit and Run
  > Assume you are already at the root directory of your project

  ```bash
  # package and run
  ./mvnw package
  java -jar target/your-project-0.0.1-SNAPSHOT.jar

  # run
  ./mvnw spring-boot:run
  ```

- Test

  ```bash
  ./mvnw test
  ```

- Clean Up the Built Files

  ```bash
  mvn clean
  ```

### About `@SpringBootApplication`

| **Annotation** | **Description** |
| :-- | :-- |
| `@EnableAutoConfiguration` | [*Guess*](https://stackoverflow.com/a/35006877/6273859) to [configure based the `.jars` you have](https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/using-boot-auto-configuration.html#using-boot-auto-configuration) |
| `@ComponentScan` | [Where to find](https://www.baeldung.com/spring-bean-annotations#scanning) the [@Component](https://www.baeldung.com/spring-component-annotation#component) *Objects* (find and register) |
| `@Configuration` | Designate this class a **configuration** class <small>(like `manage.py` in Django)</small> |

-----

### My Background

- Knowledge-wise
  - I do not know much about *Java*
  - I am not familiar with the *Java* ecosystem
  - I am not familiar with the *Spring* ecosystem
  - I have never learned anything about *Spring* before

- Ranting
  - It's <del>so damn</del> hard to find people who speak *'English'*
  - All the documentation and tutorials ***assume*** you already knew *something*
  - Unable to transfer the experience I got from other languages

- Thoughts & Summaries
  - Core logic of *Spring* is doing magic behind the scenes
  - It is like a *frameworkless* framework
  - It does not require you write lots of framework-specific code

-----

### Analyzing the Project Structure

| **File** | **Description** |
| --: | :-- |
|  `mvnw`, `mvnw.cmd` | Scripts to build your *Spring* project, *Maven* is not required |
| `pom.xml` | Managing dependencies, like [`requirements.txt` in *Python*](https://stackoverflow.com/a/62419297/6273859) |
| `TacoCouldApplication.java` | Main class that [bootstrapp](https://stackoverflow.com/a/1255796/6273859) the Spring Boot project |
| `application.properties` | Configuration [like database](https://github.com/search?q=application.properties&type=repositories) etc. |
| `static/` | Place content like images, CSSes, scripts etc. |
| `template/` | [Template engine](https://www.baeldung.com/spring-template-engines) files being rendered to the browsers |

### Dependencies in `pom.xml`

| **Name** | **Description** |
| :-- | :-- |
| `spring-boot-starter-parent` | [Manage dependencies and plugins](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-parent)  |
| `spring-boot-devtools` | Ease the dev process by [features like Live Reload etc.](https://www.baeldung.com/spring-boot-devtools) |
| `spring-boot-starter-web` | Build web apps using [SpringMVC and Tomcat etc.](https://www.baeldung.com/spring-boot-starters#Starter) |
| `spring-boot-starter-thymeleaf` | Provide template rendering capability |
| `spring-boot-starter-test` | Provide testing capability |

### `s-b-starter-x`

> Think of `...-starter-web` as `...-List-of-Deps-that-were-Related-to-the-Web`

- Or
  - Smaller and cleaner build file (`pom.xml`)
  - Declaring *capabilities* instead of *library names*
  - No worry about library versions (⬅︎ `spring-boot-starter-parent`)

<!-- https://github.com/DavidAnson/markdownlint/tree/v0.25.1#configuration -->
<!-- markdownlint-configure-file
{
  "first-line-h1": false,
  "hr-style": {
    "style": "-----"
  },
  "no-inline-html":  {
    "allowed_elements": [
      "small",
      "del"
    ]
  }
}
-->
