/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oa.repository;
import cn.zhuatech.oa.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface NoticeRepository extends JpaRepository<Notice, Long> { List<Notice> findAllByOrderByPinnedDescPublishTimeDesc(); }
