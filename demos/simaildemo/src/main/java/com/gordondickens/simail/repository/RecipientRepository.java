package com.gordondickens.simail.repository;

import com.gordondickens.simail.domain.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipientRepository extends JpaSpecificationExecutor<Recipient>, JpaRepository<Recipient, Long> {
}
