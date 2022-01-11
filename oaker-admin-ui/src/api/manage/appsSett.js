import request from '@/utils/request'



// 查询应用配置信息信息
export function geApptConfig() {
  return request({
    url: "/system/app/config",
    method: "get"
  })
}

// 修改系统信息接口
export function editAppConfig(data) {
  return request({
    type:'form',
    url: "/system/app/config/edit",
    method: "put",
    data
  })
}
