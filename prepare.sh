# install JDK
apt-get update
apt-get upgrade -y
apt-get install openjdk-8-jdk -y

# Install Scala
apt-get install scala

# Install gnupg2
apt-get update && apt-get install -y gnupg

# Install Curl
apt install curl

# Install SBT
echo "deb https://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list
curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | apt-key add
apt-get update
apt-get install sbt
