package de.benedikt_schwering.thicnd.adapters.in.grpc;

import de.benedikt_schwering.thicnd.stubs.RecipeServiceGrpc;
import de.benedikt_schwering.thicnd.stubs.TestRequest;
import de.benedikt_schwering.thicnd.stubs.TestResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class RecipeGrpcController extends RecipeServiceGrpc.RecipeServiceImplBase {
    @Override
    public void test(TestRequest request, StreamObserver<TestResponse> responseStreamObserver) {
        System.out.println(request.getName());

        responseStreamObserver.onNext(TestResponse.newBuilder().setMessage("Joo").build());
        responseStreamObserver.onCompleted();
    }
}
