import request from '@/utils/request'

const login = async (params) => {
    return await request.post('/login', params);
}
const logout = async () => {
    return await request.get('/logout');
}
const loadCodePic = async () => {
    return await request.get('/code');
}

export {
    login,
    logout,
    loadCodePic
}
