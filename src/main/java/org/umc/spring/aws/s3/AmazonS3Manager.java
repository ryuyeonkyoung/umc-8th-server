package org.umc.spring.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.umc.spring.config.AmazonConfig;
import org.umc.spring.domain.Uuid;
import org.umc.spring.repository.UuidRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager {

  private final AmazonS3 amazonS3;

  private final AmazonConfig amazonConfig;

  private final UuidRepository uuidRepository;

  public String uploadFile(String keyName, MultipartFile file) {
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(file.getSize());
    try {
      amazonS3.putObject(
          new PutObjectRequest(amazonConfig.getBucket(), keyName, file.getInputStream(), metadata));
    } catch (IOException e) {
      log.error("Error uploading file to S3: {}", e.getMessage(), e);
      throw new RuntimeException("Failed to upload file to S3", e);
    }

    return amazonS3.getUrl(amazonConfig.getBucket(), keyName).toString();
  }

  public String generateReviewKeyName(Uuid uuid) {
    return amazonConfig.getReviewPath() + '/' + uuid.getUuid();
  }

}