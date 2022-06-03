package com.example.violetserver.record.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaViewTimeRepository extends JpaRepository<ViewTime, ViewTimeRepository>, ViewTimeRepository  {
}
