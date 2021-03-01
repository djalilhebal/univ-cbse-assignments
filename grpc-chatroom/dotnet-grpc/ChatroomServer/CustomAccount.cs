using System;
using System.Collections.Generic;
using System.Text;

namespace ChatroomServer
{
    /**
     * Inspired by IRC:
     * - https://freenode.net/kb/answer/registration
     * - https://freenode.net/kb/answer/sendpass
     */
    [Serializable]
    public class CustomAccount
    {

        public CustomAccount(string nickname, string password)
        {
            Id = System.Guid.NewGuid().ToString();
            Nickname = nickname;
            Password = password;
        }

        public string Id { init; get; }

        public string Nickname { init; get; }

        // NOTE: Any one who has access to this object can change the account password,
        //       hopefully that's only the Accounts manager or the account owner.
        public string Password { set; private get; }

        public bool PasswordMatches(string str)
        {
            return str == Password;
        }

        // ---

        public override string ToString()
        {
            return $"Account({Nickname})";
        }

        public override bool Equals(object obj)
        {
            return obj is CustomAccount account &&
                   Id == account.Id;
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(Id);
        }

    }

}
