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

##### !NB
We can use public IP address instead of a DNS name if we want.

Example in our case:
```shell script
ssh -i "kiloss@mainPc.pem" ubuntu@13.53.186.119
```

### Adding Public keys

If you want to access the instance from other machines, you will have to add their public keys to .ssh/authorized_keys
file.

```shell script
cd ~/.ssh

nano authorized_keys
```

After you have your public key added, you can now ssh with just:

```shell script
ssh ubuntu@ec2-13-53-186-119.eu-north-1.compute.amazonaws.com
```

Or if you prefer to use your IP:

```shell script
ssh ubuntu@13.53.186.119
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

If you want to manually run your .jar file (from .jar's directory):

```shell script
java -jar Volatilator-api.jar
```

Replace Volatilator-api.jar with whatever is your .jar file.

### Installing gitlab-runner
Get the required binary, in our case we need *amd64 binary:
```shell script
sudo curl -L --output /usr/local/bin/gitlab-runner https://gitlab-runner-downloads.s3.amazonaws.com/latest/binaries/gitlab-runner-linux-amd64
```

Give gitlab-runner execution permissions:
```shell script
sudo chmod +x /usr/local/bin/gitlab-runner
```

Create a gitlab user that will be executing the binary
```shell script
sudo useradd --comment 'GitLab Runner' --create-home gitlab-runner --shell /bin/bash
```

Install and run as service
```shell script
sudo gitlab-runner install --user=gitlab-runner --working-directory=/home/gitlab-runner

sudo gitlab-runner start
```

If you are using gitlab runner on AWS Ubuntu 20.04 then you might run into the following problem:

https://gitlab.com/gitlab-org/gitlab-runner/-/issues/26605

To fix this problem you need to alter '/home/gitlab-runner/.bash_logout', so it'd look the following way:

```shell script
# ~/.bash_logout: executed by bash(1) when login shell exits.

# when leaving the console clear the screen to increase privacy

#if [ "$SHLVL" = 1 ]; then
#    [ -x /usr/bin/clear_console ] && /usr/bin/clear_console -q
#fi

```

If you altered .bash_logout after the initial start of the runner then 
you will have to restart it with the following command:

```shell script
sudo service gitlab-runner restart

```

#### NB!
It seems that gitlab-runner might be preinstalled on the virtual server from the get go, so don't worry
too much if you get following messages when running previously listed commands.
```console
useradd: user 'gitlab-runner' already exists
```

Or

```console
FATAL: Failed to install gitlab-runner: Init already exists: /etc/systemd/system/gitlab-runner.service
```

### Register the runner
To register the runner for a specific repository

1. Go to the repository of choice
2. Settings > CI / CD
3. Expand the "Runners" tab
4. Under the "Set up a specific Runner manually" will be information for setup

Run gitlab-runner setup
```shell script
sudo gitlab-runner register
```
Provide information from the "Set up a specific Runner manually"

Also specify the tag for the runner, in our case the tag is "api"

When it asks you to Enter an executor, specify "shell"

### Configure CI in gitlab

Add .gitlab-ci.yml file to the root folder of your API repository

Example of how the file contents should look like:
```yaml
stages:
  - build
  - test
  - deploy

before_script:
  - export GRADLE_USER_HOME=`pwd`/.gradle

build api:
  stage: build
  cache:
    paths:
      - .gradle/wrapper
      - .gradle/caches
  artifacts:
    paths:
      - build/libs
  tags:
    - api
  script:
    - ./gradlew assemble

test api:
  stage: test
  tags:
    - api
  script:
    - ./gradlew check

