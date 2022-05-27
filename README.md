
### Apply Validation in Spring MVC

1. Add the Spring Validation starter to the `pom.xml`
2. Declare rules on the class that is to be validated (`Taco`)
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