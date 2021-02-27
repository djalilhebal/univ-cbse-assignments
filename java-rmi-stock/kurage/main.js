// Load environment variables from the `.env` file.
require('dotenv').config();

// Start the DuckDNS updater
require('./duckdns')();

// Start the Discord bot
require('./discordbot')();
