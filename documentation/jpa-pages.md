# Jpa pages

Let's consider a very common use case to get paginated information from a JPA Spring Repository. In `JpaBeersRepository` we have this method: 

```java
Page<BeerEntity> findAll(Pageable pagination);
```

We can call that this way: 

```java
private final JpaBeersRepository beers;

// ...

public Jhlite110Page<Beer> list(Jhlite110Pageable pagination) {
  return Jhlite110Pages.from(
      beers.findAll(Jhlite110Pages.from(pagination, Sort.by(Sort.Direction.ASC, "name"))),
      BeerEntity::toDomain
    );
}
```

And we'll need a mapping method in `BeerEntity`: 

```java
Beer toDomain() {
  // ...
}
```