import request from "@/request";

export function register(ruleForm) {

    return request({
        url: '/user/register',
        method: 'post',
        data: ruleForm
    })

}