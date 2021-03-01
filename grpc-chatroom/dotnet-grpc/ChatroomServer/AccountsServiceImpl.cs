using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using System.Threading.Tasks;
using KaiStuff;
using System.Text.RegularExpressions;

namespace ChatroomServer
{

    public class AccountsServiceImpl: AccountsService.AccountsServiceBase
    {

        // --- START gRPC methods ---
        
        public override Task<Account> GetAccount(AccountRequest accountRequest, Grpc.Core.ServerCallContext context)
        {
            CustomAccount customAccount = GetAccount(accountRequest.Nickname, accountRequest.Password);

            // Returning null is not allowed.
            // The simplest workaround is to return a "default instance" and later checking whether Id (for example) is empty or not.
            //
            // SEE: Protobuf and Null Support. Why doesn’t Protocol Buffers support… | by Erik Englund | ITNEXT
            //      https://itnext.io/protobuf-and-null-support-1908a15311b6
            Account returnAccount = new Account();
            if (customAccount != null)
            {
                returnAccount.Id = customAccount.Id;
                returnAccount.Nickname = customAccount.Nickname;
            }

            return Task.FromResult(returnAccount);
        }

        // --- END gRPC methods ---

        private Dictionary<string, CustomAccount> accounts = new Dictionary<string, CustomAccount>();
        
        public CustomAccount GetAccount(string nickname, string password)
        {
            return GetAccountOrCreate(nickname, password);
        }

        /**
         * "Login or Register".
         *
         * Returns account object, or null if the account already exists but the password doesn't match, or the new account's credentails are invalid.
         */
        public CustomAccount GetAccountOrCreate(string nickname, string password)
        {
            CustomAccount foundAccount = FindAccountByNick(nickname);

            // Account already exists
            if (foundAccount != null)
            {
                if (foundAccount.PasswordMatches(password))
                {
                    Utils.Log(nameof(AccountsServiceImpl), $"Password matches for: {foundAccount}");
                    return foundAccount;
                }
                else
                {
                    Utils.Log(nameof(AccountsServiceImpl), $"Password mismatches for: {foundAccount}");
                    return null;
                }
            }

            // Create new account
            bool areValidCredentials = ValidateNicknameAndPassword(nickname, password);
            if (!areValidCredentials)
            {
                Utils.Log(nameof(AccountsServiceImpl), $"Account creation failed: Invalid nick:pass values");
                return null;
            }

            // Everything seems well, let's create a new account and return it.
            CustomAccount createdAccount = new CustomAccount(nickname, password);
            accounts.Add(createdAccount.Id, createdAccount);
            Utils.Log(nameof(AccountsServiceImpl), $"Account created: {createdAccount}");

            return createdAccount;
        }

        private bool ValidateNicknameAndPassword(string nick, string pass)
        {
            if (string.IsNullOrEmpty(nick) || string.IsNullOrEmpty(pass))
            {
                return false;
            }

            const int MIN_NICKNAME_LENGTH = 1;
            const int MAX_NICKNAME_LENGTH = 15;
            bool isNickWithinLimits = MIN_NICKNAME_LENGTH <= nick.Length && nick.Length <= MAX_NICKNAME_LENGTH;
            if (!isNickWithinLimits)
            {
                // Nickname/username's length must be in [1; 15] (a la Twitter's policy and Auth0's defaults)
                return false;
            }

            // Kinda like IRC's nick rule: `<letter> {<letter> <number> <special>}`
            //  (but we only accept '-' and '_' as <special> characters, a la Reddit.)
            const string nickRegex = "^[a-z]([a-z]|[0-9]|[_-])*$";
            bool isNickConforming = Regex.IsMatch(nick, nickRegex, RegexOptions.IgnoreCase);
            if (!isNickConforming)
            {
                return false;
            }

            return true;
        }

        public CustomAccount FindAccountById(string targetId)
        {
            return accounts.GetValueOrDefault(targetId);
        }

        /**
         * Find an account with `targetNick` as their nickname.
         * 
         * NOTE: Nicknames are case-insensitive, even though they retain their original case when displayed 
         * (this is similar to IRC, Reddit, etc.).
         */
        public CustomAccount FindAccountByNick(string targetNick)
        {
            // Like Java's `stream().filter(..).findFirst()`.
            // The default value of CustomAccount is (I believe) null.
            //
            // About the predicate: It works like Java's String#equalsIgnoreCase(String)
            // (https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#equalsIgnoreCase-java.lang.String-)
            return accounts
                .FirstOrDefault(account => account.Value.Nickname.Equals(targetNick, StringComparison.OrdinalIgnoreCase))
                .Value;
        }

    }

}
