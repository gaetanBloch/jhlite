package com.mycompany.myapp.shared.kipe.application;

import com.mycompany.myapp.shared.authentication.application.AuthenticatedUser;
import com.mycompany.myapp.shared.authentication.domain.Role;
import com.mycompany.myapp.shared.authentication.domain.Roles;
import com.mycompany.myapp.shared.authentication.domain.Username;
import com.mycompany.myapp.shared.kipe.domain.Action;
import com.mycompany.myapp.shared.kipe.domain.Resource;
import com.mycompany.myapp.shared.kipe.domain.RolesAccesses;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class Jhlite110Authorizations {

  private final RolesAccesses accesses;

  public Jhlite110Authorizations(Collection<RolesAccesses> accesses) {
    this.accesses = accesses.stream().reduce(RolesAccesses.EMPTY, RolesAccesses::merge);
  }

  public boolean allAuthorized(Authentication authentication, String action, Resource resource) {
    if (missingAuthenticationInfo(authentication, action, resource)) {
      return false;
    }

    return accesses.allAuthorized(roles(authentication), action(action), resource);
  }

  public Username getUsername(Authentication authentication) {
    return new Username(AuthenticatedUser.readPrincipal(authentication));
  }

  public boolean specificAuthorized(Authentication authentication, String action, Resource resource) {
    if (missingAuthenticationInfo(authentication, action, resource)) {
      return false;
    }

    return accesses.specificAuthorized(roles(authentication), action(action), resource);
  }

  private Roles roles(Authentication authentication) {
    Set<Role> role = authentication
      .getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .map(Role::from)
      .collect(Collectors.toSet());

    return new Roles(role);
  }

  private Action action(String action) {
    return new Action(action);
  }

  private boolean missingAuthenticationInfo(Authentication authentication, String action, Resource resource) {
    return authentication == null || StringUtils.isBlank(action) || resource == null;
  }
}
