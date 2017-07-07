package com.codeup.Services;

import com.codeup.Models.User;
import com.codeup.Models.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

/**
 * Created by larryg on 7/6/17.
 */

    public class UserWithRoles extends User implements UserDetails {
        private UserRole userRoles;

        public UserWithRoles(User user, UserRole userRoles) {
            super(user);
            this.userRoles = userRoles;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(userRoles.getRole());
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

