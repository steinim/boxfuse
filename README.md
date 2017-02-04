# Requirements

## Install envchain to set boxfuse credentials

```bash
brew install envchain
envchain --set boxfuse BOXFUSE_USER BOXFUSE_SECRET
```
See: https://github.com/sorah/envchain

## Install additional tools

```bash
brew install ansible
```

#Build

```bash
cd helloworld-java-app
envchain boxfuse mvn clean install
```

#Commands
* [Commandline](https://boxfuse.com/docs/commands)
* [Maven](https://boxfuse.com/docs/maven/)

