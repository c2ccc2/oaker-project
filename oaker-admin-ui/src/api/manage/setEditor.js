import request from '@/utils/request'

// 上传logo
export function logoUpdata() {
  return request({
    url: "/system/info/config/logo/upload",
    method: "post"
  })
}

// 查询系统信息
export function getConfig() {
  return request({
    url: "/system/info/config/get",
    method: "get"
  })
}

// 修改系统信息接口
export function editConfig(data) {
  return request({
    type: 'form',
    url: "/system/info/config/edit",
    method: "put",
    data
  })
}
