package com.timcast.flurbovurbo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timcast.flurbovurbo.model.Vurbo;

public interface VurboRepository extends JpaRepository<Vurbo, Integer> {

	List<Vurbo> findByFlurboId(int flurboId);

}
