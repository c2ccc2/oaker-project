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
// 查询项目详情
export function projectDetail(projectId) {
  return request({
    url: '/system/project/' + projectId,
    method: 'get'
  })
}
// 查询项目成员
export function userList(projectId) {
  return request({
    url: '/system/project/user/list?projectId=' + projectId,
    method: 'get'
  })
}
// 查询项目可选成员
export function userSelectList(projectId) {
  return request({
    url: '/system/project/user/select?projectId=' + projectId,
    method: 'get'
  })
}

//项目工时设置
export function updateHour(data) {
  return request({
    url: '/system/project/hour/edit',
    method: 'put',
    params: data
  })
}
//项目概要设置
export function updateModify(data) {
  return request({
    url: '/system/project/modify',
    method: 'put',
    data: data
  })
}

// 更改项目成员每日上报分组接口
export function updateUserEveryday(data) {
  return request({
    type:'form',
    url: '/system/project/user/everyday',
    method: 'put',
    data: data
  })
}

// 项目人员增加
export function addProjectUsers(data) {
  return request({
    url: '/system/project/user/create',
    method: 'post',
    data: data
  })
}
// 移除项目成员
export function removeProjectUser(data) {
  return request({
    url: '/system/project/user/remove',
    method: 'put',
    params: data
  })
}