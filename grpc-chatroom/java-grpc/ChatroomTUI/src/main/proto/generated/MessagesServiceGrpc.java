import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.35.0)",
    comments = "Source: messages_service.proto")
public final class MessagesServiceGrpc {

  private MessagesServiceGrpc() {}

  public static final String SERVICE_NAME = "MessagesService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<MessagesServiceOuterClass.Message,
      MessagesServiceOuterClass.SendMessageResponse> getSendMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendMessage",
      requestType = MessagesServiceOuterClass.Message.class,
      responseType = MessagesServiceOuterClass.SendMessageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<MessagesServiceOuterClass.Message,
      MessagesServiceOuterClass.SendMessageResponse> getSendMessageMethod() {
    io.grpc.MethodDescriptor<MessagesServiceOuterClass.Message, MessagesServiceOuterClass.SendMessageResponse> getSendMessageMethod;
    if ((getSendMessageMethod = MessagesServiceGrpc.getSendMessageMethod) == null) {
      synchronized (MessagesServiceGrpc.class) {
        if ((getSendMessageMethod = MessagesServiceGrpc.getSendMessageMethod) == null) {
          MessagesServiceGrpc.getSendMessageMethod = getSendMessageMethod =
              io.grpc.MethodDescriptor.<MessagesServiceOuterClass.Message, MessagesServiceOuterClass.SendMessageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  MessagesServiceOuterClass.Message.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  MessagesServiceOuterClass.SendMessageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MessagesServiceMethodDescriptorSupplier("SendMessage"))
              .build();
        }
      }
    }
    return getSendMessageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<MessagesServiceOuterClass.EmptyRequest,
      MessagesServiceOuterClass.GetMessagesResponse> getGetMessagesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMessages",
      requestType = MessagesServiceOuterClass.EmptyRequest.class,
      responseType = MessagesServiceOuterClass.GetMessagesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<MessagesServiceOuterClass.EmptyRequest,
      MessagesServiceOuterClass.GetMessagesResponse> getGetMessagesMethod() {
    io.grpc.MethodDescriptor<MessagesServiceOuterClass.EmptyRequest, MessagesServiceOuterClass.GetMessagesResponse> getGetMessagesMethod;
    if ((getGetMessagesMethod = MessagesServiceGrpc.getGetMessagesMethod) == null) {
      synchronized (MessagesServiceGrpc.class) {
        if ((getGetMessagesMethod = MessagesServiceGrpc.getGetMessagesMethod) == null) {
          MessagesServiceGrpc.getGetMessagesMethod = getGetMessagesMethod =
              io.grpc.MethodDescriptor.<MessagesServiceOuterClass.EmptyRequest, MessagesServiceOuterClass.GetMessagesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMessages"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  MessagesServiceOuterClass.EmptyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  MessagesServiceOuterClass.GetMessagesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MessagesServiceMethodDescriptorSupplier("GetMessages"))
              .build();
        }
      }
    }
    return getGetMessagesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<MessagesServiceOuterClass.Message,
      MessagesServiceOuterClass.GetMessagesResponse> getGetMessagesAfterMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMessagesAfter",
      requestType = MessagesServiceOuterClass.Message.class,
      responseType = MessagesServiceOuterClass.GetMessagesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<MessagesServiceOuterClass.Message,
      MessagesServiceOuterClass.GetMessagesResponse> getGetMessagesAfterMethod() {
    io.grpc.MethodDescriptor<MessagesServiceOuterClass.Message, MessagesServiceOuterClass.GetMessagesResponse> getGetMessagesAfterMethod;
    if ((getGetMessagesAfterMethod = MessagesServiceGrpc.getGetMessagesAfterMethod) == null) {
      synchronized (MessagesServiceGrpc.class) {
        if ((getGetMessagesAfterMethod = MessagesServiceGrpc.getGetMessagesAfterMethod) == null) {
          MessagesServiceGrpc.getGetMessagesAfterMethod = getGetMessagesAfterMethod =
              io.grpc.MethodDescriptor.<MessagesServiceOuterClass.Message, MessagesServiceOuterClass.GetMessagesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMessagesAfter"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  MessagesServiceOuterClass.Message.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  MessagesServiceOuterClass.GetMessagesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MessagesServiceMethodDescriptorSupplier("GetMessagesAfter"))
              .build();
        }
      }
    }
    return getGetMessagesAfterMethod;
  }

  private static volatile io.grpc.MethodDescriptor<MessagesServiceOuterClass.EmptyRequest,
      MessagesServiceOuterClass.Message> getGetNewMessagesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetNewMessages",
      requestType = MessagesServiceOuterClass.EmptyRequest.class,
      responseType = MessagesServiceOuterClass.Message.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<MessagesServiceOuterClass.EmptyRequest,
      MessagesServiceOuterClass.Message> getGetNewMessagesMethod() {
    io.grpc.MethodDescriptor<MessagesServiceOuterClass.EmptyRequest, MessagesServiceOuterClass.Message> getGetNewMessagesMethod;
    if ((getGetNewMessagesMethod = MessagesServiceGrpc.getGetNewMessagesMethod) == null) {
      synchronized (MessagesServiceGrpc.class) {
        if ((getGetNewMessagesMethod = MessagesServiceGrpc.getGetNewMessagesMethod) == null) {
          MessagesServiceGrpc.getGetNewMessagesMethod = getGetNewMessagesMethod =
              io.grpc.MethodDescriptor.<MessagesServiceOuterClass.EmptyRequest, MessagesServiceOuterClass.Message>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetNewMessages"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  MessagesServiceOuterClass.EmptyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  MessagesServiceOuterClass.Message.getDefaultInstance()))
              .setSchemaDescriptor(new MessagesServiceMethodDescriptorSupplier("GetNewMessages"))
              .build();
        }
      }
    }
    return getGetNewMessagesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MessagesServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MessagesServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MessagesServiceStub>() {
        @java.lang.Override
        public MessagesServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MessagesServiceStub(channel, callOptions);
        }
      };
    return MessagesServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MessagesServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MessagesServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MessagesServiceBlockingStub>() {
        @java.lang.Override
        public MessagesServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MessagesServiceBlockingStub(channel, callOptions);
        }
      };
    return MessagesServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MessagesServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MessagesServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MessagesServiceFutureStub>() {
        @java.lang.Override
        public MessagesServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MessagesServiceFutureStub(channel, callOptions);
        }
      };
    return MessagesServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class MessagesServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     **
     * Add message.
     * NOTE: As a crude form of authentication, when sending a new message,
     * the Author attribute MUST be the Author's Id (secret) instead of their nick (public).
     * </pre>
     */
    public void sendMessage(MessagesServiceOuterClass.Message request,
        io.grpc.stub.StreamObserver<MessagesServiceOuterClass.SendMessageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendMessageMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Return all messages.
     * </pre>
     */
    public void getMessages(MessagesServiceOuterClass.EmptyRequest request,
        io.grpc.stub.StreamObserver<MessagesServiceOuterClass.GetMessagesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMessagesMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Return all messages after `targetMessage`.
     * Like, if we have a list `{A, B, C, D, E}` and we call `GetMessagesAfter(C)`, it will return `{D, E}`.
     * `GetMessagesAfter(E)` would send an empty list `{}`.
     * </pre>
     */
    public void getMessagesAfter(MessagesServiceOuterClass.Message request,
        io.grpc.stub.StreamObserver<MessagesServiceOuterClass.GetMessagesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMessagesAfterMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Subscribe for new messages.
     * </pre>
     */
    public void getNewMessages(MessagesServiceOuterClass.EmptyRequest request,
        io.grpc.stub.StreamObserver<MessagesServiceOuterClass.Message> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetNewMessagesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSendMessageMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                MessagesServiceOuterClass.Message,
                MessagesServiceOuterClass.SendMessageResponse>(
                  this, METHODID_SEND_MESSAGE)))
          .addMethod(
            getGetMessagesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                MessagesServiceOuterClass.EmptyRequest,
                MessagesServiceOuterClass.GetMessagesResponse>(
                  this, METHODID_GET_MESSAGES)))
          .addMethod(
            getGetMessagesAfterMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                MessagesServiceOuterClass.Message,
                MessagesServiceOuterClass.GetMessagesResponse>(
                  this, METHODID_GET_MESSAGES_AFTER)))
          .addMethod(
            getGetNewMessagesMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                MessagesServiceOuterClass.EmptyRequest,
                MessagesServiceOuterClass.Message>(
                  this, METHODID_GET_NEW_MESSAGES)))
          .build();
    }
  }

  /**
   */
  public static final class MessagesServiceStub extends io.grpc.stub.AbstractAsyncStub<MessagesServiceStub> {
    private MessagesServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MessagesServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MessagesServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     **
     * Add message.
     * NOTE: As a crude form of authentication, when sending a new message,
     * the Author attribute MUST be the Author's Id (secret) instead of their nick (public).
     * </pre>
     */
    public void sendMessage(MessagesServiceOuterClass.Message request,
        io.grpc.stub.StreamObserver<MessagesServiceOuterClass.SendMessageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendMessageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Return all messages.
     * </pre>
     */
    public void getMessages(MessagesServiceOuterClass.EmptyRequest request,
        io.grpc.stub.StreamObserver<MessagesServiceOuterClass.GetMessagesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMessagesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Return all messages after `targetMessage`.
     * Like, if we have a list `{A, B, C, D, E}` and we call `GetMessagesAfter(C)`, it will return `{D, E}`.
     * `GetMessagesAfter(E)` would send an empty list `{}`.
     * </pre>
     */
    public void getMessagesAfter(MessagesServiceOuterClass.Message request,
        io.grpc.stub.StreamObserver<MessagesServiceOuterClass.GetMessagesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMessagesAfterMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Subscribe for new messages.
     * </pre>
     */
    public void getNewMessages(MessagesServiceOuterClass.EmptyRequest request,
        io.grpc.stub.StreamObserver<MessagesServiceOuterClass.Message> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetNewMessagesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class MessagesServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<MessagesServiceBlockingStub> {
    private MessagesServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MessagesServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MessagesServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     **
     * Add message.
     * NOTE: As a crude form of authentication, when sending a new message,
     * the Author attribute MUST be the Author's Id (secret) instead of their nick (public).
     * </pre>
     */
    public MessagesServiceOuterClass.SendMessageResponse sendMessage(MessagesServiceOuterClass.Message request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendMessageMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Return all messages.
     * </pre>
     */
    public MessagesServiceOuterClass.GetMessagesResponse getMessages(MessagesServiceOuterClass.EmptyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMessagesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Return all messages after `targetMessage`.
     * Like, if we have a list `{A, B, C, D, E}` and we call `GetMessagesAfter(C)`, it will return `{D, E}`.
     * `GetMessagesAfter(E)` would send an empty list `{}`.
     * </pre>
     */
    public MessagesServiceOuterClass.GetMessagesResponse getMessagesAfter(MessagesServiceOuterClass.Message request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMessagesAfterMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Subscribe for new messages.
     * </pre>
     */
    public java.util.Iterator<MessagesServiceOuterClass.Message> getNewMessages(
        MessagesServiceOuterClass.EmptyRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetNewMessagesMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MessagesServiceFutureStub extends io.grpc.stub.AbstractFutureStub<MessagesServiceFutureStub> {
    private MessagesServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MessagesServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MessagesServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     **
     * Add message.
     * NOTE: As a crude form of authentication, when sending a new message,
     * the Author attribute MUST be the Author's Id (secret) instead of their nick (public).
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<MessagesServiceOuterClass.SendMessageResponse> sendMessage(
        MessagesServiceOuterClass.Message request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendMessageMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Return all messages.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<MessagesServiceOuterClass.GetMessagesResponse> getMessages(
        MessagesServiceOuterClass.EmptyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMessagesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Return all messages after `targetMessage`.
     * Like, if we have a list `{A, B, C, D, E}` and we call `GetMessagesAfter(C)`, it will return `{D, E}`.
     * `GetMessagesAfter(E)` would send an empty list `{}`.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<MessagesServiceOuterClass.GetMessagesResponse> getMessagesAfter(
        MessagesServiceOuterClass.Message request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMessagesAfterMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND_MESSAGE = 0;
  private static final int METHODID_GET_MESSAGES = 1;
  private static final int METHODID_GET_MESSAGES_AFTER = 2;
  private static final int METHODID_GET_NEW_MESSAGES = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MessagesServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MessagesServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND_MESSAGE:
          serviceImpl.sendMessage((MessagesServiceOuterClass.Message) request,
              (io.grpc.stub.StreamObserver<MessagesServiceOuterClass.SendMessageResponse>) responseObserver);
          break;
        case METHODID_GET_MESSAGES:
          serviceImpl.getMessages((MessagesServiceOuterClass.EmptyRequest) request,
              (io.grpc.stub.StreamObserver<MessagesServiceOuterClass.GetMessagesResponse>) responseObserver);
          break;
        case METHODID_GET_MESSAGES_AFTER:
          serviceImpl.getMessagesAfter((MessagesServiceOuterClass.Message) request,
              (io.grpc.stub.StreamObserver<MessagesServiceOuterClass.GetMessagesResponse>) responseObserver);
          break;
        case METHODID_GET_NEW_MESSAGES:
          serviceImpl.getNewMessages((MessagesServiceOuterClass.EmptyRequest) request,
              (io.grpc.stub.StreamObserver<MessagesServiceOuterClass.Message>) responseObserver);
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

  private static abstract class MessagesServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MessagesServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return MessagesServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MessagesService");
    }
  }

  private static final class MessagesServiceFileDescriptorSupplier
      extends MessagesServiceBaseDescriptorSupplier {
    MessagesServiceFileDescriptorSupplier() {}
  }

  private static final class MessagesServiceMethodDescriptorSupplier
      extends MessagesServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MessagesServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (MessagesServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MessagesServiceFileDescriptorSupplier())
              .addMethod(getSendMessageMethod())
              .addMethod(getGetMessagesMethod())
              .addMethod(getGetMessagesAfterMethod())
              .addMethod(getGetNewMessagesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
