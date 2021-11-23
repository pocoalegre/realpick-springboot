package com.realpick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.realpick.dao.BannerMapper;
import com.realpick.entity.Banner;
import com.realpick.service.BannerService;
import com.realpick.vo.ResultVO;
import com.realpick.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 头图 服务实现类
 * </p>
 *
 * @author pocoalegre
 * @since 2021-10-22
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public ResultVO addBanner(Banner banner) {

        //判断是否有重复顺序，否则无法添加
        Integer bannerSeq = banner.getBannerSeq();
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("banner_seq", bannerSeq);
        List<Banner> bannerList = bannerMapper.selectByMap(columnMap);

        //判断
        if (bannerList.size() == 0){
            int insert = bannerMapper.insert(banner);
            if (insert == 1){
                return new ResultVO(StatusCode.OK, "添加成功！", null);
            }else {
                return new ResultVO(StatusCode.NO, "添加失败！", null);
            }
        }else {
            return new ResultVO(StatusCode.NO, "banner顺序重复！", null);
        }
    }

    @Override
    public ResultVO modifyBanner(Banner banner) {

        //判断是否有重复顺序，否则无法添加
        Integer bannerSeq = banner.getBannerSeq();
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("banner_seq", bannerSeq);
        List<Banner> bannerList = bannerMapper.selectByMap(columnMap);

        //判断
        if (bannerList.size() != 0){
            if (!banner.getBannerId().equals(bannerList.get(0).getBannerId())){
                return new ResultVO(StatusCode.NO, "banner顺序重复！", null);
            }
        }

        //修改
        int update = bannerMapper.updateById(banner);
        if (update == 1){
            return new ResultVO(StatusCode.OK, "修改成功！", null);
        }else {
            return new ResultVO(StatusCode.NO, "修改失败！", null);
        }
    }

    @Override
    public ResultVO deleteBanner(Integer id) {

        //删除
        int delete = bannerMapper.deleteById(id);
        if (delete == 1){
            return new ResultVO(StatusCode.OK, "删除成功！", null);
        }else {
            return new ResultVO(StatusCode.NO, "删除失败！", null);
        }
    }

    @Override
    public ResultVO bannerList(String queryType, Integer pageNum, Integer pageSize) {

        //分页设置
        PageHelper.startPage(pageNum, pageSize);

        //查询条件封装
        QueryWrapper<Banner> qw = new QueryWrapper<>();

        //判断是否有查询条件
        if (!queryType.equals("")){
            qw.eq("banner_type", Integer.valueOf(queryType));
            qw.orderByAsc("banner_seq");
        }

        //查询列表并分页
        try {
            List<Banner> bannerList = bannerMapper.selectList(qw);
            PageInfo<Banner> bannerPageInfo = new PageInfo<>(bannerList);
            return new ResultVO(StatusCode.OK, "获取列表成功！", bannerPageInfo);
        }catch (Exception e){
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取列表失败！", null);
        }
    }

    @Override
    public ResultVO bannerById(Integer id) {
        try {
            Banner banner = bannerMapper.selectById(id);
            return new ResultVO(StatusCode.OK, "获取信息成功！", banner);
        }catch (Exception e){
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "获取信息失败！", null);
        }

    }
}
