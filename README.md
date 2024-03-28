# 一、背景

在互联网行业中，企业通常可分为两大类别：2C和2B。对于2B企业而言，它们的产品往往以产品的形式提供给各个合作机构。以金融领域为例，一家2B金融公司通常将产品销售给各个银行和证券公司，这是2B领域常见的做法。

然而，在与众多合作机构合作时，常常需要进行产品迭代和定制化功能的开发。这些定制化功能涉及到前端页面和后端接口，有些功能甚至不适合合并到主线产品中，而只能作为合作机构的特殊功能。随着合作机构的增加，一个产品可能需要兼容数十家银行，这使得面对众多定制化需求以及需求可能需要合并至产品的情况变得复杂。

采用分支管理来解决这些问题是一个可能的方法，但随着时间的推移，分支管理可能会变得难以维护。开发人员可能会忘记自己的分支所对应的功能，这会导致混乱和错误。

因此，2B企业需要一种高效的方式来解决这些问题，同时还能够快速地与合作公司对接。这种方式可以是一种灵活的架构设计，可以支持定制化需求，并且具备良好的可维护性和可扩展性。



# 二、解决方案

针对这种情况，业内存在两种常见的观点：

1. **超级产品：产品自身支持所有合作机构所需功能**
2. **产品SDK化：产品自身维持标准版本，各机构版本定制化功能单独开发**

第一种方案通常适用于合作机构较少、产品量级较小的公司。然而，随着产品功能和合作机构数量的增长，采用这种方案可能导致应用变得越来越庞大且臃肿。产品可能会逐渐偏离最初的设计理念，因为众所周知，产品的功能越多并不意味着产品的质量更好。此外，如果在后期发现问题需要进行调整，那么回归到原始的设计将变得非常困难，这会降低系统的扩展性，如下图：

![image01.png](picture%2Fimage01.png)


第二种方案是笔者推荐的方法。将产品SDK化，打造成通用的标准版本，各合作机构只需依赖SDK产品并开发定制化功能即可。这种方式既保持了产品的精简性，又实现了各机构版本的可插拔性。通过从一开始就设计良好的架构，可以极大地方便后续的扩展和维护。同时，这种方案也为未来的产品升级和技术创新提供了更大的空间和灵活性，如下图：

![image01.png](picture%2Fimage02.png)




# 三、什么是SDK？

SDK本质上是一个JAR包，其中包含了一个应用的所有功能和组件。

1. 假设你是一家提供自行车租赁服务的公司你的自行车租赁服务可以让用户通过手机App租借自行车并进行骑行。

现在，你的公司面临一个问题：你的自行车租赁服务需要适应不同城市的交通规划和用户需求，每个城市可能都有自己特定的交通规则和骑行习惯。

这时候，你可以考虑将你的自行车租赁服务功能SDK化。所谓SDK化，就是将你的自行车租赁服务功能打包成一个软件开发工具包（SDK），让其他开发者可以轻松地集成你的自行车租赁服务功能到他们的应用中去。

比如，你可以把自行车租赁服务功能封装成一个SDK，其中包含了租借自行车的接口、骑行路线规划的文档说明、示例代码等。其他应用开发者只需要引入你的SDK到他们的应用中，并根据你提供的文档和示例代码来调用租借自行车的接口，就可以在他们的应用中实现自行车租赁服务了。



2. 假设你是一家提供在线支付服务的公司，你的服务可以让其他应用在其应用中集成支付功能。

现在，你的公司面临一个问题：你的支付服务需要适应不同的应用场景和不同的合作伙伴，每个合作伙伴可能都有自己特定的需求和定制化要求。

这时候，你可以考虑将你的支付服务SDK化。所谓SDK化，就是将你的支付服务打包成一个软件开发工具包（SDK），让其他开发者可以轻松地集成你的支付功能到他们的应用中去。

比如，你可以把支付服务封装成一个SDK，其中包含了支付接口、支付流程的文档说明、示例代码等。其他开发者只需要引入你的SDK到他们的应用中，并根据你提供的文档和示例代码来调用支付接口，就可以在他们的应用中实现支付功能了。

