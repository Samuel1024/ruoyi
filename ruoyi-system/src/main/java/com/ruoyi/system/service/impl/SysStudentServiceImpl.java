package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.Md5Utils;
import com.ruoyi.system.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysStudentMapper;
import com.ruoyi.system.domain.SysStudent;
import com.ruoyi.system.service.ISysStudentService;
import com.ruoyi.common.core.text.Convert;

/**
 * 学生信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-03-22
 */
@Service
public class SysStudentServiceImpl implements ISysStudentService 
{
    @Autowired
    private SysStudentMapper sysStudentMapper;

    /**
     * 查询学生信息
     * 
     * @param studentId 学生信息ID
     * @return 学生信息
     */
    @Override
    public SysStudent selectSysStudentById(Long studentId)
    {
        return sysStudentMapper.selectSysStudentById(studentId);
    }

    /**
     * 查询学生信息列表
     * 
     * @param sysStudent 学生信息
     * @return 学生信息
     */
    @Override
    public List<SysStudent> selectSysStudentList(SysStudent sysStudent)
    {
        return sysStudentMapper.selectSysStudentList(sysStudent);
    }

    /**
     * 新增学生信息
     * 
     * @param sysStudent 学生信息
     * @return 结果
     */
    @Override
    public int insertSysStudent(SysStudent sysStudent)
    {
        return sysStudentMapper.insertSysStudent(sysStudent);
    }

    /**
     * 修改学生信息
     * 
     * @param sysStudent 学生信息
     * @return 结果
     */
    @Override
    public int updateSysStudent(SysStudent sysStudent)
    {
        return sysStudentMapper.updateSysStudent(sysStudent);
    }

    /**
     * 删除学生信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysStudentByIds(String ids)
    {
        return sysStudentMapper.deleteSysStudentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除学生信息信息
     * 
     * @param studentId 学生信息ID
     * @return 结果
     */
    @Override
    public int deleteSysStudentById(Long studentId)
    {
        return sysStudentMapper.deleteSysStudentById(studentId);
    }

    @Override
    public String importStu(List<SysStudent> studentList, boolean updateSupport, String operName) {

        if (StringUtils. isNull (studentList) || studentList.size() == 0) {
            throw new BusinessException("导入 t student  数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg =  new StringBuilder();
        StringBuilder failureMsg =  new StringBuilder();
        for (SysStudent student : studentList){
            try{
                // 验证是否存在这个用户
                SysStudent stu =  sysStudentMapper.selectSysStudentById(student.getStudentId());
                if (StringUtils. isNull (stu)){
                    student.setCreateBy(operName);
                    this.insertSysStudent(student);
                    successNum++;
                    successMsg.append( "<br/>" + successNum + "学生 " + student.getStudentName() +  "  导入成功");
                } else if (updateSupport){
                    student.setUpdateBy(operName);
                    this.updateSysStudent(student);
                    successNum++;
                    successMsg.append( "<br/>" + successNum + " 、账号 " + student.getStudentName() +  "  更新成 功");
                } else{
                    failureNum++;
                    failureMsg.append( "<br/>" + failureNum + "、账号 " + student.getStudentName() +  "  已存在");
                }
            } catch (Exception e){
                failureNum++;
                String msg =  "<br/ >" + failureNum +  "、账号  + student.getStudentName() + 导入失败：";
                failureMsg.append(msg + e.getMessage());
                e.printStackTrace();
            }
        }
        if (failureNum > 0){
            failureMsg.insert(0, " 很抱歉，导入失败！共 " + failureNum +  "  条数据格式不 正确，错误如下： ");
            throw new BusinessException(failureMsg.toString());
        }
        else{
                    successMsg.insert(0, " 恭喜您，数据已全部导入成功！共 " + successNum +  "  条，数据如下： ");
        }
        return successMsg.toString();
    }
}
