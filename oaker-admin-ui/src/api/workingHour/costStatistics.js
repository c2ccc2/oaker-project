import request from '@/utils/request'

// 查询成本统计列表-分页
export function getCostQuery(params) {
  return request({
    url: "/mh/cost/query",
    method: "get",
    params
  })
}


// 查询成本统计页面参与职位下拉框
export function getCostProjectPosts(projectId) {
  return request({
    url: "/mh/cost/project/posts?projectId="+projectId,
    method: "get"
  })
}

// 查询成本统计页面参与人员下拉框
export function getCostProjectUsers(projectId) {
  return request({
    url: "/mh/cost/project/users?projectId="+projectId,
    method: "get"
  })

}
