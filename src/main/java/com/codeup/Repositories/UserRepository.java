package com.codeup.Repositories;

import com.codeup.Models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Carlos on 7/5/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    public User findByUsername(String username);

    @Transactional
    @Modifying
    @Query("update User u set u.address=?1, u.city=?2, u.state=?3, u.zipcode=?4, u.phone=?5 where u.id = ?6")
    public void updateUserProfile(String address, String city, String state, Long zipcode, String phone, Long id);



}
