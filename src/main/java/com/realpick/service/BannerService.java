package com.realpick.service;

import com.realpick.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.realpick.vo.ResultVO;

/**
 * <p>
 * 头图 服务类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
public interface BannerService extends IService<Banner> {

    //添加banner
    ResultVO addBanner(Banner banner);

    //修改banner
    ResultVO modifyBanner(Banner banner);

    //删除banner
    ResultVO deleteBanner(Integer id);

    //banner列表
    ResultVO bannerList(String queryType, Integer pageNum, Integer pageSize);

    //单条banner
    ResultVO bannerById(Integer id);

    //首页banner展示
    ResultVO bannerIndexList();

}
