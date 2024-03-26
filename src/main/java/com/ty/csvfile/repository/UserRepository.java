package com.ty.csvfile.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.ty.csvfile.entity.Users;

public interface UserRepository  extends JpaRepositoryImplementation<Users, Long>{

}
