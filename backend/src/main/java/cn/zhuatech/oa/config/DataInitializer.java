/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.config;

import cn.zhuatech.oa.model.*;
import cn.zhuatech.oa.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {
    private final DepartmentRepository departments; private final UserRepository users; private final NoticeRepository notices; private final TodoTaskRepository tasks; private final PasswordEncoder encoder;
    public DataInitializer(DepartmentRepository departments, UserRepository users, NoticeRepository notices, TodoTaskRepository tasks, PasswordEncoder encoder) { this.departments=departments; this.users=users; this.notices=notices; this.tasks=tasks; this.encoder=encoder; }
    @Override @Transactional public void run(String... args) {
        if (users.count() > 0) return;
        Department tech = departments.save(new Department("TECH", "技术研发部", 10));
        Department adminDept = departments.save(new Department("ADMIN", "综合管理部", 20));
        Department sales = departments.save(new Department("SALES", "市场商务部", 30));
        UserAccount admin = new UserAccount("admin", encoder.encode("ZhuaTech@2026"), "系统管理员", UserAccount.Role.ADMIN, adminDept); admin.updateProfile("contact@zhuatech.cn", "021-00000000", "平台管理员"); users.save(admin);
        UserAccount demo = new UserAccount("demo", encoder.encode("Demo@2026"), "知华员工", UserAccount.Role.EMPLOYEE, tech); demo.updateProfile("demo@zhuatech.cn", "13800000000", "软件工程师"); users.save(demo);
        UserAccount colleague = new UserAccount("zhangsan", encoder.encode("Demo@2026"), "张珊", UserAccount.Role.EMPLOYEE, sales); colleague.updateProfile("zhangsan@zhuatech.cn", "13900000000", "商务经理"); users.save(colleague);
        notices.save(new Notice("欢迎体验知华科技 OA 社区源码版", "本工程仅允许个人用于非商业学习交流。商业使用、企业内部使用或生产部署须事先取得上海如静知华信息科技有限公司的书面授权。", "系统管理员", true));
        notices.save(new Notice("社区源码版使用说明", "首次登录后请及时修改初始化账号。需要商业授权、私有化部署或深度定制，请访问知华科技官网咨询。", "系统管理员", false));
        tasks.save(new TodoTask(demo, "完成 OA 系统体验", "体验考勤打卡和请假审批流程", LocalDate.now().plusDays(2), "HIGH"));
    }
}
