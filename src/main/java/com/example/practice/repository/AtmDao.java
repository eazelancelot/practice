package com.example.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.practice.entity.Atm;

@Repository
public interface AtmDao extends JpaRepository<Atm, String>{

}
