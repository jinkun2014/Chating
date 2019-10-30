import request from '@/utils/request'

const list = async (params) => {
  return await request.post('/busi/servicePhone/list', params);
}
const add = async (params) => {
  return await request.post('/busi/servicePhone/add', params);
}
const update = async (params) => {
  return await request.post('/busi/servicePhone/update', params);
}
const del = async (params) => {
  return await request.post('/busi/servicePhone/delete', params);
}
const get = async (id) => {
  return await request.get(`/busi/servicePhone/getById?id=${id}`);
}

export {
  list,
  add,
  update,
  del,
  get
}
