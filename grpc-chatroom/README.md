# gRPC Chatroom
gRPC Chatroom (C#.NET and Java) is an IRC-inspired thing, for a CBSE assignment. `#grpc #dotnet #java`

Projects:
- .NET 5 server: `dotnet-grpc/ChatroomServer/`
- .NET 5 GUI client (WPF): `dotnet-grpc/ChatroomWPF/`
- Java CLI client: `java-grpc/ChatroomTUI/`
  * Also see [ChatroomTUI's README](java-grpc/ChatroomTUI/README.md).

![.NET WPF client and .NET server on Windows](dotnet-grpc/screenshot-2021-02-28--dotnet-windows.png)

![Java client and .NET server on Linux/Ubuntu](java-grpc/ChatroomTUI/screenshot-2021-02-26--java-dotnet-ubuntu.png)


## Notes

- IMPORTANT: The authoritative `.proto` files are those in `dotnet-grpc/ChatroomServer/protos/`.  
  (Meaning: We are editing those files and then copying them to wherever.)

- We're trying to follow Google's Proto style guide.  
  See: [Style Guide | Protocol Buffers | Google Developers](https://developers.google.com/protocol-buffers/docs/style).

- ChatroomServer: About our "custom" data types: we are using `CustomMessage` and `CustomAccount` instead of the automatically generated stuff so we
  can add custom methods and logic (`ToString`, `Equals`, `PasswordMatches`, `EnsureNotFrozen`, etc.).

- We could, and this is probably better, use the same channel for both Account and Message operations.  
  See: [Performance best practices with gRPC, "Reuse gRPC channels" section | Microsoft Docs](https://docs.microsoft.com/en-us/aspnet/core/grpc/performance?view=aspnetcore-5.0).

- ChatroomWPF: Although WPF is still a Windows-only UI framework, it is now a part of .NET 5.
ChatroomWPF was originally a .NET Framework project, but the target was changed to .NET [Core] 5.
This was made because .NET 5 is the future of the .NET ecosystem and is easier this way to port (maybe to AvaloniaUI).


### "Refresh" Options

1. After every X seconds, get all messages (`GetMessages()`) and replace the whole output.

2. At first, get all messages (`GetMessages()`), and then start getting only messages that come after the latest message (`GetMessagesAfter(latestMessage)`).

3. At startup, get all messages (`GetMessages()`) and then listen for new messages (`GetNewMessages()...`).

4. To make it simpler to implement and closer to how IRC works, we can give clients only the option to receive new messages after they have connected (`GetNewMessages()...`).

The 3rd option is what we're doing in ChatroomWPF.


## Possible Improvements

Check [the TODO file](TODO.md).

- ChatroomWPF: For some reason, the Proto files are not included in build path and I kept having "class not defined" errors although they were being build in the `obj/Debug/netcoreapp5.0/protos/` directory.
I simply disabled the compilation of Proto files, manually copied the generated classes to `protos/generated/`, and called it a day.  
A similar thing happened with the Java client.

- Obvious cases such as the client getting disconnected due to network errors are not handled.
  See [my "About Network Errors" ramblings](2021-03-08_about-network-errors.md).

- ChatroomServer/Security: We're using the user's ID as if it were their secret, unchangeable login token, even if their password is changed. This is unacceptable.


## References and Good Reads

- [RFC 1459 - Internet Relay Chat Protocol](https://tools.ietf.org/html/rfc1459)

- [RFC 2812 - Internet Relay Chat: Client Protocol, § 2.3.1 Message format in Augmented BNF](https://tools.ietf.org/html/rfc2812#section-2.3.1) `#insp`

- [Example IRC Communications — The UChicago χ-Projects | UChicago.edu](http://chi.cs.uchicago.edu/chirc/irc_examples.html) `#interesting`

- [Best IRC Clients for Linux | Tecmint](https://www.tecmint.com/best-irc-clients-for-linux/) `#insp`

- [Why I Live in IRC | Aaron Parecki](https://aaronparecki.com/2015/08/29/8/why-i-live-in-irc) `#interesting`


## License

By @DjalilHebal and @WanisRamdani, under CC BY 3.0.
