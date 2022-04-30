
### My Background

- I do not know much about *Java*
- I am not familiar with the *Java* ecosystem
- I am not familiar with the *Spring* ecosystem
- I have never learned anything about *Spring* before

### Analyzing the Project Structure

| **File** | **Description** |
| --: | :-- |
|  `mvnw`, `mvnw.cmd` | Scripts to build your *Spring* project, *Maven* is not required |
| `pom.xml` | Managing dependencies, like [`requirements.txt` in *Python*](https://stackoverflow.com/a/62419297/6273859) |
| `TacoCouldApplication.java` | Main class that [bootstrapp](https://stackoverflow.com/a/1255796/6273859) the Spring Boot project |
| `application.properties` | Configuration [like database](https://github.com/search?q=application.properties&type=repositories) etc. |
| `static/` | Place content like images, CSSes, scripts etc. |
| `template/` | [Template engine](https://www.baeldung.com/spring-template-engines) files being rendered to the browsers |

-----

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
