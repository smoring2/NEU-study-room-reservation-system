mysql password: (empty) installed by homwbrew

Translate rules:
atguigu -> group2
yygh -> nustudy
hosipital -> campus
医院分级 -> 东海岸西海岸，加拿大/美国


nacos -> 类似zookeeper这种注册中心，所以也是基于paxos的package？
Nginx 解决跨域问题 [P65] line 7 - BASE_API: '"http://localhost:9001"' (Path: /Users/cortey/Documents/GitHub/cs6650-final-project/finalproject/nustudy/vue-admin-template-master/config/dev.env.js)
Spring Cache 引入了缓存 + Redis -> 可写入report [P64]
导入excel表格可以自定义，因为模板里只是定义了父子结构

P63更改了数据的id 手动导入形式

TODO:
/hosp 改路径时, nginx需要改；前端路径需要改；后端两个controller需要改 （Dict & ServiceCampusApp）
# Report Group Project NU-Study
## Overview

## Technical Impression
### Tech stack

## How To Test
## Configurations
#### MySQL
#### MongoDB
#### Nginx
- Download Nginx. You can use homebrew to install nginx `brew install nginx`.  
- Modify its config. If you use homebrew to install nginx, you can find its config file at `/usr/local/etc/nginx/nginx.conf`. Please add the following setting in the `http` block:
    ```nginx configuration
        server {
                listen       9001;
                server_name  localhost;
    
            location ~ /campusset/ {
                proxy_pass http://localhost:8201;
            }
            location ~ /cmn/ {
                proxy_pass http://localhost:8202;
            }
        }
    ```
- Start the Nginx. For example, using `brew services restart nginx` to brew start the service.


