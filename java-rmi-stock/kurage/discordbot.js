const Discord = require('discord.js');
const client = new Discord.Client();

const checkAll = require('./ping-version');

// To add the bot to a Discord server (Permissions = 16 = "Manager Channels"):
// const DISCORD_INVITE_LINK = 'https://discord.com/oauth2/authorize?client_id=742772879099297893&scope=bot&permissions=16';
const DISCORD_BOT_TOKEN = process.env.DISCORD_BOT_TOKEN;

async function main() {
  const settings = {
    stopped: false,
    every: 1, // in minutes
  }

  console.log('[discordbot] Logging in...');
  await client.login(DISCORD_BOT_TOKEN);
  console.log(`[discordbot] Logged in as ${client.user.tag}`);

  process.on('beforeExit', () => {
    console.log('[discordbot] Destorying...');
    settings.stopped = true;
    client.destroy();
  });

  client.once('ready', () => {
    console.log('[discordbot] Ready. Starting the updater...');
    updateChannelDesc(settings);
  });

}

/**
 * Every `every` minutes, update #server's description ("topic") to the current timestamp.
 * 
 * @todo Consider https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Intl/DateTimeFormat
 * 
 * @see https://discord.js.org/#/docs/main/master/class/TextChannel?scrollTo=setTopic
 */
async function updateChannelDesc(settings) {
  const delay = (ms) => new Promise(resolve => setTimeout(resolve, ms));

  // #server channel
  const API_CHANNEL_ID = '734879946446602272';

  /**
   * @type {Discord.TextChannel}
   */
  const channel = await client.channels.fetch(API_CHANNEL_ID);

  while (!settings.stopped) {
    try {
      const isUp = (await checkAll()).some(entry => entry.isOnline);
      if (!isUp) {
        throw new Error('Tomcat not running');
      }

      // `toISOString`: The timezone is always zero UTC offset, as denoted by the suffix "Z".
      const tzoffset = (new Date()).getTimezoneOffset() * 60000; //offset in milliseconds
      const localISOTime = (new Date(Date.now() - tzoffset)).toISOString().slice(0, -1);
      const [date, time] = localISOTime.split('T');
      const hhmm = time.slice(0, time.indexOf(':', time.indexOf(':') + 1));

      console.log(`[discordbot][${ (new Date()).toISOString() }] Updating...`);
      await channel.setTopic(`Last seen up: ${hhmm} (${date})`);
      console.log('[discordbot] Updated.');
    } catch (e) {
      console.error('[discordbot] Error: ', e);
    }
    await delay(settings.every * 1000 * 60);
  }

}

module.exports = main;
