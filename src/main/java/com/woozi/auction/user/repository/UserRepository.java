package com.woozi.auction.user.repository;

import com.woozi.auction.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
