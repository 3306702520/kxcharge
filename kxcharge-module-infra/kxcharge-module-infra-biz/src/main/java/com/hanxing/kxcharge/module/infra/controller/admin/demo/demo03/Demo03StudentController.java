package com.hanxing.kxcharge.module.infra.controller.admin.demo.demo03;

import com.hanxing.kxcharge.framework.apilog.core.annotation.ApiAccessLog;
import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.common.pojo.PageParam;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.common.util.object.BeanUtils;
import com.hanxing.kxcharge.framework.excel.core.util.ExcelUtils;
import com.hanxing.kxcharge.module.infra.controller.admin.demo.demo03.vo.Demo03StudentPageReqVO;
import com.hanxing.kxcharge.module.infra.controller.admin.demo.demo03.vo.Demo03StudentRespVO;
import com.hanxing.kxcharge.module.infra.controller.admin.demo.demo03.vo.Demo03StudentSaveReqVO;
import com.hanxing.kxcharge.module.infra.dal.dataobject.demo.demo03.Demo03CourseDO;
import com.hanxing.kxcharge.module.infra.dal.dataobject.demo.demo03.Demo03GradeDO;
import com.hanxing.kxcharge.module.infra.dal.dataobject.demo.demo03.Demo03StudentDO;
import com.hanxing.kxcharge.module.infra.service.demo.demo03.Demo03StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.hanxing.kxcharge.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 学生")
@RestController
@RequestMapping("/infra/demo03-student")
@Validated
public class Demo03StudentController {

    @Resource
    private Demo03StudentService demo03StudentService;

    @PostMapping("/create")
    @Operation(summary = "创建学生")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:create')")
    public CommonResult<Long> createDemo03Student(@Valid @RequestBody Demo03StudentSaveReqVO createReqVO) {
        return success(demo03StudentService.createDemo03Student(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新学生")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:update')")
    public CommonResult<Boolean> updateDemo03Student(@Valid @RequestBody Demo03StudentSaveReqVO updateReqVO) {
        demo03StudentService.updateDemo03Student(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除学生")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:delete')")
    public CommonResult<Boolean> deleteDemo03Student(@RequestParam("id") Long id) {
        demo03StudentService.deleteDemo03Student(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得学生")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:query')")
    public CommonResult<Demo03StudentRespVO> getDemo03Student(@RequestParam("id") Long id) {
        Demo03StudentDO demo03Student = demo03StudentService.getDemo03Student(id);
        return success(BeanUtils.toBean(demo03Student, Demo03StudentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得学生分页")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:query')")
    public CommonResult<PageResult<Demo03StudentRespVO>> getDemo03StudentPage(@Valid Demo03StudentPageReqVO pageReqVO) {
        PageResult<Demo03StudentDO> pageResult = demo03StudentService.getDemo03StudentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, Demo03StudentRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出学生 Excel")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDemo03StudentExcel(@Valid Demo03StudentPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<Demo03StudentDO> list = demo03StudentService.getDemo03StudentPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "学生.xls", "数据", Demo03StudentRespVO.class,
                        BeanUtils.toBean(list, Demo03StudentRespVO.class));
    }

    // ==================== 子表（学生课程） ====================

    @GetMapping("/demo03-course/page")
    @Operation(summary = "获得学生课程分页")
    @Parameter(name = "studentId", description = "学生编号")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:query')")
    public CommonResult<PageResult<Demo03CourseDO>> getDemo03CoursePage(PageParam pageReqVO,
                                                                        @RequestParam("studentId") Long studentId) {
        return success(demo03StudentService.getDemo03CoursePage(pageReqVO, studentId));
    }

    @PostMapping("/demo03-course/create")
    @Operation(summary = "创建学生课程")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:create')")
    public CommonResult<Long> createDemo03Course(@Valid @RequestBody Demo03CourseDO demo03Course) {
        return success(demo03StudentService.createDemo03Course(demo03Course));
    }

    @PutMapping("/demo03-course/update")
    @Operation(summary = "更新学生课程")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:update')")
    public CommonResult<Boolean> updateDemo03Course(@Valid @RequestBody Demo03CourseDO demo03Course) {
        demo03StudentService.updateDemo03Course(demo03Course);
        return success(true);
    }

    @DeleteMapping("/demo03-course/delete")
    @Parameter(name = "id", description = "编号", required = true)
    @Operation(summary = "删除学生课程")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:delete')")
    public CommonResult<Boolean> deleteDemo03Course(@RequestParam("id") Long id) {
        demo03StudentService.deleteDemo03Course(id);
        return success(true);
    }

    @GetMapping("/demo03-course/get")
    @Operation(summary = "获得学生课程")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:query')")
    public CommonResult<Demo03CourseDO> getDemo03Course(@RequestParam("id") Long id) {
        return success(demo03StudentService.getDemo03Course(id));
    }

    @GetMapping("/demo03-course/list-by-student-id")
    @Operation(summary = "获得学生课程列表")
    @Parameter(name = "studentId", description = "学生编号")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:query')")
    public CommonResult<List<Demo03CourseDO>> getDemo03CourseListByStudentId(@RequestParam("studentId") Long studentId) {
        return success(demo03StudentService.getDemo03CourseListByStudentId(studentId));
    }

    // ==================== 子表（学生班级） ====================

    @GetMapping("/demo03-grade/page")
    @Operation(summary = "获得学生班级分页")
    @Parameter(name = "studentId", description = "学生编号")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:query')")
    public CommonResult<PageResult<Demo03GradeDO>> getDemo03GradePage(PageParam pageReqVO,
                                                                      @RequestParam("studentId") Long studentId) {
        return success(demo03StudentService.getDemo03GradePage(pageReqVO, studentId));
    }

    @PostMapping("/demo03-grade/create")
    @Operation(summary = "创建学生班级")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:create')")
    public CommonResult<Long> createDemo03Grade(@Valid @RequestBody Demo03GradeDO demo03Grade) {
        return success(demo03StudentService.createDemo03Grade(demo03Grade));
    }

    @PutMapping("/demo03-grade/update")
    @Operation(summary = "更新学生班级")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:update')")
    public CommonResult<Boolean> updateDemo03Grade(@Valid @RequestBody Demo03GradeDO demo03Grade) {
        demo03StudentService.updateDemo03Grade(demo03Grade);
        return success(true);
    }

    @DeleteMapping("/demo03-grade/delete")
    @Parameter(name = "id", description = "编号", required = true)
    @Operation(summary = "删除学生班级")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:delete')")
    public CommonResult<Boolean> deleteDemo03Grade(@RequestParam("id") Long id) {
        demo03StudentService.deleteDemo03Grade(id);
        return success(true);
    }

    @GetMapping("/demo03-grade/get")
    @Operation(summary = "获得学生班级")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:query')")
    public CommonResult<Demo03GradeDO> getDemo03Grade(@RequestParam("id") Long id) {
        return success(demo03StudentService.getDemo03Grade(id));
    }

    @GetMapping("/demo03-grade/get-by-student-id")
    @Operation(summary = "获得学生班级")
    @Parameter(name = "studentId", description = "学生编号")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:query')")
    public CommonResult<Demo03GradeDO> getDemo03GradeByStudentId(@RequestParam("studentId") Long studentId) {
        return success(demo03StudentService.getDemo03GradeByStudentId(studentId));
    }

}