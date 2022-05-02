
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

### *Testing* the Project

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