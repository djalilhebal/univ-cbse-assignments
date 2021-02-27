const fetch = require('node-fetch').default;

const DUCKDNS_DOMAIN = 'kurage.duckdns.org';
const DUCKDNS_TOKEN  = process.env.MY_DUCKDNS_TOKEN;

/**
 * Update `every` X minutes.
 * 
 * NOTE: To stop it, set `settings.stopped` to `true`.
 * 
 * @see https://www.duckdns.org/spec.jsp
 * 
 * @param {{ token: string; domain: string; every: number; stopped: boolean; }} settings
 */
async function startDuckDNSUpdater(settings) {
  const url = `https://duckdns.org/update/${settings.domain}/${settings.token}`;
  const delay = (ms) => new Promise(resolve => setTimeout(resolve, ms));

  while (!settings.stopped) {
    try {
      console.log(`[duckdns][${ (new Date()).toISOString() }] Updating...`);
      const reply = await fetch(url).then(res => res.text());
      if (reply === 'OK') {
        console.log('[duckdns] Update successful.');
      } else if (reply === 'KO') {
        console.error('[duckdns] Update failed.');
      } else {
        console.error('[duckdns] Unexpected reply: ' + reply);
      }
    } catch (ignoredNetworkError) {};
    await delay(settings.every * 1000 * 60);
  }

  console.log('[duckdns] Stopped');
}

const obj = {
  token: DUCKDNS_TOKEN,
  domain: DUCKDNS_DOMAIN,
  every: 30, // minutes
  stopped: false,
};

module.exports = () => startDuckDNSUpdater(obj);
