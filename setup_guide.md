# ICS0024 Setup and Installation guide for CI/CD

## Virtual server setup

We decided to use AWS's EC2 Computing service to instantiate our linux instances


### SSH keys

To get an EC2 instance running, first we need an SSH key pair We created the initial key pair through EC2.

1. Go to EC2 Dashboard
2. In "Resources" select "Key pairs"
3. Click the "Create key pair"
4. Name that was chosen was "kiloss@mainPc.pem"
5. File format was ".pem"
6. No tags added
7. Confirm by pressing "Create key pair"

### Launching a new EC2 instance

To launch a EC2 instance, which is basically a virtual server in the cloud, we need to take the following steps:

1. Go to EC2 Dashboard
2. In Launch instance press "Launch instance"
3. Select "Ubuntu Server 20.04 LTS (HVM)"
4. When choosing an instance type, we went with "t3.micro" (Because it's free :D)
5. Leave "Configure Instance Details" as is
6. In "Add Storage" set Size to 16 GiB
7. Skipped adding tags
8. In "Configure Security Group" added a rule with Type "All traffic" and Source "0.0.0.0/0, ::/0"
9. Review and Launch > Launch
10. In the list of instances you can specify a name for your new instance (In our case it was "ics0024_prod" for production)

### Connecting to instance via SSH
To get detailed information on how to connect to your running instance select your instance and press "Connect".

Then press the "SSH client" tab, and you will see a nice guide on how to make an initial connection with the key pair
that your instance is using.

Example of initial connection:
```shell script
ssh -i "kiloss@mainPc.pem" ubuntu@ec2-13-53-186-119.eu-north-1.compute.amazonaws.com
```

We are in!

### Adding Public keys

If you want to access the instance from other machines, you will have to add their public keys to .ssh/authorized_keys
file.

```shell script
cd ~/.ssh

nano authorized_keys
```

### Add additional virtual memory

If you want to do stuff that require a lot of memory consumption, you can create a swap file, which basically allows
using disk space as ram, which can improve performance.

 Adding 2GB to swap
```shell script
sudo fallocate -l 2G /swapfile  
sudo chmod 600 /swapfile  
sudo mkswap /swapfile  
sudo swapon /swapfile  
sudo swapon -show  
echo '/swapfile none swap sw 0 0' | sudo tee -a /etc/fstab
```

Last line allows the configuration to persist after machine reboot

### Installing Java to execute our .jar files

```shell script
sudo apt-get update
sudo apt-get upgrade
sudo apt-get install openjdk-11-jre openjdk-11-jdk
```

### Manually run .jar files

If you want to manually run your .jar file

```shell script
java -jar Volatilator-api.jar
```

Replace Volatilator-api.jar with whatever is your .jar file is