# Rest pagination

Let's consider a very common use case to get paginated information. In `BeersApplicationService` we have:

```java
public Jhlite110Page<Beer> list(Jhlite110Pageable pagination) {
  // ...
}
```

To call that and expose a result using a rest service we can do something like that: 

```java
private final BeersApplicationService beers;

// ...

ResponseEntity<RestJhlite110Page<RestBeer>> list(@Validated RestJhlite110Pageable pagination) {
  return ResponseEntity.ok(RestJhlite110Page.from(beers.list(pagination.toPageable()), RestBeer::from))
}
```

And we'll need a mapping method in `RestBeer`: 

```java
static RestBeer from(Beer beer) {
  // ...
}
```