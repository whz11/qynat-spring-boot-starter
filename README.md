# qynat-springboot-starter

[![standard-readme compliant](https://img.shields.io/static/v1?label=qynat&message=springboot&color=important)](https://github.com/whz11/qynat-springboot-starter/)

基于netty的内网穿透工具在springboot中的整合

protocol协议：[protobuf](https://developers.google.cn/protocol-buffers?hl=zh-cn)

只需在`application.properties`中配置少量信息，实现零代码侵入的web项目内网穿透

项目的server端的源码在另一个多模块项目中，包括client端的命令行版、GUI版，还没写好readme暂时先不贴链接



## Table of Contents

- [QuickStart](https://github.com/whz11/qynat-springboot-starter/blob/master/README.md#QuickStart)
- [Maintainers](https://github.com/whz11/qynat-springboot-starter/blob/master/README.md#maintainers)
- [License](https://github.com/whz11/qynat-springboot-starter/blob/master/README.md#license)



## QuickStart

### 服务端配置

在项目的lib包下有个`server.jar`,将他部署在可外网访问的服务器上，输入命令（需在jdk环境下）：

```
java -jar server.jar -p <server-port> -t <token>
```

看到以下，说明服务端成功运行

```
QyNat server started on port xxxx
```

### 客户端配置

#### 引入依赖

  [![](https://jitpack.io/v/whz11/qynat-springboot-starter.svg)](https://jitpack.io/#whz11/qynat-springboot-starter)

* maven项目在pom中引入

```xml
    <dependencies>      
        <dependency>
            <groupId>com.github.whz11</groupId>
            <artifactId>qynat-springboot-starter</artifactId>
            <version>Tag</version><!--见上方jitpack后面的版本-->
        </dependency>
    </dependencies>

    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
 
```

* gradle项目

```properties
	dependencies {
	        implementation 'com.github.whz11:qynat-springboot-starter:Tag'
	}
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

#### 配置application.properties

```properties
##server端部署地址
qynat.server-address=xxx.xxx.xxx.xxx
##server端部署端口号
qynat.server-port=7777
##server端配置令牌（可空）
qynat.token=123456
##本机地址
qynat.proxy-address=localhost
##本机端口
qynat.proxy-port=8080
##映射端口
qynat.remote-port=8080
```

解释：服务运行后，本机地址可以通过`server-address:remote-port`访问

#### 客户端最后一步

在启动类中加上`@EnabledQyNat`注解

```java
@SpringBootApplication
@EnabledQyNat
public class StarterDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarterDemoApplication.class, args);
    }

}
```

在控制台看到以下，说明客户端启动成功

```
Started QyNatClient success mapping at:http://server-address:remote-port
Register to qynat
```



## Maintainers

[@whz11](https://github.com/whz11/).



## Contributing

Feel free to dive in! [Open an issue](https://github.com/whz11/qynat-springboot-starter/issues/new) or submit PRs.



## License

[MIT](https://github.com/whz11/qynat-springboot-starter/blob/master/LICENSE) © whz11

<details class="details-reset details-overlay details-overlay-dark" id="jumpto-line-details-dialog" style="box-sizing: border-box; display: block;"><summary data-hotkey="l" aria-label="Jump to line" role="button" style="box-sizing: border-box; display: list-item; cursor: pointer; list-style: none;"></summary></details>
