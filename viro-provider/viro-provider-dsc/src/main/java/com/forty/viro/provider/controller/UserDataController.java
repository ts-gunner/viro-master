package com.forty.viro.provider.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forty.viro.core.dto.dsc.DscUserInfoRequest;
import com.forty.viro.core.model.BaseResponse;
import com.forty.viro.provider.mapper.DscUserInfoMapper;
import com.forty.viro.provider.model.DscUserInfo;
import com.forty.viro.provider.vo.DscUserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequestMapping("/user_data")
@RestController
public class UserDataController {

    @Autowired
    DscUserInfoMapper dscUserInfoMapper;

    @PostMapping("/search_user")
    public BaseResponse<Page<DscUserInfoVo>> searchUserData(@RequestBody DscUserInfoRequest request) {
        Page<DscUserInfo> dscUserInfoPage = new Page<>(request.getCurrentPage(), request.getPageSize());
        QueryWrapper<DscUserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(request.getUserId() != null, "user_id", request.getUserId());
        queryWrapper.eq(StringUtils.isNotBlank(request.getUserName()), "user_name", request.getUserName());
        queryWrapper.eq(StringUtils.isNotBlank(request.getEmail()), "email", request.getEmail());
        queryWrapper.eq(StringUtils.isNotBlank(request.getCardId()), "card_id", request.getCardId());
        queryWrapper.eq(StringUtils.isNotBlank(request.getSex()), "sex", request.getSex());
        queryWrapper.eq(StringUtils.isNotBlank(request.getNation()), "nation", request.getNation());
        queryWrapper.eq(StringUtils.isNotBlank(request.getPhoneNumber()), "phone_number", request.getPhoneNumber());
        List<DscUserInfo> userInfoList = dscUserInfoMapper.selectList(dscUserInfoPage, queryWrapper);
        List<DscUserInfoVo> dscUserInfoVos = userInfoList.stream().map(userInfo -> {
            DscUserInfoVo dscUserInfoVo = new DscUserInfoVo();
            BeanUtils.copyProperties(userInfo, dscUserInfoVo);
            return dscUserInfoVo;
        }).toList();
        Page<DscUserInfoVo> targetPage = new Page<>(request.getCurrentPage(), request.getPageSize());
        targetPage.setRecords(dscUserInfoVos);
        targetPage.setTotal(dscUserInfoVos.size());
        return new BaseResponse<>(targetPage);
    }
}
