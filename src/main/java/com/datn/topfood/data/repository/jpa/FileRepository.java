package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
