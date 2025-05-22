package com.studyhub.repository;

import com.studyhub.model.UserHelp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHelpRepository extends JpaRepository<UserHelp, Integer> {

}
