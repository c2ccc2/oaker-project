import request from '@/utils/request'

// 查询项目列表
export function getIndexProjectStat() {
  return request({
    url: '/index/projectStat',
    method: 'get',
  })
}
// 主页上报统计
export function getIndexFillInfo() {
  return request({
    url: '/index/fillInfo',
    method: 'get',
  })
}
//用户信息登录查询
export function getIndexLoginUser(query) {
  return request({
    url: '/index/login/user',
    method: 'get',
    params: query
  })
}
