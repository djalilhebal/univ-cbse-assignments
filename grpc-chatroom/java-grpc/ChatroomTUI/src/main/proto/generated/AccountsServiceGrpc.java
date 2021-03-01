import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.35.0)",
    comments = "Source: accounts_service.proto")
public final class AccountsServiceGrpc {

  private AccountsServiceGrpc() {}

  public static final String SERVICE_NAME = "AccountsService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<AccountsServiceOuterClass.AccountRequest,
      AccountsServiceOuterClass.Account> getGetAccountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAccount",
      requestType = AccountsServiceOuterClass.AccountRequest.class,
      responseType = AccountsServiceOuterClass.Account.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<AccountsServiceOuterClass.AccountRequest,
      AccountsServiceOuterClass.Account> getGetAccountMethod() {
    io.grpc.MethodDescriptor<AccountsServiceOuterClass.AccountRequest, AccountsServiceOuterClass.Account> getGetAccountMethod;
    if ((getGetAccountMethod = AccountsServiceGrpc.getGetAccountMethod) == null) {
      synchronized (AccountsServiceGrpc.class) {
        if ((getGetAccountMethod = AccountsServiceGrpc.getGetAccountMethod) == null) {
          AccountsServiceGrpc.getGetAccountMethod = getGetAccountMethod =
              io.grpc.MethodDescriptor.<AccountsServiceOuterClass.AccountRequest, AccountsServiceOuterClass.Account>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AccountsServiceOuterClass.AccountRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AccountsServiceOuterClass.Account.getDefaultInstance()))
              .setSchemaDescriptor(new AccountsServiceMethodDescriptorSupplier("GetAccount"))
              .build();
        }
      }
    }
    return getGetAccountMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AccountsServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccountsServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccountsServiceStub>() {
        @java.lang.Override
        public AccountsServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccountsServiceStub(channel, callOptions);
        }
      };
    return AccountsServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AccountsServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccountsServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccountsServiceBlockingStub>() {
        @java.lang.Override
        public AccountsServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccountsServiceBlockingStub(channel, callOptions);
        }
      };
    return AccountsServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AccountsServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccountsServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccountsServiceFutureStub>() {
        @java.lang.Override
        public AccountsServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccountsServiceFutureStub(channel, callOptions);
        }
      };
    return AccountsServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class AccountsServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getAccount(AccountsServiceOuterClass.AccountRequest request,
        io.grpc.stub.StreamObserver<AccountsServiceOuterClass.Account> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAccountMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetAccountMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                AccountsServiceOuterClass.AccountRequest,
                AccountsServiceOuterClass.Account>(
                  this, METHODID_GET_ACCOUNT)))
          .build();
    }
  }

  /**
   */
  public static final class AccountsServiceStub extends io.grpc.stub.AbstractAsyncStub<AccountsServiceStub> {
    private AccountsServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccountsServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccountsServiceStub(channel, callOptions);
    }

    /**
     */
    public void getAccount(AccountsServiceOuterClass.AccountRequest request,
        io.grpc.stub.StreamObserver<AccountsServiceOuterClass.Account> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAccountMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AccountsServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<AccountsServiceBlockingStub> {
    private AccountsServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccountsServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccountsServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public AccountsServiceOuterClass.Account getAccount(AccountsServiceOuterClass.AccountRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAccountMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AccountsServiceFutureStub extends io.grpc.stub.AbstractFutureStub<AccountsServiceFutureStub> {
    private AccountsServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccountsServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccountsServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<AccountsServiceOuterClass.Account> getAccount(
        AccountsServiceOuterClass.AccountRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAccountMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ACCOUNT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AccountsServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AccountsServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_ACCOUNT:
          serviceImpl.getAccount((AccountsServiceOuterClass.AccountRequest) request,
              (io.grpc.stub.StreamObserver<AccountsServiceOuterClass.Account>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class AccountsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AccountsServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return AccountsServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AccountsService");
    }
  }

  private static final class AccountsServiceFileDescriptorSupplier
      extends AccountsServiceBaseDescriptorSupplier {
    AccountsServiceFileDescriptorSupplier() {}
  }

  private static final class AccountsServiceMethodDescriptorSupplier
      extends AccountsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AccountsServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AccountsServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AccountsServiceFileDescriptorSupplier())
              .addMethod(getGetAccountMethod())
              .build();
        }
      }
    }
    return result;
  }
}
