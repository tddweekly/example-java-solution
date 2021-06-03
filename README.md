# Example tddweekly.com Java solution

Join tddweekly.com Java premium to get a similar solution each week.

Each e-mail contains

* Everything from the task e-mail
* Link to the code repository with working solution
* More code explanations, tips & tricks
* Additional reference materials

## Task e-mail

[Task repo](https://github.com/tddweekly/example-java)

## Code repository

This example repo :)

Solution repositories are NOT removed!

## Further code overview

* As we have fixed, well-known _Support Plan_ values there is no DB, just `InMemory` repo
* It's still a separate class (single responsibility) to make further changes easier (e.g. using an actual DB if needed)
* `organizaton` package could be a dedicated, self-contained library, shared within the company
* The only `public` classes are the ones which have to be shared. `support` doesn't need to expose anything from the package
* If the app grows (new packages), `support` should expose just its `SupportService` as the battle-tested entry point

BTW feel free to use the actual app, e.g.

```
curl --location --request GET 'localhost:8080/plans' \
--header 'X-Organization-Tier: PREMIUM_PLUS'
```

## Tips & tricks

* See how `organization` uses `@RequestScope` to make current _Organization_ available just for the request
* You cannot have
  ```java
  @ParameterizedTest(name = "{0}")
  @NullAndEmptySource
  ```
  it gives
  ```java
  PreconditionViolationException: displayName must not be null or blank
  ```
* By default microservice sets a safe `FREE` _Tier_ value. Alternatively, `OrganizationFilter` could early return
  with `400` HTTP status
* Java 16 introduced `toList()` `Stream` method to handle the most common collecting

## Additional materials

* [art] [Quick Guide to Spring Bean Scopes](https://www.baeldung.com/spring-bean-scopes) - read more about `@RequestScope`
* [art] [JUnit Parameterized Tests](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests) - app already uses `@NullAndEmptySource`, `@ValueSource`, `@EnumSource`, `@MethodSource`
* [art] [Service Layer](https://martinfowler.com/eaaCatalog/serviceLayer.html) - service class as the contract, set of available operations
* [51:49] [JDD 2017: Keep IT clean: mid-sized building blocks and hexagonal architecture (Jakub Nabrdalik)](https://www.youtube.com/watch?v=KrLFs6f2bOA) - how to structure the code. This app was designed like that 
