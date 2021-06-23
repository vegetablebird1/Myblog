import request from "@/request";

export function login(LoginDto) {
    return request({
        url: '/login',
        method: 'post',
        data: {
            username: LoginDto.username,
            password: LoginDto.password
        }
    })
}


export function logout(){
    return request({
        url: '/logout',
        method: 'get',
        headers: {
            "Authorization": localStorage.getItem("token")
        }
    })
}

export function isLogin() {
    return request({
        url: '/isLogin',
        method: 'get'
    })
}