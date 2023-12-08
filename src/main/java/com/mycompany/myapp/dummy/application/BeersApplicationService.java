package com.mycompany.myapp.dummy.application;

import com.mycompany.myapp.dummy.domain.BeerId;
import com.mycompany.myapp.dummy.domain.beer.Beer;
import com.mycompany.myapp.dummy.domain.beer.BeerToCreate;
import com.mycompany.myapp.dummy.domain.beer.Beers;
import com.mycompany.myapp.dummy.domain.beer.BeersCreator;
import com.mycompany.myapp.dummy.domain.beer.BeersRemover;
import com.mycompany.myapp.dummy.domain.beer.BeersRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BeersApplicationService {

  private final BeersRepository beers;
  private final BeersCreator creator;
  private final BeersRemover remover;

  public BeersApplicationService(BeersRepository beers) {
    this.beers = beers;

    creator = new BeersCreator(beers);
    remover = new BeersRemover(beers);
  }

  @Transactional
  @PreAuthorize("can('create', #beerToCreate)")
  public Beer create(BeerToCreate beerToCreate) {
    return creator.create(beerToCreate);
  }

  @Transactional
  @PreAuthorize("can('remove', #beer)")
  public void remove(BeerId beer) {
    remover.remove(beer);
  }

  @Transactional(readOnly = true)
  public Beers catalog() {
    return beers.catalog();
  }
}
