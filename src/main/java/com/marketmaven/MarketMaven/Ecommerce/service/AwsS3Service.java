package com.marketmaven.MarketMaven.Ecommerce.service;

//This service handles image upload functionality to AWS S3.
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
public class AwsS3Service {
    private final String bucketName = "market-maven";
    @Value("${aws.s3.access}")// Injects AWS access key from properties
    private String awsS3AccessKey;

    @Value("${aws.s3.secrete}")// Injects AWS secret key from properties
    private String awsS3SecreteKey;

    //Method to upload image to the bucket
    public String saveImageToS3(MultipartFile photo)//Takes a MultipartFile (uploaded image) as input
    {
    try {
            String s3FileName = photo.getOriginalFilename();//Gets original filename from uploaded file
            //create aws cred object using access and secrete key
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsS3AccessKey, awsS3SecreteKey);

            //create an s3 client with configured credintials and region
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.US_EAST_2)
                    .build();

            //get input stream from photo
            InputStream photoStream = photo.getInputStream();//Gets input stream from uploaded file


            //set metadata for the object
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("image/jpeg");//Sets content type for metadata of the image

            //Creates upload request with bucket name, file name, stream, and metadata
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3FileName, photoStream, objectMetadata);
            s3Client.putObject(putObjectRequest);//Executes upload to S3

            return "https://" + bucketName + ".s3.us-east-2.amazonaws.com/"+ s3FileName;
            //Constructs and returns public String URL for uploaded image
    } catch (IOException e) {
            e.printStackTrace();
            throw  new RuntimeException("Unable to upload image to S3 Bucket :" + e.getMessage());
        }
        //Handles IO exceptions during upload process

    }
}
