# Aurora
The number 1 open-source backdoor solution.

Aurora is a plugin I (Banana-Grease) designed to have fun with my friend's Minecraft servers, and if I get the chance combat EULA-breaking servers. Therefore I take no responsibility for what you do with the plugin, it has some incredibly powerful features if you don't know what you are doing.

Just so you are aware, the whole point of Aurora is to be compiled into another open-source plugin with a little bit of Java know-how. It will definitely still work as a stand-alone plugin though, just have good social engineering skills

# How to use?

Whenever an Aurora command is detected to be sent through chat, the event will be cancelled with the highest priority.

An Aurora command is prefixed by a unique code for every player. This is to prevent randos from discovering the plugin. 

To acquire your code, take your players name and SHA-256 Hash it though this website https://xorbin.com/tools/sha256-hash-calculator

your hash is from the third character (inclusive) of the hash untill you have reached 6 characters.

e.g. This Hash (9a7946b0ec69f25fc1b252fc43405536d89dcd8bffff71007b5b24397c77eb09) Generates this Usage Key: "946b0e"

A normal command looks like this: "946b0e ServerSpec"'

A command with the prefix key disabled looks like this: "ServerSpec"

IF THE PREFIX KEY IS DISABLED, NO MESSAGE YOU SEND WILL GO TO THE CHAT, IF THE KEY IS ENABLED (default), ONLY COMMANDS THAT CONTAIN THE USAGE KEY WILL NOT BE SENT


# Feature-List
for reference, all the below functions will have the command and then parameters will be displayed like (Parameter1) and optional parameters will be displayed like %(OptionalParameter2)%

## DoSecurityPrefix -> %("true"/"false")%
This command has three outcomes based on the parameter.

#### No Parameter:

  it will show you whether the security prefix is required before an Aurora command
  
#### Boolean:

  will set if you need to type the security prefix before an Aurora command
  
  true - prefix (recommended for max stealth)
  
  false - no prefix

## (PlayerName)
This command has no name and only requires you to type any players-exact name. The command brings up a GUI with options to manipulate that player. This includes Anti-Ban & Anti-Kick options as well as malicious ones.

## Server
Similarly to the above command, this one brings up multiple manipulation options for the server through a GUI.

## ServerExecute -> (Windows Shell command / Linux Shell command)
This command is potentially very dangerous and thus is not recommended to ever use, this runs any command you parse to the OSs shell and is executed as administrator

## ServerSpec
This command attempts to fetch as much data as possible about the JRE running the server and the bare-metal hardware

## Sudo -> %(Console Command)%
This command attempts to send any console command to the console. It will not inform anyone weather the command fails or succeeds in chat or in the console. BUT it will say something along the lines of "[Server] Changed time to day". or if you run a command giving yourself things your name will show up so use locations instead

## Version
Inside each release of the plugin, there is a version and a build hard coded. to access these use this command
