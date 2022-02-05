package com.github.jakutenshi.examples.grpc;

import com.github.jakutenshi.examples.grpc.RegistrationServiceGrpc.RegistrationServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Client {
    public static void main(String[] args) {
        int port = 50051;

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext().build();
        RegistrationServiceBlockingStub stub = RegistrationServiceGrpc.newBlockingStub(channel);
        User data = User.newBuilder()
                .setLastname("Ivanov")
                .setFirstname("Petr")
                .setMiddlename("Sidorovich")
                .setAge(23)
                .setGender(User.Gender.MALE)
                .build();
        RegistrationResult result = stub.register(data);
        System.out.println("Success is " + result.getSucceeded());
    }
}