这样一来，你的支付服务就变得更加灵活和易用了。不同的应用开发者可以根据自己的需求定制支付功能，而你也不用为每个合作伙伴单独开发定制化的支付功能，节省了大量的时间和精力。同时，你的支付服务也更加易于扩展和维护，因为你只需要维护一个统一的SDK版本，而不用担心不同版本之间的兼容性和更新问题。



# 四、SDK化实操

以下示例均可在 [gitHub#sdk-examples](https://github.com/LightGao-Hub/sdk-examples) 仓库上找到。

为了让读者更好地理解产品SDK化改造的过程，我们将采用**传统应用**和**应用SDK化**两种实现进行对比，以便读者更轻松地理解两者之间的区别和详细实现方式。

## 4.1、传统应用

1. 这里我们简单模拟一个web应用，使用maven工具创建三个model模块，结构如下：

   ```
   sdk-app
       └─app-api
       └─app-common
       └─app-web
   ```

2. app-web模块中编写常见的controller、service层且该模块依赖app-api及app-common，结构如下图:

![image01.png](picture%2Fimage03.png)


3. app-web模块的实现非常简洁明了。它通过对外开放/order接口，调用OrderService接口，最终返回Order实体类。以下是相关代码示例：

   ```java
   // AppWebApplication
   @SpringBootApplication
   public class AppWebApplication {
       public static void main(String[] args) {
           SpringApplication.run(AppWebApplication.class, args);
       }
   }
   ```

   ```java
   @RestController
   public class AppOrderController {
       @Autowired
       private OrderService orderService;
       @GetMapping("/order")
       public Order selectOrder() {
           return orderService.getOrder();
       }
   }
   ```

   ```java
   public interface OrderService {
       Order getOrder();
   }
   ```

   ```java
   @Service
   public class OrderServiceImpl implements OrderService {
   	// 获取application.properties配置文件参数值
       @Value("${tenant.order.name}") 
       private String orderName;
       @Override
       public Order getOrder() {
           return new Order(orderName);
       }
   }
   ```

   ```java
   @Data
   @AllArgsConstructor
   public class Order {
       private String name;
   }
   ```

   ```properties
   # application.properties
   tenant.order.name=macbook
   ```

   > 至此，我们实现了一个传统上的服务应用，接下来我们将其改造为SDK。



## 4.2、应用SDK化

1. 当考虑将产品转化为 SDK 时，首要考虑的是确定粒度，粒度的选择至关重要。通常情况下，**一个服务应该对应一个 SDK，这代表该SDK中包含了一个服务的所有依赖。**

2. 本文所述的是一个 app-web 应用，因此只需要一个对应该应用的 SDK。**然而，平台通常包含多个服务，因此每个服务应该对应一个独立的 SDK 包。**

3. SDK本质也是一个jar包，规范上应该以starter结尾，且该jar包包含了一个服务的所有功能及依赖，此刻的`app-web`模块已经包含了所有功能和依赖，故我们更名`app-web`为`app-starter`，此时结构如下：

   ```
   sdk-app
       └─app-api
       └─app-common
       └─app-starter
   ```

4. 因SDK包只作为功能提供，故不应包含`Application`启动类，删除`AppWebApplication`启动类。

5. 为了提高代码的清晰度和简洁性，在SDK化后，传统应用中通过注解（如@Service、@Component、@Bean等）将类托管至IOC容器中的方式将被统一。为此，在SDK的根目录下创建了`AppWebAutoConfiguration`类，使用@Configuration注解，并在其中统一使用@Bean方式构建所有被IOC托管的类，以便更方便地管理。下面是代码示例：

   ```java
   /**
    * 自动装配类
    */
   @Configurable
   @ComponentScan({ "org.example.sdk.app.starter" })
   public class AppWebAutoConfiguration {
   	@Bean
   	public OrderService orderService() {
   		return new OrderServiceImpl();
   	}
   }
   ```

6. 此外，在传统的设置中，`application.properties`文件中的参数配置通常是分散在各个服务层中，通过@Value注解的方式使用。这种分散的配置方式不够清晰，因此我们也应该将这些参数配置统一管理，并交由@Configuration注解的配置类来处理。为了与上面构建的`AppWebAutoConfiguration`类区分开来，我们可以新建一个`AppWebConfig`配置类，用于托管所有的配置参数。下面是示例代码：

   ```java
   /**
    * 参数配置类
    */
   @Getter
   @Configurable
   public class AppWebConfig {
   
   	@Value("${tenant.order.name}")
   	private String orderName;
   
   }
   ```

7. 在服务使用层，只需引入`AppWebConfig`配置类，就可以通过其提供的`get方法`轻松获取相应的配置参数，使代码结构更加清晰。下面是改造后的`OrderServiceImpl`示例：

   ```java
   public class OrderServiceImpl implements OrderService {
       @Resource
       private AppWebConfig appWebConfig;
       @Override
       public Order getOrder() {
           return new Order(appWebConfig.getOrderName());
       }
   }
   ```

8. 在当前阶段，我们还面临一个问题：**当合作机构引入了SDK包时，我们如何加载新增的两个配置类呢？**由于我们没有启动类，无法自动加载这些被@Bean注解托管的类。为了解决这个问题，我们可以利用Spring提供的自动装配功能，即 `spring.factories`。

9. `spring.factories` 是Spring框架中的一种特殊配置文件，用于自动化配置和加载Spring应用中的扩展点。在Spring Boot应用中，`spring.factories` 文件通常位于 `META-INF/spring.factories` 路径下。该文件使用标准的Java properties格式，其中包含了各种Spring应用中需要自动加载的配置信息，**我们在SDK中新建 `META-INF/spring.factories` 自动装配配置文件**，如下图：

   ![image01.png](picture%2Fimage04.png)


10. 截止目前，我们已经完成了SDK的基本改造，主要做了以下四件事：
    1. 删除 `AppWebApplication` 启动类。
    2. 新增托管所有被IOC容器管理的配置类 `AppWebAutoConfiguration`，以及管理所有配置项的 `AppWebConfig` 类。
    3. 修改代码中对配置的引用和使用方式。
    4. 新增 `META-INF/spring.factories`自动装配配置文件。

> 完成了sdk化改造，接下来我们在机构仓库中使用SDK。



## 4.3、机构使用

1. **首先要明确的是：每个合作机构对应一个独立的Git仓库，也就是说，每个机构都有自己的项目。如果有20家合作机构，那就对应着20个不同的机构仓库。这样的做法能够充分利用SDK的优势。**

   举例来说，假设机构A使用SDK-1.0版本，而机构B希望使用SDK-2.0版本，而机构C则希望在SDK-2.0版本的基础上新增一些定制化的功能。正是由于我们将不同机构之间通过不同的仓库方式进行分割，才能够灵活地满足上述需求。

   机构A的仓库只需依赖SDK-1.0版本，机构B的仓库依赖SDK-2.0版本，而机构C的仓库则可以依赖SDK-2.0版本，并且在该仓库中开发新的功能。这样做完全实现了隔离，同时也实现了灵活的配置和可插拔性，如下图所示：

   ![image01.png](picture%2Fimage05.png)


2. 了解了上述结构后我们模拟江苏银行合作机构，这里使用模块来模拟机构仓库，构建`sdl-jsbank`模块，又由于一个SDK对应一个服务应用，虽然我们只有一个sdk，但仍构建子模块`jsbanl-app-web`作为SDK的使用模块，此时结构如下：

   ```
   sdk-jsbank
       └─jsbank-app-web
   ```

   >  产品一般是微服务架构 ，服务个数 = SDK个数 = 机构仓库下web模块个数。

3. 在`jsbank-app-web`模块中依赖`app-starter`，如下

   ```xml
   <artifactId>jsbank-app-web</artifactId>
   
   <dependencies>
       <dependency>
           <groupId>org.example</groupId>
           <artifactId>app-starter</artifactId>
       </dependency>
   </dependencies>
   ```

4. 在`jsbank-app-web`模块根目录下创建`JsAppWebApplication`作为SDK应用的启动类，如下:

   ```java
   @SpringBootApplication
   public class JsAppWebApplication {
       public static void main(String[] args) {
           SpringApplication.run(JsAppWebApplication.class, args);
       }
   }
   ```

5. 此外还需在`jsbank-app-web`模块`resources`文件夹下创建`application.properties`放置SDK所需配置，如下:

   ```properties
   tenant.order.name=macbook
   ```

6. 现在，只需启动`JsAppWebApplication`应用，然后执行`curl ip:port/order`，即可得到SDK返回的结果：`{name: macbook}`。这是因为SDK已经配置了`spring.factories`自动装配文件，在机构端启动后便会自动加载SDK所需的托管类。

7. 假设现在江苏银行需要进行定制化开发，添加一个`/customize`接口。在这种情况下，我们只需要在 `jsbank-app-web` 模块中新增控制器层和相应的服务层代码即可。这样的操作完全与SDK标准化产品和其他机构的功能隔离开来。下面是新增的 `CustomizeController` 的代码示例：

   ```java
   @RestController
   public class CustomizeController {
       @Autowired
       private CustomizeService customizeService;
   
       @GetMapping("/customize")
       public String customize() {
           return customizeService.customize();
       }
   }
   ```

8. 新增`CustomizeService`接口及实现类`CustomizeServiceImpl`，代码如下：

   ```java
   public interface CustomizeService {
       String customize();
   }
   ```

   ```java
   @Service
   public class CustomizeServiceImpl implements CustomizeService {
       @Override
       public String customize() {
           return "jsBankCustomize";
       }
   }
   ```

9. 启动 `JsAppWebApplication` 应用，然后执行 `curl ip:port/customize`，即可获得定制化功能返回结果：`jsBankCustomize`。此时再次执行 `curl ip:port/order`，即可得到SDK返回的结果：`{name: macbook}`。这说明机构定制化功能与SDK标准化产品完全隔离。

> 至此机构使用SDK结束。



# 五、其他问题

在上述SDK化改造过程中，我们看到对于合作机构新增定制化功能已经有了很好的支持。但如果合作机构需要的定制化功能不仅仅是新增，而是需要对SDK本身进行更改呢？

举例：假设SDK中对外用户身份认证的 `/userAuth` 接口底层算法是普通加密，而南京银行需要使用国密算法进行身份认证。在这种情况下，既不能更改 `/userAuth` 接口的定义，也不能直接修改SDK中的代码，因为这会影响其他合作机构的使用。

这种类似情况十分常见，针对这类问题也很好解决，解决方式如下：

1. 首先我们的SDK模块中所有具体操作均要抽象成接口，即全面面向接口编程，这样才会给后续机构定制化留出口子。

2. 在机构仓库中根据SDK提供的接口从而实现对应的实现类，如上示例中在机构仓库中便可以实现`/userAuth`接口对应的`UserAuthservice`接口，从而实现国密算法。
3. 接下来有两种方式可以将我们自实现类注入进`UserAuthservice`接口中：
    1. 通过`JDK中的SPI`
    2. `springBoot`自身提供的`@conditionalonproperty`条件注解方式将实现注入
4. 以上两种方式都可以将自身实现内容注入到SDK接口中，从而实现上述示例的需求。
5. 关于`JDK中的SPI`实现方式可参考笔者另一篇文章: [Java SPI解读：揭秘服务提供接口的设计与应用]()
6. 关于`@conditionalonproperty`条件注解笔者后续会单独开一篇文章讲解，望读者见谅。



# 六、总结

在开发SDK时，需要考虑到不同合作机构可能有不同的定制化需求。为了满足这些需求，我们采取了SDK+机构仓库的架构设计，使得SDK和机构均具有高度的灵活性和可扩展性。

首先，将SDK设计为一个独立的jar包，其中包含了所有功能的总和。这使得合作机构可以轻松地引入SDK，并使用其中的功能。

其次，利用了Spring框架提供的自动装配功能，通过`spring.factories`文件实现了对托管类的自动加载，使得合作机构在引入SDK后可以自动加载所需的托管类，无需手动配置。

最后，通过定制开发的方式与机构紧密合作，这种方式既保持了产品的精简性，又实现了各机构版本的可插拔性。通过从一开始就设计良好的架构，可以极大地方便后续的扩展和维护，同时，这种方案也为未来的产品升级和技术创新提供了更大的空间和灵活性。



# 七、相关资料

-  [Java SPI解读：揭秘服务提供接口的设计与应用](https://blog.csdn.net/qq_35128600/article/details/137100512)
- [文章代码示例](https://github.com/LightGao-Hub/sdk-examples)
- [@ConditionalOnProperty注解使用](https://springdoc.cn/spring-conditionalonproperty/)

