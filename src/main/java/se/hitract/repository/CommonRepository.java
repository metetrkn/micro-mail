package se.hitract.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import se.hitract.model.CommonEntity;

@NoRepositoryBean
public interface CommonRepository<E extends CommonEntity> extends JpaRepository<E, Long>, JpaSpecificationExecutor<E> {

    List<E> findByCreatedAfterAndCreatedBefore(Date startDate, Date fromDate);
}
