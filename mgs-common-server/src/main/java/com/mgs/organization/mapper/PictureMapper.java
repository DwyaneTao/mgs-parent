package com.mgs.organization.mapper;

import com.mgs.common.MyMapper;
import com.mgs.organization.domain.PicturePO;
import com.mgs.organization.remote.dto.CompanyBusinessLicenseUrlDTO;
import com.mgs.organization.remote.dto.PictureLicenseDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author py
 * @date 2019/6/28 12:00
 **/
public interface PictureMapper extends MyMapper<PicturePO> {
    List<PictureLicenseDTO>  queryPictureLicense(@Param("companyCode")String companyCode,@Param("pictureType")Integer pictureType);
    List<CompanyBusinessLicenseUrlDTO>  PictureLicense(@Param("companyCode")String companyCode, @Param("pictureType")Integer pictureType);
}
