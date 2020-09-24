package org.ejb.sample.servlet.helper;

import java.io.FileWriter;
import java.io.IOException;
import com.amazonaws.auth.*;
import com.amazonaws.services.s3.*;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class FileUtility {

    void generateReportFile(String content) throws IOException {
        FileWriter fileWriter = new FileWriter("~/DailyReports/Report.csv");
        fileWriter.write(content);
        fileWriter.close();
    }

    public URL uploadS3ObjectAndGetUrl(String bucketName, String uploadContentPath, String keyName) {
        AmazonS3 s3client = getS3Client();
        s3client.putObject(new PutObjectRequest(bucketName, keyName, new File(uploadContentPath)));
        return s3client.getUrl(bucketName, keyName);
    }

    public AmazonS3 getS3Client() {
        AmazonS3 s3client;
        AWSCredentials credentials = new BasicAWSCredentials(System.getenv("AWS_S3_ACCESS_KEY"), System.getenv("AWS_S3_SECRET_KEY"));
        s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(System.getenv("AWS_S3_CLIENT_REGION")).build();
        return s3client;
    }
}
