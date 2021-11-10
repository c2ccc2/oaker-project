import request from '@/utils/request'


// 项目原型列表查询
export function listProto(query) {
  return request({
    url: '/pr/proto/list',
    method: 'get',
    params: query
  })
}
// 新增原型数据
export function createProto(data) {
  return request({
    type: 'form',
    url: '/pr/proto/create',
    method: 'post',
    data: data
  })
}
// 新增原型数据
export function uploadProto(data) {
  return request({
    url: '/pr/proto/upload',
    method: 'post',
    data: data
  })
}
// 删除原型信息
export function deleteProto(id) {
  return request({
    url: '/pr/proto/delete?id=' + id,
    method: 'get'
  })
}
//根据原型id查询文档
export function queryDoc(id) {
  return request({
    url: '/pr/doc/query?protoId=' + id,
    method: 'get',
  })
}
// 删除原型文档
export function deleteDoc(id) {
  return request({
    url: '/pr/doc/delete?id=' + id,
    method: 'delete'
  })
}
// 删除原型历史记录
export function delProtoRecord(id) {
  return request({
    url: '/pr/proto/record/delete?id=' + id,
    method: 'delete'
  })
}
// 项目原型列表查询
export function listProtoRecord(id) {
  return request({
    url: '/pr/proto/record/list?protoId=' + id,
    method: 'get'
  })
}
//修改项目原型
export function updateProto(data) {
  return request({
    type: 'form',
    url:'/pr/proto/update',
    method:'put',
    data:data
  })
}
