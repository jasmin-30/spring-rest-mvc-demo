package com.example.springrestmvcdemo.repositories;

import com.example.springrestmvcdemo.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
