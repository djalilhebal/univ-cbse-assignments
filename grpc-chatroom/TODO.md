# TODO

- [x] ChatroomServer/clients: gRPC: Use a response stream to get new messages (Observable?) instead of polling the server every X seconds...
At least this way, we will be using a little advanced feature of gRPC and making the UX a bit ~~better~~ less horrible.

- [ ] ChatroomServer: Like `OnNewMessage(..)`, updating the observers set should be made thread-safe as well... but then again, we are not caring about synchronization, this is not the _Concurrent Software Development_ class.
  * Even list operations should be locked, or at least replace the list with a thread-safe collection.  
  See: [Thread-Safe Collections | Microsoft Docs](https://docs.microsoft.com/en-us/dotnet/standard/collections/thread-safe/).

- [ ] ChatroomServer: Also, `OnNewMessage(..)` should be taken out of `SaveMessage(..)` and invoked in `SendMessage` (the gRPC-aware overload).

- [ ] ChatroomServer: We have already strayed away from the assignment, might as well simplify the project and get rid of array stuff:
  * `MessagesServiceImpl` doesn't need to use arrays. Get rid of `messagesArray` and `ToArray()` calls.
  * Instead of returning an `Array` or a `List`, return `IList` which both of them implement.  
    About `Array`s and the interfaces it implements.  
    See: [Array Class (System) | Microsoft Docs](https://docs.microsoft.com/en-us/dotnet/api/system.array?view=netframework-4.8).

- [ ] ChatroomServer: Expose `UpdatePassword` in the `AccountsService`
```cs
bool UpdatePassword(string accountId, string newPassword)
{
    // Users shouldn't use an empty password.
    if (String.IsNullOrEmpty(newPassword))
    {
        return false;
    }

    var account = getAccountById(accountId);
    if (account == null)
    {
        return false;
    }
    else
    {
        account.Password = newPassword;
        return true;
    }
}
```

---

- [ ] ChatroomTUI: Use `me.djalil.univ` for the package instead of the default package.

---

- [ ] ChatroomWPF: Auto-scroll
  * [ ] CHECK: https://stackoverflow.com/questions/2337822/wpf-listbox-scroll-to-end-automatically

- [ ] ChatroomWPF: LATER: New messages fade into view (animate opacity)
  * [ ] CHECK: [Animation Overview - WPF .NET Framework, "Example: Make an Element Fade In and Out of View" section | Microsoft Docs](https://docs.microsoft.com/en-us/dotnet/desktop/wpf/graphics-multimedia/animation-overview?view=netframeworkdesktop-4.8)

- [ ] ChatroomWPF: Use MVVM.

- [ ] ChatroomWPF: LATER: Replace WPF with Avalonia to make the .NET GUI cross-platform.

---

END.
