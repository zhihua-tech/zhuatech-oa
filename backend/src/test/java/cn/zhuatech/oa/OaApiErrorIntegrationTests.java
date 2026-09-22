/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@SpringBootTest
@AutoConfigureMockMvc
class OaApiErrorIntegrationTests {
    @Autowired MockMvc mvc;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void employeeCanLoginWithDepartmentDetailsWhenOpenInViewIsDisabled() throws Exception {
        mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"demo\",\"password\":\"Demo@2026\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.user.role").value("EMPLOYEE"))
            .andExpect(jsonPath("$.data.user.departmentName").value("技术研发部"))
            .andExpect(jsonPath("$.data.token").isNotEmpty());
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void authenticationAndRequestErrorsUseStructuredStatusCodes() throws Exception {
        mvc.perform(get("/api/dashboard"))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("请先登录或重新登录"));
        mvc.perform(get("/api/dashboard").header("Authorization", "Bearer invalid-token"))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("请先登录或重新登录"));
        mvc.perform(get("/api/auth/login"))
            .andExpect(status().isMethodNotAllowed())
            .andExpect(jsonPath("$.message").value("请求方法不支持"));
        mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content("{bad-json"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("请求体格式不正确"));
    }
}
