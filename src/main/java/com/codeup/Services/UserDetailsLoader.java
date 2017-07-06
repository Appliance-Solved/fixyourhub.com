//package com.codeup.Services;
//
//import com.codeup.Models.User;
//import com.codeup.Models.UserWithRoles;
//import com.codeup.Repositories.UserRepository;
//import com.codeup.Repositories.UserRolesRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * Created by larryg on 7/6/17.
// */
//@Service("customUserDetailsService")
//public class UserDetailsLoader implements UserDetailsService {
//    private final UserRepository users;
//    private final UserRolesRepository roles;
//
//    @Autowired
//    public UserDetailsLoader(UserRepository users, UserRolesRepository roles) {
//        this.users = users;
//        this.roles = roles;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = users.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("No user found for " + username);
//        }
//
//        List<String> userRoles = roles.ofUserWith(username);
//        return new UserWithRoles(user, userRoles);
//    }
//}