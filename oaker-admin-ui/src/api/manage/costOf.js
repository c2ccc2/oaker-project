import request from '@/utils/request'

// 查询成本统计配置
export function geCosConf() {
  return request({
    url: "/mh/cost/conf/query",
    method: "get"
  })
}

// 修改成本统计设置接口
export function editCost(data) {
  return request({
    type:'form',
    url: "/mh/cost/edit",
    method: "put",
    data
  })
}
// 查询岗位成本列表-分页
export function getCostPost(params) {
  return request({
    url: "/mh/cost/post/query",
    method: "get",
    params
  })
}

// 设置岗位成本
export function setPostCost(data) {
  return request({
    url: "/mh/cost/post/set",
    method: "post",
    data
  })
}

// 查询人员成本列表-分页
export function getCostUser(params) {
  return request({
    url: "/mh/cost/user/query",
    method: "get",
    params
  })
}

// 设置人员成本
export function setUserCost(data) {
  return request({
    url: "/mh/cost/user/set",
    method: "post",
    data
  })
}
