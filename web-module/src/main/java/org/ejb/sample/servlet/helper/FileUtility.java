package org.ejb.sample.servlet.helper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;

public class FileUtility {

    void generateReportFile(String content) throws IOException {
        FileWriter fileWriter = new FileWriter("~/DailyReports/Report.csv");
        fileWriter.write(content);
        fileWriter.close();
    }

    public void generateReportFile_Azure(String content) {
        String storageConnectionString = System.getenv("AZURE_BLOB_CONNECTION_STRING");
        File sourceFile = null;
        CloudStorageAccount storageAccount;
        CloudBlobClient blobClient = null;
        CloudBlobContainer container = null;
        try {
            // Parse the connection string and create a blob client to interact with Blob storage
            storageAccount = CloudStorageAccount.parse(storageConnectionString);
            blobClient = storageAccount.createCloudBlobClient();
            container = blobClient.getContainerReference("quickstartcontainer");
            // Create the container if it does not exist with public access.
            System.out.println("Creating container: " + container.getName());
            container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
            // Creating file from content
            sourceFile = File.createTempFile("sampleFile", ".txt");
            System.out.println("Creating a sample file at: " + sourceFile.toString());
            Writer output = new BufferedWriter(new FileWriter(sourceFile));
            output.write(content);
            output.close();
            // Getting a blob reference
            CloudBlockBlob blob = container.getBlockBlobReference(sourceFile.getName());
            // Creating blob and uploading file to it
            blob.uploadFromFile(sourceFile.getAbsolutePath());
        } catch (StorageException ex) {
            System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s", ex.getHttpStatusCode(), ex.getErrorCode()));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
