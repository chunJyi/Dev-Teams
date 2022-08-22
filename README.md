# <h1>Dev-Teams</h1>
# deploy by heroku

You can try -DevTeams Application 

Admin user (root@gmail.com,root) and Normal user (create account using sign-up page)

https://ourdevteam.herokuapp.com

![This is an image](https://github.com/chunJyi/Dev-Teams/blob/main/Screenshot%20from%202022-08-08%2012-31-19.png)

#Docker profile

https://hub.docker.com/u/chunjyi


# <h2>Steps to Setup</h2>

#### 1. pull docker image


      sudo docker pull chunjyi/dev_team
      
#### 2. create database using PostgreSQL

      create database dev_teamdb;
      
      
 #### . insert root user 
    
#### 2. run docker 

     sudo docker run  --net=host --name dev_team  -p 8080:8080 chunjyi/dev_team:latest


#### 3. Explore

    http://localhost:8080/



