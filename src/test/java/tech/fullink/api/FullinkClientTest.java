package tech.fullink.api;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import tech.fullink.api.domain.ThreeElements;
import tech.fullink.api.request.CommonLxfRequest;
import tech.fullink.api.request.EnterpriseLxfRequest;
import tech.fullink.api.request.PersonalLxfRequest;
import tech.fullink.api.response.CommonLxfResponse;
import tech.fullink.api.response.EnterpriseLxfResponse;
import tech.fullink.api.response.PersonalLxfResponse;

/**
 * @author crow
 */
public class FullinkClientTest {

    FullinkClient client;

    @Before
    public void setUp() {
        client = new DefaultFullinkClient(
                "http://lxf.fullink.test",
                "xxx",
                "xxxx"
        );
    }

    @Test
    public void testLxf() {
        PersonalLxfRequest request = new PersonalLxfRequest();
        request.setCustomerRequestId(CommonUtil.uuid());
        request.setCustomerId("xxx");
        request.setCustomerProdId("xxxx");
        request.setMobile(CommonUtil.uuid());
        request.setName(CommonUtil.uuid());
        request.setIdCardNo(CommonUtil.uuid());
        PersonalLxfResponse response = client.execute(request);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void testEnterprise() {
        EnterpriseLxfRequest request = new EnterpriseLxfRequest();
        request.setCustomerRequestId(CommonUtil.uuid());
        request.setCustomerId("xxx");
        request.setCustomerProdId("xxx");
        request.setUscc("12345677");
        request.setMobile("13300000001");
        request.setName("张三");
        request.setIdCardNo("1222313123123123");
        EnterpriseLxfResponse response = client.execute(request);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void testCommon() {
        ThreeElements threeElements = new ThreeElements();
        threeElements.setName("xxx");
        threeElements.setMobile("xxx");
        threeElements.setIdCardNo("xxx");

        CommonLxfRequest request = new CommonLxfRequest();
        request.setCustomerId("xxx");
        request.setCustomerProdId("xxx");
        request.setCustomerRequestId(CommonUtil.uuid());
        request.setBizModel(threeElements);

        CommonLxfResponse response = client.execute(request);
        System.out.println(JSON.toJSONString(response));
    }

}
