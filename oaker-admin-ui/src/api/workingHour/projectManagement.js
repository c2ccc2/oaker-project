import request from '@/utils/request'

// 上报记录统计
export function getHourStatFill(date,id) {
  return request({
    url: "/system/project/hour/stat/fill?localDate="+date+"&projectId="+id,
    method: "get",
    
  })
}

// 项目工时统计详情
export function getHourStatFillDetail(params) {
  return request({
    url: "/system/project/hour/stat/fill/detail",
    method: "get",
    params
  })
}

// 查询成本统计页面参与职位下拉框
export function getCostProjectPosts(projectId) {
  return request({
    url: "/system/project/hour/project/posts?projectId="+projectId,
    method: "get"
  })
}

// 查询成本统计页面参与人员下拉框
export function getCostProjectUsers(projectId) {
  return request({
    url: "/system/project/hour/project/users?projectId="+projectId,
    method: "get"
  })

}
//查询上报记录-日历
export function getProjectHourStatCalendar(date,id) {
  return request({
    url:'/system/project/hour/stat/calendar?localDate='+date+"&projectId="+id,
    method:'get',
  })
}