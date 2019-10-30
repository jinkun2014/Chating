import request from '@/utils/request'

const list = async (params) => {
  return await request.post('/busi/vipOrder/list', params);
}
const add = async (params) => {
  return await request.post('/busi/vipOrder/add', params);
}
const update = async (params) => {
  return await request.post('/busi/vipOrder/update', params);
}
const del = async (params) => {
  return await request.post('/busi/vipOrder/delete', params);
}
const get = async (id) => {
  return await request.get(`/busi/vipOrder/getById?id=${id}`);
}

export {
  list,
  add,
  update,
  del,
  get
}
