# TODO

- [x] ChatroomServer/clients: gRPC: Use a response stream to get new messages (Observable?) instead of polling the server every X seconds...
At least this way, we will be using a little advanced feature of gRPC and making the UX a bit ~~better~~ less horrible.

- [ ] ChatroomServer: Expose `UpdatePassword` in the `AccountsService`
```cs
bool UpdatePassword(string accountId, string newPassword) {
  // Users shouldn't use an empty password.
  if (String.IsNullOrEmpty(newPassword)) {
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

- [ ] ChatromWPF: Auto-scroll
  * [ ] CHECK: https://stackoverflow.com/questions/2337822/wpf-listbox-scroll-to-end-automatically

- [ ] ChatroomWPF: LATER: New messages fade into view (animate opacity)
  * [ ] CHECK: [Animation Overview - WPF .NET Framework, "Example: Make an Element Fade In and Out of View" section | Microsoft Docs](https://docs.microsoft.com/en-us/dotnet/desktop/wpf/graphics-multimedia/animation-overview?view=netframeworkdesktop-4.8)


- [ ] ChatroomWPF: Use MVVM.

- [ ] ChatroomWPF: LATER: Replace WPF with Avalonia to make the .NET GUI cross-platform.

---

END.
