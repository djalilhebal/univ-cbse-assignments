const fetch = require('node-fetch').default;

/**
 * Ping the server `/api/version` to see if it's still up.
 */
async function checkServer(host) {
  const API_VERSION_PATH = '/api/version';

  try {
    const json = await fetch(host + API_VERSION_PATH).then(res => res.json());
    if (json.version) {
      return true;
    }
  } catch (e) {}

  return false;
}

async function checkAll() {
  const variations = [
    {desc: 'localhost', host: 'http://localhost:8080', isOnline: null},
    
    {desc: 'local-network', host: 'http://192.168.1.10:8080', isOnline: null},

    // FIXME: Why do I need a proxy to access this? Can't config the modem/router to fix this NAT Loopback issue?
    // {desc: 'internet', host: 'http://[redacted].tech', isOnline: null},
    {desc: 'internet', host: 'http://kurage.duckdns.org', isOnline: null},
  ];

  for (const v of variations) {
    v.isOnline = await checkServer(v.host);
  }

  return variations;
}

module.exports = checkAll;
