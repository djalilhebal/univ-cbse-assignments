using RemotableObjects;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Server
{
    class MailBoxBuilder : MarshalByRefObject, RemotableObjects.IMailBoxBuilder
    {
        public IMailBox buildMailBox()
        {
            return new MailBox();
        }
    }
}