deploy api:
  stage: deploy
  only:
    refs:
      - main
  tags:
    - api
  script:
    # mkdir make folder api-deployment ~/ is under current user directory so for gitlab it would be /home/gitlab/api-deployment
    - mkdir -p ~/api-deployment
    # rm remove -rf is recursive files from api-deployment
    - rm -rf ~/api-deployment/*
    # cp - copy build/libs is where
    - cp -r build/libs/. ~/api-deployment
    # this requires sudo rights for gitlab user
    - sudo service volatilator-api restart
```

You will also need to be sure the gradlew file has execution permission, this can be done the following way:
```shell script
git update-index --chmod=+x gradlew
```

After you are done, you need to push the changes.

### Create Linux service

We need to create a service which will restart our api after it's deploy.

```shell script
cd /etc/systemd/system

sudo touch volatilator-api.service
```

volatilator-api.service contents:

```shell script
[Unit]
Description=volatilator api service
After=network.target

[Service]
Type=simple
User=gitlab-runner
WorkingDirectory=/home/gitlab-runner/api-deployment
ExecStart=/usr/bin/java -jar Volatilator-api.jar                                                                         Restart=on-abort

[Install]
WantedBy=multi-user.target

```

##### !NB

Since we don't want to commit any sensitive information to our git repository (like contents of certain config files)
we should specify external custom config file when we run our api's .jar file. The config should be created in the
gitlab-runner user's home directory.

```shell script
cd /home/gitlab-runner

nano custom.yaml
```

The contents of custom.yaml should be kept secret (not to leak the api keys, for example)

We also need to alter the api service file, change the line:
```shell script
ExecStart=/usr/bin/java -jar Volatilator-api.jar 
```
To:
```shell script
ExecStart=/usr/bin/java -jar -Dspring.config.location=/home/gitlab-runner/custom.yaml Volatilator-api.jar   
```
We basically tell our service to run our backend with specified config.

After the service file had been defined we need to reload the configuration:

```shell script
sudo systemctl daemon-reload
```

Process must be enabled:

```shell script
sudo systemctl enable volatilator-api
```

Start the service:
```shell script
sudo  service volatilator-api restart
```

To check service status:
```shell script
sudo  service volatilator-api status
```

The last thing we need to do is to provide gitlab-runner user with sudo permissions for the volatilator-api
service, open visudo:

```shell script
sudo visudo
```

At the end of the file add the following:
```
gitlab-runner ALL = NOPASSWD: /usr/sbin/service volatilator-api *
```

Now our built api can be automatically deployed by the runner!

### Map our api to /api

Add this to the top of our backend config:
```yaml
server:
  servlet:
    context-path: /api
```

### Our Api is available at:

13.53.186.119/api

13.53.186.119/api/volatilator

13.53.186.119/api/volatilator?symbol=AMD

### Install Nginx

```shell script
sudo apt-get install nginx
```

### Configure Nginx' reverse proxy
To get rid of the port we will need to add the following to our nginx config at /etc/nginx/sites-enabled/default:
```bash
location /api/ {
    proxy_pass http://localhost:8080;
}
```

After that we need to restart nginx:

```bash
sudo service nginx restart
```

### Final nginx config
```shell script
server {

	listen 80 default_server;
	listen [::]:80 default_server;

	root /var/www/html;

	index index.html index.htm index.nginx-debian.html;

	server_name _;

	location / {
		try_files $uri $uri/ =404;
	}

    location /api/ {  
            proxy_pass   http://localhost:8080;  
    }  
}
```

### Setup DNS through AWS's Route 53 and Freenom

Get a free domain from Freenom (https://my.freenom.com/), then go to AWS

1. Go to AWS' Route 53 service
2. Go to "Hosted zones"
3. Click "Create hosted zones" button
4. Specify the domain name and click "Create hosted zones"

#### Add record

1. Simple routing
2. Define simple record
3. End point: IP address 
4. Record type "A"
5. Define simple record
6. Create Records

#### Provide NS(name servers) to Your owned domain

Go to Route 53's "Hosted zones" and see the NS record server names, keep note of them. Then go to Freenom.

1. Go to "My domains"

2. Click "Manage Domain" for the one you've chosen

3. Go to Management Tools > Nameservers

4. Select "Use custom nameservers"

5. Enter the nameservers from Route 53

##### !NB
The name server will not work straight away, so you will have to wait for a bit.

After you are done, now we are able to access our api though our chosen Domain!

http://volatilator2020.ga/api/

http://volatilator2020.ga/api/volatilator

http://volatilator2020.ga/api/volatilator?symbol=AMD

### HTTPS

Install certbot
``` shell script
sudo add-apt-repository ppa:certbot/certbot

sudo apt install python3-certbot-nginx
```


Go through the setup process (On the last prompt say '2')
```shell script
sudo certbot --nginx
```

### Docker

```shell script
apt install docker
apt install docker-compose
```

add gitlab-runner to docker group
```shell script
usermod -a -G docker gitlab-runner
```

```shell script
mkdir /home/ubuntu/volatilator
mkdir /home/ubuntu/volatilator/public
```

Make config for docker image
```shell script
nano /home/ubuntu/volatilator/docker-compose.yml
```
Content is as follows:
```yaml
version: "3.3"
services:
    web: 
        image: nginx:latest
        restart: always
        volumes:
            - ./public:/var/www/html
            - ./conf.d:/etc/nginx/conf.d
            - /etc/letsencrypt:/etc/letsencrypt
            - ./certbot/data:/var/www/certbot
        ports:
            - 80:80
            - 443:443
    java:
        image: openjdk:latest
        restart: always
        volumes:
           - /home/gitlab-runner/api-deployment:/api
           - ./api-config:/api-config 
        command: bash -c "cd /api && java -jar -Dspring.config.location=/api-config/custom.yaml Volatilator-api.jar"
```

configure nginx
```shell script
mkdir /home/ubuntu/volatilator/conf.d
nano /home/ubuntu/volatilator/conf.d/default
```
Content is as follows:
```yaml
server {
        listen 80 default_server;
        listen [::]:80 default_server;

        root /var/www/html;

        index index.html index.htm index.nginx-debian.html;

        server_name _;

        location / {
                try_files $uri $uri/ =404;
        }

        location /api/ {
                proxy_pass http://java:8080;
                proxy_set_header Host $http_host;
        }
}

server {

        root /var/www/html;

        index index.html index.htm index.nginx-debian.html;
    server_name volatilator2020.ga; # managed by Certbot


        location / {
                try_files $uri $uri/ =404;
        }

        location /api/ {
                proxy_pass http://java:8080;
                proxy_set_header Host $http_host;
        }


    listen [::]:443 ssl ipv6only=on; # managed by Certbot
    listen 443 ssl; # managed by Certbot
    ssl_certificate /etc/letsencrypt/live/volatilator2020.ga/fullchain.pem; # managed by Certbot
    ssl_certificate_key /etc/letsencrypt/live/volatilator2020.ga/privkey.pem; # managed by Certbot
    include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
    ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot

}server {
    if ($host = volatilator2020.ga) {
        return 301 https://$host$request_uri;
    } # managed by Certbot


        listen 80 ;
        listen [::]:80 ;
    server_name volatilator2020.ga;
    return 404; # managed by Certbot


}
```

```shell script
mkdir /home/ubuntu/volatilator/api-config
nano /home/ubuntu/volatilator/api-config/custom.yaml
```
content is as follows:
```yaml
server:
  servlet:
    context-path: /api

app:
  alpha-vantage:
    url: https://www.alphavantage.co/query
    key: IK8R1OK95QFY6YGD

logging:
  file:
    name: logs/volatilator.log
    max-size: 3MB
    max-history: 2
```

```shell script
cd /home/ubuntu/volatilator
docker-compose up -d
```

```shell script
docker-compose ps
```

To restart the docker container
```shell script
docker restart volatilator_java_1
```

add this to sudoers
```shell script
gitlab-runner ALL = NOPASSWD: /usr/sbin/docker restart volatilator_java_1
```
