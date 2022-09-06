## 环境要求
1. `JDK 1.8+`
2. `fastjson`

## 安装依赖
```xml
<dependency>
    <groupId>tech.fullink.sdk</groupId>
    <artifactId>fullink-sdk-java</artifactId>
    <version>xxx</version>
</dependency>
```

## 快速开始
1. 从商务处获得`商户编号(customerId)`、`商户密钥(key)`、`产品编号(productCode)`、`服务地址(serverUrl)`；
2. 提供服务调用ip，配置白名单；
3. 初始化`FullinkClient`，建议单例使用；
4. 获取`灵犀分`或者`企业分`
```java
import tech.fullink.api.request.EnterpriseLxfRequest;
import tech.fullink.api.request.PersonalLxfRequest;
import tech.fullink.api.response.EnterpriseLxfResponse;
import tech.fullink.api.response.PersonalLxfResponse;

public class Main {
    public static void main(String[] args) {
        // 接口地址
        String serverUrl = "http://test.fullink.tech";
        // 商户编号
        String customerId = "xxx";
        // 商户密钥
        String key = "xxx";
        // 产品编号
        String productCode1 = "PROD1";
        String productCode2 = "PROD2";

        // 代码中建议单例使用
        FullinkClient client = new DefaultFullinkClient(serverUrl, customerId, key);

        // 灵犀分
        PersonalLxfRequest personalLxfRequest = new PersonalLxfRequest();
        // 商户需要保证唯一，重复会拒绝
        personalLxfRequest.setCustomerRequestId(uuid());
        personalLxfRequest.setCustomerId(customerId);
        personalLxfRequest.setCustomerProdId(productCode1);
        personalLxfRequest.setMobile("xxx");
        personalLxfRequest.setName("xxx");
        personalLxfRequest.setIdCardNo("xxx");
        PersonalLxfResponse personalLxfResponse = client.execute(personalLxfRequest);
        
        // 企业分
        EnterpriseLxfRequest enterpriseLxfRequest = new EnterpriseLxfRequest();
        // 商户需要保证唯一，重复会拒绝
        enterpriseLxfRequest.setCustomerRequestId(uuid());
        enterpriseLxfRequest.setCustomerId(customerId);
        enterpriseLxfRequest.setCustomerProdId(productCode2);
        enterpriseLxfRequest.setUscc("xxxx");
        // 非必填
        enterpriseLxfRequest.setMobile("xxxx");
        EnterpriseLxfResponse enterpriseLxfResponse = client.execute(enterpriseLxfRequest);
    }
}
```