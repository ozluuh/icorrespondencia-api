package br.com.icorrespondencia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.icorrespondencia.api.domain.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
