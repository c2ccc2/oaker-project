import request from '@/utils/request'

// 查询角色列表
export function listRole(query) {
  return request({
    url: '/system/role/list',
    method: 'get',
    params: query
  })
}

// 修改角色
export function updateRole(data) {
  return request({
    url: '/system/role',
    method: 'put',
    data: data
  })
}

// 删除角色
export function delRole(roleId) {
  return request({
    url: '/system/role/' + roleId,
    method: 'delete'
  })
}

// 创建项目
export function createProject(data) {
  return request({
    url: '/system/project/create',
    method: 'post',
    data: data
  })
}

// 查询项目列表
export function listProject(query) {
  return request({
    url: '/system/project/list',
    method: 'get',
    params: query
  })
}
// 删除项目
export function delProject(roleId) {
  return request({
    url: '/system/project/remove?projectId=' + roleId,
    method: 'delete'
  })
}
// 获取用户-项目经理
export function getBox() {
  return request({
    url: '/system/user/box',
    method: 'get'
  })
}
//获取状态字典
export function getDicttype(params) {
  return request({
    url: '/system/dict/data/type/' + params,
    method: 'get'
  })
}
//按项目状态查询项目列表
export function getProjectStatus(params) {
  return request({
    url: '/system/project/list?projectStatus=' + params,
    method: 'get'
  })
}
// 修改项目状态
export function updaProjectStatus(projectId, projectStatus) {
  const data = {
    projectId,
    projectStatus
  }
  return request({
    url: '/system/project/status',
    method: 'put',
    params: data
  })
}
//用户查询自己参与的项目
export function getMyProjectAll() {
  return request({
    url: '/system/project/user/my/project/all',
    method: 'get'
  })
}
//用户查询按照状态自己参与的项目
export function getMyProjectStatus(params) {
  return request({
    url: '/system/project/user/my/project/all?projectStatus=' + params,
    method: 'get'
  })
}
//查询我的工时列表
export function getMyHourList(endDate, startDate) {
  return request({
    url: `/mh/hour/list?endDate=${endDate}&startDate=${startDate}`,
    method: 'get'
  })
}
// 用户查询参与项目不包含已归档的项目
export function getMyActorProject() {
  return request({
    url: '/system/project/user/my/project',
    method: 'get'
  })
}
// 用户填报提交工时
export function createHour(data) {
  return request({
    url: '/mh/hour/create',
    method: 'post',
    data: data
  })
}
// 查询我的工时详情
export function getMyHourDetailt(id) {
  return request({
    url: '/mh/hour/detail?hourId=' + id,
    method: 'get'
  })
}
// 用户修改填报工时
export function updateHour(data) {
  return request({
    url: '/mh/hour/edit',
    method: 'put',
    data: data
  })
}
// 我的统计
export function getHourStat(date) {
  return request({
    url: '/mh/hour/stat?date=' + date,
    method: 'get'
  })
}
// 我的统计-详细模式
export function getHourStatDetail(date) {
  return request({
    url: '/mh/hour/stat/detail?date=' + date,
    method: 'get'
  })
}
// 项目工时统计列表
export function projectHourStat() {
  return request({
    url:'/system/project/hour/stat',
    // url:'/system/project/hour/stat?projectStatus=',
    method:'get'
  })
}
// 项目工时统计列表-按状态
export function projectHourStatStatus(status) {
  return request({
    url:'/system/project/hour/stat?projectStatus='+status,
    method:'get'
  })
}
// 项目上报记录
export function projectHourStatFillDetail(params) {
  return request({
    url:`/system/project/hour/stat/fill/detail?date=${params.date}&projectId=${params.projectId}`,
    method:'get'
  })
}
// 项目工时统计列表详情-按月统计
export function projectHourMonth(id) {
  return request({
    url:'/system/project/hour/stat/hour/month?projectId='+id,
    method:'get'
  })
}
// 项目工时统计列表详情-按月统计详情
export function projectHourMonthDetail(id,date) {
  return request({
    url:`/system/project/hour/stat/hour/month/detail?projectId=${id}&yearMonth=${date}`,
    method:'get'
  })
}
// 项目工时统计列表详情-按人统计
export function projectHourUser(id,date) {
  return request({
    url:`/system/project/hour/stat/hour/user?projectId=${id}&yearMonth=${date}`,
    method:'get'
  })
}
