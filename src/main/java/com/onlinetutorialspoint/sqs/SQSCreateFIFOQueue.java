package com.onlinetutorialspoint.sqs;

import java.util.Map;
import java.util.HashMap;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

public class SQSCreateFIFOQueue {
    public static void main(String[] args) {
        SqsClient sqsClient = SqsClient.builder()
                .region(Region.US_WEST_2)
                .build();

        String fifoQueueUrl= createFIFOQueue(sqsClient, "My-FIFO-Queue.fifo");
        System.out.println("fifoQueueUrl : "+fifoQueueUrl);
        sqsClient.close();
    }

    public static String createFIFOQueue(SqsClient sqsClient,String queueName) {
        Map<QueueAttributeName, String> queueAttributes = new HashMap<>();
        queueAttributes.put(QueueAttributeName.FIFO_QUEUE, "true");
        try {
            CreateQueueRequest cqr = CreateQueueRequest.builder()
                    .queueName(queueName)
                    .attributes(queueAttributes)
                    .build();

            sqsClient.createQueue(cqr);

            GetQueueUrlResponse getQueueUrlResponse =
                    sqsClient.getQueueUrl(GetQueueUrlRequest.builder().queueName(queueName).build());
            return getQueueUrlResponse.queueUrl();
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return "";
    }
}
