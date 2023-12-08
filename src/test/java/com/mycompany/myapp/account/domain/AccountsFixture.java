package com.mycompany.myapp.account.domain;

import static com.mycompany.myapp.shared.useridentity.domain.UsersIdentitiesFixture.*;

import com.mycompany.myapp.shared.authentication.domain.Role;
import java.util.List;

public final class AccountsFixture {

  private AccountsFixture() {}

  public static Account account() {
    return Account
      .builder()
      .username(username())
      .firstname(firstname())
      .lastname(lastname())
      .email(email())
      .roles(List.of(Role.ADMIN.key()))
      .build();
  }
}
