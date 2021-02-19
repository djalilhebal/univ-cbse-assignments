TODO
=====

- `LogWithTime`: Force using offset 0 (Z) because it's shorter by 5 characters.
```cs
// XXX: ToString("O") returns the offset even +00:00, while 'Z' would be better.
DateTimeOffset now = DateTimeOffset.UtcNow;
string nowStr = now.ToString("O").Replace("+00:00", "Z");
```
