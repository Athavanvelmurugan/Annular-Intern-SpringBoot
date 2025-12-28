package io.annular.File.Upload.repository;

import io.annular.File.Upload.Entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository <ImageData , Long> {

  Optional<ImageData> findByName(String fileName);
}
