package com.github.jakutenshi.examples.grpc;

import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class Server {
    private static class RegistrationService extends RegistrationServiceGrpc.RegistrationServiceImplBase {
        @Override
        public void register(User request, StreamObserver<RegistrationResult> responseObserver) {
            System.out.printf("Registering user %s %s %s age: %d gender: %s\n",
                request.getLastname(), request.getFirstname(), request.getMiddlename(),
                request.getAge(), request.getGender().name());
            responseObserver.onNext(RegistrationResult.newBuilder().setSucceeded(true).build());
            responseObserver.onCompleted();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50051;
        io.grpc.Server server = ServerBuilder
                .forPort(port)
                .addService(new RegistrationService())
                .build();
        server.start();
        Runtime.getRuntime().addShutdownHook( new Thread(() -> {
            System.out.println("Server shutdowning");
            server.shutdown();
        }));
        server.awaitTermination();
    }
}
