import request from '@/utils/request'

// 查询总体统计
export function getOverallStat(params) {
  return request({
    url: "/system/project/hour/overall",
    method: "get",
    params
  })
}


// 查询总体统计详情-已上报-分页
export function getOverallDetail(params) {
  return request({
    url: "/system/project/hour/overall/detail",
    method: "get",
    params
  })
}

// 查询总体统计详情-详情统计
export function getOverallDetailStat(params){
  return request({
    url: "/system/project/hour/overall/detail/stat",
    method: "get",
    params
  })
}
// 查询总体统计详情-缺报人员详情(分页)
export function getOverallDetailMiss(params){
  return request({
    url: "/system/project/hour/overall/miss",
    method: "get",
    params
  })
}
// 查询总体统计详情-请假人员详情(分页)
export function getOverallDetailLeave(params){
  return request({
    url: "/system/project/hour/overall/leave",
    method: "get",
    params
  })
}